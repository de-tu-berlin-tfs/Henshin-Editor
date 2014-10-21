package agg.termination;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import agg.xt_basis.GraGra;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Rule;
import agg.xt_basis.Type;
import agg.util.Pair;


/**
 * This class implements termination conditions of Layered Graph Grammar.
 * 
 * @author $Author: olga $
 * @version $Id: TerminationLGTSInterface.java,v 1.4 2009/02/04 10:11:29 olga Exp $
 */
public interface TerminationLGTSInterface {

	/**
	 * Initialize a termination layers of the grammar. Initially the termination
	 * conditions are invalid.
	 * 
	 * @param gra
	 *            The graph grammar.
	 */
	public void setGrammar(GraGra gra); 

	public void resetGrammar(); 
	
	public GraGra getGrammar();
	

	public List<Rule> getListOfEnabledRules();
		
	public boolean hasGrammarChanged();
	
	public List<Rule> getListOfRules();
	
	public Hashtable<Integer, HashSet<Rule>> getInvertedRuleLayer();

	public Vector<Integer> getOrderedRuleLayer();

	public Hashtable<Integer, HashSet<Object>> getInvertedTypeDeletionLayer();

	public Hashtable<Integer, HashSet<Object>> getInvertedTypeCreationLayer();

	public Hashtable<Integer, Vector<Type>> getDeletionType();

	public Hashtable<Integer, Vector<GraphObject>> getDeletionTypeObject();
	
	public Hashtable<Integer, Pair<Boolean, Vector<Rule>>> getResultTypeDeletion();

	public Hashtable<Integer, Pair<Boolean, Vector<Rule>>> getResultDeletion();

	public Hashtable<Integer, Pair<Boolean, Vector<Rule>>> getResultNondeletion();

	public void resetLayer();

	public void initRuleLayer(Hashtable<?, Integer> init);

	public void initAll(boolean generate);

	public Vector<Object> getCreatedTypesOnDeletionLayer(Integer layer);

	/**
	 * Checks layer conditions .
	 * 
	 * @return true if conditions are valid.
	 */
	public boolean checkTermination();

	/**
	 * A fast check on validity.
	 * 
	 * @return true if the layer function is valid.
	 */
	public boolean isValid();

	/**
	 * Returns an error message if the layer function is not valid.
	 * 
	 * @return The error message.
	 */
	public String getErrorMessage();

	/**
	 * Returns the rule layer of the layer function.
	 * 
	 * @return The rule layer.
	 */
	public Hashtable<Rule, Integer> getRuleLayer();

	public int getRuleLayer(Rule r);

	/**
	 * Returns the creation layer of the layer function.
	 * 
	 * @return The creation layer.
	 */
	public Hashtable<Object, Integer> getCreationLayer();
	
	public int getCreationLayer(Type t);

	public int getCreationLayer(GraphObject t);
	
	/**
	 * Returns the deletion layer of the layer function.
	 * 
	 * @return The deletion layer.
	 */
	public Hashtable<Object, Integer> getDeletionLayer();
	
	public int getDeletionLayer(Type t);
	
	public int getDeletionLayer(GraphObject t);

	/**
	 * Returns the smallest layer of the rule layer.
	 * 
	 * @return The smallest layer.
	 */
	public Integer getStartLayer();

	/**
	 * Inverts a layer function so that the layer is the key and the value is a
	 * set.
	 * 
	 * @param layer
	 *            The layer function will be inverted.
	 * @return The inverted layer function.
	 */
	public Hashtable<Integer, HashSet<Rule>> invertLayer(
			Hashtable<Rule, Integer> layer);

	public void saveRuleLayer();

	public void setGenerateRuleLayer(boolean b);

	public void showLayer();

	/**
	 * Returns the layer function in a human readable way.
	 * 
	 * @return The text.
	 */
	public String toString();


}
