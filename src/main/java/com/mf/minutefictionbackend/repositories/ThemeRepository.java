package com.mf.minutefictionbackend.repositories;

import com.mf.minutefictionbackend.models.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    List<Theme> findByClosingDateAfter(LocalDate now);

}
