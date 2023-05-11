package xyz.qakashi.qreceipt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.qakashi.qreceipt.domain.qReceipt;
import xyz.qakashi.qreceipt.repository.qReceiptRepository;
import xyz.qakashi.qreceipt.service.AnalyticsService;

import java.nio.DoubleBuffer;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {
    private final qReceiptRepository receiptRepository;

    @Override
    public List<Map<String, Double>> getSpendingsPerLastNMonths(int numberOfMonths) {
        ZonedDateTime startDate = ZonedDateTime.now().minusMonths(numberOfMonths).withDayOfMonth(1);
        List<Map<String, String>> queryResult = receiptRepository.getTotalSum(startDate);

        Map<Integer, String> lastNMonths = getLastNMonths(numberOfMonths);
        List<Map<String, Double>> result = new ArrayList<>();

        for (Map.Entry<Integer, String> month : lastNMonths.entrySet()) {
            Map<String, Double> currentMonthSum = new HashMap<>();
            String monthName = month.getValue();
            Double monthTotal = queryResult.stream()
                    .filter(r -> Integer.parseInt(r. get("month")) == month.getKey())
                    .mapToDouble(r -> Double.parseDouble(r.getOrDefault("sum", "0")))
                    .findFirst()
                    .orElse(0D);
            currentMonthSum.put(monthName, monthTotal);
            result.add(currentMonthSum);
        }

        return result;
    }

    public Map<Integer, String> getLastNMonths(int n) {
        ZonedDateTime currentDate = ZonedDateTime.now();
        Map<Integer, String> monthNames = new HashMap<>();
        for (int i = n - 1; i >= 0; i--) {
            YearMonth yearMonth = YearMonth.from(currentDate.minusMonths(i));
            monthNames.put(yearMonth.getMonthValue(), yearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));
        }
        return monthNames;
    }
}
