package agg.gui.typeeditor;

import java.awt.Color;
import java.util.List;
import java.util.Vector;
import java.util.Hashtable;
import java.util.Enumeration;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.JComponent;
import javax.swing.undo.*;

import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdNode;
import agg.editor.impl.EdArc;
import agg.editor.impl.EdType;
import agg.editor.impl.EdGraphObject;
import agg.editor.impl.EdTypeSet;
import agg.editor.impl.EditUndoManager;
import agg.xt_basis.TypeSet;
import agg.xt_basis.TypeException;
import agg.gui.editor.GraGraEditor;
import agg.gui.event.TypeEvent;
import agg.gui.event.TypeEventListener;
import agg.util.Pair;

/**
 * A TypeEditor defines an editor for the editing the node and edge types.
 * 
 * @author $Author: olga $
 * @version $version: $
 */
public class TypeEditor implements TypeEventListener, StateEditable {

	/** Creates a type editor */
	public TypeEditor(JFrame aggappl, GraGraEditor gragraEditor) {
		this.applFrame = aggappl;
		this.gragraEditor = gragraEditor;

		this.typePalette = new TypePalette(this.applFrame, this);

		this.nodeTypePropertyEditor = new NodeTypePropertyEditor(this.applFrame, this,
				this.typePalette);
		this.arcTypePropertyEditor = new ArcTypePropertyEditor(this.applFrame, this,
				this.typePalette);

		this.typeEventListeners = new Vector<TypeEventListener>();
	}

	public void setUndoManager(EditUndoManager anUndoManager) {
		this.undoManager = anUndoManager;
		this.nodeTypePropertyEditor.setUndoManager(anUndoManager);
		this.arcTypePropertyEditor.setUndoManager(anUndoManager);
	}

	public UndoManager getUndoManager() {
		return this.undoManager;
	}

	/**
	 * Implements the interface <EM>StateEditable</EM>.
	 */
	public void storeState(Hashtable<Object, Object> state) {
		if (this.undoObj.first != null && this.undoObj.second != null) {
//			System.out.println("TypeEditor.storeState: "+this.undoObj.first);
			
//			String op = this.undoObj.first;
//			if (op.equals(EditUndoManager.COMMON_DELETE_CREATE)) {
//				Vector<?> vec = this.undoObj.second;
//				for (int i = 0; i < vec.size(); i++) {
//					EdGraph g = (EdGraph) vec.get(i);
//					g.storeState(state);
//				}
//			}

			state.put(this, this.undoObj);
		}
	}

	/**
	 * Implements the interface <EM>StateEditable</EM>.
	 */
	public void restoreState(Hashtable<?, ?> state) {
		if (this.undoManager == null)
			return;
		Object obj = state.get(this);
		if (obj == null || !(obj instanceof Pair)) {
			return;
		}
		String op = (String) ((Pair) obj).first;
		if (op.equals(EditUndoManager.COMMON_DELETE_CREATE)) {
			Vector<?> vec = (Vector) ((Pair) obj).second;
			for (int i = vec.size() - 1; i >= 0; i--) {
				EdGraph g = (EdGraph) vec.get(i);
				g.restoreState(state);
			}
		}
	}

	private void undoManagerAddDeleteEdit(
			final Hashtable<EdGraph, Vector<EdGraphObject>> graph2typeObservers,
			final String undoKind) {
		if (this.undoManager == null || !this.undoManager.isEnabled()) {
			return;
		}
		// System.out.println("TypeEditor.undoManagerAddDeleteEdit...");
		final Vector<EdGraph> vec = new Vector<EdGraph>();
		Enumeration<EdGraph> keys = graph2typeObservers.keys();
		while (keys.hasMoreElements()) {
			EdGraph g = keys.nextElement();
			Vector<EdGraphObject> gos = graph2typeObservers.get(g);
			if (gos != null && !gos.isEmpty()) {
				g.addCommonDeletedToUndo(gos);
				if (this.gragra.getTypeGraph() == g)
					vec.add(0, g);
				else
					vec.add(g);
			}
		}
		if (!vec.isEmpty()) {
			this.undoObj = new Pair<String, Vector<?>>(
					EditUndoManager.COMMON_DELETE_CREATE, vec);
		}
	}

