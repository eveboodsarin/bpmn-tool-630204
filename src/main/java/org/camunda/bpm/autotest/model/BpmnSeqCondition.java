package org.camunda.bpm.autotest.model;

import javax.persistence.*;

@Entity
@Table(name = "BPMN_Seq_Condition")
public class BpmnSeqCondition {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "xsi_type")
    private String xsiType;

    @Column(name = "condition")
    private String condition;

    public BpmnSeqCondition() {
        super();
    }

    public BpmnSeqCondition(String xsiType, String condition) {
        this.xsiType = xsiType;
        this.condition = condition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
