/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.util;

public class ExceptionUtil {

	public static void error(String errorSstring) {
		System.out.println("TGG editor error: " + errorSstring);
		Throwable t = new Throwable();
		t.printStackTrace();
	}
	
	

}
