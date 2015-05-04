package de.tub.tfs.henshin.tggeditor.util.dialogs.constraint;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.interpreter.impl.NodeTypes;
import de.tub.tfs.henshin.tggeditor.util.dialogs.DialogUtil;
import de.tub.tfs.henshin.tggeditor.util.dialogs.SingleElementListSelectionDialog;

public class PlainGraphDialogUtil extends DialogUtil {

	public static EClass runPlainNodeCreationDialog(Shell shell, de.tub.tfs.henshin.tggeditor.commands.create.constraint.CreateNodeCommand c) {
		TGG tgg = c.getTgg();	
		List<EPackage> epackages = getPackages(tgg, c.getNodeTripleComponent());	
		List<EClass> nodeTypes = NodeTypes.getNodeTypesOfEPackages(epackages, false);
		switch (nodeTypes.size()) {
		case 0:
			MessageDialog.openError(shell, "Node Creation Error", "There are no " + c.getNodeTripleComponent() + " model packages imported yet!");
			return null;
		case 1:
			return nodeTypes.get(0);
		default:
			return new SingleElementListSelectionDialog<EClass>(shell,
					new LabelProvider() {
						@Override
						public String getText(Object element) {
							return ((EClass) element).getName();
						}
					}, nodeTypes.toArray(new EClass[nodeTypes.size()]),
					"Node Type Selection",
					"Select a type for the new node:").run();
		}
	}
	
}
