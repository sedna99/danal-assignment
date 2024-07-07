package com.danal.assignment;

import com.danal.assignment.dto.StoreInfo;
import com.danal.assignment.processor.DatabaseItemProcessor;
import com.danal.assignment.reader.CsvItemReader;

import com.danal.assignment.writer.ConsoleItemWriter;
import com.danal.assignment.writer.DatabaseItemWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StoreInfoJobConfig {
    private final DatabaseItemWriter storeItemWriter;
    private final DatabaseItemProcessor databaseItemProcessor;
    @Bean
    public Job storeInfoJob(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager
    ){
        return new JobBuilder("storeInfoJob", jobRepository)
                .start(step(jobRepository, transactionManager, null))
                .build();
    }

    @Bean
    @JobScope
    public Step step(
            JobRepository jobRepository,
            PlatformTransactionManager transactinManager,
            @Value("#{jobParameters[filePath]}") String filePath
    ){
        return new StepBuilder("step1", jobRepository)
                .<StoreInfo, StoreInfo>chunk(100, transactinManager)
                .reader(CsvItemReader.of(filePath))
                .processor(databaseItemProcessor)
                .writer(storeItemWriter)
                .build();
    }

    //remove warning message
    @Bean
    public static BeanDefinitionRegistryPostProcessor jobRegistryBeanPostProcessorRemover() {
        return registry -> registry.removeBeanDefinition("jobRegistryBeanPostProcessor");
    }
}
