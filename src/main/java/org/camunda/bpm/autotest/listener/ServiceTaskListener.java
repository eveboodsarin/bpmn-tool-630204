package org.camunda.bpm.autotest.listener;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ServiceTaskListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        System.out.println(delegateExecution.getCurrentActivityId());
        provideVariables(delegateExecution.getCurrentActivityId(), delegateExecution);
    }

    private void provideVariables(String taskId, DelegateExecution delegateExecution) {
        try {
            File fXmlFile = new File("src/main/resources/TC_01.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("testcase");
            Element eElement = (Element) nList.item(0);
            NodeList variableList = eElement.getElementsByTagName(taskId).item(0).getChildNodes();
            for (int index = 0; index < variableList.getLength(); index++) {
                Node nNode = variableList.item(index);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element variableNode = (Element) nNode;
                    setVariableByType(variableNode, delegateExecution, variableNode.getAttribute("type"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    private void setVariableByType(Element variableNode, DelegateExecution delegateExecution, String type) {
        switch (type) {
            case "string":
                delegateExecution.setVariable(variableNode.getNodeName(), variableNode.getTextContent());
                break;
            case "boolean":
                delegateExecution.setVariable(variableNode.getNodeName(), new Boolean(variableNode.getTextContent()));
                break;
            case "long":
                delegateExecution.setVariable(variableNode.getNodeName(), new Long(variableNode.getTextContent()));
                break;
        }

    }
}
