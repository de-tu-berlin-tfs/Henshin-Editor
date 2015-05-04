package de.tub.tfs.henshin.tggeditor.editparts.tree.constraint;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.model.Constraint;

import de.tub.tfs.henshin.tgg.TGG;

public class ConstraintFolder extends EObjectImpl {
	
	private TGG tgg;
	private List<Constraint> constraints;
	
	public ConstraintFolder(TGG tgg) {
		this.tgg = tgg;
		constraints = tgg.getConstraints();
	}
	
	public void update() {
		constraints = tgg.getConstraints();
		eNotify(new ENotificationImpl(this, Notification.ADD, 0, null, null));
	}

	public TGG getTgg() {
		return tgg;
	}
	
	public List<Constraint> getConstraints() {
		return constraints;
	}
	
}
