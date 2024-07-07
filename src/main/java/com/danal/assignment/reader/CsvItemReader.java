package com.danal.assignment.reader;

import com.danal.assignment.dto.StoreInfo;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.core.io.FileSystemResource;

public class CsvItemReader {
    public static FlatFileItemReader<StoreInfo> of(String filePath) {
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

}
