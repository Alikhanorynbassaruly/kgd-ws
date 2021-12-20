package kz.nitec.shep.service.sync;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * Интерфейс для работы с синхронным каналом
 *
 * This class was generated by Apache CXF 3.3.0
 * 2021-06-08T11:25:56.034+06:00
 * Generated source version: 3.3.0
 *
 */
@WebService(targetNamespace = "http://bip.bee.kz/SyncChannel/v10/Interfaces", name = "ISyncChannel")
@XmlSeeAlso({ObjectFactory.class})
public interface ISyncChannel {

    /**
     * Метод отправки сообщения по синхронному каналу
     */
    @WebMethod(operationName = "SendMessage")
    @RequestWrapper(localName = "SendMessage", targetNamespace = "http://bip.bee.kz/SyncChannel/v10/Types", className = "kz.nitec.shep.service.sync.SendMessage")
    @ResponseWrapper(localName = "SendMessageResponse", targetNamespace = "http://bip.bee.kz/SyncChannel/v10/Types", className = "kz.nitec.shep.service.sync.SendMessageResponse")
    @WebResult(name = "response", targetNamespace = "")
    public kz.nitec.shep.service.sync.SyncSendMessageResponse sendMessage(
        @WebParam(name = "request", targetNamespace = "")
        kz.nitec.shep.service.sync.SyncSendMessageRequest request
    ) throws SendMessageSendMessageFaultMsg;
}