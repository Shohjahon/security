package uz.playground.security.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import uz.playground.security.constant.MessageKey;
import uz.playground.security.dto.ResponseData;
import uz.playground.security.dto.ResponseMessage;
import uz.playground.security.service.MessageService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResponseHelper {

    private final MessageService messageService;

    public ResponseHelper(MessageService messageService) {
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

    public ResponseEntity<ResponseData<ResponseMessage>> unauthorized() {
        return prepareResponse(messageService.getMessage(MessageKey.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ResponseData<ResponseMessage>> userDoesNotExist() {
        return prepareResponse(messageService.getMessage(MessageKey.USER_DOES_NOT_EXIST), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseData<ResponseMessage>> usernameExists() {
        return prepareResponse(messageService.getMessage(MessageKey.USERNAME_EXISTS), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseData<ResponseMessage>> emailExists() {
        return prepareResponse(messageService.getMessage(MessageKey.EMAIL_EXISTS), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseData<ResponseMessage>> incorrectPassword() {
        return prepareResponse(messageService.getMessage(MessageKey.INCORRECT_PASSWORD));
    }

    public ResponseEntity<ResponseData<ResponseMessage>> success() {
        return prepareResponse(messageService.getMessage(MessageKey.SUCCESS), HttpStatus.OK);
    }

    public ResponseEntity<ResponseData<ResponseMessage>> error() {
        return prepareResponse(messageService.getMessage(MessageKey.ERROR));
    }

    public ResponseEntity<ResponseData<ResponseMessage>> noDataFound() {
        return prepareResponse(messageService.getMessage(MessageKey.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<ResponseData<ResponseMessage>> internalServerError() {
        return prepareResponse(messageService.getMessage(MessageKey.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<ResponseData<ResponseMessage>> invalidData() {
        return prepareResponse(messageService.getMessage(MessageKey.INVALID_DATA));
    }

    private ResponseEntity<ResponseData<ResponseMessage>> prepareResponse(String message, HttpStatus status) {
        ResponseData<ResponseMessage> response;
        if (isSuccessStatus(status)) {
            response = new ResponseData<>(new ResponseMessage(message));
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

    private ResponseEntity<ResponseData<ResponseMessage>> prepareResponse(String message) {
        return prepareResponse(message, HttpStatus.BAD_REQUEST);
    }
}
