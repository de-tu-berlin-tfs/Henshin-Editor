package agg.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.beans.ExceptionListener;

import org.xml.sax.InputSource;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.w3c.dom.traversal.DocumentTraversal;

/*
import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
*/


/**
 * Implementation of routines for reading and writing AGG XML documents.
 */

public class XMLHelper implements ExceptionListener {

	private long lID;
	boolean isAGG;
	private Document doc;

	private Map<Object, Object> object2index;
	private Map<Object, Object> index2object;

	private Map<Object, Object> index2element;
	private Map<Object, Object> element2index;

	private Vector<Node> estack; 

	private Vector<Node> chld_stack;

	private int esp;

	private static final String VERSION = "1.0";

	private String version = "";

	private String ioException = "";

	private String dir = System.getProperty("user.dir");
	
	

	public XMLHelper() {

		/*
		// Java XML Document
		this.doc = new DocumentImpl();

		Element top_elem = null;
		Element e = this.doc.createElement("Document");

		this.version = VERSION;
		e.setAttribute("version", this.version);

		this.doc.appendChild(e);
		top_elem = this.doc.getDocumentElement();

		this.object2index = new HashMap<Object, Object>(400);
		this.index2object = new HashMap<Object, Object>(400);
		this.index2element = new HashMap<Object, Object>(400);
		this.element2index = new HashMap<Object, Object>(400);
		
		this.estack = new Vector<Node>();
		this.chld_stack = new Vector<Node>();
		this.esp = 0;
		push(top_elem);
		*/
	}

	public String getDocumentVersion() {
		return this.version;
	}

	public String getO2I(Object o) {
		if (this.object2index.containsKey(o)) {
			return (String) this.object2index.get(o);
		}
		return "";
	}

	public Object getI2O(String i) {
		if (this.index2object.containsKey(i)) {
			return this.index2object.get(i);
		}
		return null;
	}
	
	private String newO2I(Object o) {
		String newi = getO2I(o);
		if (newi.length() == 0) {
			newi = (String.valueOf("I"))
					.concat(String.valueOf(this.index2object.size()));
			this.index2object.put(o, newi);
			this.object2index.put(o, newi);
		}
		return newi;
	}

	public boolean save_to_xml(String fname) {
		/*
		OutputFormat format = new OutputFormat(this.doc, "UTF-8", true);
		File f = new File(fname);
		// System.out.println("XMLHelper.save_to_xml file: "+f.getParent() +"
		// "+f.getName());
		if (f.exists()) {
			this.dir = f.getParent();
			if (this.dir == null)
				this.dir = System.getProperty("user.dir");
			if (!(new File(this.dir)).canWrite()) {
				this.dir = System.getProperty("user.dir");
				if (!(new File(this.dir)).canWrite()) {
					System.out
							.println("XMLHelper.save_to_xml: cannot write file to  "
									+ this.dir);
					return false;
				}
			}
		}

		FileOutputStream os = null;
		try {
			os = new FileOutputStream(f);

			XMLSerializer serializer = new XMLSerializer(os, format);
			serializer.setOutputByteStream(os);
			serializer.serialize(this.doc);

			os.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}*/
		return true;
	}

	public boolean read_from_xml(String fname) {
		File f = new File(fname);
		if (f.exists()) {
			this.dir = f.getParent();
			if (this.dir == null)
				this.dir = System.getProperty("user.dir");

			if (!(new File(this.dir)).canWrite()) {
				this.dir = System.getProperty("user.dir");
				if (!(new File(this.dir)).canWrite()) {
					System.out.println("XMLHelper.read_from_xml "
									+ fname
									+ ": failed! Cannot write to directory: "+this.dir);
					return false;
				}
			}
		} else {
			System.out.println("XMLHelper.read_from_xml::  Cannot read! File: "
					+ fname + "  doesn't exist!");
			return false;
		}

		this.esp = 0;
		try {
			// read file
			InputSource inSrc = new InputSource();
			BufferedReader in = new BufferedReader(new FileReader(fname));
			inSrc.setCharacterStream(in);

			/*
			// java xml parser
			DOMParser parser = new DOMParser();
			try {
			parser.parse(inSrc);			
			this.doc = parser.getDocument();
			this.doc.getDocumentElement().normalize();
			} catch (org.xml.sax.SAXParseException saxex) {
				System.out.println("XMLHelper.read_from_xml -> DOMParser.parse: "+saxex.getLocalizedMessage());
				return false;
			}*/
		} catch (IOException iox) {
			System.out.println("XMLHelper.read_from_xml: "+iox.getLocalizedMessage());
//			iox.printStackTrace();
			return false;
		} catch (Exception e) {
			System.out.println("XMLHelper.read_from_xml: "+e.getLocalizedMessage());
//			e.printStackTrace();
			return false;
		}

		isAGG = fname.endsWith(".ggx") || fname.endsWith(".cpx") || fname.endsWith(".rsx");
		
		push(this.doc.getDocumentElement());

		NodeIterator ni = ((DocumentTraversal) this.doc).createNodeIterator(top(),
				NodeFilter.SHOW_ALL, new NodeFilter() {
					public short acceptNode(Node n) {
						if (n.getNodeType() != Node.ELEMENT_NODE)
							return NodeFilter.FILTER_SKIP;
						Element e = (Element) n;
						if (e.getAttributeNode("ID") == null) {
							if (isAGG)
								return NodeFilter.FILTER_SKIP;
						}
						return NodeFilter.FILTER_ACCEPT;
					}
				}, true);
		Node n;
		while ((n = ni.nextNode()) != null) {
			Element e = (Element) n;
			String id = "";
			if (e.getAttributeNode("ID") != null) {
				id = e.getAttribute("ID");
				this.index2element.put(id, e);
			}
			else if (!isAGG) {
				id = "I".concat(String.valueOf(lID++));
				this.index2element.put(id, e);
				this.element2index.put(e, id);
			}
			else
				break;
		}

		this.version = this.doc.getDocumentElement().getAttribute("version");
		
		return true;
	}

	
	/*
	 * public static boolean hasGermanSpecialCh(String str){
	 * if((str.indexOf('ö') != -1) || (str.indexOf('Ö') != -1) ||
	 * (str.indexOf('ä') != -1) || (str.indexOf('Ä') != -1) || (str.indexOf('ü') !=
	 * -1) || (str.indexOf('Ü') != -1) || (str.indexOf('ß') != -1) ||
	 * (str.indexOf(' ') != -1)) return true; else return false; }
	 * 
	 * public static String replaceGermanSpecialCh(String name){ String str =
	 * name; str = str.replaceAll("ö","oe"); str = str.replaceAll("Ö","Oe"); str =
	 * str.replaceAll("ä","ae"); str = str.replaceAll("Ä","Ae"); str =
	 * str.replaceAll("ü","ue"); str = str.replaceAll("Ü","Ue"); str =
	 * str.replaceAll("ß","ss"); str = str.replaceAll(" ",""); return str; }
	 */

