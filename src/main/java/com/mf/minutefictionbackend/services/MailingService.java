package com.mf.minutefictionbackend.services;

import com.mf.minutefictionbackend.dtos.inputDtos.MailingInputDto;
import com.mf.minutefictionbackend.dtos.mappers.MailingMapper;
import com.mf.minutefictionbackend.dtos.outputDtos.MailingOutputDto;
import com.mf.minutefictionbackend.models.Mailing;
import com.mf.minutefictionbackend.repositories.MailingRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MailingService {

    private final MailingRepository mailingRepository;
;

    public MailingService(MailingRepository mailingRepository) {
        this.mailingRepository = mailingRepository;
    }


    public MailingOutputDto createMailing(MailingInputDto mailingInputDto) {
        Mailing mailing = mailingRepository.save(MailingMapper.mailingFromInputDtoToModel(mailingInputDto));
        return MailingMapper.mailingFromModelToOutputDto(mailing);
    }

    public List<MailingOutputDto> getAllMailings() {
        List<Mailing> allMailings = mailingRepository.findAll();
        return MailingMapper.mailingFromModelListToOutputList(allMailings);
    }



}