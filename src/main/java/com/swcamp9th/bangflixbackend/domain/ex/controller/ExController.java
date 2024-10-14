//package com.swcamp9th.bangflixbackend.domain.ex.controller;
//
//import com.swcamp9th.bangflixbackend.common.ResponseMessage;
//import com.swcamp9th.bangflixbackend.domain.ex.dto.ExDTO;
//import com.swcamp9th.bangflixbackend.domain.ex.service.ExService;
//import java.util.Arrays;
//import java.util.List;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@Slf4j
//@RequestMapping("api/v1/ex")
//public class ExController {
//
//    private final ExService exService;
//
//    @Autowired
//    public ExController(ExService exService) {
//        this.exService = exService;
//    }
//
//    @GetMapping("/many")
//    public ResponseEntity<ResponseMessage<List<ExDTO>>> getUsers() {
//        List<ExDTO> exDTOS = Arrays.asList(
//            new ExDTO(7, 7),
//            new ExDTO(8, 8)
//        );
//
//
//        return ResponseEntity.ok(new ResponseMessage<>(200, "회원가입 성공", exDTOS));
//    }
//
//    @GetMapping("/one")
//    public ResponseEntity<ResponseMessage<ExDTO>> getUser() {
//        ExDTO ex = new ExDTO(7, 7);
//
//        exService.example(ex);
//
//        return ResponseEntity.ok(new ResponseMessage<>(200, "회원가입 성공", ex));
//    }
//
//}
