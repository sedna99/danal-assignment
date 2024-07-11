package com.danal.assignment.listener;

import com.danal.assignment.dto.StoreInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.annotation.OnReadError;
import org.springframework.batch.item.file.FlatFileParseException;

@Slf4j
public class StoreInfoListener implements ItemReadListener<StoreInfo> {
    @OnReadError
    public void onReadError(Exception e) {
        if(e instanceof FlatFileParseException ffpe) {
            StringBuilder output = new StringBuilder()
                    .append("An exception occurred while processing the ")
                    .append(ffpe.getLineNumber())
                    .append(" line of the file. ");
            log.error("FlatFileFormatException: {}", output);
        } else {
            log.error("Exception: {}", e.getMessage());
        }
    }
}
