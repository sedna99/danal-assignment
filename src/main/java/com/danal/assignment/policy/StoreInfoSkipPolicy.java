package com.danal.assignment.policy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ParseException;

import java.io.FileNotFoundException;

@Slf4j
public class StoreInfoSkipPolicy implements SkipPolicy {
    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
        log.info("Skip count: {}", skipCount);
        if(t instanceof FileNotFoundException){
            return false;
        }else if(t instanceof ParseException && skipCount <= 10){
            return true;
        }else{
            return false;
        }
    }
}
