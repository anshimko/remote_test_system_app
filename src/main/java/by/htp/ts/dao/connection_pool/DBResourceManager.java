package by.htp.ts.dao.connection_pool;

import java.util.Locale;
import java.util.ResourceBundle;

public class DBResourceManager {
	
	private final static DBResourceManager instance = new DBResourceManager();
	private ResourceBundle boundle = ResourceBundle.getBundle("main/resources.dp", Locale.ENGLISH);
	
	public static DBResourceManager getInstance () {
		return instance;
	}
	
	public String getValue (String key) {
		return boundle.getString(key);
	}

}
