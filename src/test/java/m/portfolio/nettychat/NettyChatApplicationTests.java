package m.portfolio.nettychat;

import m.portfolio.nettychat.socket.TestClientManager;
import m.portfolio.nettychat.socket.TestServerManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class NettyChatApplicationTests {
	@Autowired
	private TestServerManager testServerManager;
	@Autowired
	private TestClientManager testClientManager;

	private ExecutorService executorService = Executors.newFixedThreadPool(2);

	@Test
	void contextLoads() throws InterruptedException {
		executorService.submit(() -> {
			try {
				testServerManager.start();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		testClientManager.start();

		executorService.shutdown();

		testServerManager.shutdown();
		testClientManager.shutdown();
	}

}
