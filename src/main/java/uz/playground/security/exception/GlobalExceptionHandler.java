
package uz.playground.security.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.playground.security.constant.Lang;
import uz.playground.security.constant.RoleName;
import uz.playground.security.helper.ResponseHelper;
import uz.playground.security.security.UserPrincipal;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

import static uz.playground.security.helper.SecurityHelper.getUser;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ResponseHelper responseHelper;

    public GlobalExceptionHandler(ResponseHelper responseHelper) {
        this.responseHelper = responseHelper;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(final Exception e,
                                                    final WebRequest webRequest,
                                                    final HttpServletRequest request) {
        logger.error(getStackTrace(e), e.getMessage());
        return responseHelper.internalServerError();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(final Exception e,
                                                    final WebRequest webRequest,
                                                    final HttpServletRequest request) {
        logger.error(getStackTrace(e), e.getMessage());
        return responseHelper.incorrectPassword();
    }

    private String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

}
