package com.nexus.NexusEndOfMonthProcessingBatch.tasklet;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.*;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity.TksMasterCompanyEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity.TksMasterEmployeeEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.file_system.InvoiceDataComputationService;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.jinji.dto.FreeeApiHrEmployeeListDto;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.jinji.dto.FreeeApiHrEmployeePayrollStatementsListDto;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.jinji.request_body.FreeeApiHrEmployeesRequestBody;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.jinji.request_body.FreeeApiHrSalariesEmployeePayrollStatementsRequestBody;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.kaikei.dto.FreeeApiAccountingCompanyDto;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.kaikei.request_body.FreeeApiAccountingCompanyRequestBody;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.rest_template.FreeeApiRestTemplate;
import com.nexus.NexusEndOfMonthProcessingBatch.logger.BatchLogger;
import com.nexus.NexusEndOfMonthProcessingBatch.logger.CustomLogger;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.entity_collection.EompEntityCollectionSource;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.entity_collection.EompSheet2EntityCollection;
import com.nexus.NexusEndOfMonthProcessingBatch.service.*;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.MyDateUtility;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FreeeApiから情報を取得してデータベースに取り込む
 */
@Component
@RequiredArgsConstructor
public class ImportInformationFromFreeeTasklet implements Tasklet {

    public static class FreeeCompanyData {
        int tksCompanyId;
        //FreeeApiから取得した会社情報
        FreeeApiAccountingCompanyDto freeeApiCompanyDto;
        //会社に所属する社員の情報
        List<FreeeEmployeeData> employeeList = new ArrayList<>();

        public void addEmployee(FreeeApiHrEmployeeListDto.Employee employeeDto, FreeeApiHrEmployeePayrollStatementsListDto.EmployeePayrollStatements freeeApiEmployeePayrollStatementsDto) {
            if(employeeDto == null || freeeApiEmployeePayrollStatementsDto == null) return;
            FreeeEmployeeData employee = new FreeeEmployeeData();
            employee.employeeDto = employeeDto;
            employee.freeeApiEmployeePayrollStatementsDto = freeeApiEmployeePayrollStatementsDto;
            employeeList.add(employee);
        }
    }

    @Data
    public static class FreeeEmployeeData {
        //FreeeApiから取得した社員情報
        FreeeApiHrEmployeeListDto.Employee employeeDto = null;
        //FreeeApiから取得した給与情報
        FreeeApiHrEmployeePayrollStatementsListDto.EmployeePayrollStatements freeeApiEmployeePayrollStatementsDto = null;
    }

    @Autowired
    MstCompaniesThatOutputProfitInformationService mstCompaniesThatOutputProfitInformationService;

    @Autowired
    NexusFreeeApiInfoService nexusFreeeApiInfoService;

    @Autowired
    NexusProfitListSummaryService nexusProfitListSummaryService;

    @Autowired
    NexusLinkingInformationBetweenTksAndFreeeService nexusLinkingInformationBetweenTksAndFreeeService;

    @Autowired
    TksMasterCompanyService tksMasterCompanyService;

    @Autowired
    TksMasterEmployeeService tksMasterEmployeeService;

    @Autowired
    NexusEndOfMonthProcessingSheet02Service nexusEndOfMonthProcessingSheet02Service;

    @Autowired
    EompEntityCollectionSource eompEntityCollectionSource;

    @Autowired
    InvoiceDataComputationService invoiceDataComputationService;

    @Autowired
    FreeeApiRestTemplate freeeApiRestTemplate;

