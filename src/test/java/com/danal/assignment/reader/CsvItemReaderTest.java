package com.danal.assignment.reader;

import com.danal.assignment.dto.StoreInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;

import static org.assertj.core.api.Assertions.assertThat;
public class CsvItemReaderTest {
    private FlatFileItemReader<StoreInfo> reader;

    @BeforeEach
    public void setUp() {
        reader = CsvItemReader.of("src/main/resources/test.csv");
        reader.open(new ExecutionContext());
    }

    @Test
    public void testRead() throws Exception {
        StoreInfo storeInfo = reader.read();
        assertThat(storeInfo.getStoreCode()).isEqualTo("MA0101202210A0084547");
        assertThat(storeInfo.getStoreName()).isEqualTo("금강산노래광장");
        assertThat(storeInfo.getBranchName()).isEqualTo("");
        assertThat(storeInfo.getLargeCategoryCode()).isEqualTo("I2");
        assertThat(storeInfo.getLargeCategoryName()).isEqualTo("음식");
        assertThat(storeInfo.getMediumCategoryCode()).isEqualTo("I211");
        assertThat(storeInfo.getMediumCategoryName()).isEqualTo("주점");
        assertThat(storeInfo.getSmallCategoryCode()).isEqualTo("I21101");
        assertThat(storeInfo.getSmallCategoryName()).isEqualTo("일반 유흥 주점");
        assertThat(storeInfo.getStandardIndustryCode()).isEqualTo("I56211");
        assertThat(storeInfo.getStandardIndustryName()).isEqualTo("일반 유흥 주점업");
        assertThat(storeInfo.getSiDoCode()).isEqualTo("51");
        assertThat(storeInfo.getSiDoName()).isEqualTo("강원특별자치도");
        assertThat(storeInfo.getSiGunGuCode()).isEqualTo("51170");
        assertThat(storeInfo.getSiGunGuName()).isEqualTo("동해시");
        assertThat(storeInfo.getAdministrativeDongCode()).isEqualTo("51170520");
        assertThat(storeInfo.getAdministrativeDongName()).isEqualTo("송정동");
        assertThat(storeInfo.getLegalDongCode()).isEqualTo("5117010300");
        assertThat(storeInfo.getLegalDongName()).isEqualTo("송정동");
        assertThat(storeInfo.getLotNumberCode()).isEqualTo("5117010300107470000");
        assertThat(storeInfo.getLandDivisionCode()).isEqualTo("1");
        assertThat(storeInfo.getLandDivisionName()).isEqualTo("대지");
        assertThat(storeInfo.getLotMainNumber()).isEqualTo(747);
        assertThat(storeInfo.getLotSubNumber()).isEqualTo(null);
        assertThat(storeInfo.getLotAddress()).isEqualTo("강원특별자치도 동해시 송정동 747");
        assertThat(storeInfo.getRoadNameCode()).isEqualTo("511703221039");
        assertThat(storeInfo.getRoadName()).isEqualTo("강원특별자치도 동해시 송정로");
        assertThat(storeInfo.getBuildingMainNumber()).isEqualTo(11);
        assertThat(storeInfo.getBuildingSubNumber()).isEqualTo(null);
        assertThat(storeInfo.getBuildingManagementNumber()).isEqualTo("4217010300007470000000086");
        assertThat(storeInfo.getBuildingName()).isEqualTo("파크장");
        assertThat(storeInfo.getRoadNameAddress()).isEqualTo("강원특별자치도 동해시 송정로 11");
        assertThat(storeInfo.getOldPostalCode()).isEqualTo("240806");
        assertThat(storeInfo.getNewPostalCode()).isEqualTo("25789");
        assertThat(storeInfo.getDongInfo()).isEqualTo("");
        assertThat(storeInfo.getFloorInfo()).isEqualTo("지");
        assertThat(storeInfo.getRoomInfo()).isEqualTo("");
        assertThat(storeInfo.getLongitude()).isEqualTo(129.12752511744d);
        assertThat(storeInfo.getLatitude()).isEqualTo(37.4952646683955d);
    }
}
