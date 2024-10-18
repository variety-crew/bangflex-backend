package com.swcamp9th.bangflixbackend.exception;

public class ExpiredTokenExcepiton  extends RuntimeException {
    public ExpiredTokenExcepiton(String message) {
        super(message);
    }
}
