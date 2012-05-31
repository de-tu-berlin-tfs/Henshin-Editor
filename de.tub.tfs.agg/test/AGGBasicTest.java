

import java.io.File;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import agg.xt_basis.BaseFactory;
import agg.xt_basis.CompletionStrategySelector;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.Node;
import agg.xt_basis.Rule;
import agg.xt_basis.Type;
import agg.xt_basis.TypeException;
import agg.xt_basis.TypeGraph;
import agg.xt_basis.TypeSet;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.GraTra;
import agg.xt_basis.DefaultGraTraImpl;
import agg.xt_basis.LayeredGraTraImpl;
import agg.xt_basis.PriorityGraTraImpl;
import agg.xt_basis.RuleSequencesGraTraImpl;
import agg.xt_basis.GraTraEvent;
import agg.xt_basis.GraTraEventListener;
//import agg.xt_basis.Match;
import agg.util.XMLHelper;
import agg.attribute.impl.CondTuple;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.DeclTuple;
import agg.convert.ConverterXML;

public class AGGBasicTest implements GraTraEventListener {
	
	private static XMLHelper h;

	private static GraGra gragra;

	private static GraGra impGraGra;
	
	private static Graph impGraph;
	
	private static Graph impTypeGraph;
	
	private static GraTra gratra;

	private int msgGraTra;

	private static boolean layered = false, ruleSequence = false,
			priority = false;;

	private static boolean didTransformation = false;

	private static String fileName;

	private static String impFileName;

	private static String outputFileName;

	private static String error;

	private static boolean writeLogFile = false;

	public AGGBasicTest() {
	}

	public AGGBasicTest(String filename) {
		fileName = filename;
		System.out.println("File name:  " + fileName);
		/* load gragra */
				
		gragra = load(fileName);
				
//		makeRule(gragra, BaseFactory.theFactory().createGraGra());
		
		if (gragra != null) {
			gragra.getLevelOfTypeGraphCheck();
			
			// do transform
			
			//attr test: use user-own class
			if (setAttrOfRHS(gragra.getRule(0))) {
				setAttrCondition(gragra.getRule(0));
				((CondTuple) gragra.getRule(0).getAttrContext().getConditions()).showConditions();
				System.out.println(gragra.getRule(0).getAttrContext().getConditions().isDefinite());
				System.out.println(gragra.getRule(0).getAttrContext().getConditions().isValid());
				
			/* do transform */
			transform(gragra, this);
			}

			if (didTransformation) {
				// save gragra
				String out = "_out.ggx";
				save(gragra, out);
				System.out.println("Output file:  " + outputFileName);

				if (writeLogFile) {
					if (gratra instanceof DefaultGraTraImpl)
						System.out.println("Transformation protocol: "
								+ ((DefaultGraTraImpl) gratra)
										.getProtocolName());
					else if (gratra instanceof PriorityGraTraImpl)
						System.out.println("Transformation protocol: "
								+ ((PriorityGraTraImpl) gratra)
										.getProtocolName());
					else if (gratra instanceof LayeredGraTraImpl)
						System.out.println("Transformation protocol: "
								+ ((LayeredGraTraImpl) gratra)
										.getProtocolName());
					else if (gratra instanceof RuleSequencesGraTraImpl)
						System.out.println("Transformation protocol: "
								+ ((RuleSequencesGraTraImpl) gratra)
										.getProtocolName());
				}
			} else
				System.out.println("Grammar:  " + gragra.getName()
						+ "   could not perform any transformations!");
		} else
			System.out.println("Grammar:  " + filename + "   FAILED! "+error);
	}

