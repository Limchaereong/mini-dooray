package com.example.demo.config;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PageableResolver implements HandlerMethodArgumentResolver {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 5;
    private static final int MAX_SIZE = 10;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Pageable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        int page = parseParameter(webRequest, "page", DEFAULT_PAGE);
        int size = parseParameter(webRequest, "size", DEFAULT_SIZE);

        if (size > MAX_SIZE) {
            size = MAX_SIZE;
        }

        return PageRequest.of(page, size);
    }

    private int parseParameter(NativeWebRequest webRequest, String paramName, int defaultValue) {
        String paramValue = webRequest.getParameter(paramName);
        return (paramValue != null) ? Integer.parseInt(paramValue) : defaultValue;
    }
}
