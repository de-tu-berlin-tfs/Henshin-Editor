// $Id: COPROD_OBJECT.java,v 1.2 1998/04/07 13:34:10 mich Exp $
package agg.xt_basis.colim;
//-------------------------------------------------------------------
//                 Colimit Basic Data Type COPROD_OBJECT                     
//-------------------------------------------------------------------

//  Copyright (c) 1995 Technical University of Berlin, Dept TFS.
//  All rights reserved.

//  Colimes Computation Project  V1.0 98/02/15 
//  Author: Dietmar Wolz, Technical University Berlin FB 13, WE 1331 
//          email: dietmar@cs.tu-berlin.de
//-------------------------------------------------------------------


// import COLIM_DEFS;

public class COPROD_OBJECT {
  
  public COPROD_OBJECT(int low, int upp) 
  {
    lower = low;
    upper = upp; 
  }

  public int lower;
  public int upper;

  public String toString() 
  {
    StringBuffer Result = new StringBuffer("{");
    Result.append(lower);
    Result.append(",");
    Result.append(upper);
    Result.append("}");
    return new String(Result);
  }
}
