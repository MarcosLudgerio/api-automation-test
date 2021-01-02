package br.edu.ufcg.virtus.courseautomation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.edu.ufcg.virtus.courseautomation"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo());
    }
    public ApiInfo metaInfo(){
        ApiInfo appinfo = new ApiInfo(
                "Curso Testes Automatizados API - CTAA",
                "API Rest para aprender testes automatizados utilizando Postman",
                "1.0.0",
                "Terms of Services",
                new Contact("Marcos Ludgério", "https://github.com/MarcosLudgerio", "raimundo.marcos@dcx.ufpb.br"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<VendorExtension>()
        );
        return appinfo;
    }

}
