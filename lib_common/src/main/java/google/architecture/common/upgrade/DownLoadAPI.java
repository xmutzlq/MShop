package google.architecture.common.upgrade;

public interface DownLoadAPI {
	/** 初始化一些数据 **/
	public void init();
	/** 注册下载进度监听 **/
	public void registDownLoadContentObserver();
	/** 注销下载进度监听 **/
	public void unregistDownLoadContentObserver();
	/** 开始下载 **/
	public void startDownLoad();
	/** 下载结束 **/
	public void overDownLoad();
}
