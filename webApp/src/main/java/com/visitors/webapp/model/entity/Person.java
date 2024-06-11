package com.visitors.webapp.model.entity;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import com.visitors.webapp.model.entity.enums.City;
import com.visitors.webapp.model.entity.enums.Gender;
import com.visitors.webapp.model.entity.enums.Status;
import com.visitors.webapp.model.entity.enums.Role;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder

public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String nationalId;
    private String email;
    private Gender gender;
    private String phone_number;
    private Status status;
    private LocalDate birthDate;
    private City city;
    private String username;
    private String password;
    private Role role;
    private MedicalService medicalService;


@Override
    public String toString(){
    Gson json = new Gson();
    return json.toJson(this);
}

}
