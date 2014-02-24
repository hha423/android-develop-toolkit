package com.allthelucky.memo.persistence;

import java.util.List;

import com.allthelucky.memo.domain.Sms;

public interface SmsMapper {
	
	public void addSms(Sms sms);

	public List<Sms> getSms(String mobile);

	public void updateSms(Sms sms);

	public void deleteSms(String mobile);
	
}
