package com.danal.assignment.writer;

import com.danal.assignment.dto.StoreInfo;
import com.danal.assignment.repository.StoreInfoRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseItemWriter implements ItemWriter<StoreInfo> {

    @Autowired
    private StoreInfoRepository storeInfoRepository;

    @Override
    public void write(Chunk<? extends StoreInfo> items){
        for (StoreInfo storeInfo : items) {
            storeInfoRepository.save(storeInfo);
        }
    }
}
