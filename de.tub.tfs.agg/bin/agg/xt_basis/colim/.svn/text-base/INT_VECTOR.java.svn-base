// $Id: INT_VECTOR.java,v 1.2 1998/04/07 13:34:10 mich Exp $
package agg.xt_basis.colim;


import agg.util.colim.IntBuffer;


//-------------------------------------------------------------------
//                 dynamic integer array          
//-------------------------------------------------------------------

//  Copyright (c) 1995 Technical University of Berlin, Dept TFS.
//  All rights reserved.

//  Colimes Computation Project  V1.0 98/02/15  
//  Author: Dietmar Wolz, Technical University Berlin FB 13, WE 1331 
//          email: dietmar@cs.tu-berlin.de
//-------------------------------------------------------------------


public class INT_VECTOR extends IntBuffer {
	
  public INT_VECTOR() { 
    clear();	  
  }

  public INT_VECTOR(int size) {
    clear();
    ensureCapacity(size);    
  }

  public INT_VECTOR(IntBuffer buf) {     
    super(buf);
  }

  public void push_back(int i) { 
    super.add(i); 
  }

  public int item(int index) { 
    return super.intAt(index); 
  }

  public void put(int i, int index) { 
    super.put(index, i); 
  }

}






