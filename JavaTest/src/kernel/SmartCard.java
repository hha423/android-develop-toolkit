package kernel;

public interface SmartCard {

	String getCSN();

	String[] getBankCardFileEntry();

	String getCardInfo();

}
