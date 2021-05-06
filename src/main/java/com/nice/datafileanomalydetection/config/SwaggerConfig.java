package com.nice.datafileanomalydetection.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableAsync
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public UiConfiguration uiConfig () {
        return UiConfigurationBuilder
                .builder()
                .build();

    }

    private ApiInfo metadata () {
        return new ApiInfoBuilder()
                .title("WatchDog - 데이터 파일 이상감지")
                .description("Rest API")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket api () {
        return new Docket(DocumentationType.SWAGGER_2)
                //.groupName("수행")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/anomaly/**"))
                .build()
                .apiInfo(metadata())
                ;
    }
}
