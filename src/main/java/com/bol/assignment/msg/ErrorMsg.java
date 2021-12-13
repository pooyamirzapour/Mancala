package com.bol.assignment.msg;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.io.Serializable;

/**
 * error message class
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

@JsonPropertyOrder({"errorCode", "errorMessage"})
@Data
public class ErrorMsg implements Serializable {

    private int errorCode;
    private String errorMessage;

    public ErrorMsg(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
