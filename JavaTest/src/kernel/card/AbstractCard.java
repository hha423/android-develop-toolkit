package kernel.card;

import kernel.IDevice;
import kernel.SmartCard;

public class AbstractCard implements SmartCard {
	IDevice dev = null;

	public AbstractCard(IDevice dev) {
		this.dev = dev;
	}

	@Override
	public String getCSN() {
		return null;
	}

	@Override
	public String[] getBankCardFileEntry() {
		return null;
	}

	@Override
	public String getCardInfo() {
		return null;
	}

}
