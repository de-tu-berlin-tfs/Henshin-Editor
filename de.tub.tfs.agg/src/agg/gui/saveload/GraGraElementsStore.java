package agg.gui.saveload;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.Enumeration;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.Icon;
import javax.swing.JButton;

import agg.xt_basis.Graph;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.TypeGraph;
import agg.cons.AtomConstraint;
import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdNestedApplCond;
import agg.editor.impl.EdRule;
import agg.editor.impl.EdNAC;
import agg.editor.impl.EdPAC;
import agg.editor.impl.EdAtomic;
import agg.editor.impl.EdConstraint;
import agg.editor.impl.EdRuleScheme;
import agg.gui.IconResource;
import agg.gui.icons.NestedACIcon;
import agg.gui.icons.NewConclusionIcon;
import agg.gui.treeview.GraGraTreeView;

public class GraGraElementsStore implements MouseListener {

	// key: EdGraGra,
	// value: Hashtable with
	// key: "TG"|"GRAPH"|"RULE"|"RULESCHEME"|"KERNELRULE"|"MULTIRULE"|"ATOMIC"|"FORMULA"
	// value: Vector with elements:
	// EdGraph for "TG", EdGraph for "GRAPH", EdRule for "RULE",
	// EdAtomic for "ATOMIC", EdConstraint for "FORMULA"
	protected final Hashtable<EdGraGra, Hashtable<String, Vector<Object>>> storeGraGra;

	// key: EdRuleScheme,
	// value: Vector with elements of EdRule 
	protected final Hashtable<EdRuleScheme, Vector<Object>> storeRuleScheme;
	
	// key: EdRule,
	// value: Vector with 3 Vectors:
	// (0): Vector with elements of EdNAC
	// (1): Vector with elements of EdPAC
	// (2): Vector with elements of EdNestedApplCond
	protected final Hashtable<EdRule, Vector<Vector<Object>>> storeRule;

	// key: EdNestedApplCond,
	// value: Vector with elements of EdNestedApplCond
	protected final Hashtable<EdNestedApplCond, Vector<Object>> storeNestedAC;
	
	// key: EdAtomic,
	// value: Vector with elements of EdAtomic
	protected final Hashtable<EdAtomic, Vector<Object>> storeAtomConstraint;

	protected JPanel palette;

	private JScrollPane scrollPane;

	protected final Vector<JPanel> paletteElems;
	private JPanel panel;
	
	private final Hashtable<Object, JPanel> obj2panel;

	private final Hashtable<JLabel, Object> buttons;
	private JLabel label;
	
	private Object current;

	private boolean currentValid = false;

	protected JDialog d;

	private static final Color 
	SelectedBackgroundColor = new Color(153, 153, 255);

//	private String dot = " . ";

	int x = -1, y = -1;

	int lWeidth;

	private JButton trash;

	private GraGraTreeView treeView;

	public GraGraElementsStore(GraGraTreeView tree) {
		super();
		this.treeView = tree;
		this.storeGraGra = new Hashtable<EdGraGra, Hashtable<String, Vector<Object>>>(
				5);
		this.storeRuleScheme = new Hashtable<EdRuleScheme, Vector<Object>>(5,2);
		this.storeRule = new Hashtable<EdRule, Vector<Vector<Object>>>(5,2);
		this.storeNestedAC = new Hashtable<EdNestedApplCond, Vector<Object>>(5,2);
		this.storeAtomConstraint = new Hashtable<EdAtomic, Vector<Object>>(5,2);
		this.paletteElems = new Vector<JPanel>(5);
		this.buttons = new Hashtable<JLabel, Object>(5);
		this.obj2panel = new Hashtable<Object, JPanel>(5);
	}

	public void setTrash(JButton aTrash) {
		this.trash = aTrash;
	}

	public boolean isEmpty() {
		if (this.paletteElems.isEmpty())
			return true;
		
		return false;
	}

	public void storeGraph(EdGraGra parent, EdGraph g) {
		// System.out.println("store.Graph...");
		Hashtable<String, Vector<Object>> ht = this.storeGraGra.get(parent);
		if (ht == null)
			ht = new Hashtable<String, Vector<Object>>(5);
		Vector<Object> v = ht.get("GRAPH");
		if (v == null)
			v = new Vector<Object>(1);
		v.add(g);
		ht.put("GRAPH", v);
		this.storeGraGra.put(parent, ht);
		String name = makeName(g.getBasisGraph().getName(), g.getGraGra()
				.getName(), "");
		int w = getNameLength(name);
		JLabel b = createButton(name, IconResource.getIconFromURL(IconResource
				.getURLGraph()));
		this.buttons.put(b, g);
		this.obj2panel.put(g, this.paletteElems.lastElement());
		if (!this.trash.isEnabled())
			this.trash.setEnabled(true);
		if (this.lWeidth < w)
			this.lWeidth = w;
		refreshStorePalette();
	}

