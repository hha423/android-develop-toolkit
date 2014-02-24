package com.allthelucky.examples.common;

import java.lang.reflect.Field;

import android.content.Context;

/**
 * R资源解析器
 * 
 * @author savant-pan
 * 
 */
public class ResourceUtils {

	/**
	 * 默认资源包名
	 */
	private final static String DEFAULT_PKG_NAME = "com.base.framework.common.R";

	/**
	 * 资源包名
	 */
	private static String packageNameR = DEFAULT_PKG_NAME;

	/**
	 * 初始化R资源解析器包名
	 * 
	 * @param context
	 */
	public static void init(Context context) {
		packageNameR = context.getPackageName() + ".R";
	}

	/**
	 * 获取String资源ID
	 * 
	 * @param stringName
	 *            String资源名
	 * @return
	 */
	public static int getStringId(String stringName) {
		return getResourceId("string", stringName);
	}

	/**
	 * 根据String资源名取String字符串
	 * 
	 * @param context
	 *            Context
	 * @param stringName
	 *            String资源名
	 * @return
	 */
	public static String getStringById(Context context, String stringName) {
		final int stringId = getStringId(stringName);
		if (stringId != -1) {
			return context.getString(stringId);
		} else {
			return "Error";
		}
	}
	/**
	 * 获取Color资源ID
	 * 
	 * @param colorName
	 *            Color资源名
	 * @return
	 */
	public static int getColorId(String colorName) {
		return getResourceId("color", colorName);
	}

	/**
	 * 获取Layout资源ID
	 * 
	 * @param layoutName
	 *            Layout资源名（文件名）
	 * @return
	 */
	public static int getLayoutId(String layoutName) {
		return getResourceId("layout", layoutName);
	}

	/**
	 * 获取Drawable资源ID
	 * 
	 * @param drawableName
	 *            Drawable资源名（文件名）
	 * @return
	 */
	public static int getDrawableId(String drawableName) {
		return getResourceId("drawable", drawableName);
	}

	/**
	 * 获取Raw资源ID
	 * 
	 * @param rawName
	 *            Raw资源名（文件名）
	 * @return
	 */
	public static int getRawId(String rawName) {
		return getResourceId("raw", rawName);
	}

	/**
	 * 获取Layout资源ID
	 * 
	 * @param styleName
	 *            Style资源名
	 * @return
	 */
	public static int getStyleId(String styleName) {
		return getResourceId("style", styleName);
	}

	/**
	 * 获取Attr资源ID
	 * 
	 * @param attrName
	 *            Attr资源名
	 * @return
	 */
	public static int getAttrId(String attrName) {
		return getResourceId("attr", attrName);
	}

	/**
	 * 获取id资源ID值
	 * 
	 * @param idName
	 *            ID资源名
	 * @return
	 */
	public static int getId(String idName) {
		return getResourceId("id", idName);
	}

	/**
	 * 取资源ID
	 * 
	 * @param resType
	 *            资源类型
	 * @param resName
	 *            资源名（文件名）
	 * @return
	 */
	public static int getResourceId(String resType, String resName) {
		if (packageNameR == null) {
			packageNameR = DEFAULT_PKG_NAME;
		}
		if ((resType != null) && (resName != null))
			try {
				Class<?> localClass = Class.forName(packageNameR + "$" + resType);
				Field localField = localClass.getField(resName);
				Object localObject = localField.get(localClass.newInstance());
				return Integer.parseInt(localObject.toString());
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		return -1;
	}
}
