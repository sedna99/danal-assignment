package com.danal.assignment.processor;

import com.danal.assignment.dto.StoreInfo;
import com.danal.assignment.repository.StoreInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class DatabaseItemProcessorTest {

    @Mock
    private StoreInfoRepository storeInfoRepository;

    @InjectMocks
    private DatabaseItemProcessor databaseItemProcessor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void storeExistTest() throws Exception {
        // given
        StoreInfo storeInfo = new StoreInfo();
        storeInfo.setStoreCode("1");

        // when
        when(storeInfoRepository.existsByStoreCode("1")).thenReturn(true);

        // then
        StoreInfo result = databaseItemProcessor.process(storeInfo);
        assertThat(result).isNull();
    }

    @Test
    public void storeNonExistTest() throws Exception {
        // given
        StoreInfo storeInfo = new StoreInfo();
        storeInfo.setStoreCode("2");

        // when
        when(storeInfoRepository.existsByStoreCode("2")).thenReturn(false);

        // then
        StoreInfo result = databaseItemProcessor.process(storeInfo);
        assertThat(result).isEqualTo(storeInfo);
    }
}
