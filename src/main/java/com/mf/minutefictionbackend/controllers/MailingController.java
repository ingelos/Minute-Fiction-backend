package com.mf.minutefictionbackend.controllers;

import com.mf.minutefictionbackend.dtos.inputDtos.MailingInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.MailingOutputDto;
import com.mf.minutefictionbackend.services.MailingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/mailings")
public class MailingController {

    private final MailingService mailingService;

    public MailingController(MailingService mailingService) {
        this.mailingService = mailingService;
    }

    @PostMapping
    public ResponseEntity<MailingOutputDto> createMailing(@Valid @RequestBody MailingInputDto mailingInputDto) {
        MailingOutputDto mailing = mailingService.createMailing(mailingInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + mailing.subject).toUriString());
        return ResponseEntity.created(uri).body(mailing);
    }

    @GetMapping("/{mailingId}")
    public ResponseEntity<MailingOutputDto> getMailingById(@Valid@PathVariable("mailingId") Long mailingId) {
        return ResponseEntity.ok().body(mailingService.getMailingById(mailingId));
    }

    @GetMapping
    public ResponseEntity<List<MailingOutputDto>> getAllMailings() {
        return ResponseEntity.ok().body(mailingService.getAllMailings());
    }

    @PutMapping("/{mailingId}")
    public ResponseEntity<MailingOutputDto> updateMailing(@Valid @PathVariable("mailingId") Long mailingId, @RequestBody MailingOutputDto mailingDto) {
        MailingOutputDto updatedMailing = mailingService.updateMailing(mailingId, mailingDto);
        return ResponseEntity.ok().body(updatedMailing);
    }

    @DeleteMapping("/{mailingId}")
    public ResponseEntity<Void> deleteMailingById(@PathVariable("mailingId") Long mailingId) {
        mailingService.deleteMailingById(mailingId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{mailingId}/send")
    public ResponseEntity<Void> sendMailing(@PathVariable Long mailingId) {
        mailingService.sendMailing(mailingId);
        return ResponseEntity.ok().build();
    }

    // sendMailing - authorisation Editor


}