	public void setEnabled(boolean b) {
		this.typePalette.setEnabled(b);
	}

	public TypePalette getTypePalette() {
		return this.typePalette;
	}

	public NodeTypePropertyEditor getNodeTypePropertyEditor() {
		return this.nodeTypePropertyEditor;
	}

	public ArcTypePropertyEditor getArcTypePropertyEditor() {
		return this.arcTypePropertyEditor;
	}

	public void showNodeTypePropertyEditorl(int x, int y) {
		this.nodeTypePropertyEditor.invoke(x, y);
	}

	public void showArcTypePropertyEditorl(int x, int y) {
		this.arcTypePropertyEditor.invoke(x, y);
	}

	/**
	 * Sets a gragra specified by the EdGraGra gra. Creates the elements of the
	 * NodeTypeComboBox and ArcTypeComboBox using the typeset of the gragra.
	 */
	public void setGraGra(EdGraGra gra) {
		this.gragra = gra;

		this.typePalette.clear();
		this.nodeTypePropertyEditor.setNewTypeDefaultProperty();
		this.arcTypePropertyEditor.setNewTypeDefaultProperty();

		if (this.gragra == null) {
//			this.typePalette.
			fireTypeEvent(new TypeEvent(this, new JLabel(""), 0, TypeEvent.SELECTED_NODE_TYPE));
			fireTypeEvent(new TypeEvent(this, new JLabel(""), 1, TypeEvent.SELECTED_ARC_TYPE));
			return;
		}

		this.gragra.getTypeSet().addTypeEventListener(this);

		initializeTypes();
	}

	public GraGraEditor getGraGraEditor() {
		return this.gragraEditor;
	}

	public EdGraGra getGraGra() {
		return this.gragra;
	}

	public EdTypeSet getTypeSet() {
		if (this.gragra != null)
			return this.gragra.getTypeSet();
		
		return null;
	}

	public Vector<EdType> getNodeTypes() {
		if (this.gragra != null)
			return this.gragra.getTypeSet().getNodeTypes();
		
		return null;
	}

	public Vector<EdType> getArcTypes() {
		if (this.gragra != null)
			return this.gragra.getTypeSet().getArcTypes();
		
		return null;
	}

	public EdType getSelectedNodeType() {
		return this.gragra.getSelectedNodeType();
	}

	public EdType getSelectedArcType() {
		return this.gragra.getSelectedArcType();
	}

	public EdType selectNodeTypeAtIndex(int index) {
		if (index >= 0) {
			this.gragra.setSelectedNodeType(this.gragra.getNodeTypes()
					.elementAt(index));
						
			fireTypeEvent(new TypeEvent(this, this.typePalette.getSelectedNodeTypeLabel(), 0, TypeEvent.SELECTED));
			
			return this.gragra.getSelectedNodeType();
		} 
		this.gragra.setSelectedNodeType(null);
		return null;
	}

	public EdType selectArcTypeAtIndex(int index) {
		if (index >= 0) {
			this.gragra.setSelectedArcType(this.gragra.getArcTypes().elementAt(
					index));
						
			fireTypeEvent(new TypeEvent(this, this.typePalette.getSelectedArcTypeLabel(), 1, TypeEvent.SELECTED));
			return this.gragra.getSelectedArcType();
		} 
		this.gragra.setSelectedArcType(null);
		return null;
	}

	public int getNodeTypeIndex(EdType t) {
		return this.gragra.getTypeSet().getNodeTypes().indexOf(t);
	}

	public int getArcTypeIndex(EdType t) {
		return this.gragra.getTypeSet().getArcTypes().indexOf(t);
	}

	public void refreshTypes() {
		this.typePalette.clear();
		initializeTypes();
	}

