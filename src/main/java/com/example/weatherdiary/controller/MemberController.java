package com.example.weatherdiary.controller;

import com.example.weatherdiary.dto.MemberSignUpParam;
import com.example.weatherdiary.exception.NotUniqueLoginIdException;
import com.example.weatherdiary.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<String> signUpMember(@Valid MemberSignUpParam memberSignUpParam) {
        try {
            memberService.signUpMember(memberSignUpParam);
        } catch (NotUniqueLoginIdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{loginId}/exist")
    public ResponseEntity<String> checkLoginIdDuplicated(@PathVariable String loginId) {
        try {
            memberService.checkLoginIdDuplicated(loginId);
        } catch (NotUniqueLoginIdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
