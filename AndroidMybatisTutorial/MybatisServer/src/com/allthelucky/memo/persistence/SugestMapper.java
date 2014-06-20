package com.allthelucky.memo.persistence;

import java.util.List;
import java.util.Map;

import com.allthelucky.memo.domain.Sugest;

public interface SugestMapper {

	public void addSugest(Sugest sugest);

	public int getCount();

	public List<Sugest> getSugests(Map<String, Integer> map);

}
