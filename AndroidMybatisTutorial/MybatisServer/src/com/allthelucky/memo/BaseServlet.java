package com.allthelucky.memo;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.allthelucky.memo.utils.Utils;

/**
 * Servlet implementation class MemoBaseServlet
 */
@WebServlet("/BaseHttpServlet")
public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = -6862352916739044543L;

	private static final String ACTION_TAG = "action";
	private static final String SPEC_TAG = "spec";
	private static final String CODE_TAG = "code";
	private static final String DATA_TAG = "data";
	private static final String APPKEY_TAG = "appkey";
	private static final String APPSIGN_TAG = "sign";
	private static final String MESSAGE_TAG = "message";
	private static final String DEBUG_TAG = "debug";

	private static final String SUCCESS_MSG = "OK";
	private static final String EMPTY_POST = "empty post data";
	private static final String EMPTY_ACTION = "empty action";
	private static final String EMPTY_SPEC = "empty spec";
	private static final String EMPTY_DATA = "empty data";
	private static final String ERROR_SIGN = "error sign";
	private static final String EMPTY_APPKEY = "empty appkey";
	private static final String UNKNOW_APPKEY = "unknow appkey";

	protected static final String FAILED_CODE = "01";
	protected static final String SUCCESS_CODE = "00";

	protected static final String UNKNOW_ACTION = "unknow action";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BaseServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setResult(response, "post only");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(200);
		try {
			final JSONObject json = Utils.readJSONObject(request);
			final String message = dataValidate(json);
			if (!message.equals(SUCCESS_CODE)) {
				setResult(response, message);
				return;
			}
			this.doPost(response, json.optString(ACTION_TAG), json.optJSONObject(DATA_TAG));
		} catch (Exception e) {
			e.printStackTrace();
			setResult(response, e);
		}
	}

	/**
	 * 具体处理
	 * 
	 * @param response
	 * @param action
	 * @param data
	 * @throws IOException
	 */
	protected abstract void doPost(HttpServletResponse response, final String action, final JSONObject data)
			throws IOException;

	/**
	 * 请求JSON参数校验
	 * 
	 * @param json
	 *            JSONObject数据
	 * @return
	 */
	private String dataValidate(final JSONObject json) {
		if (null == json || !json.keys().hasNext()) {
			return EMPTY_POST;
		}
		final String action = json.optString(ACTION_TAG);
		if (isNullStr(action)) {
			return EMPTY_ACTION;
		}
		final JSONObject spec = json.optJSONObject(SPEC_TAG);
		if (spec == null || !spec.keys().hasNext()) {
			return EMPTY_SPEC;
		}
		final String appkey = spec.optString(APPKEY_TAG);
		if (isNullStr(appkey)) {
			return EMPTY_APPKEY;
		}
		if (!SecretUtils.hasKey(appkey)) {
			return UNKNOW_APPKEY;
		}
		final JSONObject data = json.optJSONObject(DATA_TAG);
		if (data == null) {
			return EMPTY_DATA;
		}
		final Map<String, Object> map = new HashMap<String, Object>();
		final Iterator<?> keys = data.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			map.put(key, data.get(key));
		}
		final String sign = spec.optString(APPSIGN_TAG);
		if (!sign.equalsIgnoreCase(SecretUtils.sign(appkey, SecretUtils.getSecret(appkey), action, map))) {
			return json.optBoolean(DEBUG_TAG, false) ? SUCCESS_CODE : ERROR_SIGN;
		}
		return SUCCESS_CODE;
	}

	/**
	 * 判断是否空字符串
	 * 
	 * @param str
	 * @return
	 */
	protected boolean isNullStr(String str) {
		return str == null || str.equals("") || str.equals("null");
	}

	/**
	 * 响应输出
	 * 
	 * @param response
	 *            　 HttpServletResponse对象　
	 * @param data
	 *            　响应消息或数据
	 * @throws IOException
	 */
	protected void setResult(HttpServletResponse response, Object data) throws IOException {
		final JSONObject result = new JSONObject();
		if (data != null) {
			if (data instanceof String) {
				if (SUCCESS_CODE.equals(data)) {// 成功提示“success”
					result.put(CODE_TAG, SUCCESS_CODE);
					result.put(MESSAGE_TAG, SUCCESS_MSG);
				} else {// 失败提示自定义消息
					setError(result, (String) data);
				}
			} else if (data instanceof Exception) {
				setError(result, ((Exception) data).getMessage());
			} else {
				result.put(CODE_TAG, SUCCESS_CODE);
				if (data instanceof JSONObject || data instanceof JSONArray || data instanceof Collection<?>) {
					result.put(DATA_TAG, data);
				} else {// Map及其它对象转JSONObject
					result.put(DATA_TAG, new JSONObject(data));
				}
			}
		}
		response.getWriter().write(result.toString());
	}

	/**
	 * 错误输出
	 */
	private void setError(final JSONObject result, String message) {
		result.put(CODE_TAG, FAILED_CODE);
		result.put(MESSAGE_TAG, message);
	}

}
