package m.portfolio.nettychat;

import m.portfolio.nettychat.socket.ClientManager;
import m.portfolio.nettychat.socket.ServerManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class NettyChatApplicationTests {
	@Autowired
	private ServerManager serverManager;
	@Autowired
	private ClientManager clientManager;

	private ExecutorService executorService = Executors.newFixedThreadPool(2);

	@Test
	void contextLoads() throws InterruptedException {
		executorService.submit(() -> {
			try {
				serverManager.start();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		clientManager.start();

		executorService.shutdown();

		serverManager.shutdown();
		clientManager.shutdown();
	}

}
