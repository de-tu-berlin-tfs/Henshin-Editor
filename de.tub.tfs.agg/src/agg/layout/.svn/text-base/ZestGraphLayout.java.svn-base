/**
 * 
 */
package agg.layout;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.eclipse.zest.layouts.InvalidLayoutConfiguration;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutEntity;
import org.eclipse.zest.layouts.LayoutRelationship;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalTreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.VerticalLayoutAlgorithm;
import org.eclipse.zest.layouts.exampleStructures.SimpleGraph;
//import org.eclipse.zest.layouts.progress.ProgressEvent;
//import org.eclipse.zest.layouts.progress.ProgressListener;

import agg.editor.impl.EdArc;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdNode;

/**
 * @author olga
 *
 */
public class ZestGraphLayout
{

	// TODO: user setting for 
//	dimension, 
//	GridLayoutAlgorithm.RowPadding, 
//	SpringLayoutAlgorithm.SpringGravitation, SpringMove, SpringStrain, SpringLength
	
	
	
	EdGraph edgraph;
	Dimension dimension;
	Hashtable<EdNode, LayoutEntity> ednode2node = new Hashtable<EdNode, LayoutEntity>();
	Hashtable<EdArc, LayoutRelationship> edarc2arc = new Hashtable<EdArc, LayoutRelationship>();
	List<EdNode> ednodes = new Vector<EdNode>();
	List<EdArc> edarcs = new Vector<EdArc>();
	Dimension averNodeSize = new Dimension(500, 500);
	SimpleGraph simplegraph;
	LayoutAlgorithm algorithm;
	String algorithmName;
	Hashtable<String, LayoutAlgorithm> name2algorithm;
		           		
