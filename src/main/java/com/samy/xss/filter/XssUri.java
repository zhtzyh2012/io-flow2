package com.samy.xss.filter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "xss.verify")
@Data
public class XssUri {
    private List<String> uri;
}
