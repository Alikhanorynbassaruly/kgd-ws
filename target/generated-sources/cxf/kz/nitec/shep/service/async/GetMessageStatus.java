
package kz.nitec.shep.service.async;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="request" type="{http://bip.bee.kz/AsyncChannel/v10/ITypes}AsyncGetMessageStatusRequest"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "request"
})
@XmlRootElement(name = "getMessageStatus", namespace = "http://bip.bee.kz/AsyncChannel/v10/Types")
public class GetMessageStatus {

    @XmlElement(required = true, nillable = true)
    protected AsyncGetMessageStatusRequest request;

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link AsyncGetMessageStatusRequest }
     *     
     */
    public AsyncGetMessageStatusRequest getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link AsyncGetMessageStatusRequest }
     *     
     */
    public void setRequest(AsyncGetMessageStatusRequest value) {
        this.request = value;
    }

}
