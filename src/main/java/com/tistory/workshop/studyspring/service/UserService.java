package com.tistory.workshop.studyspring.service;

import com.tistory.workshop.studyspring.domain.entity.RefreshToken;
import com.tistory.workshop.studyspring.domain.entity.RefreshTokenId;
import com.tistory.workshop.studyspring.domain.entity.User;
import com.tistory.workshop.studyspring.domain.repository.RefreshTokenRepository;
import com.tistory.workshop.studyspring.domain.repository.UserRepository;
import com.tistory.workshop.studyspring.dto.sign.UserRequestDto;
import com.tistory.workshop.studyspring.dto.user.UserResponseDto;
import com.tistory.workshop.studyspring.dto.token.TokenDto;
import com.tistory.workshop.studyspring.service.token.TokenProvider;
import com.tistory.workshop.studyspring.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SecurityService securityService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void register(UserRequestDto userRequestDto) {
        String email = userRequestDto.getEmail();
        String password = userRequestDto.getPassword();

        if (userRepository.existsByEmail(email)) {
            String msg = "user " + email + " is already exists";
            throw new HttpClientErrorException(HttpStatus.CONFLICT, msg);
        }

        String salt = securityService.getSalt();
        String hashedPassword = securityService.encrypt(password + salt);

        userRepository.save(User.builder()
                        .email(email)
                        .password(hashedPassword)
                        .salt(salt)
                .build());
    }

    @Transactional
    public TokenDto login(UserRequestDto userRequestDto) {
        User user = userRepository.findByEmail(userRequestDto.getEmail())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.CONFLICT, "user not found"));
        String password = userRequestDto.getPassword();
        String salt = user.getSalt();

        if (!securityService.validPassword(password, salt, user.getPassword())) {
            String msg = "password is incorrect";
            throw new HttpClientErrorException(HttpStatus.CONFLICT, msg);
        }

        TokenDto tokenDto = tokenProvider.createToken(user.getId());

        RefreshToken refreshToken = RefreshToken.builder()
                .refreshTokenId(new RefreshTokenId(user))
                .token(tokenDto.getRefreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);
        return tokenDto;
    }

    public UserResponseDto findByToken(String token) {
        Long userId = tokenProvider.getUserId(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.CONFLICT));
        return new UserResponseDto(user);
    }

}
