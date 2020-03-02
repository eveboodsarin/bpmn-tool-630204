package org.camunda.bpm.autotest.model;

import javax.persistence.*;

@Entity
@Table(name = "BPMN_User_Task_Var")
public class BpmnUserTaskVar {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private BpmnNode bpmnNode;

    @Column(name = "field_label")
    private String fieldLabel;

    @Column(name = "field_id")
    private String fieldId;

    @Column(name = "field_type")
    private String fieldType;

    public BpmnUserTaskVar() {
        super();
    }

    public BpmnUserTaskVar(BpmnNode bpmnNode, String fieldLabel, String fieldId, String fieldType) {
        this.bpmnNode = bpmnNode;
        this.fieldLabel = fieldLabel;
        this.fieldId = fieldId;
        this.fieldType = fieldType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BpmnNode getBpmnNode() {
        return bpmnNode;
    }

    public void setBpmnNode(BpmnNode bpmnNode) {
        this.bpmnNode = bpmnNode;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
