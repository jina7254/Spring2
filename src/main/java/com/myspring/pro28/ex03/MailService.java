package com.myspring.pro28.ex03;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private SimpleMailMessage preConfiguredMessage;		// (11~14) mail-context.xml에서 설정한 빈을 자동으로 주입.
	
	@Async
	public void sendMail(String to, String subject, String body) {
		MimeMessage message = mailSender.createMimeMessage();	//MimeMessage타입 객체를 생성
		
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");	//메일을 보내기 위해 MimeMessageHelper객체 생성
			messageHelper.setCc("****@****.**");
			messageHelper.setFrom("***@****.**","메일관리자");	//메일 수신 시 지정한 이름으로 표시되게 . 지정하지 않으면 송신 메일주소 그대로 표시.
			messageHelper.setSubject(subject);
			messageHelper.setTo(to);
			messageHelper.setText(body, true);
			mailSender.send(message);	//제목, 수신처, 내용 설정 후 메일 전송
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Async
	public void sendPreConfiguredMail(String message) {	//mail-context.xml에서 미리 설정한 수신 주소로 메일 내용 전송
		SimpleMailMessage mailMessage = new SimpleMailMessage(preConfiguredMessage);
		mailMessage.setText(message);
		mailSender.send(mailMessage);
	}
}
