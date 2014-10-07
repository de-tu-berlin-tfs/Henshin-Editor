package agg.util.colim;

import java.util.Enumeration;

/**
 * IntArray allows a native array of ints to be accessed.
 */

public class IntArray implements Container {
	
	static final int DEFAULT_SIZE = 10;
	static final int THRESHOLD = 2000;
	static final int MULTIPLIER = 2;
	static final int HASH_SIZE = 16;
	
	int array[];

	/**
	 * Construct myself to refer to an empty array.
	 */
  	public IntArray() {
  		this( new int[ 0 ] );
  	}

	/**
	 * Construct myself to refer to an existing IntArray.
	 * @param array The IntArray to copy.
	 */
  	public IntArray( IntArray array ){
		this( array.array );
	}
	
	/**
	 * Construct myself to be a copy of an existing IntBuffer.
	 * @param buffer The IntBuffer to copy.
	 */
  	public IntArray( IntBuffer buffer ){
		this( buffer.get() );
	}
	
	/**
	 * Construct myself to refer to a native Java array.
	 * @param array The int[] to ape.
	 */
	public IntArray( int array[] ){
		this.array = array;
	}
	
	/**
	 * Return a shallow copy of myself.
	 */
	public synchronized Object clone(){
		return new IntArray( this );
	}

	/**
	 * Return a string that describes me.
	 */
	@SuppressWarnings("rawtypes")
	public synchronized String toString(){
		StringBuffer buffer = new StringBuffer( "int[]" );
		buffer.append( "(" );
		boolean first = true;
		Enumeration iter = this.begin();
		while ( iter.hasMoreElements() ){
		    if ( first ){
			    buffer.append( " " );
			    first = false;
		    }
		    else
		    	buffer.append( ", " );
		    buffer.append( iter.nextElement() );
		}
		if ( first )
			buffer.append( ")" );
		else
		    buffer.append( " )" );
		return buffer.toString();   
	}
	
	/**
	 * Return true if I'm equal to a specified object.
	 * @param object The object to compare myself against.
	 * @return true if I'm equal to the specified object.
	 */
	public boolean equals( Object object ){
	    return 
	      object instanceof IntArray && equals( (IntArray)object )
	      || object instanceof IntBuffer && equals( (IntBuffer)object );
	}
	
	/**
	 * Return true if I contain the same items in the same order as
	 * another IntArray.
	 * @param object The IntArray to compare myself against.
	 * @return true if I'm equal to the specified object.
	 */
	public boolean equals( IntArray object ){
	    return equals( object.array );
	}

	/**
	 * Return true if I contain the same items in the same order as
	 * another IntBuffer.
	 * @param buffer The IntBuffer to compare myself against.
	 */
	public boolean equals( IntBuffer buffer ){
		return equals( buffer.storage );
    }

	/**
	 * Return true if I contain the same items in the same order as
	 * a native array of ints.
	 * @param array The array to compare myself against.
	 */
	public synchronized boolean equals( int array[] ){
	    synchronized( array ){
	    	if ( this.array.length != array.length )
	    		return false;
	
	    	int i = 0;
	    	while ( i < array.length ){
	    		if ( this.array[ i ] != array[ i ] )
	    			return false;
	    		++i;
	        }
	    }
	    return true;
    }

	/**
	 * Retrieve the underlying primitive array.
	 */
	public int[] get(){
		return array;
    }

	/**
	 * Return the number of objects that I contain.
	 */
	public int size(){
		return array.length;
    }

	/**
	 * Return the maximum number of objects that I can contain.
	 */
	public int maxSize(){
		return array.length;
    }

	/**
	 * Return an Enumeration of my components.
	 */
	@SuppressWarnings("rawtypes")
	public Enumeration elements(){
		return new IntIterator( this, 0 );
    }

	/**
	 * Return an iterator positioned at my first item.
	 */
	public synchronized IntIterator begin(){
		return new IntIterator( this, 0 );
    }

	/**
	 * Return an iterator positioned immediately after my last item.
	 */
	public synchronized IntIterator end(){
		return new IntIterator( this, array.length );
    }

	/**
	 * Return the Integer object at the specified index.
	 * @param index The index.
	 */
	public Object at( int index ){
		return new Integer( intAt( index ) );
    }

	/**
	 * Return the int at the specified index.
	 * @param index The index.
	 */
	public synchronized int intAt( int index ){
		return array[ index ];
    }

	/**
	 * Return the index of the first object that matches a particular value, or
	 * -1 if the object is not found.  Uses .equals() to find a match
	 * @param object The object to find.
	 * @exception java.lang.ClassCastException if objects are not Boolean
	 */
	public int indexOf( Object object ){
		return indexOf( 0, size() - 1, object );
    }


