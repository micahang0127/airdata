package github.hangming.airdata.service;

import java.util.List;

import org.springframework.stereotype.Service;

import github.hangming.airdata.dto.Pmdata;

@Service
public class EmailService {

	/**
	 * �־��� ���� �����͸� ����ڵ鿡�� �̸渱�� �뺸�մϴ�.
	 * @param data
	 */
	public void sendNotification ( List<Pmdata> data) {
		
		System.out.println("[�������� ���⼭ �մϴ� ] ���� �ȵǾ���~");
	}
}
