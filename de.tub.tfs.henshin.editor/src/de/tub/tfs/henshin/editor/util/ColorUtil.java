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
package de.tub.tfs.henshin.editor.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * The Class ColorUtil.
 */
public class ColorUtil {

	/** The mapping colors. */
	private static Map<Integer, Integer> mappingColors = new HashMap<Integer, Integer>();

	/**
	 * Stores all used colors in the exist rules in transformation system. <li>
	 * Map-key: integer value of the color <li>Map-value: the rules which use
	 * the color.
	 */
	private static Map<Integer, Set<Rule>> usedColors = new HashMap<Integer, Set<Rule>>();

	private static Map<Integer, Integer> mappingNumbers = new HashMap<Integer, Integer>();

	/**
	 * Gets the color.
	 * 
	 * @param index
	 *            the index
	 * @return the color
	 */
	public static Color getColor(Integer index) {
		if (index == -1) {
			return null;
		}

		// The number of colors stored in mappingColors are less than the given
		// index
		if (index >= mappingColors.size()) {
			for (int i = mappingColors.size(); i < index + 1; i++) {
				Color color = getDistinctColor(mappingColors.values());
				mappingColors.put(i, color2Int(color));
			}
		}

		int colorInt = mappingColors.get(index);
		return int2Color(colorInt);
	}

	/**
	 * Int2 color.
	 * 
	 * @param iColor
	 *            the i color
	 * @return the color
	 */
	public static Color int2Color(int iColor) {
		if (iColor == 0) {
			return SWTResourceManager.getColor(220, 220, 220);
		}
		return SWTResourceManager.getColor((iColor & 0x00FF0000) >> 16,
				(iColor & 0x0000FF00) >> 8, iColor & 0x000000FF);
	}

	/**
	 * Color2 int.
	 * 
	 * @param color
	 *            the color
	 * @return the int
	 */
	public static int color2Int(Color color) {
		int iColor = 0;
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		r = r << 16;
		g = g << 8;
		iColor = iColor | r | g | b;
		return iColor;
	}

	/**
	 * Remove the the given mapping color and a rule from the global variable
	 * {@link ColorUtil#usedColors} that stores all used mapping color.
	 * 
	 * @param colorInt
	 *            The color to remove.
	 * @param rule
	 *            The rule to remove from {@link ColorUtil#usedColors} with the
	 *            given color as a map key.
	 */
	public static void removeMappingColor(final int colorInt, final Rule rule) {
		Set<Rule> rules = usedColors.get(colorInt);
		if (rules != null) {
			rules.remove(rule);
			if (rules.isEmpty()) {
				usedColors.remove(colorInt);

				int numberToRemove = -1;
				for (Integer mappingNumber : mappingNumbers.keySet()) {
					if (mappingNumbers.get(mappingNumber).equals(colorInt)) {
						numberToRemove = mappingNumber;
						break;
					}
				}

				if (numberToRemove != -1) {
					mappingNumbers.remove(numberToRemove);
				}
			}
		}
	}

	/**
	 * Add the the given mapping color and a rule to the global variable
	 * {@link ColorUtil#usedColors} that stores all used mapping color.
	 * 
	 * @param colorInt
	 *            The color to add.
	 * @param rule
	 *            The rule to add to {@link ColorUtil#usedColors} with the given
	 *            color as a map key.
	 */
	public static void addMappingColor(final int colorInt, final Rule rule) {
		if (colorInt != 0) {
			actualizeUsedColors(colorInt, rule);
		}
	}

	/**
	 * Returns the next mapping color for the given {@code rule}.
	 * 
	 * @param rule
	 *            For this rule the next mapping color has to be found.
	 * @return The next mapping color-int for the given {@code rule}.
	 */
	public static int getNextMappingColor(final Rule rule) {
		Integer nextColorInt = null;

		for (Integer colorInt : usedColors.keySet()) {
			if (!usedColors.get(colorInt).contains(rule)) {
				nextColorInt = colorInt;
				break;
			}
		}

		if (nextColorInt == null) {
			nextColorInt = color2Int(getDistinctColor(usedColors.keySet()));
		}

		actualizeUsedColors(nextColorInt, rule);
		return nextColorInt;
	}

