package cn.pattern.preference;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ContextWrapper {
	public Object getSystemService(String name) {
		if(name.equals("share")) {
			return new SharePreferenceImpl();
		} else {
			return null;
		}
	}
	
	class SharePreferenceImpl implements SharePreference{

		@Override
		public Editor edit() {
			return new SharedEditor();
		}
	}
	
	class SharedEditor implements Editor {
		private Map<String, Object> values = new HashMap<String, Object>();
	
		@Override
		public void put(String key, String value) {
			values.put(key, value);
		}

		@Override
		public void commit() {
			Set<String> key = values.keySet();
			for(Iterator<?> it = key.iterator();it.hasNext();) {
				String k = (String) it.next();
				System.out.println(k+":"+values.get(k));
			}
		}
		
	}
}
