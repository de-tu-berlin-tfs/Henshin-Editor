/**
 * 
 */
package agg.layout;

import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;


/**
 * @author olga
 *
 */
public class GraphLayouts {

	final static public String DEFAULT_LAYOUT = "Default ( Spring based )";
	// ZEST Layouts
	final static public String SPRING_LAYOUT = "Spring";
	final static public String TREE_VERTICAL_LAYOUT = "Tree (vertical)"; 
	final static public String TREE_HORIZONTAL_LAYOUT = "Tree (horizontal)"; 
	final static public String RADIAL_LAYOUT = "Radial";
	final static public String GRID_LAYOUT = "Grid";
	final static public String VERTICAL_LAYOUT = "Vertical"; 
	final static public String HORIZONTAL_LAYOUT = "Horizontal"; 
	
	final private JPopupMenu menu;
	private ButtonGroup group;
	private ZestGraphLayout zestLayouter;
	private String msg = "";
	
//	private boolean zestavailable, available;
	
	
	
	/**
	 * 
	 */
	public GraphLayouts() {
		this.menu = new JPopupMenu();
		this.group = new ButtonGroup();
		
		createMenu();
		
//		zestavailable = 
		this.canCreateZestGraphLayouter();
	}
	
	public String getMessage() {
		return this.msg;
	}
	
	
	public void addActionListener(final ActionListener l) {
		Enumeration<AbstractButton> en = this.group.getElements();
		while (en.hasMoreElements()) {
			en.nextElement().addActionListener(l);
		}
	}
	
	
	public JPopupMenu getMenu() {
		return this.menu;
	}
		
	public void setZestLayoutEnabled(boolean b) {
		Enumeration<AbstractButton> en = this.group.getElements();
		en.nextElement();
		while (en.hasMoreElements()) {
			en.nextElement().setEnabled(b);
		}
	}
	
	public ZestGraphLayout getZestLayouter() {
		return this.zestLayouter;
	}
			
	private void createMenu() {		
		this.menu.setName("Graph Layout");
		
		JCheckBoxMenuItem mi = new JCheckBoxMenuItem(GraphLayouts.DEFAULT_LAYOUT);
		mi.setActionCommand(GraphLayouts.DEFAULT_LAYOUT);
		mi.setSelected(true);
		this.menu.add(mi);
		this.group.add(mi);
		
		this.menu.addSeparator();
		
		mi = new JCheckBoxMenuItem(GraphLayouts.SPRING_LAYOUT);
		mi.setActionCommand(GraphLayouts.SPRING_LAYOUT);
		this.menu.add(mi);
		this.group.add(mi);
		
		mi = new JCheckBoxMenuItem(GraphLayouts.TREE_VERTICAL_LAYOUT);
		mi.setActionCommand(GraphLayouts.TREE_VERTICAL_LAYOUT);
		this.menu.add(mi);
		this.group.add(mi);
		
		mi = new JCheckBoxMenuItem(GraphLayouts.TREE_HORIZONTAL_LAYOUT);
		mi.setActionCommand(GraphLayouts.TREE_HORIZONTAL_LAYOUT);
		this.menu.add(mi);
		this.group.add(mi);
		
		mi = new JCheckBoxMenuItem(GraphLayouts.RADIAL_LAYOUT);
		mi.setActionCommand(GraphLayouts.RADIAL_LAYOUT);
		this.menu.add(mi);		
		this.group.add(mi);
		
		mi = new JCheckBoxMenuItem(GraphLayouts.GRID_LAYOUT);
		mi.setActionCommand(GraphLayouts.GRID_LAYOUT);
		this.menu.add(mi);
		this.group.add(mi);
		
		mi = new JCheckBoxMenuItem(GraphLayouts.VERTICAL_LAYOUT);
		mi.setActionCommand(GraphLayouts.VERTICAL_LAYOUT);
		this.menu.add(mi);
		this.group.add(mi);
		
		mi = new JCheckBoxMenuItem(GraphLayouts.HORIZONTAL_LAYOUT);
		mi.setActionCommand(GraphLayouts.HORIZONTAL_LAYOUT);
		this.menu.add(mi);
		this.group.add(mi);
		
		this.menu.pack();
		this.menu.setBorderPainted(true);
	}
		
	public boolean canCreateZestGraphLayouter() {
		try {
			Class.forName("org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm");
			this.zestLayouter = new ZestGraphLayout();	
			return true;
		} catch (Exception ex) {
			this.msg = "WARNING! Class is not found. "
					+" Program tries to load the JAR file: \n"+ex.getMessage()
					+"\nThere will be no further warnings about this issue.\n";
			System.out.println(this.msg);
			
			this.setZestLayoutEnabled(false);
		}
		return false;
	}

	public void runCurrentLayoutAlgorithm() {
		
	}
}
