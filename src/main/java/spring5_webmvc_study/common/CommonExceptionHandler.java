package spring5_webmvc_study.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("spring5_webmvc_study")
public class CommonExceptionHandler {
	// 여러 컨트롤러에서 동일하게 처리할 예외가 발생할때 사용 (공통 예외 처리)
	@ExceptionHandler(RuntimeException.class)
	public String handleRuntimeException() {
		return "error/commonException";
	}
}
