package com.danal.assignment.processor;

import com.danal.assignment.dto.StoreInfo;
import com.danal.assignment.repository.StoreInfoRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseItemProcessor implements ItemProcessor<StoreInfo, StoreInfo> {
    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Override
    public StoreInfo process(StoreInfo item) throws Exception {
        return storeInfoRepository.existsByStoreCode(item.getStoreCode()) ? null : item;
    }
}