	public EdType addNodeType(EdType et) {
		if (et != null) {
			this.gragra.getTypeSet().addNodeType(et);
			this.gragra.setChanged(true);
			int index = this.gragra.getTypeSet().getNodeTypes().indexOf(et);

			Icon typeIcon = NodeTypePropertyEditor.getNodeTypeIcon(
					et.getShape(), et.getColor(), et.hasFilledShape());
			JLabel l = makeTypeLabel(et.getName(), typeIcon, et.getColor());
			this.typePalette.addNodeType(l, index);

			fireTypeEvent(new TypeEvent(this.nodeTypePropertyEditor, et, index,
					TypeEvent.MODIFIED_CREATED));

			this.typePalette.setSelectedNodeTypeIndex(index);
			this.selectNodeTypeAtIndex(index);
		}
		return et;
	}

	public EdType addNodeType(String tname, Color tcolor, int tshape, boolean filledshape,
			String tresourcespath, String timage, String tcomment, boolean tanimated) {
		
		EdType et = this.gragra.getTypeSet().createNodeType(tname, tshape, tcolor, filledshape, timage);
		if (et != null) {
			this.gragra.setChanged(true);
			
			et.setAnimated(tanimated);
			et.getBasisType().setTextualComment(tcomment);

			int index = this.gragra.getTypeSet().getNodeTypes().indexOf(et);

			Icon typeIcon = NodeTypePropertyEditor.getNodeTypeIcon(tshape,
					tcolor, filledshape);
			JLabel l = this.makeTypeLabel(tname, typeIcon, tcolor);
			this.typePalette.addNodeType(l, index);

			this.typePalette.setSelectedNodeTypeIndex(index);
			this.selectNodeTypeAtIndex(index);

			this.nodeTypePropertyEditor
					.undoManagerAddEdit(EditUndoManager.CREATE_DELETE);
			this.nodeTypePropertyEditor.undoManagerEndEdit(et);
			this.gragraEditor.updateUndoButton();
			fireTypeEvent(new TypeEvent(this.nodeTypePropertyEditor, et, index,
					TypeEvent.MODIFIED_CREATED));
		}
		return et;
	}

	public EdType addArcType(EdType et) {
		if (et != null) {
			this.gragra.getTypeSet().addArcType(et);
			this.gragra.setChanged(true);
			int index = this.gragra.getTypeSet().getArcTypes().indexOf(et);

			Icon typeIcon = this.arcTypePropertyEditor.getArcTypeIcon(et.shape,
					et.color, et.filled);
			JLabel l = makeTypeLabel(et.getName(), typeIcon, et.getColor());
			this.typePalette.addArcType(l, index);

			fireTypeEvent(new TypeEvent(this.arcTypePropertyEditor, et, index,
					TypeEvent.MODIFIED_CREATED));

			this.typePalette.setSelectedArcTypeIndex(index);
			this.selectArcTypeAtIndex(index);
		}
		return et;
	}

	public EdType addArcType(String tname, Color tcolor, int tshape, boolean filledshape,
			String tcomment) {
		EdType et = this.gragra.getTypeSet().createArcType(tname, tshape, tcolor, filledshape);
		if (et != null) {
			this.gragra.setChanged(true);
			et.getBasisType().setTextualComment(tcomment);
			int index = this.gragra.getTypeSet().getArcTypes().indexOf(et);

			Icon typeIcon = this.arcTypePropertyEditor
					.getArcTypeIcon(tshape, tcolor, filledshape);
			JLabel l = makeTypeLabel(tname, typeIcon, tcolor);
			this.typePalette.addArcType(l, index);

			this.typePalette.setSelectedArcTypeIndex(index);
			this.selectArcTypeAtIndex(index);

			this.arcTypePropertyEditor
					.undoManagerAddEdit(EditUndoManager.CREATE_DELETE);
			this.arcTypePropertyEditor.undoManagerEndEdit(et);
			this.gragraEditor.updateUndoButton();
			fireTypeEvent(new TypeEvent(this.arcTypePropertyEditor, et, index,
					TypeEvent.MODIFIED_CREATED));

		}
		return et;
	}

