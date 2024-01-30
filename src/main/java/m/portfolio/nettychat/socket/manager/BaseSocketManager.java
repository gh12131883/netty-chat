package m.portfolio.nettychat.socket.manager;

import jakarta.annotation.PreDestroy;

public abstract class BaseSocketManager {
//    public abstract void start() throws InterruptedException;

    public void initialize() throws InterruptedException {
        try{
            this.start();
        }finally {
            this.shutdown();
        }
    }

    public abstract void start() throws InterruptedException;

    @PreDestroy
    public abstract void shutdown();
}
