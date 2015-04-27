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
/**
 * ParameterFigure.java
 *
 * Created 23.12.2011 - 17:41:13
 */
package de.tub.tfs.henshin.editor.figure.flow_diagram;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.util.ResourceUtil;

/**
 * @author nam
 * 
 */
public class ParameterFigure extends ActivityFigure {

	private int mappingId;

	private boolean highlighted = false;

	public static enum TYPE {
		IN(ResourceUtil.ICONS.INPUT_PARAMETER.img(16)), OUT(
				ResourceUtil.ICONS.OUTPUT_PARAMETER.img(16)), NONE(
				ResourceUtil.ICONS.DUMMY.img(16));

		private Image icon;

		/**
		 * @param icon
		 */
		private TYPE(Image icon) {
			this.icon = icon;
		}

		/**
		 * @return the icon
		 */
		public Image getIcon() {
			return icon;
		}
	}

	private Label typeIcon;

	/**
     * 
     */
	public ParameterFigure() {
		super();

		typeIcon = new Label();

		add(typeIcon, 0);

		setType(TYPE.NONE);

		mappingLabel.setLayoutManager(new FlowLayout());
	}

	/**
	 * @param typeIcon
	 *            the typeIcon to set
	 */
	public void setType(TYPE type) {
		typeIcon.setIcon(type.getIcon());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Shape#paintFigure(org.eclipse.draw2d.Graphics)
	 */
	@Override
	public void paintFigure(Graphics graphics) {
		graphics.setLineWidth(2);

		if (highlighted) {
			graphics.setForegroundColor(ColorConstants.black);
		} else {
			graphics.setForegroundColor(ColorConstants.gray);
		}

		int w = typeIcon.getSize().width;

		graphics.fillRectangle(Rectangle.SINGLETON.setBounds(getBounds())
				.resize(-w, 0).translate(w, 0));

		graphics.drawRectangle(Rectangle.SINGLETON.setBounds(getBounds())
				.resize(-w, 0).translate(w, 0).resize(-2, -2).translate(1, 1));
	}

	/**
	 * @param highlighted
	 *            the highlighted to set
	 */
	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;

		repaint();
	}

	public void setMapExt(int mappingId, boolean in) {
		mappingLabel.removeAll();

		mappingLabel.add(new Label("[" + this.mappingId + "]"));
		mappingLabel.add(new Label(
				in ? ResourceUtil.ICONS.INPUT_PARAMETER.img(16)
						: ResourceUtil.ICONS.OUTPUT_PARAMETER.img(16)));
		mappingLabel.add(new Label("[" + mappingId + "]"));

		if (mappingId >= 0) {
			if (mappingLabel.getParent() != this) {
				add(mappingLabel, 1);
			}
		} else {
			if (mappingLabel.getParent() == this) {
				remove(mappingLabel);
			}
		}

		setCompact(mappingLabel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.figure.flow_diagram.ActivityFigure#setMapping
	 * (int)
	 */
	@Override
	public void setMapping(int mappingId) {
		super.setMapping(mappingId);

		this.mappingId = mappingId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.figure.flow_diagram.ActivityFigure#setName(
	 * java.lang.String)
	 */
	@Override
	public void setName(String name) {
		super.setName(name);

		if (contentIcon.getParent() == this) {
			remove(contentIcon);
		}

		setToolTip("Parameter \"" + name + "\"");
	}
}
