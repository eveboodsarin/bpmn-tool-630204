package org.camunda.bpm.autotest.model;

import java.util.List;

public class NodeCommon {
    private String nodeName;

    private String nodeId;

    private String nodeType;

    private String calledElement;

    private List<String> incoming;

    private List<String> outgoing;

    private List<BpmnUserTaskVar> userTaskVars;

    public NodeCommon() {
    }

    public NodeCommon(String nodeName, String nodeId, String nodeType) {
        this.nodeName = nodeName;
        this.nodeId = nodeId;
        this.nodeType = nodeType;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getCalledElement() {
        return calledElement;
    }

    public void setCalledElement(String calledElement) {
        this.calledElement = calledElement;
    }

    public List<String> getIncoming() {
        return incoming;
    }

    public void setIncoming(List<String> incoming) {
        this.incoming = incoming;
    }

    public List<String> getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(List<String> outgoing) {
        this.outgoing = outgoing;
    }

    public List<BpmnUserTaskVar> getUserTaskVars() {
        return userTaskVars;
    }

    public void setUserTaskVars(List<BpmnUserTaskVar> userTaskVars) {
        this.userTaskVars = userTaskVars;
    }
}
