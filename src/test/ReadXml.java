package test;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadXml {
    private static ArrayList<Object> objects = new ArrayList<>();

    public static void main(String[] args) {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException |SAXException e ) {
            throw new RuntimeException(e);
        }

        XMLHandler handler = new XMLHandler();
        try {
            parser.parse(new File("AS_ADDR_OBJ.xml"), handler);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        ArrayList<String> list = new ArrayList<>();
        list.add("1422396");
        list.add("1450759");
        list.add("1449192");
        list.add("1451562");
        System.out.println(getAdres("1900-01-01", list));
        System.out.println(getAdres("2014-12-10", list));
        System.out.println(getAdres("2005-02-24", list));
        System.out.println(getAdresById());
    }
    private static class XMLHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("OBJECT")) {
                String objectId = attributes.getValue("OBJECTID");
                String name = attributes.getValue("NAME");
                String typeName = attributes.getValue("TYPENAME");
                String startDate = attributes.getValue("STARTDATE");
                String endDate = attributes.getValue("ENDDATE");
                String isActual = attributes.getValue("ISACTUAL");
                String isActive = attributes.getValue("ISACTIVE");
                objects.add(new Object(objectId, name, typeName, startDate, endDate, isActual, isActive));
            }
        }
    }

    private static List<String> getAdres(String date, List<String> objectIds) {
        List<String> adres = new ArrayList<>();
        for (Object obj : objects){
            if(obj.getStartDate().equals(date)){
                if(objectIds.contains(obj.getObjectId())){
                    adres.add(obj.getObjectId() + ": " + obj.getTypeName() + " " + obj.getName());
                }
            }
        }
        return adres;
    }

    private static List<String> getAdresById(){
        List<String> getAdreses = new ArrayList<>();

        for (Object obj : objects) {
            if(obj.getTypeName().contains("проезд")){
                getAdreses.add(obj.getTypeName() + " " + obj.getName() + " ");
            }
        }
        return getAdreses;
    }
}
