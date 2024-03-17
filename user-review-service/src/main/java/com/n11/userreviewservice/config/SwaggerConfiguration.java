package com.n11.userreviewservice.config;
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
                        .title(" User Review Services")
                        .version("v1")
                        .description("If we summarize the work of this API. This API contains three controllers where user, address and review operations are performed."));
    }
}