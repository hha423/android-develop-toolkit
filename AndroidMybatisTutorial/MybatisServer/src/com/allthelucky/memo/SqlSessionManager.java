package com.allthelucky.memo;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionManager {
	private static SqlSessionFactory sqlSessionFactory = null;

	static {
		try {
			LogFactory.getLog(SqlSessionManager.class);
			initConfiguration();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void initConfiguration() {
		try {
			sqlSessionFactory = new SqlSessionFactoryBuilder()
					.build(Resources.getResourceAsReader("Configuration.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final static SqlSessionFactory getFactory() {
		if (sqlSessionFactory == null) {
			initConfiguration();
		}
		return sqlSessionFactory;
	}

}