	/**
	 * the algorithms ZEST provide
	 */
	private static final ArrayList<LayoutAlgorithm> algorithms = new ArrayList<LayoutAlgorithm>();
	static {
		algorithms.add(new SpringLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING));
		algorithms.add(new TreeLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING));
		algorithms.add(new HorizontalTreeLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING));
		algorithms.add(new RadialLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING));
		algorithms.add(new GridLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING));
		algorithms.add(new HorizontalLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING));
		algorithms.add(new VerticalLayoutAlgorithm(
				LayoutStyles.NO_LAYOUT_NODE_RESIZING));
	}

	public static final ArrayList<String> algorithmNames = new ArrayList<String>();
	static {
		algorithmNames.add("Spring");
		algorithmNames.add("Tree (vertical)");
		algorithmNames.add("Tree (horizontal)");
		algorithmNames.add("Radial");
		algorithmNames.add("Grid");
		algorithmNames.add("Horizontal");
		algorithmNames.add("Vertical");
	}

	
	public ZestGraphLayout() {	
		this.name2algorithm = new Hashtable<String, LayoutAlgorithm>();
		for (int i=0; i<algorithmNames.size(); i++) {
			this.name2algorithm.put(algorithmNames.get(i), algorithms.get(i));
		}		
	}
	
	public ZestGraphLayout(final EdGraph graph) {
		this();
		this.edgraph = graph;
	}

	public ZestGraphLayout(final EdGraph graph, final String algorithmname) {
		this();
		this.edgraph = graph;
		this.setAlgorithm(algorithmname);
	}
	
	public void start() {
		applyLayout();
	}
	
	
	public void setGraph(final EdGraph graph) {
		this.edgraph = graph;
	}
	
	public void setGraphDimension(final Dimension dim) {
		this.dimension = dim;
	}
	
	@SuppressWarnings("rawtypes")
	public void setAlgorithm(final String algorithmName) {	
		this.algorithmName = algorithmName;
		this.algorithm = this.name2algorithm.get(algorithmName);
			
//		this.algorithm.setEntityAspectRatio(4/3);
		
		if (this.algorithm instanceof SpringLayoutAlgorithm) {
			((SpringLayoutAlgorithm) this.algorithm).setIterations(1000); // default
			((SpringLayoutAlgorithm) this.algorithm).setRandom(true); // default
			((SpringLayoutAlgorithm) this.algorithm).setSpringGravitation(0.0001d); // default 1.0
//			((SpringLayoutAlgorithm) this.algorithm).setSpringStrain(0.0001d); // default 1.0
//			((SpringLayoutAlgorithm) this.algorithm).setSpringMove(0.001d);  // default 1.0
		} 
		else if (this.algorithm instanceof TreeLayoutAlgorithm) {
			((TreeLayoutAlgorithm) this.algorithm).setComparator(new Comparator() {
				@SuppressWarnings("unchecked")
				public int compare(Object o1, Object o2) {
					if (o1 instanceof Comparable && o2 instanceof Comparable) {
						return ((Comparable) o1).compareTo(o2);
					}
					return 0;
				}
			});
		}
		else if (this.algorithm instanceof GridLayoutAlgorithm) {
			((GridLayoutAlgorithm) this.algorithm).setRowPadding(20);
		}
	}
		
	public boolean applyLayout() {
		if (this.edgraph == null)
			return false;
			
		setGraphLayoutData();
		
		// extract graph entities and relationships
		final List<?> entitiesList = this.simplegraph.getEntities();		
		final LayoutEntity[] entities = new LayoutEntity[entitiesList.size()];
		entitiesList.toArray(entities);
		
		final List<?> relationshipList = this.simplegraph.getRelationships();
		final LayoutRelationship[] relationships = new LayoutRelationship[relationshipList.size()];		
		relationshipList.toArray(relationships);
		
		try {
//			long time1 = System.currentTimeMillis();			
//			System.out.println("***  Zest  "+this.algorithmName+"  layout  running ...");	
			this.algorithm.applyLayout(entities, relationships, 15.0, 15.0,
									this.dimension.width, this.dimension.height,
									false, false);	
//			System.out.println("***  Zest  "+this.algorithmName+"  layout  done  in "
//							+(System.currentTimeMillis()-time1)+"ms");								
		} catch (InvalidLayoutConfiguration e) {			
			System.out.println(e.getLocalizedMessage());
			return false;
		}
		
		convertFromSimpleGraph();
			
		unsetGraphLayoutData();
		
		return true;
	}
	
	private void setGraphLayoutData() {
		this.simplegraph = new SimpleGraph();
		this.ednodes.clear();
		this.edarcs.clear();
		this.ednodes.addAll(this.edgraph.getVisibleNodes());
		this.edarcs.addAll(this.edgraph.getArcs());
		
//		final List<EdArc> connections = new Vector<EdArc>();
					
		for (int i=0; i<this.ednodes.size(); i++) {
			EdNode ednode = this.ednodes.get(i);
			final LayoutEntity node = this.simplegraph.addObjectNode(ednode);
			node.setLocationInLayout(ednode.getX(), ednode.getY());
			node.setSizeInLayout(ednode.getWidth(), ednode.getHeight());	
			this.ednode2node.put(ednode, node);
		}	
		for (int i=0; i<this.edarcs.size(); i++) {
			EdArc edarc = this.edarcs.get(i);
			EdNode src = (EdNode)edarc.getSource();
			EdNode tar = (EdNode)edarc.getTarget();
			if (this.ednodes.contains(src) && this.ednodes.contains(tar)) {
				this.simplegraph.addObjectRelationship(src, tar, false, 1);
//				connections.add(edarc);				
			}
		}

//		if (this.dimension == null) 
		{	
			this.averNodeSize.setSize(this.edgraph.getAverageNodeDim(this.ednodes));	
			this.dimension = getNeededPanelSize(this.averNodeSize, this.ednodes.size(), this.algorithm);
		}
	}
	
	private void unsetGraphLayoutData() {
		this.simplegraph = null;
		this.ednodes.clear();
		this.edarcs.clear();
		this.ednode2node.clear();
		this.dimension = null;
	}
	
	protected Dimension getNeededPanelSize(
			final Dimension averagenodesize, 
			int count,
			final LayoutAlgorithm algorith) {
		
		if (averagenodesize.width < 25 || averagenodesize.height < 25) {
			averagenodesize.width = 25;
			averagenodesize.height = 25;
		}
		
		final int ans = (averagenodesize.width >= averagenodesize.height) ? averagenodesize.width : averagenodesize.height;
//		int sizetmp = 2 * (int)Math.sqrt(count);
		int sizeX = ans * count/2; //sizetmp;
		if (sizeX > 500 && sizeX < 1000)
			sizeX = 1000;
		int sizeY = sizeX*3/4;
		
//		if (this.algorithm instanceof SpringLayoutAlgorithm) {
//		} 
//		else 
//		if (this.algorithm instanceof TreeLayoutAlgorithm) {		
//		} else 
//		if (this.algorithm instanceof HorizontalTreeLayoutAlgorithm) {			
//		}
//		else 
//		if (this.algorithm instanceof RadialLayoutAlgorithm) {
//		}
//		else 
//		if (this.algorithm instanceof GridLayoutAlgorithm) {
//		}
//		else 
		if (this.algorithm instanceof HorizontalLayoutAlgorithm) {
			sizeX = (ans +20) * count;
			sizeY = 400;
		}
		else 
		if (this.algorithm instanceof VerticalLayoutAlgorithm) {
			sizeX = 400;
			sizeY = (ans +20) * count;
		}
		
		final Dimension dim = new Dimension(sizeX, sizeY);

		// wenn zu kleine panel
		if (dim.width < 400 || dim.height < 400) {
			dim.width = 400;
			dim.height = 400;
		}
//		System.out.println(dim.width+"    "+dim.height);
		return dim;
	}
		
	protected void convertFromSimpleGraph()  {
		if (this.edgraph != null) {
			Enumeration<EdNode> list = this.ednode2node.keys();
			while (list.hasMoreElements()) {			
				EdNode ednode = list.nextElement();
				LayoutEntity n = this.ednode2node.get(ednode);
				ednode.setX((int)n.getXInLayout());
				ednode.setY((int)n.getYInLayout());
			}			
			this.edgraph.straightAllArcs();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.zest.layouts.progress.ProgressListener#progressEnded(org.eclipse.zest.layouts.progress.ProgressEvent)
	 */
//	public void progressEnded(ProgressEvent arg0) {
//		System.out.println("progressEnded  "+arg0.getTotalNumberOfSteps()+"   "+arg0.getStepsCompleted());
//		
//	}

	/* (non-Javadoc)
	 * @see org.eclipse.zest.layouts.progress.ProgressListener#progressStarted(org.eclipse.zest.layouts.progress.ProgressEvent)
	 */
//	public void progressStarted(ProgressEvent arg0) {
//		System.out.println("progressStarted  ");
//		
//	}

	/* (non-Javadoc)
	 * @see org.eclipse.zest.layouts.progress.ProgressListener#progressUpdated(org.eclipse.zest.layouts.progress.ProgressEvent)
	 */
//	public void progressUpdated(ProgressEvent arg0) {
//		System.out.println("progressUpdated  "+arg0.getTotalNumberOfSteps()+"   "+arg0.getStepsCompleted());
//		
//	}
	
}
