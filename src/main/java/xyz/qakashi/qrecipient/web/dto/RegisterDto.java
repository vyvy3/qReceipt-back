package xyz.qakashi.qrecipient.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.*;
import xyz.qakashi.qrecipient.domain.enums.Gender;

import java.time.LocalDate;

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
    private String patronymic;
    private String phone;
    private String country;
    private String username;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birth;
    private Gender gender;
    private String about;
    private String job;
}
