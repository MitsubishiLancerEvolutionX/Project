package com.override.controller.rest;

import com.override.service.InterviewReportService;
import dtos.InterviewReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interviewReport")
public class InterviewReportRestController {

    @Autowired
    private InterviewReportService interviewReportService;

    @PatchMapping
    public ResponseEntity<String> saveOrUpdateInterviewReport(@RequestBody InterviewReportDTO interviewReportDTO) {
        interviewReportService.saveInterviewReport(interviewReportDTO);
        return new ResponseEntity<>("Отчёт о собеседовании сохранён!", HttpStatus.OK);
    }

    @GetMapping
    public List<InterviewReportDTO> findAllInterviewReports() {
        return interviewReportService.findAllInterviewReports();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteInterviewReport(@RequestParam Long id) {
        interviewReportService.deleteInterviewReport(id);
        return new ResponseEntity<>("Отчёт о собеседовании удалён!", HttpStatus.OK);
    }
}
