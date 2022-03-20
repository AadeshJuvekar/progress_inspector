package edu.idol.mca.piapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket productApi() {
		
		//Configure Swagger and return Docker instance
		return new Docket(DocumentationType.SWAGGER_2)		
				.select().apis(RequestHandlerSelectors.basePackage("com.capgemini.piapi.web"))
				.paths(PathSelectors.regex("/api.*"))				
				.build();
	}
}
