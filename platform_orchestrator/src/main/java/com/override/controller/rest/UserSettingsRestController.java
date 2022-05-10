package com.override.controller.rest;

import com.override.models.UserSettings;
import com.override.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userSettings")
public class UserSettingsRestController {

    @Autowired
    private UserSettingsService userSettingsService;

    @PatchMapping("/{userLogin}")
    public void patch(@RequestBody UserSettings userSettings,
                      @PathVariable String userLogin) {
        userSettingsService.save(userSettings, userLogin);
    }
}
