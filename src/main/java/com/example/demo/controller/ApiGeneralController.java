package com.example.demo.controller;

import com.example.demo.api.response.InitResponse;
import com.example.demo.api.response.SettingsResponse;
import com.example.demo.api.response.calendar.CalendarRootResponse;
import com.example.demo.api.response.tag.TagRootResponse;
import com.example.demo.service.CalendarResponseProcessor;
import com.example.demo.service.SettingsService;
import com.example.demo.service.TagResponseProcessor;
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

    private final TagResponseProcessor tagResponseProcessor;
    private final CalendarResponseProcessor calendarResponseProcessor;

    @GetMapping("/init")
    @ResponseBody
//    public InitResponse apiGeneralInit(@RequestBody InitResponse initResponse)
    public InitResponse apiGeneralInit() {

        System.out.println(initResponse.getTitle());

        return initResponse;
    }

    @RequestMapping("/tag")
    @ResponseBody
    public TagRootResponse apiGeneralTag(@RequestParam(value = "query", required = false, defaultValue = "") String query) {

        return tagResponseProcessor.process(query);
    }

    @GetMapping("/settings")
    public ResponseEntity<SettingsResponse> apiGeneralSettings() {

        return new ResponseEntity<>(settingsService.getGlobalSettings(), HttpStatus.OK);
    }

    @GetMapping("/calendar")
    @ResponseBody
    public CalendarRootResponse apiGeneralCalendar(@RequestParam(value = "year", required = false, defaultValue = "") String year) {

        return calendarResponseProcessor.process(year);
    }
}
