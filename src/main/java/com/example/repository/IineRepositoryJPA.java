package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.IineJPA;

@Repository
public interface IineRepositoryJPA extends JpaRepository<IineJPA, Integer> {

	/**
	 * いいね情報のインサート
	 * 
	 * @param iine
	 * @return id
	 * 
	 */
	@Query(value = "insert into iines (article_id,count) values (?1, ?2) returning id", nativeQuery = true)
	Integer insertIine(Integer articleId, Integer count);

	/**
	 * 該当の投稿が既にいいねされているか確認と情報の呼び出し
	 * 
	 * @param articleId
	 * @return iineJPA
	 * 
	 */
	List<IineJPA> findByArticleId(Integer articleId);

	/**
	 * 該当の投稿に対してのいいね情報の更新
	 * 
	 * @param articleId
	 * @param count
	 * @return
	 * 
	 */
	@Modifying
	@Query(value = "UPDATE iines SET count =:count WHERE article_id=:articleId", nativeQuery = true)
	void updateCount(@Param("count") Integer count, @Param("articleId") Integer articleId);

}
