package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Iine;
import com.example.service.IineMapperService;

@Controller
@CrossOrigin
@RequestMapping("/api")
public class IineController {

//  Mybatis使用中のため一旦コメントアウト
//	@Autowired
//	private IineService iineService;

	@Autowired
	private IineMapperService iineMapperService;

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
		Iine iine = new Iine();
		iine = iineMapperService.addIine(articleId, userId);

		// JSON用に情報を詰める
		Map<String, Integer> iineMap = new HashMap<>();
		iineMap.put("count", iine.getCount());
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
		Iine iine = iineMapperService.findIine(articleId);

		Map<String, Integer> iineMap = new HashMap<>();

		if (iine == null) {
			iineMap.put("count", 0);
		} else {
			iineMap.put("count", iine.getCount());
		}

		return iineMap;
	}
}
