package com.green.backend_plant_comunity.board.mapper;

import com.green.backend_plant_comunity.board.dto.QnaBoardDTO;
import com.green.backend_plant_comunity.board.dto.QnaCategoryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface QnaBoardMapper {

    // 특정 회원의 문의 목록 조회
    List<QnaBoardDTO> getMyQnaList(String memId);

    // 문의 상세 조회
    QnaBoardDTO getQnaDetail(int qnaNum);

    // 문의 등록
    void insertQna(QnaBoardDTO qnaBoardDTO);

    // 문의 수정
    void updateQna(QnaBoardDTO qnaBoardDTO);

    // 문의 삭제
    void deleteQna(@Param("qnaNum") int qnaNum, @Param("memId") String memId);

    // 관리자 답변 등록
    void insertAnswer(QnaBoardDTO qnaBoardDTO);

    // 활성화된 카테고리 목록 조회
    List<QnaCategoryDTO> selectQnaCategories();

    // 관리자용 문의사항 전체 조회 (필터링 포함)
    List<QnaBoardDTO> getAdminQnaList(@Param("cateNum") Integer cateNum,
                                      @Param("memId") String memId,
                                      @Param("qnaStatus") String qnaStatus);

    // 관리자 답변 등록 (새로운 방식)
    int insertAdminAnswer(@Param("qnaNum") int qnaNum,
                          @Param("answerContent") String answerContent,
                          @Param("adminId") String adminId);

    // 문의사항 답변 상태 변경
    int updateQnaStatus(@Param("qnaNum") int qnaNum,
                        @Param("qnaStatus") String qnaStatus);

    // 카테고리별 문의사항 통계 조회
    List<Map<String, Object>> getQnaStatsByCategory();

    // 상태별 문의사항 통계 조회
    List<Map<String, Object>> getQnaStatsByStatus();

    // 전체 문의사항 수 조회 (필터링 포함)
    int getQnaCount(@Param("cateNum") Integer cateNum,
                    @Param("memId") String memId,
                    @Param("qnaStatus") String qnaStatus);
}
