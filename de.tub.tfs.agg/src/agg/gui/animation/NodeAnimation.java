/**
 * 
 */
package agg.gui.animation;

import java.awt.Graphics2D;
import java.awt.Point;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;

import agg.editor.impl.EdArc;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdNode;
import agg.editor.impl.EdRule;
import agg.editor.impl.EdType;
import agg.xt_basis.Arc;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Match;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;


/**
 * @author olga
 *
 */
public class NodeAnimation {

	public final static int JUMP = 11;
	public final static int WORM = 20;
	public final static int CROSS = 30;
	public final static int COMBI_CROSS = 31;
	
	
	private ImageIcon image; //, inverseImage;	
	
	protected String imageFileName = "";
	
	private final Hashtable<EdType, ImageIcon> type2icon;
	
	private final Hashtable<String, ImageIcon> name2icon;
	
	protected int kind;
	
	protected boolean stop;
	
	protected int x1, y1, x2, y2, plus;
	
	protected EdNode nG, n1G;
	
	private int step, delay;
	
	protected EdRule r;
	protected EdGraph g;
	protected Hashtable<EdNode, Point> node2position;
	
	protected Graphics2D grs;
	protected boolean needGraphicsToMove;
	protected boolean left, right, top, down;
	protected int dx, dy;
	private boolean ddone;
		
	public NodeAnimation() {
		this.type2icon = new Hashtable<EdType, ImageIcon>();
		this.name2icon = new Hashtable<String, ImageIcon>();
		this.node2position = new Hashtable<EdNode, Point>();
	}
	
	public void dispose() {
		this.type2icon.clear();
		this.name2icon.clear();
		this.r = null;
		this.g = null;
		this.grs = null;
	}
	
	public static Vector<String> getAnimationKindsAsString() {
		final Vector<String> vec = new Vector<String>();
		vec.add("JUMP");
		vec.add("WORM");
		vec.add("CROSS");
		vec.add("COMBI_CROSS");
		return vec;
	}
	
	public boolean prepareAnimation(
			final EdRule rule, 
			final EdGraph graph, 
			final Graphics2D graphics,
			final int animation) {
//		System.out.println("NodeAnimation.animation:: of rule: "+rule.getName()+"   kind: "+animation);	
		this.r = rule;
		this.g = graph;
		this.grs = graphics;
		this.kind = animation;
			
		if (this.kind == JUMP) {
			// if an edge changed its target node
			this.setAnimationData();
			// if a new node created
			this.setAnimationDataOfCreation();
		} else if (this.kind == CROSS) {
			this.setAnimationDataOfSpan();
		} else if (this.kind == COMBI_CROSS) {
			this.setAnimationDataOfCombiSpan();
		} 
		else {	
			// if an edge changed its target node
			this.setAnimationData();
		}
		
		return true;
	}

	public boolean prepareAnimation(
			final EdRule rule, 
			final EdGraph graph, 
			final Graphics2D graphics) {
		this.r = rule;
		this.g = graph;
		this.grs = graphics;
		
		this.kind = this.r.getAnimationKind();
//		System.out.println("NodeAnimation.animation:: of rule: "+rule.getName()+"  kind: "+kind);
		if (this.kind == JUMP) {
			// if an edge changed its target node
			this.setAnimationData();
			// if a new node created
			this.setAnimationDataOfCreation();
		} else if (this.kind == CROSS) {
			this.setAnimationDataOfSpan();
		} else if (this.kind == COMBI_CROSS) {
			this.setAnimationDataOfCombiSpan();
		}
		else {	
			// if an edge changed its target node
			this.setAnimationData();
		}		
				
		return true;
	}	
	
	public void animate() {	
//		System.out.println("NodeAnimation.running   ...");
			
		enableNodeAnimation();
			
		Enumeration<EdNode> keys = this.node2position.keys();
		while (keys.hasMoreElements()) {
			EdNode n = keys.nextElement();
			Point p = this.node2position.get(n);
			n.setX(p.x);
			n.setY(p.y);
			n.getLNode().setFrozen(true);
		}
		this.node2position.clear();	
	}
	
