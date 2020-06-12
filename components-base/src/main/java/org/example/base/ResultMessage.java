package org.example.base;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultMessage<E> {

    public static final int code_OK    = 200;
    public static final int code_ERROR = 500;

    public static final String _OK    = "Okay";
    public static final String _ERROR = "Server exception";

    public static final ResultMessage<String> message_OK    = ResultMessage.buildMessage(_OK);
    public static final ResultMessage<String> message_ERROR = ResultMessage.buildErrorMessage(_ERROR);

    private int code;
    private String message;
    private E data;

    public static <E> ResultMessage<E> buildMessage(E data) {
        ResultMessage<E> message = new ResultMessage<>();
        message.code    = code_OK;
        message.message = _OK;
        message.data    = data;
        return message;
    }

    public static <E> ResultMessage<E> buildMessage(String _message, E _data) {
        ResultMessage<E> message = new ResultMessage<>();
        message.code    = code_OK;
        message.message = _message;
        message.data    = _data;
        return message;
    }

    public static <E> ResultMessage<E> buildMessage(int _code, String _message, E _data) {
        ResultMessage<E> message = new ResultMessage<>();
        message.code    = _code;
        message.message = _message;
        message.data    = _data;
        return message;
    }

    public static ResultMessage<String> buildErrorMessage(String _message) {
        ResultMessage<String> message = new ResultMessage<>();
        message.code    = code_ERROR;
        message.message = _message;
        return message;
    }

    public static ResultMessage<String> buildErrorMessage(int _code, String _message) {
        ResultMessage<String> message = new ResultMessage<>();
        message.code    = _code;
        message.message = _message;
        return message;
    }
}