	public AGGBasicTest(String filename, String impFilename) {
		fileName = filename;
		impFileName = impFilename;
		System.out.println("File name:  " + fileName);
		gragra = load(fileName);
		if (gragra != null) {
			int levelOfTGcheck = gragra.getLevelOfTypeGraphCheck();
			System.out.println("Import file name:  " + impFileName);
					
			impGraph = importGraph(impFilename);
			
			if (impGraGra != null) {
				if (impTypeGraph != null) {
					gragra.setLevelOfTypeGraphCheck(TypeSet.DISABLED);
					if (!gragra.importTypeGraph(impTypeGraph, true)) {
						System.out
								.println("Error:  Import Type Graph failed! Please check types of the import. ");
						return;
					}			
					System.out.println("Importing Type Graph successful.");
				}
					
				for (int ri = 0; ri<impGraGra.getListOfRules().size(); ri++) {
					Rule rulei = impGraGra.getListOfRules().get(ri);
					if (gragra.addImportRule(rulei, true)) {
						System.out.println("Importing rule successful: "+rulei.getName());
					}else {
						System.out.println("Importing rule failed: "+rulei.getName());
					}
					
//					Rule rule = BaseFactory.theFactory().cloneRule(rulei, gragra.getTypeSet());
//					if (rule != null) {
//						System.out.println("Importing rule successful: "+rulei.getName());
//						gragra.addRule(rule);
//					} else {
//						System.out.println("Importing rule failed: "+rulei.getName());
//					}
				}
				System.out.println("Imported rules: "+gragra.getListOfRules().size());
			}
			
//			if (impGraph != null) {
//				gragra.setLevelOfTypeGraphCheck(TypeSet.DISABLED);
//
//				if (!gragra.importGraph(impGraph)) {
//					System.out
//							.println("Error:  Import graph failed! Please check types of the import. ");
//					return;
//				} 
//				System.out.println("Importing graph successful.");
//			}
						
				
			if (gragra.getTypeSet().hasInheritance()) {
				if (levelOfTGcheck != TypeSet.DISABLED)
					gragra.setLevelOfTypeGraphCheck(levelOfTGcheck);
				else
					gragra.setLevelOfTypeGraphCheck(TypeSet.ENABLED_MAX);
			} else {
				gragra.setLevelOfTypeGraphCheck(levelOfTGcheck);
			}

			// save current grammar with import graph
			save(gragra, "Import_" + fileName);
			System.out.println("Import is written into:  " + "Import_"
						+ fileName);

			/* do transform */
			transform(gragra, this);
			
			if (didTransformation) {
				/* save gragra */
				String out = "_out.ggx";
				save(gragra, out);
				System.out.println("Output file:  " + outputFileName);

				if (gratra instanceof DefaultGraTraImpl)
					System.out.println("Transformation protocol: "
								+ ((DefaultGraTraImpl) gratra)
										.getProtocolName());
				else if (gratra instanceof PriorityGraTraImpl)
					System.out.println("Transformation protocol: "
								+ ((PriorityGraTraImpl) gratra)
										.getProtocolName());
				else if (gratra instanceof LayeredGraTraImpl)
					System.out.println("Transformation protocol: "
								+ ((LayeredGraTraImpl) gratra)
										.getProtocolName());
				else if (gratra instanceof RuleSequencesGraTraImpl)
					System.out.println("Transformation protocol: "
								+ ((RuleSequencesGraTraImpl) gratra)
										.getProtocolName());

			} else {
				System.out.println("Grammar:  " + gragra.getName()
							+ "   could not perform any transformations!");
			}
			
		} else
			System.out.println("Grammar:  " + filename + "   FAILED!");
	}

	public static void main(String[] args) {
		String vers = System.getProperty("java.version");
		if (vers.compareTo("1.4.2") < 0) {
			System.out.println("WARNING : Swing must be run with the "
					+ "1.4.2 version of the JVM.");
		}

		if (args.length == 0) {
			warning();
			return;
		}

		if (args.length == 1) {
			if ((args[0]).compareToIgnoreCase("-logfile") != 0) {
				new AGGBasicTest(args[0]);
				writeLogFile = false;
			}
		} else if (args.length == 2) {
			if (args[0].compareToIgnoreCase("-logfile") == 0) {
				writeLogFile = true;
				new AGGBasicTest(args[1]);
			} else {
				new AGGBasicTest(args[0], args[1]);
			}
		} else if (args.length == 3) {
			if (args[0].compareToIgnoreCase("-logfile") == 0) {
				writeLogFile = true;
				new AGGBasicTest(args[1], args[2]);
			} else
				warning();
		}
	}

	static void warning() {
		System.out
				.println("Usage unaliased: java -oss3m -Xmx1000m agg.xt_basis.AGGBasicTest [-logfile] grammar [import]");
		System.out.println("Usage aliased:");
		System.out.println("aggengine [-logfile] grammar [import]");
		System.out
				.println("(aggengine:     aliased to java -oss3m -Xmx1000m agg.xt_basis.AGGBasicTest)");
		System.out.println("");
		System.out.println("  -logfile \twrite transformation logfile");
		System.out
				.println("  grammar \tfull file name of  '.ggx'  file in XML format");
		System.out
				.println("  import	\tfull file name of a GXL file '.gxl' in XML format \n\t\tthat contains the graph to import.");
		System.out.println("also possible");
		System.out
				.println("  import	\tfull file name of an OMONDO XMI file '.ecore' in XML format \n\t\tthat contains the graph to import.");
		System.out.println("");
	}

