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
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.impl.ConditionalUnitImpl;
import org.eclipse.emf.henshin.model.impl.HenshinPackageImpl;
import org.eclipse.emf.henshin.model.impl.IndependentUnitImpl;
import org.eclipse.emf.henshin.model.impl.PriorityUnitImpl;
import org.eclipse.emf.henshin.model.impl.RuleImpl;
import org.eclipse.emf.henshin.model.impl.SequentialUnitImpl;

public class ControlFlowRelation {
	private abstract class ControlFlowHandler<T extends Unit>{
				
		List<Unit> localPriorRules = new LinkedList<Unit>();
		public abstract HashSet<Pair<Unit,Unit>> handle(ControlFlowRelation r,T t, List<Unit> globalPriorRules);
	}

	private Unit emfUnit;
	private static HashMap<Class,ControlFlowHandler> handler = new HashMap<Class, ControlFlowRelation.ControlFlowHandler>();
	private List<String> errors = new LinkedList<String>();
	
	private List<Unit> priorRules = new LinkedList<Unit>();
	private HashSet<Pair<Unit,Unit>> cfr = new HashSet<Pair<Unit,Unit>>();
	
	private void initHandler() {
		if (!handler.isEmpty())
			return;
		
	    handler.put(RuleImpl.class, new ControlFlowHandler<Rule>() {

			@Override
			public HashSet<Pair<Unit,Unit>> handle(ControlFlowRelation r,Rule t, List<Unit> globalPriorRules) {
				localPriorRules.clear();
				HashSet<Pair<Unit,Unit>> cfr = new HashSet<Pair<Unit,Unit>>();
				List<Unit> l = globalPriorRules;
				for (Unit prior : l){
					cfr.add(new Pair<Unit,Unit>(prior, t));
				}
				localPriorRules.add(t);
				globalPriorRules.add(t);

				return cfr;
			}
		});
		
	    handler.put(IndependentUnitImpl.class, new ControlFlowHandler<IndependentUnit>() {

			@Override
			public HashSet<Pair<Unit,Unit>> handle(ControlFlowRelation r,IndependentUnit t, List<Unit> globalPriorRules) {
				localPriorRules.clear();
				HashSet<Pair<Unit,Unit>> cfr = new HashSet<Pair<Unit,Unit>>();
				EList<Unit> subUnits = t.getSubUnits();
				List<List<Unit>> priorUnits = new LinkedList<List<Unit>>();
				for (Unit unit : subUnits) {
					cfr.addAll( ControlFlowRelation.handler.get(unit.getClass()).handle(r, unit, new LinkedList<Unit>( globalPriorRules) ) );
					priorUnits.add(new LinkedList<Unit>( ControlFlowRelation.handler.get(unit.getClass()).localPriorRules));
					//localPriorRules.addAll(ControlFlowRelation.handler.get(unit.getClass()).localPriorRules);
				}
				
				
				for (List<Unit> list : priorUnits) {
					cfr.addAll(merge(localPriorRules,list,true));
					localPriorRules.addAll(list);
				}
				globalPriorRules.addAll(localPriorRules);
				
				return cfr;
			}
		});
	    
	    handler.put(SequentialUnitImpl.class, new ControlFlowHandler<SequentialUnit>() {

			@Override
			public HashSet<Pair<Unit,Unit>> handle(ControlFlowRelation r,SequentialUnit t, List<Unit> globalPriorRules) {
				localPriorRules.clear();
				HashSet<Pair<Unit,Unit>> cfr = new HashSet<Pair<Unit,Unit>>();
				
				List<Unit> subUnits = t.getSubUnits();
				for (Unit sub : subUnits) {
					cfr.addAll(r.handler.get(sub.getClass()).handle(r, sub, globalPriorRules));
					localPriorRules.addAll(r.handler.get(sub.getClass()).localPriorRules);
					globalPriorRules.addAll(r.handler.get(t.getClass()).localPriorRules);
				}
				
				return cfr;
			}
		});
	    
	    handler.put(PriorityUnitImpl.class, new ControlFlowHandler<PriorityUnit>() {

			@Override
			public HashSet<Pair<Unit,Unit>> handle(ControlFlowRelation r,PriorityUnit t, List<Unit> globalPriorRules) {
				throw new UnsupportedOperationException();
			}
		});
	    
	   /* handler.put(AmalgamationUnitImpl.class, new ControlFlowHandler<AmalgamationUnit>() {

			@Override
			public HashSet<Pair<Unit,Unit>> handle(ControlFlowRelation r,AmalgamationUnit t, List<Unit> globalPriorRules) {
				throw new UnsupportedOperationException();
			}
		});
	    
	    handler.put(CountedUnitImpl.class, new ControlFlowHandler<CountedUnit>() {

			@Override
			public HashSet<Pair<Unit,Unit>> handle(ControlFlowRelation r,CountedUnit t, List<Unit> globalPriorRules) {
				throw new UnsupportedOperationException();
			}
		});	    
	    */
	    handler.put(ConditionalUnitImpl.class, new ControlFlowHandler<ConditionalUnit>() {

			@Override
			public HashSet<Pair<Unit,Unit>> handle(ControlFlowRelation r,ConditionalUnit t, List<Unit> globalPriorRules) {
				throw new UnsupportedOperationException();
			}
		});
	}
	
	public ControlFlowRelation(Unit tu) {
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
		Unit u2 = HenshinFactory.eINSTANCE.createRule();
		u2.setName("r1");
		Unit u3 = HenshinFactory.eINSTANCE.createRule();
		u3.setName("r2");
		Unit u4 = HenshinFactory.eINSTANCE.createRule();
		u4.setName("r3");
		Unit u5 = HenshinFactory.eINSTANCE.createRule();
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
	
	private static HashSet<Pair<Unit, Unit>> merge(
			Collection<Unit> mainPrior,
			Collection<Unit> subPrior,
			boolean bidirectional) {
		HashSet<Pair<Unit, Unit>> subCFR = new HashSet<Pair<Unit,Unit>>();
		for (Unit r2 : subPrior) {
			for (Unit r1 : mainPrior) {
				subCFR.add(new Pair<Unit,Unit>(r1,r2));
				if (bidirectional)
					subCFR.add(new Pair<Unit,Unit>(r2,r1));
			}
		}

		return subCFR;
				
	}

	
}
