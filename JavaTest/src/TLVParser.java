
public class TLVParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String hex="9F370400000000950500000008009A031407149C01319F02060000000001005F2A0201569F1A0201569F030600000000000082027C009F3303A04000";
		System.out.println(parserTLV(hex));
	}
	
	public static String parserTLV(String hex){
		StringBuffer sbf=new StringBuffer(64);
		hex=hex.toUpperCase();
		int hexLen=hex.length();
		char flag=0;
		String t,l,v;
		int len=0;
		int index=0;
		boolean isDouble=false;
		int taglen=0;
		String r="\r\n";
		do{
			flag=hex.charAt(index+1);
			isDouble=(flag=='F');
			taglen=(isDouble?4:2);
			t=hex.substring(index+0, index+taglen);
			l=hex.substring(index+taglen,index+taglen+2);
			len=Integer.parseInt(l, 16)*2;
			v=hex.substring(index+taglen+2, index+taglen+2+len);
			index=index+taglen+2+len;
			sbf.append(t);
			sbf.append(r);
			sbf.append(l);
			sbf.append(r);
			sbf.append(v);
			sbf.append(r);
			System.out.println(t);
			System.out.println(l);
			System.out.println(v);
		}while(index<hexLen);
		
		return sbf.toString();
	}

}
