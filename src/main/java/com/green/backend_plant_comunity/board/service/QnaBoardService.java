package com.green.backend_plant_comunity.board.service;

import com.green.backend_plant_comunity.board.dto.QnaBoardDTO;
import com.green.backend_plant_comunity.board.dto.QnaCategoryDTO;
import com.green.backend_plant_comunity.board.mapper.QnaBoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QnaBoardService {
    private final QnaBoardMapper qnaBoardMapper;

    //특정 회원의 문의 목록 조회
    public List<QnaBoardDTO> getMyQnaList(String memId) {
        return qnaBoardMapper.getMyQnaList(memId);
    }

    //문의 상세 조회
    public QnaBoardDTO getQnaDetail(int qnaNum) {
        return qnaBoardMapper.getQnaDetail(qnaNum);
    }

    //문의 등록
    public void insertQna(QnaBoardDTO qnaBoardDTO) {
        qnaBoardMapper.insertQna(qnaBoardDTO);
    }

    //문의수정
    public void updateQna(QnaBoardDTO qnaBoardDTO) {
        qnaBoardMapper.updateQna(qnaBoardDTO);
    }

    //문의삭제
    public void deleteQna(int qnaNum, String memId) {
        qnaBoardMapper.deleteQna(qnaNum, memId);
    }

    //관리자 답변 등록
    public void insertAnswer(QnaBoardDTO qnaBoardDTO) {
        qnaBoardMapper.insertAnswer(qnaBoardDTO);
    }

    // 활성화된 카테고리 목록 조회
    public List<QnaCategoryDTO> getQnaCategories() {
        return qnaBoardMapper.selectQnaCategories();
    }
}
