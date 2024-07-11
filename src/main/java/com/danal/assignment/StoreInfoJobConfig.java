package com.danal.assignment;

import com.danal.assignment.dto.StoreInfo;
import com.danal.assignment.listener.EmptyStepListener;
import com.danal.assignment.listener.StoreInfoListener;
import com.danal.assignment.policy.StoreInfoSkipPolicy;
import com.danal.assignment.processor.StoreItemProcessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StoreInfoJobConfig {
    private final ApplicationContext ac;
    private final DataSource dataSource;
    @Bean
    public Job storeInfoJob(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager
    ){
        return new JobBuilder("storeInfoJob", jobRepository)
                .start(storeInfoStep(jobRepository, transactionManager, null, null))
                .build();
    }

    @Bean
    @JobScope
    public Step storeInfoStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactinManager,
            @Value("#{jobParameters[filePath]}") String filePath,
            @Value("#{jobParameters[chunkSize]}") Long chunkSize
    ){
        return new StepBuilder("storeInfoStep", jobRepository)
                .<StoreInfo, StoreInfo>chunk(Math.toIntExact(chunkSize), transactinManager)
                .reader(storeItemReader(filePath))
                .processor(storeItemProcessor())
                .writer(storeItemWriter())
                .faultTolerant()
                .skipPolicy(storeInfoSkipPolicy())
                .listener(storeInfoListener())
                .listener(emptyStepListener())
                .build();
    }

    public FlatFileItemReader<StoreInfo> storeItemReader(String filePath){
        return new FlatFileItemReaderBuilder<StoreInfo>()
                .name("storeInfoItemReader")
                .resource(new FileSystemResource(filePath))
                .linesToSkip(1)
                .strict(false)
                .delimited()
                .names("storeCode", "storeName", "branchName", "largeCategoryCode", "largeCategoryName",
                        "mediumCategoryCode", "mediumCategoryName", "smallCategoryCode", "smallCategoryName",
                        "standardIndustryCode", "standardIndustryName", "siDoCode", "siDoName", "siGunGuCode",
                        "siGunGuName", "administrativeDongCode", "administrativeDongName", "legalDongCode",
                        "legalDongName", "lotNumberCode", "landDivisionCode", "landDivisionName", "lotMainNumber",
                        "lotSubNumber", "lotAddress", "roadNameCode", "roadName", "buildingMainNumber",
                        "buildingSubNumber", "buildingManagementNumber", "buildingName", "roadNameAddress",
                        "oldPostalCode", "newPostalCode", "dongInfo", "floorInfo", "roomInfo", "longitude",
                        "latitude")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<StoreInfo>() {{
                    setTargetType(StoreInfo.class);
                }})
                .build();
    }

    @Bean
    public StoreItemProcessor storeItemProcessor(){
        return new StoreItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<StoreInfo> storeItemWriter(){
        return new JdbcBatchItemWriterBuilder<StoreInfo>()
                .dataSource(dataSource)
                .sql("insert into store_info(store_code, store_name, branch_name, large_category_code, large_category_name, medium_category_code, medium_category_name, small_category_code, small_category_name, standard_industry_code, standard_industry_name, si_do_code, si_do_name, si_gun_gu_code, si_gun_gu_name, administrative_dong_code, administrative_dong_name, legal_dong_code, legal_dong_name, lot_number_code, land_division_code, land_division_name, lot_main_number, lot_sub_number, lot_address, road_name_code, road_name, building_main_number, building_sub_number, building_management_number, building_name, road_name_address, old_postal_code, new_postal_code, dong_info, floor_info, room_info, longitude, latitude)" +
                        " values (:storeCode, :storeName, :branchName, :largeCategoryCode, :largeCategoryName, :mediumCategoryCode, :mediumCategoryName, :smallCategoryCode, :smallCategoryName, :standardIndustryCode, :standardIndustryName, :siDoCode, :siDoName, :siGunGuCode, :siGunGuName, :administrativeDongCode, :administrativeDongName, :legalDongCode, :legalDongName, :lotNumberCode, :landDivisionCode, :landDivisionName, :lotMainNumber, :lotSubNumber, :lotAddress, :roadNameCode, :roadName, :buildingMainNumber, :buildingSubNumber, :buildingManagementNumber, :buildingName, :roadNameAddress, :oldPostalCode, :newPostalCode, :dongInfo, :floorInfo, :roomInfo, :longitude, :latitude)")
                .beanMapped()
                .build();
    }

    @Bean
    public EmptyStepListener emptyStepListener(){
        return new EmptyStepListener();
    }

    @Bean
    public StoreInfoListener storeInfoListener(){
        return new StoreInfoListener();
    }

    @Bean
    public StoreInfoSkipPolicy storeInfoSkipPolicy(){
        return new StoreInfoSkipPolicy();
    }

    //remove warning message
    @Bean
    public static BeanDefinitionRegistryPostProcessor jobRegistryBeanPostProcessorRemover() {
        return registry -> registry.removeBeanDefinition("jobRegistryBeanPostProcessor");
    }
}
