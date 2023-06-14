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
    //DB
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

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인이 우선
        if(memolist.containsKey(id)) {
            Memo memo = memolist.get(id);

            // memo 수정
            memo.update(requestDto);

            return memo.getId();
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        if(memolist.containsKey(id)) {
            // id에 해당하는 Memo 삭제
            memolist.remove(id);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}