	public static GraGra load(String fName) {
		// System.out.println(fileName.endsWith(".ggx"));
		if (fName.endsWith(".ggx")) {
			h = new XMLHelper();
			if (h.read_from_xml(fName)) {
	
				// create a gragra
				GraGra gra =  new GraGra(true);
				h.getTopObject(gra);
				gra.setFileName(fName);
				return gra;
			} 
			return null;
		} 
		return null;
	}

	public static Graph importGraph(String filename) {
		// System.out.println(filename.endsWith(".gxl"));
		if (filename.endsWith(".ggx")) {
			setItemsToImport(filename);
			return impGraph;
//			return importGraphGGX(filename);
		}
		
		else if (filename.endsWith(".gxl"))
			return importGraphGXL(filename);
		else if (filename.endsWith(".gtxl"))
			return importGraphGTXL(filename);
		else if (filename.endsWith(".ecore"))
			return importGraphOMONDO_XMI(filename);
		else {
			error = "Import failed!   < "
					+ filename
					+ " >  should be < .ggx > , < .gxl >  or  < .ecore >  file.";
			return null;
		}
	}

	@SuppressWarnings("unused")
	private static Graph importGraphGGX(String filename) {
		// System.out.println("importGraphGGX: "+filename);
		impGraGra = load(filename);
		if (impGraGra != null) {
			// save(impGra, "_outImportGrammar.ggx"); //test
			if (impGraGra.getTypeGraph() != null) {
				impTypeGraph = impGraGra.getTypeGraph();
			}
			return impGraGra.getGraph();
		} 
		return null;
	}

	private static void setItemsToImport(String ggxfilename) {
		System.out.println("importGGXfile: "+ggxfilename);
		impGraGra = load(ggxfilename);
		if (impGraGra != null) {			
			if (impGraGra.getTypeGraph() != null) {
				impTypeGraph = impGraGra.getTypeGraph();
			}
			impGraph = impGraGra.getGraph();
		}
	}
	
	// not implemented now
	private static Graph importGraphGTXL(String filename) {
		return null;
	}

	private static Graph importGraphGXL(String filename) {
		String fd = ".";
		String fn = filename;
		String fnOut = "";
		File gxldtd = null;
		File gtsdtd = null;
		File source = null;
		File layout = null;
		error = "";

		File f = new File(fn);
		if (f.exists()) {
			if (f.isFile())
				fd = f.getParent();
		}
		if (fd != null)
			fd = fd + File.separator;
		else
			fd = "." + File.separator;
		// System.out.println("dir: "+fd);
		// System.out.println("file: "+fn);

		/*
		 * if(XMLHelper.hasGermanSpecialCh(fn)){ System.out.println("File name:
		 * "+fn); System.out.println("\nRead file name exception occurred! "
		 * +"\nMaybe the German umlaut like ä, ö, ü or ß were used. " +"\nPlease
		 * replace it by ae, oe, ue or ss " +"\nand try again."); return null; }
		 */

		ConverterXML converter = new ConverterXML();
		fnOut = fn.substring(0, fn.length() - 4) + "_gxl.ggx";
		source = converter.copyFile(fd, "gxl2ggx.xsl");
		gxldtd = converter.copyFile(fd, "gxl.dtd");
		gtsdtd = converter.copyFile(fd, "gts.dtd");
		layout = converter.copyFile(fd, "agglayout.dtd");
		if (source == null) {
			error = "Import failed! File   < gxl2ggx.xsl >  is not found.";
			return null;
		} else if (gxldtd == null) {
			error = "Import failed! File   < gxl.dtd >  is not found.";
			return null;
		} else if (gtsdtd == null) {
			error = "Import failed! File   < gts.dtd >  is not found.";
			return null;
		} else if (layout == null) {
			error = "Import failed! File   < agglayout.dtd >  is not found.";
			return null;
		}

		String in = fn;
		String out = fnOut;
		GraGra impGra = null;
		if (converter.gxl2ggx(in, out, fd + "gxl2ggx.xsl")) {
			if (out.endsWith(".ggx")) {
				h = new XMLHelper();
				if (h.read_from_xml(out))
					impGra = (GraGra) h.getTopObject(
							BaseFactory.theFactory().createGraGra());

				if (impGra != null)
					return impGra.getGraph();
			}
		}
		error = "Import failed! Please check format of the  GXL  file.";
		return null;
	}

