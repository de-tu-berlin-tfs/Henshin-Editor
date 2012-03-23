package agg.gui.event;

import java.util.EventObject;

import agg.gui.trafo.TransformInterpret;
import agg.gui.trafo.TransformLayered;
import agg.xt_basis.Match;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;

/**
 * The class TransformEvent is used to notify the state of the gragra
 * transformation.
 * 
 * @author $Author: olga $
 * @version $ID:$
 */
public class TransformEvent extends EventObject {

	public static final int INPUT_PARAMETER_NOT_SET = 1;

	public static final int STEP_COMPLETED = 2;

	public static final int NO_COMPLETION = 3;

	public static final int NOT_VALID = 14;

	public static final int CANNOT_TRANSFORM = 4;

	public static final int CLEAR_MATCH = 5;

	public static final int KEEP_MATCH = 6;

	public static final int MATCH_COMPLETED = 7;

	public static final int CANCEL = 8;

	public static final int START = 9;

	public static final int STOP = 10;

	public static final int MATCH_DEF = 11;

	public static final int NEXT = 12;

	public static final int STEP = 13;

	public static final int CANNOT_MATCH = 15;

	public static final int NEW_MATCH = 16;

	public static final int MATCH_VALID = 17;

	public static final int NOT_READY_TO_TRANSFORM = 18;

	public static final int INCONSISTENT = 19;

	public static final int APPLICABLE_RULES = 20;

	public static final int INHERITANCE = 21;

	public static final int LAYER_FINISHED = 22;

	public static final int MATCH_PARTIAL = 23;

	public static final int RULE_SEQUENCE_DEFINE = 24;

	public static final int ANIMATED_NODE = 25;
	
	public static final int RESET_GRAPH = 26;
	
	public static final int RULE = 27;
	
	
	public TransformEvent(Object source, int key) {
		super(source);
		this.msgkey = key;
		if (source instanceof TransformInterpret) {
			if (key == CANCEL)
				this.msg = "was canceled";
			else if (key == STOP) {
				if (((TransformInterpret) source).isSuccessful())
					this.msg = "is finished";
				else if (((TransformInterpret) source).isStopped())
					this.msg = "is stopped";
				else
					this.msg = "is failed";
			}
		} else if (source instanceof TransformLayered) {
			if (key == CANCEL)
				this.msg = "was canceled";
			else if (key == STOP) {
				if (((TransformLayered) source).isSuccessful())
					this.msg = "is finished";
				else if (((TransformLayered) source).isStopped())
					this.msg = "is stopped";
				else
					this.msg = "is failed";
			}
		} else
			this.msg = "";
	}

	public TransformEvent(Object source, int key, Object obj) {
		this(source, key);
		if (obj instanceof String)
			this.msg = (String) obj;
		else
			this.obj = obj;
	}

	public TransformEvent(Object source, int key, Match m) {
		this(source, key);
		// match = m;
		this.obj = m;
	}

	public TransformEvent(Object source, int key, String aMsg) {
		this(source, key);
		this.msg = aMsg;
	}

	public TransformEvent(Object source, int key, Object obj, String aMsg) {
		this(source, key);
		this.obj = obj;
		this.msg = aMsg;
	}

	public int getMsg() {
		return this.msgkey;
	}

	public String getMessage() {
		return this.msg;
	}

	public Rule getRule() {
		if (this.obj instanceof Rule)
			return (Rule) this.obj;
		
		return null;
	}
	
	public Match getMatch() {
		if (this.obj instanceof Match)
			return (Match) this.obj;
		
		return null;
	}

	public OrdinaryMorphism getMorphism() {
		if (this.obj instanceof OrdinaryMorphism)
			return (OrdinaryMorphism) this.obj;
		
		return null;
	}

	public Object getObject() {
		return this.obj;
	}

	private int msgkey;

	private String msg = "";

	// private Match match;
	private Object obj;
}
