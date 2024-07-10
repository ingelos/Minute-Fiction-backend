package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.services.MailingService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailingController {

    private final MailingService mailingService;

    public MailingController(MailingService mailingService) {
        this.mailingService = mailingService;
    }



}
