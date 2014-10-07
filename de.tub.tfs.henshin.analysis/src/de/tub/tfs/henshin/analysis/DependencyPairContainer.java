package de.tub.tfs.henshin.analysis;

import agg.parser.ParserMessageEvent;
import agg.xt_basis.GraGra;
import agg.xt_basis.Rule;

public class DependencyPairContainer extends agg.parser.DependencyPairContainer {

	public DependencyPairContainer(GraGra gragra) {
		super(gragra);
		// TODO Auto-generated constructor stub
	}

	public void computeCriticalPair(Rule r1, Rule r2) {
		
		try{
			
	        long time0 = System.currentTimeMillis();
	        stop = false;
	        isAlive = true;
	        if(useHostGraph)
	            firePairEvent(new ParserMessageEvent(this, "Thread  -  Checking Host Graph  -  started."));
	        firePairEvent(new ParserMessageEvent(this, "Thread  - Critical pairs -  runs ..."));
	        computeCritical(r1,r2);
	        if(stop)
	        {
	            firePairEvent(new ParserMessageEvent(this, "Thread  -  Critical pairs  -  was stopped."));
	            stop = false;
	        } else
	        {
	            System.out.println((new StringBuilder("Used time: ")).append(System.currentTimeMillis() - time0).append("ms").toString());
	            firePairEvent(new ParserMessageEvent(this, -2, "Thread  -  Critical pairs  -  finished."));
	            if(useHostGraph)
	                firePairEvent(new ParserMessageEvent(this, -2, "Thread  -  Checking Host Graph  -  finished."));
	        }
	        isAlive = false;
		} catch (NullPointerException ex){
			ex.printStackTrace();
		}
		
	}
	
	
	

}
