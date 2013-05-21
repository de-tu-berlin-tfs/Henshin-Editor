package de.tub.tfs.henshin.tggeditor.actions.imports;

import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.impl.SAXXMLHandler;
import org.eclipse.emf.ecore.xmi.impl.XMLLoadImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

final class LoggingXMLLoad extends XMLLoadImpl {
	private Stack<String> stack;

	LoggingXMLLoad(XMLHelper helper,Stack<String> elemStack) {
		super(helper);
		this.stack = elemStack;
	}

	@Override
	protected DefaultHandler makeDefaultHandler() {
		// TODO Auto-generated method stub
		return new SAXXMLHandler(resource,
				helper, options) {
			@Override
			public void endElement(String uri,
					String localName,
					String name) {

				super.endElement(uri,
						localName, name);
				String cur = stack
						.pop();
				if (!cur.equals(name)) {
					System.out
							.println("ERROR!");
				}
			}

			@Override
			public void endEntity(String name) {
				// TODO Auto-generated
				// method stub
				super.endEntity(name);
			}

			@Override
			public void startElement(
					String uri,
					String localName,
					String name) {
				stack.push(name);
				super.startElement(uri,
						localName, name);

			}

			@Override
			public void startEntity(String name) {
				// TODO Auto-generated
				// method stub
				super.startEntity(name);
			}
			
			@Override
					public void characters(char[] ch, int start, int length) {
						
						super.characters(ch, start, length);
					}

			
			@Override
					protected void processObject(EObject object) {
						EStructuralFeature mixed = null;
						if (object == null){
							super.processObject(object);
							return;
						}
						if ((mixed = object.eClass().getEStructuralFeature(LoadReconstructXMLForSource.MIXEDELEMENTFEATURE) ) != null){
							object.eSet(mixed, new BasicFeatureMap((InternalEObject) object,XMLTypePackage.Literals.XML_TYPE_DOCUMENT_ROOT__TEXT.getFeatureID(), XMLTypePackage.Literals.XML_TYPE_DOCUMENT_ROOT__TEXT));
						}
						super.processObject(object);
					}
			
			@Override
					protected void handleMixedText() {
						if (text.length() == 0){
							text = null;
							return;
						}
						if (text.charAt(0) == '\n'){
							text = null;
							return;
						}
						super.handleMixedText();
					}
		};
	}

	@Override
	protected SAXParser makeParser()
			throws ParserConfigurationException,
			SAXException {
		SAXParserFactory f = SAXParserFactory
				.newInstance();

		return f.newSAXParser();
	}
}