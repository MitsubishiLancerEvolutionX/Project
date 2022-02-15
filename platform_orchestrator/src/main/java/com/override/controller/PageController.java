package com.override.controller;

import com.github.benmanes.caffeine.cache.Cache;
import dtos.HelpMeTaskTextAndCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {
    private final Cache<Integer, HelpMeTaskTextAndCodeDTO> helpMeCache;

    @Autowired
    public PageController(Cache<Integer, HelpMeTaskTextAndCodeDTO> helpMeCache) {
        this.helpMeCache = helpMeCache;
    }


    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/courses")
    public String coursesPage() {
        return "courses";
    }

    @GetMapping("/balancePage")
    public String balancePage() {
        return "balance-check";
    }

    @GetMapping("/helpMe/{key}")
    public String getHelpView(Model model, @PathVariable int key) {
        HelpMeTaskTextAndCodeDTO helpMeDTO = helpMeCache.getIfPresent(key);
        if (helpMeDTO == null) {
            return "emptyHelpMeView";
        }
        model.addAttribute("insertText", helpMeDTO.getTaskHTML());
        model.addAttribute("editor.value", helpMeDTO.getCode());
        return "helpMeView";
    }
}
