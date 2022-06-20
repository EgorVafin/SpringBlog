package com.example.demo.service;

import com.example.demo.api.response.SettingsResponse;
import com.example.demo.dao.SettingsRepository;
import com.example.demo.model.GlobalSetting;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingsService {

    private final SettingsRepository settingsRepository;

    public SettingsResponse getGlobalSettings() {

        List<GlobalSetting> settings = settingsRepository.findAll();

        SettingsResponse settingsResponse = new SettingsResponse();

        for (GlobalSetting item : settings) {

            switch (item.getCode()) {
                case "MULTIUSER_MODE" -> settingsResponse.setMultiuserMode(convertToBool(item.getValue()));
                case "POST_PREMODERATION" -> settingsResponse.setPostPremoderation(convertToBool(item.getValue()));
                case "STATISTICS_IS_PUBLIC" -> settingsResponse.setStatisticsIsPublic(convertToBool(item.getValue()));
                default -> {
                }
            }
        }

        return settingsResponse;
    }

    private boolean convertToBool(String value) {

        if (value.equals("YES")) {
            return true;
        }
        if (value.equals("NO")) {
            return false;
        }

        return false;
    }

}
