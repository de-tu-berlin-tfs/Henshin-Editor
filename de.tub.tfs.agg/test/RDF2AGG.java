/**
 * 
 */



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import agg.util.XMLHelper;
import agg.xt_basis.GraGra;


/**
 * @author olga
 *
 */
public class RDF2AGG {

	private static String fileName;
		
//	private File file;
//	private static String outputFileName;
	
	
	public RDF2AGG(final String arg) {
		if (arg != null && arg.endsWith(".owl")) {
			fileName = arg;
		}
		
		if (fileName != null) {
			System.out.println("File:  " + fileName);
			/* read .owl file */			
			GraGra gra = readInputFile(fileName);
			if (gra != null) {	
				gra.save(fileName+".ggx");
				System.out.println("File saved to:  " + fileName+".ggx");
			}
		}
	}
	
	public static void main(String[] args) {
		String vers = System.getProperty("java.version");
		if (vers.compareTo("1.5") < 0) {
			System.out.println("WARNING : Swing must be run with the "
					+ "1.5 version of the JVM.");
		}

		if (args.length == 1) {
			new RDF2AGG(args[0]);
		}
	}
	
		
	GraGra readInputFile(final String name) {
		String tmpname = name.concat(".ggx");
		final File tmp = new File(tmpname);
		try {
			BufferedWriter wr = new BufferedWriter(new FileWriter(tmp));
			
			final File f = new File(name);
			if (f.exists()) {
				// read file
				try {
					BufferedReader in = new BufferedReader(new FileReader(name));
					
					String line = in.readLine();
					if (line != null && line.startsWith("<?xml")) {
						wr.write(line);
						wr.flush();
						wr.write("<Document version=\"1.0\">");
						wr.flush();
						wr.write("<GraphTransformationSystem ID=\"I1\" name=\"RDFGrammar\">");
						wr.flush();
						
						line = in.readLine();
						while (line != null) {
							wr.write(line);
							wr.flush();
							line = in.readLine();
						}
						
						wr.write("</GraphTransformationSystem>");
						wr.flush();
						wr.write("</Document>");
						wr.flush();
					}			
				} catch (IOException iox) {}			
			}
		} catch (Exception ex) {}
		
		XMLHelper h = new XMLHelper();
		if (h.read_from_xml(tmpname)) {
			// create a gragra
			RDFGraGra gra =  new RDFGraGra();
			h.getTopObject(gra);
			gra.setFileName(name);
			return gra;
		} 
		return null;
	}
	
}
