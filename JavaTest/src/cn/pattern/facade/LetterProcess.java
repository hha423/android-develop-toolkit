package cn.pattern.facade;

public  interface LetterProcess {
	public void writeContent(String content);
	public void fillEnvelope(String address);
	public void letterIntroEnvelope();
	public void sendLetter();
}
