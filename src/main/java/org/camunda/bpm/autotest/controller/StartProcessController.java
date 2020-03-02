package org.camunda.bpm.autotest.controller;

import org.camunda.bpm.autotest.model.BpmnNode;
import org.camunda.bpm.autotest.model.BpmnSequence;
import org.camunda.bpm.autotest.repositories.BpmnNodeRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
public class StartProcessController {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private BpmnNodeRepository bpmnNodeRepository;

    @RequestMapping("/start")
    public String index() throws IOException {
        //createXML("PATH_GEN");
        runtimeService.startProcessInstanceByKey("Process_1");
        return "Process started!";
    }

    @RequestMapping("/input-data")
    public String inputData() throws IOException {
        BpmnSequence incoming = new BpmnSequence();
        incoming.setSequenceFlowId("a");
        incoming.setSequenceFlowName("b");
        incoming.setSourceRefId("c");
        incoming.setTargetRefId("d");
        List<BpmnSequence> inList = new ArrayList<>();
        inList.add(incoming);

        BpmnSequence outgoing = new BpmnSequence();
        outgoing.setSequenceFlowId("a");
        outgoing.setSequenceFlowName("b");
        outgoing.setSourceRefId("c");
        outgoing.setTargetRefId("d");
        List<BpmnSequence> outList = new ArrayList<>();
        outList.add(outgoing);

        BpmnNode bpmnNode = new BpmnNode();
        bpmnNode.setNodeId("a");
        bpmnNode.setNodeName("b");
        bpmnNode.setNodeType("c");
        bpmnNode.setSeq_incoming(inList);
        bpmnNode.setSeq_outgoing(outList);

        outgoing.setBpmnNode(bpmnNode);
        incoming.setBpmnNode(bpmnNode);
        BpmnNode savedBpmnNode = bpmnNodeRepository.save(bpmnNode);

        return "ok";
    }

    @RequestMapping("/compare")
    public String compare() throws IOException {
        ArrayList<LinkedList> pathAll = new ArrayList<>();
        LinkedList<String> pathTestcase = new LinkedList<>();
        try {
            File fXmlFile = new File("src/main/resources/PATH_ALL.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList pathList = doc.getElementsByTagName("path");
            for (int index = 0; index < pathList.getLength(); index++) {
                Node nodeItem = pathList.item(index);
                NodeList nodeList = nodeItem.getChildNodes();
                LinkedList<String> path = new LinkedList<>();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node nodeItem1 = nodeList.item(i);
                    if (nodeItem1.getNodeType() == Node.ELEMENT_NODE) {
                        Element nodeElem = (Element) nodeItem1;
                        path.add(nodeElem.getNodeName());
                    }
                }
                pathAll.add(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File fXmlFile = new File("src/main/resources/PATH_GEN.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            Node containerNode = doc.getElementsByTagName("path").item(0);
            NodeList pathList = containerNode.getChildNodes();
            for (int index = 0; index < pathList.getLength(); index++) {
                Node nodeItem = pathList.item(index);
                if (nodeItem.getNodeType() == Node.ELEMENT_NODE) {
                    Element nodeElem = (Element) nodeItem;
                    pathTestcase.add(nodeElem.getNodeName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String outputStr = "";
        for (int index = 0; index < pathAll.size(); index++) {
            LinkedList<String> path = pathAll.get(index);
            Boolean result = path.equals(pathTestcase);
            System.out.println("path id ".concat(new Integer(index + 1).toString() + " --> ").concat(result.toString()));
            outputStr = outputStr.concat("path id ".concat(new Integer(index + 1).toString() + " --> ").concat(result.toString()).concat(" _ "));
        }

        return "Compare Result : ".concat(outputStr);
    }

    private void createXML(String fileName) {
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("path");
            document.appendChild(root);

            // create the xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("src/main/resources/".concat(fileName).concat(".xml")));

            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    private void readXML() {
        try {

            File fXmlFile = new File("src/main/resources/TC_01.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("employee");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println("employee id : " + eElement.getAttribute("id"));
                    System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
                    System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
                    System.out.println("email : " + eElement.getElementsByTagName("email").item(0).getTextContent());
                    System.out.println("department : " + eElement.getElementsByTagName("department").item(0).getTextContent());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
