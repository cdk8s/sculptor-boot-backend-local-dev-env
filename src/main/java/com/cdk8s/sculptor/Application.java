package com.cdk8s.sculptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableScheduling
@tk.mybatis.spring.annotation.MapperScan(basePackages = {"com.cdk8s.sculptor.mapper"})
@EnableAsync
@ServletComponentScan
@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Value("${server.port:9091}")
	private String serverPort;

	@Value("${server.servlet.context-path:/sculptor-boot-backend}")
	private String serverContextPath;

	@Value("${management.server.servlet.context-path:/sculptor-boot-backend-actuator}")
	private String managementContextPath;

	@Value("${management.server.port:19091}")
	private String managementPort;

	@Value("${spring.h2.console.path:/abc}")
	private String h2ConsolePath;

	//=================================================================================

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... strings) {
		log.info("=================================Application Startup Success=================================");
		log.info("index >> http://sculptor.cdk8s.com:{}{}", serverPort, serverContextPath);
		log.info("json >> http://sculptor.cdk8s.com:{}{}/api/sysUser/detail?id=111111111111111111", serverPort, serverContextPath);
		log.info("H2 Console >> http://sculptor.cdk8s.com:{}{}{}", serverPort, serverContextPath, h2ConsolePath);
		log.info("druid >> http://sculptor.cdk8s.com:{}{}/druid", serverPort, serverContextPath);
		log.info("swagger >> http://sculptor.cdk8s.com:{}{}/swagger-ui.html", serverPort, serverContextPath);
		log.info("actuator-health >> http://sculptor.cdk8s.com:{}{}/actuator/health", managementPort, managementContextPath);
		log.info("actuator-prometheus >> http://sculptor.cdk8s.com:{}{}/actuator/prometheus", managementPort, managementContextPath);
		log.info("=================================Application Startup Success=================================");
	}


}

