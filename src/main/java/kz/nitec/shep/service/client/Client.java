package kz.nitec.shep.service.client;

import kz.nitec.shep.service.utils.xmlds.XmlDsUtils;
import kz.nitec.shep.userdata.Request;

import java.util.Date;

public class Client {

    public static void main(String[] args) {

        //ObjectFactory objectFactory = new ObjectFactory();
        Request userRequest = new Request();

        for (int i=0;i<1;i++) {
            Request.Events events = new Request.Events();
            events.setDocumentType("1");
            events.setDeclarationType("asd");
            events.setCustomProdCode("asd");
            events.setReleaseDate(XmlDsUtils.dateToCalendar(new Date()));
            events.setOriginCountry("123");
            events.setDepartureCountry("3");
            events.setDestinationCountry("3");
            events.setCustomPoint("4");
            events.setDocNumber("65");
            events.setCustomLimitDescription("asd");
            events.setCustomPointName("asdasd");
            events.setConsignorName("asdqwe");
            events.setConsignorIinBin("7");
            events.setRecipientName("as");
            events.setDeclarantIinBin("123");
            events.setTnvedCode("65");
            events.setNetMass("asd");
            events.setAddUnitCode("");
            events.setUnitMeasurement("zxc");
            events.setNumberUnitMeasurement("12");
            events.setBrand("");
            //events.setModel("");
            events.setVinCode("12");
            events.setEngVolume("");
            events.setEngNumber("sasd");
            events.setChassisNumber("65152");
            events.setKabinNumber("1234");
            events.setKuzovNumber("12");
            events.setIssueYear("1234");
            events.setDocName("1");
            events.setCustomLimitBegin(XmlDsUtils.dateToCalendar(new Date()));
            events.setCustomLimitEnd(XmlDsUtils.dateToCalendar(new Date()));
            userRequest.getEvents().add(events);
        }

        try {
            SyncServiceClient client = new SyncServiceClient();
            client.sendMessageData(userRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
