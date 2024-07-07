package com.danal.assignment.writer;

import com.danal.assignment.dto.StoreInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.Chunk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableBatchProcessing
@Transactional
public class DatabaseItemWriterTest {

    @Autowired
    private DatabaseItemWriter databaseItemWriter;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testWrite(){
        // given
        StoreInfo storeInfo = new StoreInfo();
        storeInfo.setStoreCode("1");
        storeInfo.setStoreName("Store1");
        storeInfo.setBranchName("Branch1");
        storeInfo.setLargeCategoryCode("100");
        storeInfo.setLargeCategoryName("Category1");
        storeInfo.setMediumCategoryCode("110");
        storeInfo.setMediumCategoryName("Medium1");
        storeInfo.setSmallCategoryCode("111");
        storeInfo.setSmallCategoryName("Small1");
        storeInfo.setStandardIndustryCode("200");
        storeInfo.setStandardIndustryName("Industry1");
        storeInfo.setSiDoCode("01");
        storeInfo.setSiDoName("SiDo1");
        storeInfo.setSiGunGuCode("010");
        storeInfo.setSiGunGuName("SiGunGu1");
        storeInfo.setAdministrativeDongCode("0010");
        storeInfo.setAdministrativeDongName("AdminDong1");
        storeInfo.setLegalDongCode("00100");
        storeInfo.setLegalDongName("LegalDong1");
        storeInfo.setLotNumberCode("1000");
        storeInfo.setLandDivisionCode("10000");
        storeInfo.setLandDivisionName("Land1");
        storeInfo.setLotMainNumber(10);
        storeInfo.setLotSubNumber(1);
        storeInfo.setLotAddress("Address1");
        storeInfo.setRoadNameCode("100");
        storeInfo.setRoadName("100Name");
        storeInfo.setBuildingMainNumber(1);
        storeInfo.setBuildingSubNumber(1);
        storeInfo.setBuildingManagementNumber("100001");
        storeInfo.setBuildingName("Building1");
        storeInfo.setRoadNameAddress("RoadNameAddress1");
        storeInfo.setOldPostalCode("10001");
        storeInfo.setNewPostalCode("100001");
        storeInfo.setDongInfo("Dong1");
        storeInfo.setFloorInfo("1");
        storeInfo.setRoomInfo("101");
        storeInfo.setLongitude(127.0d);
        storeInfo.setLatitude(37.0d);

        Chunk<StoreInfo> chunk = new Chunk<>(Arrays.asList(storeInfo));

        // when
        databaseItemWriter.write(chunk);

        // then
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM store_info WHERE store_code = ?", Integer.class, "1");
        assertThat(count).isNotNull();
        assertThat(count).isEqualTo(1);
    }
}