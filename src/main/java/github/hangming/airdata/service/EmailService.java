package github.hangming.airdata.service;

import java.util.List;

import org.springframework.stereotype.Service;

import github.hangming.airdata.dto.Pmdata;

@Service
public class EmailService {

	/**
	 * 주어진 관측 데이터를 사용자들에게 이멩릴로 통보합니다.
	 * @param data
	 */
	public void sendNotification ( List<Pmdata> data) {
		
		System.out.println("[메일전송 여기서 합니다 ] 구현 안되었음~");
	}
}
