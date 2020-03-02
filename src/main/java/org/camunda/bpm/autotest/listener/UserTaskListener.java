package org.camunda.bpm.autotest.listener;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class UserTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        provideVariables(delegateTask.getTaskDefinitionKey(), delegateTask);
        delegateTask.complete();
    }

    private void provideVariables(String taskId, DelegateTask delegateTask) {
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
                    setVariableByType(variableNode, delegateTask, variableNode.getAttribute("type"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setVariableByType(Element variableNode, DelegateTask delegateTask, String type) {
        switch (type) {
            case "string" :
                delegateTask.setVariable(variableNode.getNodeName(), variableNode.getTextContent());
                break;
            case "boolean" :
                delegateTask.setVariable(variableNode.getNodeName(), new Boolean(variableNode.getTextContent()));
                break;
            case "long" :
                delegateTask.setVariable(variableNode.getNodeName(), new Long(variableNode.getTextContent()));
                break;
        }

    }
}
