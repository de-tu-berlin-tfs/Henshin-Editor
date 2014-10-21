package agg.parser;

import agg.xt_basis.Rule;

class ComputingThread extends Thread {
	Rule r1;

	Rule r2;

	ExcludePairContainer container;

	ComputingThread(ExcludePairContainer container, Rule r1, Rule r2) {
		this.r1 = r1;
		this.r2 = r2;
		this.container = container;
		this.start();
	}

	public void run() {
		this.container.computeCritical(this.r1, this.r2);
	}

}
