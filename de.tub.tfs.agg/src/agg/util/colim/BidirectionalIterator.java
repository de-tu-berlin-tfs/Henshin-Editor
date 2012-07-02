package agg.util.colim;

/**
 * BidirectionalIterator is the interface of all iterators that can
 * read and/or write one item at a time in a forwards or backwards direction.
 */

public interface BidirectionalIterator extends ForwardIterator {
	
  public Object clone();

  /**
   * Retreat by one.
   */
  public void retreat();

  /**
   * Retreat by a specified amount.
   * @param n The amount to retreat.
   */
  public void retreat( int n );
}
