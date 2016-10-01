package sapience.core.tasks;

import java.io.InputStream;


public abstract class UpdateTask extends CoreTask {
	public static String PARAM_URL = "commit_param_url";
	public static String PARAM_BODY = "commit_param_body";
	public static String PARAM_FORMAT = "commit_param_format";

	
	public abstract InputStream getStream() throws Exception;

	



}
