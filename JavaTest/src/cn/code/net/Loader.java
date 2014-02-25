package cn.code.net;
public interface Loader {
	public void request(String url, String params);
	public String getContent();
}