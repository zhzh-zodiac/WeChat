package com.donghaiair.wechat.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.donghaiair.wechat.message.resp.TextMessage;
import com.donghaiair.wechat.util.MessageUtil;

/**
 * 核心服务类
 * @author zhzh
 * @data 2015-8-25
 */
public class CoreService {

	/**
	 * 处理微信发来的请求
	 */
	public static String processRequest(HttpServletRequest request){
		String respMessage = null;
		System.out.println("接收到的数据:"+request);
		try{
			//默认返回的文本消息内容
			String respContent = "请求处理异常，请稍后尝试!";
			//XML请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			//发送方账号(open_id)
			String fromUserName = requestMap.get("FromUserName");
			//公众账号
			String toUserName = requestMap.get("ToUserName");
			//消息类型
			String msgType = requestMap.get("MsgType");
			
			//回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			
			//文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				respContent = "您发送的是文本消息!";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "您发送的是图片消息!";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "你发送的是地理位置消息!";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "您发送的是链接消息!";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "你发送的是音频消息!";
			}else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				//时间类型
				String eventType = requestMap.get("Event");
				//订阅
				if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
					respContent = "欢迎关注!";
				}else if(eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){
					//取消关注后用户不再收到消息，故不需要回复
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
