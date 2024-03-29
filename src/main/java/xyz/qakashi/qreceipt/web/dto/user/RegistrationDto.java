package xyz.qakashi.qreceipt.web.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.qakashi.qreceipt.domain.Gender;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Registration model")
public class RegistrationDto {
    protected String firstname;

    protected String lastname;

    protected String email;

    protected String password;

    @ApiModelProperty(example = "OTHER")
    protected String gender;

    protected ZonedDateTime dateOfBirth;
}
