package com.example.demo.config.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@Component
@Order(1)
public class SqlFilter extends OncePerRequestFilter {

    private static final Pattern SQL_PATTERN = Pattern.compile("('.+--)|(--)|(\\|)|(%7C)", Pattern.CASE_INSENSITIVE);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(new SqlInjectionSafeRequest(request), response);
    }

    /**
     * SQL 注入过滤请求包装类
     */
    static class SqlInjectionSafeRequest extends HttpServletRequestWrapper {

        public SqlInjectionSafeRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String name) {
            String value = super.getParameter(name);
            if (value != null && SQL_PATTERN.matcher(value).find()) {
                throw new IllegalArgumentException("Invalid request parameter: " + name);
            }
            return value;
        }

    }

}
