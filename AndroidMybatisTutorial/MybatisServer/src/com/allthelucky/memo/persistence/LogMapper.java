package com.allthelucky.memo.persistence;

import java.util.List;
import java.util.Map;

import com.allthelucky.memo.domain.Log;

public interface LogMapper {
	
	public void addLog(Log log);

	public int getCount();

	public List<Log> getLogs(Map<String, Integer> map);

}
