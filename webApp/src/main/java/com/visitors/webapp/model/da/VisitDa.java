package com.visitors.webapp.model.da;

import com.visitors.webapp.model.controller.exceptions.NoVisitFoundException;
import com.visitors.webapp.model.entity.Payment;
import com.visitors.webapp.model.entity.Person;
import com.visitors.webapp.model.entity.Timing;
import com.visitors.webapp.model.entity.Visit;
import com.visitors.webapp.model.entity.enums.Status;
import com.visitors.webapp.model.tools.CRUD;
import com.visitors.webapp.model.tools.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VisitDa implements AutoCloseable, CRUD<Visit> {

    private static Connection connection;
    private static PreparedStatement preparedStatement;

    public VisitDa() throws Exception {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }

    @Override
    public Visit save(Visit visit) throws Exception {
        visit.setId(ConnectionProvider.getConnectionProvider().getNextId("VISIT_SEQ"));
        preparedStatement = connection.prepareStatement(
                "INSERT INTO VISIT (visit_id, customer, timing_id, visit_time, duration, payment_id, status) VALUES (?, ?, ?, ?, ?, ?, ?)"
        );
        preparedStatement.setInt(1, visit.getId());
        preparedStatement.setInt(2, visit.getCustomer().getId());
        preparedStatement.setInt(3, visit.getTiming().getTimeId());
        preparedStatement.setTimestamp(4, Timestamp.valueOf(visit.getVisitTime()));
        preparedStatement.setInt(5, visit.getDuration());
        preparedStatement.setInt(6, visit.getPayment().getPaymentId());
        preparedStatement.setString(7, visit.getStatus().toString());
        preparedStatement.executeQuery();
        return visit;
    }


    @Override
    public Visit edit(Visit visit) throws Exception {
        preparedStatement = connection.prepareStatement(
                "UPDATE VISIT SET customer = ?, timing_id = ?, visit_time = ?, duration = ?, payment_id = ?, status = ? WHERE VISIT_ID = ?"
        );

        preparedStatement.setInt(1, visit.getCustomer().getId());
        preparedStatement.setInt(2, visit.getTiming().getTimeId());
        preparedStatement.setTimestamp(3, Timestamp.valueOf(visit.getVisitTime()));
        preparedStatement.setInt(4, visit.getDuration());
        preparedStatement.setInt(5, visit.getPayment().getPaymentId());
        preparedStatement.setString(6, visit.getStatus().toString());
        preparedStatement.setInt(7, visit.getId());
        preparedStatement.executeQuery();
        return visit;
    }

    @Override
    public Visit remove(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM VISIT WHERE visit_id = ?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        return null;
    }

    @Override
    public List<Visit> findAll() throws Exception {
        List<Visit> visits = new ArrayList<>();
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM VISIT"
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Visit visit = Visit
                    .builder()
                    .Id(resultSet.getInt("VISIT_ID"))
                    .customer(Person.builder().id(resultSet.getInt("CUSTOMER")).build())
                    .timing(Timing.builder().timeId(resultSet.getInt("TIMING_ID")).build())
                    .visitTime(resultSet.getTimestamp("VISIT_TIME").toLocalDateTime())
                    .duration(resultSet.getInt("DURATION"))
                    .payment(Payment.builder().paymentId(resultSet.getInt("PAYMENT_ID")).build())
                    .status(Status.valueOf(resultSet.getString("STATUS")))
                    .build();

            visits.add(visit);
        }
        return visits;
    }

    @Override
    public Visit findById(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM VISIT WHERE visit_id = ?"
        );
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        Visit visit = null;
        if (resultSet.next()) {
            visit = Visit
                    .builder()
                    .Id(resultSet.getInt("VISIT_ID"))
                    .customer(Person.builder().id(resultSet.getInt("CUSTOMER")).build())
                    .timing(Timing.builder().timeId(resultSet.getInt("TIMING_ID")).build())
                    .visitTime(resultSet.getTimestamp("VISIT_TIME").toLocalDateTime())
                    .duration(resultSet.getInt("DURATION"))
                    .payment(Payment.builder().paymentId(resultSet.getInt("PAYMENT_ID")).build())
                    .status(Status.valueOf(resultSet.getString("STATUS")))
                    .build();
            return visit;
        } else {
            throw new NoVisitFoundException();
        }

    }

    @Override
    public void close() throws Exception {

        preparedStatement.close();
        connection.close();
    }

}
