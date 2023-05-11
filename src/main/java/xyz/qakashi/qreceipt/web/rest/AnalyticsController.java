package xyz.qakashi.qreceipt.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import xyz.qakashi.qreceipt.service.AnalyticsService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import static xyz.qakashi.qreceipt.util.Constants.PRIVATE_API_ENDPOINT;
import static xyz.qakashi.qreceipt.util.Constants.PROFILE_PAGE_ANALYTICS_NUMBER_OF_MONTHS;

@RestController
@RequestMapping(PRIVATE_API_ENDPOINT + "/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @GetMapping(value = "/getMyTotalSpendingsForLastMonths")
    public ResponseEntity<List<Map<String, Double>>> get(@ApiIgnore @Autowired Principal principal) {
        return ResponseEntity.ok(analyticsService.getMySpendingsPerLastNMonths(PROFILE_PAGE_ANALYTICS_NUMBER_OF_MONTHS, principal.getName()));
    }
}