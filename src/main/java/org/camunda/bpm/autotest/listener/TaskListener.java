package org.camunda.bpm.autotest.listener;

import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class TaskListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        System.out.println(delegateExecution.getCurrentActivityId());
        DmnDecisionResult decisionResult = (DmnDecisionResult) delegateExecution.getVariable("decisionResult");
//        System.out.println(decisionResult.getResultList());
//        Boolean member = (Boolean) decisionResult.getSingleResult().get("member");
        try {
            File fXmlFile = new File("src/main/resources/PATH_GEN.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document path = dBuilder.parse(fXmlFile);
            path.getDocumentElement().normalize();
            Node rootNode = path.getElementsByTagName("path").item(0);

            Element item = path.createElement(delegateExecution.getCurrentActivityId());
            item.setAttribute("name",delegateExecution.getCurrentActivityName());
            rootNode.appendChild(item);

            // create the xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(path);
            StreamResult streamResult = new StreamResult(fXmlFile);

            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
