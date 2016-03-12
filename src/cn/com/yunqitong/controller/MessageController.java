package cn.com.yunqitong.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.yunqitong.logic.MessageLogic;
import cn.com.yunqitong.util.HttpsUtil;
/**
* 项目名称：MessageServer   
* 类名称：MessageController   
* 创建人：huli   
* 创建时间：2016-1-25 下午5:10:28      
 */
@Controller
@RequestMapping("/message")
public class MessageController {
	protected Logger log=Logger.getLogger(MessageController.class);
	@Autowired
	private MessageLogic messageLogic;
	@RequestMapping("/send")
	public String reqSendMessage(HttpServletRequest request,HttpServletResponse response){
		String reqJson = HttpsUtil.getJsonFromRequest(request);
		log.info("accept json "+reqJson);
		String responseText=messageLogic.sendMessage(reqJson);
		HttpsUtil.sendAppMessage(responseText, response);
		return null;
	}
	
}
