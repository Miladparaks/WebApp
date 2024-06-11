package com.visitors.webapp.model.entity;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import com.visitors.webapp.model.entity.enums.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder

public class Visit {
    private int Id;
    private Person customer;
    private Timing timing;
    private LocalDateTime visitTime;
    private int duration;
    private Payment payment;
    private Status status;

    @Override
    public String toString() {
        Gson json = new Gson();
        return json.toJson(this);
    }
}
