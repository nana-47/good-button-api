package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.example.domain.Iine;

@SpringBootTest
class IineServiceTest {

	@Autowired
	private IineService iineService;

	private static final RowMapper<Iine> REVIEW_ROW_MAPPER = new BeanPropertyRowMapper<>(Iine.class);

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		// 使いたいDBを設定
		SingleConnectionDataSource singleConnectionDataSource = new SingleConnectionDataSource(
				"jdbc:postgresql://localhost:5432/rakustagram", "postgres", "postgres", true);
		SqlParameterSource param = new MapSqlParameterSource();
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				singleConnectionDataSource);

		// テスト前にDBの中身消去
		namedParameterJdbcTemplate.update("DELETE FROM iines", param);

		// 挿入したいDBデータを記述
		String insertSql = "INSERT INTO iines (article_id,count) VALUES (1, 1) returning id";

		// テストデータを挿入
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(insertSql, param, keyHolder);
		Integer id = (Integer) keyHolder.getKey();

		// テスト前にDBの中身消去
		namedParameterJdbcTemplate.update("DELETE FROM iine_user", param);

		// 挿入したいDBデータを記述
		SqlParameterSource param2 = new MapSqlParameterSource().addValue("id", id);
		String insertSql2 = "INSERT INTO iine_user (iine_id,user_id) VALUES (:id, 1)";

		// テストデータを挿入
		namedParameterJdbcTemplate.update(insertSql2, param2);
	}

	@AfterEach
	void tearDown() throws Exception {
		String sql = "DELETE FROM iines";
		String sql2 = "DELETE FROM iine_user";
		SqlParameterSource param = new MapSqlParameterSource();
		SingleConnectionDataSource singleConnectionDataSource = new SingleConnectionDataSource(
				"jdbc:postgresql://localhost:5432/rakustagram", "postgres", "postgres", true);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				singleConnectionDataSource);
		namedParameterJdbcTemplate.update(sql, param);
		namedParameterJdbcTemplate.update(sql2, param);
	}

	@Test
	@DisplayName("すでに他者からいいねされている記事にユーザー2が初めていいねする場合")
	void testAddIine1() {
		Iine iine = new Iine();
		iine = iineService.addIine(1, 2);
		Integer expected = 2;
		assertEquals(iine.getCount(), expected, "カウントが違います");
	}

	@Test
	@DisplayName("すでにいいねされている記事にユーザー1がもう一度いいねする場合")
	void testAddIine2() {
		Iine iine = new Iine();
		iine = iineService.addIine(1, 1);
		Integer expected = 0;
		assertEquals(iine.getCount(), expected, "カウントが違います");
	}

	@Test
	@DisplayName("まだ一回もいいねされてない記事にいいねする場合")
	void testAddIine3() {
		Iine iine = new Iine();
		iine = iineService.addIine(2, 1);
		Integer expected = 1;
		assertEquals(iine.getCount(), expected, "カウントが違います");
	}

	@Test
	@DisplayName("articleIdで呼び出されるIine情報の確認")
	void testFindIine() {
		Iine actual = iineService.findIine(1);
		Iine expected = new Iine();
		expected.setArticleId(1);
		expected.setCount(1);
		assertEquals(expected.getArticleId(), actual.getArticleId(), "articleIdが違います");
		assertEquals(expected.getCount(), actual.getCount(), "countが違います");
	}

}
