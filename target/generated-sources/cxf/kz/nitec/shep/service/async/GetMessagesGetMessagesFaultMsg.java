
package kz.nitec.shep.service.async;

import javax.xml.ws.WebFault;


/**
 * ошибка
 *
 * This class was generated by Apache CXF 3.3.0
 * 2021-06-08T11:25:55.792+06:00
 * Generated source version: 3.3.0
 */

@WebFault(name = "getMessagesFault1_getMessagesFault", targetNamespace = "http://bip.bee.kz/AsyncChannel/v10/Types")
public class GetMessagesGetMessagesFaultMsg extends Exception {

    private kz.nitec.shep.service.async.ErrorInfo getMessagesFault1GetMessagesFault;

    public GetMessagesGetMessagesFaultMsg() {
        super();
    }

    public GetMessagesGetMessagesFaultMsg(String message) {
        super(message);
    }

    public GetMessagesGetMessagesFaultMsg(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public GetMessagesGetMessagesFaultMsg(String message, kz.nitec.shep.service.async.ErrorInfo getMessagesFault1GetMessagesFault) {
        super(message);
        this.getMessagesFault1GetMessagesFault = getMessagesFault1GetMessagesFault;
    }

    public GetMessagesGetMessagesFaultMsg(String message, kz.nitec.shep.service.async.ErrorInfo getMessagesFault1GetMessagesFault, java.lang.Throwable cause) {
        super(message, cause);
        this.getMessagesFault1GetMessagesFault = getMessagesFault1GetMessagesFault;
    }

    public kz.nitec.shep.service.async.ErrorInfo getFaultInfo() {
        return this.getMessagesFault1GetMessagesFault;
    }
}
