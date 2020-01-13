package com.regist.nacos;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@NacosPropertySource(dataId = "WARE_ID", autoRefreshed = true)
public class Application7017 {
    public static void main(String[] args) {
        SpringApplication.run(Application7017.class,args) ;
    }
}
