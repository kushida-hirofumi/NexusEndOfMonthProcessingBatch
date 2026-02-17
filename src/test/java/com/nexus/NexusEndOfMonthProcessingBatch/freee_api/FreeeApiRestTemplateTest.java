package com.nexus.NexusEndOfMonthProcessingBatch.freee_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusFreeeApiInfoEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.constant.FreeeApiConstant;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.public_model.dto.FreeeApiPublicTokenDto;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.public_model.request_body.FreeeApiPublicAccessTokenRequestBody;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.rest_template.FreeeApiRestTemplate;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(MockitoExtension.class)
@DisplayName("FreeeApiRestTemplate テスト")
public class FreeeApiRestTemplateTest {

    @InjectMocks
    FreeeApiRestTemplate freeeApiRestTemplate;

    List<NexusFreeeApiInfoEntity> nexusFreeeApiInfoEntities;

    //アクセストークン更新APIのリクエストボディ
    FreeeApiPublicAccessTokenRequestBody freeeApiPublicAccessTokenRequestBody;

    //アクセストークン更新APIのレスポンス
    FreeeApiPublicTokenDto freeeApiPublicTokenDto;

    @BeforeEach
    public void setUp() {
        nexusFreeeApiInfoEntities = new ArrayList<>();
        NexusFreeeApiInfoEntity nexusFreeeApiInfoEntity = new NexusFreeeApiInfoEntity();
        nexusFreeeApiInfoEntity.setClientId("00001");
        nexusFreeeApiInfoEntity.setClientSecret("00001");
        nexusFreeeApiInfoEntity.setRefreshToken("Afgeag43t");
        nexusFreeeApiInfoEntity.setAccessToken("S34tujh3aa");
        nexusFreeeApiInfoEntities.add(nexusFreeeApiInfoEntity);

        freeeApiPublicAccessTokenRequestBody = FreeeApiPublicAccessTokenRequestBody.createRefreshToken(nexusFreeeApiInfoEntity.getClientId(), nexusFreeeApiInfoEntity.getClientSecret(), nexusFreeeApiInfoEntity.getRefreshToken());

        freeeApiPublicTokenDto = new FreeeApiPublicTokenDto() {{
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
    void mockAccessToken(FreeeApiPublicAccessTokenRequestBody requestBody, FreeeApiPublicTokenDto responseBody, Exception exception) throws JsonProcessingException {

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
    @DisplayName("accessToken_正常系")
    public void success() throws Exception {

        mockAccessToken(freeeApiPublicAccessTokenRequestBody, freeeApiPublicTokenDto, null);

        FreeeApiPublicTokenDto responseUser = freeeApiRestTemplate.accessToken(freeeApiPublicAccessTokenRequestBody);

        assertNotNull(responseUser);

        assertEquals(freeeApiPublicTokenDto.getAccessToken(), responseUser.getAccessToken());
        assertEquals(freeeApiPublicTokenDto.getRefreshToken(), responseUser.getRefreshToken());
    }

    @Test
    @DisplayName("accessToken_異常系_リクエストボディnull")
    public void requestBodyNull() throws Exception {

        mockAccessToken(freeeApiPublicAccessTokenRequestBody, freeeApiPublicTokenDto, null);

        FreeeApiPublicTokenDto responseUser = freeeApiRestTemplate.accessToken(null);

        assertNull(responseUser);
    }

    @Test
    @DisplayName("accessToken_異常系_リフレッシュトークンが違う")
    public void refreshTokenErr() throws Exception {

        mockAccessToken(freeeApiPublicAccessTokenRequestBody, freeeApiPublicTokenDto, new Exception(""));

        freeeApiPublicAccessTokenRequestBody.setRefreshToken("afger4");
        AssertionError ex =  assertThrows(AssertionError.class, () -> freeeApiRestTemplate.accessToken(freeeApiPublicAccessTokenRequestBody));
        assertTrue(ex.getMessage().contains("refresh_token"));
    }

}