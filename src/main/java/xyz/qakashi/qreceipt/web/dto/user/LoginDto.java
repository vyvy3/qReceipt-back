package xyz.qakashi.qreceipt.web.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Login credentials model")
public class LoginDto {
    @ApiModelProperty(value = "Email", required = true)
    private String email;
    @ApiModelProperty(value = "Password", required = true)
    private String password;
}
