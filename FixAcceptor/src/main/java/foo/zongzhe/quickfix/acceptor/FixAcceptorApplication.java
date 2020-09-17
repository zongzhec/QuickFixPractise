package foo.zongzhe.quickfix.acceptor;

import quickfix.*;
import quickfix.field.ExecID;
import quickfix.field.Symbol;
import quickfix.fix44.ExecutionReport;
import quickfix.fix44.MarketDataRequest;
import quickfix.fix44.MessageCracker;
import quickfix.fix44.NewOrderSingle;

import java.util.List;

public class FixAcceptorApplication extends MessageCracker implements Application {

    /// 以下是Application的固定七件套
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
        System.out.println("toApp is called");
    }

    @Override
    public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        System.out.println("fromApp is called: " + message);
        crack(message, sessionId);
    }

    // 以下是你可以自定义的消息接收器，来自MessageCracker
    @Override
    public void onMessage(MarketDataRequest message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        System.out.println("Received MarketDataRequest: " + message + ", sessionID: " + sessionID);
        // 收都收了，解析一下
        System.out.println("MDReqID: " + message.getMDReqID().getValue());

        // 解析重复组
        MarketDataRequest.NoRelatedSym symGroup = new MarketDataRequest.NoRelatedSym();
        List<Group> groupList = message.getGroups(symGroup.getFieldTag());
        System.out.println("Group size: " + groupList.size());

        // Group Index 从 1 开始
        for (int groupIndex = 1; groupIndex <= groupList.size(); groupIndex++) {
            message.getGroup(groupIndex, symGroup);
            Symbol symbol = new Symbol();
            symGroup.get(symbol);
            System.out.println("Symbol in group" + groupIndex + ": " + symbol.getValue());
        }
    }

    @Override
    public void onMessage(NewOrderSingle message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        System.out.println("Received NewOrderSingle: " + message + ", sessionID: " + sessionID);
        // 收都收了，解析一下
        System.out.println(String.format("clOrderID: %s, symbol: %s, side: %s",
                message.getClOrdID().getValue(),
                message.getSymbol().getValue(),
                message.getSide().getValue()));

        // 返还一个订单回复
        ExecutionReport executionReport = new ExecutionReport();
        executionReport.set(message.getClOrdID());
        executionReport.set(new ExecID("mockedExecID"));
        executionReport.set(message.getSide());
        executionReport.set(message.getSymbol());
        try {
            Session.sendToTarget(executionReport, sessionID);
        } catch (SessionNotFound sessionNotFound) {
            sessionNotFound.printStackTrace();
        }
    }


}
