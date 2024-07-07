package com.danal.assignment.repository;

import com.danal.assignment.dto.StoreInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StoreInfoRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_STORE_INFO_SQL = "INSERT INTO `store_info` (store_code, store_name, branch_name, large_category_code, large_category_name, " +
            "medium_category_code, medium_category_name, small_category_code, small_category_name, standard_industry_code, standard_industry_name, " +
            "si_do_code, si_do_name, si_gun_gu_code, si_gun_gu_name, administrative_dong_code, administrative_dong_name, legal_dong_code, legal_dong_name, " +
            "lot_number_code, land_division_code, land_division_name, lot_main_number, lot_sub_number, lot_address, road_name_code, road_name, " +
            "building_main_number, building_sub_number, building_management_number, building_name, road_name_address, old_postal_code, new_postal_code, " +
            "dong_info, floor_info, room_info, longitude, latitude) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_STORE_INFO_SQL = "SELECT * FROM store_info WHERE store_code= ?";

    public void save(StoreInfo storeInfo) {
        jdbcTemplate.update(INSERT_STORE_INFO_SQL,
                storeInfo.getStoreCode(), storeInfo.getStoreName(), storeInfo.getBranchName(), storeInfo.getLargeCategoryCode(), storeInfo.getLargeCategoryName(),
                storeInfo.getMediumCategoryCode(), storeInfo.getMediumCategoryName(), storeInfo.getSmallCategoryCode(), storeInfo.getSmallCategoryName(),
                storeInfo.getStandardIndustryCode(), storeInfo.getStandardIndustryName(), storeInfo.getSiDoCode(), storeInfo.getSiDoName(),
                storeInfo.getSiGunGuCode(), storeInfo.getSiGunGuName(), storeInfo.getAdministrativeDongCode(), storeInfo.getAdministrativeDongName(),
                storeInfo.getLegalDongCode(), storeInfo.getLegalDongName(), storeInfo.getLotNumberCode(), storeInfo.getLandDivisionCode(), storeInfo.getLandDivisionName(),
                storeInfo.getLotMainNumber(), storeInfo.getLotSubNumber(), storeInfo.getLotAddress(), storeInfo.getRoadNameCode(), storeInfo.getRoadName(),
                storeInfo.getBuildingMainNumber(), storeInfo.getBuildingSubNumber(), storeInfo.getBuildingManagementNumber(), storeInfo.getBuildingName(),
                storeInfo.getRoadNameAddress(), storeInfo.getOldPostalCode(), storeInfo.getNewPostalCode(), storeInfo.getDongInfo(), storeInfo.getFloorInfo(),
                storeInfo.getRoomInfo(), storeInfo.getLongitude(), storeInfo.getLatitude());
    }

    public Boolean existsByStoreCode(String storeCode) {
        try {
            StoreInfo result = jdbcTemplate.queryForObject(SELECT_STORE_INFO_SQL, new BeanPropertyRowMapper<>(StoreInfo.class), storeCode);
            return result != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