	public void storeTypeGraph(EdGraGra parent, EdGraph g) {
		Hashtable<String, Vector<Object>> ht = this.storeGraGra.get(parent);
		if (ht == null)
			ht = new Hashtable<String, Vector<Object>>(5);
		Vector<Object> v = ht.get("TG");
		if (v == null)
			v = new Vector<Object>(1);
		v.add(g);
		ht.put("TG", v);
		this.storeGraGra.put(parent, ht);
		String name = makeName(g.getBasisGraph().getName(), g.getGraGra()
				.getName(), "");
		int w = getNameLength(name);
		JLabel b = createButton(name, IconResource.getIconFromURL(IconResource
				.getURLTypeGraph()));
		this.buttons.put(b, g);
		this.obj2panel.put(g, this.paletteElems.lastElement());
		if (!this.trash.isEnabled())
			this.trash.setEnabled(true);
		if (this.lWeidth < w)
			this.lWeidth = w;
		refreshStorePalette();
	}

	public void storeRuleScheme(EdGraGra parent, EdRuleScheme rs) {
		Hashtable<String, Vector<Object>> ht = this.storeGraGra.get(parent);
		if (ht == null)
			ht = new Hashtable<String, Vector<Object>>(5);
		Vector<Object> v = ht.get("RULESCHEME");
		if (v == null)
			v = new Vector<Object>(1);
		v.add(rs);
		ht.put("RULESCHEME", v);
		this.storeGraGra.put(parent, ht);
		String name = makeName(rs.getBasisRuleScheme().getSchemeName(), rs.getGraGra()
				.getName(), "");
		int w = getNameLength(name);
		JLabel b = createButton(name, IconResource.getIconFromURL(IconResource
				.getURLRuleScheme()));
		this.buttons.put(b, rs);
		this.obj2panel.put(rs, this.paletteElems.lastElement());
		if (!this.trash.isEnabled())
			this.trash.setEnabled(true);
		if (this.lWeidth < w)
			this.lWeidth = w;
		refreshStorePalette();
	}
	
	public void storeMultiRule(EdRuleScheme parent, EdRule mr) {
		Vector<Object> v = this.storeRuleScheme.get(parent);
		if (v == null)
			v = new Vector<Object>(5);
		v.add(mr);		
		this.storeRuleScheme.put(parent, v);
		String name = makeName(mr.getBasisRule().getName(), 
				parent.getBasisRuleScheme().getSchemeName(), "");
		int w = getNameLength(name);
		JLabel b = createButton(name, IconResource.getIconFromURL(IconResource
				.getURLRule()));
		this.buttons.put(b, mr);
		this.obj2panel.put(mr, this.paletteElems.lastElement());
		if (!this.trash.isEnabled())
			this.trash.setEnabled(true);
		if (this.lWeidth < w)
			this.lWeidth = w;
		refreshStorePalette();
	}

	public void storeRule(EdGraGra parent, EdRule r) {
		Hashtable<String, Vector<Object>> ht = this.storeGraGra.get(parent);
		if (ht == null)
			ht = new Hashtable<String, Vector<Object>>(5);
		Vector<Object> v = ht.get("RULE");
		if (v == null)
			v = new Vector<Object>(1);
		v.add(r);
		ht.put("RULE", v);
		this.storeGraGra.put(parent, ht);
		String name = makeName(r.getBasisRule().getName(), r.getGraGra()
				.getName(), "");
		int w = getNameLength(name);
		JLabel b = createButton(name, IconResource.getIconFromURL(IconResource
				.getURLRule()));
		this.buttons.put(b, r);
		this.obj2panel.put(r, this.paletteElems.lastElement());
		if (!this.trash.isEnabled())
			this.trash.setEnabled(true);
		if (this.lWeidth < w)
			this.lWeidth = w;
		refreshStorePalette();
	}

