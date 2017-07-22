package life.playTogether.service;

import java.util.List;

import javax.annotation.Resource;

import life.playTogether.dao.ActivityDao;
import life.playTogether.entity.Activity;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component(value="activityService")
public class AddActivityService {

	@Resource(name="activityDao")
	private ActivityDao<Activity> activityDao;
	
	public List<Activity> findAll(){
		return activityDao.findAll(Activity.class);
	}
	
	public void save(Activity activity){
		activityDao.save(activity);
	}
	
	public List<Activity> findByPage(final int pageNo, final int pageSize ){
		return activityDao.findByPage("select en from Activity en", pageNo, pageSize);
	}

}
