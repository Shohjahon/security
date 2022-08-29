package uz.playground.security.dto;

public class ResponseDto {
    private String message;

    public ResponseDto() {
        this.message = "";
    }

    public ResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static ResponseDto message(final String message) {
        return new ResponseDto(message);
    }
}
