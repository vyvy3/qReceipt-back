package xyz.qakashi.qreceipt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.qakashi.qreceipt.repository.qReceiptRepository;
import xyz.qakashi.qreceipt.service.AnalyticsService;
import xyz.qakashi.qreceipt.util.Pair;
import xyz.qakashi.qreceipt.web.dto.analytics.AnalyticsPerCategoryDto;

import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {
    private final qReceiptRepository receiptRepository;

    @Override
    public List<Map<String, Double>> getMySpendingsPerLastNMonths(int numberOfMonths, String email) {
        ZonedDateTime startDate = ZonedDateTime.now().minusMonths(numberOfMonths).withDayOfMonth(1);
        List<Map<String, String>> queryResult = receiptRepository.getTotalSumByLogin(startDate, email);

        return getMonthsSum(numberOfMonths, queryResult);
    }

    @Override
    public AnalyticsPerCategoryDto getMySpendingsForLastNDaysPerCategory(int numberOfDays, String email) {
        ZonedDateTime startDate = ZonedDateTime.now().minusDays(numberOfDays);
        List<Map<String, String>> spendingsPerCategories = receiptRepository.getTotalSumPerCategoryByLogin(startDate, email);
        Double totalSum = spendingsPerCategories.stream()
                .mapToDouble(map -> Double.parseDouble(map.get("sum")))
                .sum();
        AnalyticsPerCategoryDto dto = new AnalyticsPerCategoryDto();
        Map<String, Double> formatedMap = new HashMap<>();
        spendingsPerCategories.stream()
                .forEach(map -> {
                    formatedMap.put(map.get("category"), Double.parseDouble(map.get("sum")));
                });
        dto.setTotalSpendings(totalSum);
        dto.setSpendingsPerCategory(formatedMap);
        return dto;
    }

    @Override
    public List<Map<String, Double>> getSpendingsPerLastNMonths(int numberOfMonths) {
        ZonedDateTime startDate = ZonedDateTime.now().minusMonths(numberOfMonths).withDayOfMonth(1);
        List<Map<String, String>> queryResult = receiptRepository.getTotalSum(startDate);

        return getMonthsSum(numberOfMonths, queryResult);
    }

    private List<Map<String, Double>> getMonthsSum(int numberOfMonths, List<Map<String, String>> queryResult) {
        List<Pair<Integer, String>> lastNMonths = getLastNMonths(numberOfMonths);
        List<Map<String, Double>> result = new ArrayList<>();

        for (Pair<Integer, String> month : lastNMonths) {
            Map<String, Double> currentMonthSum = new HashMap<>();
            String monthName = month.getValue();
            Double monthTotal = queryResult.stream()
                    .filter(r -> Integer.parseInt(r.get("month")) == month.getKey())
                    .mapToDouble(r -> Double.parseDouble(r.getOrDefault("sum", "0")))
                    .findFirst()
                    .orElse(0D);
            currentMonthSum.put(monthName, monthTotal);
            result.add(currentMonthSum);
        }

        return result;
    }

    public List<Pair<Integer, String>> getLastNMonths(int n) {
        ZonedDateTime currentDate = ZonedDateTime.now();
        List<Pair<Integer, String>> monthNames = new ArrayList<>();
        for (int i = n - 1; i >= 0; i--) {
            YearMonth yearMonth = YearMonth.from(currentDate.minusMonths(i));
            monthNames.add(new Pair<>(
                    yearMonth.getMonthValue(),
                    yearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())
            ));
        }
        return monthNames;
    }
}
