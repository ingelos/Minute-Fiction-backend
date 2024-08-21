package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.MailingInputDto;
import com.mf.minutefictionbackend.dtos.mappers.MailingMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.MailingOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.Mailing;

import com.mf.minutefictionbackend.models.User;
import com.mf.minutefictionbackend.repositories.MailingRepository;
import com.mf.minutefictionbackend.repositories.UserRepository;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MailingService {

    private final MailingRepository mailingRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;


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

    public MailingOutputDto updateMailing(Long mailingId, MailingOutputDto updatedMailing) {
        Mailing updateMailing = mailingRepository.findById(mailingId)
                .orElseThrow(() -> new ResourceNotFoundException("No mailing found"));

        updateMailing.setSubject(updatedMailing.getSubject());
        updateMailing.setBody(updateMailing.getBody());
        updateMailing.setDate(updatedMailing.getDate());

        Mailing returnMailing = mailingRepository.save(updateMailing);
        return MailingMapper.mailingFromModelToOutputDto(returnMailing);
    }

    public void deleteMailingById(Long mailingId) {
        Mailing mailing = mailingRepository.findById(mailingId)
                .orElseThrow(() -> new ResourceNotFoundException("No mailing found with id " + mailingId));
        mailingRepository.delete(mailing);
    }


    public void sendMailing(Long mailingId) {
        Mailing mailing = mailingRepository.findById(mailingId)
                .orElseThrow(() -> new ResourceNotFoundException("No mailing found with id " + mailingId));

        List<User> subscribers = userRepository.findBySubscribedToMailingTrue();

        if(subscribers.isEmpty()) {
            throw new RuntimeException("No subscribers to the mailing at this moment.");
        }
        subscribers.forEach(user -> sendEmail(user.getEmail(), mailing.getSubject(), mailing.getBody()));
        }


        // html of html template?

    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@minutefiction.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }


}