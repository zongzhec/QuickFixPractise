package foo.zongzhe.quickfix.initiator;

import quickfix.*;
import quickfix.field.*;
import quickfix.fix44.MarketDataRequest;
import quickfix.fix44.NewOrderSingle;

import java.time.LocalDateTime;

public class FixInitiator {

    private static SocketInitiator initiator;
    private static SessionSettings settings;
    private static FixInitiatorApplication application;

    public static SocketInitiator getInitiator() {
        return initiator;
    }

    public FixInitiator() {
        try {
            settings = new SessionSettings("src/main/resources/quickfix.properties");
        } catch (ConfigError configError) {
            System.out.println("Warning: config error!" + configError);
        }

        application = new FixInitiatorApplication();
        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new FileLogFactory(settings);
        MessageFactory messageFactory = new DefaultMessageFactory(); // 不是quickfix.fix44.MessageFactory
        try {
            initiator = new SocketInitiator(application, storeFactory, settings, logFactory, messageFactory);
        } catch (ConfigError configError) {
            System.out.println("Warning: config error! " + configError);
        }
    }

    private void startServer() {
        try {
            initiator.start();
        } catch (ConfigError configError) {
            configError.printStackTrace();
        }
    }

    private void stopServer() {
        initiator.stop();
    }

    public static void main(String[] args) {
        FixInitiator fixInitiator = new FixInitiator();
        fixInitiator.startServer();

        // 启动一个Session，记得参考你的quickfix.properties设定
        SessionID sessionID = new SessionID("FIX.4.4", "QUICKFIX_INITIATOR1", "QUICKFIX_ACCEPTOR");

        // 开始发点消息
        try {
            application.sendMarketDataRequest(sessionID);
            Thread.sleep(5000);
            application.sendNewOrderRequest(sessionID);
            Thread.sleep(5000);
        } catch (SessionNotFound | InterruptedException exception) {
            exception.printStackTrace();
        }
    }


}











