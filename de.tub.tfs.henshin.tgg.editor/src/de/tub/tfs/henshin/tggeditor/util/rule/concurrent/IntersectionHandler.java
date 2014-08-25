/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.util.rule.concurrent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.impl.AttributeConditionImpl;
import org.eclipse.emf.henshin.model.impl.MappingListImpl;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.AttributeUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphicalRuleUtil;
import de.tub.tfs.henshin.tggeditor.util.rule.copy.Graph2GraphCopyMappingList;

public class IntersectionHandler extends MappingListImpl{
	
	
private HashMap<String, String> noneValue2Value;
private HashMap<String, String> oldNoneValue2Value;
private Set<Set<String>> noneValueCosets;
private Set<Set<String>> oldNoneValueCosets;
private Set<String> noneValuesBelongingToCoset;
private AttributeCondition attributeCondition;

private boolean consistent = true;

private HashMap<String, String> hash2Value;

public IntersectionHandler(Graph leftGraph, Graph rightGraph){
	super();
	this.noneValue2Value = new HashMap<String, String>();
	this.oldNoneValue2Value = new HashMap<String, String>();
	this.noneValueCosets = new HashSet<Set<String>>();
	this.oldNoneValueCosets = new HashSet<Set<String>>();
	this.noneValuesBelongingToCoset = new HashSet<String>();
	this.hash2Value = new HashMap<String, String>();
	
	this.graphL = leftGraph;
	this.graphR = rightGraph;
	this.graphL2GraphCopy = new Graph2GraphCopyMappingList(this.graphL);
	this.graphR2GraphCopy = new Graph2GraphCopyMappingList(this.graphR);
	this.edges = new HashSet<Edge>();
}

public boolean isConsistent(){
	return consistent;
}

public AttributeCondition getAttributeCondition(){
	 if (this.attributeCondition!=null) return this.attributeCondition;
	 this.attributeCondition = new AttributeConditionImpl();
	 String conditionText = "";
	 for (Set<String> coset : noneValueCosets){
		 String prev = null;
		 String value = null;
		 for (String param : coset){
			 if (prev!=null){
				 if (!conditionText.equals("")){
					 conditionText+=" && ";
				 }
				 conditionText+="("+prev+")==("+param+")";
				 //value = noneValue2Value.remove(prev);
			 }
			 prev = param;
		 }
		 value = noneValue2Value.get(prev);
		 if (value!=null){
			 conditionText += "&& ("+prev+")==("+value+")";
		 }
	 }
	 
	 for (String noneValue : this.noneValue2Value.keySet()){
		 if (!this.noneValuesBelongingToCoset.contains(noneValue)){
			 String value = this.noneValue2Value.get(noneValue);
			 if (!conditionText.equals("")){
				 conditionText+=" && ";
			 }
			 conditionText+="("+noneValue+"=="+value+")";
		 }
	 }
	 
	 this.attributeCondition.setConditionText(conditionText);
	 return this.attributeCondition;
}

public void persistTemporaryChanges(){
	//copy paramCosets;
	boolean empty = this.noneValueCosets.isEmpty();
			
	if (!changed) return;
	this.oldNoneValueCosets.clear();
	this.oldNoneValueCosets = new HashSet<Set<String>>();
	for (Set<String> coset : this.noneValueCosets){
		this.oldNoneValueCosets.add(new HashSet<String>(coset));
	}
	Test.check(this.noneValueCosets.isEmpty()==empty);
	empty = this.noneValue2Value.isEmpty();
	this.oldNoneValue2Value.clear();
	this.oldNoneValue2Value = new HashMap<String, String>(this.noneValue2Value);
	Test.check(this.noneValue2Value.isEmpty()==empty);
	this.changed = false;
	this.consistent = true;
}

//private boolean unChanged = false;
private boolean changed = false;

public boolean undoTemporaryChanges(){
	if (!changed) return false;
	this.noneValueCosets.clear();
	this.noneValueCosets = new HashSet<Set<String>>();
	for (Set<String> coset : this.oldNoneValueCosets){
		this.noneValueCosets.add(new HashSet<String>(coset));
	}
	this.noneValue2Value.clear();
	this.noneValue2Value = new HashMap<String, String>(this.oldNoneValue2Value);
	this.changed = false;
	this.consistent = true;
	return true;
}



private boolean isIntersectingNodes(Node nodeLRuleL, Node nodeLRuleR, boolean add){
	if (this.getImage(nodeLRuleL, nodeLRuleR.getGraph())==nodeLRuleR) return true;
	Node nodeRRuleR = RuleUtil.getRHSNode(nodeLRuleR);
	if (!ConcurrentRuleUtil.sameNodeType(nodeLRuleL, nodeLRuleR)) return false;
	
	if (nodeLRuleL instanceof TNode && nodeLRuleR instanceof TNode && 
			!GraphicalNodeUtil.guessTC((TNode)nodeLRuleL).equals(GraphicalNodeUtil.guessTC((TNode)nodeLRuleR)))
		return false;
	if (nodeLRuleL instanceof TNode && nodeLRuleR instanceof TNode && 
			GraphicalNodeUtil.isCorrNode((TNode)nodeLRuleL) && GraphicalNodeUtil.isCorrNode((TNode)nodeLRuleR))
		return true;
	EList<Attribute> attributesNodeLRuleL = nodeLRuleL.getAttributes();
	EList<Attribute> attributesNodeLRuleR = nodeLRuleR.getAttributes();
	//persistTemporaryChanges();
	for (Attribute attributeNodeLRuleR : attributesNodeLRuleR){
		boolean attributeNodeLRuleLFound = false;
		for (Attribute attributeNodeLRuleL : attributesNodeLRuleL){
			if (attributeNodeLRuleL.getType().getName().equals(attributeNodeLRuleR.getType().getName())){ 
				String valueL = attributeNodeLRuleL.getValue();
				String valueR = attributeNodeLRuleR.getValue();
				attributeNodeLRuleLFound = (valueL==null ? valueR==null : valueL.equals(valueR));
				if (!attributeNodeLRuleLFound){
					if (isValue(valueL) && !isValue(valueR)){
						attributeNodeLRuleLFound = addValues(valueL, valueR, add);
					}else if (isValue(valueR) && !isValue(valueL)){
						attributeNodeLRuleLFound = addValues(valueR, valueL, add);
					}else if (!isValue(valueL) && !isValue(valueR)){
						attributeNodeLRuleLFound = addNoneValues(valueL, valueR, add);
					}
				}
				if (attributeNodeLRuleLFound) break;
			}
		}
		if (!attributeNodeLRuleLFound) { 
			return false;
		}
	}
	
	//attributes from NodeLRuleL that are not in NodeLRuleR are only valid if they are not created by ruleR in case they are unique
	//if not they would be overwritten by the attribute creation of the rule
	for (Attribute attributeNodeLRuleL : attributesNodeLRuleL){
		if (!attributeNodeLRuleL.getType().isUnique()) continue;
		boolean attributeNodeLRuleRFound = false;
		for (Attribute attributeNodeLRuleR : attributesNodeLRuleR){
				if (attributeNodeLRuleL.getType().getName().equals(attributeNodeLRuleR.getType().getName())){ 
					String valueL = attributeNodeLRuleL.getValue();
					String valueR = attributeNodeLRuleR.getValue();
					attributeNodeLRuleRFound = (valueL==null ? valueR==null : valueL.equals(valueR));
					if (!attributeNodeLRuleRFound && !(isValue(valueL) && isValue(valueR))) attributeNodeLRuleRFound=true;
					if (attributeNodeLRuleRFound) break;
				}
			}
		//if new attribute
		if (!attributeNodeLRuleRFound && nodeRRuleR!=null){
			EList<Attribute> attributesNodeRRuleR = nodeRRuleR.getAttributes();
			for (Attribute attributeNodeRRuleR : attributesNodeRRuleR){
				if (attributeNodeLRuleL.getType().getName().equals(attributeNodeRRuleR.getType().getName())){
					return false;
				}
			}
		}
	}
	return true;
}


 
 private boolean addValues(String value, String noneValue, boolean add){
	 if (!isValue(value)) throw new IllegalArgumentException(value+" is not a valid value");
	 if (!addValue(value, noneValue, add)) {
		 return false;
	 }
	 Set<String> coSet = getCoset(noneValue);
	 if (coSet!=null){
		 boolean inConsistent = false;
		 for (String nValue : coSet){
			 if (!addValue(value, nValue, add)){
				 if (inConsistent && add) consistent = false;
				 return false;
			 }
			 inConsistent = true;
		 }
	 }
	 if (add) this.changed = true;
	 return true;
 }
 
