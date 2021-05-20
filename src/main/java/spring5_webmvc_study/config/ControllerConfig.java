package spring5_webmvc_study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import spring5_webmvc_study.controller.RegisterController;

@Configuration
@ComponentScan(basePackages = {"spring5_webmvc_study.controller"})
public class ControllerConfig {
	
	@Bean
	public RegisterController registerController() {
		return new RegisterController();
	}
}
