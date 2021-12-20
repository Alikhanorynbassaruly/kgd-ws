package kz.nitec.shep.service.sync;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.3.0
 * 2021-06-08T11:25:56.037+06:00
 * Generated source version: 3.3.0
 *
 */
@WebServiceClient(name = "ISyncChannelHttpService",
                  wsdlLocation = "classpath:shep/SyncChannel/v10/Interfaces/SyncChannelHttp_Service.wsdl",
                  targetNamespace = "http://bip.bee.kz/SyncChannel/v10/Interfaces/Binding2")
public class ISyncChannelHttpService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://bip.bee.kz/SyncChannel/v10/Interfaces/Binding2", "ISyncChannelHttpService");
    public final static QName SyncChannelHttpPort = new QName("http://bip.bee.kz/SyncChannel/v10/Interfaces/Binding2", "SyncChannelHttpPort");
    static {
        URL url = ISyncChannelHttpService.class.getClassLoader().getResource("shep/SyncChannel/v10/Interfaces/SyncChannelHttp_Service.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(ISyncChannelHttpService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "classpath:shep/SyncChannel/v10/Interfaces/SyncChannelHttp_Service.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public ISyncChannelHttpService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ISyncChannelHttpService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ISyncChannelHttpService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public ISyncChannelHttpService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public ISyncChannelHttpService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public ISyncChannelHttpService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns ISyncChannel
     */
    @WebEndpoint(name = "SyncChannelHttpPort")
    public ISyncChannel getSyncChannelHttpPort() {
        return super.getPort(SyncChannelHttpPort, ISyncChannel.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ISyncChannel
     */
    @WebEndpoint(name = "SyncChannelHttpPort")
    public ISyncChannel getSyncChannelHttpPort(WebServiceFeature... features) {
        return super.getPort(SyncChannelHttpPort, ISyncChannel.class, features);
    }

}
