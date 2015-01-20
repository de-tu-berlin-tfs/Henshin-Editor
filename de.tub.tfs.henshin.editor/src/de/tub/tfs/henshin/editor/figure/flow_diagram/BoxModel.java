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
 * BoxModel.java
 *
 * Created 18.12.2011 - 17:13:05
 */
package de.tub.tfs.henshin.editor.figure.flow_diagram;

/**
 * @author nam
 * 
 */
public class BoxModel {
	/**
     * 
     */
	private int hPadding = 0;

	/**
     * 
     */
	private int vPadding = 0;

	/**
     * 
     */
	private int hSpacing = 0;

	/**
     * 
     */
	private int vSpacing = 0;

	/**
	 * @return the hPadding
	 */
	public int gethPadding() {
		return hPadding;
	}

	/**
	 * @param hPadding
	 *            the hPadding to set
	 */
	public void sethPadding(int hPadding) {
		this.hPadding = hPadding;
	}

	/**
	 * @return the vPadding
	 */
	public int getvPadding() {
		return vPadding;
	}

	/**
	 * @param vPadding
	 *            the vPadding to set
	 */
	public void setvPadding(int vPadding) {
		this.vPadding = vPadding;
	}

	/**
	 * @return the hSpacing
	 */
	public int gethSpacing() {
		return hSpacing;
	}

	/**
	 * @param hSpacing
	 *            the hSpacing to set
	 */
	public void sethSpacing(int hSpacing) {
		this.hSpacing = hSpacing;
	}

	/**
	 * @return the vSpacing
	 */
	public int getvSpacing() {
		return vSpacing;
	}

	/**
	 * @param vSpacing
	 *            the vSpacing to set
	 */
	public void setvSpacing(int vSpacing) {
		this.vSpacing = vSpacing;
	}

}
