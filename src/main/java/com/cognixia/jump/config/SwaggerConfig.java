package com.cognixia.jump.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.builders.ResponseMessageBuilder;
//import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * On Postman, do a get request on: http://localhost:8080/v2/api-docs
 * 
 * On Browser, go to URL: http://localhost:8080/swagger-ui.html
 */

/**
 * Configuration for Swagger Documentation for the Restaurant Reviews API.
 * @author Lori White
 * @version v1 (08/29/2020)
 */
//@SuppressWarnings("deprecation")
@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig extends WebMvcConfigurerAdapter {
	/**
	 * Selects all APIs that have the class annotation RestController and includes the API's info.
	 * @author Lori White
	 * @return Docket - the Restaurant Reviews API's Documentation
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.any())
				.build()
				.apiInfo( getApiInfo() )
		/*
		 * .useDefaultResponseMessages(false) .globalResponseMessage(RequestMethod.GET,
		 * newArrayList( new ResponseMessageBuilder() .code(500) .message("500 message")
		 * .responseModel(new ModelRef("Error")) .build(), new ResponseMessageBuilder()
		 * .code(403) .message("Forbidden!") .build()))
		 */;
	}
	/**
	 * Creates the Restaurant Reviews API's base info.
	 * @author Lori White
	 * @return AppInfo - the Restaurant Reviews API's base info 
	 */
	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder()
        .title("Restaurant Reviews API")
        .description("API for a database with Restaurants, Reviews, Users, and Addresses.")
        .version("0.0.1")
        .contact(new Contact("Project Team", "http://localhost:3000/", "http://localhost:8080/"))
        .license("API License")
        .licenseUrl("https://github.com/JumpTeamProject")
        .build();
	}

	@Override
	   public void addResourceHandlers(ResourceHandlerRegistry registry) {
	       registry.addResourceHandler("swagger-ui.html")
	       .addResourceLocations("classpath:/META-INF/resources/");

	       registry.addResourceHandler("/webjars/**")
	       .addResourceLocations("classpath:/META-INF/resources/webjars/");
	   }
}
