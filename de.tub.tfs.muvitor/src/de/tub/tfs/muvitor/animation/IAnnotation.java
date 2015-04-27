/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
package de.tub.tfs.muvitor.animation;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;

/**
 * @author Tony Modica
 * 
 */
public interface IAnnotation {
	
	static public class LabelAnnotation extends Stub {
		
		public LabelAnnotation(final String text, final boolean isStatic) {
			this(text, new Point(50, 50), isStatic);
		}
		
		public LabelAnnotation(final String text, final Point offset,
				final AbstractConnectionAnchor targetAnchor, final boolean isStatic) {
			super(new FlowPage(), offset, new ChopboxAnchor(null), targetAnchor, isStatic);
			getFigure().add(new TextFlow(text));
			getConnection().setLineStyle(Graphics.LINE_DASHDOTDOT);
			getConnection().setTargetDecoration(new PolylineDecoration());
		}
		
		public LabelAnnotation(final String text, final Point offset, final boolean isStatic) {
			this(text, offset, new ChopboxAnchor(null), isStatic);
		}
	}
	
	static public class Stub implements IAnnotation {
		
		final private PolylineConnection connection = new PolylineConnection();
		
		final private IFigure figure;
		
		final private boolean isStatic;
		
		final private Point offset;
		
		/**
		 * @param figure
		 * @param offset
		 * @param targetAnchor
		 * @param isStatic
		 */
		public Stub(final IFigure figure, final Point offset,
				final AbstractConnectionAnchor sourceAnchor,
				final AbstractConnectionAnchor targetAnchor, final boolean isStatic) {
			this.figure = figure;
			this.offset = offset;
			this.isStatic = isStatic;
			sourceAnchor.setOwner(figure);
			connection.setSourceAnchor(sourceAnchor);
			connection.setTargetAnchor(targetAnchor);
		}
		
		@Override
		public void annotate(final IFigure container, final IFigure target) {
			((AbstractConnectionAnchor) connection.getTargetAnchor()).setOwner(target);
			container.add(figure);
			container.add(connection);
		}
		
		@Override
		public void deannotate() {
			final IFigure container = figure.getParent();
			container.remove(connection);
			container.remove(figure);
			((AbstractConnectionAnchor) connection.getTargetAnchor()).setOwner(null);
		}
		
		/*
		 * (non-Javadoc)
		 * @see muvitorkit.animation.IAnnotation#getConnection()
		 */
		@Override
		public PolylineConnection getConnection() {
			return connection;
		}
		
		/*
		 * (non-Javadoc)
		 * @see muvitorkit.animation.IAnnotation#getFigure()
		 */
		@Override
		public IFigure getFigure() {
			return figure;
		}
		
		/*
		 * (non-Javadoc)
		 * @see muvitorkit.animation.IAnnotation#getOffset()
		 */
		@Override
		public Point getOffset() {
			return offset;
		}
		
		/*
		 * (non-Javadoc)
		 * @see muvitorkit.animation.IAnnotation#isStatic()
		 */
		@Override
		public boolean isStatic() {
			return isStatic;
		}
	}
	
	/**
	 * @param container
	 * @param target
	 */
	public void annotate(IFigure container, IFigure target);
	
	/**
	 *
	 */
	public void deannotate();
	
	/**
	 * @return
	 */
	public PolylineConnection getConnection();
	
	/**
	 * @return
	 */
	public IFigure getFigure();
	
	/**
	 * @return
	 */
	public Point getOffset();
	
	/**
	 * @return
	 */
	public boolean isStatic();
}