	/**
	 * @param colorInt
	 *            The color whose mapping number is returned.
	 * @return The mapping number for the given color.
	 */
	public static int getMappingNumber(final Integer colorInt) {
		int mappingNumber = -1;

		if (mappingNumbers.containsValue(colorInt)) {
			for (Integer key : mappingNumbers.keySet()) {
				if (mappingNumbers.get(key).equals(colorInt)) {
					mappingNumber = key;
					break;
				}
			}
		} else if (usedColors.containsKey(colorInt)) {
			final List<Integer> keyList = new ArrayList<Integer>(
					usedColors.keySet());
			mappingNumber = keyList.indexOf(colorInt);
			if (mappingNumbers.containsKey(mappingNumber)) {
				mappingNumber = usedColors.size();
			}
			mappingNumbers.put(mappingNumber, colorInt);
		}

		return mappingNumber;
	}

	/**
	 * Add the given {@code rule} to the map value with the given key
	 * {@code colorInt}.
	 * 
	 * @param rule
	 *            A part of map value.
	 * @param colorInt
	 *            Map key.
	 */
	private static void actualizeUsedColors(final int colorInt, final Rule rule) {
		Set<Rule> rules;
		if (usedColors.containsKey(colorInt)) {
			rules = usedColors.get(colorInt);
		} else {
			rules = new HashSet<Rule>();
		}

		// Add only the given rule
		rules.add(rule);

		usedColors.put(colorInt, rules);
	}

	/**
	 * Gets the distinct color.
	 * 
	 * @param forbiddenColors
	 *            the forbidden colors
	 * @return the distinct color
	 */
	public static Color getDistinctColor(Collection<Integer> forbiddenColors) {
		int c = 1;
		Color color = null;

		do {
			final float f = (float) Math.ceil(c / 360.0f);
			color = getColorFromHSB(c % 360, 0.7f / f, 0.8f);
			/*
			 * we may try one of these nice prime-numbers too 2, 3, 5, 7, 11,
			 * 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73,
			 * 79, 83, 89, 97, 101, 103, 107, 109, 113
			 */
			c += 61.0f; // step in hue direction
		} while (forbiddenColors.contains(Integer.valueOf(color2Int(color))));

		return color;
	}

	/**
	 * Gets the color from hsb.
	 * 
	 * @param hue
	 *            the hue
	 * @param saturation
	 *            the saturation
	 * @param brightness
	 *            the brightness
	 * @return the color from hsb
	 */
	private static Color getColorFromHSB(float hue, final float saturation,
			final float brightness) {
		if (hue < 0 || hue > 360 || saturation < 0 || saturation > 1
				|| brightness < 0 || brightness > 1) {
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		}
		float r, g, b;
		if (saturation == 0) {
			r = g = b = brightness;
		} else {
			if (hue == 360) {
				hue = 0;
			}
			hue /= 60;
			final int i = (int) hue;
			final float f = hue - i;
			final float p = brightness * (1 - saturation);
			final float q = brightness * (1 - saturation * f);
			final float t = brightness * (1 - saturation * (1 - f));
			switch (i) {
			case 0:
				r = brightness;
				g = t;
				b = p;
				break;
			case 1:
				r = q;
				g = brightness;
				b = p;
				break;
			case 2:
				r = p;
				g = brightness;
				b = t;
				break;
			case 3:
				r = p;
				g = q;
				b = brightness;
				break;
			case 4:
				r = t;
				g = p;
				b = brightness;
				break;
			case 5:
			default:
				r = brightness;
				g = p;
				b = q;
				break;
			}
		}

		return SWTResourceManager.getColor((int) (r * 255 + 0.5),
				(int) (g * 255 + 0.5), (int) (b * 255 + 0.5));
	}
}
