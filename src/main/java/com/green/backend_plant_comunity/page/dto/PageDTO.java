package com.green.backend_plant_comunity.page.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PageDTO {
   private int nowPage; // 현재 선택된 페이지 번호
   private int totalDataCnt; // 전체 데이터 수
   private int beginPage; // 화면에 보이는 첫 번째 페이지 번호
   private int endPage; // 화면에 보이는 마지막 페이지 번호
   private int displayCnt; // 한 페이지에 보여지는 게시글 수
   private int displayPageCnt; // 한번에 보여지는 페이지 수
   private boolean prev; // '이전' 버튼의 유무
   private boolean next; // '다음' 버튼의 유무
   private int offsetCnt; // 건너뛸 개수

   // 생성자
   public PageDTO() {
      nowPage = 1;
      displayCnt = 10;
      displayPageCnt = 5;
   }

   // 이 메서드가 실행되면 page 처리를 위한 모든 변수 값을 세팅
   public void setPageInfo() {
      // 마지막에 보이는 페이지 번호
      endPage = displayPageCnt * (int)Math.ceil(nowPage / (double)displayPageCnt); // math.ceil 은 올림을 준다.
      //math.ceil은 리턴 결과가 실수(double)이다. 그래서 displayPagCnt를 곱하면 에ㅐ러가뜬다. 하나 바꿔주자.

      //처음에 보이는 페이지 번호
      beginPage = endPage - displayPageCnt +1;

      //전체 페이지 수  ex) 155/10 = 15가 나오니까, 하나를 실수형으로 바꿔준다.
      int totalPageCnt = (int)Math.ceil(totalDataCnt / (double)displayCnt);


      //next버튼 유무
      if(endPage < totalPageCnt)
      {
         next = true;
      } else {
         next = false;
         endPage = totalPageCnt;
      }

      //prev 버튼 유무
      prev= beginPage == 1 ? false : true;
      if(totalDataCnt == 0) {
         endPage = 1;
      }

      //검색 시작과 마지막 ROW_NUM
      offsetCnt = (nowPage -1) * displayCnt;

   }
}

