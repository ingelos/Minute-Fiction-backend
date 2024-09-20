package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.MailingInputDto;
import com.mf.minutefictionbackend.dtos.mappers.MailingMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.MailingOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.Mailing;

import com.mf.minutefictionbackend.models.User;
import com.mf.minutefictionbackend.repositories.MailingRepository;
import com.mf.minutefictionbackend.repositories.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MailingService {

    private final MailingRepository mailingRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    @Value("${app.mail.sending.enabled}")
    private boolean isMailSendingEnabled;


    public MailingService(MailingRepository mailingRepository, UserRepository userRepository, JavaMailSender mailSender) {
        this.mailingRepository = mailingRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }


    public MailingOutputDto createMailing(MailingInputDto mailingInputDto) {
        Mailing mailing = mailingRepository.save(MailingMapper.mailingFromInputDtoToModel(mailingInputDto));
        return MailingMapper.mailingFromModelToOutputDto(mailing);
    }

    public MailingOutputDto getMailingById(Long mailingId) {
        Mailing mailing = mailingRepository.findById(mailingId)
                .orElseThrow(() -> new ResourceNotFoundException("No mailing found with id " + mailingId));
        return MailingMapper.mailingFromModelToOutputDto(mailing);
    }

    public List<MailingOutputDto> getAllMailings() {
        List<Mailing> allMailings = mailingRepository.findAll();
        return MailingMapper.mailingFromModelListToOutputList(allMailings);
    }

    public MailingOutputDto updateMailing(Long mailingId, MailingInputDto updatedMailing) {
        Mailing updateMailing = mailingRepository.findById(mailingId)
                .orElseThrow(() -> new ResourceNotFoundException("No mailing found"));

        if(updatedMailing.getSubject() != null) {
            updateMailing.setSubject(updatedMailing.getSubject());
        }
        if(updatedMailing.getBody() != null) {
            updateMailing.setBody(updatedMailing.getBody());
        }
        if(updatedMailing.getDate() != null) {
            updateMailing.setDate(updatedMailing.getDate());
        }
        Mailing returnMailing = mailingRepository.save(updateMailing);
        return MailingMapper.mailingFromModelToOutputDto(returnMailing);
    }

    public void deleteMailingById(Long mailingId) {
        Mailing mailing = mailingRepository.findById(mailingId)
                .orElseThrow(() -> new ResourceNotFoundException("No mailing found with id " + mailingId));
        mailingRepository.delete(mailing);
    }


    @Transactional
    public void sendMailing(Long mailingId) {
        Mailing mailing = mailingRepository.findById(mailingId)
                .orElseThrow(() -> new ResourceNotFoundException("No mailing found with id " + mailingId));
        List<User> subscribers = userRepository.findBySubscribedToMailingTrue();
        if(subscribers.isEmpty()) {
            throw new RuntimeException("No subscribers to the mailing at this time.");
        }

        subscribers.forEach(user -> {
            if (isMailSendingEnabled) {
                sendRealMailing(user.getEmail(), mailing.getSubject(), mailing.getBody());
            } else {
                System.out.println("Simulated email to:" + user.getEmail() +
                        ", Subject: " + mailing.getSubject() + ", Body: " + mailing.getBody());
            }
        });
    }

    private void sendRealMailing(String to, String subject, String body) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,"UTF-8");
            helper.setFrom("noreply@minutefiction.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
        mailSender.send(mimeMessage);
    }


}