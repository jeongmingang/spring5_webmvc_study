package spring5_webmvc_study.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import spring5_webmvc_study.spring.Member;
import spring5_webmvc_study.spring.MemberDao;

@RestController
public class RestMemberController {
	// @RestController 애노테이션을 붙인 경우 스프링MVC는 요청 매핑 애노테이션을 붙인 메서드가 리턴한 객체를 알맞은 형식으로 변환해서 응답 데이터
	// 로 전송한다. 이때 클래스 패스에 Jackson 이 존재하면 JSON 형식의 문자열로 변환해서 응답한다.
	
	@Autowired
	private MemberDao memberDao;
	
	@GetMapping("/api/members")
	public List<Member> members() {
		return memberDao.selectAll();
	}
	
	@GetMapping("/api/members/{id}")
	public Member member(@PathVariable Long id, HttpServletResponse response) throws IOException {
		Member member = memberDao.selectById(id);
		if (member == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		return member;
	}

}
