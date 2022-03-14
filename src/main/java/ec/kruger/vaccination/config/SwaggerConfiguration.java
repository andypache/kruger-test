package ec.kruger.vaccination.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @author andres.pacheco
 *
 * Configuration swagger documentation
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    /**
     * Create context documentation
     *
     * @return Docket
     */
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ec.kruger.vaccination.web.rest").or
                        (RequestHandlerSelectors.basePackage("org.springframework.security.oauth2.provider.endpoint")))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    /**
     * Create api information
     *
     * @return Api information
     */
    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Kruger test vaccination management Service API",
                "Management vaccination of employees",
                "1.0",
                "https://localhost/terms",
                new Contact("Kruger", "https://localhost", "andypache@gmail.com"),
                "LICENSE",
                "LICENSE URL",
                Collections.emptyList()
        );
    }
}