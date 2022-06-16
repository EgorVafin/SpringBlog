package com.example.demo.api.response.calendar;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CalendarRootResponse {

    private List<Integer> years;
    private List<CalendarResponse> posts;

}
