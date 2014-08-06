package boodater.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class BaseController {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public final static String ERROR_VIEW = "error";

    @ExceptionHandler
    public String handleException(Exception e) {
        log.error("Controller error", e);
        return ERROR_VIEW;
    }
}