	public String getIOException() {
		return this.ioException;
	}

	public void push(Node e) {
		this.estack.setSize(this.esp + 1);
		this.estack.set(this.esp, e);
		this.chld_stack.setSize(this.esp + 1);
		this.chld_stack.set(this.esp, e.getFirstChild());
		this.esp++;
	}

	public Element top() {
		if (this.esp <= 0)
			return null;
		return (Element) this.estack.get(this.esp - 1);
	}

	/**
	 * Cycled above the children of the current DOM-Element.<br>
	 * Only elements of the type <code>Node.ELEMENT_NODE</code> taken in account.
	 */
	private Element top_child() {
		return next_child(this.esp - 1);
	}

	private Element next_child(int i) {
		if (i < 0)
			return null;
		Node n = this.chld_stack.get(i);
		Node m;
		for (m = n; (m != null) && (m.getNodeType() != Node.ELEMENT_NODE); m = m
				.getNextSibling()) {
			;
		}
		if (m != null) {
			Node sib = m.getNextSibling();
			Node sib1;
			for (sib1 = sib; (sib1 != null)
					&& (sib1.getNodeType() != Node.ELEMENT_NODE); sib1 = sib1
					.getNextSibling()){
				;
			}
			this.chld_stack.set(i, sib1);
		} else {
			this.chld_stack.set(i, top().getFirstChild());
		}
		return (Element) m;
	}

	/**
	 * Returns the parent of current element.
	 */
	private Element parent() {
		if (this.esp <= 1)
			return null;
		return (Element) this.estack.get(this.esp - 2);
	}

	private void pop() {
		if (this.esp <= 0)
			return;
		this.esp--;
		this.estack.setSize(this.esp);
		this.chld_stack.setSize(this.esp);
	}

	public Document getDoc() {
		return this.doc;
	}

	/**
	 * Opens already saved object XMLObject o for further work.
	 * The XMLObject t must point to <code>this</code> when this method
	 * is called inside of the XwriteObject(XMLHelper).
	 */
	public boolean openObject(XMLObject o, XMLObject t) {
		if (o == null) {
			return false;
		}
		String newi = getO2I(o);
		String thisi = getO2I(t);
		if (newi.length() == 0 || thisi.length() == 0) {
			return false;
		}
		push((Element) this.index2element.get(newi));
		if (this.index2element.get(thisi) == null) {
			this.index2element.put(thisi, top());
		}
		return true;
	}

	public boolean reopenObject(XMLObject o, XMLObject t) {
		if (o == null) {
			return false;
		}
		String newi = getO2I(o);
		String thisi = getO2I(t);
		if (newi.length() == 0 || thisi.length() == 0) {
			return false;
		}
		push((Element) this.index2element.get(newi));
		if (this.index2element.get(thisi) == null) {
			this.index2element.put(thisi, top());
		}
		return true;
	}

	/**
	 * Opens the element XMLObject o of the element XMLObject t for reading.
	 */
	public boolean peekObject(XMLObject o, XMLObject t) {
		if (o == null)
			return false;
		String id = getO2I(o);
		if (id.length() == 0)
			return false;
		if (t != null && getO2I(t).length() == 0) {
			this.object2index.put(id, t);
		}
		push((Element) this.index2element.get(id));
		return true;
	}

	/**
	 * Creates a new DOM-Element (with ID) of the specified XMLObject t.<br>
	 * This method is called in 
	 * The XMLObject t must point to <code>this</code> when this method is called
	 * inside of the method <code>XwriteObject(XMLHelper h)</code>.
	 */
	public void openNewElem(String tagname, XMLObject t) {
		Element e;
		String newi = getO2I(t);
		/* Ja, o muss schon einen Index haben (von addObject()) */
		if (newi.length() != 0 && !"".equals(tagname)) {
			e = this.doc.createElement(tagname);
			e.setAttribute("ID", newi);
			this.index2element.put(newi, e);
			push(e);
		}
	}

	/**
	 * Creates a new Element (without ID) of the current DOM-Element.
	 */
	public void openSubTag(String tagname) {
		if (!"".equals(tagname)) {
			Element e = this.doc.createElement(tagname);
			top().appendChild(e);
			push(e);
		}
	}

	/**
	 * Read a DOM-Element with the specified tagname.
	 */
	public boolean readSubTag(String tagname) {
		Element e;
		do {
			e = top_child();
			if (e == null)
				return false;
			if (e.getTagName().equals(tagname))
				break;
		} while (true);
		push(e);
		return true;
	}

	/**
	 * Read a DOM-Element with the name which is in the given list.
	 */
	public boolean readSubTag(List<String> tagnames) {
		Element e;
		do {
			e = top_child();
			if (e == null)
				return false;
			if (tagnames.contains(e.getTagName()))
				break;
		} while (true);
		push(e);
		return true;
	}

	/**
	 * Reads one DOM-Element only. Returns the name of this element or null if
	 * no more elements.
	 */
	public String readSubTag() {
		Element e;
		String tagname = "";
		e = top_child();
		if (e == null)
			return null;
		tagname = e.getTagName();
		push(e);
		return tagname;
	}

	/**
	 * Real work routine of object saving. Used by addObjectRef() and
	 * addObjectSub().
	 */
	public void addObject(String mem_name, XMLObject o, boolean sub) {
		if (o == null)
			return;
		
		String newi = getO2I(o);		
		if (newi.length() == 0) {
			newi = newO2I(o);
			this.index2element.put(newi, null);
			o.XwriteObject(this);
			if (this.index2element.get(newi) == null) {
				/*
				 * this O decided to _not_ create any element or reopen other
				 * Elements. I don't know yet, but I tend to forbid that.
				 */
			} else {
				Element el = (Element) this.index2element.get(newi);
				if (el.getParentNode() == null) {
					if (sub) {
						top().appendChild(el);
					} else {
						parent().appendChild(el);
					}
				}
			}
		} 
		else {
//			 Its an error, if o should be a subobject, but its already saved
//			 Diagnostic it.
//			if (sub) {
//				 System.err.println("XMLHelper: A subobject is already saved");
//			}
		}
		if (!sub) {
			top().setAttribute(mem_name, newi);
		}
	}

	/**
	 * The specified XMLObject o will be saved as a Sub-Element of the current DOM-Element.
	 * The XMLObject o must not be saved before. It gets an ID.
     * The ID is saved as Attribut <code>mem_name</code> of the current DOM-Element. 
	 */
	public void addObjectRef(String mem_name, XMLObject o) {
		addObject(mem_name, o, false);
	}

