package com.techolution.controller;

import com.google.common.base.Optional;
import com.techolution.config.SwaggerConfiguration;
import com.techolution.util.GatewayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.UiConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dharmendra Pandit on 6/28/2017.
 */
@Controller
@ApiIgnore
public class ApiResourceController {

    @Autowired
    private SwaggerConfiguration gatewayConfigurations;

    @Autowired
    private GatewayUtils gatewayUtils;

    @Autowired(required = false)
    private SecurityConfiguration securityConfiguration;

    @Autowired(required = false)
    private UiConfiguration uiConfiguration;

    @RequestMapping(value = "/configuration/security")
    @ResponseBody
    public ResponseEntity<SecurityConfiguration> securityConfiguration() {
        return new ResponseEntity<SecurityConfiguration>(
            Optional.fromNullable(securityConfiguration).or(SecurityConfiguration.DEFAULT), HttpStatus.OK);
    }

    @RequestMapping(value = "/configuration/ui")
    @ResponseBody
    public ResponseEntity<UiConfiguration> uiConfiguration() {
        return new ResponseEntity<UiConfiguration>(Optional.fromNullable(uiConfiguration).or(UiConfiguration.DEFAULT),
            HttpStatus.OK);
    }

    @RequestMapping(value = "/swagger-resources")
    @ResponseBody
    ResponseEntity<List<SwaggerResource>> swaggerResources() {
        final List<SwaggerResource> resources = new ArrayList<SwaggerResource>();
        if(gatewayConfigurations.getConfiguration() != null) {
            final List<SwaggerConfiguration.GatewayConfiguration> configurationList = gatewayConfigurations.getConfiguration();
            for(SwaggerConfiguration.GatewayConfiguration configuration : configurationList) {
                resources.add(gatewayUtils.buildSwaggerResource(configuration.getApplicationName(), configuration.getPath(), configuration.getVersion()));
            }
        }
        return new ResponseEntity<List<SwaggerResource>>(resources, HttpStatus.OK);
    }
}
