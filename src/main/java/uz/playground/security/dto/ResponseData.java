package uz.playground.security.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseData<T> {
    @JsonProperty("data")
    private T data;
    @JsonProperty("message")
    private String message;
    @JsonProperty("timestamp")
    private long timestamp;

    public ResponseData(T data) {
        this.data = data;
        this.message = "";
        this.timestamp = System.currentTimeMillis();
    }

    public ResponseData(String message) {
        this.message = "";
        this.data = (T) message;
        this.timestamp = System.currentTimeMillis();
    }

    public ResponseData(T data, String message) {
        this.data = data;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }


    public ResponseData() {
        this.message = "";
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResponseEntity<ResponseData<T>> response(T data) {
        return ResponseEntity.ok(new ResponseData<>(data));
    }

    public static <T> ResponseEntity<ResponseData<T>> response(ResponseData<T> responseData, HttpStatus status) {
        return new ResponseEntity<>(responseData, status);
    }

    public static <T> ResponseEntity<ResponseData<T>> response(String errorMessage, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ResponseData<>(null, errorMessage), httpStatus);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String errorMessage) {
        this.message = errorMessage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
