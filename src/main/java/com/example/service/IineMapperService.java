package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Iine;
import com.example.mapper.IineMapper;

@Transactional
@Service
public class IineMapperService {

	@Autowired
	private IineMapper iineMapper;

	/**
	 * いいね情報の更新(既にいいねされているかどうかで分岐)
	 * 
	 * @param articleId
	 * @param userId
	 */
	public Iine addIine(Integer articleId, Integer userId) {

		Iine iine = new Iine();

		// articleIdから、該当の投稿に既にいいねがされているか判定
		iine = iineMapper.findByArticleId(articleId);

		// すでにいいねされていた場合
		if (iine.getId() != null) {

			// 該当の投稿に既に同一ユーザーがいいねしているかどうかを判定
			Iine iine2 = new Iine();
			iine2 = iineMapper.findByArticleIdAndUserId(articleId, userId);

			// まだ同一ユーザーはいいねしていなかった場合
			if (iine2 == null) {
				// いいねのカウントを+1
				Integer newCount = iine.getCount() + 1;
				iineMapper.updateCount(newCount, articleId);
				// 関連テーブルにも情報をインサート
				iineMapper.insertIineAndUser(iine.getId(), userId);
			} else { // すでに同一ユーザーがいいねしていた場合はいいねを取り消す
				// いいねのカウントを-1
				Integer newCount = iine.getCount() - 1;
				iineMapper.updateCount(newCount, articleId);
				// 関連テーブルから情報を削除
				iineMapper.deleteIine(iine.getId(), userId);
			}
		} else { // まだ一回もいいねされていなかった場合
			Integer iineId = iineMapper.insertIine(articleId, 1);
			iineMapper.insertIineAndUser(iineId, userId);
		}

		// 改めて最新情報を取得し、iineオブジェクトを返す
		iine = iineMapper.findByArticleId(articleId);
		return iine;
	}

	/**
	 * いいね情報の呼び出し
	 * 
	 * @param articleId
	 * @param userId
	 */
	public Iine findIine(Integer articleId) {
		return iineMapper.findByArticleId(articleId);
	}
}