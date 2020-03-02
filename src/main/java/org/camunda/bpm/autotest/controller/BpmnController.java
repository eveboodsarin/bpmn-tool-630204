package org.camunda.bpm.autotest.controller;

import org.camunda.bpm.autotest.model.BpmnNode;
import org.camunda.bpm.autotest.model.BpmnProcess;
import org.camunda.bpm.autotest.model.BpmnSequence;
import org.camunda.bpm.autotest.model.BpmnUserTaskVar;
import org.camunda.bpm.autotest.repositories.BpmnNodeRepository;
import org.camunda.bpm.autotest.repositories.BpmnProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
public class BpmnController {
    @Autowired
    private BpmnNodeRepository bpmnNodeRepository;

    @Autowired
    private BpmnProcessRepository bpmnProcessRepository;

    @RequestMapping("/all-process")
    public List<BpmnProcess> getAllProcess() {
        List<BpmnProcess> bpmnProcesses = bpmnProcessRepository.findAll();
        for (BpmnProcess process : bpmnProcesses) {
            process.setBpmn_nodes(null);
        }
        return bpmnProcesses;
    }

    @RequestMapping("/all-node-by-process-id/{id}")
    public List<BpmnNode> getAllNodeByProcessId(@PathVariable("id") long id) {
        List<BpmnNode> bpmnNodes = bpmnNodeRepository.findByProcessId(id);

        for (BpmnNode node : bpmnNodes) {
            node.setBpmnProcess(null);
            for (BpmnUserTaskVar userTaskVar : node.getUser_task_var()) {
                userTaskVar.setBpmnNode(null);
            }
            for (BpmnSequence sequence : node.getSeq_incoming()) {
                sequence.setBpmnNode(null);
            }
            for (BpmnSequence sequence : node.getSeq_outgoing()) {
                sequence.setBpmnNode(null);
            }
        }
        return bpmnNodes;
    }
}
