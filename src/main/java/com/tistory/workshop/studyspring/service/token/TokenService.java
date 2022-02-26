package com.tistory.workshop.studyspring.service.token;

import com.tistory.workshop.studyspring.domain.entity.RefreshToken;
import com.tistory.workshop.studyspring.domain.entity.User;
import com.tistory.workshop.studyspring.domain.repository.RefreshTokenRepository;
import com.tistory.workshop.studyspring.domain.repository.UserRepository;
import com.tistory.workshop.studyspring.dto.token.TokenDto;
import com.tistory.workshop.studyspring.dto.token.TokenRequestDto;
import com.tistory.workshop.studyspring.service.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenDto reIssue(TokenRequestDto tokenRequestDto) {
        // 만료된 refresh token 에러
        if (!tokenProvider.validationToken(tokenRequestDto.getRefreshToken())) {
            String msg = tokenRequestDto.getRefreshToken() + "is expired RefreshToken";
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, msg);
        }

        // Access token 에서 Username(PK) 가져오기
        String accessToken = tokenRequestDto.getAccessToken();
        Long id = tokenProvider.getUserId(accessToken);

        // User pk 로 회원 검색 및 리포지토리에서 저장된 refresh token 이 없음
        User user = userRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.CONFLICT, "user not found"));

        RefreshToken refreshToken = refreshTokenRepository.findByRefreshTokenIdUserId(user.getId())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "refresh token not found"));

        // refresh token 불일치 에러
        if (!refreshToken.getToken().equals(tokenRequestDto.getRefreshToken())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "refresh token not found");
        }

        // Access Token, Refresh Token 재발급 및 refresh 토큰 저장
        TokenDto newCreateToken = tokenProvider.createToken(user.getId());
        RefreshToken updateRefreshToken = refreshToken.updateToken(newCreateToken.getRefreshToken());
        refreshTokenRepository.save(updateRefreshToken);

        return newCreateToken;
    }

}
