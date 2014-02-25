package cn.code.net;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NetWorkUtils {
	public RequestManager getRequestManager() {
		return new MyRequestManager();
	}

	class MyRequestManager implements RequestManager {

		@Override
		public Loader get() {
			return new MyLoader();
		}
	}

	class MyLoader implements Loader {
		String url;
		String params;
		
		@Override
		public String getContent() {
			System.out.println("url:"+url+",params:"+params);
			ExecutorService threadPool = Executors.newCachedThreadPool();
			Future<String> task = threadPool.submit(new Callable<String>() {
				@Override
				public String call() throws Exception {
					Thread.sleep(3000);
					return "hello";
				}
			});
			//task.cancel(true);
			
			try {
				return task.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public void request(String url, String params) {
			this.url = url;
			this.params = params;
		}
	}

}
