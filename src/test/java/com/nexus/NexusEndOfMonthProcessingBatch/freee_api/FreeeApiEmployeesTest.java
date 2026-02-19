package com.nexus.NexusEndOfMonthProcessingBatch.freee_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusFreeeApiInfoEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.exception.FreeeApiErrorException;
import com.nexus.NexusEndOfMonthProcessingBatch.freee_api.test_data.FreeeApiRestTemplateTestUtility;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.constant.FreeeApiConstant;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.jinji.dto.FreeeApiHrEmployeeListDto;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.jinji.request_body.FreeeApiHrEmployeesRequestBody;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(MockitoExtension.class)
@DisplayName("FreeeApiRestTemplate 従業員一覧取得APIのテスト")
@Slf4j
public class FreeeApiEmployeesTest {

    static final String API_NAME = "従業員一覧取得API";

    @InjectMocks
    FreeeApiRestTemplate freeeApiRestTemplate;

    //従業員一覧取得APIのリクエストボディ
    FreeeApiHrEmployeesRequestBody requestBody;

    //従業員一覧取得APIのレスポンス
    FreeeApiHrEmployeeListDto responseBody;

    @BeforeEach
    public void setUp() {

        NexusFreeeApiInfoEntity nexusFreeeApiInfoEntity = FreeeApiRestTemplateTestUtility.getNexusFreeeApiInfoEntity().get(0);
        LocalDateTime ldt = LocalDateTime.of(2026,1,1, 0, 0);
        requestBody = new FreeeApiHrEmployeesRequestBody(ldt){{
            setCompanyId(nexusFreeeApiInfoEntity.getCompanyId());
        }};
        responseBody = new  FreeeApiHrEmployeeListDto(){{
            setEmployees(new ArrayList<Employee>() {{
                add(new Employee(){{
                    setId(1);
                    setNum("test0001");
                    setHealthInsuranceRule(new HealthInsuranceRule(){{
                        setStandardMonthlyRemuneration(540000);
                    }});
                    setWelfarePensionInsuranceRule(new WelfarePensionInsuranceRule(){{
                        setStandardMonthlyRemuneration(540000);
                    }});
                }});
            }});
        }};

    }

    void createMock(NexusFreeeApiInfoEntity nexusFreeeApiInfoEntity, FreeeApiHrEmployeesRequestBody requestBody, FreeeApiHrEmployeeListDto responseBody, Exception exception) throws JsonProcessingException, FreeeApiErrorException {

        // 返却されるレスポンス
        String jsonResponseBody = freeeApiRestTemplate.convertToJson(responseBody);

        String url = freeeApiRestTemplate.generateUrl(FreeeApiConstant.HrUrl.employees, requestBody);

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

        FreeeApiHrEmployeeListDto response = freeeApiRestTemplate.employees(FreeeApiRestTemplateTestUtility.getNexusFreeeApiInfoEntity().get(0).getAccessToken(), requestBody);

        assertNotNull(response);

        assertNotNull(response.getEmployees());
        assertEquals(responseBody.getEmployees().size(), response.getEmployees().size());
        int index = 0;
        for(FreeeApiHrEmployeeListDto.Employee employee : response.getEmployees()) {
            FreeeApiHrEmployeeListDto.Employee target = responseBody.getEmployees().get(index++);
            assertEquals(target.getId(), employee.getId());
            assertEquals(target.getNum(), employee.getNum());
            assertEquals(target.getHealthInsuranceRule(), employee.getHealthInsuranceRule());
            if(employee.getHealthInsuranceRule()!=null) {
                assertEquals(target.getHealthInsuranceRule().getStandardMonthlyRemuneration(), employee.getHealthInsuranceRule().getStandardMonthlyRemuneration());
            }
            assertEquals(target.getWelfarePensionInsuranceRule(), employee.getWelfarePensionInsuranceRule());
            if(employee.getWelfarePensionInsuranceRule()!=null) {
                assertEquals(target.getWelfarePensionInsuranceRule().getStandardMonthlyRemuneration(), employee.getWelfarePensionInsuranceRule().getStandardMonthlyRemuneration());
            }
        }
    }

}