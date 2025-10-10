package com.green.backend_plant_comunity.board.service;

import com.green.backend_plant_comunity.board.dto.QnaBoardDTO;
import com.green.backend_plant_comunity.board.dto.QnaCategoryDTO;
import com.green.backend_plant_comunity.board.mapper.QnaBoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QnaBoardService {
    private final QnaBoardMapper qnaBoardMapper;

    // 특정 회원의 문의 목록 조회
    public List<QnaBoardDTO> getMyQnaList(String memId) {
        return qnaBoardMapper.getMyQnaList(memId);
    }

    // 문의 상세 조회
    public QnaBoardDTO getQnaDetail(int qnaNum) {
        return qnaBoardMapper.getQnaDetail(qnaNum);
    }

    // 문의 등록
    public void insertQna(QnaBoardDTO qnaBoardDTO) {
        qnaBoardMapper.insertQna(qnaBoardDTO);
    }

    // 문의수정
    public void updateQna(QnaBoardDTO qnaBoardDTO) {
        qnaBoardMapper.updateQna(qnaBoardDTO);
    }

    // 문의삭제
    public void deleteQna(int qnaNum, String memId) {
        qnaBoardMapper.deleteQna(qnaNum, memId);
    }

    // 관리자 답변 등록
    public void insertAnswer(QnaBoardDTO qnaBoardDTO) {
        qnaBoardMapper.insertAnswer(qnaBoardDTO);
    }

    // 활성화된 카테고리 목록 조회
    public List<QnaCategoryDTO> getQnaCategories() {
        return qnaBoardMapper.selectQnaCategories();
    }

    // 관리자용 문의사항 전체 조회 (필터링 포함)
    public List<QnaBoardDTO> getAdminQnaList(Integer cateNum, String memId, String qnaStatus) {
        try {
            return qnaBoardMapper.getAdminQnaList(cateNum, memId, qnaStatus);
        } catch (Exception e) {
            throw new RuntimeException("문의사항 목록 조회에 실패했습니다.", e);
        }
    }

    // 관리자 답변 등록
    public boolean insertAdminAnswer(int qnaNum, String answerContent, String adminId) {
        try {
            // 답변 내용 유효성 검증
            if (answerContent == null || answerContent.trim().isEmpty()) {
                throw new IllegalArgumentException("답변 내용을 입력해주세요.");
            }

            // 문의사항 존재 여부 확인
            QnaBoardDTO existingQna = qnaBoardMapper.getQnaDetail(qnaNum);
            if (existingQna == null) {
                throw new RuntimeException("존재하지 않는 문의사항입니다.");
            }

            int result = qnaBoardMapper.insertAdminAnswer(qnaNum, answerContent.trim(), adminId);
            return result > 0;

        } catch (Exception e) {
            throw new RuntimeException("답변 등록에 실패했습니다.", e);
        }
    }

    // 문의사항 답변 상태 변경
    public boolean updateQnaStatus(int qnaNum, String qnaStatus) {
        try {
            // 상태 유효성 검증
            if (!isValidStatus(qnaStatus)) {
                throw new IllegalArgumentException("유효하지 않은 상태입니다.");
            }

            int result = qnaBoardMapper.updateQnaStatus(qnaNum, qnaStatus);
            return result > 0;

        } catch (Exception e) {
            throw new RuntimeException("상태 변경에 실패했습니다.", e);
        }
    }

    // 카테고리별 문의사항 통계 조회
    public List<Map<String, Object>> getQnaStatsByCategory() {
        try {
            return qnaBoardMapper.getQnaStatsByCategory();
        } catch (Exception e) {
            throw new RuntimeException("통계 조회에 실패했습니다.", e);
        }
    }

    // 상태별 문의사항 통계 조회
    public List<Map<String, Object>> getQnaStatsByStatus() {
        try {
            return qnaBoardMapper.getQnaStatsByStatus();
        } catch (Exception e) {
            throw new RuntimeException("통계 조회에 실패했습니다.", e);
        }
    }

    // 전체 문의사항 수 조회 (필터링 포함)
    public int getQnaCount(Integer cateNum, String memId, String qnaStatus) {
        try {
            return qnaBoardMapper.getQnaCount(cateNum, memId, qnaStatus);
        } catch (Exception e) {
            throw new RuntimeException("문의사항 개수 조회에 실패했습니다.", e);
        }
    }

    // 상태 유효성 검증
    private boolean isValidStatus(String status) {
        return status != null &&
                (status.equals("답변대기") ||
                        status.equals("답변중") ||
                        status.equals("답변완료"));
    }
}
