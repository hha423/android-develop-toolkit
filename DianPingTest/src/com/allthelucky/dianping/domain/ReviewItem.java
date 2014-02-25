package com.allthelucky.dianping.domain;

import java.io.Serializable;

public class ReviewItem implements Serializable {
	private static final long serialVersionUID = 8200309940190062525L;
	public String reviewId;
	public String userNickname;
	public String textExcerpt;
	public String reviewRating;
	public String ratingImgUrl;
	public String ratingSImageUrl;
	public String productRating;
	public String decorationRating;
	public String serviceRating;
	public String reviewUrl;

	@Override
	public String toString() {
		return "ReviewItem [reviewId=" + reviewId + ", userNickname=" + userNickname + ", textExcerpt=" + textExcerpt
				+ ", reviewRating=" + reviewRating + ", ratingImgUrl=" + ratingImgUrl + ", ratingSImageUrl="
				+ ratingSImageUrl + ", productRating=" + productRating + ", decorationRating=" + decorationRating
				+ ", serviceRating=" + serviceRating + ", reviewUrl=" + reviewUrl + "]";
	}

}
