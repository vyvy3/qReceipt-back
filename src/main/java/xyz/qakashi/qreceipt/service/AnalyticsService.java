package xyz.qakashi.qreceipt.service;

import java.util.Map;

public interface AnalyticsService {
    Map<String, Double> getSpendingsPerLast5Months();
}
