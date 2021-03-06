package spring5_webmvc_study.controller;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import spring5_webmvc_study.spring.Member;
import spring5_webmvc_study.spring.MemberDao;
import spring5_webmvc_study.spring.MemberNotFoundException;

@Controller
public class MemberDetailController {
	@Autowired
	private MemberDao memberDao;
	
	@GetMapping("/members/{id}")
	public ModelAndView detail(@PathVariable("id") Long memId) {
		Member member = memberDao.selectById(memId);
		if (member == null) {
			throw new MemberNotFoundException();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("member", member);
		mav.setViewName("member/memberDetail");
		return mav;
	}
	
	// 컨트롤러 예외 처리
	// @ExceptionHandler 애노테이션을 적용하면 해당 컨트롤러에서 발생한 익셉션만 처리
	@ExceptionHandler (TypeMismatchException.class)
	public String handleTypeMismatchException() {
		return "member/invalidId";
	}
	
	@ExceptionHandler (MemberNotFoundException.class)
	public String handleMemberNotFoundException() {
		return "member/noMember";
	}
}
