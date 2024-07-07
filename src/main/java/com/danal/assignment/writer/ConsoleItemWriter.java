package com.danal.assignment.writer;

import com.danal.assignment.dto.StoreInfo;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

public class ConsoleItemWriter implements ItemWriter<StoreInfo> {
    @Override
    public void write(Chunk<? extends StoreInfo> items) throws Exception {
        for (StoreInfo item : items) {
            System.out.println(item);
        }
    }
}