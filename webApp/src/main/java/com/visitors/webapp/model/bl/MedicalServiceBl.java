package com.visitors.webapp.model.bl;


import com.visitors.webapp.model.controller.exceptions.NoMedicalFoundException;
import com.visitors.webapp.model.da.MedicalServiceDa;
import com.visitors.webapp.model.entity.MedicalService;
import com.visitors.webapp.model.tools.CRUD;
import lombok.Getter;

import java.util.List;

public class MedicalServiceBl implements CRUD<MedicalService> {

    @Getter
    private static MedicalServiceBl medicalServiceBl = new MedicalServiceBl();

    private MedicalServiceBl() {

    }

    @Override
    public MedicalService save(MedicalService medicalService) throws Exception {
            try(MedicalServiceDa medicalServiceDa = new MedicalServiceDa()){
                medicalServiceDa.save(medicalService);
                return medicalService;
            }
        }

        @Override
        public MedicalService edit (MedicalService medicalService) throws Exception {
            try(MedicalServiceDa medicalServiceDa = new MedicalServiceDa()){
                if(medicalServiceDa.findById(medicalService.getServiceId()) != null){
                    medicalServiceDa.edit(medicalService);
                    return medicalService;
                }
                else{
                    throw new NoMedicalFoundException();
                }
            }
        }

        @Override
        public MedicalService remove ( int id) throws Exception {
            try(MedicalServiceDa medicalServiceDa = new MedicalServiceDa()){
                MedicalService medicalService =  medicalServiceDa.findById(id);
                if(medicalService != null){
                    medicalServiceDa.remove(id);
                    return medicalService;
                }
                else{
                    throw new NoMedicalFoundException();
                }
            }
        }

        @Override
        public List<MedicalService> findAll () throws Exception {
            try(MedicalServiceDa medicalServiceDa = new MedicalServiceDa()){
                List<MedicalService> medicalServices = medicalServiceDa.findAll();
                if(!medicalServices.isEmpty()){
                    return medicalServices;
                }else{
                    throw new NoMedicalFoundException();
                }
            }
        }

        @Override
        public MedicalService findById ( int id) throws Exception {
            try(MedicalServiceDa medicalServiceDa = new MedicalServiceDa()){
                MedicalService medicalService = medicalServiceDa.findById(id);
                if(medicalService != null){
                    return medicalService;
                }
                else{
                    throw new NoMedicalFoundException();
                }
            }
        }

        public List<MedicalService> findByServiceName(String serviceName) throws Exception {
            try(MedicalServiceDa medicalServiceDa = new MedicalServiceDa()){
                List<MedicalService> medicalServices = medicalServiceDa.findByServiceName(serviceName);
                if(!medicalServices.isEmpty()){
                    return medicalServices;
                }
                else {
                    throw new NoMedicalFoundException();
                }
            }

        }

    public List<MedicalService> findByServiceType(String serviceType) throws Exception {
        try(MedicalServiceDa medicalServiceDa = new MedicalServiceDa()){
            List<MedicalService> medicalServices = medicalServiceDa.findByServiceType(serviceType);
            if(!medicalServices.isEmpty()){
                return medicalServices;
            }
            else {
                throw new NoMedicalFoundException();
            }
        }

    }


    }
