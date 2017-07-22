package life.playTogether.dao;

import java.util.List;
import life.playTogether.entity.Activity;

@SuppressWarnings("hiding")
public interface ActivityDao <Activity>{

	public List<Activity> findAll(Class<Activity> entityClazz);
	
	public void save(Activity activity);
	
	public Activity query(int id);
	
	public void update(Activity activity);
	
	public List<Activity> findByPage(final String hql, final int pageNo, final int pageSize);
	
	public  List<Activity> findByPage(final String hql , final int pageNo, final int pageSize , final  Object... params);

   
}