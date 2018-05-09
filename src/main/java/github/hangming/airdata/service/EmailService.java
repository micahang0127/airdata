package github.hangming.airdata.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import github.hangming.airdata.dto.Pmdata;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	/**
	 * �־��� ���� �����͸� ����ڵ鿡�� �̸渱�� �뺸�մϴ�.
	 * @param data
	 */
	public void sendNotification ( List<Pmdata> data) {
//		String html = "dkdkdkdkdkdkdkdkdkdk"
		System.out.println("[�������� ���⼭ �մϴ� ] ���� �ȵǾ���~");
	}
	
	public void sendTestMail ( String receiver , String title, String content ) {
		MimeMessage msg = mailSender.createMimeMessage();
//		msg.setSubject(title); 
		
		
        try {
        	MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
			helper.setSubject(title);
			helper.setText(content, true); 
			helper.setFrom(new InternetAddress("no.rep.for.javatuition@gmail.com", "DDDD", "utf-8"));
			helper.setTo(new InternetAddress(receiver, "Hello", "utf-8"));
			
			mailSender.send(msg); // ���⼭ ���� �߼��� �������� ��Ź�� 
			
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 

		
	}
}
