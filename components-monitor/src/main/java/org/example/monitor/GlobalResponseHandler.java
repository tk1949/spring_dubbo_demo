package org.example.monitor;

import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter mp,
                            Class<? extends HttpMessageConverter<?>> clazz) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter mp,
                                  MediaType mt,
                                  Class<? extends HttpMessageConverter<?>> clazz,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if (o == null) {
            return ResultMessage.message_OK;
        }

        if (o instanceof ResultMessage) {
            return o;
        }

        if (o instanceof Resource) {
            return o;
        }

        if (o instanceof String) {
            return o;
        }

        return ResultMessage.buildMessage(o);
    }
}