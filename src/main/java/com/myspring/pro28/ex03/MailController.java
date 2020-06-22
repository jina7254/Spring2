package com.myspring.pro28.ex03;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@EnableAsync	//메서드를 호출할 경우 비동기로 동작하게 하는 기능
public class MailController {

	@Autowired
	private MailService mailService;
	
	@RequestMapping(value="/sendMail.do", method=RequestMethod.GET)
	public void sendSimpleMail(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
//		mailService.sendMail("****@****.**", "테스트 메일", "안녕하세요, 테스트 메일입니다.");
//		mailService.sendPreConfiguredMail("테스트 메일입니다.");	//mail-context.xml에 설정한 메일 주소로 내용을 전송
		
		StringBuffer sb = new StringBuffer();
		sb.append("<html><body>");
		sb.append("<meta http-equiv='Content-Type' content='text/html; charset=euc-kr'>");
		sb.append("<h1>"+"제품소개"+"</h1><br>");
		sb.append("신간 도서를 소개합니다.<br><br>");
		sb.append("<a href='http://www.kyobobook.co.kr/product/detailViewKor.laf?ejkGb=KOR&mallGb=KOR&barcode=9788956746425&orderClick=LAG&Kc=#N'>");
		sb.append("<img src='http://image.kyobobook.co.kr/images/book/xlarge/425/x9788956746425.jpg'/></a><br>");
		sb.append("</a>");
		sb.append("<a href='http://www.kyobobook.co.kr/product/detailViewKor.laf?ejkGb=KOR&mallGb=KOR&barcode=9788956746425&orderClick=LAG&Kc=#N'>상품보기</a>");
		sb.append("</body></html>");	//문자열로 HTML태그 작성 후 sb에 저장
		
		String str = sb.toString();	//문자열로 변환
		mailService.sendMail("****@***.**", "신상품을 소개합니다", str);
		
		out.print("메일을 보냈습니다!!");
	}
}
