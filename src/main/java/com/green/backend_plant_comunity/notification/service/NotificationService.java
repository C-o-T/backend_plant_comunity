package com.green.backend_plant_comunity.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private static final String EXPO_PUSH_URL = "https://exp.host/--/api/v2/push/send";
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Expo Push Notification 전송
     * @param pushToken 푸시 토큰
     * @param title 알림 제목
     * @param body 알림 내용
     * @param data 추가 데이터 (ex: boardNum)
     */
    public void sendPushNotification(String pushToken, String title, String body, Map<String, Object> data) {
        try {
            // 푸시 토큰이 없으면 무시
            if (pushToken == null || pushToken.isEmpty()) {
                return;
            }

            // Expo Push 메시지 생성
            Map<String, Object> message = new HashMap<>();
            message.put("to", pushToken);
            message.put("sound", "default");
            message.put("title", title);
            message.put("body", body);
            message.put("priority", "high");

            if (data != null && !data.isEmpty()) {
                message.put("data", data);
            }

            // HTTP 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(message, headers);

            // Expo Push API 호출
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    EXPO_PUSH_URL,
                    request,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("푸시 알림 전송 성공: " + pushToken);
            } else {
                System.err.println("푸시 알림 전송 실패: " + response.getStatusCode());
            }

        } catch (Exception e) {
            System.err.println("푸시 알림 전송 오류: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 단순 텍스트 알림 전송 (데이터 없음)
     */
    public void sendSimpleNotification(String pushToken, String title, String body) {
        sendPushNotification(pushToken, title, body, null);
    }
}
