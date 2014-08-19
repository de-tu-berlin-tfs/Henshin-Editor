package de.tub.tfs.henshin.tggeditor.editparts.graphical;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.graphics.Color;

import de.tub.tfs.henshin.tggeditor.util.RuleUtil;

public class RuleObjectTextWithMarker extends TextWithMarker {

	public RuleObjectTextWithMarker(Color FG){
		super();
		setFG_COLOR(FG);
		setAttributes();
	}
	
	@Override
	public boolean setKnownMarker(String newText) {
		boolean isBasicMarker = super.setKnownMarker(newText);
		if (!isBasicMarker) {
			if (RuleUtil.Translated.equals(newText)) {
				text.setForegroundColor(FG_COLOR);
				marker.setText(newText);
				marker.setFont(TEXT_BOLD_FONT);
				marker.setForegroundColor(ColorConstants.blue);
			} else if (RuleUtil.NEW.equals(newText)) {
				text.setForegroundColor(FG_COLOR);
				marker.setText(newText);
				marker.setFont(TEXT_BOLD_FONT);
				marker.setForegroundColor(ColorConstants.darkGreen);
			} else
				return false;

			if(!this.getChildren().contains(marker))
				add(marker);
			return true;
		}
		return true;
	}	
	

}
