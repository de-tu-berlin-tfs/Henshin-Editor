/**
 * Title:        AGG<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Michael Matz<p>
 * Company:      TU Berlin<p>
 * @author Michael Matz
 * @version 1.0
 */
package agg.cons;

public interface Evaluable {
	public boolean eval(java.lang.Object o);

	public boolean eval(java.lang.Object o, int tick);

	public boolean eval(java.lang.Object o, boolean negation);

	public boolean eval(java.lang.Object o, int tick, boolean negation);
	
	public boolean evalForall(java.lang.Object o, int tick);
	
}
