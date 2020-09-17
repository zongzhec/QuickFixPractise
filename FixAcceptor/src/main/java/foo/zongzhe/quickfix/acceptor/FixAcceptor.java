package foo.zongzhe.quickfix.acceptor;

import quickfix.*;

public class FixAcceptor {
    private static ThreadedSocketAcceptor acceptor;
    private static SessionSettings settings;
    private static FixAcceptorApplication application;

    public static ThreadedSocketAcceptor getAcceptor() {
        return acceptor;
    }

    public FixAcceptor() {
        try {
            settings = new SessionSettings("src/main/resources/quickfix.properties");
        } catch (ConfigError configError) {
            System.out.println("Warning: config error!" + configError);
        }

        application = new FixAcceptorApplication();
        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new FileLogFactory(settings);
        MessageFactory messageFactory = new DefaultMessageFactory(); // 不是quickfix.fix44.MessageFactory
        try {
            acceptor = new ThreadedSocketAcceptor(application, storeFactory, settings, logFactory, messageFactory);
        } catch (ConfigError configError) {
            System.out.println("Warning: config error! " + configError);
        }
    }

    private void startServer() {
        try {
            acceptor.start();
        } catch (ConfigError configError) {
            configError.printStackTrace();
        }
    }

    private void stopServer() {
        acceptor.stop();
    }

    public static void main(String[] args) {
        FixAcceptor fixAcceptor = new FixAcceptor();
        fixAcceptor.startServer();

        // 启动一个Session，记得参考你的quickfix.properties设定
        SessionID sessionID = new SessionID("FIX.4.4", "QUICKFIX_ACCEPTOR", "QUICKFIX_INITIATOR1");

        while (true) {
            // 等消息就行了
        }
    }

}
