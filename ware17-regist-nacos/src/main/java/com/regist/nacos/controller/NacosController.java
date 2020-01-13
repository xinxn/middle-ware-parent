package com.regist.nacos.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Api("Nacos接口管理")
@RestController
@RequestMapping("/nacos")
public class NacosController {

    @NacosValue(value = "${MyName:null}", autoRefreshed = true)
    private String myName;

    @NacosValue(value = "${project:null}", autoRefreshed = true)
    private String project;

    @ApiOperation(value="查询配置信息")
    @GetMapping(value = "/info")
    public String info () {
        return myName+":"+project;
    }

    @NacosInjected
    private NamingService namingService;

    @ApiOperation(value="查询服务列表")
    @GetMapping(value = "/getServerList")
    public List<Instance> getServerList (@RequestParam String serviceName) {
        try {
            return namingService.getAllInstances(serviceName) ;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null ;
    }
}
