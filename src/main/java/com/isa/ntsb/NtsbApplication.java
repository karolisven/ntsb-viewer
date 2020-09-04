package com.isa.ntsb;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan( basePackages = {"com.isa.ntsb.config, com.isa.ntsb.swagger, com.isa.ntsb.security, com.isa.ntsb.service, com.isa.ntsb.controllers, com.isa.ntsb.persistence, com.isa.ntsb.model"},
		excludeFilters = @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class))
@SpringBootApplication
public class NtsbApplication {
    private static final Logger logger = LoggerFactory.getLogger(NtsbApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(NtsbApplication.class, args);
	}
}