	public void storeAtomConstraint(EdGraGra parent, EdAtomic c) {
		Hashtable<String, Vector<Object>> ht = this.storeGraGra.get(parent);
		if (ht == null)
			ht = new Hashtable<String, Vector<Object>>(5);
		Vector<Object> v = ht.get("ATOMIC");
		if (v == null)
			v = new Vector<Object>(1);
		v.add(c);
		ht.put("ATOMIC", v);
		this.storeGraGra.put(parent, ht);
		String name = makeName(c.getBasisAtomic().getAtomicName(), c
				.getGraGra().getName(), "");
		int w = getNameLength(name);
		JLabel b = createButton(name, IconResource.getIconFromURL(IconResource
				.getURLAtomic()));
		this.buttons.put(b, c);
		this.obj2panel.put(c, this.paletteElems.lastElement());
		if (!this.trash.isEnabled())
			this.trash.setEnabled(true);
		if (this.lWeidth < w)
			this.lWeidth = w;
		refreshStorePalette();
	}

	public void storeConstraint(EdGraGra parent, EdConstraint c) {
		Hashtable<String, Vector<Object>> ht = this.storeGraGra.get(parent);
		if (ht == null)
			ht = new Hashtable<String, Vector<Object>>(5);
		Vector<Object> v = ht.get("FORMULA");
		if (v == null)
			v = new Vector<Object>(1);
		v.add(c);
		ht.put("FORMULA", v);
		this.storeGraGra.put(parent, ht);
		String name = makeName(c.getBasisConstraint().getName(), c.getGraGra()
				.getName(), "");
		int w = getNameLength(name);
		JLabel b = createButton(name, IconResource.getIconFromURL(IconResource
				.getURLConstraint()));
		this.buttons.put(b, c);
		this.obj2panel.put(c, this.paletteElems.lastElement());
		if (!this.trash.isEnabled())
			this.trash.setEnabled(true);
		if (this.lWeidth < w)
			this.lWeidth = w;
		refreshStorePalette();
	}

	public void storeNAC(EdRule parent, EdNAC nac) {
		Vector<Vector<Object>> v = this.storeRule.get(parent);
		if (v == null) {
			v = new Vector<Vector<Object>>(3);
			v.add(new Vector<Object>(2,2)); // contains NACs
			v.add(new Vector<Object>(2,2)); // contains PACs
			v.add(new Vector<Object>(2,2)); // contains nested ACs
		}
		v.get(0).add(nac);
		this.storeRule.put(parent, v);
		String name = makeName(nac.getName(), nac.getRule().getBasisRule()
				.getName(), nac.getRule().getGraGra().getName());
		int w = getNameLength(name);
		JLabel b = createButton(name, IconResource.getIconFromURL(IconResource
				.getURLNAC()));
		this.buttons.put(b, nac);
		this.obj2panel.put(nac, this.paletteElems.lastElement());
		if (!this.trash.isEnabled())
			this.trash.setEnabled(true);
		if (this.lWeidth < w)
			this.lWeidth = w;
		refreshStorePalette();
	}

	public void storePAC(EdRule parent, EdPAC pac) {
		Vector<Vector<Object>> v = this.storeRule.get(parent);
		if (v == null) {
			v = new Vector<Vector<Object>>(3);
			v.add(new Vector<Object>(2,2)); // contains NACs
			v.add(new Vector<Object>(2,2)); // contains PACs
			v.add(new Vector<Object>(2,2)); // contains nested ACs
		}
		v.get(1).add(pac);
		this.storeRule.put(parent, v);
		String name = makeName(pac.getName(), pac.getRule().getBasisRule()
				.getName(), pac.getRule().getGraGra().getName());
		int w = getNameLength(name);
		JLabel b = createButton(name, IconResource.getIconFromURL(IconResource
				.getURLPAC()));
		this.buttons.put(b, pac);
		this.obj2panel.put(pac, this.paletteElems.lastElement());
		if (!this.trash.isEnabled())
			this.trash.setEnabled(true);
		if (this.lWeidth < w)
			this.lWeidth = w;
		refreshStorePalette();
	}

	public void storeNestedAC(EdRule parent, EdNestedApplCond ac) {
		// NOTE: formula get lost
		Vector<Vector<Object>> v = this.storeRule.get(parent);
		if (v == null) {
			v = new Vector<Vector<Object>>(3);
			v.add(new Vector<Object>(2,2)); // contains NACs
			v.add(new Vector<Object>(2,2)); // contains PACs
			v.add(new Vector<Object>(2,2)); // contains nested ACs
		}
		v.get(2).add(ac);
		this.storeRule.put(parent, v);
		String name = makeName(ac.getName(), ac.getRule().getBasisRule()
				.getName(), ac.getRule().getGraGra().getName());
		int w = getNameLength(name);
		final NestedACIcon icon = new NestedACIcon(Color.blue);
		JLabel b = createButton(name, icon);
		this.buttons.put(b, ac);
		this.obj2panel.put(ac, this.paletteElems.lastElement());
		if (!this.trash.isEnabled())
			this.trash.setEnabled(true);
		if (this.lWeidth < w)
			this.lWeidth = w;
		refreshStorePalette();
	}
	
