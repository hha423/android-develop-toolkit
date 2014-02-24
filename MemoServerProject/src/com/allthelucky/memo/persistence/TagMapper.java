package com.allthelucky.memo.persistence;

import java.util.List;
import java.util.Map;

import com.allthelucky.memo.domain.Tag;

public interface TagMapper {

	public void addTag(Tag tag);

	public int getCount();

	public List<Tag> getTags(Map<String, Integer> map);

}
