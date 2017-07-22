package life.playTogether.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import life.playTogether.entity.Activity;
import life.playTogether.service.AddActivityService;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.zzu.entity.Results;


@Component(value="activityAction")
@Scope(value="prototype")
public class ActivityAction extends ActionSupport{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource(name="activityService")
	private AddActivityService activityService;

	@Resource(name="results")
	private Results results;
	
	public String execute() throws Exception {
		HttpServletRequest request =ServletActionContext.getRequest();
		request.setCharacterEncoding("UTF-8");
		String page=request.getParameter("page");
		int intpage= Integer.parseInt(page);
		
		List<Activity> al =(ArrayList<Activity>)activityService.findByPage(intpage, 6);
		results.setResults(al);
		results.setNumber(intpage);
		
		Gson gson =new Gson();
		String jsonObject= gson.toJson(results);
		HttpServletResponse response= ServletActionContext.getResponse();	
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
	         PrintWriter out=response.getWriter();
	    	 out.println(jsonObject);
	    	 System.out.println(jsonObject);
	    	 out.flush();
	    	 out.close();
		return NONE;
	}
}
