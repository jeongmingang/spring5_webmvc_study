package spring5_webmvc_study.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import spring5_webmvc_study.spring.DuplicateMemberException;
import spring5_webmvc_study.spring.ErrorResponse;
import spring5_webmvc_study.spring.Member;
import spring5_webmvc_study.spring.MemberDao;
import spring5_webmvc_study.spring.MemberNotFoundException;
import spring5_webmvc_study.spring.MemberRegisterService;
import spring5_webmvc_study.spring.RegisterRequest;

@RestController
public class RestMemberController {
	// @RestController 애노테이션을 붙인 경우 스프링MVC는 요청 매핑 애노테이션을 붙인 메서드가 리턴한 객체를 알맞은 형식으로 변환해서 응답 데이터
	// 로 전송한다. 이때 클래스 패스에 Jackson 이 존재하면 JSON 형식의 문자열로 변환해서 응답한다.
	
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private MemberRegisterService registerService;
	
	@GetMapping("/api/members")
	public List<Member> members() {
		return memberDao.selectAll();
	}
	
	@GetMapping("/api/members/{id}")
	public Member member(@PathVariable Long id, HttpServletResponse response) throws IOException {
		Member member = memberDao.selectById(id);
		if (member == null) {
			throw new MemberNotFoundException();
		} 
		return member;
	}
	
	@PostMapping("/api/members")
	public ResponseEntity<Object> newMember(@RequestBody RegisterRequest regReq, Errors errors, HttpServletResponse response) throws IOException {
		try {
			new RegisterRequestValidator().validate(regReq, errors);
			if (errors.hasErrors()) {
//				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
//				return ResponseEntity.badRequest().build();
				String errorcode = errors.getAllErrors()
						.stream()
						.map(error -> error.getCodes()[0])
						.collect(Collectors.joining(","));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(new ErrorResponse("errorCodes = " + errorcode));
			}
			Long newMemberId = registerService.regist(regReq);
//				response.setHeader("Location", "/api/members/" + newMemberId);
//				response.setStatus(HttpServletResponse.SC_CREATED);
			URI uri = URI.create("/api/mambers/" + newMemberId);
			return ResponseEntity.created(uri).build();
		}catch (DuplicateMemberException e) {
//			response.sendError(HttpServletResponse.SC_CONFLICT);
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