 private boolean addValue(String value, String noneValue, boolean add){
	 if (this.noneValue2Value.containsKey(noneValue)){
		 String val = this.noneValue2Value.get(noneValue);
		 return val.equals(value);
	 }
	 if (!add) return true;
	 this.noneValue2Value.put(noneValue, value);
	 this.changed = true;
	 return true;
 }
 
 private String getValue(String noneValue){
	return noneValue2Value.get(noneValue);
 }
 
 private Set<String> getCoset(String noneValue){
	 for (Set<String> noneValueCoset : noneValueCosets){
		 if (noneValueCoset.contains(noneValue)) return noneValueCoset;
	 }
	 return null;
 }
 
 private boolean addNoneValues(String noneValue1, String noneValue2, boolean add){
	String value1 = getValue(noneValue1);
	String value2 = getValue(noneValue2);
	if (value1!=null && value2!=null && !value1.equals(value2))	return false;
	if (!add) return true;
	
	Set<String> coset1 = getCoset(noneValue1);
	Set<String> coset2 = getCoset(noneValue2);
	if (coset1!=null && coset2==null){
		/*if (value2!=null ){
			if (!addValues(value2, noneValue1)) Test.check(false);
		}else if (value1!=null){
			if (!addValue(value1, noneValue2)) Test.check(false);
		}*/
		coset1.add(noneValue2);
	}else if (coset2!=null && coset1==null){
		
		/*if (value1!=null ){
			if (!addValues(value1, noneValue2)) Test.check(false);
		}else if (value2!=null){
			if (!addValue(value2, noneValue1)) Test.check(false);
		}*/
		coset2.add(noneValue1);
	}else if (coset2==null && coset1==null){
		Set<String> set = new HashSet<String>();
		set.add(noneValue1);
		set.add(noneValue2);
		this.noneValuesBelongingToCoset.add(noneValue1);
		this.noneValuesBelongingToCoset.add(noneValue2);
		this.noneValueCosets.add(set);
		/*
		if (value1!=null){
			if (!addValue(value1,noneValue2)) Test.check(false);
		}else if (value2!=null){
			if (!addValue(value2, noneValue1)) Test.check(false);
		}*/
	}else{
		if (coset1 != coset2){
			coset1.addAll(coset2);
			noneValueCosets.remove(coset2);
		}
	}
	changed = true;
	if (value1==null && value2!=null && !addValues(value2, noneValue1, add)) Test.check(false);
	if (value2==null && value1!=null && !addValues(value1, noneValue2, add)) Test.check(false);
	return true;
 }
 
 
//NEW
		public boolean isValue(String value){
			if (value==null) return true;
			if (graphL.getRule()!=null)
			for (Parameter p : graphL.getRule().getParameters()){
				if (value.equals(p.getName())) return false;
			}
			if (graphR.getRule()!=null)
			for (Parameter p : graphR.getRule().getParameters()){
				if (value.equals(p.getName())) return false;
			}
			
			boolean result = 
				(
				(
					((value.charAt(0)=='"' && value.charAt(value.length()-1)=='"') ||
					(value.charAt(0)=='\'' && value.charAt(value.length()-1)=='\'') )
					&&
					(!value.substring(1, value.length()-1).contains("\""))
					&&
					(!value.substring(1, value.length()-1).contains("\'"))
				)
				||isNumberValue(value)
				|| !(value.contains("+") 
						|| value.contains("-") 
						|| value.contains("/")
						|| value.contains("*"))
				);
			return result;
		}
		//NEW
		public static boolean isNumberValue(String number){
			try{
				Integer.valueOf(number);
			}catch(NumberFormatException e){
				try{
					Double.valueOf(number);
				}catch(NumberFormatException exception){
					return false;
				}
			}
			return true;
		}
		//NEW
		public static boolean isParam(String param){
			boolean specialChar = //param.contains("\"") ||
								  param.contains("\'") ||
								  param.contains("+")  ||
								  param.contains("-")  ||
								  param.contains("\\") ||
								  param.contains("/")  ||
								  param.contains("%")  ||
								  param.contains("*")  || 
								  isNumberValue(param);
			return !specialChar;
		}

 
 public boolean corresponds(Attribute att1, Attribute att2){
	 if (!ConcurrentRuleUtil.equivalent(att1, att2)) {
		 return false;
	 };
	 String attribute1 = att1.getValue();
	 String attribute2 = att2.getValue();
	 if (attribute1 == null && attribute2 == null) {
		 return true;
	 }
	 for (String noneValue : this.noneValue2Value.keySet()){
		String value = this.noneValue2Value.get(noneValue);
		if (attribute1.equals(value)){
			return attribute2.equals(noneValue);
		}
		if (attribute2.equals(value)){
			return attribute1.equals(noneValue);
		}
	 }
	 for(Set<String> coset : this.noneValueCosets){
		 boolean found = false;
		 for (String noneValue : coset){
			 if (noneValue.equals(attribute1)){
				 found = true;
				 break;
			 }
		 }
		 if (found){
			 for (String noneValue : coset){
				 if (noneValue.equals(attribute2)){
					 return true;
				 }
			 } 
		 }
	 }
	 return false;
 }
 
