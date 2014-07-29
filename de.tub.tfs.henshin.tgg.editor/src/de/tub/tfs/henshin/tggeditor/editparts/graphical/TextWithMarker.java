package de.tub.tfs.henshin.tggeditor.editparts.graphical;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import de.tub.tfs.henshin.tgg.interpreter.RuleUtil;
import de.tub.tfs.henshin.tggeditor.ui.TGGEditorConstants;

// label container with text and marker
public class TextWithMarker extends Figure {

	protected Color FG_COLOR = TGGEditorConstants.FG_STANDARD_COLOR;
	
	public void setFG_COLOR(Color fG_COLOR) {
		FG_COLOR = fG_COLOR;
	}

	/** The text. */
	protected Label text = new Label("");

	/** The marker label. */
	protected Label marker= new Label("");

	public TextWithMarker() {
		super();
		setAttributes();

	}

	/**
	 * 
	 */
	protected void setAttributes() {
//		if (FG_COLOR==null)
//			FG_COLOR= ColorConstants.black;
		setLayoutManager(new FlowLayout());
		setBorder(new MarginBorder(0, 1, 0 , 1));

		text.setFont(TGGEditorConstants.TEXT_FONT);
		text.setOpaque(true);
		text.setForegroundColor(FG_COLOR);

		marker.setTextAlignment(SWT.CENTER);
		marker.setOpaque(true);
		marker.setForegroundColor(FG_COLOR);
		marker.setFont(TGGEditorConstants.TEXT_FONT);
		marker.setVisible(true);
		
		add(text);
		add(marker);
	}
	
	public TextWithMarker(Color FG) {
		super();
		setFG_COLOR(FG);
		setAttributes();
	}
	
	
	public void setText(String newText) {
		text.setText(newText);
	}

	public void setMarker(String newText) {
		boolean success = setKnownMarker(newText);
		if (!success)
			setErrorMarker();
	}

	public boolean setKnownMarker(String newText) {
		if (newText == null) {
			text.setForegroundColor(FG_COLOR);
			marker.setText("");
			if(this.getChildren().contains(marker))
				remove(marker);
		} else if (RuleUtil.Translated_Graph.equals(newText)) {
			text.setForegroundColor(TGGEditorConstants.FG_TRANSLATED_COLOR);
			marker.setForegroundColor(TGGEditorConstants.FG_TRANSLATED_COLOR);
			marker.setText(RuleUtil.Translated_Graph);
		} else if (RuleUtil.Not_Translated_Graph.equals(newText)) {
			text.setForegroundColor(TGGEditorConstants.FG_NOT_TRANSLATED_COLOR);
			marker.setForegroundColor(TGGEditorConstants.FG_NOT_TRANSLATED_COLOR);
			marker.setText(RuleUtil.Not_Translated_Graph);
		} else if (RuleUtil.TR_UNSPECIFIED.equals(newText)) {
			text.setForegroundColor(FG_COLOR);
			marker.setText(RuleUtil.TR_UNSPECIFIED);
			marker.setForegroundColor(FG_COLOR);
			marker.setFont(TGGEditorConstants.TEXT_FONT);
		} else
			return false;

		if(!this.getChildren().contains(marker))
			add(marker);
		return true;
	}

	protected void setErrorMarker() {
		text.setForegroundColor(FG_COLOR);
		marker.setText(RuleUtil.ErrorMarker);
		marker.setForegroundColor(FG_COLOR);
		marker.setFont(TGGEditorConstants.TEXT_FONT);
	}

	
}
