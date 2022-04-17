package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class Zookeeper03RegisterProductCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(Zookeeper03RegisterProductCenterApplication.class, args);
    }

    @Value("${server.port}")
    private String port;

    @Value( "${spring.application.name}" )
    private String name;

    @RequestMapping("/getInfo")
    public String getServerPortAndName(){

        return  this.name +" : "+ this.port;
    }

}
