package com.green.backend_plant_comunity.board.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QnaBoardDTO {
    private int qnaNum;                  // 문의 번호 (PRIMARY KEY)
    private int cateNum;                 // 문의 카테고리 번호
    private String title;                // 문의 제목
    private String content;              // 문의 내용
    private String memId;                // 작성자 회원 ID
    private String qnaStatus;            // 답변 상태 (답변대기, 답변완료)
    private String answerContent;        // 관리자 답변 내용
    private LocalDateTime answerDate;    // 답변 날짜
    private String adminId;              // 답변한 관리자 ID
    private LocalDateTime createDate;    // 문의 등록일

    private QnaCategoryDTO qnaCategory;  // 카테고리 정보
}