	public boolean changeSelectedNodeType(
			String tname, 
			Color tcolor,
			int tshape, boolean tfilledshape, 
			String tresourcespath, String timage, 
			String tcomment,
			boolean tanimated) {
		
		if (!this.gragra.getTypeSet().isNewType(this.gragra.getTypeSet().getNodeTypes(),
				tname, tshape, tcolor, tfilledshape)) {
			EdType et = getSelectedNodeType();
			if (et.getImageFileName().equals(timage)) {
				if (et.isAnimated() != tanimated) {
					et.setAnimated(tanimated);	
				} 
				return true;
			} 
		}
		
		EdType et = getSelectedNodeType();
		int index = this.typePalette.getSelectedNodeTypeIndex();
		this.nodeTypePropertyEditor.undoManagerAddEdit(EditUndoManager.CHANGE);
		this.gragraEditor.updateUndoButton();

		if (this.gragra.getTypeSet().redefineType(
				et, tname, tshape, tcolor, tfilledshape, timage, tcomment)) {
			
			et.setAnimated(tanimated);
			
			JLabel tlabel = makeNodeTypeLabel(et);
			this.typePalette.changeNodeType(tlabel, index);
			this.nodeTypePropertyEditor.undoManagerEndEdit(et);
			
			fireTypeEvent(new TypeEvent(this.nodeTypePropertyEditor, et, index,
					TypeEvent.MODIFIED_CHANGED));			
			return true;
		} 
		((EditUndoManager) this.nodeTypePropertyEditor.getUndoManager())
				.lastEditDie();
		this.gragraEditor.updateUndoButton();
		return false;
	}

	public boolean changeSelectedArcType(String tname, Color tcolor,
			int tshape, boolean tfilledshape, String tcomment) {
		if (!this.gragra.getTypeSet().isNewType(this.gragra.getTypeSet().getArcTypes(),
				tname, tshape, tcolor, tfilledshape)) {
			return false;
		}
		
		int index = this.typePalette.getSelectedArcTypeIndex();
		EdType et = getSelectedArcType();
		this.arcTypePropertyEditor.undoManagerAddEdit(EditUndoManager.CHANGE);
		this.gragraEditor.updateUndoButton();
		
		if (this.gragra.getTypeSet().redefineType(
				et, tname, tshape, tcolor, tfilledshape, "", tcomment)) {
			
			JLabel tlabel = makeArcTypeLabel(et);
			this.typePalette.changeArcType(tlabel, index);
			this.arcTypePropertyEditor.undoManagerEndEdit(et);
			fireTypeEvent(new TypeEvent(this.arcTypePropertyEditor, et, index,
					TypeEvent.MODIFIED_CHANGED));
			return true;
		} 
		((EditUndoManager) this.nodeTypePropertyEditor.getUndoManager())
				.lastEditDie();
		this.gragraEditor.updateUndoButton();
		return false;
	}

	protected boolean deleteNodeType(EdType et, boolean undoable) {
		int index = this.gragra.getTypeSet().getNodeTypes().indexOf(et);
		if (deleteType(et, index, this.nodeTypePropertyEditor, undoable)) {
			this.typePalette.deleteNodeTypeAt(index);

			int size = this.gragra.getTypeSet().getNodeTypes().size();
			if (size > 0) {
				if (index >= size)
					index = index - 1;

				EdType type = selectNodeTypeAtIndex(index);
				this.typePalette.setSelectedNodeTypeIndex(index);
				this.nodeTypePropertyEditor.setSelectedTypeProperty(type);
				
				fireTypeEvent(new TypeEvent(this, this.typePalette.getSelectedNodeTypeLabel(), 0, TypeEvent.SELECTED_NODE_TYPE));
			} else {
				this.typePalette.setSelectedNodeTypeIndex(-1);
				selectNodeTypeAtIndex(-1);
				this.nodeTypePropertyEditor.setNewTypeDefaultProperty();
				
				fireTypeEvent(new TypeEvent(this, new JLabel(""), 0, TypeEvent.SELECTED_NODE_TYPE));
			}

			if (undoable)
				this.nodeTypePropertyEditor.undoManagerEndEdit(et);
			this.gragraEditor.updateUndoButton();
			
			et.dispose();
			return true;
		}
		return false;
	}

