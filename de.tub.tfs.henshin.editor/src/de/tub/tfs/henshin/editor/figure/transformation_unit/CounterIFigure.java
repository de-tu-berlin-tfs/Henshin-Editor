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
 * 
 */
package de.tub.tfs.henshin.editor.figure.transformation_unit;

import java.util.Iterator;

import org.eclipse.draw2d.ActionEvent;
import org.eclipse.draw2d.ActionListener;
import org.eclipse.draw2d.Button;
import org.eclipse.draw2d.ChangeEvent;
import org.eclipse.draw2d.ChangeListener;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

/**
 * The Class CounterIFigure.
 * 
 * @author Johann Schmidt
 * 
 *         The Class CounterIFigure.
 */
public class CounterIFigure extends Panel {

	/** The decrement. */
	private Button decrement;

	/** The increment. */
	private Button increment;

	/** The counter. */
	private Label counter;

	/** The value. */
	private Integer value;

	/** The decrement container. */
	private Rectangle decrementContainer;

	/** The increment container. */
	private Rectangle incrementContainer;

	/** The counter container. */
	private Rectangle counterContainer;

	/**
	 * Instantiates a new counter i figure.
	 * 
	 * @param startValue
	 *            the start value
	 */
	public CounterIFigure(Integer startValue) {
		super();
		setLayoutManager(new XYLayout());
		this.value = startValue;
		decrement = new Button("<");
		decrement.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				if (value > 0) {
					value--;
					if (value == 0) {
						value = -1;
					}
					counter.setText(value.toString());
					fireStateChanged();
				}

			}
		});
		decrementContainer = new Rectangle(0, 0, 15, 20);
		add(decrement, decrementContainer);

		counter = new Label(value.toString());
		counterContainer = new Rectangle(15, 0, 30, 20);
		add(counter, counterContainer);

		increment = new Button(">");
		increment.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				value++;
				if (value == 0) {
					value = 1;
				}
				counter.setText(value.toString());
				fireStateChanged();
			}
		});

		incrementContainer = new Rectangle(45, 0, 15, 20);
		add(increment, incrementContainer);

		setSize(60, 20);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#setSize(int, int)
	 */
	@Override
	public void setSize(int w, int h) {
		super.setSize(w, h);
		counterContainer.setSize(w - 30, h);
		incrementContainer.setLocation(w - 15, 0);
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public synchronized Integer getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            the new value
	 */
	public synchronized void setValue(Integer value) {
		this.value = value;
		counter.setText(value.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.Figure#setBackgroundColor(org.eclipse.swt.graphics
	 * .Color)
	 */
	@Override
	public void setBackgroundColor(Color bg) {
		decrement.setBackgroundColor(bg);
		increment.setBackgroundColor(bg);
		counter.setBackgroundColor(bg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.Figure#setForegroundColor(org.eclipse.swt.graphics
	 * .Color)
	 */
	@Override
	public void setForegroundColor(Color fg) {
		decrement.setForegroundColor(fg);
		increment.setForegroundColor(fg);
		counter.setForegroundColor(fg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean value) {
		decrement.setEnabled(value);
		increment.setEnabled(value);
		counter.setEnabled(value);
		super.setEnabled(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#setFont(org.eclipse.swt.graphics.Font)
	 */
	@Override
	public void setFont(Font f) {
		decrement.setFont(f);
		increment.setFont(f);
		counter.setFont(f);
	}

	/**
	 * Adds the change listener.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addChangeListener(ChangeListener listener) {
		addListener(ChangeListener.class, listener);
	}

	/**
	 * Fire state changed.
	 */
	protected void fireStateChanged() {
		ChangeEvent change = new ChangeEvent(this, "Value");
		Iterator<?> listeners = getListeners(ChangeListener.class);
		while (listeners.hasNext())
			((ChangeListener) listeners.next()) // Leave newline for debug
					// stepping
					.handleStateChanged(change);
	}

}
