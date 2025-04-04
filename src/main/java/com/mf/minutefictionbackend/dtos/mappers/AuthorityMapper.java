package com.mf.minutefictionbackend.dtos.mappers;

import com.mf.minutefictionbackend.dtos.dtos.AuthorityDto;
import com.mf.minutefictionbackend.models.Authority;
import java.util.HashSet;

import java.util.Set;
public class AuthorityMapper {

    public static Set<Authority> fromDtoToSet(Set<AuthorityDto> authorityDtos) {
        Set<Authority> authorities = new HashSet<>();
        if(authorityDtos != null) {
            authorityDtos.forEach(dto -> authorities.add(fromDto(dto)));
        }
        return authorities;
    }

    public static Authority fromDto(AuthorityDto dto) {
        return new Authority(dto.getAuthority());
    }


}
