package org.camunda.bpm.autotest.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

@CrossOrigin(maxAge = 3600)
@RestController
public class ReadDmnController {

    @RequestMapping("/read-dmn")
    public HashMap<String, Object> readDmn() {
        HashMap<String, Object> map = new HashMap<>();

        try {
            File fXmlFile = new File("src/main/resources/salary_range.dmn");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList decisionList = doc.getElementsByTagName("decision");
            for (int index = 0; index < decisionList.getLength(); index++) {
                Node decisionItem = decisionList.item(index);
                NamedNodeMap decisionNodeAttrList = decisionItem.getAttributes();
                HashMap<String, Object> decisionMap = new HashMap<>();
                decisionMap.put("id", decisionNodeAttrList.getNamedItem("id").getNodeValue());
                decisionMap.put("name", decisionNodeAttrList.getNamedItem("name").getNodeValue());


                //Loop for decisionTable
                NodeList decisionTableList = decisionItem.getChildNodes();
                for (int index1 = 0; index1 < decisionTableList.getLength(); index1++) {
                    Node decisionTableItem = decisionTableList.item(index1);
                    HashMap<String, Object> decisionTableMap = new HashMap<>();
                    //check when loop choose ELEMENT_NODE only (sometime it not element)
                    if (decisionTableItem.getNodeType() == Node.ELEMENT_NODE) {
                        switch (decisionTableItem.getNodeName()) {
                            case "decisionTable":
                                NamedNodeMap decisionTableNodeAttrList = decisionTableItem.getAttributes();
                                decisionTableMap.put("id", decisionTableNodeAttrList.getNamedItem("id").getNodeValue());
                                decisionMap.put("decisionTable", decisionTableMap);
                                break;
                        }
                    }
                    //Loop for childs of decisionTable
                    NodeList childDecisionTableList = decisionTableItem.getChildNodes();
                    ArrayList<HashMap> inputArray = new ArrayList();
                    ArrayList<HashMap> outputsArray = new ArrayList();
                    ArrayList<HashMap> rulesArray = new ArrayList();
                    for (int index2 = 0; index2 < childDecisionTableList.getLength(); index2++) {
                        Node childDecisionTableItem = childDecisionTableList.item(index2);
                        //check when loop choose ELEMENT_NODE only (sometime it not element)
                        if (childDecisionTableItem.getNodeType() == Node.ELEMENT_NODE) {
                            switch (childDecisionTableItem.getNodeName()) {
                                case "input":
                                    NamedNodeMap inputNodeAttrList = childDecisionTableItem.getAttributes();
                                    HashMap<String, Object> inputMap = new HashMap<>();
                                    inputMap.put("id", inputNodeAttrList.getNamedItem("id").getNodeValue());
                                    NodeList childInputList = childDecisionTableItem.getChildNodes();
                                    for (int index3 = 0; index3 < childInputList.getLength(); index3++) {
                                        Node childInputItem = childInputList.item(index3);
                                        //check when loop choose ELEMENT_NODE only (sometime it not element)
                                        if (childInputItem.getNodeType() == Node.ELEMENT_NODE) {
                                            switch (childInputItem.getNodeName()) {
                                                case "inputExpression":
                                                    NamedNodeMap inputExpressionNodeAttrList = childInputItem.getAttributes();
                                                    HashMap<String, Object> inputExpressionMap = new HashMap<>();
                                                    inputExpressionMap.put("id", inputExpressionNodeAttrList.getNamedItem("id").getNodeValue());
                                                    inputExpressionMap.put("typeRef", inputExpressionNodeAttrList.getNamedItem("typeRef").getNodeValue());
                                                    inputMap.put("inputExpression", inputExpressionMap);
                                                    NodeList childInputExpressionList = childInputItem.getChildNodes();
                                                    for (int index4 = 0; index4 < childInputExpressionList.getLength(); index4++) {
                                                        Node childInputExpressionItem = childInputExpressionList.item(index4);
                                                        //check when loop choose ELEMENT_NODE only (sometime it not element)
                                                        if (childInputExpressionItem.getNodeType() == Node.ELEMENT_NODE) {
                                                            switch (childInputExpressionItem.getNodeName()) {
                                                                case "text":
                                                                    inputExpressionMap.put("text", childInputExpressionItem.getTextContent());
                                                                    break;
                                                            }
                                                        }
                                                    }
                                            }
                                        }
                                    }
                                    inputArray.add(inputMap);
                                    break;
                                case "output":
                                    NamedNodeMap outputNodeAttrList = childDecisionTableItem.getAttributes();
                                    HashMap<String, Object> outputMap = new HashMap<>();
                                    outputMap.put("id", outputNodeAttrList.getNamedItem("id").getNodeValue());
                                    outputMap.put("name", outputNodeAttrList.getNamedItem("name").getNodeValue());
                                    outputMap.put("typeRef", outputNodeAttrList.getNamedItem("typeRef").getNodeValue());
                                    outputsArray.add(outputMap);
                                    break;
                                case "rule":
                                    NamedNodeMap ruleNodeAttrList = childDecisionTableItem.getAttributes();
                                    HashMap<String, Object> ruleMap = new HashMap<>();
                                    ruleMap.put("id", ruleNodeAttrList.getNamedItem("id").getNodeValue());
                                    NodeList childRuleList = childDecisionTableItem.getChildNodes();
                                    for (int index3 = 0; index3 < childRuleList.getLength(); index3++) {
                                        Node childRuleItem = childRuleList.item(index3);
                                        //check when loop choose ELEMENT_NODE only (sometime it not element)
                                        if (childRuleItem.getNodeType() == Node.ELEMENT_NODE) {
                                            switch (childRuleItem.getNodeName()) {
                                                case "inputEntry":
                                                    NamedNodeMap inputEntryNodeAttrList = childRuleItem.getAttributes();
                                                    HashMap<String, Object> inputEntryMap = new HashMap<>();
                                                    inputEntryMap.put("id", inputEntryNodeAttrList.getNamedItem("id").getNodeValue());
                                                    ruleMap.put("inputEntry", inputEntryMap);
                                                    NodeList childInputEntryList = childRuleItem.getChildNodes();
                                                    for (int index4 = 0; index4 < childInputEntryList.getLength(); index4++) {
                                                        Node childInputEntryItem = childInputEntryList.item(index4);
                                                        //check when loop choose ELEMENT_NODE only (sometime it not element)
                                                        if (childInputEntryItem.getNodeType() == Node.ELEMENT_NODE) {
                                                            switch (childInputEntryItem.getNodeName()) {
                                                                case "text":
                                                                    inputEntryMap.put("text", childInputEntryItem.getTextContent());
                                                                    break;
                                                            }
                                                        }
                                                    }
                                            }
                                        }
                                    }
                                    rulesArray.add(ruleMap);
                                    break;
                            }
                        }
                    }

                    decisionTableMap.put("inputs", inputArray);
                    decisionTableMap.put("outputs", outputsArray);
                    decisionTableMap.put("rules", rulesArray);
                }
                map.put("decision", decisionMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
