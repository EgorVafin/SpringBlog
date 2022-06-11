package com.example.demo.controller;

import com.example.demo.api.response.InitResponse;
import com.example.demo.api.response.SettingsResponse;
import com.example.demo.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final InitResponse initResponse;

    @GetMapping("/init")
    @ResponseBody
//    public InitResponse apiGeneralInit(@RequestBody InitResponse initResponse)
    public InitResponse apiGeneralInit() {

        System.out.println(initResponse.getTitle());

        return initResponse;
    }

    @RequestMapping("/tag")
    @ResponseBody
    public String apiGeneralTag() {



        return "General tag";
    }

    @GetMapping("/settings")
    public ResponseEntity<SettingsResponse> apiGeneralSettings() {

        return new ResponseEntity<>(settingsService.getGlobalSettings(), HttpStatus.OK);
    }
}
