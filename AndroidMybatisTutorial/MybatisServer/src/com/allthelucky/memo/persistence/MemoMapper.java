package com.allthelucky.memo.persistence;

import java.util.List;
import java.util.Map;

import com.allthelucky.memo.domain.Memo;

public interface MemoMapper {

	public void addMemo(Memo memo);

	public void updateMemo(Memo memo);

	public void delMemo(int id);

	public int getCount();

	public List<Memo> getMemos(Map<String, Integer> map);

}