	private void setAnimationData() {
		Match m = this.r.getMatch();
		if (m != null) {
//			System.out.println("NodeAnimation.setAnimationData::  ");
			this.nG = null;
			this.n1G = null;
			List<GraphObject> dom = m.getDomainObjects();
			for (int i=0; i<dom.size() && !this.stop; i++) {
				GraphObject oL = dom.get(i);
//				System.out.println("NodeAnimation.setAnimationData::  oL: "+oL.getType().getName());

				if (oL.isNode()) {
					EdNode nL = this.r.getLeft().findNode(oL);							
//					System.out.println("NodeAnimation.setAnimationData::  nL: "+nL.getType().getName()+"   animated: "+nL.getType().isAnimated());
					if (nL != null && nL.getType().isAnimated()) {
						Arc arcR = null;
						for (Iterator<Arc> outsL = ((Node)oL).getOutgoingArcs(); outsL.hasNext() && (arcR == null);) {
							Arc outL = outsL.next();									
							if (this.r.getBasisRule().getImage(outL) == null) {
								GraphObject oR = this.r.getBasisRule().getImage(oL);
								if (oR != null) {
//									EdNode nR = this.r.getRight().findNode(oR);
																	
									Arc outR = null;
									
									arcR = getTargetEdgeOfAnimation(((Node)oR).getOutgoingArcs(),
											nL.getType().animationParameter.targetEdgeTypeName);
//									System.out.println("NodeAnimation.setAnimationData::  arcR: "+arcR);
									
									if (arcR == null) {
										for (Iterator<Arc> outsR = ((Node)oR).getOutgoingArcs(); outsR.hasNext();) {
											outR = outsR.next();
											if (!this.r.getBasisRule().getInverseImage(outR).hasMoreElements()) {
												arcR = outR;											
												break;
											} 
										}
										if (arcR == null && outR != null)
											arcR = outR;
									}									
								}
							}
						}
//						System.out.println("NodeAnimation.setAnimationData::  arcR: "+arcR);
						if (arcR != null) {
							EdNode n1R = this.r.getRight().findNode(arcR.getTarget());
//							System.out.println("NodeAnimation.setAnimationData::  n1R: "+n1R);
							if (n1R != null) {	
								Enumeration<GraphObject> inv = this.r.getBasisRule().getInverseImage(n1R.getBasisObject());
								if (inv.hasMoreElements()) {
									this.n1G = this.g.findNode(m.getImage(inv.nextElement()));
									this.nG = this.g.findNode(m.getImage(oL));
									
//									System.out.println("NodeAnimation.animationTest::  nG: "+nG);
//									System.out.println("NodeAnimation.animationTest::  n1G: "+n1G);
//									System.out.println(nG.getType().animationParameter.kind+"   "
//											+nG.getType().animationParameter.step+"   "
//											+nG.getType().animationParameter.delay+"   "
//											+nG.getType().animationParameter.plus);
									
									setStartPosition(this.nG.getX(), this.nG.getY());
									setEndPosition(this.n1G.getX(), this.n1G.getY());																		
									setStep(this.nG.getType().animationParameter.step);								
									setDelay(this.nG.getType().animationParameter.delay);
									setEndPlus(this.nG.getType().animationParameter.plus);
									this.imageFileName = this.nG.getType().imageFileName;
									
									this.node2position.put(this.nG, new Point(
											applyPlusToPosition(this.n1G.getX(), this.plus), 
											applyPlusToPosition(this.n1G.getY(), this.plus)));
									
//									correctAdjacentEdgesText(this.nG);
								}
							}
						} 
//						else {
//							nG = g.findNode(m.getImage(oL));
//							
//							setStartPosition(nG.getX(), nG.getY());
//							setEndPosition(nG.getX()+200, nG.getY()+200);							
//							setStep(nG.getType().animationParameter.step);								
//							setDelay(nG.getType().animationParameter.delay);
//							setEndPlus(nG.getType().animationParameter.plus);
//							imageFileName = nG.getType().imageFileName;	
//							
//							node2position.put(nG, new Point(nG.getX()+200+this.plus, nG.getY()+200+this.plus));
//						}
					}
				}
			}
		}
	}
	
	private void correctAdjacentEdgesText(EdNode nG) {
		List<EdArc> arcs = nG.getContext().getOutgoingArcs(nG);
		for (EdArc a: arcs) {
			a.refreshTextLocation();			
		}
		arcs = nG.getContext().getIncomingArcs(nG);
		for (EdArc a: arcs) {
			a.refreshTextLocation();			
		}
	}
	
