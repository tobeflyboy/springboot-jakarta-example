package com.nutcracker.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.context.WebApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Start Application
 *
 * @author 胡桃夹子
 * @date 2022/1/29 11:01
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.nutcracker.example.demo")
@EnableAsync
@EnableCaching
@ServletComponentScan(basePackages = "com.nutcracker.example.demo")
public class JwtDemoStarter {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JwtDemoStarter.class, args);
        printApplicationInfo(context);
    }

    private static void printApplicationInfo(ApplicationContext context) {
        StringJoiner joiner = new StringJoiner("\n\t", "\n\n\t", "\n\t");
        // 获取 SpringBoot 的版本号
        joiner.add("SpringBoot Version: " + SpringApplication.class.getPackage().getImplementationVersion());
        // 获取当前应用的版本号
        String version = JwtDemoStarter.class.getPackage().getImplementationVersion();
        if (version != null) {
            joiner.add("Application Version: " + version);
        }
        // 获取本地 IP 地址
        String hostAddress;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            hostAddress = null;
        }
        String app = "";
        if (!Objects.isNull(hostAddress) && context instanceof WebApplicationContext) {
            Environment env = context.getEnvironment();
            String contextPath = env.getProperty("server.servlet.context-path");
            joiner.add("Doc URL: http://" + hostAddress + ":" + env.getProperty("server.port", Integer.class, 8080) + contextPath + "/doc.html");
            app = Objects.isNull(contextPath) ? "" : contextPath.replace("/", "") + " ";
        }
        joiner.add(app + "Start Successful");
        log.info(joiner.toString());
    }
}