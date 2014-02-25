package cn.pattern.facade;

public class LetterProcessImpl implements LetterProcess {

	@Override
	public void writeContent(String content) {
		System.out.println("letter content is"+content);
	}

	@Override
	public void fillEnvelope(String address) {
		System.out.println("address:"+address);
	}

	@Override
	public void letterIntroEnvelope() {
		System.out.println("enveloping...");
	}

	@Override
	public void sendLetter() {
		System.out.println("sending...");
	}

}
