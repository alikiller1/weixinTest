package per.liuqh.weixin.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import per.liuqh.common.utils.weixin.WechatMessageUtil;

@Service
public class WechatService {
    private static Logger log = LoggerFactory.getLogger(WechatService.class);

    public String processRequest(HttpServletRequest request) {
        Map<String, String> map = WechatMessageUtil.xmlToMap(request);
        log.info("接收到的消息->"+map.toString());
        // 发送方帐号（一个OpenID）
        String fromUserName = map.get("FromUserName");
        // 开发者微信号
        String toUserName = map.get("ToUserName");
        // 消息类型
        String msgType = map.get("MsgType");
        // 默认回复一个"success"
        String responseMessage = "success";
        // 对消息进行处理
        if (WechatMessageUtil.MESSAGE_TEXT.equals(msgType)) {// 文本消息
            responseMessage=" <xml>";
            responseMessage+="<ToUserName><![CDATA["+fromUserName+"]]></ToUserName>";
            responseMessage+="<FromUserName><![CDATA["+toUserName+"]]></FromUserName>";
            		responseMessage+="<CreateTime>"+System.currentTimeMillis()+"</CreateTime>";
            				responseMessage+="<MsgType><![CDATA[text]]></MsgType>";
            						responseMessage+="<Content><![CDATA[你为什么要说"+map.get("Content")+"]]></Content>";
            								responseMessage+="</xml>";
           
        }else if(WechatMessageUtil.MESSAGE_EVENT.equals(msgType)){
             responseMessage=" <xml>";
             responseMessage+="<ToUserName><![CDATA["+fromUserName+"]]></ToUserName>";
             responseMessage+="<FromUserName><![CDATA["+toUserName+"]]></FromUserName>";
             		responseMessage+="<CreateTime>"+System.currentTimeMillis()+"</CreateTime>";
             				responseMessage+="<MsgType><![CDATA[image]]></MsgType>";
             						responseMessage+="<Image><MediaId><![CDATA[yabihIE50uYUpM-0Ncd3I05FWkjCucnIIOZtUMJPNcY]]></MediaId></Image>";
             								responseMessage+="</xml>";
        }
        log.info("回复到的消息->"+responseMessage);
        return responseMessage;

    }
}

