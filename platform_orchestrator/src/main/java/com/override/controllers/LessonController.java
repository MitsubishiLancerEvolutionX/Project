package com.override.controllers;

import com.override.feigns.CodeExecutorFeign;
import dtos.CodeTryDTO;
import dtos.TaskIdentifierDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LessonController {

    @Autowired
    private CodeExecutorFeign codeExecutorFeign;

    @GetMapping("/lessons/{course}/{chapter}/{step}/{lesson}")
    public String lessonPage(@PathVariable String course, @PathVariable Integer chapter, @PathVariable Integer step,
                             @PathVariable Integer lesson) {
        return "lessons" + "/" + course + "/" + chapter + "/" + step + "/" + lesson;
    }

    @PostMapping("/lessons/{course}/{chapter}/{step}/{lesson}")
    public void TestResultDTO(@PathVariable String course,
                              @PathVariable Integer chapter,
                              @PathVariable Integer step,
                              @PathVariable Integer lesson,
                              @RequestBody String studentCode) {
        TaskIdentifierDTO taskIdentifierDTO = TaskIdentifierDTO.builder().chapter(chapter).step(step).lesson(lesson).build();
        codeExecutorFeign.execute(new CodeTryDTO(taskIdentifierDTO, studentCode));
    }
}
