package agg.gui.parser.event;

import agg.xt_basis.Rule;

public class CPAEventData {
	public Rule r1;

	public Rule r2;

	public boolean visible;

	public static int SHOW_RULE = 1;

	public static int SHOW_RELATION = 2;
	
	public static int HIDE_RELATION = 3;

	public String type; // C | D

	public int kind;

	public CPAEventData(Rule r, int kind, boolean vis) {
		this.r1 = r;
		this.kind = kind;
		this.visible = vis;
	}

	public CPAEventData(Rule r1, Rule r2, int kind, String type, boolean vis) {
		this.r1 = r1;
		this.r2 = r2;
		this.kind = kind;
		this.type = type;
		this.visible = vis;
	}
}
