package com.samy.io2.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * XSS过滤器
 *
 * @author zhanghongtu
 */
@Slf4j
@WebFilter(filterName = "xssFilter", urlPatterns = "/*")
public class XssFilter implements Filter {
    @SuppressWarnings("unused")
    private FilterConfig config;

    @Override
    public void init(FilterConfig config) {
        this.config = config;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // 1.当前设置主要是允许多次读取IO流
        HttpServletRequest req2 = (HttpServletRequest) request;
        XssRequestWrapper wrapper = new XssRequestWrapper(req2);

        // 2.获取body中的数据进行业务操作
        String body = wrapper.getBodyString();
        log.info("request body is {}", body);

        chain.doFilter(wrapper, response);
    }

    @Override
    public void destroy() {
        this.config = null;
    }
}
