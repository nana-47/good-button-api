package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.domain.Iine;

@Repository
public class IineRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * いいねしたユーザーの情報は別で取得予定
	 * 
	 */
	private static final RowMapper<Iine> IINE_ROW_MAPPER = (rs, i) -> {
		Iine iine = new Iine();
		iine.setId(rs.getInt("id"));
		iine.setArticleId(rs.getInt("article_id"));
		iine.setCount(rs.getInt("count"));
		return iine;
	};

	/**
	 * いいね情報のインサート
	 * 
	 * @param iine
	 * @return id
	 * 
	 */
	public Integer insertIine(Integer articleId, Integer count) {
		String sql = "INSERT INTO iines (article_id,count)" + " VALUES (:articleId, :count) returning id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId).addValue("count",
				count);

		// 同時に関連テーブルへidをinsertするため、シリアルidを同時に取得
		KeyHolder keyHolder = new GeneratedKeyHolder();
		template.update(sql, param, keyHolder);
		Integer id = (Integer) keyHolder.getKey();
		return id;
	}

	/**
	 * いいね情報とユーザー情報を紐づける関連テーブルにインサート
	 * 
	 * @param iineId
	 * @param userId
	 * @return
	 * 
	 */
	public void insertIineAndUser(Integer iineId, Integer userId) {
		String sql = "INSERT INTO iine_user (iine_id,user_id)" + " VALUES (:iineId, :userId)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("iineId", iineId).addValue("userId", userId);
		template.update(sql, param);
	}

	/**
	 * 該当の投稿にいいねしているユーザーの検索
	 * 
	 * @param articleId
	 * @param userId
	 * @return iine
	 * 
	 */
	public Iine findByArticleIdAndUserId(Integer articleId, Integer userId) {
		String sql = "SELECT i.id,i.article_id,i.count FROM ( "
				+ "SELECT i.id,i.article_id,i.count FROM iines AS i WHERE i.article_id=:articleId ) AS i "
				+ "JOIN ( SELECT iu.iine_id,iu.user_id FROM iine_user AS iu WHERE iu.user_id=:userId) AS iu ON i.id = iu.iine_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId).addValue("userId",
				userId);
		List<Iine> iineList = template.query(sql, param, IINE_ROW_MAPPER);

		Iine iine = new Iine();
		for (int i = 0; i < iineList.size(); i++) {
			iine = iineList.get(0);
		}
		return iine;
	}

	/**
	 * 該当の投稿が既にいいねされているか確認と情報の呼び出し
	 * 
	 * @param articleId
	 * @return iine
	 * 
	 */
	public Iine findByArticleId(Integer articleId) {
		String sql = "SELECT id,article_id,count FROM iines WHERE article_id=:articleId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		List<Iine> iineList = template.query(sql, param, IINE_ROW_MAPPER);

		Iine iine = new Iine();
		for (int i = 0; i < iineList.size(); i++) {
			iine = iineList.get(0);
		}
		return iine;
	}

	/**
	 * 該当の投稿に対してのいいね情報の更新
	 * 
	 * @param articleId
	 * @param count
	 * @return
	 * 
	 */
	public void updateCount(Integer count, Integer articleId) {
		String sql = "UPDATE iines SET count =:count WHERE article_id=:articleId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("count", count).addValue("articleId",
				articleId);
		template.update(sql, param);
	}

	/**
	 * いいねを押したのが2回目だった時に関連テーブルから情報をDELETEする
	 * 
	 * @param iineId
	 * @param userId
	 * @return
	 * 
	 */
	public void deleteIine(Integer iineId, Integer userId) {
		String sql = "DELETE FROM iine_user WHERE iine_id=:iineId AND user_id=:userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("iineId", iineId).addValue("userId", userId);
		template.update(sql, param);
	}
}