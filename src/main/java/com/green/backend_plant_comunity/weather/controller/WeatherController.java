package com.green.backend_plant_comunity.weather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class WeatherController {

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    // 주소 -> 좌표 변환
    @GetMapping("/geocode")
    public ResponseEntity<?> getCoordinates(@RequestParam String address) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + kakaoApiKey);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            // 1. 전체 주소로 검색 시도
            String addressUrl = "https://dapi.kakao.com/v2/local/search/address.json?query=" 
                + URLEncoder.encode(address, StandardCharsets.UTF_8);
            
            ResponseEntity<Map> addressResponse = restTemplate.exchange(addressUrl, HttpMethod.GET, entity, Map.class);
            Map addressBody = addressResponse.getBody();
            
            if (addressBody != null && hasDocuments(addressBody)) {
                System.out.println("✅ 전체 주소 검색 성공: " + address);
                return ResponseEntity.ok(addressBody);
            }
            
            // 2. 시/군/구까지만 검색 시도
            String simplifiedAddress = simplifyAddress(address);
            System.out.println("⚠️ 전체 주소 실패, 간소화 주소 시도: " + simplifiedAddress);
            
            addressUrl = "https://dapi.kakao.com/v2/local/search/address.json?query=" 
                + URLEncoder.encode(simplifiedAddress, StandardCharsets.UTF_8);
            
            addressResponse = restTemplate.exchange(addressUrl, HttpMethod.GET, entity, Map.class);
            addressBody = addressResponse.getBody();
            
            if (addressBody != null && hasDocuments(addressBody)) {
                System.out.println("✅ 간소화 주소 검색 성공: " + simplifiedAddress);
                return ResponseEntity.ok(addressBody);
            }
            
            // 3. 키워드 검색 시도
            System.out.println("⚠️ 주소 검색 실패, 키워드 검색 시도: " + simplifiedAddress);
            String keywordUrl = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" 
                + URLEncoder.encode(simplifiedAddress, StandardCharsets.UTF_8);
            
            ResponseEntity<Map> keywordResponse = restTemplate.exchange(keywordUrl, HttpMethod.GET, entity, Map.class);
            Map keywordBody = keywordResponse.getBody();
            
            if (keywordBody != null && hasDocuments(keywordBody)) {
                System.out.println("✅ 키워드 검색 성공");
                return ResponseEntity.ok(keywordBody);
            }
            
            // 4. 모두 실패 시 기본 좌표 반환 (시/군/구 중심)
            System.out.println("❌ 모든 검색 실패, 기본 좌표 반환");
            return ResponseEntity.ok(createDefaultResponse(address));
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("좌표 변환 실패");
        }
    }
    
    // documents 배열이 있는지 확인
    private boolean hasDocuments(Map response) {
        if (response == null) return false;
        Object docs = response.get("documents");
        if (docs instanceof List) {
            return !((List) docs).isEmpty();
        }
        return false;
    }
    
    // 주소 간소화 (시/군/구까지만)
    private String simplifyAddress(String address) {
        String[] parts = address.split(" ");
        if (parts.length >= 2) {
            return parts[0] + " " + parts[1];
        }
        return address;
    }
    
    // 기본 좌표 응답 생성
    private Map<String, Object> createDefaultResponse(String address) {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> documents = new ArrayList<>();
        Map<String, Object> document = new HashMap<>();
        
        // 주요 도시별 기본 좌표
        String city = address.split(" ")[0];
        Map<String, double[]> defaultCoords = new HashMap<>();
        defaultCoords.put("서울", new double[]{126.9780, 37.5665});
        defaultCoords.put("부산", new double[]{129.0756, 35.1796});
        defaultCoords.put("대구", new double[]{128.6014, 35.8714});
        defaultCoords.put("인천", new double[]{126.7052, 37.4563});
        defaultCoords.put("광주", new double[]{126.8526, 35.1595});
        defaultCoords.put("대전", new double[]{127.3845, 36.3504});
        defaultCoords.put("울산", new double[]{129.3114, 35.5384});
        defaultCoords.put("세종", new double[]{127.2890, 36.4800});
        
        double[] coords = defaultCoords.getOrDefault(city, new double[]{126.9780, 37.5665}); // 기본: 서울
        
        document.put("x", String.valueOf(coords[0]));
        document.put("y", String.valueOf(coords[1]));
        document.put("address_name", address.split(" ")[0]);
        
        documents.add(document);
        response.put("documents", documents);
        
        System.out.println("📍 기본 좌표 사용: " + city + " (" + coords[1] + ", " + coords[0] + ")");
        
        return response;
    }
}
