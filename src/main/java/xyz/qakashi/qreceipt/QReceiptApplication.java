package xyz.qakashi.qreceipt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories
public class QReceiptApplication {

    public static void main(String[] args) {
        SpringApplication.run(QReceiptApplication.class, args);
    }

}
