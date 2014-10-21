package agg.util.colim;


/**
 * ForwardIterator is the interface of all iterators that can
 * read and/or write one item at a time in a forward direction.
 */

public interface ForwardIterator extends InputIterator, OutputIterator
  {
  /**
   * Advance by one.
   */
  public void advance();

  /**
   * Advance by a specified amount.
   * @param n The amount to advance.
   */
  public void advance( int n );

  /**
   * Return the object that is a specified distance from my current position.
   * @param offset The offset from my current position.
   */
  public Object get( int offset );

  /**
   * Replace the object at a specified distance from my current position.
   * @param offset The offset from my current position.
   * @param object The object to write.
   */
  public void put( int offset, Object object );

  /**
   * Return the distance from myself to another iterator.
   * I should be before the specified iterator.
   * @param iterator The iterator to compare myself against.
   */
  public int distance( ForwardIterator iterator );

  /**
   * Return a clone of myself.
   */
  public Object clone();

  /**
   * Return my associated container.
   */
  public Container getContainer();
  }
