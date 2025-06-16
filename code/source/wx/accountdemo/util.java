package wx.accountdemo;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.wm.app.b2b.server.Server;
import com.wm.app.b2b.server.ServerAPI;
// --- <<IS-END-IMPORTS>> ---

public final class util

{
	// ---( internal utility methods )---

	final static util _instance = new util();

	static util _newInstance() { return new util(); }

	static util _cast(Object o) { return (util)o; }

	// ---( server methods )---




	public static final void readCloudConfiguration (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(readCloudConfiguration)>> ---
		// @sigtype java 3.5
		// [o] field:0:required cloudAppConfiguration
		File packageConfigDir = ServerAPI.getPackageConfigDir(PACKAGE_NAME);
		
		if (packageConfigDir == null) {
			simpleLog("package config dir is: " + packageConfigDir.toPath().toString(), null, null);
			throw new ServiceException("Unable to read " + PACKAGE_NAME + " configuration folder");
		}
		Path configFilePath = Paths.get(packageConfigDir + File.separator + CONFIG_FILE_NAME);
		if (!Files.exists(configFilePath)) {
			simpleLog("config file is: " + configFilePath.toString(), null, null);
			throw new ServiceException("Unable to find the needed " + CONFIG_FILE_NAME + " in the configuration folder");
		}
		try {
			String configAsString = new String(Files.readAllBytes(configFilePath));
			
			if (configAsString == null || configAsString.isEmpty()) {
				throw new ServiceException("Empty configuration file for " + PACKAGE_NAME + ". Unable to continue initial configuration.");
			}
		
			// pipeline
			IDataCursor pipelineCursor = pipeline.getCursor();
			IDataUtil.put( pipelineCursor, "cloudAppConfiguration", configAsString );
			pipelineCursor.destroy();
		} catch (IOException e) {
			throw new ServiceException(e);
		}
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	private static final String PACKAGE_NAME = "WxAccountDemo";
	private static final String CONFIG_FILE_NAME = "cloud_config.json";
	private static final String CONFIGURATION_DOCTYPE = "wx.accountdemo.admin:packageConfiguration";
	private static final void simpleLog(String msg, String prefix, String level) {
		try {
			IData input = IDataFactory.create();
			IDataCursor inputCursor = input.getCursor();
			IDataUtil.put(inputCursor, "message", msg);
			
	//			IDataUtil.put(inputCursor, "function", (prefix != null && !"".equals(prefix)) ? prefix + " " + function : function);
			IDataUtil.put(inputCursor, "level", level);
			inputCursor.destroy();
			
			
			Service.doInvoke( "pub.flow", "debugLog", input );
		} catch (Exception e){
		// just log the error into the server's error log ... 
			e.printStackTrace();
			Server.logError(e);
		}
	}
	// --- <<IS-END-SHARED>> ---
}

