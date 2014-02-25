package cn.pattern.facade;

/**
 * 	Facade
 */
public class Test {
	public static void main(String[] args) {
//		LetterProcess letterProcess = new LetterProcessImpl();
//		letterProcess.writeContent("hello, i love you");
//		letterProcess.fillEnvelope("wuhan hubei");
//		letterProcess.letterIntroEnvelope();
//		letterProcess.sendLetter();
		
		ModenPostOffice office = new ModenPostOffice();
		office.sendLtter("hello, i love you", "wuhan hubei");
	}
}
