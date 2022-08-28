package uz.playground.security.exception;

import org.springframework.http.ResponseEntity;
import uz.playground.security.dto.ResponseData;
import uz.playground.security.dto.ResponseMessage;

import java.util.Objects;

public class CustomException extends RuntimeException{
    private ResponseEntity<?> response;

    public CustomException(String message){
        super(message);
    }

    public CustomException(ResponseEntity<?> response){
        super(Objects.requireNonNull((ResponseData<?>) response.getBody()).getErrorMessage());
        this.response = response;
    }

    public ResponseEntity<?> getResponse(){
        return response;
    }

    public void setResponse(ResponseEntity<ResponseData<ResponseMessage>> response){
        this.response = response;
    }
}
