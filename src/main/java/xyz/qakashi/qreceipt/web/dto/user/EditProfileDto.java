package xyz.qakashi.qreceipt.web.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditProfileDto {
    private String firstname;

    private String lastname;

    @ApiModelProperty(example = "OTHER")
    private String gender;

    private ZonedDateTime dateOfBirth;
}
