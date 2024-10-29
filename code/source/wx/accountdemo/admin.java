package wx.accountdemo;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import com.wm.app.b2b.server.globalvariables.GlobalVariablesManager;
import com.wm.util.GlobalVariables;
import com.wm.util.GlobalVariables.GlobalVariableValue;
import com.wm.util.PackageVariables;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;
import com.wm.app.b2b.server.ServerAPI;
import com.wm.app.b2b.server.ServerStartupNotifier;
import com.wm.app.b2b.server.InvokeState;
// --- <<IS-END-IMPORTS>> ---

public final class admin

{
	// ---( internal utility methods )---

	final static admin _instance = new admin();

	static admin _newInstance() { return new admin(); }

	static admin _cast(Object o) { return (admin)o; }

	// ---( server methods )---




	public static final void getGlobalPackageVariable (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getGlobalPackageVariable)>> ---
		// @sigtype java 3.5
		// [i] field:0:required key
		// [i] field:0:optional defaultValue
		// [i] field:0:optional ignoreErrors
		// [o] field:0:required value
		// [o] field:0:required isSecure
		// [o] field:0:optional isDefined
		// pipeline	
		IDataCursor cursor = pipeline.getCursor();
		String key = IDataUtil.getString(cursor, "key");
		String value = IDataUtil.getString(cursor, "defaultValue");
		String ignoreError = IDataUtil.getString(cursor, "ignoreErrors");
		cursor.delete();
		cursor.destroy();
		// process
		String isSecure = "false";
		String isDefined = "false";
		
		try {
			// InvokeState invs = InvokeState.getCurrentState();
			// String svcName = InvokeState.getCurrentService().getNSName().getFullName();
			String svcPackage = InvokeState.getCurrentService().getPackage().getName();
			
			PackageVariables pv = PackageVariables.getInstance();
			Map<String, GlobalVariableValue> gvList = pv.getPkgGlobalVariables().get(svcPackage);
			
			if(gvList.keySet().contains(key)) {
				GlobalVariables.GlobalVariableValue gvValue = pv.get(svcPackage, key);
				isDefined = "true";
				value = gvValue.getValue();
				isSecure = "" + gvValue.isSecure();			
			}
		}
		catch (Exception e) {
			if (value == null && (ignoreError == null || ignoreError == "false"))
				throw new ServiceException(e);
		}
		IDataUtil.put(cursor, "value", value);
		IDataUtil.put(cursor, "isSecure", isSecure);
		IDataUtil.put(cursor, "isDefined", isDefined);
		cursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void getGlobalVariable (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getGlobalVariable)>> ---
		// @sigtype java 3.5
		// [i] field:0:required key
		// [i] field:0:optional defaultValue
		// [i] field:0:optional ignoreErrors
		// [o] field:0:required value
		// [o] field:0:required isSecure
		// pipeline	
		IDataCursor cursor = pipeline.getCursor();
		String key = IDataUtil.getString(cursor, "key");
		String value = IDataUtil.getString(cursor, "defaultValue");
		String ignoreError = IDataUtil.getString(cursor, "ignoreErrors");
		
		// process
		
		String isSecure = "false";
		
		try {
		    GlobalVariablesManager manager = GlobalVariablesManager.getInstance();
		    
		    GlobalVariables.GlobalVariableValue gvValue = manager.getGlobalVariableValue(key);
		    
		    value = gvValue.getValue();
		    isSecure = "" + gvValue.isSecure();
		}
		catch (Exception e) {
			if (value == null && (ignoreError == null || ignoreError == "false"))
				throw new ServiceException(e);
		}
		
		// pipeline out
		
		IDataUtil.put(cursor, "value", value);
		IDataUtil.put(cursor, "isSecure", isSecure);
			
		cursor.destroy();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	public static void log(String msg) {
		IData input = IDataFactory.create();
		IDataCursor inputCursor = input.getCursor();
		IDataUtil.put( inputCursor, "message", msg );
		inputCursor.destroy();
		try{
			Service.doInvoke( "pub.flow", "debugLog", input );
		}catch( Exception e){}
	}
	static {
	}
	// --- <<IS-END-SHARED>> ---
}

