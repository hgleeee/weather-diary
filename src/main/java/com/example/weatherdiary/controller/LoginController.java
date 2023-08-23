package com.example.weatherdiary.controller;

import com.example.weatherdiary.dto.LoginIdAndPassword;
import com.example.weatherdiary.service.MemberService;
import com.example.weatherdiary.utils.SessionConst;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final HttpSession httpSession;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid LoginIdAndPassword loginIdAndPassword) {

        Optional<String> optionalMember = memberService.findMemberLoginId(loginIdAndPassword);
        if (optionalMember.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (getCurrentLoginId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        httpSession.setAttribute(SessionConst.LOGIN_ID, loginIdAndPassword.getLoginId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String getCurrentLoginId() {
        return (String) httpSession.getAttribute(SessionConst.LOGIN_ID);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        httpSession.invalidate();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
