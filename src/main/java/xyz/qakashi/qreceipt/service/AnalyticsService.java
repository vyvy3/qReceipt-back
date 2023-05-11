package xyz.qakashi.qreceipt.service;

import java.util.List;
import java.util.Map;

public interface AnalyticsService {
    List<Map<String, Double>> getSpendingsPerLastNMonths(int numberOfMonths);

    List<Map<String, Double>> getMySpendingsPerLastNMonths(int numberOfMonths, String email);

}
