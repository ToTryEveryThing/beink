package com.example.demo.config.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author 睡醒继续做梦
 * @date 2023/2/16
 */

public class XSSFilter implements Filter {
    private static final Pattern SCRIPT_PATTERN = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
    private static final Pattern SRC_PATTERN = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern SRC_PATTERN2 = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern EVAL_PATTERN = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    private static final Pattern JAVASCRIPT_PATTERN = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        chain.doFilter(new XSSRequestWrapper(httpServletRequest), response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化
    }

    @Override
    public void destroy() {
        // 销毁
    }

    private static class XSSRequestWrapper extends HttpServletRequestWrapper {

        public XSSRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String[] getParameterValues(String parameter) {
            String[] values = super.getParameterValues(parameter);
            if (values == null) {
                return null;
            }
            int count = values.length;
            String[] encodedValues = new String[count];
            for (int i = 0; i < count; i++) {
                System.out.println(encodedValues[i]);
                encodedValues[i] = stripXSS(values[i]);
            }
            return encodedValues;
        }

        @Override
        public String getParameter(String parameter) {
            String value = super.getParameter(parameter);
            return stripXSS(value);
        }

        @Override
        public String getHeader(String name) {
            String value = super.getHeader(name);
            return stripXSS(value);
        }

        private String stripXSS(String value) {
            if (value != null) {
                value = SCRIPT_PATTERN.matcher(value).replaceAll("");
                value = SRC_PATTERN.matcher(value).replaceAll("");
                value = SRC_PATTERN2.matcher(value).replaceAll("");
                value = EVAL_PATTERN.matcher(value).replaceAll("");
                value = EXPRESSION_PATTERN.matcher(value).replaceAll("");
                value = JAVASCRIPT_PATTERN.matcher(value).replaceAll("");
            }
            return value;
        }
    }
}
