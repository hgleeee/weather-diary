package com.example.weatherdiary.service;

import com.example.weatherdiary.utils.SessionConst;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionLoginService implements LoginService {

    private final HttpSession httpSession;

    @Override
    public void login(String loginId) {

    }

    @Override
    public void logout() {

    }

    @Override
    public String getCurrentLoginId() {
        return (String) httpSession.getAttribute(SessionConst.LOGIN_ID);
    }
}