	public boolean deleteSelectedNodeType(boolean undoable) {
		int index = this.typePalette.getSelectedNodeTypeIndex();
		EdType et = this.getSelectedNodeType();
		if (deleteType(et, index, this.nodeTypePropertyEditor, undoable)) {
			this.typePalette.deleteSelectedNodeType();

			int size = this.gragra.getTypeSet().getNodeTypes().size();
			if (size > 0) {
				if (index >= size)
					index = index - 1;

				EdType type = selectNodeTypeAtIndex(index);
				this.typePalette.setSelectedNodeTypeIndex(index);
				this.nodeTypePropertyEditor.setSelectedTypeProperty(type);

				fireTypeEvent(new TypeEvent(this, this.typePalette.getSelectedNodeTypeLabel(), 0, TypeEvent.SELECTED_NODE_TYPE));
			} else {
				this.typePalette.setSelectedNodeTypeIndex(-1);
				selectNodeTypeAtIndex(-1);
				this.nodeTypePropertyEditor.setNewTypeDefaultProperty();
				
				fireTypeEvent(new TypeEvent(this, new JLabel(""), 0, TypeEvent.SELECTED_NODE_TYPE));
			}

			this.nodeTypePropertyEditor.undoManagerEndEdit(et);
			this.gragraEditor.updateUndoButton();
			
			et.dispose();			
			return true;
		}
		return false;
	}

	protected boolean deleteArcType(EdType et, boolean undoable) {
		int index = this.gragra.getTypeSet().getArcTypes().indexOf(et);
		if (deleteType(et, index, this.arcTypePropertyEditor, undoable)) {
			this.typePalette.deleteArcTypeAt(index);

			int size = this.gragra.getTypeSet().getArcTypes().size();
			if (size > 0) {
				if (index >= size)
					index = index - 1;

				EdType type = selectArcTypeAtIndex(index);
				this.typePalette.setSelectedArcTypeIndex(index);
				this.arcTypePropertyEditor.setSelectedTypeProperty(type);
				
				fireTypeEvent(new TypeEvent(this, this.typePalette.getSelectedArcTypeLabel(), 1, TypeEvent.SELECTED_ARC_TYPE));
			} else {
				this.typePalette.setSelectedArcTypeIndex(-1);
				selectArcTypeAtIndex(-1);
				this.arcTypePropertyEditor.setNewTypeDefaultProperty();
				
				fireTypeEvent(new TypeEvent(this, new JLabel(""), 1, TypeEvent.SELECTED_ARC_TYPE));
			}

			if (undoable)
				this.arcTypePropertyEditor.undoManagerEndEdit(et);
			this.gragraEditor.updateUndoButton();
			
			et.dispose();
			return true;
		}
		return false;
	}

	public boolean deleteSelectedArcType(boolean undoable) {
		int index = this.typePalette.getSelectedArcTypeIndex();
		EdType et = this.getSelectedArcType();
		if (deleteType(et, index, this.arcTypePropertyEditor, undoable)) {
			this.typePalette.deleteSelectedEdgeType();

			int size = this.gragra.getTypeSet().getArcTypes().size();
			if (size > 0) {
				if (index >= size)
					index = index - 1;

				EdType type = selectArcTypeAtIndex(index);
				this.typePalette.setSelectedArcTypeIndex(index);
				this.arcTypePropertyEditor.setSelectedTypeProperty(type);
				
				fireTypeEvent(new TypeEvent(this, this.typePalette.getSelectedArcTypeLabel(), 1, TypeEvent.SELECTED_ARC_TYPE));
			} else {
				this.typePalette.setSelectedArcTypeIndex(-1);
				selectArcTypeAtIndex(-1);
				this.arcTypePropertyEditor.setNewTypeDefaultProperty();
				
				fireTypeEvent(new TypeEvent(this, new JLabel(""), 1, TypeEvent.SELECTED_ARC_TYPE));
			}
			this.arcTypePropertyEditor.undoManagerEndEdit(et);
			this.gragraEditor.updateUndoButton();
			
			et.dispose();			
			return true;
		}
		return false;
	}

