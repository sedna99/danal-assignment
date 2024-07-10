package com.danal.assignment.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepListener;
import org.springframework.batch.core.annotation.AfterStep;

public class EmptyStepListener implements StepListener {
    @AfterStep
    public ExitStatus afterStep(StepExecution execution){
        if(execution.getReadCount() > 0){
            return execution.getExitStatus();
        }else{
            return ExitStatus.FAILED;
        }
    }
}