	public void storeNestedAC(EdNestedApplCond parent, EdNestedApplCond ac) {
		// NOTE: formula get lost
		Vector<Object> v = this.storeNestedAC.get(parent);
		if (v == null) {
			v = new Vector<Object>(2,2);
		}
		v.add(ac);
		this.storeNestedAC.put(parent, v);
		List<String> parNames = new Vector<String>(3); 
		parNames.add(parent.getName());
		parNames.add(ac.getRule().getBasisRule().getName());
		parNames.add(ac.getRule().getGraGra().getName());
		String name = makeName(ac.getName(), parNames);
		int w = getNameLength(name);
		final NestedACIcon icon = new NestedACIcon(Color.blue);
		JLabel b = createButton(name, icon);
		this.buttons.put(b, ac);
		this.obj2panel.put(ac, this.paletteElems.lastElement());
		if (!this.trash.isEnabled())
			this.trash.setEnabled(true);
		if (this.lWeidth < w)
			this.lWeidth = w;
		refreshStorePalette();
	}
	
	public void storeAtomConclusion(EdAtomic parent, EdAtomic c) {
		Vector<Object> v = this.storeAtomConstraint.get(parent);
		if (v == null)
			v = new Vector<Object>(5);
		v.add(c);
		this.storeAtomConstraint.put(parent, v);
		String name = makeName(c.getBasisAtomic().getName(), c.getBasisAtomic()
				.getAtomicName(), c.getGraGra().getName());
		int w = getNameLength(name);
		NewConclusionIcon icon = new NewConclusionIcon(Color.blue);
		icon.setEnabled(true);
		JLabel b = createButton(name, icon);
		this.buttons.put(b, c);
		this.obj2panel.put(c, this.paletteElems.lastElement());
		if (!this.trash.isEnabled())
			this.trash.setEnabled(true);
		if (this.lWeidth < w)
			this.lWeidth = w;
		refreshStorePalette();
	}

	public EdGraph getTypeGraph(EdGraGra parent) {
		EdGraph g = null;
		Hashtable<String, Vector<Object>> ht = this.storeGraGra.get(parent);
		if (ht != null) {
			Vector<Object> v = ht.get("TG");
			if (v != null && !v.isEmpty()) {
				g = (EdGraph) v.lastElement();
				v.remove(g);
				if (v.isEmpty())
					ht.remove("TG");
				// System.out.println("Type graph undo");
			}
			if (ht.isEmpty())
				this.storeGraGra.remove(parent);
			updateTrash();
		}
		return g;
	}

	public EdGraph getGraph(EdGraGra parent) {
		// System.out.println("store.getGraph...");
		EdGraph g = null;
		Hashtable<String, Vector<Object>> ht = this.storeGraGra.get(parent);
		if (ht != null) {
			Vector<Object> v = ht.get("GRAPH");
			if (v != null && !v.isEmpty()) {
				g = (EdGraph) v.lastElement();
				// System.out.println(g.hashCode());
				v.remove(g);
				if (v.isEmpty())
					ht.remove("GRAPH");
				// System.out.println(ht.get("GRAPH"));
			}
			if (ht.isEmpty())
				this.storeGraGra.remove(parent);
			updateTrash();
		}
		// System.out.println(this.storeGraGra.get(parent));
		return g;
	}

	public EdRule getRule(EdGraGra parent) {
		EdRule r = null;
		Hashtable<String, Vector<Object>> ht = this.storeGraGra.get(parent);
		if (ht != null) {
			Vector<Object> v = ht.get("RULE");
			if (v != null && !v.isEmpty()) {
				r = (EdRule) v.lastElement();
				v.remove(r);
				if (v.isEmpty())
					ht.remove("RULE");
			}
			if (ht.isEmpty())
				this.storeGraGra.remove(parent);
			updateTrash();
		}
		return r;
	}

