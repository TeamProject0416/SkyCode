package teamproject.skycode.news.notion;

import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class JacksonConfig {
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
