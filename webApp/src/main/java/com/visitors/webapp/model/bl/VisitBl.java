package com.visitors.webapp.model.bl;



import lombok.Getter;
import com.visitors.webapp.model.controller.exceptions.NoVisitFoundException;
import com.visitors.webapp.model.da.VisitDa;
import com.visitors.webapp.model.entity.Visit;
import com.visitors.webapp.model.tools.CRUD;

import java.util.Collections;
import java.util.List;

public class VisitBl implements CRUD<Visit> {

    @Getter
    private static VisitBl visitBl = new VisitBl();

    public VisitBl(){

    }

    @Override
    public Visit save(Visit visit) throws Exception {
       try(VisitDa visitDa = new VisitDa()){
           visitDa.save(visit);
           return visit;
       }
    }

    @Override
    public Visit edit(Visit visit) throws Exception {
       try(VisitDa visitDa = new VisitDa()){
           if(visitDa.findById(visit.getId()) != null){
               visitDa.edit(visit);
               return visit;
           }else {
               throw new NoVisitFoundException();
           }
       }
    }

    @Override
    public Visit remove(int id) throws Exception {
        try(VisitDa visitDa = new VisitDa()){
            Visit visit = visitDa.findById(id);
            if(visit != null){
                visitDa.remove(id);
                return visit;
            }else {
                throw new NoVisitFoundException();
            }
        }
    }

    @Override
    public List<Visit> findAll() throws Exception {
       try(VisitDa visitDa = new VisitDa()){
           List<Visit> visits = visitDa.findAll();
           if(!visits.isEmpty()){
               return visits;
           }
           else {
               throw new NoVisitFoundException();
           }
       }
    }

    @Override
    public Visit findById(int id) throws Exception {
        try(VisitDa visitDa = new VisitDa()){
            Visit visit = visitDa.findById(id);
            if(visit != null){
                return visit;
            }else{
                throw new NoVisitFoundException();
            }
        }
    }
}
