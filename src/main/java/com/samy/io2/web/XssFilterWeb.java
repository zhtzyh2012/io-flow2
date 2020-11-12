package com.samy.io2.web;

import com.samy.io2.entity.Text;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
public class XssFilterWeb {

    @RequestMapping(value = "/verify/xss", method = POST)
    public String verifyXss(@RequestBody Text xss) {
        log.info("res is {}", xss.getText());
        return "okay";
    }
}
