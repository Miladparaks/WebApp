package com.visitors.webapp.model.entity;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@SuperBuilder


public class
Timing {
    private int timeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Person doctor;
    private String location;
    private int roomNumber;


    @Override
    public String toString() {
        Gson json = new Gson();
        return json.toJson(this);
    }
}
