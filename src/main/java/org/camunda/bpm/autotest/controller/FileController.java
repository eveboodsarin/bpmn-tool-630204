package org.camunda.bpm.autotest.controller;

import org.camunda.bpm.autotest.payload.UploadFileResponse;
import org.camunda.bpm.autotest.repositories.BpmnProcessRepository;
import org.camunda.bpm.autotest.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@CrossOrigin(maxAge = 3600)
@RestController
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private BpmnProcessRepository bpmnProcessRepository;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        Resource resource = fileStorageService.loadFileAsResource(fileName);
        AnalysisVariablesController analysisCon = new AnalysisVariablesController(bpmnProcessRepository, resource);
        analysisCon.Analysis();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
//        return  ResponseEntity.ok()
//                .body("aa");
    }

    @RequestMapping("/analysis")
    public String index() throws IOException {
        Resource resource = fileStorageService.loadFileAsResource("customer.bpmn");
        AnalysisVariablesController analysisCon = new AnalysisVariablesController(bpmnProcessRepository, resource);
        analysisCon.Analysis();
        Resource resource1 = fileStorageService.loadFileAsResource("store.bpmn");
        AnalysisVariablesController analysisCon1 = new AnalysisVariablesController(bpmnProcessRepository, resource1);
        analysisCon1.Analysis();
        return "Analysis started!";
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
