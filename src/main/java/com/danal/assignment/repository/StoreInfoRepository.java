package com.danal.assignment.repository;

import com.danal.assignment.dto.StoreInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreInfoRepository {
    private final JdbcTemplate jdbcTemplate;
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
