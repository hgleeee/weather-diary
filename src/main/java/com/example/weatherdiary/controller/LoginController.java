package com.example.weatherdiary.controller;

import com.example.weatherdiary.exception.AlreadyLoginException;
import com.example.weatherdiary.utils.SessionConst;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final HttpSession httpSession;

    public void login(String loginId) throws AlreadyLoginException {


        httpSession.setAttribute(SessionConst.LOGIN_ID, loginId);
    }

    public void logout() {

    }
}
