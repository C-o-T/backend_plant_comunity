package com.green.backend_plant_comunity.board.controller;

import com.green.backend_plant_comunity.board.dto.QnaBoardDTO;
import com.green.backend_plant_comunity.board.dto.QnaCategoryDTO;
import com.green.backend_plant_comunity.board.service.QnaBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaBoardController {
    private final QnaBoardService qnaBoardService;


    //특정 회원의 문의 목록 조회 API
    @GetMapping("/{memId}")
    public ResponseEntity<?> getMyQnaList(@PathVariable("memId") String memId) {
        try {
            List<QnaBoardDTO> qnaList = qnaBoardService.getMyQnaList(memId);
            return ResponseEntity.status(HttpStatus.OK).body(qnaList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("문의 목록 조회 중 오류가 발생했습니다.");
        }
    }

    //문의 상세 조회 API
    @GetMapping("/detail/{qnaNum}")
    public ResponseEntity<?> getQnaDetail(@PathVariable("qnaNum") int qnaNum) {
        try {
            QnaBoardDTO qnaBoardDTO = qnaBoardService.getQnaDetail(qnaNum);
            
            // 문의글이 없는 경우
            if (qnaBoardDTO == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("해당 문의를 찾을 수 없습니다.");
            }
            
            return ResponseEntity.status(HttpStatus.OK).body(qnaBoardDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("문의 상세 조회 중 오류가 발생했습니다.");
        }
    }


    //문의 등록 API
    @PostMapping("")
    public ResponseEntity<?> insertQna(@RequestBody QnaBoardDTO qnaBoardDTO) {
        try {
            // 필수 값 체크
            if (qnaBoardDTO.getTitle() == null || qnaBoardDTO.getTitle().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("문의 제목을 입력해주세요.");
            }
            if (qnaBoardDTO.getContent() == null || qnaBoardDTO.getContent().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("문의 내용을 입력해주세요.");
            }
            
            qnaBoardService.insertQna(qnaBoardDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("문의가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("문의 등록 중 오류가 발생했습니다.");
        }
    }


    //문의 수정 API

    @PutMapping("")
    public ResponseEntity<?> updateQna(@RequestBody QnaBoardDTO qnaBoardDTO) {
        try {
            // 필수 값 체크
            if (qnaBoardDTO.getQnaNum() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("문의 번호가 필요합니다.");
            }
            if (qnaBoardDTO.getTitle() == null || qnaBoardDTO.getTitle().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("문의 제목을 입력해주세요.");
            }
            if (qnaBoardDTO.getContent() == null || qnaBoardDTO.getContent().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("문의 내용을 입력해주세요.");
            }
            
            qnaBoardService.updateQna(qnaBoardDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("문의가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("문의 수정 중 오류가 발생했습니다. 답변이 완료된 문의는 수정할 수 없습니다.");
        }
    }


    //문의 삭제 API
    @DeleteMapping("/{qnaNum}/{memId}")
    public ResponseEntity<?> deleteQna(@PathVariable("qnaNum") int qnaNum, 
                                       @PathVariable("memId") String memId) {
        try {
            qnaBoardService.deleteQna(qnaNum, memId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("문의가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("문의 삭제 중 오류가 발생했습니다.");
        }
    }


    //관리자 답변 등록 API
    @PostMapping("/answer")
    public ResponseEntity<?> insertAnswer(@RequestBody QnaBoardDTO qnaBoardDTO) {
        try {
            // 필수 값 체크
            if (qnaBoardDTO.getQnaNum() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("문의 번호가 필요합니다.");
            }
            if (qnaBoardDTO.getAnswerContent() == null || qnaBoardDTO.getAnswerContent().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("답변 내용을 입력해주세요.");
            }
            
            qnaBoardService.insertAnswer(qnaBoardDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("답변이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("답변 등록 중 오류가 발생했습니다.");
        }
    }

    // 활성화된 카테고리 목록 조회
    @GetMapping("/categories")
    public List<QnaCategoryDTO> getCategories() {
        return qnaBoardService.getQnaCategories();
    }
}
