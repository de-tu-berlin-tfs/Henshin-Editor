package de.tub.tfs.henshin.editor.commands.transformation_unit;

import java.util.Map;

import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.Command;

/**
 * The Class ExecuteTransformationUnitCommand.
 * 
 * @author Johann, nam
 */
public class ExecuteTransformationUnitCommand extends Command {

	/** The graph. */
	private Graph graph;

	/** The transformation unit. */
	private Unit transformationUnit;

	/** The assignments. */
	private Map<String, Object> assignments;

	/** The unit application. */
	private UnitApplication unitApplication;

	/** The henshin graph. */
//	private HenshinGraph henshinGraph;

	/**
	 * Instantiates a new execute transformation unit command.
	 * 
	 * @param graph
	 *            the graph
	 * @param transformationUnit
	 *            the transformation unit
	 * @param assignments
	 *            the assignments
	 */
	public ExecuteTransformationUnitCommand(Graph graph,
			Unit transformationUnit,
			Map<String, Object> assignments) {
		super();
		this.graph = graph;
		this.transformationUnit = transformationUnit;
		this.assignments = assignments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return graph != null && transformationUnit != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
//	@Override
//	public void execute() {
//		final List<Node> alteNodes = new ArrayList<Node>(graph.getNodes());
//		henshinGraph = new HenshinGraph(graph);
//		EmfEngine emfEngine = new EmfEngine(henshinGraph);
//		emfEngine.getOptions().setInjective(true);
//		unitApplication = new UnitApplication(emfEngine, transformationUnit);
//		if (assignments != null) {
//			for (String parameter : assignments.keySet()) {
//				unitApplication.setParameterValue(parameter,
//						assignments.get(parameter));
//			}
//		}
//		try {
//
//			graph.eSetDeliver(false);
//			for (Node node : graph.getNodes()) {
//				node.eSetDeliver(false);
//				for (Attribute attr : node.getAttributes()) {
//					attr.eSetDeliver(false);
//				}
//
//			}
//			for (Edge edge : graph.getEdges()) {
//				edge.eSetDeliver(false);
//			}
//			Job j = new Job("Execution of " + transformationUnit.getName()
//					+ " on Graph " + graph.getName()) {
//				@SuppressWarnings("deprecation")
//				@Override
//				protected void canceling() {
//					this.getThread().stop(new Exception("Execution canceled!"));
//
//					super.canceling();
//				}
//
//				@Override
//				protected IStatus run(IProgressMonitor monitor) {
//					try {
//						monitor.beginTask(
//								"Execute unit " + transformationUnit.getName()
//										+ " on Graph " + graph.getName(), -1);
//						if (!unitApplication.execute()) {
//							Display.getDefault().asyncExec(new Runnable() {
//
//								@Override
//								public void run() {
//									MessageDialog
//											.openError(null, "Execute Error",
//													"Transformation unit couldn't be executed.");
//								}
//							});
//							// MessageDialog.openError(null,
//							// "Execute Error",
//							// "Transformation unit couldn't be executed.");
//						} else {
//							// ----
//							List<Node> newNodes = new ArrayList<Node>(
//									graph.getNodes());
//							newNodes.removeAll(alteNodes);
//							// ------
//							createNodeLayouts(newNodes, alteNodes);
//						}
//					} catch (Exception ex) {
//						monitor.beginTask(
//								"Canceling execution and undoing changes.", -1);
//						try {
//							unitApplication.undo();
//						} catch (Exception ex2) {
//
//						}
//						monitor.done();
//						return Status.CANCEL_STATUS;
//					}
//					monitor.done();
//					return Status.OK_STATUS;
//				}
//			};
//
//			PlatformUI.getWorkbench().getProgressService()
//					.showInDialog(Display.getDefault().getActiveShell(), j);
//			j.setPriority(Job.LONG);
//			j.schedule();
//			int priority = 0;
//			Thread t = j.getThread();
//			if (t != null) {
//				priority = t.getPriority();
//				t.setPriority(Thread.MIN_PRIORITY);
//			}
//			Display d = Display.getDefault();
//			while (j.getResult() == null) {
//				if (!d.readAndDispatch())
//					;
//				d.sleep();
//
//			}
//
//			graph.eSetDeliver(true);
//			for (Node node : graph.getNodes()) {
//				node.eSetDeliver(true);
//				for (Attribute attr : node.getAttributes()) {
//					attr.eSetDeliver(true);
//				}
//			}
//			for (Edge edge : graph.getEdges()) {
//				edge.eSetDeliver(true);
//
//			}
//			NotificationImpl n = new org.eclipse.emf.common.notify.impl.NotificationImpl(
//					Notification.ADD, false, false) {
//				@Override
//				public Object getNotifier() {
//					// TODO Auto-generated method stub
//					return graph;
//				}
//
//			};
//
//			graph.eNotify(n);
//			for (Edge edge : graph.getEdges()) {
//				edge.eNotify(n);
//			}
//			for (Node node : graph.getNodes()) {
//				node.eNotify(n);
//				for (Attribute attr : node.getAttributes()) {
//					attr.eNotify(n);
//				}
//			}
//
//			if (t != null)
//				t.setPriority(priority);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		unitApplication.undo(null);
	}

	/**
	 * Creates the node layouts.
	 * 
	 * @param newNodes
	 *            the new nodes
	 * @param oldNodes
	 *            the old nodes
	 */
//	private void createNodeLayouts(List<Node> newNodes, List<Node> oldNodes) {
//		for (Node n : newNodes) {
//			EObject o = henshinGraph.getNode2eObjectMap().get(n);
//			Node rhsNode = null;
//
//			for (RuleApplication rApplication : unitApplication
//					.getAppliedRules()) {
//				for (Entry<Node, EObject> entry : rApplication.getComatch()
//						.getNodeMapping().entrySet()) {
//					if (entry.getValue() == o) {
//						rhsNode = entry.getKey();
//					}
//				}
//			}
//
//			int r = new Random(System.currentTimeMillis()).nextInt(100) + 1;
//			int x = r;
//			int y = r;
//
//			if (rhsNode != null) {
//				NodeLayout rhsNodeLayout = HenshinLayoutUtil.INSTANCE
//						.getLayout(rhsNode);
//
//				x += rhsNodeLayout.getX();
//				y += rhsNodeLayout.getY();
//			}
//
//			(new CreateNodeLayoutCommand(n, x, y)).execute();
//		}
//	}

	/**
	 * Gets the unit application.
	 * 
	 * @return the unitApplication
	 */
	public synchronized UnitApplication getUnitApplication() {
		return unitApplication;
	}

}
