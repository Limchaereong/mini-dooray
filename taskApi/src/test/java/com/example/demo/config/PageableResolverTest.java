package com.example.demo.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PageableResolverTest {

    private PageableResolver pageableResolver;

    @Mock
    private MethodParameter methodParameter;

    @Mock
    private ModelAndViewContainer mavContainer;

    @Mock
    private WebDataBinderFactory binderFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pageableResolver = new PageableResolver();
    }

    @Test
    void supportsParameter_ShouldReturnTrue_ForPageableParameter() {
        when(methodParameter.getParameterType()).thenReturn((Class)Pageable.class);

        boolean supports = pageableResolver.supportsParameter(methodParameter);

        assertTrue(supports);
    }

    @Test
    void supportsParameter_ShouldReturnFalse_ForNonPageableParameter() {
        when(methodParameter.getParameterType()).thenReturn((Class) String.class);

        boolean supports = pageableResolver.supportsParameter(methodParameter);

        assertTrue(!supports);
    }


    @Test
    void resolveArgument_ShouldReturnDefaultPageable_WhenNoParametersProvided() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        NativeWebRequest webRequest = new ServletWebRequest(request);

        Pageable pageable = (Pageable) pageableResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

        assertEquals(PageRequest.of(0, 5), pageable);
    }

    @Test
    void resolveArgument_ShouldReturnCustomPageable_WhenParametersProvided() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("page", "2");
        request.setParameter("size", "7");
        NativeWebRequest webRequest = new ServletWebRequest(request);

        Pageable pageable = (Pageable) pageableResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

        assertEquals(PageRequest.of(2, 7), pageable);
    }

    @Test
    void resolveArgument_ShouldCapPageSizeAtMax_WhenSizeExceedsMax() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("size", "15");
        NativeWebRequest webRequest = new ServletWebRequest(request);

        Pageable pageable = (Pageable) pageableResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

        assertEquals(PageRequest.of(0, 10), pageable);
    }
}

