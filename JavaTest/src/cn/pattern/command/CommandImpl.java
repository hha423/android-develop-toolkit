package cn.pattern.command;

public class CommandImpl implements Command {

	private OnResultNotifier notifier;
	private int type = NONE;
	private String[] args;

	public CommandImpl(OnResultNotifier notifier, int type, String... args) {
		this.notifier = notifier;
		this.type = type;
		this.args = args;
	}

	@Override
	public void execute() throws Exception {
		Account account = Account.getInstance();
		switch (type) {
		case QUERY:
			notifier.onResult(account.query());
			break;
		case EXPENSE:
			notifier.onResult(account.expense(args[0]));
			break;
		case COMMON:
			notifier.onResult(account.common(args));
			break;
		default:
			break;
		}
	}

}
