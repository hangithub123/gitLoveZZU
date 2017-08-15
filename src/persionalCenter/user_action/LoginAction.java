package persionalCenter.user_action;

import javax.annotation.Resource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


import net.sf.json.JSONObject;
import persionalCenter.entity.User;
import persionalCenter.service.UserService;
import zzu.util.Judge_character;
import zzu.util.Returndata;


@Transactional
@Component(value="loginAction")
@Scope(value="prototype")
public class LoginAction extends ActionSupport implements ModelDriven<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//ע������
	@Resource(name="User_Service")
	private UserService userService;
	
	
	private User user=new User();
	
	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return user;
	}
	
	
	
	@Override
	public String execute() throws Exception {
		
           
		Judge_character j=new Judge_character();
		String SessionID=null;
		String info=null;
		if( j.character(user.getPhone())){
		System.out.println("LoginAction��User�״ε�½"+user.toString());
		SessionID=userService.query(user);
		}else {
			if(user.getSessionID() !=null && !user.getSessionID().isEmpty()){
				System.out.println("�û��ٴε�½����,SessionID:"+user.getSessionID().toString());
				SessionID=userService.againquery(user);
				if(SessionID == null || SessionID.equals("")){
					info="��¼ʧ�ܣ��û�������";				
				}else{System.out.println("SeesionID��½");info="SessionID��½";}
				
			}else{
			info="�ͻ��˴������ֻ��ź�SessionID���ݶ�Ϊ�ջ򲻺Ϸ�";
			System.out.println("�ͻ��˴������ֻ��ź�SessionID���ݶ�Ϊ��");}
			}
	
		
		
	

		//��������
		
		
		JSONObject json = new JSONObject();   
      	 json.put("SessionID", SessionID);    	
   	     json.put("info", info);    	
         System.out.println("LoginAction��json"+json);
		Returndata.returndata(json);

	
		
		return null;
	}
	
	
	 

 


}
