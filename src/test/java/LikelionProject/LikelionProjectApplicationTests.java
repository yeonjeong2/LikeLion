package LikelionProject;

import LikelionProject.Service.AnnounceService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LikelionProjectApplicationTests {

	@Autowired
	private AnnounceService announceService;

	@Test
	@Ignore
	void TestAnnounce() {
		for(int i = 0; i <= 100; i++) {
			String subject = String.format("테스트 데이터:[%03d]", i);
			String content = String.format("%d번째 테스트 데이터", i);
			this.announceService.create(subject, content);
		}
	}
}
