package xyz.qakashi.qreceipt.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "ResponseDto", description = "Dto for all responses")
public class ResponseDto<T> {
    @ApiModelProperty(value = "Is request success", required = true)
    @Builder.Default
    private Boolean success = false;
    @ApiModelProperty(value = "Http Status Id", required = true)
    @Builder.Default
    private Integer status = HttpStatus.OK.value();
    @ApiModelProperty(value = "Message if error occurs", required = false)
    private String errorMessage;
    @ApiModelProperty(value = "Responding data", required = false)
    private T data;
}