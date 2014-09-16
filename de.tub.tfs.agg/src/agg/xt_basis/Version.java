package agg.xt_basis;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class provides the current version number
 * 
 * @author $Author: olga $
 * @version $Id: Version.java,v 1.5 2010/03/08 15:51:41 olga Exp $
 */
public final class Version {

	private static String ID = ">>Version file is corrupt<<";

	private static String FILENAME = "Version.id";

	private static boolean READ = false;

	/**
	 * get the number of the current version as a string
	 */
	public static String getID() {
		if (!READ) {
			Version own = new Version();
			Class<?> clazz = own.getClass();
			InputStream resource = clazz.getResourceAsStream(FILENAME);
			if (resource == null) {
				System.out.println("File " + FILENAME + " not found...");
				return ID;
			} 
			// System.out.println(resource);
			try {
				BufferedReader file = new BufferedReader(
						new InputStreamReader(resource));
				ID = file.readLine();
				READ = true;
			} catch (Exception ioe) {
				System.out
							.println("exception while reading version number");
			}
		}
		return ID;
		// return ID.substring(0).replace('_', '.');
	}
}
// $Log: Version.java,v $
// Revision 1.5  2010/03/08 15:51:41  olga
// code optimizing
//
// Revision 1.4  2007/11/01 09:58:16  olga
// Code refactoring: generic types- done
//
// Revision 1.3  2007/09/10 13:05:37  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.2 2005/11/16 15:20:22 olga
// Version output
//
// Revision 1.1 2005/08/25 11:56:54 enrico
// *** empty log message ***
//
// Revision 1.1 2005/07/11 09:30:20 olga
// This is test version AGG V1.2.8alfa .
// What is new:
// - saving rule option <disabled>
// - setting trigger rule for layer
// - display attr. conditions in gragra tree view
// - CPA algorithm <dependencies>
// - creating and display CPA graph with conflicts and/or dependencies
// based on (.cpx) file
//
// Revision 1.1 2005/05/30 12:58:02 olga
// Version with Eclipse
//
// Revision 1.4 2003/06/25 13:12:23 olga
// Version 1.2.0
//
// Revision 1.3 2003/03/05 18:24:17 komm
// sorted/optimized import statements
//
// Revision 1.2 2003/02/10 14:31:19 komm
// read only the first line of Version.id
//
// Revision 1.1.1.1 2002/07/11 12:17:11 olga
// Imported sources
//
// Revision 1.14 1999/07/26 10:21:56 shultzke
// Versionsnummer wird nett aus Version.id
// ausgelesen
//
