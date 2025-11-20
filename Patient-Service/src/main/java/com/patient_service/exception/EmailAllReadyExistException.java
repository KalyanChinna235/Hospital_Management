package com.patient_service.exception;

public class EmailAllReadyExistException extends RuntimeException{

   public EmailAllReadyExistException(String message){
       super(message);
   }
}
