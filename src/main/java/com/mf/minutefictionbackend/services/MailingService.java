package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.repositories.MailingRepository;
import org.springframework.stereotype.Service;

@Service
public class MailingService {

    private final MailingRepository mailingRepository;

    public MailingService(MailingRepository mailingRepository) {
        this.mailingRepository = mailingRepository;
    }
}
