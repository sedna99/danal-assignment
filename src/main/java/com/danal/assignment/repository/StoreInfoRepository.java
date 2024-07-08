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
    private static final String SELECT_STORE_INFO_SQL = "SELECT * FROM store_info WHERE store_code= ?";

    public Boolean existsByStoreCode(String storeCode) {
        try {
            StoreInfo result = jdbcTemplate.queryForObject(SELECT_STORE_INFO_SQL, new BeanPropertyRowMapper<>(StoreInfo.class), storeCode);
            return result != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}
