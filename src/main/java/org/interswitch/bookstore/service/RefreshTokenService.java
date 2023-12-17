package org.interswitch.bookstore.service;

import jakarta.servlet.http.HttpServletRequest;
import org.interswitch.bookstore.dto.request.RefreshTokenRequest;
import org.interswitch.bookstore.dto.response.RefreshTokenResponse;
import org.interswitch.bookstore.entities.RefreshToken;
import org.springframework.http.ResponseCookie;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(Long userId);
    RefreshToken verifyExpiration(RefreshToken token);
    RefreshTokenResponse generateNewToken(RefreshTokenRequest request);
    ResponseCookie generateRefreshTokenCookie(String token);
    String getRefreshTokenFromCookies(HttpServletRequest request);
    void deleteByToken(String token);
    ResponseCookie getCleanRefreshTokenCookie();
}
