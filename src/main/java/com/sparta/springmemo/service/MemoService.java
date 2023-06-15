package com.sparta.springmemo.service;

import com.sparta.springmemo.dto.MemoRequestDto;
import com.sparta.springmemo.dto.MemoResponseDto;
import com.sparta.springmemo.entity.Memo;
import com.sparta.springmemo.repository.MemoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemoService {
    //MemoService -> MemoRepository
    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        // DB 저장
        Memo saveMemo = memoRepository.save(memo);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(saveMemo);

        return memoResponseDto;
    }

    public List<MemoResponseDto> getMemos() {
        //return memoRepository.findAll();
        return memoRepository.findAll().stream().map((MemoResponseDto::new)).toList();
    }

    @Transactional
    public Long updateMemo(Long id, MemoRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);
        /*Memo memo = memoRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );*/
        // memo 내용 수정
        //memoRepository.update(id, requestDto);
        memo.update(requestDto);
        return id;

        /*// 해당 메모가 DB에 존재하는지 확인
        Memo memo = memoRepository.findById(id);
        if (memo != null) {
            memoRepository.update(id, requestDto);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }*/
    }

    @Transactional
    public Long deleteMemo(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);
        /*Memo memo = memoRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("선택한 메모는 없습니다.")
        );*/
        /*Memo memo = memoRepository.findById(id);
        if (memo != null) {
            memoRepository.delete(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }*/
        // memo 삭제
        memoRepository.delete(memo);

        return id;
    }


    private Memo findMemo(Long id) {
        return memoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );
    }
}