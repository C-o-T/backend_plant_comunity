package com.green.backend_plant_comunity.util;

import com.green.backend_plant_comunity.board.dto.BoardImgDTO;
import com.green.backend_plant_comunity.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class ImageCleanupScheduler {
   private final BoardMapper boardMapper;

   @Value("${file.upload-dir}")
   private  String uploadDir;

   @Scheduled(cron = "0 0 * * * *")
   public void cleanUpImage(){
      System.out.println("이미지 정리 작업 실행" + uploadDir);

      try {
         List<BoardImgDTO> unusedImg = boardMapper.getUnusedImg();
         System.out.println("삭제 대상 : " + unusedImg.size() + "개" );
         int deleteCount = 0;
         for(BoardImgDTO img : unusedImg){
            try {
               File file = new File(uploadDir + "/" + img.getAttachedImgName());
               if(file.exists()){
                  file.delete();
                  System.out.println("파일 삭제" + img.getAttachedImgName());
               }
               boardMapper.deleteUnusedImg(img.getImgNum());
               deleteCount++;

            }catch (Exception e){
               System.out.println("삭제실패 : " + e.getMessage() );
            }
         }
         System.out.println("정리 완료 " + deleteCount + "개");
      }catch (Exception e){
         System.out.println("스케줄러 오류" + e.getMessage());
      }
   }

}
