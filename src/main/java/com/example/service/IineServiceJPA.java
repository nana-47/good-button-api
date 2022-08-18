package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.IineJPA;
import com.example.repository.IineAndUserRepositoryJPA;
import com.example.repository.IineRepositoryJPA;

@Transactional
@Service
public class IineServiceJPA {

	@Autowired
	private IineRepositoryJPA iineRepositoryJPA;

	@Autowired
	private IineAndUserRepositoryJPA iineAndUserRepositoryJPA;

	/**
	 * いいね情報の更新(既にいいねされているかどうかで分岐)
	 * 
	 * @param articleId
	 * @param userId
	 */
	public IineJPA addIine(Integer articleId, Integer userId) {

		List<IineJPA> iineList = new ArrayList<>();

		// articleIdから、該当の投稿に既にいいねがされているか判定
		iineList = iineRepositoryJPA.findByArticleId(articleId);
		IineJPA iine = new IineJPA();
		if (iineList.size() == 0) {
			iine = null;
		} else {
			for (int i = 0; i < iineList.size(); i++) {
				iine = iineList.get(0);
			}
		}

		// すでにいいねされていた場合
		if (iine != null) {

			System.out.println("すでにいいねされていた判定済み");
			// 該当の投稿に既に同一ユーザーがいいねしているかどうかを判定
			List<IineJPA> iineList2 = iineAndUserRepositoryJPA.findByArticleIdAndUserId(articleId, userId);
			IineJPA iine2 = new IineJPA();
			if (iineList2.size() == 0) {
				iine2 = null;
			} else {
				for (int i = 0; i < iineList.size(); i++) {
					iine2 = iineList.get(0);
				}
			}

			// まだ同一ユーザーはいいねしていなかった場合
			if (iine2 == null) {
				System.out.println("いいねされてるけど同じユーザーはまだ");
				// いいねのカウントを+1
				Integer newCount = iine.getCount() + 1;
				iineRepositoryJPA.updateCount(newCount, articleId);
				// 関連テーブルにも情報をインサート
				iineAndUserRepositoryJPA.insertIineAndUser(iine.getId(), userId);
			} else { // すでに同一ユーザーがいいねしていた場合はいいねを取り消す
				// いいねのカウントを-1
				Integer newCount = iine.getCount() - 1;
				iineRepositoryJPA.updateCount(newCount, articleId);
				// 関連テーブルから情報を削除
				iineAndUserRepositoryJPA.deleteByIineIdAndUserId(iine.getId(), userId);
			}
		} else { // まだ一回もいいねされていなかった場合
			System.out.println("まだいいねされてない判定");
//			IineJPA iineJpa = new IineJPA();
//			iineJpa.setArticleId(articleId);
//			iineJpa.setCount(1);
			iineRepositoryJPA.saveIine(articleId, 1);
			Integer iineId = 1;
			iineAndUserRepositoryJPA.insertIineAndUser(iineId, userId);
		}
		System.out.println("全判定実行済み");

		// 改めて最新情報を取得し、iineオブジェクトを返す
		iineList = iineRepositoryJPA.findByArticleId(articleId);
		iine = new IineJPA();
		if (iineList.size() == 0) {
			iine = null;
		} else {
			for (int i = 0; i < iineList.size(); i++) {
				iine = iineList.get(0);
			}
		}
		return iine;
	}

	/**
	 * いいね情報の呼び出し
	 * 
	 * @param articleId
	 * @param userId
	 */
	public IineJPA findIine(Integer articleId) {
		List<IineJPA> iineList = iineRepositoryJPA.findByArticleId(articleId);
		IineJPA iine = new IineJPA();
		if (iineList.size() == 0) {
			iine = null;
		} else {
			for (int i = 0; i < iineList.size(); i++) {
				iine = iineList.get(0);
			}
		}
		System.out.println("呼び出し実行");
		return iine;
	}
//
//	EntityManager entityManager;
//
//	public IineServiceJPA(EntityManager entityManager) {
//		this.entityManager = entityManager;
//	}
//
//	public Integer saveIine(IineJPA iineJpa) {
//		entityManager.persist(iineJpa);
//		return iineJpa.getId();
//	}
}