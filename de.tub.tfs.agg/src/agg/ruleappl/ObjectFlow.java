/**
 * 
 */
package agg.ruleappl;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Rule;


/**
 * Stores related objects of two rules:
 * an output object which belongs to RHS of the first rule
 * is related to
 * an input object which belongs to LHS of the second rule.
 * 
 * Having a co-match of the first rule and match of the second rule 
 * we can find an object of the host graph which corresponds to
 * the output of the first rule and input of the second rule :
 * 
 * graph_object =  comatch.getImage(object_flow.getOutput(input)
 * 
 * match.addMapping(input, graph_object)
 * 
 * 
 * @author olga
 *
 */
public class ObjectFlow {
	
	final Hashtable<Object, Object> outputInputMap;
	

	final Object srcOfOutput;  // Graph | Rule
	final Object srcOfInput;	// Rule
	int indxOfOutput = -1, indxOfInput = -1;
		
	/**
	 * 
	 * @param sourceOfOutput	output object can be Graph or Rule which RHS contains output objects
	 * @param sourceOfInput		input object is a Rule which LHS contains input objects
	 * @param indxOfOutput  index of the output object
	 * @param indxOfOutput  index of the input object
	 */
	public ObjectFlow(final Object sourceOfOutput, final Object sourceOfInput,
						int indxOfOutput, int indxOfInput) {
				
		this.srcOfOutput = sourceOfOutput;
		this.srcOfInput = sourceOfInput;
		this.indxOfOutput = indxOfOutput;
		this.indxOfInput = indxOfInput;
		
		this.outputInputMap = new Hashtable<Object, Object>();
	}
	
	public ObjectFlow(final Object sourceOfOutput, final Object sourceOfInput,
			int indxOfOutput, int indxOfInput,
			final Hashtable<Object, Object> outputInputMap) {
	
		this.srcOfOutput = sourceOfOutput;
		this.srcOfInput = sourceOfInput;
		this.indxOfOutput = indxOfOutput;
		this.indxOfInput = indxOfInput;	
		
		this.outputInputMap = outputInputMap;
	}
	
	public boolean isGraphExtended() {
		return (this.srcOfOutput instanceof Graph);
	}
	
	public Object getSourceOfOutput() {
		return this.srcOfOutput;
	}
		
	public Object getSourceOfInput() {
		return this.srcOfInput;
	}
	
	public int getIndexOfOutput() {
		return this.indxOfOutput;
	}
	
	public int getIndexOfInput() {
		return this.indxOfInput;
	}
	
	public String getNameOfOutput() {
		if (this.srcOfOutput instanceof Graph)
			return ((Graph) this.srcOfOutput).getName();
		else if (this.srcOfOutput instanceof Rule)
			return ((Rule) this.srcOfOutput).getName();
		else return "";
	}
		
	public String getNameOfInput() {
		if (this.srcOfInput instanceof Rule)
			return ((Rule) this.srcOfInput).getName();
		return "";
	}
	
	public boolean isSourceOfOutput(final Object src) {
		return (src == this.srcOfOutput);
	}
	
	public boolean isSourceOfInput(final Object src) {
		return (src == this.srcOfInput);
	}
	
	public void addMapping(final Object output, final Object input) {
		if (output != null && input != null)
			this.outputInputMap.put(output, input);
	}

	public void removeMapping(final Object output) {
		if (output != null) {
			this.outputInputMap.remove(output);
		}
	}

	/**
	 * 
	 * @return  mappings 
	 * 					where first object is an output 
	 * 					and second object is an input
	 * 					of the object flow
	 */
	public Hashtable<Object, Object> getMapping() {
		return this.outputInputMap;
	}
	
	public int getSizeOfInput() {		
		return this.outputInputMap.size();
	}
	
	/**
	 * Returns an output (Graph)object for the specified input (Graph)object
	 * if it exists, otherwise null.
	 * @param input	(Graph)object 
	 * @return	output (Graph)object 
	 */
	public Object getOutput(final Object input) {
		if (input != null) {
			Enumeration<Object> keys = this.outputInputMap.keys();
			while (keys.hasMoreElements()) {
				Object out = keys.nextElement();
				if (this.outputInputMap.get(out) == input) {
					return out;
				}
			}
		}		
		return null;
	}
	
	public Object getConnectedInput(
			final ObjectFlow otherObjFlow, // ObjectFlow before
			final Object myInputObj) {
		
		Object outObj = this.getOutput(myInputObj);
		if (this.srcOfOutput instanceof Rule
				&& outObj instanceof GraphObject) {
			Enumeration<GraphObject> inv = ((Rule)this.srcOfOutput).getInverseImage((GraphObject) outObj);
			if (inv.hasMoreElements()) {
				return inv.nextElement();
			}				
		}
		return null;
	}
	
	public Object getConnectedOutput(
			final ObjectFlow otherObjFlow, // ObjectFlow before
			final Object myInputObj) {
		
		Object outObj = this.getOutput(myInputObj);
		if (this.srcOfOutput instanceof Rule
				&& outObj instanceof GraphObject) {
			Enumeration<GraphObject> inv = ((Rule)this.srcOfOutput).getInverseImage((GraphObject) outObj);
			if (inv.hasMoreElements()) {
				GraphObject otherInput = inv.nextElement();
				return otherObjFlow.getOutput(otherInput);
			}				
		}
		return null;
	}
	
	public boolean isInputObject(final Object obj) {
		Iterator<Object> inputs = this.outputInputMap.values().iterator();
		while (inputs.hasNext()) {
			if (inputs.next() == obj)
				return true;
		}
		return false;
	}
	
	public boolean isOutputObject(final Object obj) {
		Enumeration<Object> outputs = this.outputInputMap.keys();
		while (outputs.hasMoreElements()) {
			if (outputs.nextElement() == obj)
				return true;
		}
		return false;
	}
	
	/**
	 * Returns an input (Graph)object for the specified output (Graph)object
	 * if exists, otherwise null.
	 * @param output	(Graph)object 
	 * @return	input	 (Graph)object 
	 */
	public Object getInput(final Object output) {
		if (output != null) {
			return this.outputInputMap.get(output);
		}
		
		return null;
	}
	
	public List<Object> getInputs() {
		return new Vector<Object> (this.outputInputMap.values());
	}
	
	public boolean isEmpty() {
		return this.outputInputMap.isEmpty();
	}
	
	public String getKey() {
		return String.valueOf(this.indxOfOutput)
								.concat(":")
								.concat(String.valueOf(this.indxOfInput));
	}
	
	public boolean compareTo(final ObjectFlow objFlow) {
		if (objFlow == null)
			return false;
		
		List<Object> keys1 = new Vector<Object>(this.outputInputMap.keySet());
		List<Object> keys2 = new Vector<Object>(objFlow.getMapping().keySet());
		if (keys1.size() == keys2.size()
				&& keys1.containsAll(keys2)) {
			for (int i=0; i<keys1.size(); i++) {
				if (this.outputInputMap.get(keys1.get(i)) 
						!= objFlow.getMapping().get(keys1.get(i))) {
					return false;
				}
			}
		} else {
			return false;
		}
		
		return true;
	}
	
	
}
