package xyz.qakashi.qrecipient.web.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;
import xyz.qakashi.qrecipient.domain.enums.Gender;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RegisterDto", description = "Registration data")
public class RegisterDto {
    private String password;
    private String firstname;
    private String lastname;
    private String username;
    private Gender gender;
}
