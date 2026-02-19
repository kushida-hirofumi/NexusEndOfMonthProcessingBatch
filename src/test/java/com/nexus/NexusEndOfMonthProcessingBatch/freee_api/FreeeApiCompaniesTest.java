package com.nexus.NexusEndOfMonthProcessingBatch.freee_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusFreeeApiInfoEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.exception.FreeeApiErrorException;
import com.nexus.NexusEndOfMonthProcessingBatch.freee_api.test_data.FreeeApiRestTemplateTestUtility;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.constant.FreeeApiConstant;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.kaikei.dto.FreeeApiAccountingCompanyDto;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.kaikei.request_body.FreeeApiAccountingCompanyRequestBody;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(MockitoExtension.class)
@DisplayName("FreeeApiRestTemplate 会社情報取得APIのテスト")
@Slf4j
public class FreeeApiCompaniesTest {

    static final String API_NAME = "会社情報取得API";

    @InjectMocks
    FreeeApiRestTemplate freeeApiRestTemplate;

    //会社情報取得APIのリクエストボディ
    FreeeApiAccountingCompanyRequestBody requestBody;

    //会社情報取得APIのレスポンス
    FreeeApiAccountingCompanyDto responseBody;

    @BeforeEach
    public void setUp() {
        NexusFreeeApiInfoEntity nexusFreeeApiInfoEntity = FreeeApiRestTemplateTestUtility.getNexusFreeeApiInfoEntity().get(0);
        requestBody = new FreeeApiAccountingCompanyRequestBody(){{
            setId(nexusFreeeApiInfoEntity.getCompanyId());
        }};
        responseBody = new FreeeApiAccountingCompanyDto(){{
            setCompany(new Company(){{
                setId(nexusFreeeApiInfoEntity.getCompanyId());
                setName(nexusFreeeApiInfoEntity.getCompanyName());
            }});
        }};
    }

    void createMock(NexusFreeeApiInfoEntity nexusFreeeApiInfoEntity, FreeeApiAccountingCompanyRequestBody requestBody, FreeeApiAccountingCompanyDto responseBody, Exception exception) throws JsonProcessingException, FreeeApiErrorException {

        // 返却されるレスポンス
        String jsonResponseBody = freeeApiRestTemplate.convertToJson(responseBody);

        String url = freeeApiRestTemplate.generateUrl(FreeeApiConstant.AccountingUrl.companiesCompanyId, requestBody);

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

        FreeeApiAccountingCompanyDto response = freeeApiRestTemplate.company(FreeeApiRestTemplateTestUtility.getNexusFreeeApiInfoEntity().get(0).getAccessToken(), requestBody);

        assertNotNull(response);

        assertNotNull(response.getCompany());
        assertEquals(responseBody.getCompany().getId(), response.getCompany().getId());
        assertEquals(responseBody.getCompany().getName(), response.getCompany().getName());
    }

}