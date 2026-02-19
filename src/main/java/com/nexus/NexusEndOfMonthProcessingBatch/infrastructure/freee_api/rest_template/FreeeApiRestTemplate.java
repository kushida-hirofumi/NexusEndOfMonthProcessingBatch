package com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.rest_template;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.nexus.NexusEndOfMonthProcessingBatch.exception.FreeeApiErrorException;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.constant.FreeeApiConstant;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.jinji.dto.FreeeApiHrEmployeeListDto;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.jinji.dto.FreeeApiHrEmployeePayrollStatementsListDto;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.jinji.request_body.FreeeApiHrEmployeesRequestBody;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.jinji.request_body.FreeeApiHrSalariesEmployeePayrollStatementsRequestBody;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.kaikei.dto.FreeeApiAccountingCompanyDto;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.kaikei.request_body.FreeeApiAccountingCompanyRequestBody;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.public_model.dto.FreeeApiPublicTokenDto;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.public_model.request_body.FreeeApiPublicAccessTokenRequestBody;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * FreeeApiを利用する
 */
@Component
@Getter
public class FreeeApiRestTemplate {

    RestTemplate restTemplate = new RestTemplate();

    /**
     * FreeeApiのアクセストークンの取得
     */
    public FreeeApiPublicTokenDto accessToken(FreeeApiPublicAccessTokenRequestBody requestParam) throws Exception {
        if(requestParam == null) return null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(convertToJson(requestParam), headers);
        ResponseEntity<FreeeApiPublicTokenDto> response = restTemplate.postForEntity(FreeeApiConstant.PublicUrl.accessToken.getPath(), entity, FreeeApiPublicTokenDto.class);
        return response.getBody();
    }

    /**
     * 事業所の取得
     */
    public FreeeApiAccountingCompanyDto company(String accessToken, FreeeApiAccountingCompanyRequestBody requestBody) throws Exception {
        ResponseEntity<FreeeApiAccountingCompanyDto> response = restTemplate.exchange(
                generateUrl(FreeeApiConstant.AccountingUrl.companiesCompanyId, requestBody),
                HttpMethod.GET, createHttpEntity(accessToken),
                FreeeApiAccountingCompanyDto.class,
                requestBody.getId());
        return response.getBody();
    }

    /**
     * 従業員一覧の取得
     */
    public FreeeApiHrEmployeeListDto employees(String accessToken, FreeeApiHrEmployeesRequestBody requestParam) throws Exception {
        ResponseEntity<FreeeApiHrEmployeeListDto> response = restTemplate.exchange(
                generateUrl(FreeeApiConstant.HrUrl.employees, requestParam),
                HttpMethod.GET, createHttpEntity(accessToken),
                FreeeApiHrEmployeeListDto.class);
        return response.getBody();
    }

    /**
     * 給与明細一覧の取得
     */
    public FreeeApiHrEmployeePayrollStatementsListDto salariesEmployeePayrollStatements(String accessToken, FreeeApiHrSalariesEmployeePayrollStatementsRequestBody requestParam) throws Exception {
        ResponseEntity<FreeeApiHrEmployeePayrollStatementsListDto> response = restTemplate.exchange(
                generateUrl(FreeeApiConstant.HrUrl.salariesEmployeePayrollStatements, requestParam),
                HttpMethod.GET, createHttpEntity(accessToken),
                FreeeApiHrEmployeePayrollStatementsListDto.class);
        return response.getBody();
    }

    /**
     * URL内のパスパラメータを値に変換する処理を行う
     * @param path
     * @param map
     * @return
     */
    String convertUrlPath(String path, Map<String, Object> map) {
        for(String key : map.keySet()) {
            String val = "{" + key + "}";
            if(path.contains(val)) path = path.replace(val, map.get(key).toString());
        }
        return path;
    }

    /**
     * Getのパスにリクエストパラメータを付与する処理を行う
     * @param pathListEnum  リクエストパス
     * @param requestParam  リクエストパラメータオブジェクト
     * @return  生成したURL
     */
    public String generateUrl(FreeeApiConstant.PathListEnum pathListEnum, Object requestParam) throws JsonProcessingException {
        String json = convertToJson(requestParam);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(json, Map.class);
        String convertedPath = convertUrlPath(pathListEnum.getPath(), map);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(convertedPath);
        for(String key : map.keySet()) {
            uriComponentsBuilder.queryParam(key, map.get(key));
        }
        return uriComponentsBuilder.build().toUriString();
    }

    /**
     * Httpヘッダーにアクセストークン埋め込み
     */
    HttpEntity<Void> createHttpEntity(String accessToken) throws FreeeApiErrorException {
        if(StringUtils.isBlank(accessToken)) throw new FreeeApiErrorException("アクセストークンが存在しません");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        return new HttpEntity<>(headers);
    }

    public String convertToJson(Object object) throws JsonProcessingException {
        // ObjectMapperにスネークケース変換を設定
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        // JSON文字列に変換（スネークケースになる）
        return mapper.writeValueAsString(object);
    }

}
