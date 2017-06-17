package persionalCenter.user_action;

import java.io.PrintWriter;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import persionalCenter.entity.User;
import persionalCenter.service.UserService;
import zzu.util.Judge_character;


@Transactional
@Component(value="loginAction")
@Scope(value="prototype")
public class LoginAction extends ActionSupport implements ModelDriven<User>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//注入属性
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="user")
	private User user;
	
	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}
	
	@Override
	public String execute() throws Exception {
		Judge_character j=new Judge_character();
		String SessionID=null;
		String error=null;
		if( j.character(user.getPhone())){
		System.out.println("LoginAction层User首次登陆"+user.toString());
		SessionID=userService.query(user);
		}else{
			if(user.getSessionID() !=null){
				System.out.println("用户再次登陆操作,SessionID:"+user.getSessionID().toString());
				SessionID=userService.againquery(user);
				
			}else{
			
			System.out.println("客户端传来的手机号和标识符数据都为空");}}
		if(SessionID == null){
			error="登录失败,返回SessionID为空，用户不存在";
			
		}else{System.out.println("SeesionID登陆");}
	//返回数据
		HttpServletResponse response= ServletActionContext.getResponse();
		//如果中文返回出现？？字符，这表明没有加response.setCharacterEncoding("UTF-8");这句话。
      //如果返回的中文是“烇湫”这种乱码，说明浏览器的解析问题，应该检查下是否忘加response.setHeader("Content-type", "text/html;charset=UTF-8");这句话
		response.setHeader("Content-type", "text/html;charset=UTF-8");   
		response.setCharacterEncoding("UTF-8"); 
		
		JSONObject json = new JSONObject();   
    	 json.put("SessionID", SessionID);    	
    	 json.put("error", error);    	
          System.out.println("LoginAction层json"+json);
        
         PrintWriter out=response.getWriter();    	
    	   out.println(json);
    	   out.flush();
    	   out.close();
      
		return NONE;
	}
	
	


}
