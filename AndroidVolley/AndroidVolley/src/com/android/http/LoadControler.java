package com.android.http;

import com.android.volley.Request;

public interface LoadControler {
	void cancel();
}

class AbsLoadControler implements LoadControler {
	protected Request<?> mRequest;

	public void bindRequest(Request<?> request) {
		this.mRequest = request;
	}

	@Override
	public void cancel() {
		if (this.mRequest != null) {
			this.mRequest.cancel();
		}
	}
	
	protected String getOriginUrl() {
		return this.mRequest.getOriginUrl();
	}
}
