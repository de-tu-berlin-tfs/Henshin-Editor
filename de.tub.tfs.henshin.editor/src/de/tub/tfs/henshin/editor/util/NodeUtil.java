/**
 * 
 */
package de.tub.tfs.henshin.editor.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.model.layout.HenshinLayoutFactory;
import de.tub.tfs.henshin.model.layout.Layout;
import de.tub.tfs.henshin.model.layout.LayoutSystem;
import de.tub.tfs.henshin.model.layout.NodeLayout;

/**
 * The Class NodeUtil.
 */
public class NodeUtil {

	/**
	 * Calculate position.
	 * 
	 * @param points
	 *            the points
	 * @param nodeLayout
	 *            the node layout
	 * @return the point
	 */
	public static Point calculatePosition(
			Map<PointAndRadius, NodeLayout> points, NodeLayout nodeLayout) {
		boolean wiederholen = false;
		int x = nodeLayout.getX();
		int y = nodeLayout.getY();
		int xCenter = x
				+ Math.round(getWeight((Node) nodeLayout.getModel(), true) / 2);
		int yCenter = y
				+ Math.round(getHeight((Node) nodeLayout.getModel()) / 2);
		double radius = Math.sqrt((x - xCenter) * (x - xCenter) + (y - yCenter)
				* (y - yCenter));
		do {
			wiederholen = false;
			for (PointAndRadius p : points.keySet()) {
				while (Math.sqrt((p.getPoint().x - xCenter)
						* (p.getPoint().x - xCenter)
						+ (p.getPoint().y - yCenter)
						* (p.getPoint().y - yCenter)) < radius + p.getRadius()) {
					x += 10;
					y += 10;
					xCenter += 10;
					yCenter += 10;
					wiederholen = true;
				}
			}
		} while (wiederholen);
		points.put(new PointAndRadius(new Point(xCenter, yCenter), radius),
				nodeLayout);
		nodeLayout.setX(x);
		nodeLayout.setY(y);
		return new Point(x, y);

	}

	// /**
	// * Gets the node layout.
	// *
	// * @param node
	// * the node
	// * @return the node layout
	// *
	// * @deprecated
	// */
	// public static NodeLayout getNodeLayout(Node node) {
	// return getNodeLayout(node,
	// ModelUtil.getModelRoot(node, LayoutSystem.class));
	// }

	/**
	 * Gets the node layout.
	 * 
	 * @param node
	 *            the node
	 * @param layoutSystem
	 *            the layout system
	 * @return the node layout
	 */
	public static NodeLayout getNodeLayout(Node node, LayoutSystem layoutSystem) {
		NodeLayout result = null;
		if (layoutSystem != null) {
			result = findNodeLayout(node, layoutSystem);
			if (result == null) {
				result = createNodeLayout(node, layoutSystem);
			}
		}
		return result;
	}

	protected static NodeLayout findNodeLayout(Node node,
			LayoutSystem layoutSystem) {
		for (Layout nodeLayout : layoutSystem.getLayouts()) {
			if (nodeLayout.getModel() == node) {
				return (NodeLayout) nodeLayout;
			}
		}
		return null;
	}

	/**
	 * Gets the height.
	 * 
	 * @param node
	 *            the node
	 * @return the height
	 */
	public static int getHeight(Node node) {
		int height = 25;
		if (node.getAttributes().size() > 0) {
			height += 5 + 15 * node.getAttributes().size();
		}
		return height;
	}

	/**
	 * Gets the weight.
	 * 
	 * @param node
	 *            the node
	 * @return the weight
	 */
	public static int getWeight(Node node, boolean withAttributes) {
		int weight = 100;
		if (node != null) {
			String name = new String();
			if (node.getName() != null) {
				name += node.getName();
			}

			if (node.getType() != null) {
				name += ":" + node.getType().getName();
				weight = Math.max(name.length() * 7 + 25, weight);
				if (withAttributes) {
					for (Attribute attr : node.getAttributes()) {
						String s = "";
						if (attr.getType() != null) {
							s = ("- " + attr.getType().getName() + "=" + attr
									.getValue());
							weight = Math.max((s.length() * 7) + 15, weight);
						}
					}
				}
			}
		}
		return weight;
	}

	/**
	 * Creates the node layout.
	 * 
	 * @param node
	 *            the node
	 * @return the node layout
	 */
	private static NodeLayout createNodeLayout(Node node,
			LayoutSystem layoutSystem) {
		return createNodeLayout(node, layoutSystem, 0);
	}

	/**
	 * Creates the node layout.
	 * 
	 * @param node
	 *            the node
	 * @return the node layout
	 */
	private static NodeLayout createNodeLayout(Node node,
			LayoutSystem layoutSystem, int mappingColor) {
		NodeLayout nodeLayout = HenshinLayoutFactory.eINSTANCE
				.createNodeLayout();
		int x = 150;
		int y = 80;
		Map<PointAndRadius, NodeLayout> points = getPoints2NodeLyouts(node,
				layoutSystem);
		nodeLayout.setX(x);
		nodeLayout.setY(y);
		nodeLayout.setModel(node);

		if (mappingColor == 0) {
			setMappingColor(nodeLayout);
		} else {
			nodeLayout.setColor(mappingColor);
		}

		calculatePosition(points, nodeLayout);
		layoutSystem.getLayouts().add(nodeLayout);
		return nodeLayout;
	}

	/**
	 * @param node
	 * @param layoutSystem
	 * @return
	 */
	public static Map<PointAndRadius, NodeLayout> getPoints2NodeLyouts(
			Node node, LayoutSystem layoutSystem) {
		return getPoints2NodeLyouts(node, layoutSystem.getLayouts());
	}

