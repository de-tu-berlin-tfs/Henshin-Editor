
package agg.util.colim;


/**
 * A IntIterator allows you to iterate through
 * the contents of a IntBuffer.
 */

public final class IntIterator implements BidirectionalIterator {
  /**
   * Return an iterator positioned at the first element of a particular array.
   * @param array The array whose first element I will be positioned at.
   */
  public static IntIterator begin( int[] array ){
    return new IntIterator( array, 0 );
  }

  /**
   * Return an iterator positioned immediately after the last element of a particular array.
   * @param array The array whose last element I will be positioned after.
   */
  public static IntIterator end( int[] array ){
    return new IntIterator( array, array.length );
  }

  int buffer[];
  Container original;
  int index;

  /**
   * Construct myself to be an iterator with no associated data structure or position.
   */
  public IntIterator(){
    this( new IntArray(), 0 );
  }

  /**
   * Construct myself to be a copy of an existing iterator.
   * @param iterator The iterator to copy.
   */
  public IntIterator( IntIterator iterator ){
    buffer = iterator.buffer;
    original = iterator.original;
    index = iterator.index;
  }

  /**
   * Construct myself to be positioned at a particular index of a specific
   * array of ints.
   */
  public IntIterator( int[] vector, int index ){
    this( new IntArray( vector ), index );
  }

  /**
   * Construct myself to be positioned at a particular index of a specific IntArray.
   */
  public IntIterator( IntArray vector, int index ){
    buffer = vector.array;
    original = vector;
    this.index = index;
  }

  /**
   * Construct myself to be positioned at a particular index of a specific IntBuffer.
   * @param vector My associated IntBuffer.
   * @param index My associated index.
   */
  public IntIterator( IntBuffer vector, int index ){
    buffer = vector.storage;
    original = vector;
    this.index = index;
  }

  /**
   * Return a clone of myself.
   */
  public Object clone(){
    return new IntIterator( this );
  }

  /**
   * Return true if a specified object is the same kind of iterator as me
   * and is positioned at the same element.
   * @param object Any object.
   */
  public boolean equals( Object object ){
    return object instanceof IntIterator && equals( (IntIterator)object );
  }

  /**
   * Return true if iterator is positioned at the same element as me.
   * @param iterator The iterator to compare myself against.
   */
  public boolean equals( IntIterator iterator ){
    return iterator.index == index && iterator.buffer == buffer;
  }

  /**
   * Return true if I'm positioned at the first item of my input stream.
   */
  public boolean atBegin(){
    return index == 0;
  }

  /**
   * Return true if I'm positioned after the last item in my input stream.
   */
  public boolean atEnd(){
    return index == original.size();
  }

  /**
   * Return true if there are more elements in my input stream.
   */
  public boolean hasMoreElements(){
    return index < original.size();
  }

  /**
   * Advance by one.
   */
  public void advance(){
    ++index;
  }

  /**
   * Advance by a specified amount.
   * @param n The amount to advance.
   */
  public void advance( int n ){
    index += n;
  }

  /**
   * Retreat by one.
   */
  public void retreat(){
    --index;
  }

  /**
   * Retreat by a specified amount.
   * @param n The amount to retreat.
   */
  public void retreat( int n ){
    index -= n;
  }

  /**
   * Return the next element in my input stream.
   * @exception java.util.NoSuchElementException If I'm positioned at an invalid index.
   */
  public Object nextElement(){
    try{
      Object obj = get();
      advance();
      return obj;
    }catch ( IndexOutOfBoundsException ex ){
      throw new java.util.NoSuchElementException( "IntIterator" );
    }
  }

  /**
   * Return the object at my current position.
   * @exception java.lang.ArrayIndexOutOfBoundsException If I'm positioned at an invalid index.
   */
  public Object get(){
    return get( 0 );
  }
  
  public int getInt(){
    return getInt( 0 );
  }

  /**
   * Return the object that is a specified distance from my current position.
   * @param offset The offset from my current position.
   * @exception java.lang.ArrayIndexOutOfBoundsException If the adjusted index is invalid.
   */
  public Object get( int offset ){
    return new Integer( buffer[ index + offset ] );
  }
  
  public int getInt( int offset ){
    return buffer[ index + offset ];
  }

  /**
   * Set the object at my current position to a specified value.
   * @param object The object to be written at my current position.
   * @exception java.lang.ArrayIndexOutOfBoundsException If I'm positioned at an invalid index.
   */
  public void put( Object object ){
    put( 0, object );
  }
  
  public void put( int object ){
    put( 0, object );
  }

  /**
   * Write an object at a specified distance from my current position.
   * @param offset The offset from my current position.
   * @param object The object to write.
   * @exception java.lang.ArrayIndexOutOfBoundsException If the adjusted index is invalid.
   */
  public void put( int offset, Object object ){
    put( offset, IntBuffer.asInt( object ) );
  }
  
  public void put( int offset, int object ){
    buffer[ index + offset ] = object;
  }

  /**
   * Return the distance from myself to another iterator.
   * I should be before the specified iterator.
   * @param iterator The iterator to compare myself against.
   * @exception IllegalArgumentException If the iterators are incompatible.
   */
  public int distance( ForwardIterator iterator ){
    if ( !isCompatibleWith( iterator ) )
      throw new IllegalArgumentException( "iterators not compatible" );
    return ( (IntIterator)iterator ).index - index;
  }

  /**
   * Return my current index.
   */
  public int index(){
    return index;
  }

  /**
   * Return my associated array.
   */
  public Container getContainer(){
    return original;
  }

  /**
   * Return true if both <CODE>iterator</CODE> and myself can be used
   * as a range.
   */
  public boolean isCompatibleWith( InputIterator iterator ){
    return iterator instanceof IntIterator && buffer == ( (IntIterator)iterator ).buffer;
  }

}
