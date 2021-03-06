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
	 * 주어진 관측 데이터를 사용자들에게 이멩릴로 통보합니다.
	 * @param data
	 */
	public void sendNotification ( List<Pmdata> data) {
//		String html = "dkdkdkdkdkdkdkdkdkdk"
		System.out.println("[메일전송 여기서 합니다 ]");
		// 1. 각각의 관측소 데이터에 대해서
		for(int i=0; i< data.size(); i++){
			Pmdata pm = data.get(i);
			double pm10 = pm.getPm10Value();
			List<String> emails =  userDao.getfavEmail( pm );
			
			String html = Util.readTemplate("pm-notif");
			html = html.replace("{station}", pm.getStationName());
			html = html.replace("{time}", pm.getTime());
			html = html.replace("{pm25}", pm.getPm25());
			html = html.replace("{pm10}", pm.getPm10());
			
			String title = "[미세먼지 현황] " + pm.getStationName() ;
			
			System.out.println("메일 보내기 지역확인"+ pm.getStationName());
			
			for (int j = 0 ; j < emails.size() ; j ++ ) {
				String email = emails.get(j);
				// TODO 직접 메일을 보내면 미세먼지 데이터 업데이트 시간에 영향을 미칠 수 있음
				sendTestMail(email, title, html);
				// email > pm 
			}
			
			
		}
		// 2. 주어진 관측소를 관심 지역으로 추가한 사람들을 전부 가져옴
		// 3. 메일을 전송함
		//    3.1. !!!!
		
		
	}
	
	public void sendTestMail ( String receiver , String title, String content ) {
		MimeMessage msg = mailSender.createMimeMessage();
//		msg.setSubject(title); 
		
        try {
        	MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
			helper.setSubject(title);
			helper.setText(content, true); 
			helper.setFrom(new InternetAddress("sangpronb1@gmail.com", "운영자", "utf-8"));
			helper.setTo(new InternetAddress(receiver, "회원님", "utf-8"));
			
			mailSender.send(msg); // 여기서 메일 발송을 서버에게 부탁함 
			
		} catch (MessagingException e) {
			throw new RuntimeException("fail", e);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	
	}
}