    @Autowired
    CustomLogger customLogger;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        customLogger.print("FreeeApiから情報を取り込む処理を行う  開始");
        LocalDateTime paymentDate = MyDateUtility.localDateToLocalDateTime(MyDateUtility.nowLocalDate().withDayOfMonth(1));
        for(NexusFreeeApiInfoEntity nexusFreeeApiInfoEntity : nexusFreeeApiInfoService.get()) {
            oneRoop(nexusFreeeApiInfoEntity, paymentDate);
        }
        customLogger.print("FreeeApiから情報を取り込む処理を行う　終了");
        return RepeatStatus.FINISHED;
    }

    /**
     * Freeeから情報を取り込みDBに登録する
     * @throws Exception
     */
    void oneRoop(NexusFreeeApiInfoEntity nexusFreeeApiInfoEntity, LocalDateTime paymentDate) throws Exception {

        List<MstCompaniesThatOutputProfitInformationEntity> mstCompaniesThatOutputProfitInformationEntityList = mstCompaniesThatOutputProfitInformationService.findAll();

        List<FreeeCompanyData> freeeCompanyDataList = getCompanies(nexusFreeeApiInfoEntity, freeeApiRestTemplate, paymentDate, mstCompaniesThatOutputProfitInformationEntityList);
        if(freeeCompanyDataList==null || freeeCompanyDataList.isEmpty()) {
            customLogger.print("登録すべき情報がないようです");
            return;
        }

        //DBから会社情報を取得するために利用する会社名
        List<Integer> companyIdList = new ArrayList<>();
        //DBから社員情報を取得するために利用する社員メールアドレス
        List<String> freeeIdList = new ArrayList<>();

        for(FreeeCompanyData freeeCompanyData : freeeCompanyDataList) {
            if(!companyIdList.contains(freeeCompanyData.tksCompanyId)) companyIdList.add(freeeCompanyData.tksCompanyId);
            for(FreeeEmployeeData employee : freeeCompanyData.employeeList) {
                FreeeApiHrEmployeeListDto.Employee employeeDto = employee.employeeDto;
                String freeeId = employeeDto.getNum();
                if(!freeeIdList.contains(freeeId)) freeeIdList.add(freeeId);
            }
        }

        List<NexusLinkingInformationBetweenTksAndFreeeEntity> nexusLinkingInformationBetweenTksAndFreeeEntityList = nexusLinkingInformationBetweenTksAndFreeeService.findByFreeeIdList(freeeIdList);
        List<Integer> tksIds = new ArrayList<>();
        for(NexusLinkingInformationBetweenTksAndFreeeEntity entity : nexusLinkingInformationBetweenTksAndFreeeEntityList) {
            tksIds.add(entity.getTksData());
        }

        //TKS会社情報
        List<TksMasterCompanyEntity> tksMasterCompanyEntityList = tksMasterCompanyService.findByIds(companyIdList);
        //TKS社員情報
        List<TksMasterEmployeeEntity> tksMasterEmployeeEntityList = tksMasterEmployeeService.findByIds(tksIds);
        //社員IDと支払日で月末処理レコード取得
        List<NexusEndOfMonthProcessingSheet02Entity> nexusEndOfMonthProcessingSheet02Entities = nexusEndOfMonthProcessingSheet02Service.findByEmployeeIdsAndWorkingDate(
                tksMasterEmployeeEntityList.stream()
                        .map(TksMasterEmployeeEntity::getID)
                        .distinct()
                        .collect(Collectors.toList()),
                paymentDate);
        List<EompSheet2EntityCollection> eompSheet2EntityCollectionList = eompEntityCollectionSource.createEndOfMonthProcessingSheet2EntityCollectionList(nexusEndOfMonthProcessingSheet02Entities);

        //利益一覧
        List<NexusProfitListSummaryEntity> nexusProfitListSummaryEntityList = new ArrayList<>();

        for(FreeeCompanyData freeeCompanyData : freeeCompanyDataList) {
            TksMasterCompanyEntity tksMasterCompanyEntity = tksMasterCompanyEntityList.stream()
                    .filter(node->node.getID().equals(freeeCompanyData.tksCompanyId))
                    .findFirst().orElse(null);
            if(tksMasterCompanyEntity==null) continue;
            for(FreeeEmployeeData freeeEmployeeData : freeeCompanyData.employeeList) {
                NexusLinkingInformationBetweenTksAndFreeeEntity nexusLinkingInformationBetweenTksAndFreeeEntity = nexusLinkingInformationBetweenTksAndFreeeEntityList.stream()
                        .filter(node->node.getFreeeData().equals(freeeEmployeeData.employeeDto.getNum()))
                        .findFirst().orElse(null);
                if(nexusLinkingInformationBetweenTksAndFreeeEntity==null) continue;
                TksMasterEmployeeEntity tksMasterEmployeeEntity = tksMasterEmployeeEntityList.stream()
                        .filter(node->node.getID().equals(nexusLinkingInformationBetweenTksAndFreeeEntity.getTksData()))
                        .findFirst().orElse(null);
                if(tksMasterEmployeeEntity==null) continue;

                EompSheet2EntityCollection eompSheet2EntityCollection = eompSheet2EntityCollectionList.stream().filter(node->
                                node.getNexusEndOfMonthProcessingSheet02Entity().getEmployeeId().equals(tksMasterEmployeeEntity.getID())
                                        && node.getNexusEndOfMonthProcessingSheet02Entity().getWorkingDate().getYear()==paymentDate.getYear()
                                        && node.getNexusEndOfMonthProcessingSheet02Entity().getWorkingDate().getMonthValue()==paymentDate.getMonthValue())
                        .findFirst().orElse(null);

                NexusProfitListSummaryEntity nexusProfitListSummaryEntity = new NexusProfitListSummaryEntity();
                nexusProfitListSummaryEntity.setting(
                        paymentDate.toLocalDate(), invoiceDataComputationService.total(eompSheet2EntityCollection),
                        freeeCompanyData.freeeApiCompanyDto,
                        freeeEmployeeData.employeeDto,
                        freeeEmployeeData.freeeApiEmployeePayrollStatementsDto,
                        tksMasterCompanyEntity,
                        tksMasterEmployeeEntity);
                if(nexusProfitListSummaryEntity.getEmployeeId()!=0) nexusProfitListSummaryEntityList.add(nexusProfitListSummaryEntity);
            }
        }

        BatchLogger batchLogger = new BatchLogger(customLogger, "利益一覧登録", nexusProfitListSummaryEntityList.size());
        for(NexusProfitListSummaryEntity nexusProfitListsummaryEntity : nexusProfitListSummaryEntityList) {
            if(nexusProfitListSummaryService.insert(nexusProfitListsummaryEntity)>0) batchLogger.addSuccessCounter();
        }
        batchLogger.finish();

    }

    List<FreeeCompanyData> getCompanies(NexusFreeeApiInfoEntity nexusFreeeApiInfoEntity, FreeeApiRestTemplate freeeApiRestTemplate, LocalDateTime ld, List<MstCompaniesThatOutputProfitInformationEntity> mstCompaniesThatOutputProfitInformationEntityList) throws Exception {
        if(freeeApiRestTemplate==null || ld==null || mstCompaniesThatOutputProfitInformationEntityList==null || mstCompaniesThatOutputProfitInformationEntityList.isEmpty()) return null;
        List<FreeeCompanyData> freeeCompanyDataList = new ArrayList<>();
        FreeeApiAccountingCompanyRequestBody freeeApiAccountingCompanyRequestBody = new FreeeApiAccountingCompanyRequestBody();
        freeeApiAccountingCompanyRequestBody.setId(nexusFreeeApiInfoEntity.getCompanyId());
        FreeeApiAccountingCompanyDto freeeApiAccountingCompanyDto = freeeApiRestTemplate.company(nexusFreeeApiInfoEntity.getAccessToken(), freeeApiAccountingCompanyRequestBody);
        if(freeeApiAccountingCompanyDto == null || freeeApiAccountingCompanyDto.getCompany()==null) return freeeCompanyDataList;
        customLogger.print("DBの会社ID:  " + nexusFreeeApiInfoEntity.getCompanyId());
        MstCompaniesThatOutputProfitInformationEntity mstCompaniesThatOutputProfitInformationEntity = mstCompaniesThatOutputProfitInformationEntityList.stream()
                .filter(node -> node.getCompanyName().equals(freeeApiAccountingCompanyDto.getCompany().getName()) || node.getCompanyShortName().equals(freeeApiAccountingCompanyDto.getCompany().getName()))
                .findFirst().orElse(null);
        //出力すべき会社ではなければ追加しない
        if(mstCompaniesThatOutputProfitInformationEntity==null) return freeeCompanyDataList;

        FreeeApiHrEmployeesRequestBody freeeApiHrEmployeesRequestBody = new FreeeApiHrEmployeesRequestBody(ld);
        freeeApiHrEmployeesRequestBody.setCompanyId(freeeApiAccountingCompanyDto.getCompany().getId());
        List<FreeeApiHrEmployeeListDto.Employee> employeeList = getHrEmployeesByCompanyId(nexusFreeeApiInfoEntity.getAccessToken(), freeeApiHrEmployeesRequestBody);
        customLogger.print("Freeeから取り込んだ社員数: " + employeeList.size());

        FreeeApiHrSalariesEmployeePayrollStatementsRequestBody freeeApiHrSalariesEmployeePayrollStatementsRequestBody = new FreeeApiHrSalariesEmployeePayrollStatementsRequestBody(ld);
        freeeApiHrSalariesEmployeePayrollStatementsRequestBody.setCompanyId(freeeApiAccountingCompanyDto.getCompany().getId());
        List<FreeeApiHrEmployeePayrollStatementsListDto.EmployeePayrollStatements> employeePayrollStatementsListDto = getEmployeePayrollStatementsByCompanyId(nexusFreeeApiInfoEntity.getAccessToken(), freeeApiHrSalariesEmployeePayrollStatementsRequestBody);

        FreeeCompanyData freeeCompanyData = new FreeeCompanyData();
        freeeCompanyData.tksCompanyId = mstCompaniesThatOutputProfitInformationEntity.getTksCompanyId();
        freeeCompanyData.freeeApiCompanyDto = freeeApiAccountingCompanyDto;
        for(FreeeApiHrEmployeeListDto.Employee employeeDto : employeeList) {
            //会社の社員を入れていく
            freeeCompanyData.addEmployee(employeeDto, employeePayrollStatementsListDto.stream()
                    .filter(node -> node.getEmployeeId() == employeeDto.getId())
                    .findFirst().orElse(null));
        }
        freeeCompanyDataList.add(freeeCompanyData);
        return freeeCompanyDataList;
    }

    /**
     * 事業所の従業員の取得を行う
     * @param freeeApiHrEmployeesRequestBody    リクエストボディ
     * @return  従業員の一覧
     * @throws Exception
     */
    List<FreeeApiHrEmployeeListDto.Employee> getHrEmployeesByCompanyId(String accessToken, FreeeApiHrEmployeesRequestBody freeeApiHrEmployeesRequestBody) throws Exception {
        List<FreeeApiHrEmployeeListDto.Employee> employeeList = new ArrayList<>();
        int max = 0;
        do {
            freeeApiHrEmployeesRequestBody.setOffset(max);
            freeeApiHrEmployeesRequestBody.setLimit(100);
            FreeeApiHrEmployeeListDto freeeApiHrEmployeeListDto = freeeApiRestTemplate.employees(accessToken, freeeApiHrEmployeesRequestBody);
            if(freeeApiHrEmployeeListDto.getEmployees().isEmpty()) break;
            max += freeeApiHrEmployeeListDto.getEmployees().size();
            employeeList.addAll(freeeApiHrEmployeeListDto.getEmployees());
        } while (true);
        return employeeList;
    }

    /**
     * 給与明細一覧の取得を行う
     * @param requestParam  リクエストボディ
     * @return  給与明細の一覧
     * @throws Exception
     */
    List<FreeeApiHrEmployeePayrollStatementsListDto.EmployeePayrollStatements> getEmployeePayrollStatementsByCompanyId(String accessToken, FreeeApiHrSalariesEmployeePayrollStatementsRequestBody requestParam) throws Exception {
        List<FreeeApiHrEmployeePayrollStatementsListDto.EmployeePayrollStatements> result = new ArrayList<>();
        int max = 0;
        do {
            requestParam.setOffset(max);
            requestParam.setLimit(100);
            FreeeApiHrEmployeePayrollStatementsListDto freeeApiHrEmployeePayrollStatementsListDto = freeeApiRestTemplate.salariesEmployeePayrollStatements(accessToken, requestParam);
            if(freeeApiHrEmployeePayrollStatementsListDto.getEmployeePayrollStatements().isEmpty()) break;
            max += freeeApiHrEmployeePayrollStatementsListDto.getEmployeePayrollStatements().size();
            result.addAll(freeeApiHrEmployeePayrollStatementsListDto.getEmployeePayrollStatements());
        } while (true);
        return result;
    }
}