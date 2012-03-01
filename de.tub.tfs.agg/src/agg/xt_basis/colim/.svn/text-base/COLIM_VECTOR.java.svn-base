// $Id: COLIM_VECTOR.java,v 1.2 1998/04/07 13:34:10 mich Exp $
package agg.xt_basis.colim;
//-------------------------------------------------------------------
//                 dynamic object array          
//-------------------------------------------------------------------

//  Copyright (c) 1995 Technical University of Berlin, Dept TFS.
//  All rights reserved.

//  Colimes Computation Project  V1.0 98/02/15  
//  Author: Dietmar Wolz, Technical University Berlin FB 13, WE 1331 
//          email: dietmar@cs.tu-berlin.de
//-------------------------------------------------------------------

import java.util.Enumeration;
import java.util.Vector;

 
public class COLIM_VECTOR {

	final Vector<Object> v;
	  
    public COLIM_VECTOR() {
    	v = new Vector<Object>();
    }

    public COLIM_VECTOR(int size) { 
    	v = new Vector<Object>(size);
    }

    public COLIM_VECTOR(COLIM_VECTOR buf) {     
    	v = new Vector<Object>(buf.v);
    }

    public void push_back(Object obj) { 
    	v.add(obj);
    }

    public Object item(int index) { 
    	return v.get(index);
    }

    public void put(Object obj, int index) { 
    	v.add(index, obj);
    } 
    
    public int indexOf(Object obj) {
    	return v.indexOf(obj);
    }
    
    public Enumeration elements() {
    	return v.elements();
    }
    
    public int size() {
    	return v.size();
    }
    
    public void setSize(int size) {
  	  	v.setSize(size);
    }

    public void ensureCapacity(int size) {
    	v.ensureCapacity(size);
    }
    
    public void clear() {
    	v.clear();
    }
    
    public void trimToSize() {
    	v.trimToSize();
    }
    
    public String toString() {
    	return v.toString();
    }
}




