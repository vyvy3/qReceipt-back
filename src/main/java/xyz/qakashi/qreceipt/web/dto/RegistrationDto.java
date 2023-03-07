package xyz.qakashi.qreceipt.web.dto;

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
@ApiModel(description = "Registration model")
public class RegistrationDto {
    @ApiModelProperty("First name")
    protected String firstName;

    @ApiModelProperty("Last name")
    protected String lastName;

    @ApiModelProperty(value = "Email", required = true)
    protected String email;

    @ApiModelProperty(value = "Password", required = true)
    protected String password;
}
