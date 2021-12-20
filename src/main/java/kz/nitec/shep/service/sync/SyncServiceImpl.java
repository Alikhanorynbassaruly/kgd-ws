package kz.nitec.shep.service.sync;

import kz.nitec.shep.service.utils.xmlds.XmlDsUtils;
import kz.nitec.shep.userdata.ObjectFactory;
import kz.nitec.shep.userdata.Request;
import kz.nitec.shep.userdata.Response;
import kz.nitec.shep.userdata.Status;
import org.apache.log4j.Logger;

import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import java.io.StringWriter;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@WebService(
        serviceName = "sync-service-endpoint",
        portName = "ISyncChannelPort",
        targetNamespace = "http://bip.bee.kz/SyncChannel/v10/Interfaces",
        endpointInterface = "kz.nitec.shep.service.sync.ISyncChannel")
@XmlSeeAlso(ObjectFactory.class)
@HandlerChain(file = "/handler.xml")
public class SyncServiceImpl implements ISyncChannel {
    Logger log = Logger.getLogger(SyncServiceImpl.class.getName());

    public SyncSendMessageResponse sendMessage(SyncSendMessageRequest request) throws SendMessageSendMessageFaultMsg {
        SyncSendMessageResponse syncSendMessageResponse = new SyncSendMessageResponse();
        SyncMessageInfoResponse infoResponse = new SyncMessageInfoResponse();
        infoResponse.setMessageId(request.getRequestInfo().getMessageId());//UUID.randomUUID().toString());
        infoResponse.setSessionId(request.getRequestInfo().getSessionId());
        infoResponse.setResponseDate(XmlDsUtils.dateToCalendar(new Date()));

        XMLGregorianCalendar MessageDateRaw = request.getRequestInfo().getMessageDate();
        java.util.Date dt = MessageDateRaw.toGregorianCalendar().getTime();
        java.sql.Timestamp MessageDate = new java.sql.Timestamp(dt.getTime());

        String MessageId = request.getRequestInfo().getMessageId();
        String SessionId = request.getRequestInfo().getSessionId();
        String CorrelationId = request.getRequestInfo().getCorrelationId();
        java.sql.Timestamp Created = new java.sql.Timestamp(new Date().getTime());

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setCode("Success");
        statusInfo.setMessage("ОК");
        infoResponse.setStatus(statusInfo);

        ResponseData responseData = new ResponseData();

        ObjectFactory objectFactory = new ObjectFactory();

        Response userResponse = new Response();
        Status status = new Status();
        status.setCode("SUCCESS");
        status.setMessageRu("OK");
        status.setMessageKz("OK");
        userResponse.setStatus(status);
        userResponse.setRequestNumber(UUID.randomUUID().toString());

        //System.out.println("request data size: " + request.getRequestData().getData().getEvents().size());
        List<Request.Events> events = request.getRequestData().getData().getEvents();

        try {
            processData(events, MessageDate, MessageId, SessionId, CorrelationId, Created);
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage());
            statusInfo.setCode("ERROR");
            statusInfo.setMessage("FAILURE");
            infoResponse.setStatus(statusInfo);
            status.setCode("ERROR");
            status.setMessageRu(e.getMessage());
            status.setMessageKz(e.getMessage());
            userResponse.setStatus(status);
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.marshal(objectFactory.createResponse(userResponse), writer);
            String xml = writer.toString();
            responseData.setData(xml);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        syncSendMessageResponse.setResponseData(responseData);
        syncSendMessageResponse.setResponseInfo(infoResponse);

        return syncSendMessageResponse;
    }

