package com.allthelucky.memo.persistence;

import java.util.List;
import java.util.Map;

import com.allthelucky.memo.domain.Reply;

public interface ReplyMapper {
	
	public void addReply(Reply reply);

	public int getCount();

	public List<Reply> getReplys(Map<String, Integer> map);
	
}
