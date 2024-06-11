package com.visitors.webapp.model.bl;

import lombok.Getter;
import com.visitors.webapp.model.controller.exceptions.NoPaymentFoundException;
import com.visitors.webapp.model.da.PaymentDa;
import com.visitors.webapp.model.entity.Payment;
import com.visitors.webapp.model.tools.CRUD;

import java.util.Collections;
import java.util.List;

public class PaymentBl implements CRUD<Payment> {

    @Getter
    private static PaymentBl paymentBl = new PaymentBl();

    private PaymentBl(){

    }

    @Override
    public Payment save(Payment payment) throws Exception {
        try(PaymentDa paymentDa = new PaymentDa();){
            paymentDa.save(payment);
            return payment;
        }
    }

    @Override
    public Payment edit(Payment payment) throws Exception {
        try(PaymentDa paymentDa = new PaymentDa();){
           if(paymentDa.findById(payment.getPaymentId()) != null){
               paymentDa.edit(payment);
               return payment;
           }else{
               throw new NoPaymentFoundException();
           }
        }
    }

    @Override
    public Payment remove(int id) throws Exception {
       try(PaymentDa paymentDa = new PaymentDa();){
           Payment payment = paymentDa.findById(id);
           if(payment != null){
               paymentDa.remove(id);
               return payment;
           }
           else{
               throw new NoPaymentFoundException();
           }
       }

    }

    @Override
    public List<Payment> findAll() throws Exception {
        try(PaymentDa paymentDa = new PaymentDa();){
            List<Payment> payments = paymentDa.findAll();
            if(!payments.isEmpty()){
                    return payments;
            }else{
                throw new NoPaymentFoundException();
            }

        }
    }

    @Override
    public Payment findById(int id) throws Exception {
        try(PaymentDa paymentDa = new PaymentDa();){
           Payment payment = paymentDa.findById(id);
            if(payment != null){
                return payment;
            }
            else{
                throw new NoPaymentFoundException();
            }
        }
    }
}
