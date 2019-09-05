package com.zlll.winner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.Calendar;

@SpringBootApplication
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=43200)
public class SnailBizWebApplication {

    public static void main(String[] args) {
        long startTime = Calendar.getInstance().getTimeInMillis();
        SpringApplication.run(SnailBizWebApplication.class, args);
        long endTime = (Calendar.getInstance().getTimeInMillis() - startTime) /1000;
        System.out.println("########## SNAIL-BIZ-WEB STARTUP:"+endTime+"s ##########");
    }

}
