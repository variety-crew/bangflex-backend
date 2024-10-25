package com.varc.bangflex.domain.store.service;

import static org.junit.jupiter.api.Assertions.*;

import com.varc.bangflex.domain.store.dto.StoreDTO;
import com.varc.bangflex.domain.store.entity.Store;
import com.varc.bangflex.domain.store.repository.StoreRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class StoreServiceImplTests {

    private final StoreService storeService;

    @Autowired
    public StoreServiceImplTests(StoreService storeService) {
        this.storeService = storeService;
    }

    @MockBean
    private StoreRepository storeRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    @Transactional
    public void testFindStore() {
        // given
        Integer storeCode = 1000;

        Store mockStore = Store.builder()
            .storeCode(1000)
            .name("test용 store 이름")
            .address("test용 store 주소")
            .active(true)
            .createdAt(LocalDateTime.now())
            .pageUrl("test용 store 웹페이지 url")
            .build();

        StoreDTO mockStoreDTO = new StoreDTO();
        mockStoreDTO.setStoreCode(1000);
        mockStoreDTO.setName("test용 store 이름");
        mockStoreDTO.setAddress("test용 store 주소");
        mockStoreDTO.setPageUrl("test용 store 웹페이지 url");

        // StoreRepository가 호출될 때 Optional로 감싼 mockStore를 반환하도록 설정
        Mockito.when(storeRepository.findById(storeCode)).thenReturn(Optional.of(mockStore));
        // ModelMapper가 호출될 때 mockStore를 mockStoreDTO로 매핑하여 반환하도록 설정
        Mockito.when(modelMapper.map(mockStore, StoreDTO.class)).thenReturn(mockStoreDTO);

        // when
        StoreDTO foundStore = storeService.findStore(storeCode);

        // then
        assertNotNull(foundStore); // 반환된 StoreDTO가 null이 아님을 검증
        assertEquals(storeCode, foundStore.getStoreCode());
        Mockito.verify(storeRepository, Mockito.times(1)).findById(storeCode); // findById가 한 번 호출되었는지 검증
        Mockito.verify(modelMapper, Mockito.times(1)).map(mockStore, StoreDTO.class); // map 메서드가 한 번 호출되었는지 검증
    }

}