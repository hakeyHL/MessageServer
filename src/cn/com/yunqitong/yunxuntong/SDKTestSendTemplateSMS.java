package cn.com.yunqitong.yunxuntong;

import java.util.HashMap;
import java.util.Set;

import com.cloopen.rest.sdk.CCPRestSDK;
import com.cloopen.rest.sdk.CCPRestSDK.BodyType;
/**
 * 
*  发送短信  
* 项目名称：CCPMessageServer   
* 类名称：SDKTestSendTemplateSMS   
* 创建人：huli   
* 创建时间：2016-1-20 下午3:29:09      
*
 */
public class SDKTestSendTemplateSMS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HashMap<String, Object> result = null;

		CCPRestSDK restAPI = new CCPRestSDK();
		restAPI.init("sandboxapp.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		restAPI.setAccount("8a48b5515249574b0152593b56141fc9", "fcc962ab49004282ac055a67ec9f9cdc");// 初始化主帐号和主帐号TOKEN
		restAPI.setAppId("8a48b5515249574b0152593c64361fcf");// 初始化应用ID
		result = restAPI.sendTemplateSMS("13022250556","1" ,new String[]{"8859","10"});

		System.out.println("SDKTestSendTemplateSMS result=" + result);
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				System.out.println(key +" = "+object);
			}
		}else{
			//异常返回输出错误码和错误信息
			System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
		}
	}

}
