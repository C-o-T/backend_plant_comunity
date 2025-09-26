package com.green.backend_plant_comunity.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

public class HtmlImageParser {
   public static List<String> extractImageUrls(String html){
      //HTML 문자열을 파싱해서 JsoupDocument 객체 생성
      Document doc = Jsoup.parse(html);

      // 모든 img 태그 선택
      Elements imgElements = doc.select("img");
      //img 태그에서 src 속성만 추출해서 리스트로 반환
      return imgElements.stream()
              .map(img ->img.attr("src"))
              .filter(src -> src !=null && !src.isBlank())
              .collect(Collectors.toList());
   }
}
