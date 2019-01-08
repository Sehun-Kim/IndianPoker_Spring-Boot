package indianpoker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan({"indianpoker", "support"})
public class IndianPokerApplication {
    public static void main(String[] args) {
        SpringApplication.run(IndianPokerApplication.class, args);
    }
}
