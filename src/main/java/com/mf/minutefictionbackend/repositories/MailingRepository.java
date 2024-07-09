package com.mf.minutefictionbackend.repositories;

import com.mf.minutefictionbackend.models.Mailing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailingRepository extends JpaRepository<Mailing, Long> {
}