	/**
	 * The specified XMLObject o will be saved as a Sub-Element of the current DOM-Element.
	 * The XMLObject o must not be saved before. It gets an ID.
	 */
	public void addObjectSub(XMLObject o) {
		addObject("", o, true);
	}

	/**
	 * Workhorse of getObjectRef() and getObjectSub().
	 */
	public XMLObject getObject(String mem_name, XMLObject templ, boolean sub) {
		if (!sub) {
			/* mem_name has a reference to an Object. */
			String i = "";
			String s = top().getAttribute(mem_name);
			if (!s.equals("")) {
				i = s;
				XMLObject o = (XMLObject) this.index2object.get(i);
				if (o == null && templ != null) {
					o = templ;
					this.index2object.put(i, o);
					this.object2index.put(o, i);
					o.XreadObject(this);
					if (this.index2element.get(i) == null) {
						System.out.println("XMLHelper: Object " + templ
								+ " has no DOM elements");
					}
				}
				return o;
			} 
			return null;
		} 
		/* das naechste Unterelement ist unsers. */
		Element e;
		String s;
		do {
			e = top_child();
			if (e == null)
				return null;
			s = e.getAttribute("ID");
			if (s.equals("")) {
				// System.out.println("XMLHelper: WARNING: ignoring Element
				// "+e+" as it has no ID");
			}
		} while (s.equals(""));
		
		if (!s.equals("")) {
			String i = s;
			if (this.index2object.get(i) != null) {
				System.err.println("XMLHelper: FATAL: subobject ID=" + i
						+ " already read in.");
				return (XMLObject) this.index2object.get(i);
			}
			this.index2object.put(i, templ);
			this.object2index.put(templ, i);
			templ.XreadObject(this);
			if (this.index2element.get(i) == null) {
				System.out.println("XMLHelper: Object " + templ
						+ " has no DOM elements");
			}
			return templ;
		} 
		System.err.println("XMLHelper: getObject: next SubElement  "
						+ e + "  has no ID attribute");
		return null;
	}

	/**
	 * The specified <code>mem_name</code> is interpreted as the ID to find a DOM-Element.
	 * If the DOM-Element already read, the XMLObject associated with this DOM-Element is returned
	 * (also null can be returned).
	 * Otherwise, the specified <code>templ</code> has to be a (empty) XMLObject
	 * which will be filled with the content of the DOM-Element (using templ.XreadObject())
	 * and up to now associated with given ID.<br>
	 * In case the specified <code>mem_name</code> is not found, returns null.
	 */
	public XMLObject getObjectRef(String mem_name, XMLObject templ) {
		return getObject(mem_name, templ, false);
	}

	/**
	 * The next Sub-Element of the current DOM-Element with an ID attribute 
	 * will be associated with the specified XMLObject <code>templ</code>. 
	 * The <code>templ</code> will be filled with the content of the Sub-Element.
	 * This Sub-Element must not be read before and the <code>templ</code> is not null.<br>
	 * Returns the  <code>templ</code> object,
	 */
	public XMLObject getObjectSub(XMLObject templ) {
		return getObject("", templ, true);
	}

	/**
	 * Here the <code>top()</code> element is interpreted as object to read.
	 * The specified XMLObject <code>templ</code> will be filled with the content.<br>
	 * Such a XMLObject <code>templ</code> can be get by <code>peekElement()</code>.
	 */
	public XMLObject loadObject(XMLObject templ) {
		Element e = top();
		if (e == null)
			return null;
		String s;
		s = e.getAttribute("ID");
		if (s.equals("")) {
			System.out.println("XMLHelper: Warning: ignoring Element " + e
					+ " as it has no ID");
			return null;
		} 
		
		String i = s;
		if (this.index2object.get(i) != null) {
			System.err.println("XMLHelper: FATAL: subobject ID=" + i
					+ " already read in.");
			return (XMLObject) this.index2object.get(i);
		}
		this.index2object.put(i, templ);
		this.object2index.put(templ, i);
		templ.XreadObject(this);
		if (this.index2element.get(i) == null) {
			System.out.println("XMLHelper: Object " + templ
					+ " has no DOM elements");
		}
		return templ;
	}

	/**
	 * This method calls <code>templ.XreadObject(this)</code> with aim to enrich its content.
	 */
	public void enrichObject(XMLObject templ) {
//		String id = getO2I(templ);
		templ.XreadObject(this);
	}

	public void addEnumeration(String mem_name, Enumeration<?> e, boolean sub) {
		String refs = "";
		while (e.hasMoreElements()) {
			XMLObject o = (XMLObject) e.nextElement();
			String newi = getO2I(o);
			if (newi.length() == 0) {
				newi = newO2I(o);
				this.index2element.put(newi, null);
				o.XwriteObject(this);
				if (this.index2element.get(newi) == null) {
					/*
					 * this O decided to _not_ create any element or reopen
					 * other Elements. I don't know yet, but I tend to forbid
					 * that.
					 */
					System.err
							.println("XMLHelper: Enumeration-Object has no DOM-Elements "
									+ o.toString());
				} else {
					if (sub) {
						top().appendChild((Element) this.index2element.get(newi));
					} else {
						top().getParentNode().appendChild(
								(Element) this.index2element.get(newi));
					}
				}
			} else {
				/*
				 * Its an error, if o should be an subobject, but its already
				 * saved Diagnostic it.
				 */
				if (sub) {
					// System.err.println("XMLHelper: A subobject is already
					// saved");
				}
			}
			if (!sub) {
				String s = newi;
				if (refs.length() == 0)
					refs = s;
				else
					refs += "," + s;
			}
		}
		if (!sub) {
			top().setAttribute(mem_name, refs);
		}
	}

	public void addIteration(String mem_name, Iterator<?> e, boolean sub) {
		String refs = "";
		while (e.hasNext()) {
			XMLObject o = (XMLObject) e.next();
			String newi = getO2I(o);
			if (newi.length() == 0) {
				newi = newO2I(o);
				this.index2element.put(newi, null);
				o.XwriteObject(this);
				if (this.index2element.get(newi) == null) {
					/*
					 * this O decided to _not_ create any element or reopen
					 * other Elements. I don't know yet, but I tend to forbid
					 * that.
					 */
					System.err
							.println("XMLHelper: Enumeration-Object has no DOM-Elements "
									+ o.toString());
				} else {
					if (sub) {
						top().appendChild((Element) this.index2element.get(newi));
					} else {
						top().getParentNode().appendChild(
								(Element) this.index2element.get(newi));
					}
				}
			} else {
				/*
				 * Its an error, if o should be an subobject, but its already
				 * saved Diagnostic it.
				 */
				if (sub) {
					// System.err.println("XMLHelper: A subobject is already
					// saved");
				}
			}
			if (!sub) {
				String s = newi;
				if (refs.length() == 0)
					refs = s;
				else
					refs += "," + s;
			}
		}
		if (!sub) {
			top().setAttribute(mem_name, refs);
		}
	}

