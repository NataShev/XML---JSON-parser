import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ParserXML {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, SAXException {
        String id1 = "1";
        String id2 = "2";
        String john = "John";
        String smith = "Smith";
        String usa = "USA";
        String age25 = "25";
        String iNav = "Ivan";
        String petrov = "Petrov";
        String ru = "RU";
        String age23 = "23";
        String gsFileName = "data2.json";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element staff = document.createElement("staff");
        document.appendChild(staff);
        createXML(staff, document, id1, john, smith, usa, age25);
        createXML(staff, document, id2, iNav, petrov, ru, age23);

        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File("data.xml"));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(domSource, streamResult);
        List<Employee> list = parseXML("data.xml");
        String jSon = listToJson(list);
        writeString(jSon, gsFileName);

    }


    public static void createXML(Element element, Document document, String idn, String fName, String lName, String
            cTry, String aGe) {
        Element employee = document.createElement("employee");
        element.appendChild(employee);
        Element id = document.createElement("id");
        id.appendChild(document.createTextNode(idn));
        employee.appendChild(id);
        Element firstName = document.createElement("firstName");
        firstName.appendChild(document.createTextNode(fName));
        employee.appendChild(firstName);
        Element lastName = document.createElement("lastName");
        lastName.appendChild(document.createTextNode(lName));
        employee.appendChild(lastName);
        Element country = document.createElement("country");
        country.appendChild(document.createTextNode(cTry));
        employee.appendChild(country);
        Element age = document.createElement("age");
        age.appendChild(document.createTextNode(aGe));
        employee.appendChild(age);

    }


    public static List<Employee> parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {
        int a = 0;
        String firstName = null;
        String lastName = null;
        String country = null;
        int age = 0;
        List<Employee> employeeList = new ArrayList<>();
        DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder2 = factory1.newDocumentBuilder();
        Document document1 = builder2.parse(new File("data.xml"));
        Node root = document1.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Element element = (Element) node;
            a = nodeListElementInt("id", element);
            firstName = nodeListElementString("firstName", element);
            lastName = nodeListElementString("lastName", element);
            country = nodeListElementString("country", element);
            age = nodeListElementInt("age", element);

            Employee employee = new Employee(a, firstName, lastName, country, age);
            employeeList.add(employee);
        }
            return employeeList;
        }
    public static int nodeListElementInt(String string,Element element){
         int age = 0;
        NodeList nodeList = element.getElementsByTagName(string);
        for (int j = 0; j < nodeList.getLength(); j++) {
            age = Integer.valueOf(nodeList.item(j).getTextContent());
        }
        return age;
    }
    public static String nodeListElementString(String string,Element element){
        String name = null;
        NodeList nodeList = element.getElementsByTagName(string);
        for (int j = 0; j < nodeList.getLength(); j++) {
            name = nodeList.item(j).getTextContent();
        }
        return name;
    }
    public static String listToJson(List<Employee> list) {
        String gsonFile = null;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        return gsonFile = gson.toJson(list, listType);
    }
    public static void writeString(String file, String name) {
        try (FileWriter gsFile = new FileWriter(name)) {
            gsFile.write(file);
            gsFile.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

