package uz.playground.security.helper;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import uz.playground.security.constant.MessageKey;
import uz.playground.security.dto.ResponseData;
import uz.playground.security.dto.ResponseDto;
import uz.playground.security.service.MessageService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResponseHelper {

    private final MessageService messageService;

    public ResponseHelper(@Lazy MessageService messageService) {
        this.messageService = messageService;
    }


    static public boolean isSuccessStatus(HttpStatus status) {
        List<HttpStatus> successStatuses = new ArrayList<>() {{
            add(HttpStatus.OK);
            add(HttpStatus.ACCEPTED);
            add(HttpStatus.CREATED);
            add(HttpStatus.RESET_CONTENT);
        }};

        return successStatuses.contains(status);
    }

    public ResponseEntity<ResponseData<ResponseDto>> unauthorized() {
        return prepareResponse(messageService.getMessage(MessageKey.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ResponseData<ResponseDto>> userDoesNotExist() {
        return prepareResponse(messageService.getMessage(MessageKey.USER_DOES_NOT_EXIST), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseData<ResponseDto>> usernameExists() {
        return prepareResponse(messageService.getMessage(MessageKey.USERNAME_EXISTS), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseData<ResponseDto>> emailExists() {
        return prepareResponse(messageService.getMessage(MessageKey.EMAIL_EXISTS), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseData<ResponseDto>> incorrectPassword() {
        return prepareResponse(messageService.getMessage(MessageKey.INCORRECT_PASSWORD));
    }

    public ResponseEntity<ResponseData<ResponseDto>> success() {
        return prepareResponse(messageService.getMessage(MessageKey.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<ResponseData<ResponseDto>> error() {
        return prepareResponse(messageService.getMessage(MessageKey.ERROR));
    }

    public ResponseEntity<ResponseData<ResponseDto>> noDataFound() {
        return prepareResponse(messageService.getMessage(MessageKey.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<ResponseData<ResponseDto>> internalServerError() {
        return prepareResponse(messageService.getMessage(MessageKey.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<ResponseData<ResponseDto>> invalidData() {
        return prepareResponse(messageService.getMessage(MessageKey.INVALID_DATA));
    }

    private ResponseEntity<ResponseData<ResponseDto>> prepareResponse(String message, HttpStatus status) {
        ResponseData<ResponseDto> response;
        if (isSuccessStatus(status)) {
            response = new ResponseData<>(new ResponseDto(message));
        } else {
            response = new ResponseData<>(null, message);
        }
        return new ResponseEntity<>(response, status);
    }

    public ResponseEntity<?> prepareResponse(Object object) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", object);
        result.put("message", messageService.getMessage(MessageKey.SUCCESS));
        result.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public ResponseEntity<Object> prepareValidationResponse(Object object) {
        Map<String, Object> result = new HashMap<>();
        result.put("data", object);
        result.put("message", messageService.getMessage(MessageKey.INVALID_DATA));
        result.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ResponseData<ResponseDto>> prepareResponse(String message) {
        return prepareResponse(message, HttpStatus.BAD_REQUEST);
    }
}
