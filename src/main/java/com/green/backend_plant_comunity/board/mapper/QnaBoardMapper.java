package com.green.backend_plant_comunity.board.mapper;

import com.green.backend_plant_comunity.board.dto.QnaBoardDTO;
import com.green.backend_plant_comunity.board.dto.QnaCategoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QnaBoardMapper {
    
    //특정 회원의 문의 목록 조회
    List<QnaBoardDTO> getMyQnaList(String memId);


    //문의 상세 조회
    QnaBoardDTO getQnaDetail(int qnaNum);


    //문의 등록
    void insertQna(QnaBoardDTO qnaBoardDTO);

    //문의수정
    void updateQna(QnaBoardDTO qnaBoardDTO);

    //문의삭제
    void deleteQna(int qnaNum, String memId);

    //관리자 답변 등록
    void insertAnswer(QnaBoardDTO qnaBoardDTO);

    // 활성화된 카테고리 목록 조회
    List<QnaCategoryDTO> selectQnaCategories();
}
