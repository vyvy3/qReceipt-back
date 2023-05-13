package xyz.qakashi.qreceipt.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageableDto {

    @ApiModelProperty(notes = "Beginning page", required = true)
    protected int pageNumber = 0;

    @ApiModelProperty(notes = "Number of pages", required = true, example = "10")
    protected int pageSize = 10;

    @ApiModelProperty(notes = "Sort by field", required = false, example = "id")
    protected String sortBy = "id";

    @ApiModelProperty(notes = "Sort direction", required = false)
    protected Sort.Direction direction = Sort.Direction.DESC;
}