package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.entity.IineJPA;
import com.example.service.IineServiceJPA;

@Controller
@CrossOrigin
@RequestMapping("/api")
public class IineController {

	@Autowired
	private IineServiceJPA iineServiceJPA;

	/**
	 * 
	 * いいねのカウント追加して結果を、JSONで返す
	 * 
	 * @return JSON
	 */
	@ResponseBody
	@RequestMapping(value = "/iine", method = RequestMethod.GET)
	public Map<String, Integer> iine(Integer articleId, Integer userId) {

		// いいね履歴に応じていいねカウントを処理
		IineJPA iine = new IineJPA();
		iine = iineServiceJPA.addIine(articleId, userId);

		// JSON用に情報を詰める
		Map<String, Integer> iineMap = new HashMap<>();
		if (iine == null) {
			iineMap.put("count", 0);
		} else {
			iineMap.put("count", iine.getCount());
		}
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

		// 投稿に対するいいねの情報のみ取得
		IineJPA iine = iineServiceJPA.findIine(articleId);

		Map<String, Integer> iineMap = new HashMap<>();
		if (iine == null) {
			iineMap.put("count", 0);
		} else {
			iineMap.put("count", iine.getCount());
		}
		return iineMap;
	}
}
