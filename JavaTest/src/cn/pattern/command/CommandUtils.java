package cn.pattern.command;

import cn.pattern.command.Handler.Message;


public class CommandUtils {
	
	final static CommandInvoker invoker = new CommandInvoker();

	public static void query(final Handler handler, final int actionId) {
		invoker.sendCommand(new CommandImpl(new OnResultNotifier() {
			@Override
			public void onResult(String result) {
				Message msg=new Message();
				msg.what = actionId;
				msg.obj = result;
				handler.sendMessage(msg);				
			}
		}, Command.QUERY));
	}

	public static void spend(String amount, final Handler handler, final int actionId) {
		invoker.sendCommand(new CommandImpl(new OnResultNotifier() {
			@Override
			public void onResult(String result) {
				Message msg=new Message();
				msg.what = actionId;
				msg.obj = result;
				handler.sendMessage(msg);				
			}
		}, Command.EXPENSE, amount));
	}
	
	public static void command(String cmd, final Handler handler, final int actionId) {
		invoker.sendCommand(new CommandImpl(new OnResultNotifier() {
			@Override
			public void onResult(String result) {
				Message msg=new Message();
				msg.what = actionId;
				msg.obj = result;
				handler.sendMessage(msg);				
			}
		}, Command.COMMON, cmd));
	}
}
