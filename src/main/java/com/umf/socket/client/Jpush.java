package com.umf.socket.client;


import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.umf.config.Config;
import com.umf.utils.DateUtil;
import com.umf.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
@Component
public class Jpush {

	private DateUtil du = new DateUtil();
	@Autowired
	private LogUtil log;
	@Autowired
	private Config config;

	// ��������>>Android
	// Map<String, String> parm�����Լ��������Ĳ���,ͬѧ�ǿ����Զ������
	public void jpushAndroid(Map<String, String> parm) {
		// ����JPushClient(�������͵�ʵ��)
		JPushClient jpushClient = new JPushClient(config.getMaster_secret(), config.getApp_key());
		// ���͵Ĺؼ�,����һ��payload
		PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.android())// ָ��androidƽ̨���û�
				.setAudience(Audience.alias(parm.get("agentNum")))// registrationIdָ���û�
				.setNotification(Notification.android("�������Ӧ��", parm.get("message"), parm))
				// ��������
				.setOptions(Options.newBuilder().setApnsProduction(false).build())
				// ������ָ����������,��������Ҳû��ϵ
				.setMessage(Message.content(parm.get("message")))// �Զ�����Ϣ
				.build();

		try {
			jpushClient.sendPush(payload);
		} catch (APIConnectionException e) {
			e.printStackTrace();
			log.errorE(e);
		} catch (APIRequestException e) {
			e.printStackTrace();
			log.errorE(e);
		}
	}

	public void jpushIOS(Map<String, String> parm) {
		// ����JPushClient
		JPushClient jpushClient = new JPushClient(config.getMaster_secretios(), config.getApp_keyios());
		PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.ios())// iosƽ̨���û�
				.setAudience(Audience.alias(parm.get("agentNum")))// registrationIdָ���û�
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder().setAlert(parm.get("message")).setBadge(+1)
								.setSound("happy")// ������������ʾ��(�������ȥ��������)
								.addExtras(parm).build())
						.build())
				.setOptions(Options.newBuilder().setApnsProduction(true).build())
				.setMessage(Message.newBuilder().setMsgContent(parm.get("message")).addExtras(parm).build())// �Զ�����Ϣ
				.build();
		try {
			jpushClient.sendPush(payload);
		} catch (APIConnectionException e) {
			e.printStackTrace();
			log.errorE(e);
		} catch (APIRequestException e) {
			e.printStackTrace();
			log.errorE(e);
		}
	}

	public int jpushAll(Map<String, String> parm) {
		// ����JPushClient
		JPushClient jpushClient = new JPushClient(config.getMaster_secret(), config.getApp_key());
		// ����option
		PushPayload payload = PushPayload.newBuilder().setPlatform(Platform.all()) // ����ƽ̨���û�
				.setAudience(Audience.alias(parm.get("agentNum")))// registrationIdָ���û�
				.setNotification(Notification.newBuilder().addPlatformNotification(IosNotification.newBuilder() // ����ios
						.setAlert(parm.get("message")) // ��Ϣ��
						.setBadge(+1).setSound("happy") // ios��ʾ��
						.addExtras(parm) // ���Ӳ���
						.build()).addPlatformNotification(AndroidNotification.newBuilder() // ����android
								.addExtras(parm) // ���Ӳ���
								.setAlert(parm.get("message")) // ��Ϣ��
								.build())
						.build())
				.setOptions(Options.newBuilder().setApnsProduction(true).build())// ָ���������� trueΪ����ģʽ false Ϊ����ģʽ
																					// (android������ģʽ,ios����ģʽ)
				.setMessage(Message.newBuilder().setMsgContent(parm.get("message")).addExtras(parm).build())// �Զ�����Ϣ
				.build();
		try {
			jpushClient.sendPush(payload);
			return 1;
		} catch (APIConnectionException e) {
			e.printStackTrace();
			log.errorE(e);
		} catch (APIRequestException e) {
			e.printStackTrace();
			log.errorE(e);
		}
		return -1;
	}

	public void pAll(String agent_num, String level_tax_profit) throws Exception {
		Map map = new HashMap();
		map.put("agentNum", agent_num);
		map.put("message",
				"�𾴵ĺ������,��" + du.getCurrentDateTime("yyyy-MM-dd HH:mm:ss") + " ��������" + level_tax_profit + "Ԫ��");
//		jpushIOS(map);
		jpushAll(map);
	}
}