	private static Graph importGraphOMONDO_XMI(String filename) {
		String fd = ".";
		String fn = filename;
		String fnOut = "";
		File gxldtd = null;
		File gtsdtd = null;
		File source = null;
		File layout = null;
		File omondo = null;
		error = "";

		File f = new File(fn);
		if (f.exists()) {
			if (f.isFile())
				fd = f.getParent();
		}
		if (fd != null)
			fd = fd + File.separator;
		else
			fd = "." + File.separator;
		// System.out.println("dir: "+fd);
		// System.out.println("file: "+fn);

		ConverterXML converter = new ConverterXML();

		fnOut = fn.substring(0, fn.length() - 6) + "_ecore.ggx";
		source = converter.copyFile(fd, "gxl2ggx.xsl");
		gxldtd = converter.copyFile(fd, "gxl.dtd");
		gtsdtd = converter.copyFile(fd, "gts.dtd");
		layout = converter.copyFile(fd, "agglayout.dtd");
		omondo = converter.copyFile(fd, "omondoxmi2gxl.xsl");

		if (source == null) {
			error = "Import failed! File   < gxl2ggx.xsl >  is not found.";
			return null;
		} else if (gxldtd == null) {
			error = "Import failed! File   < gxl.dtd >  is not found.";
			return null;
		} else if (gtsdtd == null) {
			error = "Import failed! File   < gts.dtd >  is not found.";
			return null;
		} else if (layout == null) {
			error = "Import failed! File   < agglayout.dtd >  is not found.";
			return null;
		} else if (omondo == null) {
			error = "Import failed! File   < omondoxmi2gxl.xsl >  is not found.";
			return null;
		}

		String in = fn;
		String out = fnOut;
		GraGra impGra = null;
		if (converter.omondoxmi2ggx(in, out, fd + "omondoxmi2gxl.xsl", fd
				+ "gxl2ggx.xsl")) {
			if (out.endsWith(".ggx")) {
				h = new XMLHelper();
				if (h.read_from_xml(out))
					impGra = (GraGra) h.getTopObject(
							BaseFactory.theFactory().createGraGra());

				if (impGra != null) {
					return impGra.getGraph();
				}
			}
		}
		error = "Import failed! Please check format of the  GXL  file.";
		return null;
	}

	public static void transform(GraGra grammar, GraTraEventListener l) {
		if (grammar == null)
			return;
		/*
		 * test: there is a one way to set transformation options Vector gto =
		 * new Vector(); gto.add("layered"); gto.add("CSP");
		 * gto.add("injective"); gto.add("dangling");
		 * gragra.setGraTraOptions(gto);
		 */

		// create trafo
		// System.out.println(gragra.getGraTraOptions().toString());
		if (grammar.getGraTraOptions().contains("priority")) {
			gratra = new PriorityGraTraImpl();
			priority = true;
			System.out.println("Transformation by rule priority ...");
		} else if (grammar.getGraTraOptions().contains("layered")) {
			gratra = new LayeredGraTraImpl();
			layered = true;
			System.out.println("Layered transformation ...");
		} else if (grammar.getGraTraOptions().contains("ruleSequence")) {
			gratra = new RuleSequencesGraTraImpl();
			ruleSequence = true;
			System.out.println("Transformation by rule sequences ...");
		} else {
			gratra = new DefaultGraTraImpl();
			System.out.println("Transformation  non-deterministically ...");
		}

		gratra.addGraTraListener(l);
		gratra.setGraGra(grammar);
		gratra.setHostGraph(grammar.getGraph());
		gratra.enableWriteLogFile(writeLogFile);

		MorphCompletionStrategy strategy = CompletionStrategySelector
				.getDefault();
		// strategy = new Completion_NAC(new Completion_InjCSP());

		if (grammar.getGraTraOptions().isEmpty()) {
			grammar.setGraTraOptions(strategy);
			gratra.setCompletionStrategy(strategy);
		} else {
			if (grammar.getGraTraOptions().contains("showGraphAfterStep"))
				grammar.getGraTraOptions().remove("showGraphAfterStep");
			gratra.setGraTraOptions(grammar.getGraTraOptions());
			System.out.println("Options:  " + grammar.getGraTraOptions());
			System.out.println();
		}

		grammar.destroyAllMatches();

		if (priority)
			((PriorityGraTraImpl) gratra).transform();
		else if (layered)
			((LayeredGraTraImpl) gratra).transform();
		else if (ruleSequence)
			((RuleSequencesGraTraImpl) gratra).transform();
		else
			((DefaultGraTraImpl) gratra).transform();
	}