	private Arc getTargetEdgeOfAnimation(
			final Iterator<Arc> outArcs,
			final String targetEdgeTypeName) {
		
		if (targetEdgeTypeName == null)
			return null;
		
		String tname = ("[unnamed]".equals(targetEdgeTypeName))? "" : targetEdgeTypeName;
		while (outArcs.hasNext()) {
			final Arc a = outArcs.next();			
			if (a.getType().getName().equals(tname)) {
				return a;
			}
		}
		return null;
	}
	
	private void setAnimationDataOfSpan() {
//		System.out.println("NodeAnimation.setAnimationDataOfSpan:: ");
		Match m = this.r.getMatch();
		if (m != null) {
			OrdinaryMorphism com = m.getCoMorphism();
//			System.out.println("NodeAnimation.setAnimationDataOfSpan:: com: "+com);
			if (com != null) {
				this.nG = null;
				this.n1G = null;
				Point lastpoint = null;
				int p = 0;
				Enumeration<GraphObject> dom = com.getDomain();
				while (dom.hasMoreElements() && !this.stop) {
					GraphObject oR = dom.nextElement();
					if (oR.isNode()
							&& !this.r.getBasisRule().getInverseImage(oR).hasMoreElements()) {
//						System.out.println("NodeAnimation.setAnimationDataOfSpan::  oR: "+oR.getType().getName());
						// get node to create
						EdNode nR = this.r.getRight().findNode(oR);
//						System.out.println("NodeAnimation.setAnimationDataOfSpan::  nR: "+nR.getType().getName()+"   animated: "+nR.getType().isAnimated());
						if (nR != null && nR.getType().isAnimated()) {
							// get new created node
							GraphObject oG = com.getImage(oR);
							this.nG = this.g.findNode(oG);
							Iterator<Arc> neighbours = this.nG.getBasisNode().getOutgoingArcs();
//							System.out.println("NodeAnimation.setAnimationDataOfSpan:: neighbours: "+ neighbours.size());													
							while (neighbours.hasNext()) {
								Arc neighbour = neighbours.next();
								if (neighbour.getSource() != neighbour.getTarget()) {
									Point point = computeCross(neighbour);
									if (point != null) {
										setEndPosition(point.x, point.y);									
										setStep(this.nG.getType().animationParameter.step); 								
										setDelay(this.nG.getType().animationParameter.delay); 
										setEndPlus(this.nG.getType().animationParameter.plus);			
										this.imageFileName = this.nG.getType().imageFileName;
	
										if (lastpoint == null) {
											lastpoint = point;
											p++;
										} else if (Math.abs(lastpoint.x-point.x) < 5 
												&& Math.abs(lastpoint.y-point.y) < 5) {
											point = this.computeSimilarPosition(point, p*this.dx/2, p*this.dy/2, p);																						
											p++;
										}
										
										this.node2position.put(this.nG, new Point(
												applyPlusToPosition(point.x, this.plus), 
														applyPlusToPosition(point.y, this.plus)));
										break;
									}
								}							
							} 							
						}
					}
				}
			}
		}
	}

