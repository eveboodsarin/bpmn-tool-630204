package org.camunda.bpm.autotest.controller;

import org.camunda.bpm.autotest.model.*;
import org.camunda.bpm.autotest.repositories.BpmnProcessRepository;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AnalysisVariablesController {

    private BpmnProcessRepository bpmnProcessRepository;

    private Resource resourceFile;

    private ArrayList<BpmnSequence> bpmnSequenceArrayList;

    private ArrayList<SequenceCommon> sequenceCommonArrayList;

    private ArrayList<NodeCommon> nodeCommonArrayList;

    public AnalysisVariablesController(BpmnProcessRepository bpmnProcessRepository, Resource resourceFile) {
        this.bpmnProcessRepository = bpmnProcessRepository;
        this.resourceFile = resourceFile;

        bpmnSequenceArrayList = new ArrayList<>();
        sequenceCommonArrayList = new ArrayList<>();
        nodeCommonArrayList = new ArrayList<>();
    }

    public void Analysis() {
        try {
            File fXmlFile = resourceFile.getFile();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            Node processNode = doc.getElementsByTagName("bpmn:process").item(0);

            Element eElement = (Element) processNode;
            String processId = eElement.getAttribute("id");
            String processName = eElement.getAttribute("name");

            NodeList nList = processNode.getChildNodes();
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element nodeElement = (Element) nNode;
                    switch (nNode.getNodeName()) {
                        case "bpmn:sequenceFlow":
                            addSequenceCommon(nodeElement);
                            break;
                        default:
                            addCommonNode(nodeElement);
                            break;
                    }
                }
            }
            addToDatabase(processId, processName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addSequenceCommon(Element nodeElement) {
        SequenceCommon sequenceCommon = new SequenceCommon(nodeElement.getAttribute("id"),
                nodeElement.getAttribute("name"),
                nodeElement.getAttribute("sourceRef"),
                nodeElement.getAttribute("targetRef"));
        NodeList nList = nodeElement.getChildNodes();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeName().equals("bpmn:conditionExpression")) {
                Element element = (Element) nNode;
                sequenceCommon.setXsiType(element.getAttribute("xsi:type"));
                sequenceCommon.setCondition(element.getTextContent());
            }
        }

        sequenceCommonArrayList.add(sequenceCommon);
    }

    private void addCommonNode(Element nodeElement) {
        NodeCommon nodeCommon = new NodeCommon(nodeElement.getAttribute("name"),
                nodeElement.getAttribute("id"),
                nodeElement.getNodeName());
        NodeList nList = nodeElement.getChildNodes();
        List<String> incomingList = new ArrayList<>();
        List<String> outgoingList = new ArrayList<>();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeName().equals("bpmn:incoming")) {
                Element element = (Element) nNode;
                incomingList.add(element.getTextContent());
            } else if (nNode.getNodeName().equals("bpmn:outgoing")) {
                Element element = (Element) nNode;
                outgoingList.add(element.getTextContent());
            }
        }
        nodeCommon.setOutgoing(outgoingList);
        if (nodeElement.getNodeName().equals("bpmn:callActivity")) {
            nodeCommon.setCalledElement(nodeElement.getAttribute("calledElement"));
        }
        if (nodeElement.getNodeName().equals("bpmn:userTask")) {
            List<BpmnUserTaskVar> bpmnUserTaskVarList = new ArrayList<>();
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeName().equals("bpmn:extensionElements")) {
                    NodeList nodeList = nNode.getChildNodes();
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Node item = nodeList.item(i);
                        if (item.getNodeName().equals("camunda:formData")) {
                            NodeList formDataList = item.getChildNodes();
                            for (int j = 0; j < formDataList.getLength(); j++) {
                                Node formField = formDataList.item(j);
                                if (formField.getNodeName().equals("camunda:formField")) {
                                    Element element = (Element) formField;
                                    BpmnUserTaskVar userTaskVar = new BpmnUserTaskVar();
                                    userTaskVar.setFieldId(element.getAttribute("id"));
                                    userTaskVar.setFieldLabel(element.getAttribute("label"));
                                    userTaskVar.setFieldType(element.getAttribute("type"));
                                    bpmnUserTaskVarList.add(userTaskVar);
                                }
                            }
                        }
                    }
                }
            }
            nodeCommon.setUserTaskVars(bpmnUserTaskVarList);
        }
        nodeCommonArrayList.add(nodeCommon);
    }

    private void addToDatabase(String processId, String processName) {
        List<BpmnNode> bpmnNodes = new ArrayList<>();
        BpmnProcess bpmnProcess = new BpmnProcess();
        bpmnProcess.setProcessId(processId);
        bpmnProcess.setProcessName(processName);
        bpmnProcess.setFileName(resourceFile.getFilename());

        for (NodeCommon nodeCommon : nodeCommonArrayList) {
            BpmnNode bpmnNode = new BpmnNode();
            bpmnNode.setNodeId(nodeCommon.getNodeId());
            bpmnNode.setNodeName(nodeCommon.getNodeName());
            bpmnNode.setNodeType(nodeCommon.getNodeType());
            bpmnNode.setCalledElement(nodeCommon.getCalledElement());
            if (nodeCommon.getIncoming() != null) {
                bpmnNode.setSeq_incoming(getBpmnSequence(nodeCommon.getIncoming(), bpmnNode));
            }
            if (nodeCommon.getOutgoing() != null) {
                bpmnNode.setSeq_outgoing(getBpmnSequence(nodeCommon.getOutgoing(), bpmnNode));
            }
            if (nodeCommon.getUserTaskVars() != null) {
                addUserTaskVar(nodeCommon.getUserTaskVars(), bpmnNode);
            }
            bpmnNode.setBpmnProcess(bpmnProcess);
            bpmnNodes.add(bpmnNode);
        }

        bpmnProcess.setBpmn_nodes(bpmnNodes);
        bpmnProcessRepository.save(bpmnProcess);
    }

    private List<BpmnSequence> getBpmnSequence(List<String> sequenceList, BpmnNode bpmnNode) {
        List<BpmnSequence> inList = new ArrayList<>();
        for (SequenceCommon sequenceCommon : sequenceCommonArrayList) {
            BpmnSequence bpmnSequence = new BpmnSequence();
            for (String seqId : sequenceList) {
                if (sequenceCommon.getSequenceFlowId().equals(seqId)) {
                    bpmnSequence.setSequenceFlowId(sequenceCommon.getSequenceFlowId());
                    bpmnSequence.setSequenceFlowName(sequenceCommon.getSequenceFlowName());
                    bpmnSequence.setSourceRefId(sequenceCommon.getSourceRefId());
                    bpmnSequence.setTargetRefId(sequenceCommon.getTargetRefId());

                    if (sequenceCommon.getCondition() != null && sequenceCommon.getXsiType() != null) {
                        BpmnSeqCondition seqCondition = new BpmnSeqCondition();
                        seqCondition.setCondition(sequenceCommon.getCondition());
                        seqCondition.setXsiType(sequenceCommon.getXsiType());
                        bpmnSequence.setSeq_condition(seqCondition);
                    }
                    bpmnSequence.setBpmnNode(bpmnNode);
                    inList.add(bpmnSequence);
                }
            }
        }
        return inList;
    }

    private void addUserTaskVar(List<BpmnUserTaskVar> bpmnUserTaskVars, BpmnNode bpmnNode) {
        for (BpmnUserTaskVar userTaskVar : bpmnUserTaskVars) {
            userTaskVar.setBpmnNode(bpmnNode);
        }
        bpmnNode.setUser_task_var(bpmnUserTaskVars);
    }
}
