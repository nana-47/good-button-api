package com.example.domain;

public class Iine {

	/** id(主キー) */
	private Integer id;
	/** いいねした記事のID */
	private Integer articleId;
	/** いいね数のカウント */
	private Integer count;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Iine [id=" + id + ", articleId=" + articleId + ", count=" + count + "]";
	}

}
