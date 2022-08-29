
package uz.playground.security.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.playground.security.dto.ValidationDto;
import uz.playground.security.helper.ResponseHelper;
import uz.playground.security.service.MessageService;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ResponseHelper responseHelper;
    private final MessageService messageService;

    public GlobalExceptionHandler(ResponseHelper responseHelper,
                                  MessageService messageService) {
        this.responseHelper = responseHelper;
        this.messageService = messageService;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(final Exception e,
                                                    final WebRequest webRequest,
                                                    final HttpServletRequest request) {
        logger.error(getStackTrace(e), e.getMessage());
        return responseHelper.internalServerError();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(final BadCredentialsException e,
                                                    final WebRequest webRequest,
                                                    final HttpServletRequest request) {
        logger.error(getStackTrace(e), e.getMessage());
        return responseHelper.incorrectPassword();
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(final CustomException e,
                                                   final WebRequest webRequest){
        return e.getResponse();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<ValidationDto> validation = new CopyOnWriteArrayList<>();
        e.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    var message = messageService.getMessage(error.getDefaultMessage());
                    var field = error.getField();
                    validation.add(new ValidationDto(field, message));
                });
        return responseHelper.prepareValidationResponse(validation);
    }

    private String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

}