	//NEW
	private String getValueFromExpression(Attribute a){
		String value = a.getValue();
		if (!isValue(value)){
			value = a.getValue().hashCode()+"";
		}
		return value;
	}
 
 private void updateRuleNodeAttributeMarkers(Rule rule){
	 //MappingListImpl attributeMapping = new MappingListImpl();
	 for (Node nodeR : rule.getRhs().getNodes()){
		 Node nodeL = rule.getMappings().getOrigin(nodeR);
		 if (nodeL==null){
			for(Attribute aR: nodeR.getAttributes()){
				 ((TAttribute)aR).setMarkerType(RuleUtil.NEW);
			 }
		 }else{
			 for (Attribute aR : nodeR.getAttributes()){
				 boolean aLFound = false;
				 for (Attribute aL : nodeL.getAttributes()){
					 if (aL.getType().getName().equals(aR.getType().getName())){
						 if (this.corresponds(aL, aR)){
							 
							 ((TAttribute)aR).setMarkerType(null);
							 aLFound = true;
							 break;
						 }
					 }
				 }
				 if (!aLFound){
					 ((TAttribute)aR).setMarkerType(RuleUtil.NEW);
				 }
			 }
		 }
	 }
 }
 
 
 public void replaceAttributeHashValuesWithInitialExpressionsAndSetMarkers(Rule rule, HashMap<String, String> id2Value){
	 for(Node nodeR : rule.getRhs().getNodes()){
		 for (Attribute attribute : nodeR.getAttributes()){
			 String id = attribute.getValue();
			 
			 String value = id2Value.get(id);
			 if(value==null){
				 id = "\""+id+"\"";
				 value = id2Value.get(id);
			 }
			 attribute.setValue(value);
		 }
	 }
	 
	 for(Node nodeL : rule.getLhs().getNodes()){
		 for (Attribute attribute : nodeL.getAttributes()){
			 String id = attribute.getValue();
			 
			 String value = id2Value.get(id);
			 if(value==null){
				 id = "\""+id+"\"";
				 value = id2Value.get(id);
			 }
			 if (value==null){
				 System.out.println(attribute.getType().getName()+"with "+id+" has no value for "+value);
			 }
			 attribute.setValue(value);
		 }
	 }
	 
	 this.updateRuleNodeAttributeMarkers(rule);
 }
 
 
 private boolean correspondingHash(String hashValue, String attributeValue){
	 try{
		 int hash = Integer.valueOf(hashValue);
		 if (hash==attributeValue.hashCode()){
			 return true;
		 }
	 }catch(Exception e){
	 }
	 return (hashValue.equals("\""+attributeValue.hashCode()+"\"") || hashValue.equals("\'"+attributeValue.hashCode()+"\'"));
 }
 
