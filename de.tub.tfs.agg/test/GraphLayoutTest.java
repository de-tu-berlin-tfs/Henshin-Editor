

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import org.eclipse.draw2d.geometry.Rectangle;

import agg.util.XMLHelper;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraGra;
import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdNode;
import agg.layout.evolutionary.EvolutionaryGraphLayout;

public class GraphLayoutTest {

	boolean resizing = true;
	Dimension nextSize = new Dimension(0,0);
	
	GraGra basicGrammar;

	EdGraGra grammar;

	String fileName;

	XMLHelper h;

	public GraphLayoutTest() {
	}

	public GraphLayoutTest(String filename) {
		this.fileName = filename;
		System.out.println("File name:  " + this.fileName);
		/* load gragra */
		this.basicGrammar = load(this.fileName);

		if (this.basicGrammar != null) {
			// create EdGraGra instance that allows to use AGG graph layouter
			this.grammar = new EdGraGra(this.basicGrammar);
			makeLayout();

			// save EdGraGra instance
			String outName = "out_" + filename;
			this.grammar.save(outName);
			System.out.println("Grammar saved in:  " + outName);
		} else
			System.out.println("Grammar:  " + filename + "   FAILED!");
	}

	private void makeLayout() {
		EvolutionaryGraphLayout layouter = new EvolutionaryGraphLayout(100, null);
		EdGraph graph = this.grammar.getGraph();
		graph.doDefaultEvolutionaryGraphLayout(layouter, 50, 20);
	}

	
	@SuppressWarnings("unused")
	private void testTreeLayout() {
		EdGraph graph = this.grammar.getGraph();;
        List<Rectangle> figures = new ArrayList<Rectangle>();
        for (EdNode node : graph.getNodes()) {
            EdNode graphNode = node;
            Rectangle newRectangle = new Rectangle(node.getX(), node.getY(),node.getWidth(), node.getHeight());
            figures.add(newRectangle.expand(10,10));
        }
        List<Rectangle> remaning = new ArrayList<Rectangle>(figures);
        Map<Integer, Collection<Integer>> columnIntersects = new HashMap<Integer, Collection<Integer>>();
        Map<Integer, Collection<Integer>> rowIntersects = new HashMap<Integer, Collection<Integer>>();
        for (Rectangle rectangle1 : figures) {
            remaning.remove(rectangle1);
            for (Rectangle rectangle2 : remaning) {
                Rectangle r1 = new Rectangle(rectangle1);
                Rectangle r2 = new Rectangle(rectangle2);
                Rectangle intersect = r1.intersect(r2);

                if (rectangle1.y != rectangle2.y) {
                    if (intersect.height > 0) {
                        Collection<Integer> column = columnIntersects.get(Integer.valueOf(intersect.x));
                        if (column == null) {
                            column = new ArrayList<Integer>();
                            columnIntersects.put(Integer.valueOf(intersect.x), column);
                        }
                        column.add(Integer.valueOf(intersect.height));
                    }
                }
                if (rectangle1.x != rectangle2.x && intersect.width > 0) {
                    Collection<Integer> row = rowIntersects.get(Integer.valueOf(intersect.y));
                    if (row == null) {
                        row = new ArrayList<Integer>();
                        rowIntersects.put(Integer.valueOf(intersect.y), row);
                    }
                    row.add(Integer.valueOf(intersect.width));
                }
            }
        }
        int uniqueColumns = columnIntersects.keySet().size();
        int uniqueRows = rowIntersects.keySet().size();
        int maxOverlapWidth = 0;
        int maxOverlapHeight = 0;
         
        for (Entry<Integer, Collection<Integer>> entry : columnIntersects.entrySet()) {
            int overlapHeight = 0;
            for (Integer columnIntersect : entry.getValue()) {
                if (columnIntersect.intValue() > overlapHeight) {
                    overlapHeight = columnIntersect.intValue();
                }
            }
            overlapHeight = overlapHeight * uniqueRows;
            if (overlapHeight > maxOverlapHeight) {
                maxOverlapHeight = overlapHeight;
            }
        }
        for (Entry<Integer, Collection<Integer>> entry : rowIntersects.entrySet()) {
            int overlapWidth = 0;
            for (Integer rowIntersect : entry.getValue()) {
                if (rowIntersect.intValue() > overlapWidth) {
                    overlapWidth = rowIntersect.intValue();
                }
            }
            overlapWidth = overlapWidth * uniqueColumns;
            if (overlapWidth > maxOverlapWidth) {
                maxOverlapWidth = overlapWidth;
            }
        }
        if (maxOverlapWidth != 0 || maxOverlapHeight != 0) {
            this.nextSize.setSize(new Dimension(this.nextSize.width + maxOverlapWidth, this.nextSize.height + maxOverlapHeight));
            applyLayout();
        } else {
            this.resizing = false;
        }
	}
	
	private void applyLayout() {
		
		
	}
	
	public static void main(String[] args) {
		if (args.length == 1) {
			new GraphLayoutTest(args[0]);
		} else
			System.out.println("Input Grammar   FAILED!");
	}

	public GraGra load(String fName) {
		if (fName.endsWith(".ggx")) {
			this.h = new XMLHelper();
			if (this.h.read_from_xml(fName)) {

				// create a gragra
				GraGra gra = BaseFactory.theFactory().createGraGra();
				this.h.getTopObject(gra);
				gra.setFileName(fName);
				return gra;
			}
			return null;
		} 
		return null;
	}

	// not used here
	public void save(GraGra gra, String outFileName) {
		if (outFileName.endsWith(".ggx")) {
			XMLHelper xmlh = new XMLHelper();
			xmlh.addTopObject(gra);
			xmlh.save_to_xml(outFileName);
		}
	}

}
