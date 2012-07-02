/*******************************************************************************
 * Copyright 2005, CHISEL Group, University of Victoria, Victoria, BC, Canada.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     The Chisel Group, University of Victoria
 *******************************************************************************/


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import org.eclipse.zest.layouts.InvalidLayoutConfiguration;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutBendPoint;
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
import org.eclipse.zest.layouts.exampleStructures.SimpleNode;
import org.eclipse.zest.layouts.exampleStructures.SimpleRelationship;
import org.eclipse.zest.layouts.progress.ProgressEvent;
import org.eclipse.zest.layouts.progress.ProgressListener;

/**
 * @author Rob Lintern
 * @author Chris Bennett
 *
 * A simple example of using layout algorithms with a Swing application.
 */
public class SimpleSwingExample {
	
	static final Color NODE_NORMAL_COLOR = new Color(225, 225, 255);
	static final Color NODE_SELECTED_COLOR = new Color(255, 125, 125);
	//static final Color NODE_ADJACENT_COLOR = new Color (255, 200, 125); 
	static final Color BORDER_NORMAL_COLOR = new Color(0, 0, 0);
	static final Color BORDER_SELECTED_COLOR = new Color(255, 0, 0);
	//static final Color BORDER_ADJACENT_COLOR = new Color (255, 128, 0);   
	static final Stroke BORDER_NORMAL_STROKE = new BasicStroke(1.0f);
	static final Stroke BORDER_SELECTED_STROKE = new BasicStroke(2.0f);
	static final Color RELATIONSHIP_NORMAL_COLOR = Color.BLUE;
	//static final Color RELATIONSHIP_HIGHLIGHT_COLOR = new Color (255, 200, 125); 

	public static SpringLayoutAlgorithm SPRING = new SpringLayoutAlgorithm(LayoutStyles.NONE);
	public static TreeLayoutAlgorithm TREE_VERT = new TreeLayoutAlgorithm(LayoutStyles.NONE);
	public static HorizontalTreeLayoutAlgorithm TREE_HORIZ = new HorizontalTreeLayoutAlgorithm(LayoutStyles.NONE);
	public static RadialLayoutAlgorithm RADIAL = new RadialLayoutAlgorithm(LayoutStyles.NONE);
	public static GridLayoutAlgorithm GRID = new GridLayoutAlgorithm(LayoutStyles.NONE);
	public static HorizontalLayoutAlgorithm HORIZ = new HorizontalLayoutAlgorithm(LayoutStyles.NONE);
	public static VerticalLayoutAlgorithm VERT = new VerticalLayoutAlgorithm(LayoutStyles.NONE);

	List<LayoutAlgorithm> algorithms = new ArrayList<LayoutAlgorithm>();
	List<String> algorithmNames = new ArrayList<String>();

	static final int INITIAL_PANEL_WIDTH = 700;
	static final int INITIAL_PANEL_HEIGHT = 500;

	static final boolean RENDER_HIGH_QUALITY = true;

	static final double INITIAL_NODE_WIDTH = 20;
	static final double INITIAL_NODE_HEIGHT = 20;
	static final int ARROW_HALF_WIDTH = 4;
	static final int ARROW_HALF_HEIGHT = 6;
	static final Shape ARROW_SHAPE = new Polygon(new int[] { -ARROW_HALF_HEIGHT, ARROW_HALF_HEIGHT, -ARROW_HALF_HEIGHT }, new int[] { -ARROW_HALF_WIDTH, 0, ARROW_HALF_WIDTH }, 3);
	static final Stroke ARROW_BORDER_STROKE = new BasicStroke(0.5f);
	static final Color ARROW_HEAD_FILL_COLOR = new Color(125, 255, 125);
	static final Color ARROW_HEAD_BORDER_COLOR = Color.BLACK;

	public static final String DEFAULT_NODE_SHAPE = "oval";

	long updateGUICount = 0;

	JFrame mainFrame;
	JPanel mainPanel;
	List<LayoutEntity> entities;
	List<LayoutRelationship> relationships;
	JToolBar toolBar;
	JLabel lblProgress;
	JToggleButton btnContinuous;
	JToggleButton btnAsynchronous;
	JButton btnStop;

