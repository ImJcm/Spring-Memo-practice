package com.sparta.springmemo.controller;

import com.sparta.springmemo.dto.MemoRequestDto;
import com.sparta.springmemo.dto.MemoResponseDto;
import com.sparta.springmemo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemoController {
    private final Map<Long, Memo> memolist = new HashMap<>();

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        // Memo Max ID Check
        Long maxId = memolist.size() > 0 ? Collections.max(memolist.keySet()) + 1 : 1;
        memo.setId(maxId);

        // DB 저장
        memolist.put(memo.getId(), memo);

        // Entity -> memoResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;

    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        // Map To List
        //stream => forEach Memo
        //MemoResponseDto Constructor (Memo)가 호출
        List<MemoResponseDto> responseDtoList = memolist.values().stream()
                .map(MemoResponseDto::new)
                .toList();

        return responseDtoList;
    }
}
