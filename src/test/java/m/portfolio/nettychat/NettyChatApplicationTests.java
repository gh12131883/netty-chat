package m.portfolio.nettychat;

import m.portfolio.nettychat.socket.TestClient;
import m.portfolio.nettychat.socket.TestServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class NettyChatApplicationTests {
	@Autowired
	private TestServer testServer;
	@Autowired
	private TestClient testClient;

	private ExecutorService executorService = Executors.newFixedThreadPool(2);

	@Test
	void contextLoads() throws InterruptedException {
		executorService.submit(() -> {
			try {
				testServer.start();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		testClient.start();

		executorService.shutdown();

		testServer.shutdown();
		testClient.shutdown();
	}

}
