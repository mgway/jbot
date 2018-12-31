package io.esb.jbot.boot;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "io.esb.jbot")
@EnableJms
public class Config {

}
