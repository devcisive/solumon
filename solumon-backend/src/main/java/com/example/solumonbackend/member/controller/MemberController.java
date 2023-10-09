package com.example.solumonbackend.member.controller;

import com.example.solumonbackend.global.mail.EmailAuthResponseDto;
import com.example.solumonbackend.global.mail.EmailAuthService;
import com.example.solumonbackend.member.model.*;
import com.example.solumonbackend.member.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MemberController {
  private final MemberService memberService;
  private final EmailAuthService emailAuthService;

  @PostMapping("/sign-up/general")
  public ResponseEntity<GeneralSignUpDto.Response> signUp(@Valid @RequestBody GeneralSignUpDto.Request request) {
    log.info("[sign-up/general] 회원가입 진행. userEmail : {} ", request.getEmail());
    return ResponseEntity.ok(memberService.signUp(request));
  }

  @GetMapping(value = "/send-email-auth", produces = "application/json")
  @ResponseBody
  public ResponseEntity<EmailAuthResponseDto> sendEmailAuth(@RequestParam String email) throws Exception {
    String code = emailAuthService.sendSimpleMessage(email);
    log.info("[sendEmailAuth] 인증코드 발송완료");
    log.info("받는 이메일 : {}", email);
    log.info("받는 코드 : {}", code);

    return ResponseEntity.ok(EmailAuthResponseDto.builder()
        .email(email)
        .code(code)
        .build());
  }

  @PostMapping("/sign-in/general")
  public ResponseEntity<GeneralSignInDto.Response> signIn(@Valid @RequestBody GeneralSignInDto.Request request) {
    return ResponseEntity.ok(memberService.signIn(request));
  }

  @GetMapping("/log-out")
  public ResponseEntity<LogOutDto.Response> logOut(@AuthenticationPrincipal MemberDetail memberDetail,
                                                    @RequestHeader("X-AUTH-TOKEN") String accessToken) {
    return ResponseEntity.ok(memberService.logOut(memberDetail.getMember(), accessToken));
  }

  @GetMapping(value = "/find-password")
  public ResponseEntity<String> findPassword(@RequestBody FindPasswordDto.Request request) throws Exception {
    emailAuthService.sendNewPasswordMessage(request.getEmail());
    log.debug("[sendEmailAuth] 임시 비밀번호 발송완료");

    return ResponseEntity.ok("임시 비밀번호가 " + request.getEmail() + "으로 발송되었습니다.");
  }

  @GetMapping("/exception")
  public void exception() throws RuntimeException {
    throw new RuntimeException("접근이 금지되었습니다.");
  }

  @GetMapping("/test")
  public void test(@AuthenticationPrincipal MemberDetail memberDetail) {
    System.out.println(memberDetail.getMember().getEmail());
  }
}
