package xyz.qakashi.qrecipient.web.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "LoginDto", description = "User login data")
public class LoginDto {
    private String username;
    private String password;
}
