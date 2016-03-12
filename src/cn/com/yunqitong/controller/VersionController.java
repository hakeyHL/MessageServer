package cn.com.yunqitong.controller;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.yunqitong.util.GetVersion;
import cn.com.yunqitong.util.HttpsUtil;
/**
 * 版本获取
* 项目名称：MessageServer   
* 类名称：VersionController   
* 创建人：huli   
* 创建时间：2016-2-3 下午4:09:13      
*
 */
@Controller
public class VersionController {
	/**
	 * 获取版本信息
	 * @param request
	 * @param response
	 * @return 当前版本
	 */
	@RequestMapping("/version")
	public String getVersion(HttpServletResponse response){
		String yqtversion = GetVersion.version;
		HttpsUtil.sendAppMessage(yqtversion, response);
		return null;
	}
}
