package xyz.qakashi.qreceipt.util;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import xyz.qakashi.qreceipt.web.dto.PageableDto;

@UtilityClass
public class PageableUtils {
    public static PageRequest createPageRequest(PageableDto dto) {
        return PageRequest.of(dto.getPageNumber(), dto.getPageSize(), Sort.by(dto.getDirection(), dto.getSortBy()));
    }
}
