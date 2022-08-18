package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "iine_user")
public class IineAndUserJPA {

	@Id
	@Column(name = "iine_id")
	private Integer iineId;

	@Column(name = "user_id")
	private Integer userId;

	/** いいねした記事のID */
	@Column(name = "article_id")
	private Integer articleId;

	public Integer getIineId() {
		return iineId;
	}

	public void setIineId(Integer iineId) {
		this.iineId = iineId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
