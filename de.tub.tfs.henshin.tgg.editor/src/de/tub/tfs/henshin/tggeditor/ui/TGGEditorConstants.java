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
package de.tub.tfs.henshin.tggeditor.ui;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;


/**
 * @author Frank Hermann
 * 
 */
public final class TGGEditorConstants {
	
	public static final String ICONS_PATH = "icons/";
	
	public static final String ICON_DELETE_18 = ICONS_PATH + "deletion18.png";
	
	// fonts
	public static Font TEXT_BOLD_FONT = new Font(null, "SansSerif", 8, SWT.BOLD);
	public static Font TEXT_FONT = new Font(null, "SansSerif", 8, SWT.NORMAL);
	public static Font TEXT_TITLE_FONT = new Font(null, java.awt.Font.MONOSPACED, 20, SWT.BOLD);
	public static Font TEXT_TITLE_FONT_SMALL = new Font(null, "SansSerif", 14, SWT.BOLD);
	
	// colors
	public static final Color LINE_COLOR = ColorConstants.buttonDarkest;
	public static final Color FG_STANDARD_COLOR= ColorConstants.buttonDarkest;
	public static final Color FG_BLACK_COLOR= ColorConstants.black;
	public static final Color FG_TRANSLATED_COLOR = ColorConstants.darkGreen;
	public static final Color FG_NOT_TRANSLATED_COLOR = ColorConstants.red;
	public static final Color FG_CRITICAL_COLOR = ColorConstants.red;
	public static final Color FG_TRANSLATION_MARKER_COLOR = ColorConstants.blue;
	public static final Color FG_CREATION_MARKER_COLOR = ColorConstants.darkGreen;
	public static final Color BG_COLOR_GREY = new Color(null,240,240,240);

	public static final Color SOURCE_COLOR= new Color(null,252,239,226);
	public static final Color CORR_COLOR= new Color(null,226,240,252);
	public static final Color TARGET_COLOR= new Color(null,255,255,235);
	public static final Color BORDER_DEFAULT_COLOR = ColorConstants.buttonDarkest;
	public static final Color BORDER_TRANSLATED_COLOR = ColorConstants.darkGreen;
	public static final Color BORDER_NOT_TRANSLATED_COLOR = ColorConstants.buttonDarkest;
	
	/** The background color of node figure if it's selected */
	public static final Color SECLECTED_COLOR = new Color(null,100,255,100);
	/** The background color of node figure if it's primary selected */
	public static final Color SELECTED_PRIMARY_COLOR = new Color(null,255,100,100);
	
	

	public static void updateFonts(){
		String osString = System.getProperty("os.name");
		if(osString.toUpperCase().startsWith("MAC"))
		{
			TEXT_BOLD_FONT = new Font(null, "SansSerif", 10, SWT.BOLD);
			TEXT_FONT = new Font(null, "SansSerif", 10, SWT.NORMAL);
			TEXT_TITLE_FONT = new Font(null, java.awt.Font.MONOSPACED, 20, SWT.BOLD);
			TEXT_TITLE_FONT_SMALL = new Font(null, "SansSerif", 14, SWT.BOLD);
		}
	}

	
	
	
	
}// class

