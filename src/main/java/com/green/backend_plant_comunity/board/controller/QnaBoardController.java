package com.green.backend_plant_comunity.board.controller;

import com.green.backend_plant_comunity.board.dto.QnaBoardDTO;
import com.green.backend_plant_comunity.board.dto.QnaCategoryDTO;
import com.green.backend_plant_comunity.board.service.QnaBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaBoardController {
    private final QnaBoardService qnaBoardService;

    // 특정 회원의 문의 목록 조회 API
    @GetMapping("/member/{memId}")
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

    // 문의 상세 조회 API
    @GetMapping("/detail/{qnaNum}")
    public ResponseEntity<?> getQnaDetail(@PathVariable("qnaNum") int qnaNum) {
        try {
            QnaBoardDTO qnaBoardDTO = qnaBoardService.getQnaDetail(qnaNum);

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

    // 문의 등록 API
    @PostMapping("")
    public ResponseEntity<?> insertQna(@RequestBody QnaBoardDTO qnaBoardDTO) {
        try {
            if (qnaBoardDTO.getTitle() == null || qnaBoardDTO.getTitle().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("제목을 입력해주세요.");
            }
            if (qnaBoardDTO.getContent() == null || qnaBoardDTO.getContent().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("내용을 입력해주세요.");
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

    // 문의 수정 API
    @PutMapping("")
    public ResponseEntity<?> updateQna(@RequestBody QnaBoardDTO qnaBoardDTO) {
        try {
            if (qnaBoardDTO.getQnaNum() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("문의 번호가 필요합니다.");
            }
            if (qnaBoardDTO.getTitle() == null || qnaBoardDTO.getTitle().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("제목을 입력해주세요.");
            }
            if (qnaBoardDTO.getContent() == null || qnaBoardDTO.getContent().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("내용을 입력해주세요.");
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

    // 문의 삭제 API
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

    // 관리자 답변 등록 API
    @PostMapping("/answer")
    public ResponseEntity<?> insertAnswer(@RequestBody QnaBoardDTO qnaBoardDTO) {
        try {
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

    // 관리자용 문의사항 전체 조회 (필터링 포함)
    @GetMapping("/admin/list")
    public ResponseEntity<Map<String, Object>> getAdminQnaList(
            @RequestParam(required = false) Integer cateNum,
            @RequestParam(required = false) String memId,
            @RequestParam(required = false) String qnaStatus) {

        try {
            List<QnaBoardDTO> qnaList = qnaBoardService.getAdminQnaList(cateNum, memId, qnaStatus);
            int totalCount = qnaBoardService.getQnaCount(cateNum, memId, qnaStatus);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "문의사항 목록 조회 성공");
            response.put("data", qnaList);
            response.put("totalCount", totalCount);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "문의사항 목록 조회에 실패했습니다.");
            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // 관리자 답변 등록 (새로운 방식)
    @PostMapping("/admin/{qnaNum}/answer")
    public ResponseEntity<Map<String, Object>> insertAdminAnswer(
            @PathVariable int qnaNum,
            @RequestBody Map<String, String> requestBody,
            @RequestHeader(value = "Admin-Id", required = false) String adminId) {

        try {
            if (adminId == null || adminId.trim().isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "관리자 ID가 필요합니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            String answerContent = requestBody.get("answerContent");
            if (answerContent == null || answerContent.trim().isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "답변 내용을 입력해주세요.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            boolean success = qnaBoardService.insertAdminAnswer(qnaNum, answerContent, adminId);

            Map<String, Object> response = new HashMap<>();
            response.put("success", success);
            response.put("message", success ? "답변이 등록되었습니다." : "답변 등록에 실패했습니다.");

            HttpStatus status = success ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(status).body(response);

        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "답변 등록에 실패했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // 문의사항 답변 상태 변경
    @PutMapping("/admin/{qnaNum}/status")
    public ResponseEntity<Map<String, Object>> updateQnaStatus(
            @PathVariable int qnaNum,
            @RequestBody Map<String, String> requestBody) {

        try {
            String qnaStatus = requestBody.get("qnaStatus");

            if (qnaStatus == null || qnaStatus.trim().isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "변경할 상태를 선택해주세요.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            boolean success = qnaBoardService.updateQnaStatus(qnaNum, qnaStatus);

            Map<String, Object> response = new HashMap<>();
            response.put("success", success);
            response.put("message", success ? "상태가 변경되었습니다." : "상태 변경에 실패했습니다.");

            HttpStatus status = success ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(status).body(response);

        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "상태 변경에 실패했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // 카테고리별 문의사항 통계 조회
    @GetMapping("/admin/statistics/category")
    public ResponseEntity<Map<String, Object>> getQnaStatsByCategory() {
        try {
            List<Map<String, Object>> statistics = qnaBoardService.getQnaStatsByCategory();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "카테고리별 통계 조회 성공");
            response.put("data", statistics);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "통계 조회에 실패했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // 상태별 문의사항 통계 조회
    @GetMapping("/admin/statistics/status")
    public ResponseEntity<Map<String, Object>> getQnaStatsByStatus() {
        try {
            List<Map<String, Object>> statistics = qnaBoardService.getQnaStatsByStatus();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "상태별 통계 조회 성공");
            response.put("data", statistics);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "통계 조회에 실패했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}

