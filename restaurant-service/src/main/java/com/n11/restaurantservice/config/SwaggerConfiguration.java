package com.n11.restaurantservice.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author BeytullahBilek
 */
@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(" Restaurant Services")
                        .version("v1")
                        .description("What this api does is to register a restaurant, bring all restaurants, bring the desired restaurant according to its id, delete the restaurant whose id is given, update the restaurant whose id is given, make the restaurant whose id is given inactive, and in addition, it recommends restaurants according to the user id."));
    }
}