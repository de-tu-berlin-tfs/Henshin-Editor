package agg.convert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;

public class ConverterXML {

	String sourceName;
	String ext;
	
	public ConverterXML() {
	}

	public boolean ggx2gxl(String filenameIn, String filenameOut,
			String srcName) {
		this.sourceName = srcName;
		return ggx2gxl(filenameIn, filenameOut);
	}

	public boolean ggx2gxl(String filenameIn, String filenameOut) {
//		System.out.println(filenameIn+"  ->  "+filenameOut);
		TransformerFactory tf = TransformerFactory.newInstance();
		// System.out.println("factory: "+tf);
		Source templatesSrc = null;
		if ((this.sourceName != null) && !this.sourceName.equals("")) {
			templatesSrc = getTemplatesSource(this.sourceName);
		} else {
			java.net.URL url = ClassLoader.getSystemClassLoader()
					.getResource("agg/convert/ggx2gxl.xsl");
			if (url != null) {
				templatesSrc = getTemplatesSource(url.getFile());
			}
		}
//		 System.out.println("templates source: "+templatesSrc);
		if (templatesSrc == null) {
			System.out
					.println("agg.convert.ConverterXML.ggx2gxl:: source  'ggx2gxl.xsl'  NOT FOUND");
			return false;
		}

		Source src = getSource(".ggx", filenameIn);
//		 System.out.println("source: "+src);

		Result result = getResult(".gxl", filenameOut);
//		 System.out.println("result: "+result);

		if (src == null || result == null)
			return false;

		Templates templates = null;
		try {
			templates = tf.newTemplates(templatesSrc);
//			 System.out.println("templates: "+templates);
		} catch (TransformerConfigurationException ex1) {
			System.out.println(ex1.getMessage());
			return false;
		}

		Transformer transformer = null;
		try {
			transformer = templates.newTransformer();
//			 System.out.println("transformer: "+transformer);
			try {
				transformer.transform(src, result);
			} catch (TransformerException ex3) {
				System.out.println(ex3.getMessage());
				return false;
			}
		} catch (TransformerConfigurationException ex2) {
			System.out.println(ex2.getMessage());
			return false;
		}
		return true;
	}

	public boolean gxl2ggx(String filenameIn, String filenameOut, String srcName) {
		this.sourceName = srcName;
		return gxl2ggx(filenameIn, filenameOut);
	}

	public boolean gxl2ggx(String filenameIn, String filenameOut) {
//		System.out.println("gxl2ggx: "+ filenameIn+" -> "+ filenameOut);
		TransformerFactory tf = TransformerFactory.newInstance();
		Source templatesSrc = null;
//		 System.out.println("sourceName: "+ sourceName);
		if ((this.sourceName != null) && !this.sourceName.equals("")) {
			templatesSrc = getTemplatesSource(this.sourceName);
//			System.out.println("templates source : "+templatesSrc);
		} else {
			java.net.URL url = ClassLoader.getSystemClassLoader()
					.getResource("agg/convert/gxl2ggx.xsl");
			if (url != null) {
//				System.out.println("templates source file by ClassLoader : "+url.getFile());				
				templatesSrc = getTemplatesSource(url.getFile());
			}
		}
//		System.out.println("templates source : "+templatesSrc);
		if (templatesSrc == null) {
			System.out
					.println("agg.convert.ConverterXML.gxl2ggx:: source  'gxl2ggx.xsl'  NOT FOUND");
			return false;
		}

		Source src = getSource(".gxl", filenameIn);
//		System.out.println("source: "+src);

		Result result = getResult(".ggx", filenameOut);
		System.out.println("result: "+result);

		if (src == null || result == null)
			return false;

		Templates templates = null;
		try {
			templates = tf.newTemplates(templatesSrc);
//			System.out.println("templates: "+templates);
		} catch (TransformerConfigurationException ex1) {
			System.out.println("TransformerConfigurationException. "+ex1.getMessage());
			return false;
		}

		Transformer transformer = null;
		try {
			transformer = templates.newTransformer();
//			System.out.println("transformer: "+transformer);
			try {
				transformer.transform(src, result);
			} catch (TransformerException ex3) {
				System.out.println(ex3.getMessage());
				return false;
			}
		} catch (TransformerConfigurationException ex2) {
			System.out.println(ex2.getMessage());
			return false;
		}
		return true;
	}

