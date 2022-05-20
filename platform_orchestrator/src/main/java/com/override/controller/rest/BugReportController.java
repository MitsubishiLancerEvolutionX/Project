package com.override.controller.rest;

import com.override.models.Bug;
import com.override.service.BugReportService;
import com.override.service.CustomStudentDetailService;
import dtos.BugReportsDTO;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/bugReports")
public class BugReportController {

    @Autowired
    private BugReportService bugReportService;

    @PostMapping("")
    public void  uploadScreen(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user,
                                               @RequestParam("file") MultipartFile multipartFile)throws FileUploadException {
        bugReportService.uploadFile(multipartFile, user.getUsername());
//        return new ResponseEntity<>(bugs,HttpStatus.OK) ;
    }
//    @GetMapping("")
//    @ResponseBody
//    public  String soutb(){
//        System.out.println("mrfimfri");
//        return "vmfv";
//    }

//    @Secured("ROLE_ADMIN")
//    @GetMapping("/{login}")
//    public List<BugReportsDTO> getAllFilesInfo(@PathVariable String login) {
//        return bugReportService.getAllByUserLogin(login);
//    }

    @Secured("ROLE_ADMIN")
@GetMapping("/checkBugs")
public String checkBugs(){
         bugReportService.allBugs();
        return  "allBugs";

}

    @GetMapping("/current")
    public List<BugReportsDTO> getAllFilesInfoForCurrentUser(@AuthenticationPrincipal CustomStudentDetailService.CustomStudentDetails user) {
        return bugReportService.getAll(user.getUsername());
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Bug bug = bugReportService.downloadFile(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(bug.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; discription=" + bug.getName())
                .body(new ByteArrayResource(bug.getContent()));
    }
}
