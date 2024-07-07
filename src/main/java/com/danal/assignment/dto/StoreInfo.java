package com.danal.assignment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoreInfo {
    private String storeCode;
    private String storeName;
    private String branchName;
    private String largeCategoryCode;
    private String largeCategoryName;
    private String mediumCategoryCode;
    private String mediumCategoryName;
    private String smallCategoryCode;
    private String smallCategoryName;
    private String standardIndustryCode;
    private String standardIndustryName;
    private String siDoCode;
    private String siDoName;
    private String siGunGuCode;
    private String siGunGuName;
    private String administrativeDongCode;
    private String administrativeDongName;
    private String legalDongCode;
    private String legalDongName;
    private String lotNumberCode;
    private String landDivisionCode;
    private String landDivisionName;
    private Integer lotMainNumber;
    private Integer lotSubNumber;
    private String lotAddress;
    private String roadNameCode;
    private String roadName;
    private Integer buildingMainNumber;
    private Integer buildingSubNumber;
    private String buildingManagementNumber;
    private String buildingName;
    private String roadNameAddress;
    private String oldPostalCode;
    private String newPostalCode;
    private String dongInfo;
    private String floorInfo;
    private String roomInfo;
    private double longitude;
    private double latitude;
}
