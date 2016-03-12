package cn.com.yunqitong.logic;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import cn.com.yunqitong.util.PropertyFactory;
import com.cloopen.rest.sdk.CCPRestSDK;
/**
* 项目名称：MessageServer   
* 类名称：MessageLogic   
* 创建人：huli   
* 创建时间：2016-1-25 下午5:14:37      
 */
@Service
public class MessageLogic {
	protected Logger log=Logger.getLogger(MessageLogic.class);
	//从配置文件中获取调用云通讯发送短信所需信息
	//地址
	String YTXADDR=PropertyFactory.getProperty("YTXADDR");
	//端口
	String YTXPORT=PropertyFactory.getProperty("YTXPORT");
	//sid
	String YTXACCOUNTSID=PropertyFactory.getProperty("YTXACCOUNTSID");
	
	//短信模板1,阿拉丁短信验证码模板
	String YTXTEMLATEIDONE=PropertyFactory.getProperty("YTXTEMLATEIDONE");
	
	//短信模板2,云企通短信验证码模板
	String YTXTEMLATEIDTWO=PropertyFactory.getProperty("YTXTEMLATEIDTWO");
		
	//短信模板3,阿拉丁呼叫通知短信模板
	String YTXTEMLATEIDTHREE=PropertyFactory.getProperty("YTXTEMLATEIDTHREE");
	
	//短信模板4,阿拉丁呼叫通知短信模板
	String YTXTEMLATEIDFOUR=PropertyFactory.getProperty("YTXTEMLATEIDFOUR");
	
	//token
	String YTXACCOUNTOKEN=PropertyFactory.getProperty("YTXACCOUNTOKEN");
	//应用id
	String YTXAPPID=PropertyFactory.getProperty("YTXAPPID");
	/**
	 * 请求发送短信
	 * @return
	 */
	public String sendMessage(String reqJson) {
		log.info("accept text "+reqJson);
		JSONObject requestJson = JSONObject.fromObject(reqJson);
		JSONObject getMessageResult=new JSONObject();
		String content1=requestJson.optString("content1");
		String phone=requestJson.optString("phone");
		String content2=requestJson.optString("content2");
		log.info("request send message start ");
		String templateid=null;
		int optype=requestJson.optInt("optype");
		int mainbody=requestJson.optInt("mainbody");
		if(mainbody==1){
			//云企通
			if(optype==1){
				//短信验证码
				templateid=YTXTEMLATEIDTWO;
			}
		}else{
			//否则请求主体为阿拉丁
			if(optype==1){
				//短信验证码
				templateid=YTXTEMLATEIDONE;
			}else{
				//否则为呼叫通知
				templateid=YTXTEMLATEIDFOUR;
			}
		}
		HashMap<String, Object> result = null;
		CCPRestSDK restAPI = new CCPRestSDK();
		restAPI.init(YTXADDR, YTXPORT);// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		restAPI.setAccount(YTXACCOUNTSID, YTXACCOUNTOKEN);// 初始化主帐号和主帐号TOKEN
		restAPI.setAppId(YTXAPPID);// 初始化应用ID
		if(templateid.equals(YTXTEMLATEIDFOUR)){
			//如果是呼叫则需添加一个参数
			String timestamp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss 时曾 ").format(new Date());
			result = restAPI.sendTemplateSMS(phone,templateid ,new String[]{content1,timestamp,content2});
		}else{
			result = restAPI.sendTemplateSMS(phone,templateid ,new String[]{content1,content2});
		}
		System.out.println("SDKTestSendTemplateSMS result=" + result);
		if("000000".equals(result.get("statusCode"))){
			log.info("request success ");
			//正常返回输出data包体信息（map）
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				log.info("response text from yunxuntong "+key+"=="+object);
			}
		}else{
			log.info("request failed ");
			//异常返回输出错误码和错误信息
			//System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
			getMessageResult.put("errorcode", PropertyFactory.getProperty("REQUESTERROR"));
			getMessageResult.put("msg", "获取验证码失败请重新获取");
			return getMessageResult.toString();
		}
		getMessageResult.put("errorcode", PropertyFactory.getProperty("SUCCESS"));
		getMessageResult.put("msg", "成功");
		return getMessageResult.toString();
	}

}
