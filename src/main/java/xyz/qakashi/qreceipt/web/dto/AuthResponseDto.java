package xyz.qakashi.qreceipt.web.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@ApiModel(value = "AuthResponseDto", description = "returns access token")
@AllArgsConstructor
public class AuthResponseDto {
    private String accessToken;
}