	public static Map<PointAndRadius, NodeLayout> getPoints2NodeLyouts(
			Node node, Collection<Layout> layouts) {
		Map<PointAndRadius, NodeLayout> points = new HashMap<PointAndRadius, NodeLayout>();
		Iterator<Layout> iter = layouts.iterator();

		while (iter.hasNext()) {
			Layout l = iter.next();

			if (l instanceof NodeLayout) {
				NodeLayout nL = (NodeLayout) l;

				if (nL.getModel() != null) {
					if (node.getGraph().getNodes().contains(nL.getModel())) {
						int xCenter = nL.getX()
								+ Math.round(NodeUtil.getWeight(
										(Node) nL.getModel(), true) / 2);
						int yCenter = nL.getY()
								+ Math.round(NodeUtil.getHeight((Node) nL
										.getModel()) / 2);
						int dx = nL.getX() - xCenter;
						int dy = nL.getY() - yCenter;

						points.put(new PointAndRadius(new Point(xCenter,
								yCenter), Math.sqrt(dx * dx + dy * dy)), nL);
					}
				} else {
					iter.remove();
				}
			}
		}
		return points;
	}

	private static void setMappingColor(final NodeLayout nodeLayout) {
		final Node node = (Node) nodeLayout.getModel();
		int mappingColor = nodeLayout.getColor();

		// If the grand parent of the node is a rule and
		// the node doesn't have a mapping color, then compute one.
		if (node.eContainer().eContainer() instanceof Rule && mappingColor == 0) {
			final Rule rule = node.getGraph().getContainerRule();
			mappingColor = getMappingColorByUnit(node, rule);

			if (mappingColor == 0) {
				mappingColor = getMappingColor(node, rule.getMappings());
			}

			if (mappingColor != 0) {
				nodeLayout.setColor(mappingColor);
				setMappingColorInAc(node, mappingColor,
						ModelUtil.getMappings(rule.getLhs().getFormula()));
				ColorUtil.addMappingColor(mappingColor, rule);
			}
		}
	}

	/**
	 * Check, if the node is mapped in an amalgamation unit and take the color
	 * of the mapped node in the unit
	 * 
	 * @param node
	 *            Node to check
	 * @param rule
	 *            Rule to check
	 * @return The mapping color of the mapped node in amalgamation unit.
	 */
	private static int getMappingColorByUnit(final Node node, final Rule rule) {
		int mappingColor = 0;

		return mappingColor;
	}

	/**
	 * @param node
	 * @param formula
	 */
	private static void setMappingColorInAc(Node node, int mappingColor,
			List<Mapping> mappings) {
		Node mappedNode = null;
		for (Mapping mapping : mappings) {
			final Node origin = mapping.getOrigin();
			final Node image = mapping.getImage();

			if (origin == node) {
				mappedNode = mapping.getImage();
			}
			if (image == node) {
				mappedNode = mapping.getOrigin();
			}

			if (mappedNode != null) {
				final NodeLayout mappedNodeLayout = findNodeLayout(mappedNode,
						ModelUtil.getModelRoot(node, LayoutSystem.class));
				if (mappedNodeLayout != null) {
					mappedNodeLayout.setColor(mappingColor);
				} else {
					createNodeLayout(mappedNode, ModelUtil.getModelRoot(
							mappedNode, LayoutSystem.class), mappingColor);
				}

				setMappingColorInAc(mappedNode, mappingColor,
						ModelUtil.getMappings(mappedNode.getGraph()
								.getFormula()));
			} else {
				NodeLayout originLayout = findNodeLayout(origin,
						ModelUtil.getModelRoot(origin, LayoutSystem.class));
				if (originLayout == null) {
					originLayout = createNodeLayout(origin,
							ModelUtil.getModelRoot(origin, LayoutSystem.class));
				}
				if (originLayout.getColor() == 0) {
					final int color = ColorUtil.getNextMappingColor(ModelUtil
							.getRule(origin));
					originLayout.setColor(color);

					NodeLayout imageLayout = findNodeLayout(image,
							ModelUtil.getModelRoot(image, LayoutSystem.class));
					if (imageLayout == null) {
						imageLayout = createNodeLayout(image,
								ModelUtil.getModelRoot(image,
										LayoutSystem.class));
					}
					imageLayout.setColor(color);
				}
			}
		}
	}

	/**
	 * 
	 */
	private static int getMappingColor(Node node, List<Mapping> mappings,
			boolean forceColorCreation) {
		int mappingColor = 0;
		final Node mappedNode = getMappedNode(node, mappings);
		if (mappedNode != null) {
			final NodeLayout mappedNodeLayout = findNodeLayout(mappedNode,
					ModelUtil.getModelRoot(mappedNode, LayoutSystem.class));
			if (mappedNodeLayout != null) {
				mappingColor = mappedNodeLayout.getColor();
			} else {
				if (forceColorCreation) {
					mappingColor = ColorUtil.getNextMappingColor(ModelUtil
							.getRule(node));
					createNodeLayout(mappedNode, ModelUtil.getModelRoot(
							mappedNode, LayoutSystem.class), mappingColor);
				}
			}
		}

		return mappingColor;
	}

	private static Node getMappedNode(Node node, List<Mapping> mappings) {
		for (Mapping mapping : mappings) {
			if (mapping.getOrigin() == node) {
				return mapping.getImage();
			}
			if (mapping.getImage() == node) {
				return mapping.getOrigin();
			}
		}

		return null;
	}

	/**
	 * 
	 */
	private static int getMappingColor(Node node, List<Mapping> mappings) {
		return getMappingColor(node, mappings, true);
	}

}
