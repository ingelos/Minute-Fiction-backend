package com.mf.minutefictionbackend.repositories;

import com.mf.minutefictionbackend.models.Theme;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    List<Theme> findByClosingDateAfter(LocalDate now, Sort sort);
    List<Theme> findByClosingDateBefore(LocalDate now);
    Optional<Theme> findByNameIgnoreCase(String name);


}
