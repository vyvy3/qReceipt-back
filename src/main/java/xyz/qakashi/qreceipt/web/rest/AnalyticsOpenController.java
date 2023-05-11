package xyz.qakashi.qreceipt.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.qakashi.qreceipt.service.AnalyticsService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static xyz.qakashi.qreceipt.util.Constants.PROFILE_PAGE_ANALYTICS_NUMBER_OF_MONTHS;
import static xyz.qakashi.qreceipt.util.Constants.PUBLIC_API_ENDPOINT;

@RestController
@RequestMapping(PUBLIC_API_ENDPOINT + "/analytics")
@RequiredArgsConstructor
//TODO: DELETE ME
public class AnalyticsOpenController {
    private final AnalyticsService analyticsService;

    @GetMapping(value = "/getTotalSpendingsForLastFiveMonths")
    public ResponseEntity<List<Map<String, Double>>> get() {

        return ResponseEntity.ok(analyticsService.getSpendingsPerLastNMonths(PROFILE_PAGE_ANALYTICS_NUMBER_OF_MONTHS));
    }
}
