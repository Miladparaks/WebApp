package com.visitors.webapp.controller;

import com.google.gson.Gson;
import com.visitors.webapp.model.bl.PersonBl;
import com.visitors.webapp.model.entity.Person;
import org.jboss.resteasy.annotations.Form;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/person")
public class PersonController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersons() throws Exception {
//        return Response.ResponseBuilder.entity(PersonBl.getPersonBl().findAll()).build();
        return null;
    }

    @POST
    public Response save(
            @FormParam("name") String firstName,
            @QueryParam("LastName") String lastName) throws Exception {

        Person person = Person.builder().firstName(firstName).lastName(lastName).build();
        PersonBl.getPersonBl().save(person);
        URI location = new URI("login.html");
        return Response.temporaryRedirect(location).build();
    }

//    public static String save(String firstName, String lastName, int age, String nationalId, String email, Gender gender, String phone_number, Status status, LocalDate birthDate, City city, String username, String password, Role role, MedicalService medicalService) {
//        try {
//            Person person = new Person();
//            person.setFirstName(nameValidator(firstName, "Invalid First Name"));
//            person.setLastName(nameValidator(lastName, "Invalid Last Name"));
//            person.setAge(ageValidator(age, "Invalid Age, Age must be between 6 and 90"));
//            person.setNationalId(nationalIdValidator(nationalId, "Invalid National ID"));
//            person.setEmail(emailValidator(email, "Invalid Email Address"));
//            person.setGender(gender);
//            person.setPhone_number(phone_number);
//            person.setStatus(status);
//            person.setBirthDate(birthDate);
//            person.setCity(city);
//            person.setUsername(username);
//            person.setPassword(password);
//            person.setRole(role);
//            person.setMedicalService(medicalService);
//            PersonBl.getPersonBl().save(person);
//            return "Person saved in DataBase";
//
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//
//    }

//    public static String edit(String firstName, String lastName, int age, String nationalId, String email, Gender gender, String phone_number, Status status, LocalDate birthDate, City city, String username, String password, Role role, MedicalService medicalService) {
//        try {
//
//
//
//            return "Person updated in DataBase";
//
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//    }
}
