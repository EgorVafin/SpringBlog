package com.example.demo.service;

import com.example.demo.api.response.calendar.CalendarResponse;
import com.example.demo.api.response.calendar.CalendarRootResponse;
import com.example.demo.dao.CalendarProjection;
import com.example.demo.dao.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarResponseProcessor {

    private final CalendarRepository calendarRepository;

    public CalendarRootResponse process(String year) {

        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        year = year.trim();

        int intYear;
        if (year.isEmpty()) {
            intYear = currentYear;
        } else {
            intYear = Integer.parseInt(year);
        }

        List<CalendarProjection> result = calendarRepository.calendar(intYear);

        List<CalendarResponse> calendarResponseArrayList = result.stream().map(item -> new CalendarResponse(item.getDate(), item.getCount()))
                .collect(Collectors.toList());

        List<Integer> years = calendarRepository.calendarAllYears();
        CalendarRootResponse response = new CalendarRootResponse(years, calendarResponseArrayList);

        return response;
    }
}