	public static void save(GraGra gra, String outFileName) {
		// System.out.println("Output into: "+outFileName);
		if (outFileName.equals(""))
			outputFileName = gra.getName() + "_out.ggx";
		else if (outFileName.equals("_out.ggx"))
			outputFileName = fileName.substring(0, fileName.length() - 4)
					+ "_out.ggx";
		else if (outFileName.indexOf(".ggx") == -1)
			outputFileName = outFileName.concat(".ggx");
		else if (outFileName.equals(fileName))
			outputFileName = fileName.substring(0, fileName.length() - 4)
					+ "_out.ggx";
		else {
			outputFileName = outFileName;
		}
		// System.out.println("save :: Output into: "+outputFileName);
		if (outputFileName.endsWith(".ggx")) {
			XMLHelper xmlh = new XMLHelper();
			xmlh.addTopObject(gra);
			xmlh.save_to_xml(outputFileName);
		}

	}

	/** Implements GraTraEventListener.graTraEventOccurred */
	public void graTraEventOccurred(GraTraEvent event) {
		// System.out.println("AGGBasicAppl.graTraEventOccurred
		// "+event.getMessage());
		
//		Match match = event.getMatch();
		
//		String ruleName = "Rule";
//		if (match != null)
//			ruleName = match.getRule().getName();

		this.msgGraTra = event.getMessage();
		if (this.msgGraTra == GraTraEvent.TRANSFORM_FINISHED) {
			gratra.stop();
			didTransformation = gratra.transformationDone();
			// System.out.println("GraTraEvent message : TRANSFORM_FINISHED");
		} else if ((this.msgGraTra == GraTraEvent.INPUT_PARAMETER_NOT_SET)) {
			System.out.println("GraTraEvent message : PARAMETER NOT SET!");
		}
		/*
		 * else if((msgGraTra == GraTraEvent.STEP_COMPLETED)) { //
		 * System.out.println("Rule : "+ruleName+" ==> STEP DONE" ); }
		 * 
		 * else if (msgGraTra == GraTraEvent.NO_COMPLETION) {
		 * //System.out.println("Rule : "+ ruleName+" ==> NO_COMPLETION"); }
		 * else if (msgGraTra == GraTraEvent.CANNOT_TRANSFORM){
		 * //System.out.println("Rule : "+ ruleName+" ==> CANNOT_TRANSFORM"); }
		 */
	}

	@SuppressWarnings("unused")
	private void createNode(final Graph g, final String typeName) {		
		Type type = g.getTypeSet().getTypeByName(typeName);
		System.out.println(type+"   "+typeName);
		try {
			Node n = g.createNode(type);
			n.createAttributeInstance();
			
			System.out.println(n);
			// set name attr
			/*
			ValueTuple vt = (ValueTuple) n.getAttribute();
			System.out.println("ValueTuple:: "+vt);
			
//			vt.setValueAt("YYYYY", "name");
			
//			vt.setExprAt("YYYYY", "name"); // !!! geht nicht !?
			
			String name = "name";
			ValueMember vm = vt.getValueMemberAt(name);
			System.out.println("ValueMember:: name: "+vm);
			if (vm != null) {
				vm.setExprAsObject("XXXXX");
			} 
			System.out.println("ValueMember:: name: "+vm);
			*/
		} catch (TypeException ex) {}					
	}
		
	@SuppressWarnings("unused")
	private void setParent(final TypeGraph tg,
			final String childTypeName, 
			final String parentTypeName) {
		if (tg == null || tg.isEmpty()) {
			return;
		}
		
		final Type childType = tg.getTypeSet().getTypeByName(childTypeName);
		final Node child = tg.getNodes(childType).get(0);
		
		final Type parentType = tg.getTypeSet().getTypeByName(parentTypeName);
		final Node parent = tg.getNodes(parentType).get(0);
		
		tg.getTypeSet().addInheritanceRelation(childType, parentType);
//		childType.setParent(parentType);
//		childType.addParent(parentType);
		System.out.println(child+"   Parents:  "+child.getType().getAllParents());
	}
	
