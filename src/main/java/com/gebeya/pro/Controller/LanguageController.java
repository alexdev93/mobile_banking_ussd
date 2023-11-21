package com.gebeya.pro.Controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@RestController
public class LanguageController {

    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.US);
        return sessionLocaleResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource(){
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("messages");
        resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
        return resourceBundleMessageSource;

    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getSource(@RequestHeader(name = "Accept-Language", required = false) String locale) {
        if (locale == null) {
            // If the "Accept-Language" header is not present, you can set a default locale or handle it as needed.
            locale = "en"; // Default to English if the header is not provided.
        }
        return messageSource().getMessage("hello.txt", null, new Locale(locale));
    }
}
