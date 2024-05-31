package com.issuetracker;

import java.util.TimeZone;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimezoneConfig {

    @Bean
    public TimeZone timeZone() {
        TimeZone defaultTimeZone = TimeZone.getTimeZone("Asia/Seoul");
        TimeZone.setDefault(defaultTimeZone);
        return defaultTimeZone;
    }
}