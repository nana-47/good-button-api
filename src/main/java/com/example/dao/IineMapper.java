package com.example.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.Iine;

@Mapper
public interface IineMapper {

	/**
	 * いいね情報のインサート
	 * 
	 * @param iine
	 * @return id
	 * 
	 */
	Integer insertIine(Integer articleId, Integer count);

	/**
	 * いいね情報とユーザー情報を紐づける関連テーブルにインサート
	 * 
	 * @param iineId
	 * @param userId
	 * @return
	 * 
	 */
	void insertIineAndUser(Integer iineId, Integer userId);

	/**
	 * 該当の投稿にいいねしているユーザーの検索
	 * 
	 * @param articleId
	 * @param userId
	 * @return iine
	 * 
	 */
	Iine findByArticleIdAndUserId(Integer articleId, Integer userId);

	/**
	 * 該当の投稿が既にいいねされているか確認と情報の呼び出し
	 * 
	 * @param articleId
	 * @return iine
	 * 
	 */
	Iine findByArticleId(Integer articleId);

	/**
	 * 該当の投稿に対してのいいね情報の更新
	 * 
	 * @param articleId
	 * @param count
	 * @return
	 * 
	 */
	void updateCount(Integer count, Integer articleId);

	/**
	 * いいねを押したのが2回目だった時に関連テーブルから情報をDELETEする
	 * 
	 * @param iineId
	 * @param userId
	 * @return
	 * 
	 */
	void deleteIine(Integer iineId, Integer userId);

}
