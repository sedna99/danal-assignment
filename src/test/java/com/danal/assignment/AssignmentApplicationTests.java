package com.danal.assignment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@SpringBatchTest
@EnableBatchProcessing
@EnableAutoConfiguration
class AssignmentApplicationTests {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JobRepositoryTestUtils jobRepositoryTestUtils;

	@BeforeEach
	public void setUp() {
		// JobRepository 초기화
		jobRepositoryTestUtils.removeJobExecutions();
	}
	@AfterEach
	public void tearDown() {
		// JobRepository 초기화
		jobRepositoryTestUtils.removeJobExecutions();
		jdbcTemplate.update("delete from store_info");
	}
	@Test
	void storeInfoJobTest() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("filePath", "src/main/resources/test.csv")
				.addLong("chunkSize", 100L)
				.toJobParameters();

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
		StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();

		assertThat(stepExecution.getReadCount()).isEqualTo(200);
		System.out.println("read count: " + stepExecution.getReadCount());
		System.out.println("write count: " + stepExecution.getWriteCount());
		assertThat(stepExecution.getWriteCount()).isEqualTo(200);
	}

	@Test
	void relativePathCompleteTest() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("filePath", "src/main/resources/test.csv")
				.addLong("chunkSize", 100L)
				.toJobParameters();

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
		StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();

		assertThat(stepExecution.getReadCount()).isEqualTo(200);
		System.out.println("read count: " + stepExecution.getReadCount());
		System.out.println("write count: " + stepExecution.getWriteCount());
		assertThat(stepExecution.getWriteCount()).isEqualTo(200);
	}

	@Test
	void alreadyCompletedErrorTest() throws Exception{
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("filePath", "src/main/resources/test.csv")
				.addLong("chunkSize", 100L)
				.toJobParameters();

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
		assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED);

		assertThrows(JobInstanceAlreadyCompleteException.class, () -> {
			jobLauncherTestUtils.launchJob(jobParameters);
		});
	}

	@Test
	void processorReturnNullTest() throws Exception{
		jdbcTemplate.update("insert into store_info(store_code, store_name, branch_name, large_category_code, large_category_name, medium_category_code, medium_category_name, small_category_code, small_category_name, standard_industry_code, standard_industry_name, si_do_code, si_do_name, si_gun_gu_code, si_gun_gu_name, administrative_dong_code, administrative_dong_name, legal_dong_code, legal_dong_name, lot_number_code, land_division_code, land_division_name, lot_main_number, lot_sub_number, lot_address, road_name_code, road_name, building_main_number, building_sub_number, building_management_number, building_name, road_name_address, old_postal_code, new_postal_code, dong_info, floor_info, room_info, longitude, latitude) values ('MA0101202210A0084547','금강산노래광장',null,'I2','음식','I211','주점','I21101','일반 유흥 주점','I56211','일반 유흥 주점업','51','강원특별자치도','51170','동해시','51170520','송정동','5117010300','송정동','5117010300107470000','1','대지',747,null,'강원특별자치도 동해시 송정동 747','511703221039','강원특별자치도 동해시 송정로',11,null,'4217010300007470000000086','파크장','강원특별자치도 동해시 송정로 11','240806','25789',null,'지',null,129.12752511744,37.4952646683955)");
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("filePath", "src/main/resources/test.csv")
				.addLong("chunkSize", 100L)
				.toJobParameters();

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
		StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();

		assertThat(stepExecution.getReadCount()).isEqualTo(200);
		System.out.println("read count: " + stepExecution.getReadCount());
		System.out.println("write count: " + stepExecution.getWriteCount());
		assertThat(stepExecution.getWriteCount()).isEqualTo(199);
	}

	@Test
	void emptyCsvExitStatusFailed() throws Exception{
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("filePath", "src/main/resources/empty.csv")
				.addLong("chunkSize", 100L)
				.toJobParameters();

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
		StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();

		assertThat(stepExecution.getReadCount()).isEqualTo(0);
		assertThat(stepExecution.getWriteCount()).isEqualTo(0);
		assertThat(stepExecution.getExitStatus()).isEqualTo(ExitStatus.FAILED);
	}

	@Test
	void brokenCsvSkipTest() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("filePath", "src/main/resources/broken.csv")
				.addLong("chunkSize", 100L)
				.toJobParameters();

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
		StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();

		assertThat(stepExecution.getReadCount()).isEqualTo(199);
		System.out.println("read count: " + stepExecution.getReadCount());
		System.out.println("write count: " + stepExecution.getWriteCount());
		assertThat(stepExecution.getWriteCount()).isEqualTo(199);
	}

	@Test
	void brokenCsvFailTest() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addString("filePath", "src/main/resources/broken2.csv")
				.addLong("chunkSize", 100L)
				.toJobParameters();

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
		assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo(ExitStatus.FAILED.getExitCode());
	}
}
