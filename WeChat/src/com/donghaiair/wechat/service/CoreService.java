package com.donghaiair.wechat.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.donghaiair.wechat.message.resp.TextMessage;
import com.donghaiair.wechat.util.MessageUtil;

/**
 * ���ķ�����
 * @author zhzh
 * @data 2015-8-25
 */
public class CoreService {

	/**
	 * ����΢�ŷ���������
	 */
	public static String processRequest(HttpServletRequest request){
		String respMessage = null;
		System.out.println("���յ�������:"+request);
		try{
			//Ĭ�Ϸ��ص��ı���Ϣ����
			String respContent = "�������쳣�����Ժ���!";
			//XML�������
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			//���ͷ��˺�(open_id)
			String fromUserName = requestMap.get("FromUserName");
			//�����˺�
			String toUserName = requestMap.get("ToUserName");
			//��Ϣ����
			String msgType = requestMap.get("MsgType");
			
			//�ظ��ı���Ϣ
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			
			//�ı���Ϣ
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				respContent = "�����͵����ı���Ϣ!";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "�����͵���ͼƬ��Ϣ!";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "�㷢�͵��ǵ���λ����Ϣ!";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "�����͵���������Ϣ!";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "�㷢�͵�����Ƶ��Ϣ!";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				//ʱ������
				String eventType = requestMap.get("Event");
				//����
				if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
					respContent = "��ӭ��ע!";
				}else if(eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){
					//ȡ����ע���û������յ���Ϣ���ʲ���Ҫ�ظ�
				}else if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					
				}
			}
			
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}
}
