package tggeditor.tools;

public class MappingCreationTool extends
		de.tub.tfs.muvitor.gef.palette.MappingCreationTool {
	
	@Override
	protected boolean handleCreateConnection() {
		//getCurrentViewer().getFocusEditPart().getParent().getViewer().deselectAll();
		//getCurrentViewer().deselectAll();
		getCurrentViewer().select(getCurrentViewer().getFocusEditPart());
		
		return super.handleCreateConnection();
	}

}
