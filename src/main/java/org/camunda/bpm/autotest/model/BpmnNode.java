package org.camunda.bpm.autotest.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "BPMN_Node")
public class BpmnNode {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "node_name")
    private String nodeName;

    @Column(name = "node_id")
    private String nodeId;

    @Column(name = "node_type")
    private String nodeType;

    @OneToMany(mappedBy = "bpmnNode", cascade = CascadeType.ALL)
    private List<BpmnSequence> seq_incoming;

    @OneToMany(mappedBy = "bpmnNode", cascade = CascadeType.ALL)
    private List<BpmnSequence> seq_outgoing;

    @OneToMany(mappedBy = "bpmnNode", cascade = CascadeType.ALL)
    private List<BpmnUserTaskVar> user_task_var;

    @Column(name = "called_element")
    private String calledElement;

    @ManyToOne(fetch = FetchType.LAZY)
    private BpmnProcess bpmnProcess;

    public BpmnNode() {
        super();
    }

    public BpmnNode(String nodeName, String nodeId, String nodeType, List<BpmnSequence> seq_incoming, List<BpmnSequence> seq_outgoing, List<BpmnUserTaskVar> user_task_var, String calledElement, BpmnProcess bpmnProcess) {
        this.nodeName = nodeName;
        this.nodeId = nodeId;
        this.nodeType = nodeType;
        this.seq_incoming = seq_incoming;
        this.seq_outgoing = seq_outgoing;
        this.user_task_var = user_task_var;
        this.calledElement = calledElement;
        this.bpmnProcess = bpmnProcess;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<BpmnSequence> getSeq_incoming() {
        return seq_incoming;
    }

    public void setSeq_incoming(List<BpmnSequence> seq_incoming) {
        this.seq_incoming = seq_incoming;
    }

    public List<BpmnSequence> getSeq_outgoing() {
        return seq_outgoing;
    }

    public void setSeq_outgoing(List<BpmnSequence> seq_outgoing) {
        this.seq_outgoing = seq_outgoing;
    }

    public List<BpmnUserTaskVar> getUser_task_var() {
        return user_task_var;
    }

    public void setUser_task_var(List<BpmnUserTaskVar> user_task_var) {
        this.user_task_var = user_task_var;
    }

    public String getCalledElement() {
        return calledElement;
    }

    public void setCalledElement(String calledElement) {
        this.calledElement = calledElement;
    }

    public BpmnProcess getBpmnProcess() {
        return bpmnProcess;
    }

    public void setBpmnProcess(BpmnProcess bpmnProcess) {
        this.bpmnProcess = bpmnProcess;
    }
}
