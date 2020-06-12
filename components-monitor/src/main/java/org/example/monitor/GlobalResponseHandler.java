package org.example.monitor;

import org.example.base.ResultMessage;
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

        // 对于文件上传/下载对象直接返回
        if (o instanceof Resource) {
            return o;
        }

        // ******************* 这个可能是 Spring 架构设计 bug *******************
        // 对于调用函数的返回值类型为 String 的请求直接使用 StringHttpMessageConverter
        // 所以为了统一返回数据格式必须使用 ResultMessage.buildMessage(o).toString()
        // 否则将会发生 ClassCastException
        // ResultMessage.toString 已重写为 toJSON
        if (o instanceof String) {
            return ResultMessage.buildMessage(o).toString();
        }

        return ResultMessage.buildMessage(o);
    }
}