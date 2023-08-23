package com.example.weatherdiary.aspect;

import com.example.weatherdiary.controller.LoginController;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
@Aspect
@RequiredArgsConstructor
public class MemberLoginCheckAspect {

    private final LoginController loginController;

    @Before("@annotation(com.example.weatherdiary.annotation.MemberLoginCheck)")
    public void memberLoginCheck() throws HttpClientErrorException {

        String currentLoginId = loginController.getCurrentLoginId();

        if (currentLoginId == null) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }
}
