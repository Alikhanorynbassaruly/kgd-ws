
package kz.nitec.shep.service.sync;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * информация о сообщении ответе
 * 
 * <p>Java class for SyncSendMessageResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SyncSendMessageResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="responseInfo" type="{http://bip.bee.kz/SyncChannel/v10/Types}SyncMessageInfoResponse"/&gt;
 *         &lt;element name="responseData" type="{http://bip.bee.kz/SyncChannel/v10/Types/Response}ResponseData"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SyncSendMessageResponse", namespace = "http://bip.bee.kz/SyncChannel/v10/Types/Response", propOrder = {
    "responseInfo",
    "responseData"
})
public class SyncSendMessageResponse {

    @XmlElement(required = true)
    protected SyncMessageInfoResponse responseInfo;
    @XmlElement(required = true)
    protected ResponseData responseData;

    /**
     * Gets the value of the responseInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SyncMessageInfoResponse }
     *     
     */
    public SyncMessageInfoResponse getResponseInfo() {
        return responseInfo;
    }

    /**
     * Sets the value of the responseInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SyncMessageInfoResponse }
     *     
     */
    public void setResponseInfo(SyncMessageInfoResponse value) {
        this.responseInfo = value;
    }

    /**
     * Gets the value of the responseData property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseData }
     *     
     */
    public ResponseData getResponseData() {
        return responseData;
    }

    /**
     * Sets the value of the responseData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseData }
     *     
     */
    public void setResponseData(ResponseData value) {
        this.responseData = value;
    }

}
