package com.allthelucky.dianping.domain;

import java.io.Serializable;
import java.util.List;

public class CategoryItem implements Serializable {

	private static final long serialVersionUID = 7241004204669834174L;
	public String categoryName;
	public List<String> subcategories;

	public CategoryItem(String categoryName, List<String> subcategories) {
		super();
		this.categoryName = categoryName;
		this.subcategories = subcategories;
	}

	@Override
	public String toString() {
		return "CategoryItem [categoryName=" + categoryName + ", subcategories=" + subcategories + "]";
	}

}
