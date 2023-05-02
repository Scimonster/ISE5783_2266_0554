package renderer;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import geometries.*;
import primitives.*;
import scene.Scene;

import javax.xml.parsers.*;
import java.io.*;
import java.util.List;


public class XMLParser {


    private String filename;

    /**
     * give our parser a specific file name to parse
     * @param name
     */
    public XMLParser(String name)
    {
        this.filename=name;
    }

    /**
     * method to convert strings to list of numbers
     * @param string
     * @return a list of 3 Doubles
     */
    private List<Double> stringToDoubles(String string)
    {
        String list[] =string.split(" ");

        return List.of(Double.parseDouble(list[0]),Double.parseDouble(list[1]),Double.parseDouble(list[2]));
    }

    private Geometries parseGeometries(Element cur)
    {
        Geometries geometries=new Geometries();
        NodeList shapes=cur.getChildNodes();

        for (int i=0; i< shapes.getLength(); ++i) {
            Node node = shapes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;
                if (element.getTagName().equals("sphere")){

                } else if (element.getTagName().equals("triangle")) {

                } else if (element.getTagName().equals("plane")) {

                }else if(element.getTagName().equals("polygon"))
                {

                }



            }
        }
    }

    /**
     * method that receives a string and makes a Color
     * @param string
     * @return a Color
     */
    private Color makeColor(String string)
    {
        List<Double> values=stringToDoubles(string);
        return new Color(values.get(0), values.get(1), values.get(2));
    }

    /**
     * method to make a point from a string
     * @param string
     * @return a Point
     */
    private Point makePoint(String string)
    {
        List<Double> values=stringToDoubles(string);
        return new Point(values.get(0), values.get(1), values.get(2));
    }

    public Scene parse() throws IOException, ParserConfigurationException, SAXException, UnsupportedEncodingException {
        Scene scene=null;
        DocumentBuilderFactory dBfactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dBfactory.newDocumentBuilder();

        Document document = builder.parse(new File(this.filename));

        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();

        if(!root.getNodeName().equals("scene"))
            throw new UnsupportedEncodingException("Not the proper file to build a scene");

        scene = new Scene(this.filename);

        scene.setBackground(this.makeColor(root.getAttribute("background-color")));

        NodeList children = root.getChildNodes();

        for (int i=0; i< children.getLength(); ++i)
        {
            Node node = children.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;
                if (element.getTagName().equals("ambient-light"))
                    scene.setAmbientLight(makeColor(element.getAttribute("color")), Double3.ONE);


                if (element.getTagName().equals("geometries")) {


                }

            }
        }






    }


}
