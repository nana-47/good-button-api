package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Iine;
import com.example.service.IineService;

@Controller
@CrossOrigin
@RequestMapping("/api")
public class IineController {

	@Autowired
	private IineService iineService;

	private static final Logger LOGGER = LoggerFactory.getLogger(IineController.class);

	/**
	 * 
	 * いいねのカウント追加して結果を、JSONで返す
	 * 
	 * @return JSON
	 */
	@ResponseBody
	@RequestMapping(value = "/iine", method = RequestMethod.GET)
	public Map<String, Integer> iine(Integer articleId, Integer userId) {

		LOGGER.info("いいね情報の更新を行います");

		Iine iine = new Iine();
		try {
			// いいね履歴に応じていいねカウントを処理
			iine = iineService.addIine(articleId, userId);
		} catch (Exception e) {
			LOGGER.error("いいね情報の更新中にエラーが発生しました");
			e.printStackTrace();
		}

		// JSON用に情報を詰める
		Map<String, Integer> iineMap = new HashMap<>();
		iineMap.put("count", iine.getCount());

		LOGGER.info("いいね情報の更新が正常に完了しました");
		return iineMap;
	}

	/**
	 * 
	 * いいねのカウント結果を、JSONで返す
	 * 
	 * @return JSON
	 */
	@ResponseBody
	@RequestMapping(value = "/iine/show", method = RequestMethod.GET)
	public Map<String, Integer> showIine(Integer articleId) {

		LOGGER.info("いいねの最新情報を取得します");

		// 投稿に対するいいねの情報のみ取得
		Iine iine = iineService.findIine(articleId);

		Map<String, Integer> iineMap = new HashMap<>();
		iineMap.put("count", iine.getCount());

		LOGGER.info("いいねの最新情報が正常に取得できました");
		return iineMap;
	}

}
