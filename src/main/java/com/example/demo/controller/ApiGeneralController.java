package com.example.demo.controller;

import com.example.demo.api.response.InitResponse;
import com.example.demo.api.response.ResultErrorsResponse;
import com.example.demo.api.response.SettingsResponse;
import com.example.demo.api.response.calendar.CalendarRootResponse;
import com.example.demo.api.response.tag.TagRootResponse;
import com.example.demo.service.CalendarResponseProcessor;
import com.example.demo.service.SettingsService;
import com.example.demo.service.TagResponseProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiGeneralController {

    public static final String UPLOADS = "uploads";
    private final SettingsService settingsService;
    private final InitResponse initResponse;

    private final TagResponseProcessor tagResponseProcessor;
    private final CalendarResponseProcessor calendarResponseProcessor;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/init")
    @ResponseBody
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

    @PostMapping("/image")
    public String uploadImage(@RequestParam("image") MultipartFile file) {

        String originFileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        long fileSize = file.getSize();

        if (fileSize > 10 * 1024 * 1024) {
            ResultErrorsResponse resultErrorsResponse = ResultErrorsResponse.errors(Map.of("image", "Размер файла превышает допустимый размер"));

        }


        int random = (int) (Math.random() * 100_000_000);
        String hash = passwordEncoder.encode(Integer.toString(random));

        String[] parts = hash.split("\\$");
        String lastPart = parts[parts.length - 1];
        lastPart = lastPart.replaceAll("[^0-9a-zA-Z]", "");

        String[] folders = new String[3];
        String fileName;

        int folderLenght = 2;
        for (int i = 0; i < 3; i++) {
            folders[i] = lastPart.substring(i * folderLenght, i * folderLenght + (folderLenght));
        }


        fileName = lastPart.substring(3 * folderLenght) + originFileName.substring(originFileName.indexOf('.'));

        String finalFolderName = "";
        for (int i = 0; i < 3; i++) {
            finalFolderName = finalFolderName + "\\" + folders[i];
        }

        Path root = Paths.get(UPLOADS);

        if (!Files.exists(root)) {
            try {
                Files.createDirectory(root);
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize folder. Error: ", e);
            }
        }

        Path path2 = Paths.get("uploads" + finalFolderName);
        try {
            Files.createDirectories(path2);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder. Error: ", e);

        }

        System.out.println(finalFolderName);

        finalFolderName = finalFolderName.substring(1, finalFolderName.length());
        System.out.println(finalFolderName);

        try {
            Files.copy(file.getInputStream(), root.resolve(finalFolderName + "\\" + fileName));
        } catch (IOException e) {
            throw new RuntimeException("Could not save to store. Error: ", e);
        }

        return finalFolderName + "\\" + fileName;
    }
}