	private boolean setAttrOfRHS(final Rule r) {
		Iterator<Node> nodes = r.getRight().getNodesCollection().iterator();
		while (nodes.hasNext()) {
			Node n = nodes.next();
			ValueTuple vt = (ValueTuple) n.getAttribute();
			for (int i=0; i<vt.getNumberOfEntries(); i++) {
				ValueMember vm = vt.getEntryAt(i);
				if (!vm.isSet()) {
					if (vm.getDeclaration().getTypeName().equals("Integer")) {
						try {
//						String valStr = "spec.impl.N.add(Integer.valueOf(1),Integer.valueOf(2))";
							String valStr = "$spec.impl.N$.add(Integer.valueOf(1),Integer.valueOf(2))";						
							vm.trySetExprAsText(valStr);											
							System.out.println(vm.getExprAsText());
						} catch (agg.attribute.handler.AttrHandlerException ex) {
							System.out.println("Attr setting failed!\n"+ex.getLocalizedMessage());
							return false;
						}
					} else if (vm.getDeclaration().getTypeName().equals("Boolean")) {
						try {
//							String valStr = "Boolean.valueOf(\"true\")";	
							String valStr = "N.getTrue()";
							System.out.println(valStr);
							vm.trySetExprAsText(valStr);											
							System.out.println("RHS attr value:  "+vm.getExprAsText());
						} catch (agg.attribute.handler.AttrHandlerException ex) {
							System.out.println("Attr setting failed!\n"+ex.getLocalizedMessage());
							return false;
						}
					}					
				}
			}
		}
		return true;
	}
	
	private void setAttrCondition(final Rule r) {
		CondTuple cond = (CondTuple) r.getAttrContext().getConditions();
		cond.addCondition("N.odd(Integer.valueOf(7)).booleanValue()");
		// attr parse leak : public static field of static class 
		cond.addCondition("$Boolean$.TRUE.booleanValue()"); 
		cond.addCondition("$N$.getTrue().booleanValue()"); 
		cond.addCondition("$Boolean$.valueOf(\"true\").booleanValue()"); 
		cond.addCondition("N.odd(Integer.valueOf(7)).equals(Boolean.TRUE)"); 
		cond.addCondition("N.odd(Integer.valueOf(7)).equals(N.getTrue())"); 
		cond.addCondition("N.odd(Integer.valueOf(7)).equals(Boolean.valueOf(\"true\"))");
		cond.addCondition("N.odd(Integer.valueOf(7)).booleanValue()&&N.odd(Integer.valueOf(5)).booleanValue()");
	}
	
	@SuppressWarnings("unused")
	private void setAttributeValue(final Graph g,
									final String attrName,
									final String attrType,
									final String valueStr) {
		if (g.isEmpty()) {
			return;
		}
		
		final Node n = g.getNodesSet().iterator().next();
		ValueTuple vt = (ValueTuple) n.getAttribute();
		
//		ValueMember vm = vt.getValueMemberAt(attrName);
//		if (vm.getDeclaration().getTypeName().equals(attrType)) {		
//			vm.setExprAsText(valueStr);
//			System.out.println("######## 1)   "+vm);
//		}
		
		String val = valueStr+"00";
		vt.setExprAt(val, attrName);
	}
	
	@SuppressWarnings("unused")
	private void refreshAttrType(TypeSet types) {
		Enumeration<Type> en = types.getTypes();
		while (en.hasMoreElements()) {
			Type t = en.nextElement();
			if (t.getAttrType() != null) {
				((DeclTuple) t.getAttrType()).refreshParents();
			}
		}		
	}
	
	@SuppressWarnings("unused")
	private void makeRule(final GraGra sourceGra, final GraGra targetGra) {	
		List<Rule> srcRules = sourceGra.getListOfRules();
		for (int i=0; i<srcRules.size(); i++) {
			Rule srcRule = srcRules.get(i);
			
			// import the type graph
			if (sourceGra.getTypeGraph() != null) {
				targetGra.createTypeGraph();
				targetGra.importTypeGraph(sourceGra.getTypeGraph(), true);
				targetGra.setLevelOfTypeGraphCheck(TypeSet.DISABLED);
			} else {
				// or adapt the types
				targetGra.getTypeSet().adaptTypes(sourceGra.getTypeSet(), false);
			}
			
			// one way is to import a rule
//			if (targetGra.addImportRule(srcRule, true)) {				

//			}
			
			// other way is to add a copy of graph
			Rule tarRule = targetGra.createRule();
			tarRule.setName(srcRule.getName());
			if (tarRule.getLeft().addCopyOfGraph(srcRule.getLeft(), true)) {
				if (tarRule.getRight().addCopyOfGraph(srcRule.getRight(), true)) {
				}
			}
			
//			targetGra.save("TargetGraGra.ggx");
		}
	}
}