	public EdRuleScheme getRuleScheme(EdGraGra parent) {
		EdRuleScheme r = null;
		Hashtable<String, Vector<Object>> ht = this.storeGraGra.get(parent);
		if (ht != null) {
			Vector<Object> v = ht.get("RULESCHEME");
			if (v != null && !v.isEmpty()) {
				r = (EdRuleScheme) v.lastElement();
				v.remove(r);
				if (v.isEmpty())
					ht.remove("RULESCHEME");
			}
			if (ht.isEmpty())
				this.storeGraGra.remove(parent);
			updateTrash();
		}
		return r;
	}
	
	public EdNAC getNAC(EdRule parent) {
		EdNAC n = null;
		Vector<Vector<Object>> v = this.storeRule.get(parent);
		if (v != null && !v.isEmpty()) {
			Vector<Object> vNAC = v.get(0);
			if (!vNAC.isEmpty()) {
				n = (EdNAC) vNAC.lastElement();
				vNAC.remove(n);
			}
			if (v.get(0).isEmpty() && v.get(1).isEmpty() && v.get(2).isEmpty())
				this.storeRule.remove(parent);
			updateTrash();
		}
		return n;
	}

	public EdPAC getPAC(EdRule parent) {
		EdPAC p = null;
		Vector<Vector<Object>> v = this.storeRule.get(parent);
		if (v != null && !v.isEmpty()) {
			Vector<Object> vPAC = v.get(1);
			if (!vPAC.isEmpty()) {
				p = (EdPAC) vPAC.lastElement();
				vPAC.remove(p);
			}
			if (v.get(0).isEmpty() && v.get(1).isEmpty() && v.get(2).isEmpty())
				this.storeRule.remove(parent);
			updateTrash();
		}
		return p;
	}

	public EdNestedApplCond getNestedAC(EdRule parent) {
		EdNestedApplCond p = null;
		Vector<Vector<Object>> v = this.storeRule.get(parent);
		if (v != null && !v.isEmpty()) {
			Vector<Object> nestedACs = v.lastElement();
			if (!nestedACs.isEmpty()) {
				p = (EdNestedApplCond) nestedACs.lastElement();
				nestedACs.remove(p);
			}
			if (v.get(0).isEmpty() && v.get(1).isEmpty() && v.get(2).isEmpty())
				this.storeRule.remove(parent);
			updateTrash();
		}
		return p;
	}
	
	public EdNestedApplCond getNestedAC(EdNestedApplCond parent) {
		EdNestedApplCond p = null;
		Vector<Object> v = this.storeNestedAC.get(parent);
		if (v != null && !v.isEmpty()) {
			p = (EdNestedApplCond) v.lastElement();
			v.remove(v.size()-1);
			if (v.isEmpty())
				this.storeNestedAC.remove(parent);
			updateTrash();
		}
		return p;
	}
	
	public EdAtomic getAtomConstraint(EdGraGra parent) {
		EdAtomic a = null;
		Hashtable<String, Vector<Object>> ht = this.storeGraGra.get(parent);
		if (ht != null) {
			Vector<Object> v = ht.get("ATOMIC");
			if (v != null && !v.isEmpty()) {
				a = (EdAtomic) v.lastElement();
				v.remove(a);
				if (v.isEmpty())
					ht.remove("ATOMIC");
			}
			if (ht.isEmpty())
				this.storeGraGra.remove(parent);
			updateTrash();
		}
		return a;
	}

	public EdAtomic getAtomConclusion(EdAtomic parent) {
		EdAtomic a = null;
		Vector<Object> v = this.storeAtomConstraint.get(parent);
		if (v != null && !v.isEmpty()) {
			a = (EdAtomic) v.lastElement();
			v.remove(a);
			if (v.isEmpty())
				this.storeAtomConstraint.remove(parent);
			updateTrash();
		}
		return a;
	}

	public EdConstraint getConstraint(EdGraGra parent) {
		EdConstraint c = null;
		Hashtable<String, Vector<Object>> ht = this.storeGraGra.get(parent);
		if (ht != null) {
			Vector<Object> v = ht.get("FORMULA");
			if (v != null && !v.isEmpty()) {
				c = (EdConstraint) v.lastElement();
				v.remove(c);
				if (v.isEmpty())
					ht.remove("FORMULA");
			}
			if (ht.isEmpty())
				this.storeGraGra.remove(parent);
			updateTrash();
		}
		return c;
	}

