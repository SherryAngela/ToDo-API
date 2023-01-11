package com.sherryagustin.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class TodoException extends Exception{

    private static final long serialVersionUID = 1L;

    public static final String INVALID_TASKNAME = "ERROR : Invalid Taskname - Taskname is null";
    public static final String SHORT_TASKNAME = "ERROR : Invalid Taskname - Taskname is too short";
    public static final String LONG_TASKNAME = "ERROR : Invalid Taskname - Taskname is too long";
    public static final String INVALID_COMMENT = "ERROR : Invalid Comment - Taskname is too null";
    public static final String LONG_COMMENT = "ERROR : Invalid Comment - Taskname is too long";






    public TodoException(String message) {
        super(message, null, false, false);
    }

}