    private int processData(List<Request.Events> events,java.sql.Timestamp MessageDate,
                            String MessageId,String SessionId,String CorrelationId
                            ,java.sql.Timestamp Created) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            /*String dbURL = "jdbc:postgresql://localhost:5432/eptsdb";
            String user = "postgres";
            String pass = "123QWEasd!";*/
            String dbURL = "jdbc:postgresql://192.168.225.68:5432/eptsdb";
            String user = "postgres";
            String pass = "1q2w3e4r";
            conn = DriverManager.getConnection(dbURL, user, pass);
            conn.setAutoCommit(false);
            if (conn != null) {
                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            } else {
                throw new Exception("");
            }
            if (events.size()==0) throw new Exception("no data received");
            for (Request.Events event : events){
                String values=""
                        , fields = "";
                //check all necessary fields for null and empty

                if (event.getDocumentType() == null || event.getDocumentType().isEmpty()) throw new Exception("getDocumentType is null or empty");
                if (event.getDepartureCountry() == null || event.getDepartureCountry().isEmpty()) throw new Exception("getDepartureCountry is null or empty");
                if (event.getDestinationCountry() == null || event.getDestinationCountry().isEmpty()) throw new Exception("getDestinationCountry is null or empty");
                if (event.getCustomPoint() == null || event.getCustomPoint().isEmpty()) throw new Exception("getCustomPoint is null or empty");
                if (event.getDocNumber() == null || event.getDocNumber().isEmpty()) throw new Exception("getDocNumber is null or empty");
                if (event.getNetMass() == null || event.getNetMass().isEmpty()) throw new Exception("getNetMass is null or empty");
                if (event.getIssueYear() == null || event.getIssueYear().isEmpty()) throw new Exception("getIssueYear is null or empty");



                //check dependences fields
                if (!(event.getDocumentType().equals("1") || event.getDocumentType().equals("2"))) //1 это ДТ 2 это ТПО
                    throw new Exception("getDocumentType заполнен не по справочнику");

                if (event.getDocumentType().equals("1")){
                    if (event.getDeclarationType() == null || event.getDeclarationType().isEmpty())
                        throw new Exception("if DocumentType().equals(1) getDeclarationType not null and empty");
                    if (event.getCustomProdCode() == null || event.getCustomProdCode().isEmpty())
                        throw new Exception("if DocumentType().equals(1) getCustomProdCode not null and empty");
                    if (event.getReleaseDate() == null)
                        throw new Exception("if DocumentType().equals(1) getReleaseDate not null and empty");
                    if (event.getOriginCountry() == null || event.getOriginCountry().isEmpty())
                        throw new Exception("if DocumentType().equals(1) getOriginCountry not null and empty");
                    if (event.getCustomLimitDescription() == null)
                        throw new Exception("if DocumentType().equals(1) getCustomLimitDescription not null and empty");
                    if (event.getConsignorName() == null || event.getConsignorName().isEmpty())
                        throw new Exception("if DocumentType().equals(1) getConsignorName not null and empty");
                    if (event.getDeclarantIinBin() == null || event.getDeclarantIinBin().isEmpty())
                        throw new Exception("if DocumentType().equals(1) getDeclarantIinBin not null and empty");
                    if (event.getUnitMeasurement() == null || event.getUnitMeasurement().isEmpty())
                        throw new Exception("if DocumentType().equals(1) getUnitMeasurement not null and empty");
                    if (event.getEngNumber() == null || event.getEngNumber().isEmpty())
                        throw new Exception("if DocumentType().equals(1) getEngNumber not null and empty");
                    if (event.getKabinNumber() == null || event.getKabinNumber().isEmpty())
                        throw new Exception("if DocumentType().equals(1) getKabinNumber not null and empty");
                    if (event.getDocName() == null || event.getDocName().isEmpty())
                        throw new Exception("if DocumentType().equals(1) getDocName not null and empty");
                    if (event.getCustomLimitBegin() == null)
                        throw new Exception("if DocumentType().equals(1) getCustomLimitBegin not null and");
                    if (event.getCustomLimitEnd() == null)
                        throw new Exception("if DocumentType().equals(1) getCustomLimitEnd not null and");
                }
                if (event.getDocumentType().equals("2")){
                    if (event.getAddUnitCode() == null || event.getAddUnitCode().isEmpty())
                        throw new Exception("if DocumentType().equals(1) getAddUnitCode not null and empty");
                    if (event.getBrand() == null || event.getBrand().isEmpty())
                        throw new Exception("if DocumentType().equals(1) getBrand not null and empty");
                    if (event.getModel() == null || event.getModel().isEmpty())
                        throw new Exception("if DocumentType().equals(1) getModel not null and empty");
                    if (event.getEngVolume() == null || event.getEngVolume().isEmpty())
                        throw new Exception("if DocumentType().equals(1) getEngVolume not null and empty");
                }

                // Adding nullable fileds if not null
                if (event.getDeclarationType() != null && !event.getDeclarationType().isEmpty()) {
                    fields += ",\"DeclarationType\"";
                    values += ",?";
                }
                if (event.getCustomProdCode() != null && !event.getCustomProdCode().isEmpty()) {
                    fields += ",\"CustomProdCode\"";
                    values += ",?";
                }
                if (event.getReleaseDate() != null) {
                    fields += ",\"ReleaseDate\"";
                    values += ",?";
                }
                if (event.getOriginCountry() != null && !event.getOriginCountry().isEmpty()){
                    fields +=",\"OriginCountry\"";
                    values += ",?";
                }
                if (event.getCustomLimitDescription() != null && !event.getCustomLimitDescription().isEmpty()){
                    fields +=",\"CustomLimitDescription\"";
                    values += ",?";
                }
                if (event.getConsignorName() != null && !event.getConsignorName().isEmpty()){
                    fields +=",\"ConsignorName\"";
                    values += ",?";
                }
                if (event.getDeclarantIinBin() != null && !event.getDeclarantIinBin().isEmpty()) {
                    fields += ",\"DeclarantIinBin\"";
                    values += ",?";
                }
                if (event.getKabinNumber() != null && !event.getKabinNumber().isEmpty()){
                    fields +=",\"KabinNumber\"";
                    values += ",?";
                }
                if (event.getUnitMeasurement() != null && !event.getUnitMeasurement().isEmpty()){
                    fields +=",\"UnitMeasurement\"";
                    values += ",?";
                }
                if (event.getEngNumber() != null && !event.getEngNumber().isEmpty()){
                    fields +=",\"EngNumber\"";
                    values += ",?";
                }
                if (event.getDocName() != null && !event.getDocName().isEmpty()){
                    fields +=",\"DocName\"";
                    values += ",?";
                }
                if (event.getCustomLimitBegin() != null){
                    fields +=",\"CustomLimitBegin\"";
                    values += ",?";
                }
                if (event.getCustomLimitEnd() != null){
                    fields +=",\"CustomLimitEnd\"";
                    values += ",?";
                }
                if (event.getAddUnitCode() != null && !event.getAddUnitCode().isEmpty()){
                    fields +=",\"AddUnitCode\"";
                    values += ",?";
                }
                if (event.getBrand() != null && !event.getBrand().isEmpty()){
                    fields +=",\"Brand\"";
                    values += ",?";
                }
                if (event.getModel() != null && !event.getModel().isEmpty()){
                    fields +=",\"Model\"";
                    values += ",?";
                }
                if (event.getEngVolume() != null && !event.getEngVolume().isEmpty()){
                    fields +=",\"EngVolume\"";
                    values += ",?";
                }



                values = values + ")";
                String SQL = "INSERT INTO \"KgdDatas\"(\"DocumentType\",\"DepartureCountry\",\"DestinationCountry\",\"CustomPoint\",\"DocNumber\",\"CustomPointName\"," +
                        "\"ConsignorIinBin\",\"RecipientName\",\"TnvedCode\",\"NetMass\",\"VinCode\"," +
                        "\"ChassisNumber\",\"KuzovNumber\",\"IssueYear\"," +
                        "\"MessageDate\",\"MessageId\",\"SessionId\",\"CorrelationId\",\"Created\",\"NumberUnitMeasurement\"" +
                        fields+
                        ") "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"+values;
                PreparedStatement pstmt = conn.prepareStatement(SQL);
                int i = 0;
                pstmt.setString(++i, event.getDocumentType());
                pstmt.setString(++i, event.getDepartureCountry());
                pstmt.setString(++i, event.getDestinationCountry());
                pstmt.setString(++i, event.getCustomPoint());
                pstmt.setString(++i, event.getDocNumber());
                pstmt.setString(++i, event.getCustomPointName());
                pstmt.setString(++i, event.getConsignorIinBin());
                pstmt.setString(++i,  event.getRecipientName());
                pstmt.setString(++i, event.getTnvedCode());
                pstmt.setString(++i, event.getNetMass());
                pstmt.setString(++i, event.getVinCode());
                pstmt.setString(++i, event.getChassisNumber());
                pstmt.setString(++i, event.getKuzovNumber());
                pstmt.setString(++i, event.getIssueYear());
                pstmt.setTimestamp(++i, MessageDate);
                pstmt.setString(++i, MessageId);
                pstmt.setString(++i, SessionId);
                pstmt.setString(++i, CorrelationId);
                pstmt.setTimestamp(++i, Created);
                pstmt.setString(++i, event.getNumberUnitMeasurement());
                if (event.getDeclarationType() != null && !event.getDeclarationType().isEmpty())  pstmt.setString(++i, event.getDeclarationType());
                if (event.getCustomProdCode() != null && !event.getCustomProdCode().isEmpty())  pstmt.setString(++i, event.getCustomProdCode());
                if (event.getReleaseDate() != null)    pstmt.setDate(++i, GetSqlDate(event.getReleaseDate()));
                if (event.getOriginCountry() != null && !event.getOriginCountry().isEmpty())  pstmt.setString(++i, event.getOriginCountry());
                if (event.getCustomLimitDescription() != null && !event.getCustomLimitDescription().isEmpty())    pstmt.setString(++i, event.getCustomLimitDescription());
                if (event.getConsignorName() != null && !event.getConsignorName().isEmpty())    pstmt.setString(++i, event.getConsignorName());
                if (event.getDeclarantIinBin() != null && !event.getDeclarantIinBin().isEmpty())    pstmt.setString(++i, event.getDeclarantIinBin());
                if (event.getKabinNumber() != null && !event.getKabinNumber().isEmpty())    pstmt.setString(++i, event.getKabinNumber());
                if (event.getUnitMeasurement() != null && !event.getUnitMeasurement().isEmpty())    pstmt.setString(++i, event.getUnitMeasurement());
                if (event.getEngNumber() != null && !event.getEngNumber().isEmpty())    pstmt.setString(++i, event.getEngNumber());
                if (event.getDocName() != null && !event.getDocName().isEmpty())  pstmt.setString(++i, event.getDocName());
                if (event.getCustomLimitBegin() != null)    pstmt.setDate(++i, GetSqlDate(event.getCustomLimitBegin()));
                if (event.getCustomLimitEnd() != null)    pstmt.setDate(++i, GetSqlDate(event.getCustomLimitEnd()));
                if (event.getAddUnitCode() != null && !event.getAddUnitCode().isEmpty())  pstmt.setString(++i, event.getAddUnitCode());
                if (event.getBrand() != null && !event.getBrand().isEmpty())  pstmt.setString(++i, event.getBrand());
                if (event.getModel() != null && !event.getModel().isEmpty())  pstmt.setString(++i, event.getModel());
                if (event.getEngVolume() != null && !event.getEngVolume().isEmpty())  pstmt.setString(++i, event.getEngVolume());

                pstmt.executeUpdate();
            }
            conn.commit();
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();

            try{
                if(conn!=null)
                    conn.rollback();
            }catch(SQLException se2){
                se2.printStackTrace();
                throw se2;
            }
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
                throw se;
            }
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw ex;
            }
        }

        return 0;
    }

    private java.sql.Date GetSqlDate(XMLGregorianCalendar date){
        java.util.Date dt = date.toGregorianCalendar().getTime();
        return new java.sql.Date(dt.getTime());
    }

    class XMLReaderWithoutNamespace extends StreamReaderDelegate {
        public XMLReaderWithoutNamespace(XMLStreamReader reader) {
            super(reader);
        }
        @Override
        public String getAttributeNamespace(int arg0) {
            return "";
        }
        @Override
        public String getNamespaceURI() {
            return "";
        }
    }


}
