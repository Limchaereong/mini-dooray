package com.nhnacademy.minidooraydgateway.exception;

public class NonActiveMemberException extends RuntimeException {
    public NonActiveMemberException() {
        super("탈퇴하거나 휴면인 회원입니다.");
    }
}
