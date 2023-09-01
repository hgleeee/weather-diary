package com.example.weatherdiary.controller;

import com.example.weatherdiary.annotation.CurrentMember;
import com.example.weatherdiary.annotation.MemberLoginCheck;
import com.example.weatherdiary.domain.Member;
import com.example.weatherdiary.dto.MemberSignUpParam;
import com.example.weatherdiary.exception.InvalidValueException;
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
    private final LoginController loginController;

    @PostMapping("/register")
    public ResponseEntity<String> signUpMember(@Valid @RequestBody MemberSignUpParam memberSignUpParam) {
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

    @DeleteMapping("/myAccount")
    @MemberLoginCheck
    public ResponseEntity<String> deleteMember(@CurrentMember Member member,
                                               @RequestParam(name = "password") String password) {
        try {
            memberService.deleteMember(member, password);
            loginController.logout();
        } catch (InvalidValueException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
