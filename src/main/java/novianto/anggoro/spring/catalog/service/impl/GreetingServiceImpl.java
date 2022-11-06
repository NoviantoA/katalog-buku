package novianto.anggoro.spring.catalog.service.impl;

import novianto.anggoro.spring.catalog.config.ApplicationProperties;
import novianto.anggoro.spring.catalog.config.CloudProperties;
import novianto.anggoro.spring.catalog.service.GreetingService;
import novianto.anggoro.spring.catalog.web.HelloResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.TimeZone;

@Service
public class GreetingServiceImpl implements GreetingService {
//    @Value("${welcome.text}")
//    private String welcomeText;
//
//    @Value("${timezone}")
//    private String timezone;
//
//    @Value("${currency}")
//    private String currency;

    Logger log = LoggerFactory.getLogger(GreetingServiceImpl.class);


    private ApplicationProperties appProperties;
    private CloudProperties cloudProperties;

    public GreetingServiceImpl(ApplicationProperties appProperties, CloudProperties cloudProperties) {
        this.appProperties = appProperties;
        this.cloudProperties = cloudProperties;
    }

    @Override
    public String sayGreeting() {
        log.trace("this is log trace");
        log.debug("this is log debug");
        log.info("this is log info");
        log.warn("this is log warn");
        log.error("this is log error");
        System.out.println(cloudProperties.getApiKey());
        TimeZone timeZoneone = TimeZone.getTimeZone(appProperties.getTimezone());
        return appProperties.getWelcomeText() + ", our timezone = " + timeZoneone.getDisplayName()
                + ", our currency = " + appProperties.getCurrency();
    }
}
