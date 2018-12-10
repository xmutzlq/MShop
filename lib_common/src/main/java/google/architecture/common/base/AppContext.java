package google.architecture.common.base;

import com.king.android.res.util.FileUtil;

import java.io.File;


/**
 * 配置项
 */
public class AppContext {

	/** 下载保存的路径**/
	public static final String DOWN_LOAD_PATH = "farmer";
	/** 文件存储根目录 */
	public static final String fileDiretory = FileUtil.getExternalStorageDirectory()
			+ File.separator + DOWN_LOAD_PATH + File.separator;
	/** 存放LOG */
	public static final String FILE_LOG = fileDiretory + "log.txt";

	public static final String VERSION = "version";

	public static final String CACHE_DIR_API = "Api";//网络接口缓存目录
	public static final String CACHE_DIR_WEB = "Web";//webview缓存目录
	public static final String CACHE_DIR_WEB_RES = "WebRes";//webview资源缓存目录
	public static final String CACHE_DIR_IMG = "Image";//图片下载目录
}
