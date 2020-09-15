package foo.zongzhe.quickfix.accepter;

import quickfix.*;

public class FixAccepter {
    private static SocketInitiator initiator;
    private static SessionSettings settings;
    private static FixAccepterApplication application;

    public static SocketInitiator getInitiator() {
        return initiator;
    }

    public FixAccepter() {
        try {
            settings = new SessionSettings("src/main/resources/quickfix.properties");
        } catch (ConfigError configError) {
            System.out.println("Warning: config error!" + configError);
        }

        application = new FixAccepterApplication();
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
        FixAccepter fixAccepter = new FixAccepter();
        fixAccepter.startServer();

        // 启动一个Session，记得参考你的quickfix.properties设定
        SessionID sessionID = new SessionID("FIX.4.4", "QUICKFIX_ACCEPTER", "QUICKFIX_INITIATOR1");

        while (true) {
            // 等消息就行了
        }
    }

}
