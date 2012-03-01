

import java.util.List;
import java.util.Vector;

import agg.cons.Evaluable;
import agg.cons.Formula;

/**
 * An example for a formula as string representation.
 * @author olga
 *
 */
public class TestFormula {
	
	private static void test_str(List<Evaluable> evals, String s) {
		System.out.print(s);
		System.out.print(" [");
		Formula f = new Formula(evals, s);
		if (!f.isValid())
			System.out.print("in");
		System.out.print("valid] --> ");
		System.out.print(f.eval(null));
		Vector<Evaluable> v = new Vector<Evaluable>();
		System.out.println("  returned formula: " + f.getAsString(v));
	}

	public static void main(String argv[]) {
		List<Evaluable> evals = new Vector<Evaluable>();
		evals.add(new AtomTest(1));
		evals.add(new AtomTest(2));
		evals.add(new AtomTest(3));
		evals.add(new AtomTest(4));
		evals.add(new AtomTest(5));
		evals.add(new AtomTest(6));
		
		test_str(evals, "0");
		test_str(evals, "1");
		test_str(evals, "2");
		test_str(evals, "3");
		test_str(evals, "4");
		test_str(evals, "5");
		test_str(evals, "6");
		test_str(evals, "7");
		test_str(evals, "true");
		test_str(evals, "false");
		test_str(evals, "false7");
		test_str(evals, "-2");
		test_str(evals, "1 || !2");
		test_str(evals, "1 ||& !2");
		test_str(evals, "!2 &&!4");
		test_str(evals, "2|| 4");
		test_str(evals, "4||");
		test_str(evals, "2 || ");
		test_str(evals, "! || 3");
		test_str(evals, " || 3");
		test_str(evals, "|| 3");
	}
	
	static class AtomTest implements Evaluable {
		int val;

		public AtomTest(int i) {
			this.val = i;
		}

		public boolean eval(java.lang.Object o) {
			return this.val % 4 == 0;
		}

		public boolean eval(java.lang.Object o, int tick) {
			return tick % 4 == 0;
		}

		public boolean eval(java.lang.Object o, boolean negaition) {
			return this.val % 4 == 0;
		}

		public boolean eval(java.lang.Object o, int tick, boolean negaition) {
			return tick % 4 == 0;
		}

		/* (non-Javadoc)
		 * @see agg.cons.Evaluable#evalForall(java.lang.Object, int, boolean)
		 */
		public boolean evalForall(Object o, int tick) {
			// TODO Auto-generated method stub
			return false;
		}
	}
}