	public Enumeration<Element> getEnumeration(String mem_name, XMLObject templ,
			boolean sub, String tagname) {
		Vector<Element> v = new Vector<Element>();
		push(top());
		Element e;
		while ((e = top_child()) != null) {
			if (e.getTagName().equals(tagname))
				v.add(e);
		}
		pop();
		return v.elements();
	}

	public Iterator<Element> getIteration(String mem_name, XMLObject templ,
			boolean sub, String tagname) {
		Vector<Element> v = new Vector<Element>();
		push(top());
		Element e;
		while ((e = top_child()) != null) {
			if (e.getTagName().equals(tagname))
				v.add(e);
		}
		pop();
		return v.iterator();
	}
	
	public void addList(String mem_name, List<?> list, boolean sub) {
		String refs = "";
		Iterator<?> e = list.iterator();
		while (e.hasNext()) {
			XMLObject o = (XMLObject) e.next();
			String newi = getO2I(o);
			if (newi.length() == 0) {
				newi = newO2I(o);
				this.index2element.put(newi, null);
				o.XwriteObject(this);
				if (this.index2element.get(newi) == null) {
					/*
					 * this O decided to _not_ create any element or reopen
					 * other Elements. I don't know yet, but I tend to forbid
					 * that.
					 */
					System.err
							.println("XMLHelper: Enumeration-Object has no DOM-Elements "
									+ o.toString());
				} else {
					if (sub) {
						top().appendChild((Element) this.index2element.get(newi));
					} else {
						top().getParentNode().appendChild(
								(Element) this.index2element.get(newi));
					}
				}
			} else {
				/*
				 * Its an error, if o should be an subobject, but its already
				 * saved Diagnostic it.
				 */
				if (sub) {
					// System.err.println("XMLHelper: A subobject is already
					// saved");
				}
			}
			if (!sub) {
				String s = newi;
				if (refs.length() == 0)
					refs = s;
				else
					refs += "," + s;
			}
		}
		if (!sub) {
			top().setAttribute(mem_name, refs);
		}
	}

	public List<Element> getList(String mem_name, XMLObject templ,
			boolean sub, String tagname) {
		Vector<Element> v = new Vector<Element>();
		push(top());
		Element e;
		while ((e = top_child()) != null) {
			if (e.getTagName().equals(tagname))
				v.add(e);
		}
		pop();
		return v;
	}

	public void addTopObject(XMLObject o) {
		if (o == null)
			return;
		String newi = getO2I(o);
		if (newi.length() == 0) {
			newi = newO2I(o);
			// System.out.println("adding Element for ID " + newi);
			this.index2element.put(newi, null);
			// System.out.println("size after adding=" + index2element.size());
			o.XwriteObject(this);
			if (this.index2element.get(newi) == null) {
//				System.err.println("XMLHelper: Top-Object has no DOM-Elements "
//						+ o.toString()+" newi:  "+newi);
			} else {
				if (this.doc.getDocumentElement() == null)
					this.doc.appendChild((Element) this.index2element.get(newi));
				else
					this.doc.getDocumentElement().appendChild(
							(Element) this.index2element.get(newi));
			}
		}
	}

	public boolean isTag(String tag, XMLObject th) {
		String i = getO2I(th);
		// System.out.println("isTag: " + tag);
		if (i.length() == 0) {
			System.err
					.println("XMLHelper: FATAL: isTag called, without caller being in order");
			return false;
		}
		Element e = (Element) this.index2element.get(i);
		if (e == null) {
			System.err.println("XMLHelper: FATAL: ID " + i
					+ " has no DOM element");
			return false;
		}
		push(e);
		if (!tag.equals(top().getTagName())) {
//			 System.err.println("XMLHelper: WARNING: I'm expecting tag <"
//					 +tag+"> but I'm in a "+top().getTagName());
			pop();
			return false;
		}
		return true;
	}

	public XMLObject getTopObject(XMLObject t) {
		Element e = next_child(0);
		if (e == null)
			return null;
		
		String i = "";
		String s = e.getAttribute("ID");
		if (!s.equals("")) {
			i = s;
			if (this.index2object.get(i) != null) {
				return (XMLObject) this.index2object.get(i);
			}
			this.index2object.put(i, t);
			this.object2index.put(t, i);
		}
		else if (!isAGG) {
			i = (String)this.element2index.get(e);
			this.index2object.put(i, t);
			this.object2index.put(t, i);
		}
		t.XreadObject(this);
		return t;
	}

	public void peekElement(Object o) {
		push((Element) o);
	}

	public void close() {
		pop();
	}

	public void addObject(String tagname, String mem_name, XMLObject o) {
		Element e;
		String newi = getO2I(o);
		if (newi.length() == 0) {
			/*
			 * Wir muessen O erst noch speichern. Einhaengen tun wir es in der
			 * gleichen Ebene wie das aktuelle Element, also in den selben
			 * parent.
			 */
			newi = newO2I(o);
			e = this.doc.createElement(tagname);
			e.setAttribute("ID", newi);
			top().getParentNode().appendChild(e);
			this.index2element.put(newi, e);
			push(e);
			o.XwriteObject(this);
			pop();
		}
		/* jetzt die Referenz erzeugen */
		top().setAttribute(mem_name, newi);
	}

	/**
	 * O wird direkt ins Document eingehangen, wenns noch nicht gespeichert war.
	 * Da dies ein TopLevel-Objekt ist, wird natuerlich hier nirgends eine
	 * Referenz darauf eingehangen.
	 */
	public void addTopObject(String tagname, XMLObject o) {
		Element e;
		String newi = getO2I(o);
		if (newi.length() == 0) {
			newi = newO2I(o);
			e = this.doc.createElement(tagname);
			e.setAttribute("ID", newi);
			if (this.doc.getDocumentElement() == null)
				this.doc.appendChild(e);
			else
				this.doc.getDocumentElement().appendChild(e);
			push(e);
			o.XwriteObject(this);
			pop();
		}
	}

