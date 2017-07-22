package persionalCenter.confessionWall.action;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import persionalCenter.confessionWall.entity.BareHeart;
import persionalCenter.confessionWall.entity.BareHeartPraise;
import persionalCenter.confessionWall.service.BareHeartPraiseService;
import persionalCenter.confessionWall.service.BareHeartService;

@SuppressWarnings("serial")
@Controller(value="addBareHeartPraiseAction")
@Scope(value="prototype")
public class AddBareHeartPraiseAction extends ActionSupport{

	@Resource(name="bareHeartPraise")
	private BareHeartPraise bareHeartPraise;
	
	@Resource(name="bareHeartPraiseServiceImpl")
	private BareHeartPraiseService bareHeartPraiseService;
	
	@Resource(name="bareHeart")
	private BareHeart bareHeart;
	
	@Resource(name="bareHeartServiceImpl")
	private BareHeartService bareHeartService;
	
	private Integer phone;
	private Integer bareHeartid;
	private boolean isSuccessful=false;
	
	@Override
	public String execute() throws Exception {
		try{
		String sql="select t from BareHeartPraise t where t.phone=? and t.bareHeartid=?";
		List l=bareHeartPraiseService.query(sql,new Integer[]{phone,bareHeartid});
	  
		if(l.isEmpty()==true){
			bareHeartPraise.setPhone(phone);
			bareHeartPraise.setBareHeartid(bareHeartid);
			bareHeartPraiseService.save(bareHeartPraise);
			BareHeart b = bareHeartService.get(BareHeart.class, bareHeartPraise.getBareHeartid());
			
			int i =b.getPraise()+1;
			b.setPraise(i);
			bareHeartService.update(b);
		}
		isSuccessful = true ;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HttpServletResponse response= ServletActionContext.getResponse();	
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-type", "text/html;charset=UTF-8");
		         PrintWriter out=response.getWriter();
		    	 out.println(isSuccessful);
		    	
		    	 out.flush();
		    	 out.close();
		}
		
		return NONE;
	 }
	public Integer getPhone() {
		return phone;
	}
	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	public Integer getBareHeartid() {
		return bareHeartid;
	}
	public void setBareHeartid(Integer bareHeartid) {
		this.bareHeartid = bareHeartid;
	}
	
	
}
