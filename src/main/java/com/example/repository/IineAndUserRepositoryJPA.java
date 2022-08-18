package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.IineAndUserJPA;
import com.example.entity.IineJPA;

@Repository
public interface IineAndUserRepositoryJPA extends JpaRepository<IineAndUserJPA, Integer> {

	/**
	 * いいね情報とユーザー情報を紐づける関連テーブルにインサート
	 * 
	 * @param iineId
	 * @param userId
	 * @return
	 * 
	 */
	@Query(value = "INSERT INTO iine_user (iine_id,user_id) VALUES (:iineId, :userId)", nativeQuery = true)
	void insertIineAndUser(@Param("iineId") Integer iineId, @Param("userId") Integer userId);

	/**
	 * いいねを押したのが2回目だった時に関連テーブルから情報をDELETEする
	 * 
	 * @param iineId
	 * @param userId
	 * @return
	 * 
	 */
	void deleteByIineIdAndUserId(Integer iineId, Integer userId);

	/**
	 * 該当の投稿にいいねしているユーザーの検索
	 * 
	 * @param articleId
	 * @param userId
	 * @return iineJPA
	 * 
	 */
	@Query(value = "SELECT i.id,i.article_id,i.count FROM (SELECT i.id,i.article_id,i.count FROM iines AS i WHERE i.article_id=:articleId ) AS i JOIN ( SELECT iu.iine_id,iu.user_id FROM iine_user AS iu WHERE iu.user_id=:userId) AS iu ON i.id = iu.iine_id", nativeQuery = true)
	List<IineJPA> findByArticleIdAndUserId(@Param("articleId") Integer articleId, @Param("userId") Integer userId);

}
