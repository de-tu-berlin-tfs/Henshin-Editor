package de.tub.tfs.henshin.analysis;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.henshin.model.impl.ConditionalUnitImpl;
import org.eclipse.emf.henshin.model.impl.HenshinPackageImpl;
import org.eclipse.emf.henshin.model.impl.IndependentUnitImpl;
import org.eclipse.emf.henshin.model.impl.LoopUnitImpl;
import org.eclipse.emf.henshin.model.impl.PriorityUnitImpl;
import org.eclipse.emf.henshin.model.impl.RuleImpl;
import org.eclipse.emf.henshin.model.impl.SequentialUnitImpl;

public class ControlFlowRelation {
	private abstract class ControlFlowHandler<T extends TransformationUnit>{
				
		List<TransformationUnit> localPriorRules = new LinkedList<TransformationUnit>();
		public abstract HashSet<Pair<TransformationUnit,TransformationUnit>> handle(ControlFlowRelation r,T t, List<TransformationUnit> globalPriorRules);
	}

	private TransformationUnit emfUnit;
	private static HashMap<Class,ControlFlowHandler> handler = new HashMap<Class, ControlFlowRelation.ControlFlowHandler>();
	private List<String> errors = new LinkedList<String>();
	
	private List<TransformationUnit> priorRules = new LinkedList<TransformationUnit>();
	private HashSet<Pair<TransformationUnit,TransformationUnit>> cfr = new HashSet<Pair<TransformationUnit,TransformationUnit>>();
	
	private void initHandler() {
		if (!handler.isEmpty())
			return;
		
	    handler.put(RuleImpl.class, new ControlFlowHandler<Rule>() {

			@Override
			public HashSet<Pair<TransformationUnit,TransformationUnit>> handle(ControlFlowRelation r,Rule t, List<TransformationUnit> globalPriorRules) {
				localPriorRules.clear();
				HashSet<Pair<TransformationUnit,TransformationUnit>> cfr = new HashSet<Pair<TransformationUnit,TransformationUnit>>();
				List<TransformationUnit> l = globalPriorRules;
				for (TransformationUnit prior : l){
					cfr.add(new Pair<TransformationUnit,TransformationUnit>(prior, t));
				}
				localPriorRules.add(t);
				globalPriorRules.add(t);

				return cfr;
			}
		});
		
	    handler.put(IndependentUnitImpl.class, new ControlFlowHandler<IndependentUnit>() {

			@Override
			public HashSet<Pair<TransformationUnit,TransformationUnit>> handle(ControlFlowRelation r,IndependentUnit t, List<TransformationUnit> globalPriorRules) {
				localPriorRules.clear();
				HashSet<Pair<TransformationUnit,TransformationUnit>> cfr = new HashSet<Pair<TransformationUnit,TransformationUnit>>();
				EList<TransformationUnit> subUnits = t.getSubUnits();
				List<List<TransformationUnit>> priorUnits = new LinkedList<List<TransformationUnit>>();
				for (TransformationUnit unit : subUnits) {
					cfr.addAll( ControlFlowRelation.handler.get(unit.getClass()).handle(r, unit, new LinkedList<TransformationUnit>( globalPriorRules) ) );
					priorUnits.add(new LinkedList<TransformationUnit>( ControlFlowRelation.handler.get(unit.getClass()).localPriorRules));
					//localPriorRules.addAll(ControlFlowRelation.handler.get(unit.getClass()).localPriorRules);
				}
				
				
				for (List<TransformationUnit> list : priorUnits) {
					cfr.addAll(merge(localPriorRules,list,true));
					localPriorRules.addAll(list);
				}
				globalPriorRules.addAll(localPriorRules);
				
				return cfr;
			}
		});
	    
	    handler.put(SequentialUnitImpl.class, new ControlFlowHandler<SequentialUnit>() {

			@Override
			public HashSet<Pair<TransformationUnit,TransformationUnit>> handle(ControlFlowRelation r,SequentialUnit t, List<TransformationUnit> globalPriorRules) {
				localPriorRules.clear();
				HashSet<Pair<TransformationUnit,TransformationUnit>> cfr = new HashSet<Pair<TransformationUnit,TransformationUnit>>();
				
				List<TransformationUnit> subUnits = t.getSubUnits();
				for (TransformationUnit sub : subUnits) {
					cfr.addAll(r.handler.get(sub.getClass()).handle(r, sub, globalPriorRules));
					localPriorRules.addAll(r.handler.get(sub.getClass()).localPriorRules);
					globalPriorRules.addAll(r.handler.get(t.getClass()).localPriorRules);
				}
				
				return cfr;
			}
		});
	    
	    handler.put(PriorityUnitImpl.class, new ControlFlowHandler<PriorityUnit>() {

			@Override
			public HashSet<Pair<TransformationUnit,TransformationUnit>> handle(ControlFlowRelation r,PriorityUnit t, List<TransformationUnit> globalPriorRules) {
				throw new UnsupportedOperationException();
			}
		});
	    
	    handler.put(LoopUnitImpl.class, new ControlFlowHandler<LoopUnit>() {

			@Override
			public HashSet<Pair<TransformationUnit,TransformationUnit>> handle(ControlFlowRelation r,LoopUnit t, List<TransformationUnit> globalPriorRules) {
				throw new UnsupportedOperationException();
			}
		});	    
	    
	    handler.put(ConditionalUnitImpl.class, new ControlFlowHandler<ConditionalUnit>() {

			@Override
			public HashSet<Pair<TransformationUnit,TransformationUnit>> handle(ControlFlowRelation r,ConditionalUnit t, List<TransformationUnit> globalPriorRules) {
				throw new UnsupportedOperationException();
			}
		});
	}
	
