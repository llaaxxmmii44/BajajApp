package com.bajaj.health;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/*
 Minimal startup runner: triggers the webhook flow on application start.
 */
@Component
public class StartupRunner implements CommandLineRunner {

    private final WebhookService webhookService;

    public StartupRunner(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @Override
    public void run(String... args) throws Exception {
        webhookService.executeFlow();
    }
}
