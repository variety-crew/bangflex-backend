//package com.varc.bangflex.domain.community.communityPost.controller;
//
//import com.varc.bangflex.common.ResponseMessage;
//import com.varc.bangflex.domain.community.communityPost.service.CommunityFileService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController("communityFileController")
//@Slf4j
//@RequestMapping("api/v1/community/post")
//public class CommunityFileController {
//
//    private final CommunityFileService communityFileService;
//
//    @Autowired
//    public CommunityFileController(CommunityFileService communityFileService) {
//        this.communityFileService = communityFileService;
//    }
//
//    /* 게시글 첨부파일 등록 */
//    @PostMapping("/{communityPostCode}")
//    public ResponseEntity<ResponseMessage<List<String>>> uploadFiles(
//                                                        @PathVariable("communityPostCode") Integer communityPostCode,
//                                                        @RequestParam List<MultipartFile> images) throws IOException {
//
//        List<String> imageUrls = communityFileService.uploadFiles(communityPostCode, images);
//        return ResponseEntity.ok(new ResponseMessage<>(200, "첨부파일 등록 성공", imageUrls));
//    }
//
//    /* 게시글 첨부파일 수정 및 삭제 */
//    @PutMapping("/{communityPostCode}/modify")
//    public ResponseEntity<ResponseMessage<List<String>>> modifyFiles(
//                                                        @PathVariable("communityPostCode") Integer communityPostCode,
//                                                        @RequestParam List<String> newImageUrls) {
//        communityFileService.updateFiles(communityPostCode, newImageUrls);
//        return ResponseEntity.ok(new ResponseMessage<>(200, "첨부파일 수정 성공", null));
//    }
//}
