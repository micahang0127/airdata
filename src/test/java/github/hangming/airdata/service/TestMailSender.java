package github.hangming.airdata.service;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class TestMailSender {

	 @Autowired
	 EmailService mailService;

	 
	@Test
	public void test() {
		mailService.sendTestMail(
				"yeori.seo@gmail.com", 
//				"2love0909@naver.com", 
				"테스트 메일 전송", 
				"<b>메일</b>이 잘 가는지 확인해 봅시다");
	}

}
