
package kz.nitec.shep.service.async;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MessageState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MessageState"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ON_DELIVERY"/&gt;
 *     &lt;enumeration value="DELIVERED"/&gt;
 *     &lt;enumeration value="NOT_DELIVERED"/&gt;
 *     &lt;enumeration value="DELIVERY_STOPPED"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "MessageState", namespace = "http://bip.bee.kz/common/v10/Types")
@XmlEnum
public enum MessageState {

    ON_DELIVERY,
    DELIVERED,
    NOT_DELIVERED,
    DELIVERY_STOPPED;

    public String value() {
        return name();
    }

    public static MessageState fromValue(String v) {
        return valueOf(v);
    }

}
