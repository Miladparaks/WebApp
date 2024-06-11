package com.visitors.webapp.model.da;

import com.visitors.webapp.model.entity.MedicalService;
import com.visitors.webapp.model.entity.Person;
import com.visitors.webapp.model.entity.enums.City;
import com.visitors.webapp.model.entity.enums.Gender;
import com.visitors.webapp.model.entity.enums.Role;
import com.visitors.webapp.model.entity.enums.Status;
import com.visitors.webapp.model.tools.CRUD;
import com.visitors.webapp.model.tools.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDa implements AutoCloseable, CRUD<Person> {
    private static Connection connection;
    private static PreparedStatement preparedStatement;

    public PersonDa() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }

    @Override
    public Person save(Person person) throws Exception {
        person.setId(ConnectionProvider.getConnectionProvider().getNextId("PERSON_SEQ"));
        preparedStatement = connection.prepareStatement(
                "INSERT INTO PERSON (ID, FIRSTNAME, LASTNAME, AGE, NATIONALID, EMAIL, GENDER, phone_number, PERSON_STATUS, PERSON_BIRTHDATE, CITY, USERNAME, PASSWORD, ROLE, SERVICE_ID) VALUES" +
                        " (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
        );
        preparedStatement.setInt(1, person.getId());
        preparedStatement.setString(2, person.getFirstName());
        preparedStatement.setString(3, person.getLastName());
        preparedStatement.setInt(4, person.getAge());
        preparedStatement.setString(5, person.getNationalId());
        preparedStatement.setString(6, person.getEmail());
        preparedStatement.setString(7, person.getGender().toString());
        preparedStatement.setString(8, person.getPhone_number());
        preparedStatement.setString(9, person.getStatus().toString());
        preparedStatement.setDate(10, Date.valueOf(person.getBirthDate()));
        preparedStatement.setString(11, person.getCity().toString());
        preparedStatement.setString(12, person.getUsername());
        preparedStatement.setString(13, person.getPassword());
        preparedStatement.setString(14, person.getRole().toString());
        preparedStatement.setInt(15, person.getMedicalService().getServiceId());
        preparedStatement.execute();
        return person;
    }

    @Override
    public Person edit(Person person) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE PERSON SET firstname = ?, lastname = ?, age = ?, nationalid = ?, email = ?, gender = ? , phone_number = ?, person_status = ? , person_status = ? , city = ? , username = ?, password = ? , role = ?, service_id = ? WHERE ID = ?"
        );

        preparedStatement.setString(1, person.getFirstName());
        preparedStatement.setString(2, person.getLastName());
        preparedStatement.setString(3, String.valueOf(person.getAge()));
        preparedStatement.setString(4, person.getNationalId());
        preparedStatement.setString(5, person.getEmail());
        preparedStatement.setString(6, person.getGender().toString());
        preparedStatement.setString(7, person.getPhone_number());
        preparedStatement.setString(8, person.getStatus().toString());
        preparedStatement.setDate(9, Date.valueOf(person.getBirthDate()));
        preparedStatement.setString(10, person.getCity().toString());
        preparedStatement.setString(11, person.getUsername());
        preparedStatement.setString(12, person.getPassword());
        preparedStatement.setString(13, person.getRole().toString());
        preparedStatement.setInt(14, Integer.parseInt(person.getMedicalService().toString()));
        preparedStatement.setInt(15, person.getId());
        preparedStatement.execute();
        return person;
    }

    @Override
    public Person remove(int id) throws Exception {

        preparedStatement = connection.prepareStatement(
                "DELETE FROM PERSON WHERE ID = ?"
        );

        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        return null;
    }

    @Override
    public List<Person> findAll() throws Exception {

        List<Person> personList = new ArrayList<>();

        preparedStatement = connection.prepareStatement("SELECT * FROM PERSON");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Person person = Person.builder()
                    .id(resultSet.getInt("ID"))
                    .firstName(resultSet.getString("FIRSTNAME"))
                    .lastName(resultSet.getString("LASTNAME"))
                    .age((resultSet.getInt("AGE")))
                    .nationalId(resultSet.getString("NATIONALID"))
                    .email(resultSet.getString("EMAIL"))
                    .gender(Gender.valueOf(resultSet.getString("GENDER")))
                    .phone_number(resultSet.getString("phone_number"))
                    .status(Status.valueOf(resultSet.getString("PERSON_STATUS")))
                    .birthDate(resultSet.getDate("PERSON_BIRTHDATE").toLocalDate())
                    .city(City.valueOf(resultSet.getString("CITY")))
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .role(Role.valueOf(resultSet.getString("ROLE")))
                    .medicalService(MedicalService.builder().serviceId(resultSet.getInt("ID")).build())
                    .build();
            personList.add(person);
        }

        return personList;
    }

    @Override
    public Person findById(int id) throws Exception {
        preparedStatement = connection.prepareStatement("SELECT * FROM PERSON WHERE id = ?");
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        Person person = null;

        if (resultSet.next()) {
            person = Person
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .firstName(resultSet.getString("firstname"))
                    .lastName(resultSet.getString("lastname"))
                    .age(resultSet.getInt("age"))
                    .nationalId(resultSet.getString("nationalid"))
                    .email(resultSet.getString("email"))
                    .gender(Gender.valueOf(resultSet.getString("gender")))
                    .phone_number(resultSet.getString("Phone_number"))
                    .status(Status.valueOf(resultSet.getString("person_status")))
                    .birthDate(resultSet.getDate("person_birthdate").toLocalDate())
                    .city(City.valueOf(resultSet.getString("city")))
                    .username(resultSet.getString("username"))
                    .password(resultSet.getString("password"))
                    .role(Role.valueOf(resultSet.getString("role")))
                    .medicalService(MedicalService.builder().serviceId(resultSet.getInt("Id")).build())
                    .build();
        }
        return person;
    }


    public List<Person> findByLastName(String lastname) throws SQLException {

        List<Person> personList = new ArrayList<>();

        preparedStatement = connection.prepareStatement("select * from PERSON where lastname like ? order by ID ASC");
        preparedStatement.setString(1, "%" + lastname + "%");
        ResultSet resultSet = preparedStatement.executeQuery();


        while (resultSet.next()) {
            Person person = Person
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .firstName(resultSet.getString("FIRSTNAME"))
                    .lastName(resultSet.getString("LASTNAME"))
                    .nationalId(resultSet.getString("NATIONALID"))
                    .email(resultSet.getString("EMAIL"))
                    .gender(Gender.valueOf(resultSet.getString("GENDER")))
                    .phone_number(resultSet.getString("Phone_number"))
                    .status(Status.valueOf(resultSet.getString("PERSON_STATUS")))
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .build();

            personList.add(person);
        }
        return personList;
    }


    public List<Person> findByUsername(String username) throws SQLException {

        preparedStatement = connection.prepareStatement("select * from PERSON where username like ? order by ID");
        preparedStatement.setString(1, "%" + username + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        List<Person> personList = new ArrayList<>();

        while (resultSet.next()) {
            Person person = Person
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .firstName(resultSet.getString("FIRSTNAME"))
                    .lastName(resultSet.getString("LASTNAME"))
                    .nationalId(resultSet.getString("NATIONALID"))
                    .email(resultSet.getString("EMAIL"))
                    .gender(Gender.valueOf(resultSet.getString("GENDER")))
                    .phone_number(resultSet.getString("Phone_number"))
                    .status(Status.valueOf(resultSet.getString("PERSON_STATUS")))
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .build();
            personList.add(person);

        }
        return personList;
    }

    public List<Person> findByUserPass(String username, String password) throws SQLException {

        preparedStatement = connection.prepareStatement("select * from PERSON where username like ? and password like ? order by ID");
        preparedStatement.setString(1, "%" + username + "%");
        preparedStatement.setString(2, "%" + password + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Person> personList = new ArrayList<>();

        if (resultSet.next()) {
            Person person = Person
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .firstName(resultSet.getString("FIRSTNAME"))
                    .lastName(resultSet.getString("LASTNAME"))
                    .nationalId(resultSet.getString("NATIONALID"))
                    .email(resultSet.getString("EMAIL"))
                    .gender(Gender.valueOf(resultSet.getString("GENDER")))
                    .phone_number(resultSet.getString("Phone_number"))
                    .status(Status.valueOf(resultSet.getString("PERSON_STATUS")))
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .build();
            personList.add(person);

        }
        return personList;
    }

    public Person findByRole(Role role) throws SQLException {

        preparedStatement = connection.prepareStatement("select * from PERSON where role like ? order by ID");
        preparedStatement.setString(1, "%" + role + "%");

        ResultSet resultSet = preparedStatement.executeQuery();

        Person person = null;

        if (resultSet.next()) {
            person = Person
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .firstName(resultSet.getString("FIRSTNAME"))
                    .lastName(resultSet.getString("LASTNAME"))
                    .nationalId(resultSet.getString("NATIONALID"))
                    .email(resultSet.getString("EMAIL"))
                    .gender(Gender.valueOf(resultSet.getString("GENDER")))
                    .phone_number(resultSet.getString("Phone_number"))
                    .status(Status.valueOf(resultSet.getString("PERSON_STATUS")))
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .build();

        }
        return person;
    }

    public Person findByService(String service) throws SQLException {

        preparedStatement = connection.prepareStatement("select * from PERSON where SERVICE_ID like ? order by ID ASC");
        preparedStatement.setString(1, "%" + service + "%");

        ResultSet resultSet = preparedStatement.executeQuery();

        Person person = null;

        if (resultSet.next()) {
            person = Person
                    .builder()
                    .id(resultSet.getInt("ID"))
                    .firstName(resultSet.getString("FIRSTNAME"))
                    .lastName(resultSet.getString("LASTNAME"))
                    .nationalId(resultSet.getString("NATIONALID"))
                    .email(resultSet.getString("EMAIL"))
                    .gender(Gender.valueOf(resultSet.getString("GENDER")))
                    .phone_number(resultSet.getString("Phone_number"))
                    .status(Status.valueOf(resultSet.getString("PERSON_STATUS")))
                    .username(resultSet.getString("USERNAME"))
                    .password(resultSet.getString("PASSWORD"))
                    .build();

        }
        return person;
    }


    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }


}
