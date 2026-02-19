package com.nexus.NexusEndOfMonthProcessingBatch.freee_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusFreeeApiInfoEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.freee_api.test_data.FreeeApiRestTemplateTestUtility;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.constant.FreeeApiConstant;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.public_model.dto.FreeeApiPublicTokenDto;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.public_model.request_body.FreeeApiPublicAccessTokenRequestBody;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * FreeeApiのアクセストークン取得APIのテスト
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("FreeeApiRestTemplate  アクセストークン取得APIのテスト")
@Slf4j
public class FreeeApiAccessTokenTest {

    static final String API_NAME = "アクセストークン取得API";

    @InjectMocks
    FreeeApiRestTemplate freeeApiRestTemplate;

    //アクセストークン更新APIのリクエストボディ
    FreeeApiPublicAccessTokenRequestBody requestBody;

    //アクセストークン更新APIのレスポンス
    FreeeApiPublicTokenDto responseBody;

    @BeforeEach
    public void setUp() {

        NexusFreeeApiInfoEntity nexusFreeeApiInfoEntity = FreeeApiRestTemplateTestUtility.getNexusFreeeApiInfoEntity().get(0);
        requestBody = FreeeApiPublicAccessTokenRequestBody.createRefreshToken(nexusFreeeApiInfoEntity.getClientId(), nexusFreeeApiInfoEntity.getClientSecret(), nexusFreeeApiInfoEntity.getRefreshToken());

        responseBody = new FreeeApiPublicTokenDto() {{
            setAccessToken("aafg44gg");
            setRefreshToken("Dgery54e3rae");
        }};
    }

    /**
     * アクセストークンの更新処理用Mockの生成用
     * @param requestBody   リクエストボディ
     * @param responseBody  取得できるべきレスポンスボディ
     * @param exception     発生すべき例外
     * @throws JsonProcessingException
     */
    void createMock(FreeeApiPublicAccessTokenRequestBody requestBody, FreeeApiPublicTokenDto responseBody, Exception exception) throws JsonProcessingException {

        // 送信するリクエストボディ
        String jsonRequestBody = freeeApiRestTemplate.convertToJson(requestBody);

        // 返却されるレスポンス
        String jsonResponseBody = freeeApiRestTemplate.convertToJson(responseBody);

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(freeeApiRestTemplate.getRestTemplate());
        if(exception != null) {
            mockServer
                    .expect(requestTo(FreeeApiConstant.PublicUrl.accessToken.getPath()))
                    .andExpect(method(HttpMethod.POST))
                    .andExpect(content().json(jsonRequestBody))
                    .andRespond(withStatus(HttpStatus.BAD_REQUEST).body("{\"error\":\"refresh_token\"}").contentType(MediaType.APPLICATION_JSON));
        } else {
            mockServer
                    .expect(requestTo(FreeeApiConstant.PublicUrl.accessToken.getPath()))
                    .andExpect(method(HttpMethod.POST))
                    .andExpect(content().json(jsonRequestBody))
                    .andRespond(withSuccess(jsonResponseBody, MediaType.APPLICATION_JSON));
        }
    }

    @Test
    @DisplayName(API_NAME + "_正常系")
    public void success() throws Exception {

        createMock(requestBody, responseBody, null);

        FreeeApiPublicTokenDto responseUser = freeeApiRestTemplate.accessToken(requestBody);

        assertNotNull(responseUser);

        assertEquals(responseBody.getAccessToken(), responseUser.getAccessToken());
        assertEquals(responseBody.getRefreshToken(), responseUser.getRefreshToken());
    }

    @Test
    @DisplayName(API_NAME + "_異常系_リクエストボディnull")
    public void requestBodyNull() throws Exception {

        createMock(requestBody, responseBody, null);

        FreeeApiPublicTokenDto responseUser = freeeApiRestTemplate.accessToken(null);

        assertNull(responseUser);
    }

    @Test
    @DisplayName(API_NAME + "_異常系_リフレッシュトークンが違う")
    public void refreshTokenErr() throws Exception {

        createMock(requestBody, responseBody, new Exception(""));

        requestBody.setRefreshToken("afger4");
        AssertionError ex =  assertThrows(AssertionError.class, () -> freeeApiRestTemplate.accessToken(requestBody));
        assertTrue(ex.getMessage().contains("refresh_token"));
    }

}