	private boolean canDeleteNodeType(EdType t) {
		EdGraph typeGraph = this.gragra.getTypeSet().getTypeGraph();
		if (typeGraph != null) {
			Vector<EdNode> vec = typeGraph.getNodes(t);
			if (!vec.isEmpty()) {
				EdNode n = vec.get(0);
				if (typeGraph.containsIncomingOutgoingArcsAt(n))
					return false;
			}
			return true;
		} 
		return true;
	}

	private Hashtable<EdGraph, Vector<EdGraphObject>> getTypeContext(EdType et,
			boolean alsoFromTypeGraph) {
		Hashtable<EdGraph, Vector<EdGraphObject>> table = new Hashtable<EdGraph, Vector<EdGraphObject>>();
		Vector<EdGraphObject> vec = null;
		Vector<EdGraphObject> gos = this.gragra.getGraphObjectsOfType(et,
				alsoFromTypeGraph);
		EdGraph g = null;
		for (int i = 0; i < gos.size(); i++) {
			EdGraphObject go = gos.get(i);
			if (go.getBasisObject() == null
					|| go.getBasisObject().getContext() == null)
				continue;
			if (g != go.getContext()) {
				g = go.getContext();
				vec = table.get(g);
				if (vec == null) {
					vec = new Vector<EdGraphObject>();
					table.put(g, vec);
				}
			}
			if (g != null && vec != null) {
				if (go.isNode()) {
					Vector<EdArc> vIn = g.getIncomingArcs((EdNode) go);
					for (int j = 0; j < vIn.size(); j++) {
						EdArc a = vIn.get(j);
						if (!vec.contains(a))
							vec.add(a);
					}
					Vector<EdArc> vOut = g.getOutgoingArcs((EdNode) go);
					for (int j = 0; j < vOut.size(); j++) {
						EdArc a = vOut.get(j);
						if (!vec.contains(a))
							vec.add(a);
					}
					vec.add(0, go);
				} else if (!vec.contains(go))
					vec.add(go);
			}
		}
		return table;
	}

