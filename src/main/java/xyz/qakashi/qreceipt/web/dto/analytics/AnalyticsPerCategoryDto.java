package xyz.qakashi.qreceipt.web.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsPerCategoryDto {
    private Double totalSpendings = 0D;
    private Map<String, Double> spendingsPerCategory = new HashMap<>();
}
