package com.nexus.NexusEndOfMonthProcessingBatch.freee_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusFreeeApiInfoEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.exception.FreeeApiErrorException;
import com.nexus.NexusEndOfMonthProcessingBatch.freee_api.test_data.FreeeApiRestTemplateTestUtility;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.constant.FreeeApiConstant;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.jinji.dto.FreeeApiHrEmployeePayrollStatementsListDto;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.jinji.request_body.FreeeApiHrSalariesEmployeePayrollStatementsRequestBody;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.rest_template.FreeeApiRestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.ResponseActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(MockitoExtension.class)
@DisplayName("FreeeApiRestTemplate 給与明細一覧取得APIのテスト")
@Slf4j
public class FreeeApiSalariesEmployeePayrollStatementsTest {

    static final String API_NAME = "給与明細一覧取得API";

    @InjectMocks
    FreeeApiRestTemplate freeeApiRestTemplate;

    //給与明細一覧取得APIのリクエストボディ
    FreeeApiHrSalariesEmployeePayrollStatementsRequestBody requestBody;

    //給与明細一覧取得APIのレスポンス
    FreeeApiHrEmployeePayrollStatementsListDto responseBody;

    @BeforeEach
    public void setUp() {
        NexusFreeeApiInfoEntity nexusFreeeApiInfoEntity = FreeeApiRestTemplateTestUtility.getNexusFreeeApiInfoEntity().get(0);

        LocalDateTime ldt = LocalDateTime.of(2026,1,1, 0, 0);
        requestBody = new  FreeeApiHrSalariesEmployeePayrollStatementsRequestBody(ldt){{
            setCompanyId(nexusFreeeApiInfoEntity.getCompanyId());
        }};
        responseBody = new   FreeeApiHrEmployeePayrollStatementsListDto(){{
            setEmployeePayrollStatements(new ArrayList<>(){{
                add(new  EmployeePayrollStatements(){{
                    setEmployeeId(1);
                    setBasicPayAmount(450000);
                    setTotalDeductionEmployerShare(220000);
                    setGrossPaymentAmount(240000);

                    setPayments(new ArrayList<>(){{
                        add(new Payment(){{
                            setName("test0001");
                            setAmount(450000);
                        }});
                    }});
                    setDeductions(new ArrayList<>(){{
                        add(new Payment(){{
                            setName("test0001");
                            setAmount(450000);
                        }});
                    }});
                    setDeductionsEmployerShareList(new ArrayList<>(){{
                        add(new Payment(){{
                            setName("test0001");
                            setAmount(450000);
                        }});
                    }});
                }});
            }});
        }};
    }

    void createMock(NexusFreeeApiInfoEntity nexusFreeeApiInfoEntity, FreeeApiHrSalariesEmployeePayrollStatementsRequestBody requestBody, FreeeApiHrEmployeePayrollStatementsListDto responseBody, Exception exception) throws JsonProcessingException, FreeeApiErrorException {
        // 返却されるレスポンス
        String jsonResponseBody = freeeApiRestTemplate.convertToJson(responseBody);

        String url = freeeApiRestTemplate.generateUrl(FreeeApiConstant.HrUrl.salariesEmployeePayrollStatements, requestBody);

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(freeeApiRestTemplate.getRestTemplate());
        ResponseActions responseActions = mockServer
                .expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("Authorization", "Bearer " + nexusFreeeApiInfoEntity.getAccessToken()));
        if(exception != null) {
            responseActions.andRespond(withStatus(HttpStatus.BAD_REQUEST).body("{\"error\":\"refresh_token\"}").contentType(MediaType.APPLICATION_JSON));
        } else {
            responseActions.andRespond(withSuccess(jsonResponseBody, MediaType.APPLICATION_JSON));
        }
    }

    @Test
    @DisplayName(API_NAME + "_正常系")
    public void success() throws Exception {

        createMock(FreeeApiRestTemplateTestUtility.getNexusFreeeApiInfoEntity().get(0), requestBody, responseBody, null);

        FreeeApiHrEmployeePayrollStatementsListDto response = freeeApiRestTemplate.salariesEmployeePayrollStatements(FreeeApiRestTemplateTestUtility.getNexusFreeeApiInfoEntity().get(0).getAccessToken(), requestBody);

        assertNotNull(response);

        assertNotNull(response.getEmployeePayrollStatements());
        checkEmployeePayrollStatements(responseBody.getEmployeePayrollStatements(), response.getEmployeePayrollStatements());
    }

    /**
     * 給与明細のテスト
     * @param targetList
     * @param testDataList
     */
    void checkEmployeePayrollStatements(List<FreeeApiHrEmployeePayrollStatementsListDto.EmployeePayrollStatements> targetList, List<FreeeApiHrEmployeePayrollStatementsListDto.EmployeePayrollStatements> testDataList) {
        assertEquals(targetList.size(), testDataList.size());
        if(!testDataList.isEmpty()) {
            int index = 0;
            for(FreeeApiHrEmployeePayrollStatementsListDto.EmployeePayrollStatements testData : testDataList) {
                FreeeApiHrEmployeePayrollStatementsListDto.EmployeePayrollStatements target = targetList.get(index++);
                assertEquals(target.getEmployeeId(), testData.getEmployeeId());
                assertEquals(target.getBasicPayAmount(), testData.getBasicPayAmount());
                assertEquals(target.getTotalDeductionEmployerShare(), testData.getTotalDeductionEmployerShare());
                assertEquals(target.getGrossPaymentAmount(), testData.getGrossPaymentAmount());

                checkEmployeePayrollStatementsPayment(target.getPayments(), testData.getPayments());
                checkEmployeePayrollStatementsPayment(target.getDeductions(), testData.getDeductions());
                checkEmployeePayrollStatementsPayment(target.getDeductionsEmployerShareList(), testData.getDeductionsEmployerShareList());
            }
        }
    }

    /**
     * 支払い情報のテスト
     * @param targetList
     * @param testDataList
     */
    void checkEmployeePayrollStatementsPayment(List<FreeeApiHrEmployeePayrollStatementsListDto.EmployeePayrollStatements.Payment> targetList, List<FreeeApiHrEmployeePayrollStatementsListDto.EmployeePayrollStatements.Payment> testDataList) {
        assertEquals(targetList.size(), testDataList.size());
        if(!testDataList.isEmpty()) {
            int index = 0;
            for(FreeeApiHrEmployeePayrollStatementsListDto.EmployeePayrollStatements.Payment testData : testDataList) {
                FreeeApiHrEmployeePayrollStatementsListDto.EmployeePayrollStatements.Payment target = targetList.get(index++);
                assertEquals(target.getName(), testData.getName());
                assertEquals(target.getAmount(), testData.getAmount());
            }
        }
    }
}