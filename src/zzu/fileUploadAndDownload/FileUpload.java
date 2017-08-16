package zzu.fileUploadAndDownload;

//�ϴ�����ļ�
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionSupport;

import life.mytopiccircle.entity.Theme;
import life.mytopiccircle.entity.Topic;
import life.mytopiccircle.service.TopicCircleService;
import life.taoyu.entity.Goods;
import life.taoyu.service.TaoyuService;
import net.sf.json.JSONObject;
import persionalCenter.dao.Dao;
import zzu.util.GetDate;
import zzu.util.Panduanstr;
import zzu.util.Returndata;
@Transactional
@Component(value = "imagesupload")
@Scope(value = "prototype")
public class FileUpload extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	
	// �ϴ��ļ���

	//private File[] images = new File[10];
	 List<File> images=new ArrayList<File>();
	// �ϴ��ļ�����
	private String[] imagesContentType = new String[10];
	// ��װ�ϴ��ļ���
	private String[] imagesFileName = new String[10];

	private String goods_id;
	private String SessionID;
	private String action = null;

	private String[] DNames = null;// ָ��Ҫɾ����ͼƬ����
    private String ThemeId ,TopicId ;//���⣬����id
	@Resource(name = "getdata")
	private GetDate data;
	@Resource(name = "taoyuService")
	private TaoyuService taoyuService;
	@Autowired
	TopicCircleService  TCS;
	
	HttpServletRequest request = ServletActionContext.getRequest();
	boolean isSuccessful = false;
	String[] str = null;// str[0] �Ƿ�Account��str[1]�ǷŻ�ȡ���ݿ��еľɵ�ͼƬ��
	String[] imagenames = null;// ��Ż�ȡ�ľɵ�ͼƬ��
	Panduanstr p = new Panduanstr();
	Goods goods = new Goods();

	
	List<String> strlist=new ArrayList<String>();

	@Override
	public String execute() throws FileNotFoundException, IOException, InterruptedException {
		System.out.println(ThemeId+","+action+":"+images);
		String realPath = request.getRealPath("/").substring(0,
				request.getRealPath("/").lastIndexOf(request.getContextPath().replace("/", ""))) ;
		BlockingQueue BQmadeimg=new BlockingQueue();//ʵ������������
		ExecutorService exec=Executors.newCachedThreadPool();
		
		
		if (action == null&&action.equals("")) {System.out.println("�ϴ�ͼƬaction���ڻ�մ�");}
		switch (action) {
			case "�ϴ���ƷͼƬ":realPath+= "goodsuploadImage"+ File.separator;
				
				exec.execute(new UploadThread(goods_id,BQmadeimg,"goods",images,imagesContentType,imagesFileName,realPath));
				exec.execute(new UpdateImgToDB(BQmadeimg,taoyuService));//���ݶ��к�service����

				break;
				
			case "�ϴ�����ͼƬ":realPath+= "topicCircle"+ File.separator;
				exec.execute(new UploadThread(ThemeId,BQmadeimg, "theme",images,imagesContentType,imagesFileName,realPath));
				
				exec.execute(new UpdateImgToDB(BQmadeimg,TCS));
				break;
			case "�ϴ�����ͼƬ":
				realPath+= "topicCircle"+ File.separator;
				for(int i=0;i<20;i++)
					exec.execute(new UploadThread(TopicId, BQmadeimg,"topic",images,imagesContentType,imagesFileName,realPath));
					exec.execute(new UpdateImgToDB(BQmadeimg,TCS));
				
				break;
				
			default:System.err.println("ͼƬ�ϴ�actionδƥ��");
				break;
			}
		
		 
			

			
					

		return null;
	}
	
public boolean getDBinfo(){
	// ��ȡ���ݿ��м�¼��Ϣ
	boolean b=false;
	if(images==null){System.out.println("ͼƬ�ļ�Ϊ��");return b;}
				if (goods_id != null && !goods_id.equals("") && SessionID != null && !SessionID.equals("")) {
					str = taoyuService.getImageName(SessionID, goods_id);
					if (str == null) {
						System.err.println("δ���������ݿ�����Ʒ��Ϣ");
						return b;
					}
					goods.setGoods_id(Integer.parseInt(goods_id));
					if (str[1] != null)
						imagenames = p.fenli(str[1]);
                  b=true;
				} else {
					System.err.println("�ϴ���ƷͼƬʱSessionID����ƷidΪ��");
					}	
				return b;
   }


	
	


	// ɾ��ͼƬ����
	public void deletePicture() throws Exception {
		String realPath = request.getRealPath("/").substring(0,
				request.getRealPath("/").lastIndexOf(request.getContextPath().replace("/", ""))) + "goodsuploadImage"
				+ File.separator;

		if (action.equals("ɾ��ͼƬ") && imagenames != null) {
			FileDelete d = new FileDelete();
			String newImgnames = d.delete(realPath, str[1], DNames);// �����µ�imageurl
			if (newImgnames != null) {
				goods.setGimage(newImgnames);
				taoyuService.updateGoods(goods);
				
			}
			isSuccessful = true;

		}
	}

	/**
	 * ����ע��
	 * 
	 * @return
	 */
	public String[] getDNames() {// ������Ҫɾ����ͼƬ��
		return DNames;
	}

	public void setDNames(String[] dNames) {
		DNames = dNames;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSessionID() {
		return SessionID;
	}

	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}

	public String getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public List<File> getImages() {
		return images;
	}
	public void setImages(List<File> images) {
		this.images = images;
	}
//	public File[] getImages() {
//		return images;
//	}
//
//	public void setImages(File[] images) {
//		this.images = images;
//	}

	public String[] getImagesContentType() {
		return imagesContentType;
	}

	public void setImagesContentType(String[] imagesContentType) {
		this.imagesContentType = imagesContentType;
	}

	public String[] getImagesFileName() {
		return imagesFileName;
	}

	public void setImagesFileName(String[] imagesFileName) {
		this.imagesFileName = imagesFileName;
	}
	public String getThemeId() {
		return ThemeId;
	}
	public void setThemeId(String themeId) {
		ThemeId = themeId;
	}
	public String getTopicId() {
		return TopicId;
	}
	public void setTopicId(String topicId) {
		TopicId = topicId;
	}

//����ͼƬ�������ݿ���ڲ���
	
}