	public boolean gts2gtxl(String filenameIn, String filenameOut,
			String srcName) {
		this.sourceName = srcName;
		return gts2gtxl(filenameIn, filenameOut);
	}

	public boolean gts2gtxl(String filenameIn, String filenameOut) {
		// System.out.println("ConverterXML.gts2gtxl ");
		// System.out.println("source: "+sourceName);
		TransformerFactory tf = TransformerFactory.newInstance();
		// System.out.println("factory: "+tf);
		Source templatesSrc = null;
		if ((this.sourceName != null) && !this.sourceName.equals("")) {
			templatesSrc = getTemplatesSource(this.sourceName);
		} else {
			java.net.URL url = ClassLoader.getSystemClassLoader()
					.getResource("agg/convert/gts2gtxl.xsl");
			if (url != null) {
				templatesSrc = getTemplatesSource(url.getFile());
			}
		}
		// System.out.println("templates source: "+templatesSrc);
		if (templatesSrc == null) {
			System.out
					.println("agg.convert.ConverterXML.gts2gtxl:: source  'gts2gtxl.xsl'  NOT FOUND");
			return false;
		}

		Source src = getSource(".ggx", filenameIn);
		// System.out.println("source: "+src);
		// System.out.println("filenameIn: "+filenameIn);
		Result result = getResult(".gtxl", filenameOut);
		// System.out.println("result: "+result);
		// System.out.println("filenameOut: "+filenameOut);
		if (src == null || result == null)
			return false;

		Templates templates = null;
		try {
			templates = tf.newTemplates(templatesSrc);
			// System.out.println("templates: "+templates);
		} catch (TransformerConfigurationException ex1) {
			System.out.println(ex1.getMessage());
			return false;
		}

		Transformer transformer = null;
		try {
			transformer = templates.newTransformer();
			// System.out.println("transformer: "+transformer);
			try {
				transformer.transform(src, result);
			} catch (TransformerException ex3) {
				System.out.println(ex3.getMessage());
				return false;
			}
		} catch (TransformerConfigurationException ex2) {
			System.out.println(ex2.getMessage());
			return false;
		}
		return true;
	}

	public boolean gtxl2gts(String filenameIn, String filenameOut,
			String srcName) {
		return false;
	}

	public boolean gtxl2gts(String filenameIn, String filenameOut) {
		return false;
	}

