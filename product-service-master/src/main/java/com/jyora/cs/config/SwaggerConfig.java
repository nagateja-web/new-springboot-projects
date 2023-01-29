package com.jyora.cs.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //students
    @Bean
    public Docket studentsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(studentAPIInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(regex("/.*"))
                .build();
//                        securitySchemes(Arrays.asList(basicAuth()));
    }

    private Predicate<String> studentsPaths() {
        return Predicates.or(
                regex("/api/students.*"),
                regex("/api/courses.*"));
    }
}
