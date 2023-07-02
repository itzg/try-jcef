package me.itzg.tryjcef;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TryJcefApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TryJcefApplication.class)
            .headless(false)
            .run(args);
    }

}
