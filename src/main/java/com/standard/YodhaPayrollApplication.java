package com.standard;

import com.standard.util.annotations.Ownership;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Ownership(owner = "Bilal Mirje")
@RestController
@CrossOrigin("*")
@SpringBootApplication
public class YodhaPayrollApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(YodhaPayrollApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(YodhaPayrollApplication.class, args);
    }

    @GetMapping("/test")
    public String test() {
        return "YodhaPayroll API is running successfully!";
    }
}
