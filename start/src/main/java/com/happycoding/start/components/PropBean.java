package com.happycoding.start.components;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties("my")
@Component
@Data
public class PropBean {
    private Integer number;

    private Inner inner = new Inner();

    Map<Object,Object> map = new HashMap<>();

    List<Object> list = new ArrayList<>();

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration sessionTimeout = Duration.ofDays(1);

    public static class Inner{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
