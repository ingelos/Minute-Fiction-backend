package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.inputDtos.MailingInputDto;
import com.mf.minutefictionbackend.dtos.outputDtos.MailingOutputDto;
import com.mf.minutefictionbackend.models.Mailing;

import java.util.ArrayList;
import java.util.List;

public class MailingMapper {

    public static Mailing mailingFromInputDtoToModel(MailingInputDto mailingInputDto) {
        Mailing mailing = new Mailing();
        mailing.setSubject(mailingInputDto.subject);
        mailing.setBody(mailingInputDto.body);
        mailing.setDate(mailingInputDto.date);

        return mailing;
    }

    public static MailingOutputDto mailingFromModelToOutputDto(Mailing mailing) {
        MailingOutputDto mailingOutputDto = new MailingOutputDto();
        mailingOutputDto.setId(mailing.getId());
        mailingOutputDto.setSubject(mailing.getSubject());
        mailingOutputDto.setBody(mailing.getBody());
        mailingOutputDto.setDate(mailing.getDate());

        return mailingOutputDto;
    }

    public static List<MailingOutputDto> mailingFromModelListToOutputList(List<Mailing> mailings) {
        List<MailingOutputDto> mailingOutputDtoList = new ArrayList<>();

        for(Mailing mailing : mailings) {
            mailingOutputDtoList.add(mailingFromModelToOutputDto(mailing));
        }
        return mailingOutputDtoList;
    }


}
