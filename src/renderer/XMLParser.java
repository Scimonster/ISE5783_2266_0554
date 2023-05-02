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

    /**
     * parse a geometry tag and initiate a geometries object
     * @param cur
     * @return a geometries object
     */
    private Geometries parseGeometries(Element cur) throws UnsupportedOperationException {
        //Initialize an empty geometries
        Geometries geometries=new Geometries();
        NodeList shapes=cur.getChildNodes();

        //parse the current element
        for (int i=0; i< shapes.getLength(); ++i) {
            Node node = shapes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                //depending on the tagname, init different geometries, put into geometries collection
                Element element = (Element) node;
                if (element.getTagName().equals("sphere"))
                {
                    geometries.add(new Sphere(
                            makePoint(element.getAttribute("center")),
                            Double.parseDouble(element.getAttribute("radius"))
                    ));
                }
                else if (element.getTagName().equals("triangle"))
                {
                    geometries.add(new Triangle(
                            makePoint(element.getAttribute("p0")),
                            makePoint(element.getAttribute("p1")),
                            makePoint(element.getAttribute("p2"))
                    ));

                }
                else if (element.getTagName().equals("plane"))
                {
                    geometries.add(new Plane(
                            makePoint(element.getAttribute("p0")),
                            makePoint(element.getAttribute("p1")),
                            makePoint(element.getAttribute("p2"))
                    ));

                }
                else if(element.getTagName().equals("polygon"))
                {

                }
                else if(element.getTagName().equals("tube"))
                {

                }
                else if(element.getTagName().equals("cylinder"))
                {

                }
                else if (element.getTagName().equals("geometries"))
                {
                    geometries.add(parseGeometries(element));
                }
                else
                {
                    throw new UnsupportedOperationException("Unexpected tag: " + element.getTagName());
                }
            }
        }

        return geometries;
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

    /**
     * method that parses the file stored in file name
     * @return a Scene object
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws UnsupportedOperationException
     */
    public Scene parse() throws IOException, ParserConfigurationException, SAXException, UnsupportedOperationException {
        Scene scene=null;
        //get a xml parsing object
        DocumentBuilderFactory dBfactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dBfactory.newDocumentBuilder();

        Document document = builder.parse(new File(this.filename));

        document.getDocumentElement().normalize();

        Element root = document.getDocumentElement();

        //if the root is not what we expect, throw an exception
        if(!root.getNodeName().equals("scene"))
            throw new UnsupportedOperationException("Not the proper file to build a scene");

        //make a new scene with the name of the file
        scene = new Scene(this.filename);

        //set background color
        scene.setBackground(this.makeColor(root.getAttribute("background-color")));

        //iterate through the children nodes, init proper fields of scene
        NodeList children = root.getChildNodes();
        for (int i=0; i< children.getLength(); ++i)
        {
            Node node = children.item(i);
            //we only care about element nodes
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                //handle ambient-light tag
                if (element.getTagName().equals("ambient-light")) {
                    scene.setAmbientLight(makeColor(element.getAttribute("color")), Double3.ONE);
                }
                //handle geometries tag
                else if (element.getTagName().equals("geometries")) {
                    //call helper function
                    scene.addGeometries(parseGeometries(element));
                }
                else {
                    throw new UnsupportedOperationException("Unexpected tag: " + element.getTagName());
                }
            }
        }

        return scene;
    }

}
