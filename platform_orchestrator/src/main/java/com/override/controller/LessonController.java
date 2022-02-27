package com.override.controller;

import com.override.service.LessonStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lessons")
public class LessonController {

    private final LessonStructureService lessonStructureService;

    @Autowired
    public LessonController(LessonStructureService lessonStructureService) {
        this.lessonStructureService = lessonStructureService;
    }

    @GetMapping("{course}/{chapter}/{step}/{lesson}")
    public String lessonPage(@PathVariable String course, @PathVariable String chapter, @PathVariable String step,
                             @PathVariable String lesson) {
        return "lessons" + "/" + course + "/" + chapter + "/" + step + "/" + lesson;
    }

    @ResponseBody
    @GetMapping(value = "/structureOf/{course}", produces = "application/json")
    public String getLessonStructure(@PathVariable String course) {
        return lessonStructureService.getLessonStructure(course).toString();
    }
}
