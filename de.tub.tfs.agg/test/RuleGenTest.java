

import java.util.Observable;
import java.util.Observer;

import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.gui.AGGAppl;
import agg.gui.event.TreeViewEvent;
import agg.gui.event.TreeViewEventListener;
import agg.gui.treeview.GraGraTreeView;
import agg.gui.treeview.nodedata.GraGraTreeNodeData;
import agg.util.Change;
import agg.xt_basis.Arc;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.GraGra;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.TypeException;
import agg.xt_basis.TypeGraph;

public class RuleGenTest implements TreeViewEventListener, Observer {

	static RuleGenTest thisInstance;

	static AGGAppl aggAppl;

	static GraGraTreeView treeView;

	GraGra grammar;

	TypeGraph typeGraph;

	GraGraTreeNodeData treeNodeData;

	public RuleGenTest() {
		thisInstance = this;
	}

	public static void main(String[] args) {
		new RuleGenTest();

		aggAppl = new AGGAppl();
		aggAppl.showApplication(args);

		treeView = aggAppl.getGraGraTreeView();
		treeView.addTreeViewEventListener(thisInstance);
	}

	/** Implements TreeViewEventListener.treeViewEventOccurred */
	public void treeViewEventOccurred(TreeViewEvent e) {
		if (e.getMsg() == TreeViewEvent.SELECTED) {
			this.treeNodeData = e.getData();
			if (this.treeNodeData.isTypeGraph()) {
				this.typeGraph = (TypeGraph) this.treeNodeData.getGraph().getBasisGraph();
				this.typeGraph.addObserver(this);
				this.grammar = this.treeNodeData.getGraph().getGraGra().getBasisGraGra();
			}
		}
	}

	public final void update(Observable o, Object arg) {
		if (arg instanceof Change) {
			GraphObject go = null;
			Change ch = (Change) arg;
			if (ch.getEvent() == Change.OBJECT_CREATED) {
				if (ch.getItem() instanceof Node) {
					go = (Node) ch.getItem();
					newCreationRule((Node) go);
					newDeletionRule((Node) go);

					treeView.synchronizeGraGraRuleView(this.grammar);
				} else if (ch.getItem() instanceof Arc) {
					go = (Arc) ch.getItem();
					newCreationRule((Arc) go);
					newDeletionRule((Arc) go);

					treeView.synchronizeGraGraRuleView(this.grammar);
				}
			} else if (ch.getEvent() == Change.OBJECT_DESTROYED) {
				if (ch.getItem() instanceof Node) {
					go = (Node) ch.getItem();
					// remove creation/deletion rule of this node type

					treeView.synchronizeGraGraRuleView(this.grammar);
				} else if (ch.getItem() instanceof Arc) {
					go = (Arc) ch.getItem();
					// remove creation/deletion rule of this edge type

					treeView.synchronizeGraGraRuleView(this.grammar);
				}
			}
		}
	}

	private Rule newCreationRule(Node obj) {
		Rule r = this.grammar.createRule();
		r.setName("createNode_" + obj.getType().getName());
		try {
			Node n = r.getRight().createNode(obj.getType());
			// set, if needed, attribute value in case of attributed graphs
			if (n.getAttribute() != null) {
				ValueTuple vt = (ValueTuple) n.getAttribute();
				for (int i = 0; i < vt.getNumberOfEntries(); i++) {
					ValueMember vm = vt.getValueMemberAt(i);
					vm.setExprAsText("XXX");
					vm.checkValidity();
					if (vm.getValidityReport() != null
							&& !vm.getValidityReport().equals("")) {
						System.out.println(vm.getValidityReport());
						vm.setExpr(null);
					}
				}
			}
		} catch (TypeException ex) {
		}
		return null;
	}

	private Rule newCreationRule(Arc obj) {
		Rule r = this.grammar.createRule();
		r.setName("createEdge_" + obj.getType().getName());
		try {
			Node srcL = r.getLeft().createNode(obj.getSourceType());
			Node tarL = r.getLeft().createNode(obj.getTargetType());
			Node srcR = r.getRight().createNode(obj.getSourceType());
			Node tarR = r.getRight().createNode(obj.getTargetType());
			Arc a = r.getRight().createArc(obj.getType(), srcR, tarR);
			try {
				r.addMapping(srcL, srcR);
				r.addMapping(tarL, tarR);
			} catch (BadMappingException mex) {
			}

			// set, if needed, attribute value in case of attributed graphs
			if (a.getAttribute() != null) {
				ValueTuple vt = (ValueTuple) a.getAttribute();
				for (int i = 0; i < vt.getNumberOfEntries(); i++) {
					ValueMember vm = vt.getValueMemberAt(i);
					vm.setExprAsText("XXX");
					vm.checkValidity();
					if (vm.getValidityReport() != null
							&& !vm.getValidityReport().equals("")) {
						System.out.println(vm.getValidityReport());
						vm.setExpr(null);
					}
				}
			}
		} catch (TypeException tex) {
		}
		return null;
	}

	private Rule newDeletionRule(Node obj) {
		Rule r = this.grammar.createRule();
		r.setName("deleteNode_" + obj.getType().getName());
		OrdinaryMorphism nac = r.createNAC();
		try {
			Node nL = r.getLeft().createNode(obj.getType());
			Node nN = nac.getTarget().createNode(obj.getType());
//			Node n1N = 
			nac.getTarget().createNode(obj.getType());
			try {
				nac.addMapping(nL, nN);
			} catch (BadMappingException mex) {
			}
		} catch (TypeException ex) {
		}
		return null;
	}

	private Rule newDeletionRule(Arc obj) {
		Rule r = this.grammar.createRule();
		r.setName("deleteEdge_" + obj.getType().getName());
		try {
			Node srcL = r.getLeft().createNode(obj.getSourceType());
			Node tarL = r.getLeft().createNode(obj.getTargetType());
			Node srcR = r.getRight().createNode(obj.getSourceType());
			Node tarR = r.getRight().createNode(obj.getTargetType());
//			Arc a = 
			r.getLeft().createArc(obj.getType(), srcL, tarL);
			try {
				r.addMapping(srcL, srcR);
				r.addMapping(tarL, tarR);
			} catch (BadMappingException mex) {
			}
		} catch (TypeException tex) {
		}
		return null;
	}

}
