//package com.swcamp9th.bangflixbackend.domain.ex.service;
//
//import com.swcamp9th.bangflixbackend.domain.ex.entity.Ex;
//import com.swcamp9th.bangflixbackend.domain.ex.dto.ExDTO;
//import com.swcamp9th.bangflixbackend.domain.ex.repository.ExRepository;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ExServiceImpl implements ExService {
//
//    private final ModelMapper modelMapper;
//    private final ExRepository exRepository;
//
//    @Autowired
//    public ExServiceImpl(ModelMapper modelMapper, ExRepository exRepository) {
//        this.modelMapper = modelMapper;
//        this.exRepository = exRepository;
//    }
//
//    @Override
//    public void example(ExDTO exDto) {
//        Ex ex = modelMapper.map(exDto, Ex.class);
//    }
//}
