package xyz.qakashi.qreceipt.web.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("Profile view model")
public class ProfileViewDto {
    @ApiModelProperty("Email")
    private String email;
    @ApiModelProperty("Profile picture UUID")
    private UUID picture;
    @ApiModelProperty("First name")
    private String firstname;
    @ApiModelProperty("Last name")
    private String lastname;
    @ApiModelProperty(example = "OTHER")
    private String gender;
    @ApiModelProperty("Date of birth")
    private ZonedDateTime dateOfBirth;
}
