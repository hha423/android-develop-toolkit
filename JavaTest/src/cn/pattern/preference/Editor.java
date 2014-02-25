package cn.pattern.preference;

public interface Editor {
	void put(String key, String Value);
	void commit();
}