	public void removeGraGra(EdGraGra gra) {
		Enumeration<?> e = this.storeRule.keys();
		while (e.hasMoreElements()) {
			EdRule r = (EdRule) e.nextElement();
			if (r.getGraGra() == gra) {
				Vector<Vector<Object>> v = this.storeRule.get(r);
				Vector<Object> nacs =  v.firstElement();
				for (int i = 0; i < nacs.size(); i++) {
					EdNAC nac = (EdNAC) nacs.get(i);
					removeFromTrashPalette(nac);
					OrdinaryMorphism nacMorph = nac.getMorphism();
					nac.dispose();
					nacMorph.dispose(false, true);					
				}
				Vector<Object> pacs = v.lastElement();
				for (int i = 0; i < pacs.size(); i++) {
					EdPAC pac = (EdPAC) pacs.get(i);
					removeFromTrashPalette(pac);
					OrdinaryMorphism pacMorph = pac.getMorphism();
					pac.dispose();
					pacMorph.dispose(false, true);
				}
				v.clear();
				this.storeRule.remove(r);
				e = this.storeRule.keys();
			}
		}
		
		e = this.storeAtomConstraint.keys();
		while (e.hasMoreElements()) {
			EdAtomic c = (EdAtomic) e.nextElement();
			if (c.getGraGra() == gra) {
				Vector<Object> v = this.storeAtomConstraint.get(c);
				for (int i = 0; i < v.size(); i++) {
					EdAtomic a = (EdAtomic) v.get(i);
					removeFromTrashPalette(a);
					AtomConstraint ba = a.getBasisAtomic();
					a.dispose();
					if (ba != null)
						ba.dispose();
				}
				v.clear();
				this.storeAtomConstraint.remove(c);
				e = this.storeAtomConstraint.keys();
			}
		}
		
		Hashtable<String, Vector<Object>> ht = this.storeGraGra.get(gra);
		if (ht != null) {			
			Vector<Object> v = ht.get("GRAPH");
			if (v != null) {
				for (int i = 0; i < v.size(); i++) {
					EdGraph g = (EdGraph) v.get(i);
					removeFromTrashPalette(g);
					Graph bg = g.getBasisGraph();
					g.dispose();
					if (bg != null)
						bg.dispose();
				}
				v.clear();
			}
			v = ht.get("RULE");
			if (v != null) {
				for (int i = 0; i < v.size(); i++) {
					EdRule r = (EdRule) v.get(i);
					removeFromTrashPalette(r);
					Rule br = r.getBasisRule();
					r.dispose();
					if (br != null)
						br.dispose();
				}
				v.clear();
			}
			v = ht.get("ATOMIC");
			if (v != null) {
				for (int i = 0; i < v.size(); i++) {
					EdAtomic a = (EdAtomic) v.get(i);
					removeFromTrashPalette(a);
					AtomConstraint ba = a.getBasisAtomic();
					a.dispose();
					if (ba != null)
						ba.dispose();
				}
				v.clear();
			}
			v = ht.get("FORMULA");
			if (v != null) {
				for (int i = 0; i < v.size(); i++) {
					EdConstraint c = (EdConstraint) v.get(i);
					removeFromTrashPalette(c);
				}
				v.clear();
			}
			v = ht.get("TG");
			if (v != null) {
				for (int i = 0; i < v.size(); i++) {
					EdGraph tg = (EdGraph) v.get(i);
					removeFromTrashPalette(tg);
					Graph btg = tg.getBasisGraph();
					tg.dispose();
					if (btg != null)
						((TypeGraph) btg).dispose();
				}
				v.clear();
			}
			ht.clear();
		}
		this.storeGraGra.remove(gra);
		this.current = null;
		this.currentValid = false;
		updateTrash();
	}

	void updateTrash() {
		if (this.paletteElems.isEmpty())
			this.trash.setEnabled(false);
		else
			this.trash.setEnabled(true);
	}

	private String makeName(String elem, String parent1, String parent2) {
		String res = "";
		if (elem.length() > 0) {
			if (parent1.length() > 0)
				res = elem + " [" + parent1 + "]" + "  ";
			if (parent2.length() > 0)
				res = elem + " [" + parent1 + "]" + " [" + parent2 + "]" + "  ";
		}
		// System.out.println("Name length: "+res.length());
		return res;
	}

	private String makeName(String elem, List<String> parents) {
		String res = "";
		if (elem.length() > 0) {
			for (int i=0; i<parents.size(); i++) {
				String par = parents.get(i);
				res = elem + " [" + par + "]" + "  ";
			}
		}
		// System.out.println("Name length: "+res.length());
		return res;
	}
	
	private int getNameLength(String name) {
		if (name.length() < 40)
			return name.length() * 8 + 20;
		
		return name.length() * 8;
	}

