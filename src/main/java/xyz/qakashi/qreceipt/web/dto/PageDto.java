package xyz.qakashi.qreceipt.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageDto<T> {

    private List<T> data;
    private int pageNumber;
    private long total;
    private long size;
    private boolean editable;

    public PageDto(Page<?> entity, List<T> entityDto) {
        this.setData(entityDto);
        this.setPageNumber(entity.getNumber());
        this.setTotal(entity.getTotalElements());
        this.setSize(entity.getTotalPages());
    }
}