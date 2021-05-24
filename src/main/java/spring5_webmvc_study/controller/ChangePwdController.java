package spring5_webmvc_study.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import spring5_webmvc_study.spring.AuthInfo;
import spring5_webmvc_study.spring.ChangePasswordService;
import spring5_webmvc_study.spring.ChangePwdCommand;
import spring5_webmvc_study.spring.WrongIdPasswordException;

@Controller
@RequestMapping("/edit/changePassword")
public class ChangePwdController {
	@Autowired
	private ChangePasswordService changePasswordService;
	
	@GetMapping		
	// changePwdForm.jsp <form:form> 으로 modelAttribute를 설정하지 않기 때문에 "command"를 기본값으로 사용
	public String form(@ModelAttribute("command") ChangePwdCommand pwdCommand) {	
		return "edit/changePwdForm";
	}
	
	@PostMapping
	// changePwdForm.jsp <form:form> 으로 modelAttribute를 설정하지 않기 때문에 "command"를 기본값으로 사용
	public String submit(@ModelAttribute("command") ChangePwdCommand pwdCommand, Errors errors, HttpSession session) {
		new ChangePwdCommandValidator();
		if (errors.hasErrors())
			return "edit/changePwdForm";
		// 서버를 재시작하면 세션이 삭제되기 때문에 아래코드는 Null을 리턴
		AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
		// 세션에 "autoInfo"객체가 존재하지 않으면 로그인 경로로 리다이렉트하도록 함
		if (authInfo == null) {
			return "redirect:/login";
		}
		try {
			changePasswordService.changePassword(authInfo.getEmail(), pwdCommand.getCurrentPassword(), pwdCommand.getNewPassword());
			return "edit/changePwd";
		}catch(WrongIdPasswordException ex) {
			errors.rejectValue("currentPassword", "notMatching");
			return "edit/changePwdForm";
		}
	}
}
