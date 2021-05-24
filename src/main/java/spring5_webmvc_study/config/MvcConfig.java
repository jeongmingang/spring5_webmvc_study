package spring5_webmvc_study.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import spring5_webmvc_study.interceptor.AuthCheckInterceptor;

@Configuration
@EnableWebMvc	// 스프링 MVC설정 활성화
public class MvcConfig implements WebMvcConfigurer {

	// DispatcherServlet의 매핑경로를 '/'로 주었을 때, jsp/html/css 등을 올바르게 처리하기 위한 설정 추가
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	// jsp를 통해서 컨트롤러의 실행 결과를 보여주기 위한 설정
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/view/", ".jsp");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/main").setViewName("main");
	}
	
	@Bean
	public MessageSource messageSource() {	// 빈의 아이디를 반드시 "messageSource"로 지정해야 함
		ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
		ms.setBasename("message.label");	// message 패키지에 속한 label 프로퍼티 파일로 부터 메시지를 읽어 옴
		ms.setDefaultEncoding("UTF-8");
		return ms;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {	
		registry.addInterceptor(authCheckInterceptor())
			.addPathPatterns("/edit/**")			// /edit/ 로 시작하는 모든 경로에 인터셉터를 적용
			.excludePathPatterns("/edit/help/**");	// 메서드에 지정한 경로 패턴 중 일부를 제외하고 싶다면 excludePathPatterns()메서드를 사용
	}
	
	@Bean
	public AuthCheckInterceptor authCheckInterceptor() {
		return new AuthCheckInterceptor();
	}
}