	/**
	 * Return an index positioned at the first object within a specified range that
	 * matches a particular object, or -1 if the object is not found.
	 * @param first The index of the first object to consider.
	 * @param last The index of the last object to consider.
	 * @param object The object to find.
	 * @exception java.lang.IndexOutOfBoundsException If either index is invalid.
	 * @exception java.lang.ClassCastException if objects are not Boolean
	 */
	public synchronized int indexOf( int first, int last, Object object ){
		for ( int i=first; i <= last; i++ )
			if ( at( i ).equals( object ) )
				return i;

		return -1;
    }
 
	/**
	 * Return the number of objects within a specified range of that match a
	 * particular value.  the range is inclusive
	 * @param first The index of the first object to consider.
	 * @param last The index of the last object to consider.
	 * @exception java.lang.IndexOutOfBoundsException If either index is invalid.
	 */
	public synchronized int count( int first, int last, Object object ){
		int count = 0;

		for ( int i=first; i <= last; i++ )
			if ( at( i ).equals( object ) )
				++count;

		return count;
    }

	/**
	 * Replace all elements that match a particular object with a new value and return
	 * the number of objects that were replaced.
	 * @param oldValue The object to be replaced.
	 * @param newValue The value to substitute.
	 */
	public int replace( Object oldValue, Object newValue ){
		return replace( 0, size() - 1, oldValue, newValue );
    }
	
	/**
	 * Replace all elements within a specified range that match a particular object
	 * with a new value and return the number of objects that were replaced.
	 * @param first The index of the first object to be considered.
	 * @param last The index of the last object to be considered.
	 * @param oldValue The object to be replaced.
	 * @param newValue The value to substitute.
	 * @exception java.lang.IndexOutOfBoundsException If either index is invalid.
	 */
	public synchronized int replace( int first, int last, Object oldValue, Object newValue ){
		int count = 0;
		for ( int i=first; i <= last; i++ )
			if ( at( i ).equals( oldValue ) ){
				put( i, newValue );
				++count;
        }
		return count;
    }

	/**
	 * Return true if I contain a particular object using .equals()
	 * @param object The object in question.
	 */
	public boolean contains( Object object ){
		return indexOf( object ) != -1;
    }
	
	/**
	 * Set the object at a specified index.  The object must be a Number
	 * @param index The index.
	 * @param object The object to place at the specified index.
	 * @exception java.lang.ClassCastException if object is not a Number
	 * @exception java.lang.IndexOutOfBoundsException if index is invalid.
	 */
	public void put( int index, Object object ){
		put( index, ( (Number)object ).intValue() );
    }

	/**
	 * Set the value of a specified index.
	 * @param index The index.
	 * @param object The int to place at the specified index.
	 * @exception java.lang.IndexOutOfBoundsException if index is invalid.
	 */
	public synchronized void put( int index, int object ){
		array[ index ]  = object;
    }
  
	/**
	 * Return my hash code for support of hashing containers
	 */
	public synchronized int hashCode(){
		return orderedHash( begin(), size() );
    }

	final static int orderedHash( ForwardIterator iter, int length ) {
		int h = 0;
		int position = 0;
		int skip = 1;
		if ( length >= HASH_SIZE ){
			skip = length / HASH_SIZE;
			// insure that first will always exactly reach last
			iter.advance( length % HASH_SIZE );
	    }
		while ( iter.hasMoreElements() ){
			if ( iter.get() != null )
				h ^= iter.get().hashCode() / ( ( position % HASH_SIZE ) + 1 );
			++position;
			iter.advance( skip );
	    }
		return h;
	}
  
	/**
	 * Return true if I contain no objects.
	 */
	public boolean isEmpty(){
		return size() == 0;
    }

	/**
	 * Return my last element.
	 */
	public Object back(){
		return at( size() - 1 );
    }

	/**
	 * Return my first element.
	 */
	public Object front(){
		return at( 0 );
    }

	/**
	 * Return the number of objects that match a specified object.
	 * @param object The object to count.
	 */
	public int count( Object object ){
		return count( 0, size() - 1, object );
    }

  
	final protected static void checkIndex( int i, int size ){
		if ( i < 0 || i >= size )
			throw new IndexOutOfBoundsException( 
					"Attempt to access index " + i + "; valid range is 0.." + ( size - 1 ));
	}

	final protected static void checkRange( int lo, int hi, int size ){
		checkIndex( lo, size );
		checkIndex( hi, size );
	}
	
	final static int getNextSize( int cursize ){
		// multiply by MULTIPLIER until THRESHOLD reached; increment by THRESHOLD
		// from then on
		int newSize = cursize > THRESHOLD
					  ? cursize + THRESHOLD
					  : cursize * MULTIPLIER;
		return Math.max( 1, newSize );
	}
	
	/**
	 * Remove all of my objects. By default, this method throws an exception.
	 * @exception RuntimeException Thrown by default.
	 */
	public void clear(){
	    throw new RuntimeException( "cannot execute clear() on a native array" );
	}

	/**
	 * Add an object to myself. By default, this method throws an exception.
	 * @param object The object to add.
	 * @exception RuntimeException Thrown by default.
	 */
	public Object add( Object object ){
	    throw new RuntimeException( "cannot execute add() on a native array" );
	}

}
