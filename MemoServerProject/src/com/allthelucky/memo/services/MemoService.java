package com.allthelucky.memo.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;

import com.allthelucky.memo.SqlSessionManager;
import com.allthelucky.memo.domain.Log;
import com.allthelucky.memo.domain.Memo;
import com.allthelucky.memo.domain.Reply;
import com.allthelucky.memo.domain.Sms;
import com.allthelucky.memo.domain.Sugest;
import com.allthelucky.memo.domain.Tag;
import com.allthelucky.memo.domain.User;
import com.allthelucky.memo.persistence.LogMapper;
import com.allthelucky.memo.persistence.MemoMapper;
import com.allthelucky.memo.persistence.ReplyMapper;
import com.allthelucky.memo.persistence.SmsMapper;
import com.allthelucky.memo.persistence.SugestMapper;
import com.allthelucky.memo.persistence.TagMapper;
import com.allthelucky.memo.persistence.UserMapper;
import com.allthelucky.memo.utils.Utils;

public class MemoService {
	protected static final String SUCCESS_CODE = "00";

	public static Object addUser(String username, String password) {
		final User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setLevel(0);
		final SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			((UserMapper) session.getMapper(UserMapper.class)).addUser(user);
			session.commit();
			return SUCCESS_CODE;
		} catch (Exception e) {
			return e.getCause().getLocalizedMessage();
		} finally {
			session.close();
		}
	}

	public static Object updateUser(int id, String username, String password) {
		final User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setId(id);

		final SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			((UserMapper) session.getMapper(UserMapper.class)).udpateUser(user);
			session.commit();
			return SUCCESS_CODE;
		} catch (Exception e) {
			return e.getCause().getLocalizedMessage();
		} finally {
			session.close();
		}
	}

	public static Object checkUser(String username, String password) {
		final User user = new User();
		user.setUsername(username);
		user.setPassword(password);

		final SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			List<User> users = ((UserMapper) session.getMapper(UserMapper.class)).checkUser(user);
			if (users == null || users.size() == 0) {
				return null;
			} else {
				return users.get(0);
			}
		} catch (Exception e) {
			return null;
		} finally {
			session.close();
		}
	}

	public static Object getUsers(int offset, int size) {
		final Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("offset", (offset - 1));
		map.put("size", size);

		final JSONObject result = new JSONObject();
		final SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			List<User> list = ((UserMapper) session.getMapper(UserMapper.class)).getUsers(map);
			int count = ((UserMapper) session.getMapper(UserMapper.class)).getCount();
			result.put("count", count);
			result.put("list", list);
			return result;
		} catch (Exception e) {
			return result;
		} finally {
			session.close();
		}
	}

	public static Object addTag(String name) {
		final Tag tag = new Tag();
		tag.setName(name);
		final SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			((TagMapper) session.getMapper(TagMapper.class)).addTag(tag);
			session.commit();
			return SUCCESS_CODE;
		} catch (Exception e) {
			return e.getCause().getLocalizedMessage();
		} finally {
			session.close();
		}
	}

	public static Object getTags(int offset, int size) {
		final Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("offset", (offset - 1));
		map.put("size", size);

		final JSONObject result = new JSONObject();
		final SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			int count = ((TagMapper) session.getMapper(TagMapper.class)).getCount();
			List<Tag> list = ((TagMapper) session.getMapper(TagMapper.class)).getTags(map);
			result.put("count", count);
			result.put("list", list);
			return result;
		} catch (Exception e) {
			return result;
		} finally {
			session.close();
		}
	}

	public static Object addMemo(int userid, String title, String content, String tag, String createt, String updatet) {
		final Memo memo = new Memo();
		memo.setUserid(userid);
		memo.setTitle(title);
		memo.setContent(content);
		memo.setTag(tag);
		memo.setCreatet(createt);
		memo.setUpdatet(updatet);

		final SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			((MemoMapper) session.getMapper(MemoMapper.class)).addMemo(memo);
			session.commit();
			return SUCCESS_CODE;
		} catch (Exception e) {
			return e.getCause().getLocalizedMessage();
		} finally {
			session.close();
		}
	}

	public static Object updateMemo(int id, String title, String content, String tag, String updatet) {
		final Memo memo = new Memo();
		memo.setId(id);
		memo.setTitle(title);
		memo.setContent(content);
		memo.setTag(tag);
		memo.setUpdatet(updatet);

		final SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			((MemoMapper) session.getMapper(MemoMapper.class)).updateMemo(memo);
			session.commit();
			return SUCCESS_CODE;
		} catch (Exception e) {
			return e.getCause().getLocalizedMessage();
		} finally {
			session.close();
		}
	}

	public static Object delMemo(int id) {
		final SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			((MemoMapper) session.getMapper(MemoMapper.class)).delMemo(id);
			session.commit();
			return SUCCESS_CODE;
		} catch (Exception e) {
			return e.getCause().getLocalizedMessage();
		} finally {
			session.close();
		}
	}

	public static Object getMemos(int offset, int size) {
		final Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("offset", (offset - 1));
		map.put("size", size);

		final JSONObject result = new JSONObject();
		final SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			int count = ((MemoMapper) session.getMapper(MemoMapper.class)).getCount();
			List<Memo> list = ((MemoMapper) session.getMapper(MemoMapper.class)).getMemos(map);
			result.put("count", count);
			result.put("list", list);
			return result;
		} catch (Exception e) {
			return result;
		} finally {
			session.close();
		}
	}

	/**
	 * 验证码校验
	 * 
	 * @param sessionFactory
	 * @param mobile
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public static Object validateCodeByMoile(final String mobile, final String code) {
		final Sms sms = getSms(mobile);
		if (sms != null && code.equals(sms.getCode())) {
			if (!isElapse(sms.getCreatetime())) {
				clearSms(mobile);
				return SUCCESS_CODE;
			} else {
				return "验证码已过期";
			}
		}
		return "验证码错误";
	}

	/**
	 * 生成验证码，如果120内提示失败，未生成则创建，已生成则更新
	 * 
	 * @param response
	 * @param mobile
	 * @throws IOException
	 */
	public static Object getCodeByMoile(final String mobile) {
		final Sms sms = getSms(mobile);
		if (sms != null && !isElapse(sms.getCreatetime())) {
			return "120秒内重复生成";
		}
		final Sms newSms = new Sms();
		SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			final SmsMapper mapper = session.getMapper(SmsMapper.class);
			final String code = getRandom();
			newSms.setMobile(mobile);
			newSms.setCode(code);
			newSms.setCreatetime(Utils.currentTimeMillis());
			if (sms == null) {
				mapper.addSms(newSms);
			} else {
				mapper.updateSms(newSms);
			}
			session.commit();
			sendSmsInBackground(mobile, code);
			return SUCCESS_CODE;
		} catch (Exception e) {
			return e.getCause().getLocalizedMessage();
		} finally {
			session.close();
		}
	}

	/**
	 * 发送短信
	 * 
	 * @param mobile
	 * @param code
	 */
	private static void sendSmsInBackground(final String mobile, final String code) {
		final StringBuilder sb = new StringBuilder();
		sb.append("account=").append("cf_winfirm");
		sb.append("&password=").append("123456");
		sb.append("&mobile=").append(mobile);
		sb.append("&content=").append("您的验证码是：");
		sb.append(code).append("。请不要把验证码泄露给其他人。");
		final String postData = sb.toString();

		new Thread(new Runnable() {
			@Override
			public void run() {
				Utils.postData("http://106.ihuyi.com/webservice/sms.php?method=Submit", postData);
			}
		}).start();
	}

	/**
	 * 生成6位随机数
	 * 
	 * @return
	 */
	private static String getRandom() {
		final int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		final Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 6; i++)
			sb.append(array[i]);
		return sb.toString();
	}

	/**
	 * 是否已过期(120s)
	 */
	private static boolean isElapse(String createtime) {
		return System.currentTimeMillis() > (Long.valueOf(createtime) + 120 * 1000L);
	}

	/**
	 * 检测验证码是否过期
	 * 
	 * @param mobile
	 * @param code
	 * @return
	 */
	private static Sms getSms(final String mobile) {
		SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			final SmsMapper mapper = session.getMapper(SmsMapper.class);
			List<Sms> list = mapper.getSms(mobile);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			return null;
		} finally {
			session.close();
		}
	}

	/**
	 * 验证成功，删除验证码记录
	 * 
	 * @param sessionFactory
	 * 
	 * @param response
	 * @param mobile
	 * @throws IOException
	 */
	private static void clearSms(final String mobile) {
		SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			final SmsMapper mapper = session.getMapper(SmsMapper.class);
			mapper.deleteSms(mobile);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static Object addSugest(int userid, String content) {
		final Sugest sugest = new Sugest();
		sugest.setUserid(userid);
		sugest.setContent(content);
		sugest.setCreatetime(Utils.currentTimeMillis());
		final SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			((SugestMapper) session.getMapper(SugestMapper.class)).addSugest(sugest);
			session.commit();
			return SUCCESS_CODE;
		} catch (Exception e) {
			return e.getCause().getLocalizedMessage();
		} finally {
			session.close();
		}
	}

	public static Object getSugests(int offset, int size) {
		final Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("offset", (offset - 1));
		map.put("size", size);

		final JSONObject result = new JSONObject();
		final SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			List<Sugest> list = ((SugestMapper) session.getMapper(SugestMapper.class)).getSugests(map);
			int count = ((SugestMapper) session.getMapper(SugestMapper.class)).getCount();
			result.put("count", count);
			result.put("list", list);
			return result;
		} catch (Exception e) {
			return result;
		} finally {
			session.close();
		}
	}

	public static Object addReply(int userid, int sugestid, String content) {
		final Reply reply = new Reply();
		reply.setUserid(userid);
		reply.setSugestid(sugestid);
		reply.setContent(content);
		reply.setCreatetime(Utils.currentTimeMillis());
		final SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			((ReplyMapper) session.getMapper(ReplyMapper.class)).addReply(reply);
			session.commit();
			return SUCCESS_CODE;
		} catch (Exception e) {
			return e.getCause().getLocalizedMessage();
		} finally {
			session.close();
		}
	}

	public static Object getReplys(int offset, int size) {
		final Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("offset", (offset - 1));
		map.put("size", size);

		final JSONObject result = new JSONObject();
		final SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			List<Reply> list = ((ReplyMapper) session.getMapper(ReplyMapper.class)).getReplys(map);
			int count = ((ReplyMapper) session.getMapper(ReplyMapper.class)).getCount();
			result.put("count", count);
			result.put("list", list);
			return result;
		} catch (Exception e) {
			return result;
		} finally {
			session.close();
		}
	}

	public static Object addLog(int userid) {
		final Log log = new Log();
		log.setUserid(userid);
		log.setCreatetime(Utils.currentTimeMillis());
		final SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			((LogMapper) session.getMapper(LogMapper.class)).addLog(log);
			session.commit();
			return SUCCESS_CODE;
		} catch (Exception e) {
			return e.getCause().getLocalizedMessage();
		} finally {
			session.close();
		}
	}

	public static Object getLogs(int offset, int size) {
		final Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("offset", (offset - 1));
		map.put("size", size);

		final JSONObject result = new JSONObject();
		final SqlSession session = SqlSessionManager.getFactory().openSession();
		try {
			List<Log> list = ((LogMapper) session.getMapper(LogMapper.class)).getLogs(map);
			int count = ((ReplyMapper) session.getMapper(ReplyMapper.class)).getCount();
			result.put("count", count);
			result.put("list", list);
			return result;
		} catch (Exception e) {
			return result;
		} finally {
			session.close();
		}
	}
}
