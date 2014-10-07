// $Id: COLIM_PARTITION.java,v 1.2 1998/04/07 13:34:10 mich Exp $
package agg.xt_basis.colim;
//-------------------------------------------------------------------
//                Colimit Basic Data Type COLIM_PARTITION             
//-------------------------------------------------------------------

//  Copyright (c) 1995 Technical University of Berlin, Dept TFS.
//  All rights reserved.

//  Colimes Computation Project V1.0 98/02/15 
//  Author: Dietmar Wolz, Technical University Berlin FB 13, WE 1331 
//          email: dietmar@cs.tu-berlin.de
//-------------------------------------------------------------------


class COLIM_PARTITION extends INT_VECTOR {

  public int find(int attr)
  { 
    if (item(attr) < 0) return attr;
    else {
      int Result = find(item(attr));
      put(Result,attr);
      return Result;
    }
  }

  public void make_block()
  {
    push_back(-1); // append -1, denotes new block
  }
 
  public int union_elements(int attr1, int attr2)
  {
    int r1 = find(attr1);
    int r2 = find(attr2);
    if (r1 == r2) return r1;
    if (item(r1) < item(r2)) {
      // r1 is higher - becomes new root
      put(r1,r2);
      return r1;
    }
    else if (item(r1) > item(r2)) {
      // r2 is higher - becomes new root
      put(r2,r1);
      return r2;
    }
    else {
      // r1 and r2 have same height 
      // r1 becomes new root (height is incremented)
      put(item(r1)-1,r1); 
      put(r1,r2);
      return r1;
    }
  }

  boolean is_equivalent(int attr1, int attr2)
  {
    return find(attr1) == find(attr2);
  }
 
}





