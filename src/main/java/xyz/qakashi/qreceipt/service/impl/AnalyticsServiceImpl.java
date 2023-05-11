package xyz.qakashi.qreceipt.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.qakashi.qreceipt.service.AnalyticsService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {
    @Override
    public Map<String, Double> getSpendingsPerLast5Months() {
        return null;
    }
}
