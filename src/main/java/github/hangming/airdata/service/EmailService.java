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

import github.hangming.airdata.dao.IUserDao;
import github.hangming.airdata.dto.Pmdata;
import github.hangming.airdata.util.Util;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired IUserDao userDao;
	
	
	/**
	 * �־��� ���� �����͸� ����ڵ鿡�� �̸渱�� �뺸�մϴ�.
	 * @param data
	 */
	public void sendNotification ( List<Pmdata> data) {
//		String html = "dkdkdkdkdkdkdkdkdkdk"
		System.out.println("[�������� ���⼭ �մϴ� ] ���� �ȵǾ���~");
		// 1. ������ ������ �����Ϳ� ���ؼ�
		for(int i=0; i< data.size(); i++){
			Pmdata pm = data.get(i);
			double pm10 = pm.getPm10Value();
			List<String> emails =  userDao.getfavEmail( pm );
			
			String html = Util.readTemplate("pm-notif");
			html = html.replace("{time}", pm.getTime());
			html = html.replace("{pm25}", pm.getPm25());
			html = html.replace("{pm10}", pm.getPm10());
			
			String title = "[�̼����� ��Ȳ] " + pm.getStationName() ;
			
			for (int j = 0 ; j < emails.size() ; j ++ ) {
				String email = emails.get(j);
				sendTestMail(email, title, html);
				// email > pm 
			}
			
			
		}
		// 2. �־��� �����Ҹ� ���� �������� �߰��� ������� ���� ������
		// 3. ������ ������
		//    3.1. !!!!
		
		
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
