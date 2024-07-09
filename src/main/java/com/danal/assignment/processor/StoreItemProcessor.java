package com.danal.assignment.processor;

import com.danal.assignment.dto.StoreInfo;
import com.danal.assignment.repository.StoreInfoRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StoreItemProcessor implements ItemProcessor<StoreInfo, StoreInfo> {
    int count = 0;

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Override
    public StoreInfo process(StoreInfo item) {
        count++;
        System.out.println("count: " + count);
        return storeInfoRepository.existsByStoreCode(item.getStoreCode()) ? null : item;
    }
}
