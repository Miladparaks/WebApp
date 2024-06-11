package com.visitors.webapp.model.da;

import com.visitors.webapp.model.entity.Payment;
import com.visitors.webapp.model.entity.enums.VisitType;
import com.visitors.webapp.model.tools.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDa implements AutoCloseable {
    private static Connection connection;
    private static PreparedStatement preparedStatement;

    public PaymentDa() throws SQLException {
        connection = ConnectionProvider.getConnectionProvider().getConnection();
    }

    public void save(Payment payment) throws Exception {
        payment.setPaymentId(ConnectionProvider.getConnectionProvider().getNextId("PAYMENT_SEQ"));

        preparedStatement = connection.prepareStatement(
                "INSERT INTO PAYMENT (payment_Id, payment_Time, payment_Status, payment_Type) VALUES ( ?, ?, ?, ?)"
        );
        preparedStatement.setInt(1, payment.getPaymentId());
        preparedStatement.setTimestamp(2, Timestamp.valueOf(payment.getPaymentTime()));
        preparedStatement.setString(3, payment.getPaymentStatus());
        preparedStatement.setString(4, String.valueOf(VisitType.valueOf(String.valueOf(payment.getPaymentType()))));
        preparedStatement.executeQuery();
    }

    public void edit(Payment payment) throws SQLException {
        preparedStatement = connection.prepareStatement(
                "UPDATE PAYMENT SET payment_time = ?, payment_status = ?, payment_type = ?  WHERE payment_id = ?"
        );

        preparedStatement.setTimestamp(1, Timestamp.valueOf(payment.getPaymentTime()));
        preparedStatement.setString(2, payment.getPaymentStatus());
        preparedStatement.setString(3, String.valueOf(payment.getPaymentType()));
        preparedStatement.setInt(4, payment.getPaymentId());
        preparedStatement.executeQuery();
    }

    public void remove(int id) throws Exception {
        preparedStatement = connection.prepareStatement(
                "DELETE FROM PAYMENT WHERE payment_Id = ?"
        );
        preparedStatement.setInt(1, id);
        preparedStatement.executeQuery();
    }


    public static List<Payment> findAll() throws Exception {
        List<Payment> paymentList = new ArrayList<>();
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM PAYMENT"
        );
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Payment payment = Payment.builder()
                    .paymentId(resultSet.getInt("payment_Id"))
                    .paymentTime(resultSet.getTimestamp("payment_Time").toLocalDateTime())
                    .paymentStatus(resultSet.getString("payment_Status"))
                    .paymentType(VisitType.valueOf(resultSet.getString("Payment_Type")))
                    .build();
            paymentList.add(payment);
        }
        return paymentList;
    }

    public Payment findById(int id) throws Exception {
        preparedStatement = connection.prepareStatement("select * from PAYMENT where payment_Id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Payment payment = null;
        if (resultSet.next()) {
            payment = Payment
                    .builder()
                    .paymentId(resultSet.getInt("payment_Id"))
                    .paymentTime(resultSet.getTimestamp("payment_Time").toLocalDateTime())
                    .paymentStatus(resultSet.getString("payment_Status"))
                    .paymentType(VisitType.valueOf(resultSet.getString("Payment_Type")))
                    .build();
        }
        return payment;
    }





    @Override
    public void close() throws Exception {
        preparedStatement.close();
        connection.close();
    }
}
