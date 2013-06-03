// $Id: HelpText.java,v 1.2 2010/09/23 08:19:42 olga Exp $

package agg.gui.help;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import agg.gui.editor.EditorConstants;

public class HelpText {

	private String helpText = "";

	private JFrame myFrame;

	public HelpText(JFrame fr) {
		this.myFrame = fr;
	}

	public void showDialog() {
		JOptionPane.showMessageDialog(this.myFrame,
				"Please choose menu item to get help.");
	}

	public String getText(int aktChoice) {
		this.helpText = "";
		switch (aktChoice) {

		case EditorConstants.ABOUT:
			this.helpText = "Please choose menu item to get help.";
			break;
		// File menu
		case EditorConstants.QUIT:
			this.helpText = "File / Exit\n" + "   \n" + "Finish this application.";
			break;
		case EditorConstants.GRAGRA:
			this.helpText = "File / New / GraGra\n"
					+ "   \n"
					+ "Create a new graph grammar. \n"
					+ "To delete the graph grammar select ones and press the < DELETE > key\n"
					+ "or choose File /Delete.";
			break;
		case EditorConstants.RULE:
			this.helpText = "File / New / Rule\n"
					+ "   \n"
					+ "Create a new rule for selected graph grammar. \n"
					+ "To delete the rule select ones and press the < DELETE > key\n"
					+ "or choose File /Delete.";
			break;
		case EditorConstants.NAC:
			this.helpText = "File / New / NAC\n"
					+ "   \n"
					+ "Create a new negative application condition (NAC) \n"
					+ "for selected rule.\n"
					+ "To delete the NAC select ones and press the < DELETE > key\n"
					+ "or choose File /Delete.";
			break;
		case EditorConstants.OPEN:
			this.helpText = "File / Open\n" + "   \n" + "Load a graph grammar.";
			break;
		case EditorConstants.SAVE:
			this.helpText = "File / Save\n" + "   \n"
					+ "Save selected graph grammar in a file with name \n"
					+ "as name of graph grammar.";
			break;
		case EditorConstants.SAVE_AS:
			this.helpText = "File / Save As\n" + "   \n"
					+ "Save selected graph grammar in the named file.";
			break;
		case EditorConstants.FILE_DELETE:
			this.helpText = "File / Delete\n" + "   \n"
					+ "The selected GraGra / Rule / NAC will be deleted.";
			break;

		// Edit menu
		case EditorConstants.ATTRS:
			this.helpText = "Edit / Attributes\n"
					+ "   \n"
					+ "An attribute editor will be opened for the selected object.";
			break;
		case EditorConstants.DELETE:
			this.helpText = "Edit / Delete\n" + "   \n"
					+ "The selected objects and all edges that will remain \n"
					+ "dangling without them will be deleted.";
			break;
		case EditorConstants.COPY:
			this.helpText = "Edit / Duplicate\n"
					+ "   \n"
					+ "To duplicate the selected object(s), click with the mouse button\n"
					+ "on the background. The copie(s) ist (are) created on this position\n"
					+ "(with center of copied objects as a whole on this position).";
			break;
		case EditorConstants.SELECT_ALL:
			this.helpText = "Edit / Select All\n" + "   \n" + "Select all objects.";
			break;
		case EditorConstants.DESELECT_ALL:
			this.helpText = "Edit / Deselect All\n" + "   \n"
					+ "All selected objects will be deselected.";
			break;
		case EditorConstants.STRAIGHT:
			this.helpText = "Edit / Straighten Edges\n"
					+ "   \n"
					+ "All selected edges will be straight, the arc is removed.";
			break;
		case EditorConstants.IDENTIC_RULE:
			this.helpText = "Edit / Identic Rule \n"
					+ "   \n"
					+ "An isomorphic rule morphism will be created by copying\n"
					+ "the left graph of the rule to the right side.\n"
					+ "The previous contents of the right side will be deleted.\n"
					+ "If two objects are mapped by the morphism, they are labelled\n"
					+ "with the same number.";
			break;
		case EditorConstants.IDENTIC_NAC:
			this.helpText = "Edit / Identic NAC \n"
					+ "   \n"
					+ "An isomorphic NAC morphism will be created by copying\n"
					+ "the left graph of the rule to the NAC side.\n"
					+ "The previous contents of the NAC side will be deleted.\n"
					+ "If two objects are mapped by the morphism, they are labelled\n"
					+ "with the same number.";
			break;

		// Edit Modes menu
		case EditorConstants.DRAW:
			this.helpText = "Edit Modes / Draw\n"
					+ "   \n"
					+ "A node is created by clicking the left button on the background.\n"
					+ "Nodes are filled with foregroundcolor.\n"
					+ "   \n"
					+ "An edge between two nodes is created by clicking on the source \n"
					+ "and the target of the edge using the mouse button.\n"
					+ "There may be edges only between nodes.\n"
					+ "An edge can contain one arc. The arc can be inserted \n"
					+ "when creating an edge by clicking at the source, \n"
					+ "then at the background, and then at the target \n"
					+ "or\n"
					+ "by grabbing an edge with the (middle) mouse button at the point \n"
					+ "near the middle of the edge and dragging it to the desired point.";

			break;
		case EditorConstants.SELECT:
			this.helpText = "Edit Modes / Select\n"
					+ "   \n"
					+ "Objects are selected by pointing with any mouse button \n"
					+ "at the objects to be selected.\n"
					+ "Selected nodes and edges turn green.\n"
					+ "When a selected object is clicked, it is deselected.\n"
					+ "Selected objects can be moved, duplicated, deleted, \n"
					+ "selected edges with an arc can be straight.";
			break;
		case EditorConstants.MOVE:
			this.helpText = "Edit Modes / Move \n"
					+ "   \n"
					+ "A single node / edge is moved by \"dragging\" with the mouse button:\n"
					+ "Press the mouse button when the cursor points to a node / edge, \n"
					+ "move the pointer and release the button.\n"
					+ "When a node is moved, incident edges are moved accordingly.\n"
					+ "Moving effects a parallel translation of the selected objects.";

			break;
		case EditorConstants.ATTRIBUTES:
			this.helpText = "Edit Modes / Attributes \n"
					+ "   \n"
					+ "An attribute editor will be opened for the picked object.";
			break;
		case EditorConstants.MAP:
			this.helpText = "Edit Modes / Map \n"
					+ "   \n"
					+ "Toggle mode for interactive creation of the \n "
					+ "rule / NAC / match morphism.\n"
					+ "To add a mapping, first click on an object on the left side,\n"
					+ "then click on the object you want to map it to  \n"
					+ "on the right / NAC / graph side.\n"
					+ "If the two objects are type and structure compatible, \n"
					+ "they will be labelled with the same number.\n"
					+ "   \n"
					+ "To delete a mapping, hold down the < DELETE > key while\n"
					+ "clicking on an object on the left side of the rule\n"
					+ "or use  Edit Modes / Unmap";
			break;
		case EditorConstants.UNMAP:
			this.helpText = "Edit Modes / Unmap \n" + "   \n"
					+ "To delete the mapping: \n"
					+ "click on an mapped object on the left side - \n"
					+ "rule / NAC / match morphisms will be deleted,\n"
					+ "or click on an mapped object on the right side - \n"
					+ "rule morphism will be deleted,\n"
					+ "or click on an mapped object on the NAC side - \n"
					+ "NAC morphism will be deleted,\n"
					+ "or click on an mapped object on the graph side - \n"
					+ "match morphism will be deleted.";
			break;
		case EditorConstants.INTERACT_RULE:
			this.helpText = "Edit Modes / Rule Def\n"
					+ "   \n"
					+ "Toggle mode for interactive creation of the rule morphism.\n"
					+ "To add a mapping, first click on an object on the left side,\n"
					+ "then click on the object you want to map it to on the right side.\n"
					+ "If the two objects are type and structure compatible, \n"
					+ "they will be labelled with the same number.\n"
					+ "   \n"
					+ "To delete a mapping, hold down the < DELETE > key while\n"
					+ "clicking on an object on the left side of the rule.";
			break;
		case EditorConstants.INTERACT_NAC:
			this.helpText = "Edit Modes / NAC Def\n"
					+ "   \n"
					+ "Toggle mode for interactive creation of the NAC morphism.\n"
					+ "To add a mapping, first click on an object on the left side,\n"
					+ "then click on the object you want to map it to on the NAC side.\n"
					+ "If the two objects are type and structure compatible, \n"
					+ "they will be labelled with the same number.\n"
					+ "   \n"
					+ "To delete a mapping, hold down the < DELETE > key while\n"
					+ "clicking on an object on the left side of the rule.";
			break;

		// not implemented
		case EditorConstants.INTERFACE_MODE:
			this.helpText = "Interface / Mode\n"
					+ "   \n"
					+ "Open interface mode.\n"
					+ "The next created objects are added to interface part (graph) \n"
					+ "of graph/rule.";
			break;
		case EditorConstants.INTERFACE_SELECT:
			this.helpText = "Interface / Select\n"
					+ "   \n"
					+ "Open interface mode.\n"
					+ "The next selected objects are added to interface part (graph) \n"
					+ "of graph.\n"
					+ "If one object does belong to interface and is selected,\n"
					+ "the next selection delete it from interface graph.";
			break;
		case EditorConstants.INTERFACE_CLOSE:
			this.helpText = "Interface / Close\n" + "   \n"
					+ "The interface mode will be closed.";
			break;

		// Transform menu
		case EditorConstants.PRIORITY:
			this.helpText = "Transform / Priority \n" + "   \n"
					+ "Set priority of transformation thread.\n"
					+ "Max priority is 10, min priority is 1, default - 5.";
			break;
		case EditorConstants.START:
			this.helpText = "Transform / Start \n"
					+ "   \n"
					+ "Perform an inplace graph transformation: \n"
					+ "apply sequentially the rules given by graph gramma - \n"
					+ "each rule so often as possible. \n"
					+ "The host graph is modified to represent the result of the \n"
					+ "apply.";
			break;
		case EditorConstants.STOP:
			this.helpText = "Transform / Stop \n" + "   \n"
					+ "Stop of transformation that was started with Start.";
			break;
		case EditorConstants.OPTIONS:
			this.helpText = "Transform / Options\n" + "   \n"
					+ "Options for the graph transformation.";
			break;
		case EditorConstants.INTERACT_MATCH:
			this.helpText = "Transform / Match Def \n"
					+ "   \n"
					+ "Toggle mode far interactive creation of the match morphism.\n"
					+ "To add a mapping, first click on an object on the left side,\n"
					+ "then click on the object you want to map it to in the work graph.\n"
					+ "You can compute all its completions by subsequently calling\n"
					+ " Transform / Next Completion."
					+ "   \n"
					+ "To delete a mapping, hold down the < DELETE > key while\n"
					+ "clicking on an object on the left side of the rule.";
			break;
		case EditorConstants.NEXT_COMPLETION:
			this.helpText = "Transform / Next Completion\n"
					+ "   \n"
					+ "Find the next match of the current rule into the work graph.\n"
					+ "If you have specified a partial match interactively before,\n"
					+ "you can compute all its completions by subsequently calling\n"
					+ "this command.";
			break;
		case EditorConstants.STEP:
			this.helpText = "Transform / Step\n"
					+ "   \n"
					+ "Perform an inplace graph transformation step: \n"
					+ "apply the rule via match on the host graph.\n"
					+ "The host graph is modified to represent the result of the step.";
			break;

		// Nodes menu
		case EditorConstants.NODE_SHAPE:
			this.helpText = "   \n"
					+ "The shape of the new node(s) will be set to this shape.";
			break;

		// Edges menu
		case EditorConstants.ARC_SHAPE:
			this.helpText = "   \n"
					+ "The shape of the new edge(s) will be set to this shape.";
			break;

		case EditorConstants.COLOR:
			this.helpText = "   \n"
					+ "The color of the new object(s) will be set to this color.";
			break;

		// Node and edge type
		case EditorConstants.TYPE:
			this.helpText = "Node_Type  or  Edge_Type\n" + "   \n"
					+ "The next created object(s) get this type.";
			break;

		case EditorConstants.TYPE_INPUT:
			this.helpText = "Input Node Type / Input Edge Type\n" + "   \n"
					+ "Input the name of the new node(s) or edge(s) type.\n"
					+ "The new created object(s) can get this type.";
			break;

		default:
			this.helpText = "Unexpected choose";
		}

		JOptionPane.showMessageDialog(this.myFrame, this.helpText);
		return this.helpText;
	}

}
