package com.tistory.workshop.studyspring.domain.repository;

import com.tistory.workshop.studyspring.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {


}
