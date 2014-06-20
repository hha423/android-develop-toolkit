package com.allthelucky.memo.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.allthelucky.memo.BaseServlet;
import com.allthelucky.memo.domain.User;
import com.allthelucky.memo.services.MemoService;

/**
 * Servlet implementation class MemoServerServlet
 */
@WebServlet("/MemoServlet")
public class MemoServlet extends BaseServlet {
	private static final long serialVersionUID = -5801815045526237172L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MemoServlet() {
		super();
	}

	protected void doPost(HttpServletResponse response, final String action, final JSONObject data) throws IOException {
		if (Actions.GET_CODE.equals(action)) {
			getValidateCode(response, data.optString("username"));
		} else if (Actions.ADD_USER.equals(action)) {
			addUser(response, data.optString("username"), data.optString("password"), data.optString("code"));
		} else if (Actions.UPDATE_USER.equals(action)) {
			updateUser(response, data.optInt("id"), data.optString("username"), data.optString("password"));
		} else if (Actions.CHECK_USER.equals(action)) {
			checkUser(response, data.optString("username"), data.optString("password"));
		} else if (Actions.GET_USERS.equals(action)) {
			getUsers(response, data.optInt("offset"), data.optInt("size"));
		} else if (Actions.ADD_TAG.equals(action)) {
			addTag(response, data.optString("name"));
		} else if (Actions.GET_TAGS.equals(action)) {
			getTags(response, data.optInt("offset"), data.optInt("size"));
		} else if (Actions.ADD_MEMO.equals(action)) {
			addMemo(response, data.optInt("userid"), data.optString("title"), data.optString("content"),
					data.optString("tag"), data.optString("createt"), data.optString("updatet"));
		} else if (Actions.UPDATE_MEMO.equals(action)) {
			updateMemo(response, data.optInt("id"), data.optString("title"), data.optString("content"),
					data.optString("tag"), data.optString("updatet"));
		} else if (Actions.DEL_MEMO.equals(action)) {
			delMemo(response, data.optInt("id"));
		} else if (Actions.GET_MEMOS.equals(action)) {
			getMemos(response, data.optInt("offset"), data.optInt("size"));
		} else if (Actions.ADD_SUGEST.equals(action)) {
			addSugest(response, data.optInt("userid"), data.optString("content"));
		} else if (Actions.GET_SUGESTS.equals(action)) {
			getSugests(response, data.optInt("offset"), data.optInt("size"));
		} else if (Actions.ADD_REPLY.equals(action)) {
			addReply(response, data.optInt("userid"), data.optInt("sugestid"), data.optString("content"));
		} else if (Actions.GET_REPLYS.equals(action)) {
			getReplys(response, data.optInt("offset"), data.optInt("size"));
		} else if (Actions.GET_LOGS.equals(action)) {
			getLogs(response, data.optInt("offset"), data.optInt("size"));
		} else {
			setResult(response, UNKNOW_ACTION);
		}
	}

	private void getValidateCode(HttpServletResponse response, String mobile) throws IOException {
		setResult(response, MemoService.getCodeByMoile(mobile));
	}

	private void addUser(HttpServletResponse response, String username, String password, String code)
			throws IOException {
		final Object result = MemoService.validateCodeByMoile(username, code);
		if (!SUCCESS_CODE.equals(result)) {
			setResult(response, result);
			return;
		}
		setResult(response, MemoService.addUser(username, password));
	}

	private void updateUser(HttpServletResponse response, int userid, String username, String password)
			throws IOException {
		if (userid == 0) {
			setResult(response, "用户ID错误");
			return;
		}
		setResult(response, MemoService.updateUser(userid, username, password));
	}

	private void checkUser(HttpServletResponse response, String username, String password) throws IOException {
		final User user = new User();
		user.setUsername(username);
		user.setPassword(password);

		final User result = (User) MemoService.checkUser(username, password);
		if (result == null) {
			setResult(response, "用户密码不匹配");
			return;
		}
		addLog(result.getId());
		setResult(response, result);
	}

	private void getUsers(HttpServletResponse response, int offset, int size) throws IOException {
		if (offset == 0) {
			offset = 1;
		}
		if (size < 0) {
			size = 20;
		}
		setResult(response, MemoService.getUsers(offset, size));
	}

	private void addTag(HttpServletResponse response, String name) throws IOException {
		setResult(response, MemoService.addTag(name));
	}

	private void getTags(HttpServletResponse response, int offset, int size) throws IOException {
		if (offset == 0) {
			offset = 1;
		}
		if (size < 0) {
			size = 20;
		}
		setResult(response, MemoService.getTags(offset, size));
	}

	private void addMemo(HttpServletResponse response, int userid, String title, String content, String tag,
			String createt, String updatet) throws IOException {
		if (userid == 0) {
			setResult(response, "用户ID错误");
			return;
		}
		setResult(response, MemoService.addMemo(userid, title, content, tag, createt, updatet));
	}

	private void updateMemo(HttpServletResponse response, int memoid, String title, String content, String tag,
			String updatet) throws IOException {
		if (memoid == 0) {
			setResult(response, "Memo ID错误");
			return;
		}
		setResult(response, MemoService.updateMemo(memoid, title, content, tag, updatet));
	}

	private void delMemo(HttpServletResponse response, int memoid) throws IOException {
		if (memoid == 0) {
			setResult(response, "Memo ID错误");
			return;
		}
		setResult(response, MemoService.delMemo(memoid));
	}

	private void getMemos(HttpServletResponse response, int offset, int size) throws IOException {
		if (offset == 0) {
			offset = 1;
		}
		if (size < 0) {
			size = 20;
		}
		setResult(response, MemoService.getMemos(offset, size));
	}

	private void addSugest(HttpServletResponse response, int userid, String content) throws IOException {
		setResult(response, MemoService.addSugest(userid, content));
	}

	private void getSugests(HttpServletResponse response, int offset, int size) throws IOException {
		if (offset == 0) {
			offset = 1;
		}
		if (size < 0) {
			size = 20;
		}
		setResult(response, MemoService.getSugests(offset, size));
	}

	private void addReply(HttpServletResponse response, int userid, int sugestid, String content) throws IOException {
		setResult(response, MemoService.addReply(userid, sugestid, content));
	}

	private void getReplys(HttpServletResponse response, int offset, int size) throws IOException {
		if (offset == 0) {
			offset = 1;
		}
		if (size < 0) {
			size = 20;
		}
		setResult(response, MemoService.getReplys(offset, size));
	}

	private void addLog(int userid) {
		MemoService.addLog(userid);
	}

	private void getLogs(HttpServletResponse response, int offset, int size) throws IOException {
		if (offset == 0) {
			offset = 1;
		}
		if (size < 0) {
			size = 20;
		}
		setResult(response, MemoService.getLogs(offset, size));
	}

}
