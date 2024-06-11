package com.visitors.webapp.controller;

import com.visitors.webapp.model.bl.MedicalServiceBl;
import com.visitors.webapp.model.da.MedicalServiceDa;
import com.visitors.webapp.model.entity.MedicalService;

import static com.visitors.webapp.model.tools.Validator.checkNullValidator;

public class MedicalServiceController {
    public static String save(String serviceName, String serviceDescription, String serviceType, boolean serviceStatus) {
        try {
            MedicalService medicalService = new MedicalService();

            medicalService.setServiceName(checkNullValidator(serviceName, "serviceName cannot be null"));
            medicalService.setServiceDescription(checkNullValidator(serviceDescription, "serviceDescription cannot be null"));
            medicalService.setServiceType(serviceType);
            medicalService.setServiceStatus(serviceStatus);
            MedicalServiceBl.getMedicalServiceBl().save(medicalService);
            return "Medical Service saved successfully";

        } catch (Exception e) {
            return e.getMessage();
        }


    }
}
