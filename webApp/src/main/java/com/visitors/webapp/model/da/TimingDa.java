package com.visitors.webapp.model.da;

import com.visitors.webapp.model.entity.Person;
import com.visitors.webapp.model.entity.Timing;
import com.visitors.webapp.model.tools.CRUD;
import com.visitors.webapp.model.tools.ConnectionProvider;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimingDa implements AutoCloseable, CRUD<Timing> {
    private static Connection connection;
    private static PreparedStatement preparedStatement;

    public TimingDa() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }


    @Override
    public Timing save(Timing timing) throws Exception {
        timing.setTimeId(ConnectionProvider.getConnectionProvider().getNextId("TIMING_SEQ"));

        preparedStatement = connection.prepareStatement(
                "INSERT INTO TIMING (ID, START_TIME, END_TIME, DOCTOR_ID, LOCATION, ROOM_NUMBER) VALUES (?, ?, ?, ?, ?, ?)"
        );
        preparedStatement.setInt(1, timing.getTimeId());
        preparedStatement.setTimestamp(2, Timestamp.valueOf(timing.getStartTime()));
        preparedStatement.setTimestamp(3, Timestamp.valueOf(timing.getEndTime()));
        preparedStatement.setInt(4, Integer.parseInt(String.valueOf(timing.getDoctor().getId())));
        preparedStatement.setString(5, timing.getLocation());
        preparedStatement.setInt(6, timing.getRoomNumber());
        preparedStatement.execute();
        return timing;
    }

    @Override
    public Timing edit(Timing timing) throws Exception {
        timing.setTimeId(ConnectionProvider.getConnectionProvider().getNextId("TIMING_SEQ"));

        preparedStatement = connection.prepareStatement(
                "UPDATE TIMING SET START_TIME = ?, END_TIME = ?, DOCTOR_ID = ?, LOCATION = ?, ROOM_NUMBER = ? WHERE ID = ?"
        );
        preparedStatement.setTimestamp(1, Timestamp.valueOf(timing.getStartTime()));
        preparedStatement.setTimestamp(2, Timestamp.valueOf(timing.getEndTime()));
        preparedStatement.setInt(3, timing.getDoctor().getId());
        preparedStatement.setString(4, timing.getLocation());
        preparedStatement.setInt(5, timing.getRoomNumber());
        preparedStatement.setInt(6, timing.getTimeId());
        preparedStatement.execute();
        return timing;
    }

    @Override
    public Timing remove(int id) throws Exception {
        preparedStatement = connection.prepareStatement("Delete FROM TIMING WHERE ID = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeQuery();
        return null;
    }

    @Override
    public List<Timing> findAll() throws Exception {

        List<Timing> timingList = new ArrayList<>();

        preparedStatement = connection.prepareStatement("select * from timing");
        ResultSet resultSet = preparedStatement.executeQuery();


        while (resultSet.next()) {
            Timing timing = Timing
                    .builder()
                    .timeId(resultSet.getInt("ID"))
                    .startTime(resultSet.getTimestamp("START_TIME").toLocalDateTime())
                    .endTime(resultSet.getTimestamp("END_TIME").toLocalDateTime())
                    .doctor(Person.builder().id(resultSet.getInt("DOCTOR_ID")).build())
                    .location(resultSet.getString("LOCATION"))
                    .roomNumber(resultSet.getInt("ROOM_NUMBER"))
                    .build();
            timingList.add(timing);
        }
        return timingList;
    }

    @Override
    public Timing findById(int id) throws Exception {
        preparedStatement = connection.prepareStatement("select * from timing where ID = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Timing timing = null;

        if (resultSet.next()) {
            timing = Timing
                    .builder()
                    .timeId(resultSet.getInt("ID"))
                    .startTime(resultSet.getTimestamp("START_TIME").toLocalDateTime())
                    .endTime(resultSet.getTimestamp("END_TIME").toLocalDateTime())
                    .doctor(Person.builder().id(resultSet.getInt("DOCTOR_ID")).build())
                    .location(resultSet.getString("LOCATION"))
                    .roomNumber(resultSet.getInt("ROOM_NUMBER"))
                    .build();
        }
        return timing;
    }


    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }
}