	private void propagateSelection(Object obj) {
		if (obj instanceof EdNAC) {
			getNAC(((EdNAC) obj).getRule());
		} 
		else if (obj instanceof EdPAC) {
			getPAC(((EdPAC) obj).getRule());
		}
		else if (obj instanceof EdNestedApplCond) {
			if (((EdNestedApplCond) obj).getParent() == null)
				getNestedAC(((EdNestedApplCond) obj).getRule());
			else
				getNestedAC(((EdNestedApplCond) obj).getParent());
		}
		else if (obj instanceof EdGraph) {
			if (((EdGraph) obj).isTypeGraph()) {
				getTypeGraph(((EdGraph) obj).getGraGra());
			} else {
				getGraph(((EdGraph) obj).getGraGra());
			}
		} else if (obj instanceof EdAtomic) {
			if (((EdAtomic) obj).getParent() == (EdAtomic) obj) {
				getAtomConstraint(((EdAtomic) obj).getGraGra());
			} else {
				getAtomConclusion(((EdAtomic) obj).getParent());
			}
		} else if (obj instanceof EdRuleScheme) {
			getRuleScheme(((EdRuleScheme) obj).getGraGra());
		} 
		else if (obj instanceof EdRule) {
			getRule(((EdRule) obj).getGraGra());
		}
		else if (obj instanceof EdConstraint) {
			getConstraint(((EdConstraint) obj).getGraGra());
		}
	}

	/** ******** palette && dialog ***************** */

	private JLabel createButton(String name, Icon icon) {
		JLabel l = new JLabel(name);
		l.setIcon(icon);
		JPanel p = new JPanel(new GridLayout(1, 0));
		p.addMouseListener(this);
		p.add(l);
		p.setToolTipText("Double click to add this element to its parent.");
		this.paletteElems.add(p);
		return l;
	}

	public void setLocation(int X, int Y) {
		if (this.x <= 0)
			this.x = X;
		if (this.y <= 0)
			this.y = Y;
	}

