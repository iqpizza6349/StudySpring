package com.tistory.workshop.studyspring.domain.repository;

import com.tistory.workshop.studyspring.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshTokenIdUserId(Long refreshTokenId_user_id);

}
