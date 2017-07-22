package life.playTogether.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import life.playTogether.entity.Activity;
import life.playTogether.service.AddActivityService;
import persionalCenter.entity.UserInfo;
import persionalCenter.service.UserService;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;


@Component(value="addActivityAction")
@Scope(value="prototype")
public class AddActivityAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		//注入属性
		@Resource(name="activityService")
		private AddActivityService activityService;
		@Resource(name="User_Service")
		private UserService userService;
		@Resource(name="activity")
		private Activity activity;
		
		private String a_phone;
		private String a_userName;
		private String a_userImg;
		private String a_title;
		private String a_content;
		private String a_shareUrl;	
		
		private File a_img;
		private String a_imgFileName;
		private String a_imgContentType;
		
		@SuppressWarnings("deprecation")
		public String upload() throws IOException{
			
			HttpServletRequest request= ServletActionContext.getRequest();
			HttpServletResponse response= ServletActionContext.getResponse();
			
			response.setCharacterEncoding("UTF-8");
      		response.setHeader("Content-type", "text/html;charset=UTF-8");
      		PrintWriter out = response.getWriter();
      		String fullPath=null;
      		String uploadFullPath = null;
      		boolean isSuccessful = false;
      		if (a_img!=null) {
			String realPath = request.getRealPath("/").substring(0, request.getRealPath("/").lastIndexOf(request.getContextPath().replace("/", "")));
			System.out.println(realPath);
			File file = new File(realPath+File.separator+"uploadFiles"+File.separator);
			//创建文件上传的位置
	        if(!file.exists() ){
	            file.mkdirs();   
	            System.out.println("文件夹不存在已创建");
	        }else{
	            System.out.println("文件夹已经存在");
	        }
			String strNewFileName = UUID.randomUUID().toString();
			String suffix=a_imgFileName.substring(a_imgFileName.lastIndexOf("."));
			strNewFileName +=suffix;
			System.out.println(strNewFileName);
			fullPath=realPath+"uploadFiles"+File.separator+strNewFileName;
			System.out.println("上传路径："+fullPath);
			uploadFullPath = fullPath;
			fullPath="http://192.168.1.100:8080/uploadFiles/"+strNewFileName;
			}
			
			UserInfo userinfo = new UserInfo();
			userinfo.setPhone(a_phone);
			String[] user = userService.query(userinfo);
			if (user[2]==null||user[2]=="") {
		    	 out.println(isSuccessful);
		    	 out.flush();
		    	 out.close();
		    	 return NONE;
			}
			a_userName = user[1];
			a_userImg = user[0];
			activity.setA_phone(a_phone);
			activity.setA_userName(a_userName);
			activity.setA_userImg(a_userImg);
			activity.setA_content(a_content);
			activity.setA_img(fullPath);
			activity.setA_shareUrl(a_shareUrl);
			activity.setA_title(a_title);
			activity.setA_userImg(a_userImg);
			activity.setA_userName(a_userName);
			activityService.save(activity);
			isSuccessful = true;
			out.println(isSuccessful);
			out.flush();
			out.close();
			
			if (a_img!=null) {
				FileOutputStream fos=new FileOutputStream(uploadFullPath);
				FileInputStream fis=new FileInputStream(getA_img());
				byte[] buffer=new byte[1024];
				int len=0;
				while((len=fis.read(buffer))>0){
					fos.write(buffer,0,len);
				}
				fos.close();
				fis.close();
			}
			
			
			return NONE;
		}
		
		public String getA_phone() {
			return a_phone;
		}
		public void setA_phone(String a_phone) {
			this.a_phone = a_phone;
		}
		public String getA_title() {
			return a_title;
		}
		public void setA_title(String a_title) {
			this.a_title = a_title;
		}
		public String getA_content() {
			return a_content;
		}
		public void setA_content(String a_content) {
			this.a_content = a_content;
		}
		public String getA_shareUrl() {
			return a_shareUrl;
		}
		public void setA_shareUrl(String a_shareUrl) {
			this.a_shareUrl = a_shareUrl;
		}
		public File getA_img() {
			return a_img;
		}
		public void setA_img(File a_img) {
			this.a_img = a_img;
		}
		public String getA_imgFileName() {
			return a_imgFileName;
		}
		public void setA_imgFileName(String a_imgFileName) {
			this.a_imgFileName = a_imgFileName;
		}
		public String getA_imgContentType() {
			return a_imgContentType;
		}
		public void setA_imgContentType(String a_imgContentType) {
			this.a_imgContentType = a_imgContentType;
		}
		
		
		
}
