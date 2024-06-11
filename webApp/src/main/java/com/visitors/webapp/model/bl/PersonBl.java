package com.visitors.webapp.model.bl;


import com.visitors.webapp.model.controller.exceptions.NoPersonFoundException;
import com.visitors.webapp.model.da.PersonDa;
import com.visitors.webapp.model.entity.Person;
import com.visitors.webapp.model.entity.enums.Role;
import com.visitors.webapp.model.tools.CRUD;
import lombok.Getter;


import java.sql.SQLException;
import java.util.List;

public class PersonBl implements CRUD<Person> {
    @Getter
    private static PersonBl personBl = new PersonBl();

    private PersonBl() {
    }

    @Override
    public Person save(Person person) throws Exception {
        try (PersonDa personDa = new PersonDa()) {
            return personDa.save(person);
        }

    }

    @Override
    public Person edit(Person person) throws Exception {
        try(PersonDa personDa = new PersonDa()) {
            if(personDa.findById(person.getId()) != null){
                personDa.edit(person);
                return person;
            }
            else{
                throw new NoPersonFoundException();
            }
        }
    }

    @Override
    public Person remove(int id) throws Exception {
        try (PersonDa personDa = new PersonDa()) {
            Person person = personDa.findById(id);

            if (person != null) {
                personDa.remove(id);
                return person;
            } else {
                throw new NoPersonFoundException();
            }
        }
    }

    @Override
    public List<Person> findAll() throws Exception {
        try (PersonDa personDa = new PersonDa()) {
            List<Person> perosnList = personDa.findAll();
            if (!perosnList.isEmpty()) {
                return perosnList;
            } else {
                throw new NoPersonFoundException();
            }

        }
    }

    @Override
    public Person findById(int id) throws Exception {
        try (PersonDa personDa = new PersonDa()) {
            Person person = personDa.findById(id);
            if (person != null) {
                return person;
            } else {
                throw new NoPersonFoundException();
            }
        }
    }

    public List<Person> findByLastName(String lastName) throws Exception {
        try (PersonDa personDa = new PersonDa()) {
            List<Person> personList = personDa.findByLastName(lastName);
            if (!personList.isEmpty()) {
                return personList;
            } else {
                throw new NoPersonFoundException();
            }
        }
    }

    public List<Person> findByUsername(String username) throws Exception {
        try (PersonDa personDa = new PersonDa()) {
            List<Person> personList = personDa.findByUsername(username);
            if (!personList.isEmpty()) {
                return personList;
            } else {
                throw new NoPersonFoundException();
            }

        }
    }

    public List<Person> findByUserPass(String username, String password) throws Exception {
        try (PersonDa personDa = new PersonDa()) {
            List<Person> personList = personDa.findByUserPass(username, password);
            if (!personList.isEmpty()) {
                return personList;
            } else {
                throw new NoPersonFoundException();
            }
        }
    }

    public Person findByRole(Role role) throws Exception {
        try (PersonDa personDa = new PersonDa()) {
            Person person = personDa.findByRole(role);
            if (person != null) {
                return person;
            } else {
                throw new NoPersonFoundException();
            }
        }


    }

    public Person findByService(String service) throws Exception {
        try (PersonDa personDa = new PersonDa()) {
            Person person = personDa.findByService(service);
            if (person != null) {
                return person;
            } else {
                throw new NoPersonFoundException();
            }
        }
    }


}
