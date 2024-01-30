package m.portfolio.nettychat.socket;

import jakarta.annotation.PreDestroy;

public abstract class BaseSocket {
//    public abstract void start() throws InterruptedException;

    public void start() throws InterruptedException {
        try{
            this.setup();
        }finally {
            this.shutdown();
        }
    }

    public abstract void setup() throws InterruptedException;

    @PreDestroy
    public abstract void shutdown();
}
