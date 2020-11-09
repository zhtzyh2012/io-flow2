package com.samy.xss.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * XSS过滤器
 *
 * @author zhanghongtu
 */
@Slf4j
@WebFilter(filterName = "xssFilter", urlPatterns = "/*")
public class XssFilter implements Filter {
    @Autowired
    private XssUri xssUri;

    @SuppressWarnings("unused")
    private FilterConfig config;

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 是否是被过滤的URL
        boolean isIncludedUrl = false;
        HttpServletRequest req2 = (HttpServletRequest) request;
        String ctx_path = req2.getContextPath();
        String request_uri = req2.getRequestURI();
        String uri = request_uri.substring(ctx_path.length());

        // 判断是否在过滤url内
        if (Objects.nonNull(xssUri)  && Objects.nonNull(xssUri.getUri()) && xssUri.getUri().size() != 0) {
            for (String page : xssUri.getUri()) {
                if (page.equals(uri)) {
                    isIncludedUrl = true;
                    break;
                }
            }
        }

        // 需要XSS校验的地址
        if (isIncludedUrl) {
            chain.doFilter(new XssRequestWrapper(req2), response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        this.config = null;
    }
}
