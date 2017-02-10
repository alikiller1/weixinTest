package per.liuqh.weixin.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import per.liuqh.common.utils.weixin.CheckUtil;
import per.liuqh.weixin.service.WechatService;

@Controller
public class WeixinController {
	private Logger log=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WechatService wechatService;

	/**
     * 验证微信服务器
     * 
     * @param response
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     */
    @RequestMapping(value = "/wechat", method = RequestMethod.GET)
    public void check(PrintWriter out, HttpServletResponse response,
            @RequestParam(value = "signature", required = false) String signature, @RequestParam String timestamp,
            @RequestParam String nonce, @RequestParam String echostr) {
    	log.info("验证微信服务器-");
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
         log.info("验证微信服务器-验证通过");
            out.print(echostr);
        }
    }
    
    /**
     * 接收来自微信发来的消息
     * 
     * @param out
     * @param request
     * @param response
     */
    @RequestMapping(value = "/wechat", method = RequestMethod.POST)
    public void wechatServicePost(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
    	log.info("接收来自微信发来的消息-");
        String responseMessage = wechatService.processRequest(request);
        out.print(responseMessage);
        out.flush();
    }
}
