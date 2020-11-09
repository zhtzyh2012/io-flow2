package com.samy.xss.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Iterator;
import java.util.Map;

public class XssRequestWrapper extends HttpServletRequestWrapper {
    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> request_map = super.getParameterMap();
        Iterator iterator = request_map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry me = (Map.Entry) iterator.next();
            String[] values = (String[]) me.getValue();
            for (int i = 0; i < values.length; i++) {
                System.out.println(values[i]);
                values[i] = xssClean(values[i]);
            }
        }
        return request_map;
    }

    @Override
    public String[] getParameterValues(String paramString) {
        String[] arrayOfString1 = super.getParameterValues(paramString);
        if (arrayOfString1 == null)
            return null;
        int i = arrayOfString1.length;
        String[] arrayOfString2 = new String[i];
        for (int j = 0; j < i; j++)
            arrayOfString2[j] = xssClean(arrayOfString1[j]);
        return arrayOfString2;
    }

    @Override
    public String getParameter(String paramString) {
        String str = super.getParameter(paramString);
        return str == null ? null : xssClean(str);
    }

    @Override
    public String getHeader(String paramString) {
        String str = super.getHeader(paramString);
        return str == null ? null : xssClean(str);
    }

    private String xssClean(String value) {
        return XssClean.xssClean(value);
    }

}
