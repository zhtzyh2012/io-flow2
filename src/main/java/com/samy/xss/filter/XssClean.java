package com.samy.xss.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.owasp.validator.html.*;

import java.io.InputStream;

@Slf4j
public class XssClean {
    /**
     * XSS验证的策略
     */
    private static Policy policy = null;

    /**
     * 获取配置文件中的校验策略
     *
     * @return
     * @throws PolicyException
     */
    static {
        if (policy == null) {
            InputStream policyFile = XssClean.class.getResourceAsStream("/antisamy/antisamy-myspace-1.4.4.xml");
            try {
                policy = Policy.getInstance(policyFile);
            } catch (PolicyException e) {
                log.warn("XssFilter - static policy instance error. PolicyException=" , e);
            }
        }
    }

    /**
     * 根据策略规则进行数据清理工作
     *
     * @param source
     * @return
     */
    public static String xssClean(String source) {
        if (StringUtils.isNotEmpty(source)) {
            AntiSamy antiSamy = new AntiSamy();
            try {
                final CleanResults cr = antiSamy.scan(source, policy);
                log.info("before:{}", source);
                //安全的HTML输出
                source = cr.getCleanHTML();
                log.info("after: {}", source);
            } catch (ScanException e) {
                log.error("过滤XSS异常");
                //e.printStackTrace();
            } catch (PolicyException e) {
                log.error("加载XSS规则文件异常: " + e.getMessage());
                throw new RuntimeException("加载XSS规则文件异常: " + e.getMessage());
                //e.printStackTrace();
            }
        }

        // fixme 需要在此处来确认校验规则失败,根据清洗结果不允许保存数据.

        return source;
    }

}
