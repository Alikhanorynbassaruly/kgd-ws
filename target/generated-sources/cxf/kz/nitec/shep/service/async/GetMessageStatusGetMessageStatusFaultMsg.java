
package kz.nitec.shep.service.async;

import javax.xml.ws.WebFault;


/**
 * Ошибка
 *
 * This class was generated by Apache CXF 3.3.0
 * 2021-06-08T11:25:55.828+06:00
 * Generated source version: 3.3.0
 */

@WebFault(name = "getMessageStatusFault1_getMessageStatusFault", targetNamespace = "http://bip.bee.kz/AsyncChannel/v10/Types")
public class GetMessageStatusGetMessageStatusFaultMsg extends Exception {

    private kz.nitec.shep.service.async.ErrorInfo getMessageStatusFault1GetMessageStatusFault;

    public GetMessageStatusGetMessageStatusFaultMsg() {
        super();
    }

    public GetMessageStatusGetMessageStatusFaultMsg(String message) {
        super(message);
    }

    public GetMessageStatusGetMessageStatusFaultMsg(String message, java.lang.Throwable cause) {
        super(message, cause);
    }

    public GetMessageStatusGetMessageStatusFaultMsg(String message, kz.nitec.shep.service.async.ErrorInfo getMessageStatusFault1GetMessageStatusFault) {
        super(message);
        this.getMessageStatusFault1GetMessageStatusFault = getMessageStatusFault1GetMessageStatusFault;
    }

    public GetMessageStatusGetMessageStatusFaultMsg(String message, kz.nitec.shep.service.async.ErrorInfo getMessageStatusFault1GetMessageStatusFault, java.lang.Throwable cause) {
        super(message, cause);
        this.getMessageStatusFault1GetMessageStatusFault = getMessageStatusFault1GetMessageStatusFault;
    }

    public kz.nitec.shep.service.async.ErrorInfo getFaultInfo() {
        return this.getMessageStatusFault1GetMessageStatusFault;
    }
}