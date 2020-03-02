package org.camunda.bpm.autotest.repositories;

import org.camunda.bpm.autotest.model.BpmnNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BpmnNodeRepository extends JpaRepository<BpmnNode, Long> {
    @Query(value = "SELECT * FROM BPMN_NODE where BPMN_PROCESS_ID = ?1", nativeQuery = true)
    List<BpmnNode> findByProcessId(Long id);
}
