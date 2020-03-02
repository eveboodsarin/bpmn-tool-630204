package org.camunda.bpm.autotest.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "BPMN_Process")
public class BpmnProcess {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "process_name")
    private String processName;

    @Column(name = "process_id")
    private String processId;

    @Column(name = "file_name")
    private String fileName;

    @OneToMany(mappedBy = "bpmnProcess", cascade = CascadeType.ALL)
    private List<BpmnNode> bpmn_nodes;

    public BpmnProcess() {
        super();
    }

    public BpmnProcess(String processName, String processId, String fileName, List<BpmnNode> bpmn_nodes) {
        this.processName = processName;
        this.processId = processId;
        this.fileName = fileName;
        this.bpmn_nodes = bpmn_nodes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<BpmnNode> getBpmn_nodes() {
        return bpmn_nodes;
    }

    public void setBpmn_nodes(List<BpmnNode> bpmn_nodes) {
        this.bpmn_nodes = bpmn_nodes;
    }
}

