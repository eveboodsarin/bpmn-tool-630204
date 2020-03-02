package org.camunda.bpm.autotest.model;

public class SequenceCommon {
    private String sequenceFlowId;

    private String sequenceFlowName;

    private String sourceRefId;

    private String targetRefId;

    private String xsiType;

    private String condition;

    public SequenceCommon() {
    }

    public SequenceCommon(String sequenceFlowId, String sequenceFlowName, String sourceRefId, String targetRefId) {
        this.sequenceFlowId = sequenceFlowId;
        this.sequenceFlowName = sequenceFlowName;
        this.sourceRefId = sourceRefId;
        this.targetRefId = targetRefId;
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

    public String getXsiType() {
        return xsiType;
    }

    public void setXsiType(String xsiType) {
        this.xsiType = xsiType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
