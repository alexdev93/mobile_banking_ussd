package com.gebeya.pro.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

    @Bean
    public WebClient webClientForTopUp(WebClient.Builder webClientBuilder)
    {
        return webClientBuilder.baseUrl("https://meinab.com/mtelecom").build();
    }
}
