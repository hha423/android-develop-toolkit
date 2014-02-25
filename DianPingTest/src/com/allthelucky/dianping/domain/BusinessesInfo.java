package com.allthelucky.dianping.domain;

import java.io.Serializable;
import java.util.List;

public class BusinessesInfo implements Serializable {
	private static final long serialVersionUID = -5936093131078796688L;
	public String businessId;
	public String name;
	public String branch_name;
	public String address;
	public String telephone;
	public String city;
	public List<String> regions;
	public List<String> categories;
	public String latitude;
	public String longitude;
	public String avgRating;
	public String ratingImgUrl;
	public String ratingSImgUrl;
	public String productGrade;
	public String decorationGrade;
	public String serviceGrade;
	public String reviewCount;
	public String businessUrl;
	public String photoUrl;
	public String sPhotoUrl;
	public String has_coupon;
	public String couponId;
	public String couponDescription;
	public String couponUrl;
	public String hasDeal;
	public String dealCount;
	public List<String> deals;// {id;description;url}

	public BusinessesInfo() {

	}

	@Override
	public String toString() {
		return "BusinessesInfo [businessId=" + businessId + ", name=" + name + ", branch_name=" + branch_name
				+ ", address=" + address + ", telephone=" + telephone + ", city=" + city + ", regions=" + regions
				+ ", categories=" + categories + ", latitude=" + latitude + ", longitude=" + longitude + ", avgRating="
				+ avgRating + ", ratingImgUrl=" + ratingImgUrl + ", ratingSImgUrl=" + ratingSImgUrl + ", productGrade="
				+ productGrade + ", decorationGrade=" + decorationGrade + ", serviceGrade=" + serviceGrade
				+ ", reviewCount=" + reviewCount + ", businessUrl=" + businessUrl + ", photoUrl=" + photoUrl
				+ ", sPhotoUrl=" + sPhotoUrl + ", has_coupon=" + has_coupon + ", couponId=" + couponId
				+ ", couponDescription=" + couponDescription + ", couponUrl=" + couponUrl + ", hasDeal=" + hasDeal
				+ ", dealCount=" + dealCount + ", deals=" + deals + "]";
	}

}
