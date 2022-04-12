package xyz.qakashi.qrecipient.web.dto;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "AuthResponseDto", description = "returns access token")
public class AuthResponseDto {
    private String accessToken;
}
