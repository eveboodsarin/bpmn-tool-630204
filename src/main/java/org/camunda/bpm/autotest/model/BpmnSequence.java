package org.camunda.bpm.autotest.model;

import javax.persistence.*;

@Entity
@Table(name = "BPMN_Sequence")
public class BpmnSequence {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "sequence_flow_id")
    private String sequenceFlowId;

    @Column(name = "sequence_flow_name")
    private String sequenceFlowName;

    @Column(name = "source_ref_id")
    private String sourceRefId;

    @Column(name = "target_ref_id")
    private String targetRefId;

    @OneToOne(cascade = CascadeType.ALL)
    private BpmnSeqCondition seq_condition;

    @ManyToOne(fetch = FetchType.LAZY)
    private BpmnNode bpmnNode;

    public BpmnSequence() {
    }

    public BpmnSequence(String sequenceFlowId, String sequenceFlowName, String sourceRefId, String targetRefId, BpmnSeqCondition seq_condition, BpmnNode bpmnNode) {
        this.sequenceFlowId = sequenceFlowId;
        this.sequenceFlowName = sequenceFlowName;
        this.sourceRefId = sourceRefId;
        this.targetRefId = targetRefId;
        this.seq_condition = seq_condition;
        this.bpmnNode = bpmnNode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSequenceFlowId() {
        return sequenceFlowId;
    }

    public void setSequenceFlowId(String sequenceFlowId) {
        this.sequenceFlowId = sequenceFlowId;
    }

    public String getSequenceFlowName() {
        return sequenceFlowName;
    }

    public void setSequenceFlowName(String sequenceFlowName) {
        this.sequenceFlowName = sequenceFlowName;
    }

    public String getSourceRefId() {
        return sourceRefId;
    }

    public void setSourceRefId(String sourceRefId) {
        this.sourceRefId = sourceRefId;
    }

    public String getTargetRefId() {
        return targetRefId;
    }

    public void setTargetRefId(String targetRefId) {
        this.targetRefId = targetRefId;
    }

    public BpmnSeqCondition getSeq_condition() {
        return seq_condition;
    }

    public void setSeq_condition(BpmnSeqCondition seq_condition) {
        this.seq_condition = seq_condition;
    }

    public BpmnNode getBpmnNode() {
        return bpmnNode;
    }

    public void setBpmnNode(BpmnNode bpmnNode) {
        this.bpmnNode = bpmnNode;
    }
}
