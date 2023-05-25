package xyz.qakashi.qreceipt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import xyz.qakashi.qreceipt.util.Utils;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
@EnableTransactionManagement
public class AuditingConfiguration {

//    @Bean
//    public AuditorAware<String> auditorProvider() {
//        return () -> {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication != null && authentication.isAuthenticated()) {
//                return Optional.of(authentication.getName());
//            }
//            return Optional.of(SYSTEM_ACCOUNT);
//        };
//    }

    @Bean
    public DateTimeProvider auditingDateTimeProvider() {
        return () -> Optional.of(Utils.currentTime());
    }
}
