package cn.com.yunqitong.yunxuntong;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Set;

import com.cloopen.rest.sdk.CCPRestSDK;
import com.cloopen.rest.sdk.CCPRestSDK.BodyType;

public class SDKTestMediaFileUpload {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HashMap<String, Object> result = null;

		CCPRestSDK restAPI = new CCPRestSDK();
		restAPI.init("app.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https:// ，只支持生产环境使用
		restAPI.setAccount("accountSid", "accountToken");// 初始化主帐号和主帐号TOKEN
		restAPI.setAppId("AppId");// 初始化应用ID
		
		String requsetbody="";
		File file = new File("文件路径");//写您要上传的文件所在路径
		try {
			Reader reader = null;
			reader = new InputStreamReader(new FileInputStream(file));
			int tempchar;
			while ((tempchar = reader.read()) != -1){
				requsetbody+=(char)tempchar;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		result = restAPI.MediaFileUpload("文件名", requsetbody);//上传到云平台后显示的名字

		System.out.println("SDKTestMediaFileUpload result=" + result);
		
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");

		}else{
			//异常返回输出错误码和错误信息
			System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
		}
	}

}
