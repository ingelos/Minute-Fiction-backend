package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.MailingInputDto;
import com.mf.minutefictionbackend.dtos.mappers.MailingMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.MailingOutputDto;
import com.mf.minutefictionbackend.exceptions.ResourceNotFoundException;
import com.mf.minutefictionbackend.models.Mailing;
import com.mf.minutefictionbackend.models.User;
import com.mf.minutefictionbackend.repositories.MailingRepository;
import com.mf.minutefictionbackend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MailingService {

    private final MailingRepository mailingRepository;
    private final UserRepository userRepository;

//    private JavaMailSender mailSender;


    public MailingService(MailingRepository mailingRepository, UserRepository userRepository) {
        this.mailingRepository = mailingRepository;
        this.userRepository = userRepository;
    }


    public MailingOutputDto createMailing(MailingInputDto mailingInputDto) {
        Mailing mailing = mailingRepository.save(MailingMapper.mailingFromInputDtoToModel(mailingInputDto));
        return MailingMapper.mailingFromModelToOutputDto(mailing);
    }

    public List<MailingOutputDto> getAllMailings() {
        List<Mailing> allMailings = mailingRepository.findAll();
        return MailingMapper.mailingFromModelListToOutputList(allMailings);
    }

//    public void sendMailing(Long mailingId) {
//        Optional<Mailing> optionalMailing = mailingRepository.findById(mailingId);
//        if(optionalMailing.isPresent()) {
//            Mailing mailing = optionalMailing.get();
//            List<User> subscribers = userRepository.findBySubscribedToMailing();
//
//            if(!subscribers.isEmpty()) {
//                for (User user : subscribers) {
//                    sendEmail(user.getEmail(), mailing.getTitle(), mailing.getContent());
//                }
//            }
//        } else {
//            throw new ResourceNotFoundException("No mailing found.");
//        }
//    }

//    private void sendEmail(String to, String subject, String body) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body);
//        emailSender.send(message);
//    }


}