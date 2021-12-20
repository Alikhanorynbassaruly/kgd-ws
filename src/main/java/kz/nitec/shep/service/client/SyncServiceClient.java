package kz.nitec.shep.service.client;

import kz.nitec.shep.service.handlers.MessageHandler;
import kz.nitec.shep.service.sync.*;
import kz.nitec.shep.service.utils.xmlds.XmlDsUtils;
import kz.nitec.shep.userdata.Request;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SyncServiceClient {
    private static final String SERVICE_ENDPOINT = "http://127.0.0.1:8008/shepSyncChannel";
    private ISyncChannel syncChannel;

    public SyncServiceClient() {
        ISyncChannelHttpService syncChannelService = new ISyncChannelHttpService();
        syncChannel = syncChannelService.getSyncChannelHttpPort();
        BindingProvider bp = (BindingProvider) syncChannel;
        Handler handler = new MessageHandler();
        List<Handler> handlers = new ArrayList<>();
        handlers.add(handler);
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, SERVICE_ENDPOINT);
        bp.getBinding().setHandlerChain(handlers);
    }

    public SyncSendMessageResponse sendMessageData(Request xml) {
        SyncSendMessageRequest request = new SyncSendMessageRequest();

        SyncMessageInfo syncMessageInfo = new SyncMessageInfo();
        syncMessageInfo.setMessageDate(XmlDsUtils.dateToCalendar(new Date()));
        syncMessageInfo.setServiceId("GetRecycleVehicle");
        syncMessageInfo.setMessageId(UUID.randomUUID().toString());
        SenderInfo senderInfo = new SenderInfo();
        senderInfo.setSenderId("epts");
        senderInfo.setPassword("Dam3oha23dk!");
        syncMessageInfo.setSender(senderInfo);
        RequestData data = new RequestData();
        data.setData(xml);

        request.setRequestInfo(syncMessageInfo);
        request.setRequestData(data);
        try {
            SyncSendMessageResponse wsResponse = syncChannel.sendMessage(request);
            System.out.println("Sent");
            return wsResponse;
        } catch (SendMessageSendMessageFaultMsg error) {
            error.printStackTrace();
            System.out.println("SendMessageSendMessageFaultMsg: " + error.getMessage());
        }

        return null;
    }



}
