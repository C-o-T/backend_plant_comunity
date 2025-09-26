package com.green.backend_plant_comunity.util;

import com.green.backend_plant_comunity.board.dto.BoardImgDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUploadUtil {
   public static List<BoardImgDTO> fileUpload(List<MultipartFile> imgs) {
      //파일경로 지정
      String uploadPath = "D:\\01-STUDY\\dev\\team\\upload\\";

      // 2) 파일명 중복방지를 위해 업로드를 파일명을 세팅
      // ex> 원본 파일명 : java.jpg  -> fgfsdgdfgffefef.jpg
      // ex> 원본 파일명 : java.txt  -> hyhdfvsrrsfr.txt

      //리턴되는 모든 데이터를 저장할 list
      List<BoardImgDTO> imgList = new ArrayList<>();

      for (MultipartFile img : imgs) {
         if (img.isEmpty()) continue;
         String attachedFileName = UUID.randomUUID().toString();
         //원본 파일의 확장자 추출
         // "abc.jpg" -> 3
         int index = img.getOriginalFilename().lastIndexOf(".");
         String extension = img.getOriginalFilename().substring(index);

         //완성된 업로드할 파일명
         attachedFileName = attachedFileName + extension;

         // 3) 파일 업로드
         //java.io.File
         //파일이 생성될 경로와 파일명을 세팅 (ex> D:\\study\\upload\\abc-dd1.jpg )
         File f = new File(uploadPath + attachedFileName);


         //세팅된 경로와 파일명을 실제 파일로 변환
         try {
            img.transferTo(f);
         } catch (Exception e) {
            System.out.println(e);
            continue;
         }

         BoardImgDTO boardImgDTO = new BoardImgDTO();
         boardImgDTO.setOriginImgName(img.getOriginalFilename());
         boardImgDTO.setAttachedImgName(attachedFileName);
         imgList.add(boardImgDTO);
      }

      return imgList;

   }
}

//      for (MultipartFile img : imgs) {
//         BoardImgDTO imgDTO = fileUpload(img); //여러번
//         imgList.add(imgDTO);
//      }

//      //BOOK_IMG INSERT에 필요한 데이터를 리턴한다.
//      BoardImgDTO boardImgDTO = new BoardImgDTO();
//      boardImgDTO.setOriginImgName(img.getOriginalFilename());
//      boardImgDTO.setAttachedImgName(attachedFileName);
//      return boardImgDTO;


   //파일 다중 업로드 기능
   //매개변수로 업로드할 파일이 여러개 배열 형태로 전달 됨
//   public static List<BoardImgDTO> multipleFileUpload(MultipartFile[] imgs) {
//
//}