	public boolean omondoxmi2ggx(String filenameIn, String filenameOut,
			String sourceNameGXL, String sourceNameGGX) {
		// System.out.println("ConverterXML.omondoxmi2ggx ... "+filenameIn+"
		// "+filenameOut+" "+sourceNameGXL+" "+sourceNameGGX);
//		String suffix = ".gxl";
		String name = filenameIn.substring(0, filenameIn.lastIndexOf('.'));
		System.out.println(name);
		String filenameOutGXL = name //filenameIn.substring(0, filenameIn.length() - 6)
				+ "_ecore.gxl";

		TransformerFactory tf = TransformerFactory.newInstance();
		// System.out.println("factory: "+tf);
		Source templatesSrcGXL = null;
		if ((sourceNameGXL != null) && !sourceNameGXL.equals("")) {
			templatesSrcGXL = getTemplatesSource(sourceNameGXL);
		} else {
			java.net.URL url = ClassLoader.getSystemClassLoader()
					.getResource("agg/convert/omondoxmi2gxl.xsl");
			if (url != null) {
				templatesSrcGXL = getTemplatesSource(url.getFile());
			}
		}
		// System.out.println("templates GXL source: "+templatesSrcGXL);
		if (templatesSrcGXL == null) {
			System.out
					.println("agg.convert.ConverterXML.omondoxmi2gxl:: template source  'omondoxmi2gxl.xsl'  NOT FOUND");
			return false;
		}
		Source templatesSrcGGX = null;
		if ((sourceNameGGX != null) && !sourceNameGGX.equals("")) {
			templatesSrcGGX = getTemplatesSource(sourceNameGGX);
		} else {
			java.net.URL url = ClassLoader
					.getSystemResource("agg/convert/gxl2ggx.xsl");
			templatesSrcGGX = getTemplatesSource(url.getFile());
		}
		// System.out.println("templates GGX source: "+templatesSrcGGX);
		if (templatesSrcGGX == null) {
			System.out
					.println("agg.convert.ConverterXML.omondoxmi2gxl:: template source  'gxl2ggx.xsl'  NOT FOUND");
			return false;
		}

		Source src = getSource(".ecore", filenameIn);
		if (src != null) 
			ext = ".ecore";
		else {
			ext = "";
//			src = getSource(".ron", filenameIn);
//			if (src != null)
//				ext = ".ron";
//			else {
//				src = getSource(".actigra", filenameIn);
//				if (src != null)
//					ext = ".actigra";
//				else {
//					src = getSource(".henshin", filenameIn);
//					if (src != null)
//						ext = ".henshin";
//					else
//						ext = "";
//				}
//			}
		}
//		System.out.println(this.getClass().getName()+"::  "+ ext+"     source: "+src);

		Result result = getResult(".gxl", filenameOutGXL);
		// System.out.println("result: "+result);

		if (src == null || result == null)
			return false;

		Templates templates = null;
		try {
			// System.out.println("try to create templates ...");
			templates = tf.newTemplates(templatesSrcGXL);
			// System.out.println("templates: "+templates);
		} catch (TransformerConfigurationException ex1) {
			System.out
					.println("agg.convert.ConverterXML.omondoxmi2gxl:: TransformerConfigurationException\n"
							+ ex1.getLocalizedMessage());
			return false;
		}

		Transformer transformer = null;
		try {
			// System.out.println("try to create transformer ...");
			transformer = templates.newTransformer();
			// transformer = tf.newTransformer(templatesSrcGXL);
			// System.out.println("transformer: "+transformer);
			transformer.transform(src, result);
			// System.out.println("transformation from OMONDO to GXL done.");
		} catch (TransformerException ex3) {
			System.out
					.println("agg.convert.ConverterXML.omondoxmi2gxl:: TransformerException:\n"
							+ ex3.getLocalizedMessage());
			return false;
		}

		return gxl2ggx(filenameOutGXL, filenameOut, sourceNameGGX);
	}

	public String getFileExtOfImport() {
		return this.ext;
	}
	
	public File copyFile(String dirName, String fileName) {
		try {
			InputStream fis = this.getClass().getResourceAsStream(
					"/agg/convert/" + fileName);
			File outFile = new File(dirName + fileName);
			outFile.deleteOnExit();
			FileOutputStream fos = new FileOutputStream(outFile);
			byte buffer[] = new byte[1024 * 64];
			try {
				while (true) {
					int length = fis.read(buffer);
					if (length != -1)
						fos.write(buffer, 0, length);
					else
						break;
				}
				fis.close();
				fos.close();
				return outFile;
			} catch (IOException ex) {
			}
		} catch (FileNotFoundException ex1) {
		}
		System.out.println("agg.convert.ConverterXML.copyFile:: source  "
				+ fileName + "  NOT FOUND");
		return null;
	}

	private Source getTemplatesSource(String filename) {
		File f = new File(filename);
		StreamSource stream = new StreamSource(f);
		return stream;
	}

	private Source getSource(String filter, String filename) {
		File f = new File(filename);
		if (!f.exists()) {
			return null;
		}
		StreamSource stream = new StreamSource(f);
		return stream;
	}

	private Result getResult(String filter, String filename) {
		File f = new File(filename);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException ex) {
				return null;
			}
		}
		StreamResult stream = new StreamResult(f);
		return stream;
	}

}