	protected boolean deleteType(EdType et, int index, JComponent source,
			boolean undoable) {
		
		final Hashtable<EdGraph, Vector<EdGraphObject>>
		graph2typeObservers = getTypeContext(et, true);

		EdTypeSet typeSet = this.gragra.getTypeSet();
		int answer = JOptionPane.NO_OPTION;
		boolean used = false;
		if (typeSet.isTypeUsed(et)) {
			used = true;
			if (undoable) {
				if (typeSet.getBasisTypeSet().getLevelOfTypeGraphCheck() != TypeSet.DISABLED) {
					JOptionPane
							.showMessageDialog(
									this.applFrame,
									"Please disable the type graph before delete a type.",
									"Type graph enabled",
									JOptionPane.ERROR_MESSAGE);
					return false;
				}
				Object[] options = { "Delete", "Cancel" };
				answer = JOptionPane.showOptionDialog(source,
						"Are you sure you want to delete the type:  <"
								+ et.getName() + "> ?"
								+ "\nThere are objects of it.", 
								"Delete Type",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				
				if (answer == JOptionPane.YES_OPTION) {
					String failStr = this.gragra.kernelRuleContainsObjsOfType(et);
					if (failStr != null) {
						String str = "The kernel rule:  "+failStr+"  \n"
							+"contains objects of type :  <"
							+et.getName()
							+">  to delete.\n"
							+"Currently, AGG does not support Undo/Redo in this case.\n\n"
							+"Do you want to delete this type anyway?";
						answer = JOptionPane.showConfirmDialog(this.applFrame, str, 
								"Delete Type", JOptionPane.WARNING_MESSAGE);
					}
					undoable = (failStr == null);
				}
			} else {
				answer = JOptionPane.YES_OPTION;
			}
			if (answer == JOptionPane.YES_OPTION) {
				if (!canDeleteNodeType(et)) {
					JOptionPane
							.showMessageDialog(
									this.applFrame,
									"Cannot delete the node type:  <"
											+ et.getName()
											+ ">"
											+ "\n Please delete its incoming | outgoing edge types, first.",
									"Type Graph Error",
									JOptionPane.ERROR_MESSAGE);
					return false;
				}

				if (undoable) {
					// store all objects of type to delete to be able to undo
					// deleted type with its objects
					undoManagerAddDeleteEdit(graph2typeObservers,
							EditUndoManager.COMMON_DELETE_CREATE);
				}
				
				List<String> 
				failed = this.gragra.deleteGraphObjectsOfType(et, true, false);
				showDeleteMessageDialog(failed);
				if (failed != null && !failed.isEmpty()) {
					return false;
				}
			} else {
				return false;
			}
		}
		if (!used || (answer == JOptionPane.YES_OPTION)) {
			try {
				if (undoable && source == this.nodeTypePropertyEditor) {
					if (!graph2typeObservers.isEmpty())
						this.nodeTypePropertyEditor
								.undoManagerAddEdit(EditUndoManager.COMMON_DELETE_CREATE);
					else
						this.nodeTypePropertyEditor
								.undoManagerAddEdit(EditUndoManager.DELETE_CREATE);
				} else if (undoable && source == this.arcTypePropertyEditor) {
					if (!graph2typeObservers.isEmpty())
						this.arcTypePropertyEditor
								.undoManagerAddEdit(EditUndoManager.COMMON_DELETE_CREATE);
					else
						this.arcTypePropertyEditor
								.undoManagerAddEdit(EditUndoManager.DELETE_CREATE);
				}
				
				this.gragra.setChanged(true);
				fireTypeEvent(new TypeEvent(source, et, index,
						TypeEvent.MODIFIED_DELETED));
				
				typeSet.removeType(et);
				
			} catch (TypeException e) {				
				JOptionPane.showMessageDialog(this.applFrame, e.getMessage(),
						"Type Graph Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	private void showDeleteMessageDialog(List<String> failed) {
		if (failed != null && !failed.isEmpty()) {
			String str = "Cannot delete objects of this type from :\n";
			for (int i = 0; i < failed.size(); i++) {
				String s = "\t" + failed.get(i) + "\n";
				str = str + s;
			}
			JOptionPane.showMessageDialog(this.applFrame, str);
		}
	}

	private void initializeTypes() {
		EdType et = null;
		/* fill nodeTypeList */
		Vector<JLabel> nodeTypeList = new Vector<JLabel>(this.gragra.getNodeTypes()
				.size(), 5);
		if (this.gragra.getNodeTypes().size() != 0) {
			for (int i = 0; i < this.gragra.getNodeTypes().size(); i++) {
				et = this.gragra.getNodeTypes().elementAt(i);
				JLabel tlabel = makeNodeTypeLabel(et);
				nodeTypeList.add(tlabel);
			}
			int indx = nodeTypeList.size() - 1;
			et = this.gragra.getNodeTypes().elementAt(indx);
			this.gragra.setSelectedNodeType(et);
			this.nodeTypePropertyEditor.setSelectedTypeProperty(et.getName(),
					et.color, et.shape, et.filled, "", 
					et.imageFileName, 
					et.getBasisType().getTextualComment(),
					et.isAnimated());			
		}

		/* fill arcTypeBox */
		Vector<JLabel> arcTypeList = new Vector<JLabel>(this.gragra.getArcTypes()
				.size(), 5);
		if (this.gragra.getArcTypes().size() != 0) {
			for (int i = 0; i < this.gragra.getArcTypes().size(); i++) {
				et = this.gragra.getArcTypes().elementAt(i);
				JLabel tlabel = makeArcTypeLabel(et);
				arcTypeList.add(tlabel);
			}
			int indx = arcTypeList.size() - 1;
			et = this.gragra.getArcTypes().elementAt(indx);
			this.gragra.setSelectedArcType(et);
			this.arcTypePropertyEditor.setSelectedTypeProperty(et.getName(),
					et.color, et.shape, et.filled, et.getBasisType().getTextualComment());
		}

		// initialize this.typePalette
		this.typePalette.setTypes(nodeTypeList, arcTypeList);
		this.typePalette.setSelectedNodeTypeIndex(nodeTypeList.size() - 1);
		this.typePalette.setSelectedArcTypeIndex(arcTypeList.size() - 1);
		
		if (nodeTypeList.isEmpty())
			fireTypeEvent(new TypeEvent(this, new JLabel(""), 0, TypeEvent.SELECTED_NODE_TYPE));
		else
			fireTypeEvent(new TypeEvent(this, this.typePalette.getSelectedNodeTypeLabel(), 0, TypeEvent.SELECTED_NODE_TYPE));
			
		if (arcTypeList.isEmpty())
			fireTypeEvent(new TypeEvent(this, new JLabel(""), 1, TypeEvent.SELECTED_ARC_TYPE));
		else
			fireTypeEvent(new TypeEvent(this, this.typePalette.getSelectedArcTypeLabel(), 1, TypeEvent.SELECTED_ARC_TYPE));
	}

	public JLabel makeNodeTypeLabel(EdType t) {
		Icon icon = NodeTypePropertyEditor.getNodeTypeIcon(t.shape, t.color, t.filled);
		JLabel l = new JLabel(t.getName(), icon, SwingConstants.LEFT);
		l.setForeground(t.getColor());
		return l;
	}

	public JLabel makeArcTypeLabel(EdType t) {
		Icon icon = this.arcTypePropertyEditor.getArcTypeIcon(t.shape, t.color, t.filled);
		return this.makeTypeLabel(t.name, icon, t.color);
	}

	public JLabel makeTypeLabel(String name, Icon icon, Color color) {
		final JLabel l = new JLabel(name, icon, SwingConstants.LEFT);
		l.setForeground(color);
		return l;
	}

	/** Adds a new type event listener. */
	public synchronized void addTypeEventListener(TypeEventListener l) {
		if (!this.typeEventListeners.contains(l))
			this.typeEventListeners.addElement(l);
	}

	/** Removes the type event listener. */
	public synchronized void removeTypeEventListener(TypeEventListener l) {
		if (this.typeEventListeners.contains(l)) {
			this.typeEventListeners.removeElement(l);
		}
	}

	/** Sends a type event to the all my listeners. */
	public void fireTypeEvent(TypeEvent e) {
		for (int i = 0; i < this.typeEventListeners.size(); i++) {
			this.typeEventListeners.elementAt(i).typeEventOccurred(e);
		}
	}

	/** Removes my types, sets my gragra at null. */
	public void removeAll() {
		this.typePalette.clear();
		this.nodeTypePropertyEditor.setNewTypeDefaultProperty();
		this.arcTypePropertyEditor.setNewTypeDefaultProperty();
		this.gragra = null;
	}


	/**
	 * TypeEvent occurred
	 */
	public void typeEventOccurred(TypeEvent e) {
		if (e.getSource() instanceof EdTypeSet) {
			if (e.getMsg() == TypeEvent.CHANGED
					|| e.getMsg() == TypeEvent.REFRESH) {
				// initializeTypes();

				refreshTypes();
			}
		}
	}
	
	
	private final JFrame applFrame;
	
	private final GraGraEditor gragraEditor;

	private EdGraGra gragra;

	private final Vector<TypeEventListener> typeEventListeners;

	private final TypePalette typePalette;

	private final NodeTypePropertyEditor nodeTypePropertyEditor;

	private final ArcTypePropertyEditor arcTypePropertyEditor;

	private EditUndoManager undoManager;

	private Pair<String, Vector<?>> undoObj;
}
