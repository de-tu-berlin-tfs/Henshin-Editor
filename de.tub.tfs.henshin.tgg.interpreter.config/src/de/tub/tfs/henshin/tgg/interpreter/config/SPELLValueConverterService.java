package de.tub.tfs.henshin.tgg.interpreter.config;


import org.eclipse.xtext.common.services.DefaultTerminalConverters;
import org.eclipse.xtext.conversion.IValueConverter;
import org.eclipse.xtext.conversion.ValueConverter;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.NodeModelBuilder;
import org.eclipse.xtext.nodemodel.impl.RootNode;
import org.eclipse.xtext.util.Strings;

public class SPELLValueConverterService extends DefaultTerminalConverters {

	/* Used to allow for escape symbols in P.A1N.SADM_ORB_CORE.opl "Check\Modify..."
	 * sources: 
	 * - http://pettergraff.blogspot.com/2009/11/xtext-valueconverter.html
	 * - http://www.eclipse.org/forums/index.php?t=msg&th=170689&start=0&
	 * - http://www.eclipse.org/Xtext/documentation/1_0_0/xtext.html#valueconverter
	 * (non-Javadoc)
	 * @see org.eclipse.xtext.common.services.DefaultTerminalConverters#STRING()
	 */
	public static String convertToJavaString(String theString, boolean useUnicode) {
		int len = theString.length();
		int bufLen = len * 2;
		if (bufLen < 0) {
			bufLen = Integer.MAX_VALUE;
		}
		StringBuffer outBuffer = new StringBuffer(bufLen);

		for (int x = 0; x < len; x++) {
			char aChar = theString.charAt(x);
			// Handle common case first, selecting largest block that
			// avoids the specials below
			if ((aChar > 61) && (aChar < 127)) {
				if (aChar == '\\') {
					outBuffer.append('\\');
					outBuffer.append('\\');
					continue;
				}
				outBuffer.append(aChar);
				continue;
			}
			switch (aChar) {
			case ' ':
				outBuffer.append(' ');
				break;
			case '\t':
				outBuffer.append('\\');
				outBuffer.append('t');
				break;
			case '\n':
				outBuffer.append('\\');
				outBuffer.append('n');
				break;
			case '\r':
				outBuffer.append('\\');
				outBuffer.append('r');
				break;
			case '\f':
				outBuffer.append('\\');
				outBuffer.append('f');
				break;
			case '\b':
				outBuffer.append('\\');
				outBuffer.append('b');
				break;
			case '\'':
				outBuffer.append('\\');
				outBuffer.append('\'');
				break;
			default:
				if (useUnicode && ((aChar < 0x0020) || (aChar > 0x007e))) {
					outBuffer.append('\\');
					outBuffer.append('u');
					outBuffer.append(Strings.toHex((aChar >> 12) & 0xF));
					outBuffer.append(Strings.toHex((aChar >> 8) & 0xF));
					outBuffer.append(Strings.toHex((aChar >> 4) & 0xF));
					outBuffer.append(Strings.toHex(aChar & 0xF));
				} else {
					outBuffer.append(aChar);
				}
			}
		}
		return outBuffer.toString();
	}
	
	@ValueConverter(rule = "STRING")
	public IValueConverter<String> STRING() {
		return new IValueConverter<String>() {
			public String toString(String value) {
				if (value == null)
					throw new ValueConverterException("STRING-value may not be null.", null, null);
				return "'" + convertToJavaString(value, false) + "'";
			}

			public String toValue(String string, INode node) {
				if (string == null)
					return null;
				try {
					if (node.getText().startsWith("\"")){
						RootNode rootNode = (RootNode) node.getRootNode();
						NodeModelBuilder b = new NodeModelBuilder();
						String completeContent = rootNode.getCompleteContent();
						int offset = node.getTotalOffset();
						int length = node.getTotalLength();

						completeContent = completeContent.substring(0,offset) +
								"'" + completeContent.substring(offset + 1, offset + length -1) + "'" +
								completeContent.substring(offset + length,completeContent.length());

						b.setCompleteContent(rootNode, completeContent);
						
					}
					
					return string.substring(1,string.length()-1);	// return value of string (without '"')
				} catch (IllegalArgumentException e) {
					throw new ValueConverterException(e.getMessage(), node, e);
				}
			}
		};
	}
	
}

	

