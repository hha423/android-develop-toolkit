package cn.pattern.facade;

public class ModenPostOffice {
	LetterProcess letterProcess = new LetterProcessImpl();
	
	public ModenPostOffice() {
		
	}
	
	public void sendLtter(final String content, final String address) {
		letterProcess.writeContent(content);
		letterProcess.fillEnvelope(address);
		letterProcess.letterIntroEnvelope();
		letterProcess.sendLetter();
	}
}
