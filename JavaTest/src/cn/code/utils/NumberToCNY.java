package cn.code.utils;

/**
 * 将数值型金额转换成大写金额
 *
 */
public class NumberToCNY {
	
	/**
	 * 将数字金额转换成大写金额
	 * @param number
	 * @return
	 */
	public static String changeNumberToCNY(String number){
		//如果传入""即为"空值"
		if ("".equals(number)) {
			return "空值，请重新输入！";
		}
		//非数字型
		double dNumber;
		try {
			dNumber = Math.abs(Double.parseDouble(number));
		} catch (NumberFormatException e) {
			return "输入错误，请重新输入！";
		}
		
		// 将这个数转换成 double类型，并对其取绝对值后进行四舍五入操作
		
		dNumber = (dNumber * 100);
		dNumber = Math.round(dNumber);
		dNumber = dNumber/100.0;
		// 将 dNumber进行格式化 否则会以科学计数型输出
		number = new java.text.DecimalFormat("##0.00").format(dNumber);
		
		//规定数值的最大长度只能到万亿单位，否则返回 "溢出"
		int index = number.indexOf(".");
		if (number.substring(0,index).length()>13) {
			return "数据溢出，请重新输入！";
		}
		return clearZero(splitNumber(number));
	}
	
	/**
	 * 将数字格式化成中文
	 * @param flag 1表示整数部分 2表示小数部分
	 * @param number
	 * @return
	 */
	private static String numberFormat(int flag, String number){
		// 货币大写形式
		String bigLetter[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
		// 货币单位
		String unit[] = {"元", "拾", "佰", "仟", "万", 
				// 拾万位到仟万位
				"拾", "佰", "仟",
				// 亿位到万亿位
				"亿", "拾", "佰", "仟", "万"};
		String small[] = {"角","分"};
		StringBuffer newNumber=new StringBuffer();
		for (int i = 0; i < number.length(); i++) {
			if (flag==1) {
				newNumber
					//char型数字转成int型数字相要-48  char 3 相当于 int的51
					.append(bigLetter[number.charAt(i)-48])
					//单位
					.append(unit[number.length()-i-1]);
			}
			if (flag==2) {
				newNumber
					//char型数字转成int型数字相要-48  char 3 相当于 int的51
					.append(bigLetter[number.charAt(i)-48])
					//单位
					.append(small[i]);
			}
		}
		return newNumber.toString();
	}
	
	/**
	 * 以小数点为界分割开
	 * @param number
	 * @return
	 */
	private static String splitNumber(String number){
		//取得小数点前后2部分
		int index = number.indexOf(".");
		String intOnly = number.substring(0,index);
		String smallOnly = number.substring(index+1);
		//将前后2部分转换成中文金额
		String intNew = numberFormat(1, intOnly);
		String smallNew = numberFormat(2, smallOnly);
		return intNew+smallNew;
	}
	
	/**
	 * 清理字符串中多余的零
	 * @param number
	 * @return
	 */
	private static String clearZero(String number){
		while(number.charAt(0) == '零') {
			// 将字符串中的 "零" 和它对应的单位去掉
			number = number.substring(2);
			// 如果用户当初输入的时候只输入了 0，则只返回一个 "零"
			if(number.length() == 0) {
				return "零元";
			}
		}
		// 字符串中存在多个'零'在一起的时候只读出一个'零'，并省略多余的单位
		String regex1[] = {"零仟", "零佰", "零拾"};
		String regex2[] = {"零亿", "零万", "零元"};
		String regex3[] = {"亿", "万", "元"};
		String regex4[] = {"零角", "零分"};
		// 第一轮转换把 "零仟", 零佰","零拾"等字符串替换成一个"零"
		for(int i = 0; i < 3; i ++) {
			number = number.replaceAll(regex1[i], "零");
		}
		// 第二轮转换考虑 "零亿","零万","零元"等情况
		// "亿","万","元"这些单位有些情况是不能省的，需要保留下来
		for(int i = 0; i < 3; i ++) {
			// 当第一轮转换过后有可能有很多个零叠在一起
			// 要把很多个重复的零变成一个零
			number = number.replaceAll("零零零", "零");
			number = number.replaceAll("零零", "零");
			number = number.replaceAll(regex2[i], regex3[i]);
		}
		// 第三轮转换把"零角","零分"字符串省略
		for(int i = 0; i < 2; i ++) {
			number = number.replaceAll(regex4[i], "");
		}
		// 当"万"到"亿"之间全部是"零"的时候，忽略"亿万"单位，只保留一个"亿"
		number = number.replaceAll("亿万", "亿");
		if (number.charAt(number.length()-1)=='元') {
			number = number+"整";
		}
		return number;
	}
}