	LayoutAlgorithm currentLayoutAlgorithm;
	String currentLayoutAlgorithmName;
	SimpleNode selectedEntity;
	Point mouseDownPoint;
	Point selectedEntityPositionAtMouseDown;
	long idCount;
	String currentNodeShape = DEFAULT_NODE_SHAPE; // e.g., oval, rectangle

	public SimpleSwingExample() {

	}

	protected void addAlgorithm(LayoutAlgorithm algorithm, String name, boolean animate) {
		this.algorithms.add(algorithm);
		this.algorithmNames.add(name);
		if (animate) {
			System.out.println("Animate feature is not supported");
		}
	}

	public void start() {

		this.mainFrame = new JFrame("Simple Swing Layout Example");
		this.toolBar = new JToolBar();
		this.mainFrame.getContentPane().setLayout(new BorderLayout());
		this.mainFrame.getContentPane().add(this.toolBar, BorderLayout.NORTH);
		this.lblProgress = new JLabel("Progress: ");
		this.mainFrame.getContentPane().add(this.lblProgress, BorderLayout.SOUTH);

		createMainPanel();
		this.mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				stop();
				SimpleSwingExample.this.mainFrame.dispose();

			}
		});

		this.btnContinuous = new JToggleButton("continuous", false);
		this.btnAsynchronous = new JToggleButton("asynchronous", false);

		this.toolBar.add(this.btnContinuous);
		this.toolBar.add(this.btnAsynchronous);

		this.btnStop = new JButton("Stop");
		this.btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		this.toolBar.add(this.btnStop);

		JButton btnCreateGraph = new JButton("New graph");
		btnCreateGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
				createGraph(true);
			}
		});
		this.toolBar.add(btnCreateGraph);
		JButton btnCreateTree = new JButton("New tree");
		btnCreateTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
				createGraph(false);
			}
		});
		this.toolBar.add(btnCreateTree);

		createGraph(false);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.mainFrame.setLocation((int) (screenSize.getWidth() - INITIAL_PANEL_WIDTH) / 2, (int) (screenSize.getHeight() - INITIAL_PANEL_HEIGHT) / 2);
		this.mainFrame.pack();
		this.mainFrame.setVisible(true);
		this.mainFrame.repaint();

		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				public void run() {
		
					SPRING = new SpringLayoutAlgorithm(LayoutStyles.NONE);
					TREE_VERT = new TreeLayoutAlgorithm(LayoutStyles.NONE);
					TREE_HORIZ = new HorizontalTreeLayoutAlgorithm(LayoutStyles.NONE);
					RADIAL = new RadialLayoutAlgorithm(LayoutStyles.NONE);
					GRID = new GridLayoutAlgorithm(LayoutStyles.NONE);
					HORIZ = new HorizontalLayoutAlgorithm(LayoutStyles.NONE);
					VERT = new VerticalLayoutAlgorithm(LayoutStyles.NONE);

					SPRING.setIterations(1000);
					// initialize layouts
					TREE_VERT.setComparator(new Comparator() {
						@SuppressWarnings("unchecked")
						public int compare(Object o1, Object o2) {
							if (o1 instanceof Comparable && o2 instanceof Comparable) {
								return ((Comparable) o1).compareTo(o2);
							}
							return 0;
						}

					});
					GRID.setRowPadding(20);
					addAlgorithm(SPRING, "Spring", false);
					addAlgorithm(TREE_VERT, "Tree-V", false);
					addAlgorithm(TREE_HORIZ, "Tree-H", false);
					addAlgorithm(RADIAL, "Radial", false);
					addAlgorithm(GRID, "Grid", false);
					addAlgorithm(HORIZ, "Horiz", false);
					addAlgorithm(VERT, "Vert", false);

					for (int i = 0; i < SimpleSwingExample.this.algorithms.size(); i++) {
						final LayoutAlgorithm algorithm = SimpleSwingExample.this.algorithms.get(i);
						final String algorithmName = SimpleSwingExample.this.algorithmNames.get(i);
						//final boolean algorithmAnimate = ((Boolean)algorithmAnimates.get(i)).booleanValue();
						JButton algorithmButton = new JButton(algorithmName);
						algorithmButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								SimpleSwingExample.this.currentLayoutAlgorithm = algorithm;
								SimpleSwingExample.this.currentLayoutAlgorithmName = algorithmName;
								algorithm.setEntityAspectRatio((double)SimpleSwingExample.this. mainPanel.getWidth() / (double)SimpleSwingExample.this. mainPanel.getHeight());
								//animate = algorithmAnimate;
								performLayout();
							}
						});
						SimpleSwingExample.this.toolBar.add(algorithmButton);
					}
					
				}
			});
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	protected void stop() {
		if (this.currentLayoutAlgorithm != null && this.currentLayoutAlgorithm.isRunning()) {
			this.currentLayoutAlgorithm.stop();
		}
	}

	protected void performLayout() {
		stop();
		final Cursor cursor = this.mainFrame.getCursor();
		this.updateGUICount = 0;
		placeRandomly();
		final boolean continuous = this.btnContinuous.isSelected();
		final boolean asynchronous = this.btnAsynchronous.isSelected();
		ProgressListener progressListener = new ProgressListener() {
			public void progressUpdated(final ProgressEvent e) {
				//if (asynchronous) {
				updateGUI();
				//}
				SimpleSwingExample.this.lblProgress.setText("Progress: " + e.getStepsCompleted() + " of " + e.getTotalNumberOfSteps() + " completed ...");
				SimpleSwingExample.this.lblProgress.paintImmediately(0, 0, SimpleSwingExample.this.lblProgress.getWidth(), SimpleSwingExample.this.lblProgress.getHeight());
			}

			public void progressStarted(ProgressEvent e) {
				if (!asynchronous) {
					SimpleSwingExample.this.mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				}
				SimpleSwingExample.this.lblProgress.setText("Layout started ...");
				SimpleSwingExample.this.lblProgress.paintImmediately(0, 0, SimpleSwingExample.this.lblProgress.getWidth(), SimpleSwingExample.this.lblProgress.getHeight());
			}

			public void progressEnded(ProgressEvent e) {
				SimpleSwingExample.this.lblProgress.setText("Layout completed ...");
				SimpleSwingExample.this.lblProgress.paintImmediately(0, 0, SimpleSwingExample.this.lblProgress.getWidth(), SimpleSwingExample.this.lblProgress.getHeight());
				SimpleSwingExample.this.currentLayoutAlgorithm.removeProgressListener(this);
				if (!asynchronous) {
					SimpleSwingExample.this.mainFrame.setCursor(cursor);
				}
			}
		};

		this.currentLayoutAlgorithm.addProgressListener(progressListener);

		try {
			final LayoutEntity[] layoutEntities = new LayoutEntity[this.entities.size()];
			this.entities.toArray(layoutEntities);
			final LayoutRelationship[] layoutRelationships = new LayoutRelationship[this.relationships.size()];
			this.relationships.toArray(layoutRelationships);
//			SwingUtilities.invokeLater(new Runnable() {
//				public void run() {
					try {
						this.currentLayoutAlgorithm.applyLayout(layoutEntities, layoutRelationships, 0, 0, this.mainPanel.getWidth(), this.mainPanel.getHeight(), asynchronous, continuous);
					} catch (InvalidLayoutConfiguration e) {
						System.out.println(e.getLocalizedMessage());
//						e.printStackTrace();
					}

//				}
//
//			});
			//if (!animate) {
			updateGUI();
			//}
			// reset
			this.currentNodeShape = DEFAULT_NODE_SHAPE;
		} catch (StackOverflowError e) {
			e.printStackTrace();
		} finally {
		}
	}

	private void createMainPanel() {

		this.mainPanel = new MainPanel(); // see below for class definition
		this.mainPanel.setPreferredSize(new Dimension(INITIAL_PANEL_WIDTH, INITIAL_PANEL_HEIGHT));
		this.mainPanel.setBackground(Color.WHITE);
		this.mainPanel.setLayout(null);
		this.mainFrame.getContentPane().add(new JScrollPane(this.mainPanel), BorderLayout.CENTER);

		this.mainPanel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				SimpleSwingExample.this.selectedEntity = null;
				for (Iterator iter = SimpleSwingExample.this.entities.iterator(); iter.hasNext() &&SimpleSwingExample.this. selectedEntity == null;) {
					SimpleNode entity = (SimpleNode) iter.next();
					double x = entity.getX();
					double y = entity.getY();
					double w = entity.getWidth();
					double h = entity.getHeight();
					Rectangle2D.Double rect = new Rectangle2D.Double(x, y, w, h);
					if (rect.contains(e.getX(), e.getY())) {
						SimpleSwingExample.this.selectedEntity = entity;
					}
				}
				if (SimpleSwingExample.this.selectedEntity != null) {
					SimpleSwingExample.this.mouseDownPoint = e.getPoint();
					SimpleSwingExample.this.selectedEntityPositionAtMouseDown = new Point((int) SimpleSwingExample.this.selectedEntity.getX(), (int) SimpleSwingExample.this.selectedEntity.getY());
				} else {
					SimpleSwingExample.this.mouseDownPoint = null;
					SimpleSwingExample.this.selectedEntityPositionAtMouseDown = null;
				}
				updateGUI();
			}

			public void mouseReleased(MouseEvent e) {
				SimpleSwingExample.this.selectedEntity = null;
				SimpleSwingExample.this.mouseDownPoint = null;
				SimpleSwingExample.this.selectedEntityPositionAtMouseDown = null;
				updateGUI();
			}
		});

		this.mainPanel.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				//                if (selectedEntity != null) {
				//					//TODO: Add mouse moving
				//                    //selectedEntity.setLocationInLayout(selectedEntityPositionAtMouseDown.x + dx, selectedEntityPositionAtMouseDown.y + dy);
				//                    updateGUI();
				//                } 
			}

			public void mouseMoved(MouseEvent e) {
			}
		});
	}

	protected void createGraph(boolean addNonTreeRels) {
		this.entities = new ArrayList<LayoutEntity>();
		this.relationships = new ArrayList<LayoutRelationship>();
		this.selectedEntity = null;

		createTreeGraph(2, 4, 2, 5, true, true, addNonTreeRels);
		//        createCustomGraph();

		placeRandomly();
		this.mainPanel.repaint();
	}

	/**
	 * 
	 * @param maxLevels Max number of levels wanted in tree	
	 * @param maxChildren Max number of children for each node in the tree
	 * @param randomNumChildren Whether or not to pick random number of levels (from 1 to maxLevels) and 
	 * random number of children (from 1 to maxChildren)
	 */
	private void createTreeGraph(int minChildren, int maxChildren, int minLevels, int maxLevels, boolean randomNumChildren, boolean randomLevels, boolean addNonTreeRels) {
		LayoutEntity currentParent = createSimpleNode(getNextID());
		this.entities.add(currentParent);
		createTreeGraphRecursive(currentParent, minChildren, maxChildren, minLevels, maxLevels, 1, randomNumChildren, randomLevels, addNonTreeRels);
	}

	private void createTreeGraphRecursive(LayoutEntity currentParentNode, int minChildren, int maxChildren, int minLevel, int maxLevel, int level, boolean randomNumChildren, boolean randomLevels, boolean addNonTreeRels) {
		if (level > maxLevel) {
			return;
		}
		if (randomLevels) {
			if (level > minLevel) {
				double zeroToOne = Math.random();
				if (zeroToOne < 0.75) {
					return;
				}
			}
		}
		int numChildren = randomNumChildren ? Math.max(minChildren, (int) (Math.random() * maxChildren + 1)) : maxChildren;
		for (int i = 0; i < numChildren; i++) {
			LayoutEntity newNode = createSimpleNode(getNextID());
			this.entities.add(newNode);
			if (addNonTreeRels && this.entities.size() % 5 == 0) {
				int index = (int) (Math.random() * this.entities.size());
				LayoutRelationship rel = new SimpleRelationship(this.entities.get(index), newNode, false);
				this.relationships.add(rel);
			}
			LayoutRelationship rel = new SimpleRelationship(currentParentNode, newNode, false);
			this.relationships.add(rel);
			createTreeGraphRecursive(newNode, minChildren, maxChildren, minLevel, maxLevel, level + 1, randomNumChildren, randomLevels, addNonTreeRels);
		}
	}

	/**
	 * Call this from createGraph in place of createTreeGraph 
	 * this for debugging and testing.
	 */
	/*    private void createCustomGraph() {
	 LayoutEntity A = createSimpleNode("1");
	 LayoutEntity B = createSimpleNode("10");
	 LayoutEntity _1 = createSimpleNode("100");
	 entities.add(A);
	 entities.add(B);
	 entities.add(_1);
	 relationships.add(new SimpleRelationship (A, B, false));
	 relationships.add(new SimpleRelationship (A, _1, false));
	 relationships.add(new SimpleRelationship (_1, A, false));
	 }
	 */
	private String getNextID() {
		String id = "" + this.idCount;
		this.idCount++;
		return id;
	}

	/** Places nodes randomly on the screen **/
	private void placeRandomly() {
		for (Iterator iter = this.entities.iterator(); iter.hasNext();) {
			SimpleNode simpleNode = (SimpleNode) iter.next();
			double x = Math.random() * INITIAL_PANEL_WIDTH - INITIAL_NODE_WIDTH;
			double y = Math.random() * INITIAL_PANEL_HEIGHT - INITIAL_NODE_HEIGHT;
			simpleNode.setLocationInLayout(x, y);
			simpleNode.setSizeInLayout(INITIAL_NODE_WIDTH, INITIAL_NODE_HEIGHT);
		}
	}

	/**
	 * Creates a SimpleNode
	 * @param name
	 * @return
	 */
	private SimpleNode createSimpleNode(String name) {
		SimpleNode simpleNode = new SimpleNode(name);
		return simpleNode;
	}

	void updateGUI() {
		this.updateGUICount++;
		if (this.updateGUICount > 0) {
			this.mainPanel.paintImmediately(0, 0, this.mainPanel.getWidth(), this.mainPanel.getHeight());
		}
	}

	static Point2D.Double getEllipseIntersectionPoint(double theta, double ellipseWidth, double ellipseHeight) {
		double nhalfw = ellipseWidth / 2.0; // half ellipse width
		double nhalfh = ellipseHeight / 2.0; // half ellipse height
		double tanTheta = Math.tan(theta);

		double a = nhalfw;
		double b = nhalfh;
		double x = (a * b) / Math.sqrt(Math.pow(b, 2) + Math.pow(a, 2) * Math.pow(tanTheta, 2));
		if ((theta > Math.PI / 2.0 && theta < 1.5 * Math.PI) || (theta < -Math.PI / 2.0 && theta > -1.5 * Math.PI)) {
			x = -x;
		}
		double y = tanTheta * x;
		Point2D.Double p = new Point2D.Double(x, y);
		return p;
	}

	public static void main(String[] args) {
		(new SimpleSwingExample()).start();
	}

	/**
	 * A JPanel that provides entity and relationship rendering
	 * Instead of letting Swing paint all the JPanels for us, we will just do our own painting here
	 */
	class MainPanel extends JPanel {

		private static final long serialVersionUID = 1;

		protected void paintChildren(Graphics g) {
			if (g instanceof Graphics2D && RENDER_HIGH_QUALITY) {
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				((Graphics2D) g).setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			}

			// paint the nodes
			for (Iterator iter = SimpleSwingExample.this.entities.iterator(); iter.hasNext();) {
				paintEntity((SimpleNode) iter.next(), g);
			}

			// paint the relationships 
			for (Iterator iter = SimpleSwingExample.this.relationships.iterator(); iter.hasNext();) {
				paintRelationship((LayoutRelationship) iter.next(), g);
			}
		}

		private void paintEntity(SimpleNode entity, Graphics g) {
			boolean isSelected = SimpleSwingExample.this.selectedEntity != null && SimpleSwingExample.this.selectedEntity.equals(entity);
			g.setColor(isSelected ? NODE_SELECTED_COLOR : NODE_NORMAL_COLOR);
			if (SimpleSwingExample.this.currentNodeShape.equals("rectangle")) {
				g.fillRect((int) entity.getX(), (int) entity.getY(), (int) entity.getWidth(), (int) entity.getHeight());
			} else { // default 
				g.fillOval((int) entity.getX(), (int) entity.getY(), (int) entity.getWidth(), (int) entity.getHeight());
			}
			g.setColor(isSelected ? BORDER_SELECTED_COLOR : BORDER_NORMAL_COLOR);
			String name = entity.toString();
			Rectangle2D nameBounds = g.getFontMetrics().getStringBounds(name, g);
			g.drawString(name, (int) (entity.getX() + entity.getWidth() / 2.0 - nameBounds.getWidth() / 2.0), (int) (entity.getY() + entity.getHeight() / 2.0 + nameBounds.getHeight() / 2.0));//- nameBounds.getHeight() - nameBounds.getY()));
			if (g instanceof Graphics2D) {
				((Graphics2D) g).setStroke(isSelected ? BORDER_SELECTED_STROKE : BORDER_NORMAL_STROKE);
			}
			if (SimpleSwingExample.this.currentNodeShape.equals("rectangle")) {
				g.drawRect((int) entity.getX(), (int) entity.getY(), (int) entity.getWidth(), (int) entity.getHeight());
			} else { // default
				g.drawOval((int) entity.getX(), (int) entity.getY(), (int) entity.getWidth(), (int) entity.getHeight());
			}
		}

		private void paintRelationship(LayoutRelationship rel, Graphics g) {

			SimpleNode src = (SimpleNode) rel.getSourceInLayout();
			SimpleNode dest = (SimpleNode) rel.getDestinationInLayout();

			// Add bend points if required
			if (((SimpleRelationship) rel).getBendPoints() != null && ((SimpleRelationship) rel).getBendPoints().length > 0) {
				drawBendPoints(rel, g);
			} else {
				double srcX = src.getX() + src.getWidth() / 2.0;
				double srcY = src.getY() + src.getHeight() / 2.0;
				double destX = dest.getX() + dest.getWidth() / 2.0;
				double destY = dest.getY() + dest.getHeight() / 2.0;
				double dx = getLength(srcX, destX);
				double dy = getLength(srcY, destY);
				double theta = Math.atan2(dy, dx);
				drawRelationship(src, dest, theta, srcX, srcY, destX, destY, g);

				// draw an arrow in the middle of the line
				drawArrow(theta, srcX, srcY, dx, dy, g);
			}
		}

		/**
		 * Draw a line from the edge of the src node to the edge of the destination node 
		 */
		private void drawRelationship(SimpleNode src, SimpleNode dest, double theta, double srcX, double srcY, double destX, double destY, Graphics g) {

			double reverseTheta = theta > 0.0d ? theta - Math.PI : theta + Math.PI;

			Point2D.Double srcIntersectionP = getEllipseIntersectionPoint(theta, src.getWidth(), src.getHeight());

			Point2D.Double destIntersectionP = getEllipseIntersectionPoint(reverseTheta, dest.getWidth(), dest.getHeight());

			drawRelationship(srcX + srcIntersectionP.getX(), srcY + srcIntersectionP.getY(), destX + destIntersectionP.getX(), destY + destIntersectionP.getY(), g);
		}

		/**
		 * Draw a line from specified source to specified destination
		 */
		private void drawRelationship(double srcX, double srcY, double destX, double destY, Graphics g) {
			g.setColor(RELATIONSHIP_NORMAL_COLOR);
			g.drawLine((int) srcX, (int) srcY, (int) destX, (int) destY);
		}

		private void drawArrow(double theta, double srcX, double srcY, double dx, double dy, Graphics g) {
			AffineTransform tx = new AffineTransform();
			double arrX = srcX + (dx) / 2.0;
			double arrY = srcY + (dy) / 2.0;
			tx.translate(arrX, arrY);
			tx.rotate(theta);
			Shape arrowTx = tx.createTransformedShape(ARROW_SHAPE);
			if (g instanceof Graphics2D) {
				g.setColor(ARROW_HEAD_FILL_COLOR);
				((Graphics2D) g).fill(arrowTx);
				((Graphics2D) g).setStroke(ARROW_BORDER_STROKE);
				g.setColor(ARROW_HEAD_BORDER_COLOR);
				((Graphics2D) g).draw(arrowTx);
			}
		}

		/**
		 * Get the length of a line ensuring it is not too small to render
		 * @param start
		 * @param end
		 * @return
		 */
		private double getLength(double start, double end) {
			double length = end - start;
			// make sure dx is not zero or too small
			if (length < 0.01 && length > -0.01) {
				if (length > 0) {
					length = 0.01;
				} else if (length < 0) {
					length = -0.01;
				}
			}
			return length;
		}

		/**
		 * Draw a line from specified source to specified destination
		 */
		private void drawCurvedRelationship(double srcX, double srcY, double control1X, double control1Y, double control2X, double control2Y, double destX, double destY, Graphics g) {
			GeneralPath shape = new GeneralPath();
			shape.moveTo((float) srcX, (float) srcY);
			shape.curveTo((float) control1X, (float) control1Y, (float) control2X, (float) control2Y, (float) destX, (float) destY);
			g.setColor(RELATIONSHIP_NORMAL_COLOR);
			((Graphics2D) g).draw(shape);
		}

		/**
		 * Draws a set of lines between bendpoints, returning the last bendpoint
		 * drawn. Note that this assumes the first and last bendpoints are actually
		 * the source node and destination node centre points. 
		 * @param relationship
		 * @param bendNodes
		 * @param bendEdges
		 * @return the last bendpoint entity or null if there are no bendpoints
		 */
		private void drawBendPoints(LayoutRelationship rel, Graphics g) {
			final String DUMMY_TITLE = "dummy";
			LayoutBendPoint bp;

			SimpleNode startEntity = (SimpleNode) rel.getSourceInLayout();
			SimpleNode destEntity = (SimpleNode) rel.getDestinationInLayout();
			double srcX = startEntity.getX();
			double srcY = startEntity.getY();

			// Transform the bendpoints to this coordinate system
			LayoutBendPoint[] bendPoints = ((SimpleRelationship) rel).getBendPoints();

			srcX = bendPoints[1].getX();
			srcY = bendPoints[1].getY();
			int bpNum = 2;
			while (bpNum < bendPoints.length - 1) { // ignore first and last bendpoints (src and dest)
				int currentBpNum = bpNum;
				bp = bendPoints[bpNum];
				if (bp.getIsControlPoint()) {
					if (bendPoints[bpNum + 1].getIsControlPoint()) {
						destEntity = new SimpleNode(DUMMY_TITLE, bendPoints[bpNum + 2].getX(), bendPoints[bpNum + 2].getY(), 0.01, 0.01);
						drawCurvedRelationship(srcX, srcY, bp.getX(), bp.getY(), bendPoints[bpNum + 1].getX(), bendPoints[bpNum + 1].getY(), bendPoints[bpNum + 2].getX(), bendPoints[bpNum + 2].getY(), g);
						bpNum += 4;
					} else {
						destEntity = new SimpleNode(DUMMY_TITLE, bp.getX(), bp.getY(), 0.01, 0.01);
					}
				} else {
					drawRelationship(srcX, srcY, bp.getX(), bp.getY(), g);
					bpNum++;
					destEntity = new SimpleNode(DUMMY_TITLE, bp.getX(), bp.getY(), 0.01, 0.01);
				}
				startEntity = destEntity;
				if (currentBpNum == bendPoints.length - 2) { // last point
					// draw an arrow in the middle of the line
					double dx = getLength(srcX, destEntity.getX());
					double dy = getLength(srcY, destEntity.getY());
					double theta = Math.atan2(dy, dx);
					drawArrow(theta, srcX, srcY, dx, dy, g);
				} else {
					srcX = startEntity.getX();
					srcY = startEntity.getY();
				}
			}

		}
	}


}
