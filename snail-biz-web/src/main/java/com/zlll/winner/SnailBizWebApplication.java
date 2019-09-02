package com.zlll.winner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;

@SpringBootApplication
public class SnailBizWebApplication {

    public static void main(String[] args) {
        long startTime = Calendar.getInstance().getTimeInMillis();
        SpringApplication.run(SnailBizWebApplication.class, args);
        long endTime = (Calendar.getInstance().getTimeInMillis() - startTime) /1000;
        System.out.println("########## SNAIL-BIZ-WEB STARTUP:"+endTime+"s ##########");
    }

}
