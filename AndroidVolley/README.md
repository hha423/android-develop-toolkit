基于Volley的网络请求工具。  
=====================
####一、说明  
AndroidVolley，Volley核心库工程(<https://github.com/mcxiaoke/android-volley>)。  
AndroidVolleySample，网络请求工具示例工程。

####二、Volley基本处理流程：  
1、应用初始化Volley。  
2、Volley创建一个RequestQueue、NetworkDispatcher组及Network。  
3、RequestQueue即一个Request队列，RequestQueue会创建一个ExecutorDelivery。  
4、NetworkDispatcher实质是Thread，从RequestQueue中取Request，通过Network加以执行。  
5、Network负责网络请求处理，具体过程交给HttpStack处理。  
6、HttpStack分HttpClient(SDK_INT>=9)与HttpURLConnection两种方式。  
7、ExecutorDelivery负责处理请求结果，并与主线程进行交互。  
8、Volley在上述2-7的基础上增加了Cache等附加处理环节。  

####三、网络请求工具示例  
#####1.init RequestManager
	public class VolleyApplication extends Application {
		@Override
		public void onCreate() {
			super.onCreate();
			RequestManager.getInstance().init(this);
		}
	
		@Override
		public void onTerminate() {
			super.onTerminate();
		}
	}

#####2.make request by RequestManager
	public class MainActivity extends Activity {
		private LoadControler loadControler = null;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);

			testPost();
			testGet();
		}
		
		/**
		 * test POST method
		 */
		private void testPost() {
			loadControler = RequestManager.getInstance().post("http://allthelucky.ap01.aws.af.cm/memoServer", null, requestListener, 0);
		}
		
		/**
		 * test GET method
		 */
		private void testGet() {
			loadControler = RequestManager.getInstance().get("http://allthelucky.ap01.aws.af.cm/memoServer", requestListener, 1);
		}

		/**
		 * RequestListener for receiving result
		 */
		private RequestListener requestListener = new RequestListener() {

			@Override
			public void onRequest() {

			}

			@Override
			public void onSuccess(String response, String url, int actionId) {
				System.out.println("actionId:"+actionId+", OnSucess!\n"+response);
			}

			@Override
			public void onError(String errorMsg, String url, int actionId) {
				System.out.println("actionId:"+actionId+", onError!\n"+errorMsg);
			}
		};

		@Override
		public void onBackPressed() {
			super.onBackPressed();
			loadControler.cancel();
		}

	}

