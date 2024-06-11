package com.visitors.webapp.model.entity;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import com.visitors.webapp.model.entity.enums.Status;


@Getter
@Setter
@NoArgsConstructor
@SuperBuilder

public class MedicalService {
    private int serviceId;
    private String serviceName;
    private String serviceDescription;
    private String serviceType;
    private boolean serviceStatus;


    @Override
    public String toString() {
        Gson json = new Gson();
        return json.toJson(this);
    }
}
