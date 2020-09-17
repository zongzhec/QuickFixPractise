package foo.zongzhe.quickfix.initiator;

import quickfix.*;
import quickfix.field.*;
import quickfix.fix44.ExecutionReport;
import quickfix.fix44.MarketDataRequest;
import quickfix.fix44.MessageCracker;
import quickfix.fix44.NewOrderSingle;

import java.time.LocalDateTime;

public class FixInitiatorApplication extends MessageCracker implements Application {

    // 以下是Application的固定七件套
    @Override
    public void onCreate(SessionID sessionId) {
        System.out.println("onCreate is called");
    }

    @Override
    public void onLogon(SessionID sessionId) {
        System.out.println("onLogon is called");
    }

    @Override
    public void onLogout(SessionID sessionId) {
        System.out.println("onLogout is called");
    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
        System.out.println("toAdmin is called");
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        System.out.println("fromAdmin is called");
    }

    @Override
    public void toApp(Message message, SessionID sessionId) throws DoNotSend {
        System.out.println("toApp is called: " + message);
    }

    @Override
    public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        System.out.println("fromApp is called");
    }

    // 以下是你可以自定义的消息接收器，来自MessageCracker
    @Override
    public void onMessage(ExecutionReport message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        System.out.println("Received ExecutionReport: " + message + ", sessionID: " + sessionID);
        // 收都收了，解析一下
        System.out.println(String.format("clOrderID: %s, symbol: %s, side: %s",
                message.getClOrdID().getValue(),
                message.getSymbol().getValue(),
                message.getSide().getValue()));
    }

    // 以下是发消息的功能

    /**
     * 订阅行情消息
     *
     * @param sessionID
     * @throws SessionNotFound
     */
    public void sendMarketDataRequest(SessionID sessionID) throws SessionNotFound {
        // 具体set哪些字段，参考你的FIX44.modified.xml
        MarketDataRequest req = new MarketDataRequest();
        req.set(new MDReqID("mockedMDReqID"));
        req.set(new SubscriptionRequestType('1'));

        // 重复组的设置
        MarketDataRequest.NoRelatedSym symGroup1 = new MarketDataRequest.NoRelatedSym();
        symGroup1.set(new Symbol("mockedSymbol1"));
        req.addGroup(symGroup1);

        MarketDataRequest.NoRelatedSym symGroup2 = new MarketDataRequest.NoRelatedSym();
        symGroup2.set(new Symbol("mockedSymbol2"));
        req.addGroup(symGroup2);

        System.out.println("Sending MarketDataRequest");
        Session.sendToTarget(req, sessionID);
    }

    /**
     * 下单
     * @param sessionID
     * @throws SessionNotFound
     */
    public void sendNewOrderRequest(SessionID sessionID) throws SessionNotFound {
        NewOrderSingle order = new NewOrderSingle();
        LocalDateTime date = LocalDateTime.now();
        order.set(new ClOrdID("mockedClOrdID"));
        order.set(new Account("mockedAccount"));
        order.set(new HandlInst('1'));
        order.set(new OrderQty(45.00));
        order.set(new Price(25.88));
        order.set(new Symbol("mockedSymbol"));
        order.set(new Side(Side.BUY)); // 对于枚举型对象也可以这么设置
        order.set(new OrdType(OrdType.LIMIT));
        Session.sendToTarget(order, sessionID);
    }
}
