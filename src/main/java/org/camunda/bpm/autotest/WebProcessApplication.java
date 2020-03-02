package org.camunda.bpm.autotest;

import org.camunda.bpm.autotest.property.FileStorageProperties;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableProcessApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class WebProcessApplication {

    @Autowired
    private RuntimeService runtimeService;

    public static void main(String... args) {
        SpringApplication.run(WebProcessApplication.class, args);
    }

//	@EventListener
//	private void processPostDeploy(PostDeployEvent event) {
//		runtimeService.startProcessInstanceByKey("loanApproval");
//	}
}