	public void showStorePalette() {
		if (this.paletteElems.isEmpty()) {
			this.current = null;
			this.currentValid = false;
			return;
		}
		if (this.d == null || !this.d.isVisible()) {
			int size = this.paletteElems.size();
			if (size == 0)
				return;
			this.palette = new JPanel(new GridLayout(size, 0));
			for (int i = 0; i < this.paletteElems.size(); i++) {
				this.palette.add(this.paletteElems.get(i));
			}
			this.scrollPane = new JScrollPane(this.palette);
			int wdth = this.lWeidth;
			if (wdth < 300)
				wdth = 300;
			int hght = (this.paletteElems.size() + 1) * 30 +50;
			if (hght > 300)
				hght = 300;
			this.scrollPane.setPreferredSize(new Dimension(wdth, hght));

			final JPanel buttonp = new JPanel();
			
			final JButton restore = new JButton("Restore");
			restore.addActionListener(new ActionListener() {				
				public void actionPerformed(ActionEvent a) {
					if (current != null) {
						paletteElems.remove(panel);
						buttons.remove(label);
						propagateSelection(current);
						if (treeView != null && current != null) {
							treeView.undoDelete(current);
						}
						x = d.getX();
						y = d.getY();
						currentValid = false;
						exitForm();
					}
				}
			});			
			final JButton close = new JButton(" Close ");
			close.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
					GraGraElementsStore.this.x = GraGraElementsStore.this.d.getX();
					GraGraElementsStore.this.y = GraGraElementsStore.this.d.getY();
					exitForm();
				}
			});
			final JButton empty = new JButton(" Clear ");
			empty.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
					GraGraElementsStore.this.palette.removeAll();
					GraGraElementsStore.this.storeGraGra.clear();
					GraGraElementsStore.this.storeRule.clear();
					GraGraElementsStore.this.storeAtomConstraint.clear();
					GraGraElementsStore.this.paletteElems.clear();
					GraGraElementsStore.this.lWeidth = 0;
					updateTrash();
					GraGraElementsStore.this.x = GraGraElementsStore.this.d.getX();
					GraGraElementsStore.this.y = GraGraElementsStore.this.d.getY();
					exitForm();
				}
			});
			
			buttonp.add(restore);
			buttonp.add(empty);
			buttonp.add(close);

			final JPanel p = new JPanel(new BorderLayout());
			p.add(this.scrollPane, BorderLayout.CENTER);
			p.add(buttonp, BorderLayout.SOUTH);

			this.d = new JDialog();
			// this.d.setModal(true);
			this.d.setModal(false);
			this.d.setTitle("Trash");
			this.d.getContentPane().add(p);
			this.d.setSize(wdth, hght + 25);
			this.d.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent evt) {
					GraGraElementsStore.this.x = GraGraElementsStore.this.d.getX();
					GraGraElementsStore.this.y = GraGraElementsStore.this.d.getY();
					exitForm();
				}
			});
			this.d.setLocation(this.x, this.y);
			this.d.setVisible(true);
		} 
		else if (this.d.isVisible()) {
			this.d.toFront();
		}
	}

	private void refreshStorePalette() {
		if (this.d != null && this.d.isVisible()) {
			// System.out.println(this.palette.getLayout());
			((GridLayout) this.palette.getLayout()).setRows(this.paletteElems.size());
			this.palette.add(this.paletteElems.get(this.paletteElems.size() - 1));
			// System.out.println("refreshStorePalette: new count:
			// "+this.palette.getComponentCount());
			int wdth = this.lWeidth;
			int hght = (this.paletteElems.size() + 1) * 30;
			if (hght > 300)
				hght = 300;
			this.scrollPane.setPreferredSize(new Dimension(wdth, hght));
			this.scrollPane.setSize(new Dimension(wdth, hght));
			this.scrollPane.validate();
			this.d.setSize(wdth, hght + 25);
			this.d.validate();
		}
	}

	/** Exit the dialog */
	void exitForm() {
		this.d.setVisible(false);
		this.d.dispose();
	}

	public void mouseClicked(MouseEvent e) {
		// System.out.println("GraGraElementsStore.mouseClicked::
		// "+e.getSource()+" "+e.getClickCount());
		Object source = e.getSource();
		if (e.getClickCount() == 1) {
			if (source instanceof JPanel) {
				// System.out.println(source);
				panel = (JPanel) source;
				label = (JLabel) panel.getComponent(0);
				this.current = this.buttons.get(label);
				this.currentValid = true;
				Color col = label.getForeground();
				if (!col.equals(SelectedBackgroundColor)) {
					for (int i = 0; i < this.palette.getComponentCount(); i++) {
						JPanel pi = (JPanel) this.palette.getComponent(i);
						JLabel li = (JLabel) pi.getComponent(0);
						li.setForeground(col);
					}
					label.setForeground(SelectedBackgroundColor);
				}
			}
		} else if (e.getClickCount() == 2) {
			if (source instanceof JPanel) {
				panel = (JPanel) source;
				label = (JLabel) panel.getComponent(0);
				this.current = this.buttons.get(label);
				this.paletteElems.remove(panel);
				this.buttons.remove(label);
				propagateSelection(this.current);
				// System.out.println(this.currentValid+" :: "+this.current);
				if (this.treeView != null && this.current != null) {
					this.treeView.undoDelete(this.current);
				}
				this.x = this.d.getX();
				this.y = this.d.getY();
				this.currentValid = false;
				exitForm();
			}
		}
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	private void removeFromTrashPalette(Object obj) {
		JPanel p = this.obj2panel.get(obj);
		this.paletteElems.remove(p);
		this.obj2panel.remove(obj);
	}

	public Object getSelection() {
		if (this.currentValid) {
			this.currentValid = false;
			return this.current;
		} 
		return null;
	}

	/** ******** basis ***************** */
	/*
	 * public void storeGraph(GraGra parent, Graph g){ }
	 * 
	 * public void storeTypeGraph(GraGra parent, Graph g){ }
	 * 
	 * public void storeRule(GraGra parent, Rule r){ }
	 * 
	 * public void storeNAC(Rule parent, OrdinaryMorphism m){ }
	 * 
	 * public void storeAtomConstraint(GraGra parent, AtomConstraint c){ }
	 * 
	 * public void storeAtomConclusion(AtomConstraint parent, AtomConstraint c){ }
	 * 
	 * public void storeConstraint(GraGra parent, Formula c){ }
	 * 
	 * public Graph getTypeGraph(GraGra parent){ return null; }
	 * 
	 * public Graph getGraph(GraGra parent){ return null; }
	 * 
	 * public Rule getRule(GraGra parent){ return null; }
	 * 
	 * public OrdinaryMorphism getNAC(Rule parent){ return null; }
	 * 
	 * public AtomConstraint getAtomConstraint(GraGra parent){ return null; }
	 * 
	 * public AtomConstraint getAtomConclusion(AtomConstraint parentint){ return
	 * null; }
	 * 
	 * public Formula getConstraint(GraGra parent){ return null; }
	 */

}
