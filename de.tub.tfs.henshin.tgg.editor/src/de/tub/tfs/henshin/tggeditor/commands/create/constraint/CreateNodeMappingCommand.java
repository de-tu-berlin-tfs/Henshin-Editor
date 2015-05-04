package de.tub.tfs.henshin.tggeditor.commands.create.constraint;

import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tggeditor.util.SendNotify;

public class CreateNodeMappingCommand extends Command {

	private Node origin;
	private Node image;
	private Mapping mapping;
	
	public CreateNodeMappingCommand(Node node, Mapping mapping) {
		this.origin = node;
		this.image = null;
		this.mapping = mapping;
	}
	
	@Override
	public boolean canUndo() {
		return false;
	}
	
	@Override
	public boolean canExecute() {
		return this.origin != null && this.mapping != null && this.image != null;
	}
	
	@Override
	public void execute() {
		Mapping m = getExistingMapping();
		if (m != null) {
			// delete existing mapping
			((NestedCondition)image.getGraph().eContainer()).getMappings().remove(m);
			SendNotify.sendRemoveMappingNotify(m);
		} else {
			// create mapping
			mapping.setOrigin(origin);
			mapping.setImage(image);
			((NestedCondition)image.getGraph().eContainer()).getMappings().add(mapping);
			SendNotify.sendAddMappingNotify(mapping);
		}
	}
	
	private Mapping getExistingMapping() {
		for (Mapping m : ((NestedCondition)this.origin.getGraph().getFormula()).getMappings()) {
			if (m.getOrigin() == this.origin && m.getImage() == this.image) {
				return m;
			}
		}
		return null;
	}

	public Node getOrigin() {
		return origin;
	}
	
	public void setImage(Node image) {
		this.image = image;
	}
	
	public Node getImage() {
		return image;
	}
	
	public Mapping getMapping() {
		return mapping;
	}
	
}