 private boolean replaceHashValueWithInitialValue(Attribute attribute){
	 
	 String hashCode = attribute.getValue();
	 /**
	 for (Set<String> coSet : this.noneValueCosets){
		 for (String noneValue : coSet){
			 System.out.println("None Value "+noneValue);
			 if (correspondingHash(hashCode, noneValue)){
				 System.out.println(hashCode+ "corresponds to "+noneValue);
				 attribute.setValue(noneValue);
				 return true;
			 }
		 }
	 }
	 
	 for (String noneValue : this.noneValue2Value.keySet()){
		 String value = this.noneValue2Value.get(noneValue);
		 if (correspondingHash(hashCode, noneValue)){
			 System.out.println(hashCode+ "corresponds to "+noneValue);
			 attribute.setValue(noneValue);
			 return true;
		 }
		 
		 if (correspondingHash(hashCode, value)){
			 System.out.println(hashCode+ "corresponds to "+value);
			 attribute.setValue(value);
			 return true;
		 }
	 }**/
	 String value= this.hash2Value.get(hashCode);
	 if (value!=null){
		 attribute.setValue(value);
		 return true;
	 }else
	 return false;
 }
 
 
 
 
 /*********************************************************************************/
 
 private Graph2GraphCopyMappingList graphL2GraphCopy;
	private Graph2GraphCopyMappingList graphR2GraphCopy;
	private Graph graphL;
	private Graph graphR;
	private Set<Edge> edges;
	//private Set<Node> nodes;

