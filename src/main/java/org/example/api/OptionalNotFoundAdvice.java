package org.example.api;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Optional;

@RestControllerAdvice
public class OptionalNotFoundAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return (returnType.hasMethodAnnotation(NotFoundOnEmpty.class)
                || returnType.hasMethodAnnotation(BadRequestOnEmpty.class))
                && Optional.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response) {

        if (body == null) {
            throw new ResponseStatusException(resolveEmptyStatus(returnType));
        }

        Optional<?> optional = (Optional<?>) body;
        return optional.orElseThrow(() -> new ResponseStatusException(resolveEmptyStatus(returnType)));
    }

    private HttpStatus resolveEmptyStatus(MethodParameter returnType) {
        if (returnType.hasMethodAnnotation(BadRequestOnEmpty.class)) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.NOT_FOUND;
    }
}