	/**
	 * Gibt eine Representation des String S zurueck, die als Attribut-Wert
	 * verwendbar ist. (Probleme machen z.b. "#xxx;" Character Einbettungen)
	 */
	public String escapeString(String s) {
		// System.out.println("XMLHelper.escapeString");
		// System.out.println(s);
		if (s == null)
			return null;
		String r = "";
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z')
					|| ('0' <= c && c <= '9') || (c == '.')) {
				r += c;
			} else {
				String t = String.valueOf((int) c);
				r += "/" + t + "/";
			}
		}
		// System.out.println(r);
		return r;
	}

	/**
	 * Wandelt einen String aus escapeString() wieder in urspruengliche Form
	 * zurueck. Es gilt also: unescapeString(escapeString(s)) == s.
	 */
	public String unescapeString(String s) {
		// System.out.println("XMLHelper.unescapeString");
		// System.out.println(s);
		if (s == null)
			return null;
		String r = "";
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '/') {
				int j;
				i++;
				for (j = i; j < s.length() && s.charAt(j) != '/'; j++)
					;
				String t = s.substring(i, j);
				i = j;
				c = (char) Integer.parseInt(t);
				r += c;
			} else {
				r += c;
			}
		}
		// System.out.println(r);
		return r;
	}

	/**
	 * In aktuell offenes DOM-Element wird ein Attribut namens NAME mit Wert
	 * VALUE angelegt. E.g. addAttr("bla", "whatever") resultiert (wenn gerade
	 * ein <BLUBB> Element offen ist) in: "<BLUBB bla="whatever"> ...".
	 */
	public void addAttr(String name, String value) {
		top().setAttribute(name, value);
	}

	/**
	 * Wie addAttr(String, String), nur dass der Wert ein Integer ist.
	 */
	public void addAttr(String name, int value) {
		addAttr(name, Integer.toString(value));
	}

	/**
	 * Beispiel: Value von Basistypen int/Integer mit Wert 999: <int>999</int>;
	 * 
	 * Value von komplexeren Typen (auch String) Vector mit String "hhh" und
	 * "hohoho" sieht so aus: <java version="1.4.1"
	 * class="java.beans.XMLDecoder"> <object class="java.util.Vector"> <void
	 * method="add"> <string>hhh</string> </void> <void method="add">
	 * <string>hohoho</string> </void> </object> </java>
	 * 
	 * Value von eigengeschriebenen Klassen ist als SerializedData : <Entry>
	 * <SerializedData>aced000573720005456e7472799e03e593f62b333b0300025b000 *
	 * e697473446972436f6e74656e74737400135</SerializedData> </Entry>
	 */
	public void addAttrValue(String typeName, Object value) {
		boolean useXMLEncoder = false;
		// if value is null, then add : <string>null</string>
		if (value == null) {
			openSubTag("string");
			Text text = this.doc.createTextNode("null");
			top().appendChild(text);
			push(text);
			close();
			close();
			return;
		}

		Class<?>cl = value.getClass();
		String className = cl.getName();
		// System.out.println("type : "+typeName);
		// System.out.println("Class: "+className);

		if (typeName.equals("string")) { // simulate String			
			String str = (String) value;
			if (str.indexOf("\"") == -1) {
				openSubTag("string");
				Text text = this.doc.createTextNode(str);
				top().appendChild(text);
				push(text);
				close();
				close();
				return;
			} 
			useXMLEncoder = true;
		} else if (typeName.equals("String")) {
			useXMLEncoder = true;
		} else if (typeName.equals("int") || typeName.equals("Integer")) {
			openSubTag("int");
			Text text = null;
			if (value instanceof Integer) 
				text = this.doc.createTextNode(((Integer) value).toString());
			else if (value instanceof String) {
				try {
					Integer I = Integer.valueOf((String)value);
					text = this.doc.createTextNode(I.toString());
				}
				catch (NumberFormatException ex) {
					System.out.println("XMLHelper.addAttrValue:  tried to write Integer: "+value+".  FAILED! "+ex.getMessage());
				}
			}
			if (text != null) {
				top().appendChild(text);
				push(text);
				close();
			} 
			close();
			return;
		} else if (typeName.equals("boolean") || typeName.equals("Boolean")) {
			openSubTag("boolean");
			if (value instanceof Boolean) {
				Text text = this.doc.createTextNode(((Boolean) value).toString());
				top().appendChild(text);
				push(text);
				close();
			} 
			close();
			return;
		} else if (typeName.equals("char") || typeName.equals("Character")) {
			openSubTag("char");
			Text text = this.doc.createTextNode(((Character) value).toString());
			top().appendChild(text);
			push(text);
			close();
			close();
			return;
		} else if (typeName.equals("float") || typeName.equals("Float")) {
			if (value instanceof Float) {
				openSubTag("float");
				Text text = this.doc.createTextNode(((Float) value).toString());				
				top().appendChild(text);
				push(text);
				close();
				close();
				return;
			} 
			openSubTag("float");
			Text text = null;
			if (className.equals("java.lang.Double")) {
				Float floatVal = Float.valueOf(((Double) value).floatValue());
				text = this.doc.createTextNode(floatVal.toString());
			} else if (className.equals("java.lang.String")) {
				try {
					Float F = Float.valueOf((String) value);
					text = this.doc.createTextNode(F.toString());
				}
				catch (NumberFormatException ex) {}
			}
			if (text != null) {
				top().appendChild(text);
				push(text);
				close();
			}
			close();
			return;
			
		} else if (typeName.equals("double") || typeName.equals("Double")) {
			if (value instanceof Double) {
				openSubTag("double");
				Text text = this.doc.createTextNode(((Double) value).toString());
				// System.out.println(className+" saved as Double");
				top().appendChild(text);
				push(text);
				close();
				close();
				return;
			} 
			openSubTag("double");
			Text text = null;
			if (className.equals("java.lang.Double")) {
				text = this.doc.createTextNode(((Double) value).toString());
			} else if (className.equals("java.lang.String")) {
				try {
					Double D = Double.valueOf((String) value);
					text = this.doc.createTextNode(D.toString());
				}
				catch (NumberFormatException ex) {}
			}
			if (text != null) {
				top().appendChild(text);
				push(text);
				close();
			}
			close();
			return;
			
		} else if (typeName.equals("long") || typeName.equals("Long")) {
			if (value instanceof Long) {
				openSubTag("long");
				Text text = this.doc.createTextNode(((Long) value).toString());
				top().appendChild(text);
				push(text);
				close();
				close();
			} else {
				openSubTag("long");
				Text text = null;
				if (className.equals("java.lang.Float")) {
					Long longVal = Long.valueOf(((Float) value).longValue());
					text = this.doc.createTextNode(longVal.toString());
				} else if (className.equals("java.lang.Integer")) {
					Long L = Long.valueOf(((Integer) value).longValue());
					text = this.doc.createTextNode(L.toString());
				} else if (className.equals("java.lang.String")) {
					try {
						Long L = Long.valueOf((String) value);
						text = this.doc.createTextNode(L.toString());
					}
					catch (NumberFormatException ex) {}
				}
				if (text != null) {
					top().appendChild(text);
					push(text);
					close();
				}
				close();
			}
			return;
		} else if (typeName.equals("short") || typeName.equals("Short")) {
			if (value instanceof Short) {
				openSubTag("short");
				Text text = this.doc.createTextNode(((Short) value).toString());
				top().appendChild(text);
				push(text);
				close();
				close();
			} else {
				openSubTag("short");
				Text text = null;
				if (className.equals("java.lang.Float")) {
					Short shortVal = Short.valueOf(((Float) value).shortValue());
					text = this.doc.createTextNode(shortVal.toString());
				} else if (className.equals("java.lang.Integer")) {
					Short Sh = Short.valueOf(((Integer) value).shortValue());
					text = this.doc.createTextNode(Sh.toString());
				} else if (className.equals("java.lang.String")) {
					try {
						Short Sh = Short.valueOf((String) value);
						text = this.doc.createTextNode(Sh.toString());
					}
					catch (NumberFormatException ex) {}
				}
				if (text != null) {
					top().appendChild(text);
					push(text);
					close();
				}
				close();
			}
			return;
		} else if (typeName.equals("byte") || typeName.equals("Byte")) {
			if (value instanceof Byte) {
				openSubTag("byte");
				Text text = this.doc.createTextNode(((Byte) value).toString());
				top().appendChild(text);
				push(text);
				close();
				close();
			} else {
				openSubTag("byte");
				Text text = null;
				if (value instanceof Integer) {
					Byte B = Byte.valueOf(((Integer) value).byteValue());
					text = this.doc.createTextNode(B.toString());
				} else if (value instanceof String) {					
					try {
						Byte B = Byte.valueOf((String) value);
						text = this.doc.createTextNode(B.toString());
					}
					catch (NumberFormatException ex) {}
				}
				if (text != null) {
					top().appendChild(text);
					push(text);
					close();
				}
				close();
			}
			return;
		} else if (typeName.equals("Object")) { // TEST
			addAttrUsingXMLEncoder("Value", value);
			this.doc.getDocumentElement().normalize();
			return;
		} else {
			// teste value Object auf Instantiation
			try {
				cl.newInstance();
				useXMLEncoder = true;
			} catch (InstantiationException ex1) {
				useXMLEncoder = false;
			} catch (IllegalAccessException ex2) {
				useXMLEncoder = false;
			}
		}

		if (useXMLEncoder)
			addAttrUsingXMLEncoder("Value", value);
		else if (typeName.equals("Enumeration")) {
			Vector<Object> vec = new Vector<Object>();
			while(((Enumeration<?>) value).hasMoreElements())
				vec.add(((Enumeration<?>) value).nextElement());
			addAttrUsingXMLEncoder("Value", vec);
		} 
		else {
			System.out.println("\tContinue with using SerializedData for saving.");

			addAttrUsingSerializedData("Value", typeName, value);
		}

		this.doc.getDocumentElement().normalize();
	}

	public Object getElementData(Element e) {
		if (e != null && e.hasChildNodes()) {
			Text text = (Text) e.getFirstChild();
			try {
				return text.getData();
			} catch (DOMException ex) {}
		}
		return null;
	}
	
	public Object getAttrValue(String typeName) {
		Object result = null;
		boolean useXMLDecoder = false;
		// read variable name or null as value
		if (readSubTag("string")) {
			Element e = top();
			if (e.hasChildNodes()) {
				Text text = (Text) e.getFirstChild();
				try {
					String data = text.getData();
					result = data;
				} catch (DOMException ex) {
				}
			}
			close();
			return result;
		}

		if (typeName.equals("int") || typeName.equals("Integer")) {
			if (readSubTag("int")) {
				Element e = top();
				if (e.hasChildNodes()) {
					Text text = (Text) e.getFirstChild();
					try {
						String data = text.getData();
						result = Integer.valueOf(data);
					} catch (DOMException ex) {
					}
				}
				close();
				return result;
			}
		} else if (typeName.equals("boolean") || typeName.equals("Boolean")) {
			if (readSubTag("boolean")) {
				Element e = top();
				if (e.hasChildNodes()) {
					Text text = (Text) e.getFirstChild();
					try {
						String data = text.getData();
						result = Boolean.valueOf(data);
					} catch (DOMException ex) {
					}
				}
				close();
				return result;
			}
		} else if (typeName.equals("char") || typeName.equals("Character")) {
			if (readSubTag("char")) {
				Element e = top();
				if (e.hasChildNodes()) {
					Text text = (Text) e.getFirstChild();
					try {
						String data = text.getData();
						result = Character.valueOf(data.charAt(0));
					} catch (DOMException ex) {
					}
				}
				close();
				return result;
			}
		} else if (typeName.equals("float") || typeName.equals("Float")) {
			if (readSubTag("float")) {
				Element e = top();
				if (e.hasChildNodes()) {
					Text text = (Text) e.getFirstChild();
					try {
						String data = text.getData();
						if (typeName.equals("float"))
							result = Float.valueOf(data); // Double
						else
							result = Float.valueOf(data);
					} catch (DOMException ex) {
					}
				}
				close();
				return result;
			}
		} else if (typeName.equals("double") || typeName.equals("Double")) {
			if (readSubTag("double")) {
				Element e = top();
				if (e.hasChildNodes()) {
					Text text = (Text) e.getFirstChild();
					try {
						String data = text.getData();
						result = Double.valueOf(data);
					} catch (DOMException ex) {
					}
				}
				close();
				return result;
			}
		} else if (typeName.equals("long") || typeName.equals("Long")) {
			if (readSubTag("long")) {
				Element e = top();
				if (e.hasChildNodes()) {
					Text text = (Text) e.getFirstChild();
					try {
						String data = text.getData();
						if (typeName.equals("long")) {
							// Float res = Float.valueOf(data);
							// result = Integer.valueOf(res.intValue());
							result = Long.valueOf(data);
						} else
							result = Long.valueOf(data);
					} catch (DOMException ex) {
					}
				}
				close();
				return result;
			}
		} else if (typeName.equals("short") || typeName.equals("Short")) {
			if (readSubTag("short")) {
				Element e = top();
				if (e.hasChildNodes()) {
					Text text = (Text) e.getFirstChild();
					try {
						String data = text.getData();
						if (typeName.equals("short"))
							// result = Integer.valueOf(data);
							result = Short.valueOf(data);
						else
							result = Short.valueOf(data);
					} catch (DOMException ex) {
					}
				}
				close();
				return result;
			}
		} else if (typeName.equals("byte") || typeName.equals("Byte")) {
			if (readSubTag("byte")) {
				Element e = top();
				if (e.hasChildNodes()) {
					Text text = (Text) e.getFirstChild();
					try {
						String data = text.getData();
						if (typeName.equals("byte"))
							// result = Integer.valueOf(data);
							result = Byte.valueOf(data);
						else
							result = Byte.valueOf(data);
					} catch (DOMException ex) {
					}
				}
				close();
				return result;
			}
		} else if (typeName.equals("string")) {
				return null;
		} else if (typeName.equals("String")) {
			useXMLDecoder = true;
		} else if (readSubTag("object")) {
			String s = readAttr("class");
			if (s.equals(typeName)) {
				if (readSubTag("SerializedData")) {
					result = getAttrUsingSerializedData("SerializedData");
					close(); // SubTag("SerializedData")
					close(); // SubTag("object") before return
					return result;
				}
			} else
				close();
		} else
			useXMLDecoder = true;

		if (useXMLDecoder)			
			result = getAttrUsingXMLDecoder("Value");
		
		if (typeName.equals("Enumeration") && result instanceof Vector) {
			Vector<?> vec = (Vector<?>) result;			
			Enumeration<?> en = vec.elements();
			result = en;
		}
		
		return result;
	}

	private void addAttrUsingXMLEncoder(String tagName, Object value) {
		if (!tagName.equals("Value"))
			return;
		// System.out.println("XMLHelper.addAttrUsingXMLEncoder ...");
		// use ByteArrayOutputStream !
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// speichere XMLEncoder Ausgabe in tmp File
		// String xmlFilename = dir+File.separator+"AGG_tmpout1.xml";
		// File xmlFile = new File(xmlFilename);
		// xmlFile.deleteOnExit();
		try {
			// XMLEncoder e = new XMLEncoder(
			// new BufferedOutputStream(
			// new FileOutputStream(xmlFile)));
			// e.writeObject(value);
			// e.close();

			// use ByteArrayOutputStream !
			final XMLEncoder e1 = new XMLEncoder(new BufferedOutputStream(baos));
			e1.writeObject(value);
			e1.close();
			// System.out.println("XMLEncoder using ByteArrayOutputStream::
			// "+baos.toString());

		}
		// catch(java.io.FileNotFoundException ex) {
		// System.out.println("agg.util.XMLHelper.addAttrUsingXMLEncoder: write
		// attribute value FAILED!");
		// }
		catch (ArrayIndexOutOfBoundsException ex1) {
		} catch (Exception ex) {
			System.out
					.println("agg.util.XMLHelper.addAttrUsingXMLEncoder: write attribute value FAILED!");
		}

		// use ByteArrayInputStream
		final ByteArrayInputStream bais = new ByteArrayInputStream(baos
				.toByteArray());

		/*
		try {
			DOMParser parser = new DOMParser();

			// parser.parse(xmlFilename);

			// use ByteArrayInputStream
			parser.parse(new InputSource(bais));
			Document tmpDoc = parser.getDocument();
			tmpDoc.getDocumentElement().normalize();

			// System.out.println("tmpDoc: "+tmpDoc);
			// get element of tmp document
			Element element = tmpDoc.getDocumentElement();
			// System.out.println("tmp Element: "+element+"
			// "+element.getNodeType()+" "+element.getNodeValue());
			try { // import tmp element into document
				Node n = this.doc.importNode(element, true);
				top().appendChild(n);
				push(n);
				close();
			} catch (DOMException ex2) {
				System.out.println("XMLHelper: DOMException: "
						+ ex2.getMessage());
			}
		} catch (IOException iox) {
			iox.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	private Object getAttrUsingXMLDecoder(String tagName) {
		/*
		// System.out.println("XMLHelper.getAttrUsingXMLDecoder "+tagName);
		if (!tagName.equals("Value"))
			return null;
		if (top().getElementsByTagName("java").getLength() == 0)
			return null;

		Object result = null;
		Element element = top();
		// System.out.println(element.getTagName()+" java ");

		Document tmpDoc = new DocumentImpl();
		try {
			Node n = tmpDoc.importNode(element, true);
			tmpDoc.appendChild(n);
		} catch (DOMException ex2) {
			System.out
					.println("XMLHelper.getAttrUsingXMLDecoder : DOMException: "
							+ ex2.getMessage());
			return null;
		}

		final OutputFormat format = new OutputFormat(tmpDoc, "UTF-8", true);

		// use ByteArrayOutputStream
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// use tmp file
		// String xmlFile = dir+File.separator+"AGG_tmpout2.xml";
		// File f = new File(xmlFile);
		// f.deleteOnExit();
		// final FileOutputStream os = new FileOutputStream(f);
		try {
			// use tmp file
			// final XMLSerializer serializer = new XMLSerializer(os, format);
			// serializer.setOutputByteStream(os);
			// serializer.serialize(tmpDoc);

			// use ByteArrayOutputStream
			final XMLSerializer serializer = new XMLSerializer(baos, format);
			serializer.setOutputByteStream(baos);
			serializer.serialize(tmpDoc);
			// System.out.println("XMLSerializer using ByteArrayInputStream::
			// "+baos.toString());

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			// try {if (os != null) os.close();}
			try {
				baos.close();
			} catch (IOException ex) {
			}
		}

		// use ByteArrayInputStream
		final ByteArrayInputStream bais = new ByteArrayInputStream(baos
				.toByteArray());

		// lese File mit XMLDecoder und hole Object result
		try {
			// use file
			// final XMLDecoder d = new XMLDecoder(
			// new BufferedInputStream(
			// new FileInputStream(xmlFile)),
			// this, this);
			// result = d.readObject();
			// d.close();

			// use ByteArrayInputStream
			final XMLDecoder d = new XMLDecoder(new BufferedInputStream(bais),
					this, this);
			result = d.readObject();
			d.close();
			// System.out.println("XMLDecoder: resObj: "+result);
		}
		// catch(FileNotFoundException ex) { }
		catch (ArrayIndexOutOfBoundsException ex1) {
		}
		return result;
		*/
		return null;
	}

	private void addAttrUsingSerializedData(String tagName, String typeName,
			Object value) {
		if (!tagName.equals("Value"))
			return;
		// System.out.println("XMLHelper.addAttrUsingSerializedData");
		java.lang.Class<?>cl = value.getClass();
		if (!(value instanceof java.io.Serializable)) {
			System.out
					.println("XMLHelper: WARNING!  Save attribute value:  Class "
							+ cl.getName()
							+ "  CANNOT BE SAVED.\n\tThis class should implements interface java.io.Serializable.");
			return;
		}
		// System.out.println( cl.getPackage()+" :: "+cl.getName());
		String clName = cl.getName();
		if (clName.indexOf(typeName) == -1) {
			Thread.dumpStack();
			System.out
					.println("XMLHelper: WARNING! Save attribute value:  Class "
							+ cl.getName()
							+ "  CANNOT BE SAVED.\n\tClass name and the name of the AttributeMember type are not equal.");
			return;
		}

		openSubTag("object");
		addAttr("class", typeName);
		openSubTag("SerializedData");
		boolean successfullySaved = true;
		
			try {
				ByteArrayOutputStream baOut = new ByteArrayOutputStream();
				ObjectOutputStream oOut = new ObjectOutputStream(baOut);
				oOut.writeObject(value);
				oOut.flush();
				byte[] bytes = baOut.toByteArray();
				char[] conversionTable = { '0', '1', '2', '3', '4', '5', '6',
						'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
				int blockSize = 16;
				for (int i = 0; i < bytes.length;) {
					int thisBlock = bytes.length - i;
					if (thisBlock > blockSize)
						thisBlock = blockSize;
					String block = "";
					for (int k = 0; k < thisBlock; k++, i++) {
						int b = bytes[i];
						if (b < 0)
							b += 256;
						block = block + String.valueOf(conversionTable[b / 16]);
						block = block + String.valueOf(conversionTable[b % 16]);
					}
					Text text = this.doc.createTextNode(block);
					top().appendChild(text);
					push(text);
					close();
				}
			} catch (IOException e) {
				successfullySaved = false;
			}
		
		if (!successfullySaved) {
			Text text = this.doc.createTextNode("isNULL");
			top().appendChild(text);
			push(text);
			close();
		}
		close();
		close();
	}

	private Object getAttrUsingSerializedData(String tagName) {
		// System.out.println("XMLHelper.getAttrUsingSerializedData");
		Object object = null;

		if (top().getNodeName().equals(tagName)) { // "SerializedData"
			// build a list of all hex data strings
			Vector<String> dataList = new Vector<String>();
			int byteCount = 0;
			String data = null;

			Element e = top();
			if (e.hasChildNodes()) {
				Text text = (Text) e.getFirstChild();
				try {
					data = text.getData();
					// System.out.println(data);
				} catch (DOMException ex) {
				}
			}

			if ((data != null) && !data.equals("isNULL")) {
				int len = data.length();
				if (len >= 2) {
					dataList.add(data);
					byteCount += len / 2;
				}
			}

			// convert the list into a byte array
			byte[] bytes = new byte[byteCount];
			Iterator<?> dataIt = dataList.iterator();
			String currentData = null;
			int dataIndex = 0;
			int dataLen = 0;
			for (int i = 0; i < byteCount; i++) {
				if (dataIndex + 1 >= dataLen) {
					currentData = (String) dataIt.next();
					dataIndex = 0;
					dataLen = currentData.length();
				}
				int b = 0;
				for (int d = 0; d < 2; d++) {
					b *= (byte) 16;
					if (currentData != null) {
						switch (currentData.charAt(dataIndex)) {
						case '0':
							b += 0;
							break;
						case '1':
							b += 1;
							break;
						case '2':
							b += 2;
							break;
						case '3':
							b += 3;
							break;
						case '4':
							b += 4;
							break;
						case '5':
							b += 5;
							break;
						case '6':
							b += 6;
							break;
						case '7':
							b += 7;
							break;
						case '8':
							b += 8;
							break;
					case '9':
						b += 9;
						break;
					case 'a':
						b += 10;
						break;
					case 'b':
						b += 11;
						break;
					case 'c':
						b += 12;
						break;
					case 'd':
						b += 13;
						break;
					case 'e':
						b += 14;
						break;
					case 'f':
						b += 15;
						break;
					default:
						break;
					}
					dataIndex++;
					}
				}
				if (b > 127)
					b -= 256;
				bytes[i] = (byte) b;
			}
			// deserialize the object from the byte array
			try {
				ByteArrayInputStream baIn = new ByteArrayInputStream(bytes);
				ObjectInputStream oOut = new ObjectInputStream(baIn);
				object = oOut.readObject();
			} catch (Exception exc) {
			}
		}
		// System.out.println(object);
		return object;
	}

	public void addAttrToObject(Object obj, String name, String value) {
		String indx = getO2I(obj); // exmpl: "I123"
		if (!indx.equals("")) {
			Element elem = (Element) this.index2element.get(indx);
			elem.setAttribute(name, value);
		}
	}

	/**
	 * Liest aus aktuell offenem DOM-Element das Attribut namens NAME aus, und
	 * gibt es als String zurueck. Falls kein solches Attribut existiert wird ""
	 * zurueckgegeben.
	 */
	public String readAttr(String name) {
		String r = top().getAttribute(name);
		if (r == null)
			r = "";
		return r;
	}

	/**
	 * Wie readAttr(String), nur wird der Attributwert versucht als Integer zu
	 * interpretieren.
	 */
	public int readIAttr(String name) {
		String s = readAttr(name);
		if (s == "")
			return 0;
		return (Integer.valueOf(s)).intValue();
	}

	public void addSubObject(String tagname, XMLObject o) {
		Element e = this.doc.createElement(tagname);
		push(e);
		o.XwriteObject(this);
		pop();
		top().appendChild(e);
	}

	public Object getObject(String tagname) {
		return null;
	}

	/**
	 * Implements interface ExceptionListener This method is called when a
	 * recoverable exception has been caught.
	 */
	public void exceptionThrown(Exception e) {
		// System.out.println("agg.util.XMLHelper: "+e.getMessage());
	}

	public static  String checkNameDueToSpecialCharacters(final String nameStr) {
		// this check was needed because reported garbled names of the attributes

		if (//nameStr.indexOf('�') != -1 ||
				nameStr.indexOf('&') != -1
				|| nameStr.indexOf('|') != -1
				|| nameStr.indexOf('_') != -1
				|| nameStr.indexOf('.') != -1
				|| nameStr.indexOf('(') != -1
				|| nameStr.indexOf(')') != -1
				|| nameStr.indexOf('^') != -1
				) {
			String test = "";
			for(int i=0; i<nameStr.length(); i++) {
				Character ch = Character.valueOf(nameStr.charAt(i));
				if (Character.getNumericValue(nameStr.charAt(i)) == -1) {
					if (//ch.charValue() == '�' ||
							ch.charValue() == '&'
							|| ch.charValue() == '|'
							|| ch.charValue() == '_'
							|| ch.charValue() == '.'
							|| ch.charValue() == '('
							|| ch.charValue() == ')'
							|| ch.charValue() == '^') {
						test = test.concat(String.valueOf(ch));
					} 
				} else {
					test = test.concat(String.valueOf(ch));
				}					
//				System.out.println(nameStr.charAt(i)
//						+"   "+Character.getNumericValue(nameStr.charAt(i))
//						+" type "+Character.getType(nameStr.charAt(i))
//						+" def "+Character.isDefined(nameStr.charAt(i))
//						+" ISOcont  "+Character.isISOControl(nameStr.charAt(i)));
			}
//			while (test.indexOf("��") != -1) {
//				test = test.replaceAll("��", "�");
//			}
			return test;
		}
		return nameStr;
	}
}