	public ControlFlowRelation(TransformationUnit tu) {
		this.initHandler();
		this.emfUnit = tu;
		this.cfr.addAll( handler.get(tu.getClass()).handle(this,tu, priorRules));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HenshinPackageImpl.init();
	
		IndependentUnit u1 = HenshinFactory.eINSTANCE.createIndependentUnit();
		SequentialUnit s1 = HenshinFactory.eINSTANCE.createSequentialUnit();
		TransformationUnit u2 = HenshinFactory.eINSTANCE.createRule();
		u2.setName("r1");
		TransformationUnit u3 = HenshinFactory.eINSTANCE.createRule();
		u3.setName("r2");
		TransformationUnit u4 = HenshinFactory.eINSTANCE.createRule();
		u4.setName("r3");
		TransformationUnit u5 = HenshinFactory.eINSTANCE.createRule();
		u5.setName("r4");
		
		u1.getSubUnits().add(u2);
		u1.getSubUnits().add(u3);
		s1.getSubUnits().add(u4);
		s1.getSubUnits().add(u1);
		s1.getSubUnits().add(u5);
		ControlFlowRelation r1 = new ControlFlowRelation(s1);
		System.out.println(Arrays.deepToString(r1.cfr.toArray()));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static LinkedList<Object> flatten(int exclude,  List<?> obj){
		LinkedList<Object> list = new LinkedList<Object>();
		int count = 0;
		for (Object object : obj) {
			if (count++ == exclude) 
				continue;
			if (object instanceof Collection){
				list.addAll((Collection) object);
			} else {
				list.add(object);
			}
		}
		return list;
	}
	
	private static HashSet<Pair<TransformationUnit, TransformationUnit>> merge(
			Collection<TransformationUnit> mainPrior,
			Collection<TransformationUnit> subPrior,
			boolean bidirectional) {
		HashSet<Pair<TransformationUnit, TransformationUnit>> subCFR = new HashSet<Pair<TransformationUnit,TransformationUnit>>();
		for (TransformationUnit r2 : subPrior) {
			for (TransformationUnit r1 : mainPrior) {
				subCFR.add(new Pair<TransformationUnit,TransformationUnit>(r1,r2));
				if (bidirectional)
					subCFR.add(new Pair<TransformationUnit,TransformationUnit>(r2,r1));
			}
		}

		return subCFR;
				
	}

	
}