	public Graph2GraphCopyMappingList getGraphL2GraphLCopy() {
		return graphL2GraphCopy;
	}

	public Graph2GraphCopyMappingList getGraphR2GraphRCopy() {
		return graphR2GraphCopy;
	}

	public Graph getGraphL() {
		return graphL;
	}

	public Graph getGraphR() {
		return graphR;
	}
	
	//return either isConsitently Present, is present but not consistent(oronly partially present)or isnot present at all (unknown)
	private Boolean isConsistent(Node origin, Node image){
		if (containsOrigin(origin) && containsImage(image)){
			return (getImage(origin, image.getGraph())==getOrigin(image));
		}else if (containsOrigin(origin) || containsImage(image)){
			return false;
		}else return null;
	}
	
	
	public boolean addIntersection(Node nodeL, Node nodeR){
		if (!validIntersection(nodeL, nodeR, true)){
			return false;
		}
		this.add(nodeL, nodeR);
		return true;
	}
	
	public boolean potentialIntersection(Node nodeL, Node nodeR){
		return validIntersection(nodeL, nodeR, false);
	}
	
	private boolean validIntersection(Node nodeL, Node nodeR, boolean add){
		if (this.getImage(nodeL, nodeR.getGraph())==nodeR) return true;
		if (!ConcurrentRuleUtil.sameNodeType(nodeL, nodeR)) return false;
		Boolean isConsistent = isConsistent(nodeL, nodeR);
		if (isConsistent!=null && !isConsistent) return false;
		//if (!checkBijectivity(nodeL, nodeR)) return false;
		return isIntersectingNodes(nodeL, nodeR, add);
	}
	
	
	private boolean checkBiject(
			Node iNodeGraphCopyL, 
			Node iNodeGraphCopyR){
		boolean inComingEdges = false;
		boolean nodesSwaped = false;
		do{
			EList<Edge> edgesiNodeGraphCopyL = (inComingEdges ? iNodeGraphCopyL.getIncoming() : iNodeGraphCopyL.getOutgoing());
			for (Edge edgeiNodeGraphCopyL : edgesiNodeGraphCopyL){
				Node iNodeGraphCopyLAssociate = (inComingEdges ? edgeiNodeGraphCopyL.getSource() : edgeiNodeGraphCopyL.getTarget());
				//skip each associated node that is not part of the intersection
				boolean partOfIntersection = (nodesSwaped ? this.containsImage(iNodeGraphCopyLAssociate) :
														    this.containsOrigin(iNodeGraphCopyLAssociate));
				if (!partOfIntersection) continue;
				boolean iNodeGraphCopyRAssociateFound = false;
				EList<Edge> edgesiNodeGraphCopyR = (inComingEdges ? iNodeGraphCopyR.getIncoming() : iNodeGraphCopyR.getOutgoing());
				for (Edge edgeiNodeGraphCopyR : edgesiNodeGraphCopyR){
					Node iNodeGraphCopyRAssociate = (inComingEdges ? edgeiNodeGraphCopyR.getSource() : edgeiNodeGraphCopyR.getTarget());
					boolean intersectingNodes = (nodesSwaped ? 
							isIntersectingNodes(iNodeGraphCopyRAssociate, iNodeGraphCopyLAssociate, false) :
							isIntersectingNodes(iNodeGraphCopyLAssociate, iNodeGraphCopyRAssociate, false));
					if (intersectingNodes){
						boolean consistent = (nodesSwaped ? 
								this.getImage(iNodeGraphCopyRAssociate)!=iNodeGraphCopyLAssociate 
								|| this.getOrigin(iNodeGraphCopyLAssociate)!=iNodeGraphCopyRAssociate :
								this.getImage(iNodeGraphCopyLAssociate)!=iNodeGraphCopyRAssociate 
								|| this.getOrigin(iNodeGraphCopyRAssociate)!=iNodeGraphCopyLAssociate);
						if (!consistent) continue;
						iNodeGraphCopyRAssociateFound = true;
					}
				}
				if (!iNodeGraphCopyRAssociateFound) {
					System.out.println("Nonbijective, edge missing in GraphR");
					return false;
				}
			}
			if (inComingEdges && nodesSwaped) break;
			//false -> true -> false -> true
			inComingEdges=!inComingEdges;
			//true -> false -> true
			if (!inComingEdges){//false -> true-> false
				Node tmp = iNodeGraphCopyL;
				iNodeGraphCopyL = iNodeGraphCopyR;
				iNodeGraphCopyR = tmp;
				//false -> true -> true
				nodesSwaped = true;
			}
		} while(true);
		return true;
	}
	
	public Attribute getImage(Attribute origin) {
		return super.getImage(origin, graphR2GraphCopy.getGraphCopy());
	}

	public <E extends GraphElement> E getImage(E origin) {
		return super.getImage(origin, graphR2GraphCopy.getGraphCopy());
	}

	public boolean containsOrigin(Node node){
		return this.getImage(node)!=null;
	}
	
	public boolean containsImage(Node node){
		return this.getOrigin(node)!=null;
	}
 
 
 
}
