package com.sparta.springmemo.dto;

import lombok.Getter;

@Getter
public class MemoRequestDto {
    private Long id;
    private String username;
    private String contents;
}
