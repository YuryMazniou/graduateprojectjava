package ru.javawebinar.graduateprojectjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.javawebinar.graduateprojectjava.util.ValidationUtil;
import ru.javawebinar.graduateprojectjava.util.exception.ErrorType;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView wrongRequest(HttpServletRequest req, NoHandlerFoundException e) throws Exception {
        return logAndGetExceptionView(req, e, false, ErrorType.WRONG_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        return logAndGetExceptionView(req, e, true, ErrorType.APP_ERROR);
    }

    private ModelAndView logAndGetExceptionView(HttpServletRequest req, Exception e, boolean logException, ErrorType errorType) {
        Throwable rootCause = ValidationUtil.logAndGetRootCause(log, req, e, logException, errorType);

        ModelAndView mav = new ModelAndView("exception/exception");
        mav.addObject("typeMessage", errorType.getErrorCode());
        mav.addObject("exception", rootCause);
        mav.addObject("message", rootCause);
        return mav;
    }
}
