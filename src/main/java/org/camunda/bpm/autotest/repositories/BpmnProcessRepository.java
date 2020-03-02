package org.camunda.bpm.autotest.repositories;

import org.camunda.bpm.autotest.model.BpmnProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BpmnProcessRepository extends JpaRepository<BpmnProcess,Long> {
}
