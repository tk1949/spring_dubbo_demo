package org.example.monitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.UUID;

@Aspect
@Component
public class Interceptor {

    private static final Logger              log    = LoggerFactory.getLogger(Interceptor.class);
    private static final ThreadLocal<Long>   time   = new ThreadLocal<>();
    private static final ThreadLocal<String> key    = new ThreadLocal<>();
    private static final ObjectMapper        mapper = new ObjectMapper();

    @Pointcut("@annotation(Monitor)")
    public void controllerMethodPointcut() {
    }

    @Before("controllerMethodPointcut()")
    public void doBefore(JoinPoint point) {
        time.set(System.currentTimeMillis());

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;

        HttpServletRequest request = sra.getRequest();
        HttpSession session = request.getSession(false);
        Enumeration<String> enumeration = request.getHeaderNames();

        StringBuilder headers = new StringBuilder();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            headers.append(name).append(":").append(value).append(",");
        }

        String uri = UUID.randomUUID() + "|" + request.getRequestURI();

        String method = request.getMethod();
        StringBuffer params = new StringBuffer();
        if (HttpMethod.GET.toString().equals(method)) {
            String queryString = request.getQueryString();
            if (queryString != null) {
                params.append(queryString);
            }
        } else {//其他请求
            Object[] paramsArray = point.getArgs();
            if (paramsArray != null && paramsArray.length > 0) {
                for (Object o : paramsArray) {
                    if (o instanceof Serializable) {
                        params.append(o.toString()).append(",");
                    } else {
                        try {
                            String param = mapper.writeValueAsString(o);
                            if (param != null) {
                                params.append(param).append(",");
                            }
                        } catch (JsonProcessingException e) {
                            log.error("doBefore obj to json exception obj={},msg={}", o, e);
                        }
                    }
                }
            }
        }

        key.set(uri);

        log.info("request params uri={}, method={}, params={}, headers={}", uri, method, params, headers);
    }

    @AfterReturning(returning = "obj", pointcut = "controllerMethodPointcut()")
    public void doAfterReturning(Object obj) {

        long costTime = System.currentTimeMillis() - time.get();
        String uri = key.get();
        time.remove();
        key.remove();

        String result = null;
        if (obj instanceof Serializable) {
            result = obj.toString();
        } else {
            if (obj != null) {
                try {
                    result = mapper.writeValueAsString(obj);
                } catch (JsonProcessingException e) {
                    log.error("doAfterReturing obj to json exception obj={},msg={}", obj, e);
                }
            }
        }

        log.info("response result uri={}, result={}, costTime={}ms", uri, result, costTime);
    }

    @AfterThrowing(throwing = "error", pointcut = "controllerMethodPointcut()")
    public void doAfterThrowing(JoinPoint jp, Throwable error) {
        long costTime = System.currentTimeMillis() - time.get();
        String uri = key.get();
        time.remove();
        key.remove();

        log.error("response result uri={}, exception={}, costTime={}ms", uri, error, costTime);
    }
}