	private void setAnimationDataOfCombiSpan() {
//		System.out.println("NodeAnimation.setAnimationDataOfCombiSpan:: ");
		Match m = this.r.getMatch();
		if (m != null) {
			OrdinaryMorphism com = m.getCoMorphism();
//			System.out.println("NodeAnimation.setAnimationDataOfSpan:: com: "+com);
			if (com != null) {
				this.nG = null;
				this.n1G = null;
				Point lastpoint = null;
				int p = 0;
				Enumeration<GraphObject> dom = com.getDomain();
				while (dom.hasMoreElements() && !this.stop) {
					GraphObject oR = dom.nextElement();
					if (oR.isNode()
							&& !this.r.getBasisRule().getInverseImage(oR).hasMoreElements()) {
//						System.out.println("NodeAnimation.setAnimationDataOfSpan::  oR: "+oR.getType().getName());
						// get node to create
						EdNode nR = this.r.getRight().findNode(oR);
//						System.out.println("NodeAnimation.setAnimationDataOfCombiSpan::  nR: "+nR.getType().getName()+"   animated: "+nR.getType().isAnimated());
						if (nR != null && nR.getType().isAnimated()) {
							// get new created node
							GraphObject oG = com.getImage(oR);
							this.nG = this.g.findNode(oG);
							int count = this.nG.getBasisNode().getOutgoingArcsSet().size();
							Iterator<Arc> neighbours = this.nG.getBasisNode().getOutgoingArcs();
//							System.out.println("NodeAnimation.setAnimationDataOfCombiSpan:: neighbours: "+neighbours.size());
							Arc neighbour = null;
							if (count == 1) {
								neighbour = neighbours.next();
								GraphObject nbNode = neighbour.getTarget();
								if (com.getInverseImage(nbNode).hasMoreElements()
										&& m.getInverseImage(nbNode).hasMoreElements()) {
									Point point = computeCross(neighbour);
//									System.out.println("NodeAnimation.setAnimationDataOfCombiSpan (1):: point: "+point);
									if (point != null) {
										setEndPosition(point.x, point.y);									
										setStep(this.nG.getType().animationParameter.step); 								
										setDelay(this.nG.getType().animationParameter.delay); 
										setEndPlus(this.nG.getType().animationParameter.plus);			
										this.imageFileName = this.nG.getType().imageFileName;
										
										this.node2position.put(this.nG, new Point(
												applyPlusToPosition(point.x, this.plus), 
												applyPlusToPosition(point.y, this.plus)));									
									}
								}
							}
							if (count == 2) {
								while (neighbours.hasNext()) {
									neighbour = neighbours.next();
									GraphObject nbNode = neighbour.getTarget();
									if (com.getInverseImage(nbNode).hasMoreElements()
											&& m.getInverseImage(nbNode).hasMoreElements()) {
										break;
									}
								}
								Point point = computeCross(neighbour);
//								System.out.println("NodeAnimation.setAnimationDataOfCombiSpan (2):: point: "+point);
								if (point != null) {
									setEndPosition(point.x, point.y);									
									setStep(this.nG.getType().animationParameter.step); 								
									setDelay(this.nG.getType().animationParameter.delay); 
									setEndPlus(this.nG.getType().animationParameter.plus);			
									this.imageFileName = this.nG.getType().imageFileName;
									
									this.node2position.put(this.nG, new Point(
											applyPlusToPosition(point.x, this.plus), 
													applyPlusToPosition(point.y, this.plus)));									
								}
							} 
							else if (count == 3) {
								while (neighbours.hasNext()) {
									neighbour = neighbours.next();
									GraphObject nbNode = neighbour.getTarget();
									if (com.getInverseImage(nbNode).hasMoreElements()
											&& m.getInverseImage(nbNode).hasMoreElements()) {
										break;
									}
								}
								Point point = computeCross(neighbour);
//								System.out.println("NodeAnimation.setAnimationDataOfCombiSpan (3):: point: "+point);
								if (point != null) {
									setEndPosition(point.x, point.y);									
									setStep(this.nG.getType().animationParameter.step); 								
									setDelay(this.nG.getType().animationParameter.delay); 
									setEndPlus(this.nG.getType().animationParameter.plus);			
									this.imageFileName = this.nG.getType().imageFileName;
									
									if (lastpoint == null) {
										lastpoint = point;
										p++;
									} else if (Math.abs(lastpoint.x-point.x) < 5 
											&& Math.abs(lastpoint.y-point.y) < 5) {
										point = this.computeSimilarPosition(point, p*this.dx/2, p*this.dy/2, p);																						
										p++;
									}
									this.node2position.put(this.nG, new Point(
											applyPlusToPosition(point.x, this.plus), 
													applyPlusToPosition(point.y, this.plus)));									
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void setAnimationDataOfCreation() {
//		System.out.println("NodeAnimation.setAnimationDataOfCreation:: ");
		Match m = this.r.getMatch();
		if (m != null) {
			OrdinaryMorphism com = m.getCoMorphism();
//			System.out.println("NodeAnimation.setAnimationDataOfCreation:: com: "+com);
			if (com != null) {
				this.nG = null;
				this.n1G = null;
				Enumeration<GraphObject> dom = com.getDomain();
				while (dom.hasMoreElements() && !this.stop) {
					GraphObject oR = dom.nextElement();
					if (oR.isNode() // and node should be created
							&& !this.r.getBasisRule().getInverseImage(oR).hasMoreElements()) {
//						System.out.println("NodeAnimation.setAnimationDataOfCreation::  oR: "+oR.getType().getName());
						// get node to create
						EdNode nR = this.r.getRight().findNode(oR);
//						System.out.println("NodeAnimation.setAnimationDataOfCreation::  nR: "+nR.getType().getName()+"   animated: "+nR.getType().isAnimated());
						if (nR != null && nR.getType().isAnimated()) {
							// get new created node
							GraphObject oG = com.getImage(oR);
							this.nG = this.g.findNode(oG);
							Iterator<Arc> outs = this.nG.getBasisNode().getOutgoingArcs();
//							System.out.println("NodeAnimation.setAnimationDataOfCreation:: outs: "+ outs.size());
							int outsSize = this.nG.getBasisNode().getOutgoingArcsSet().size();
							if (outsSize >= 1) {	
								Arc out = outs.next();
								if (outsSize > 1) {
									Arc out1 = getTargetEdgeOfAnimation(this.nG.getBasisNode().getOutgoingArcs(),
											this.nG.getType().animationParameter.targetEdgeTypeName);									
									if (out1 != null)
										out = out1;
								}
								
								this.n1G = this.g.findNode(out.getTarget());
								if (this.n1G != null) {
									setStartPosition(this.n1G.getX(), this.n1G.getY());
									setEndPosition(this.n1G.getX(), this.n1G.getY());									
									setStep(this.nG.getType().animationParameter.step); 								
									setDelay(this.nG.getType().animationParameter.delay); 
									setEndPlus(this.nG.getType().animationParameter.plus);			
									this.imageFileName = this.nG.getType().imageFileName;
									
									this.node2position.put(this.nG, new Point(
											applyPlusToPosition(this.n1G.getX(), this.plus), 
													applyPlusToPosition(this.n1G.getY(), this.plus)));										
								}															
							}
						}
					}
				}
			}
		}
	}

	private void cross() {
		if (this.image != null) {
			this.grs.drawImage(this.image.getImage(),
					this.x2, this.y2, null);
		}
		delay();
	}
	
	private Point computeCross(final Arc outgoingArc) {
		if (outgoingArc != null) {			
			Node oG = (Node) outgoingArc.getTarget();
			this.n1G = this.g.findNode(oG);
			Vector<EdNode> neighbours = new Vector<EdNode>();
			Iterator<Arc> outarcs = oG.getOutgoingArcsSet().iterator();								
			while(outarcs.hasNext()) {								
				final Arc outarc = outarcs.next();
				if (outarc.getSource() != outarc.getTarget()) {
					EdNode n = this.g.findNode(outarc.getTarget());
					if (n != null && n != this.nG) {
						neighbours.add(n);
					}	
				}
			}
//			System.out.println("neighbours:  "+neighbours);
			setStartPosition(this.n1G.getX(), this.n1G.getY());			
			Point point = computeCrossPart4(neighbours);			
			return point;
		} 
		return null;
	}
	
	private Point computeCrossPart4(final Vector<EdNode> neighbours) {
		if (neighbours.size() >= 3) {	
			int x0 = this.n1G.getX();
			int y0 = this.n1G.getY();
//			System.out.println("neighbours: "+neighbours.size()+" --  take three of them only");
			if (!this.ddone) {
				for (int i=0; i<3; /*neighbours.size();*/ i++) {
					EdNode n = neighbours.get(i);
					int xi = n.getX();
					int yi = n.getY();
					this.dx = (Math.abs(x0-xi) > this.dx)? Math.abs(x0-xi): this.dx;
					this.dy = (Math.abs(y0-yi) > this.dy)? Math.abs(y0-yi): this.dy;
					this.ddone = true;
				}				
			}
			this.left=false; this.right=false; this.top=false; this.down=false;
			int hdx = this.dx/2;
			int hdy = this.dy/2;
			for (int i=0; i<3; /*neighbours.size();*/ i++) {
				EdNode n = neighbours.get(i);
				int xi = n.getX();
				int yi = n.getY();
				if (xi+hdx < x0 && !this.left) {
					this.left = true;					
				} else 
					if (xi-hdx > x0 && !this.right) {
						this.right = true;
				} else 
					if (yi+hdy < y0 && !this.top) {					
						this.top = true;
				} else 
					if (yi-hdy > y0 && !this.down) {
						this.down = true;
				}
			}
			
			final Point pos = computePosition(new Point(x0, y0), this.dx, this.dy);
			
			return pos;
		}
		return null;
	}
	
	private Point computePosition(final Point pos0, int distx, int disty) {
//		System.out.println("left: "+ left+"   right: "+right+"   top: "+top+"   down: "+down);
		Point pos = new Point(0,0);
		if (!this.left && !this.down) {
//			System.out.println("!left && !down");
			pos.x = pos0.x;
			pos.y = pos0.y + disty;
		} else if (!this.left) {
//			System.out.println("!left");
			pos.x = pos0.x - distx;
			pos.y = pos0.y;
		} else if (!this.right && !this.top) {
//			System.out.println("!right && !top");
			pos.x = pos0.x;
			pos.y = pos0.y - disty;
		}
		else if (!this.right && !this.down) {
//			System.out.println("!right && !down");
			pos.x = pos0.x + distx;
			pos.y = pos0.y;
		} else if (!this.right) {
//			System.out.println("!right");
			pos.x = pos0.x + distx;
			pos.y = pos0.y;
		} if (!this.left && !this.top) {
//			System.out.println("!left && !top");
			pos.x = pos0.x - distx;
			pos.y = pos0.y;
		} else if (!this.top) {
//			System.out.println("!top");
			pos.y = pos0.y - disty;
			pos.x = pos0.x;
		} else if (!this.right && !this.down) {
//			System.out.println("!right && !down");
			pos.x = pos0.x + distx;
			pos.y = pos0.y;
		}
		else if (!this.down) {
//			System.out.println("!down");
			pos.y = pos0.y + disty;
			pos.x = pos0.x;
		}
		return pos;
	}
	
	private Point computeSimilarPosition(final Point pos0, int distx, int disty, int p) {
		Point pos = new Point(0,0);
		 
		if (!this.left && !this.down) {
//			System.out.println("!left && !down");
			pos.x = pos0.x - distx;
			pos.y = pos0.y - disty;
			if (p == 1) {
				pos.x = pos.x - distx/2; 
				pos.y = pos.y + disty/2;
			}
		} else if (!this.left && !this.top) {
//			System.out.println("!left && !top");
			pos.x = pos0.x + distx;
			pos.y = pos0.y - disty;	
			if (p == 1) {
				pos.x = pos.x - distx/2;
				pos.y = pos.y - disty/2;
			}
		} else if (!this.right && !this.top) {
//			System.out.println("!right && !top");
			pos.x = pos0.x + distx;
			pos.y = pos0.y + disty;		
			if (p == 1) {
				pos.x = pos.x + distx/2; 
				pos.y = pos.y - disty/2;
			}
		} else if (!this.right && !this.down) {
//			System.out.println("!right  && !down");
			pos.x = pos0.x - distx;
			pos.y = pos0.y + disty;
			if (p == 1) {
				pos.x = pos.x + distx/2; 
				pos.y = pos.y + disty/2;
			}
		} else if (!this.left) {
//			System.out.println("!left");
			pos.x = pos0.x + distx;
			pos.y = pos0.y - disty;
		}
		else if (!this.right) {
//			System.out.println("!right");
			pos.x = pos0.x - distx;
			pos.y = pos0.y + disty;
		}		
		else if (!this.top) {
//			System.out.println("!top");
			pos.x = pos0.x + distx;
			pos.y = pos0.y + disty;
		} else if (!this.down) {
//			System.out.println("!down");
			pos.x = pos0.x - distx;
			pos.y = pos0.y + disty;
		} 		
		
		return pos;
	}

	/*
	private Point computeRect(final Vector<Arc> outgoingArcs) {		
		if (outgoingArcs.size() == 2) {
//			System.out.println("computeRect");
			Arc outarc1 = outgoingArcs.get(0);
			Arc outarc2 = outgoingArcs.get(1);
			EdNode n1 = g.findNode(outarc1.getTarget());
			EdNode n2 = g.findNode(outarc2.getTarget());			
			EdNode n = null;
			
			for (Iterator<Arc> arcs1 = n1.getBasisNode().getOutgoingArcs(); arcs1.hasNext() && n==null;) {
				Arc ai = arcs1.next();
				for (Iterator<Arc> arcs2 = n2.getBasisNode().getOutgoingArcs(); arcs2.hasNext() && n==null;) {
					Arc aj = arcs2.next();
					if (ai.getTarget() == aj.getTarget()) {
						n = this.g.findNode(ai.getTarget());
					}
				}
			}
			if (n != null) {
				Point point = computeRectPart4(n1, n, n2);				
				return point;
			}
		} 
		return null;
	}
	
	
	private Point computeRectPart4(EdNode n1, EdNode n, EdNode n2) {							
			int posx = 0;
			int posy = 0;
			
			int n1x = n1.getX();
			int n1y = n1.getY();
			
			int n2x = n2.getX();
			int n2y = n2.getY();
			
			if (n1x > n2x && n1y < n2y) {
				if (n.getY() > n1y && n.getX() > n2x) {
					posx = n2x;
					posy = n1y;
				} else if (n.getY() < n2y && n.getX() < n1x) {
					posx = n1x;
					posy = n2y;
				}
			} else if (n1x < n2x && n1y > n2y) {
				if (n.getY() > n2y && n.getX() > n1x) {
					posx = n1x;
					posy = n2y;
				} else if (n.getY() < n1y && n.getX() < n2x) {
					posx = n2x;
					posy = n1y;
				}			
			} else if (n1x < n2x && n1y < n2y) {
				if (n.getY() > n1y && n.getX() < n2x) {
					posx = n2x;
					posy = n1y;
				} else if (n.getY() < n2y && n.getX() > n1x) {
					posx = n1x;
					posy = n2y;
				}
			} else if (n1x > n2x && n1y > n2y) {
				if (n.getY() > n2y && n.getX() < n1x) {
					posx = n1x;
					posy = n2y;
				} else if (n.getY() < n1y && n.getX() > n2x) {
					posx = n2x;
					posy = n1y;
				}				
			}
			
			return new Point(posx, posy);
	}
	*/
	
	public void setStop() {
		this.stop = true;
	}
	
	public boolean isStopped() {
		return this.stop;
	}
	
	public void setAnimationKind(final int animKind) {
		this.kind = animKind;
	}
	
	public void setStartPosition(final int startX, final int startY) {
		this.x1 = startX;
		this.y1 = startY;
	}
	
	public void setEndPosition(final int endX, final int endY) {
		this.x2 = endX;
		this.y2 = endY;
	}

	public void setEndPlus(final int d) {
		this.plus = d;
	}
	
	public void setStep(final int s) {
		this.step = s;
	}
	
	public void setDelay(final int ms) {
		this.delay = ms;
	}
	
	protected void enableNodeAnimation() {		
		this.image = this.name2icon.get(this.imageFileName);
		if (this.image == null && this.kind != CROSS && this.kind != COMBI_CROSS) {
			getAnimationImageOfNode();
		} 
		
		nodeAnimation();	
	}
	
	public void enableNodeAnimation(
			final Graphics2D graphics,
			final String imageName, 
			final int animation,
			final int startX, 
			final int startY, 
			final int endX, 
			final int endY, 
			final int s,
			final int ms) {
		
		this.grs = graphics;
		this.imageFileName = imageName;
		this.kind = animation;
		this.x1 = startX;
		this.y1 = startY;
		this.x2 = endX;
		this.y2 = endY;
		this.step = s;
		this.delay = ms;
		
		this.image = this.name2icon.get(this.imageFileName);
		if (this.image == null) {
			getAnimationImageOfNode();
		} 
		
		nodeAnimation();	
	}

/*	
	private void enableNodeAnimation(
			final Graphics2D graphics,
			final EdNode n, 
			final int animation,
			final int startX, 
			final int startY, 
			final int endX, 
			final int endY, 
			final int s,
			final int ms) {
		
		grs = graphics;
		imageFileName = n.getType().imageFileName;
		kind = animation;
		x1 = startX;
		y1 = startY;
		x2 = endX;
		y2 = endY;
		step = s;
		delay = ms;

		this.image = name2icon.get(imageFileName);
		if (this.image == null) {
			getAnimationImageOfNode(n);
		} 
		
		nodeAnimation();	
	}
	
	
	private void getAnimationImageOfNode(final EdNode n) {			
		URL url = null;
		if (!n.getType().imageFileName.equals("")) {
			url = ClassLoader.getSystemClassLoader().getResource(n.getType().imageFileName);
		} else {
			url = ClassLoader.getSystemClassLoader().getResource("agg/lib/icons/smile.png");
		}
		
		if (url != null) {
			this.image = new ImageIcon(url);
			type2icon.put(n.getType(), this.image);
			
//			url = ClassLoader.getSystemResource("agg/lib/icons/sad.png");
//			this.animateInvImage = new ImageIcon(url);
		} 
	}
*/
	
	private void getAnimationImageOfNode() {			
		URL url = null;
		if (!this.imageFileName.equals("")) {
			url = ClassLoader.getSystemClassLoader().getResource(this.imageFileName);
		} 
		if (url == null) {
			url = ClassLoader.getSystemClassLoader().getResource("agg/lib/icons/smile.png");
		}
		
		if (url != null) {
			this.image = new ImageIcon(url);
			this.name2icon.put(this.imageFileName, this.image);
			
//			url = ClassLoader.getSystemResource("agg/lib/icons/sad.png");
//			this.animateInvImage = new ImageIcon(url);
		} 
	}
	
	private void nodeAnimation() {
		switch (this.kind) {
			case JUMP: this.jump(); break;
			case WORM: this.worm(); break;
			case CROSS: this.cross(); break;
			case COMBI_CROSS: this.cross(); break;
		}
		this.stop = false;
	}

	
	private void jump() {
		delay();
	}

	private void worm() {
		if (this.image != null) {
			Vector<Integer> data = computeWormLengthAndStep();
			if (!data.isEmpty()) {
				// get worm length
				int l = data.get(0).intValue();	
				// get worm X-step
				int sX = data.get(1).intValue();
				// get worm Y-step
				int sY = data.get(2).intValue();
				// set next X
				int nX = this.x1+sX;
				// set next Y
				int nY = this.y1+sY;
				
				// draw first image
				this.grs.drawImage(this.image.getImage(),
						nX, nY, null);	
	
				// do loop by length and step
				for (int s=0; s<l && !this.stop; s=s+this.step) {
					// set next X
					nX = nX+sX;
					// set next Y
					nY = nY+sY;
					
					// draw next image
					delay();
					this.grs.drawImage(this.image.getImage(),
							nX, nY, null);
					
	//				delay();
				}			
			}
		}		
	}		
	
	private Vector<Integer> computeWormLengthAndStep() {
		final Vector<Integer> result = new Vector<Integer>(3);
		boolean hasResult = false;
		int dX = this.x2-this.x1;		
		int dY = this.y2-this.y1;
		
		int lX = Math.abs(dX);
		int lY = Math.abs(dY);
		int l = lX;
		
		int sX = this.step;
		int sY = this.step;
		
		if (dX != 0 && dY != 0) {
			if (dX > 0) {
				if (lX > lY) {	
//					System.out.println(">>>> WORM:: (dX != 0 && dY != 0)  &&  (lX > lY)");
					sX = this.step;			
					sY = dY*sX/dX;	
				} else {
//					System.out.println(">>>> WORM:: (dX != 0 && dY != 0)  &&  (lX < lY)");
					sY = dY>0 ? this.step : -this.step;
					sX = dX*sY/dY;
					l = lY;
				}
			} else {
				if (lX > lY) {			
					sX = -this.step;			
					sY = dY*sX/dX;
				} else {			
					sY = dY>0 ? this.step : -this.step;			
					sX = dX*sY/dY;
					l = lY;
				}
			}
			hasResult = true;
		} else if (dX == 0 && dY != 0) {
//			System.out.println(">>>> WORM:: (dX == 0 && dY != 0)");
			sX = 0;
			sY = dY>0 ? this.step : -this.step;
			l = lY;
			hasResult = true;
		} else if (dY == 0 && dX != 0) {			
//			System.out.println(">>>> WORM:: (dY == 0 && dX != 0)");
			sX = dX>0 ? this.step : -this.step;
			sY = 0;	
			hasResult = true;
		} 
		if (hasResult) {
			result.add(new Integer(l));
			result.add(new Integer(sX));
			result.add(new Integer(sY));
		}
		return result;
	}


	private void delay() {
		try {
//			this.animationThread.sleep(delay);
			Thread.sleep(this.delay);
		} catch (Exception ex) {}
		
//		for (long l=0; l<delay; l++) {
//			for (long l1=0; l1<1000000; l1++) {}
//		}
	}
	
	private int applyPlusToPosition(int pos, int pl) {
		int newpos = pos + pl;
		if (newpos < 0 && pl < 0) {
			newpos = pos + Math.abs(pl);
		}
		return newpos;
	}
}
