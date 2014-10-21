// $Id: SIG_DIAGRAM.java,v 1.2 1998/04/07 13:34:10 mich Exp $
package agg.xt_basis.colim;
//-------------------------------------------------------------------
//                 Colimit of a Signature Diagram                            
//-------------------------------------------------------------------

//  Copyright (c) 1995 Technical University of Berlin, Dept TFS.
//  All rights reserved.

//  Colimes Computation Project V1.0 98/02/15  
//  Author: Dietmar Wolz, Technical University Berlin FB 13, WE 1331 
//          email: dietmar@cs.tu-berlin.de
//-------------------------------------------------------------------

// import COLIM_DEFS;
// import COPROD_OBJECT;
// import INT_VECTOR;
// import COLIM_VECTOR;
// import SET_DIAGRAM;
import java.util.Enumeration;

public class SIG_DIAGRAM implements COLIM_DEFS {

  //---------------- creation --------------------------------

  public SIG_DIAGRAM()
  {
    f_sort_diagram = new SET_DIAGRAM();
    f_op_diagram = new SET_DIAGRAM();
    f_coprod_arity = new COLIM_VECTOR();
    f_coprod_arity.push_back(null);
    f_colimit_valid = false;
  }
  
  //---------------- diagram construction --------------------

  @SuppressWarnings("unused")
public int insert_object(COLIM_VECTOR sorts, COLIM_VECTOR ops,
                           COLIM_VECTOR arity, String name)
  {
    int lower = f_sort_diagram.coproduct_size();
    int sort_node = f_sort_diagram.insert_object(sorts,name); 
    int op_node = f_op_diagram.insert_object(ops,name); 
    for (int i = 0; i < arity.size(); i++) {
      INT_VECTOR arity_sorts = (INT_VECTOR) arity.item (i);
      INT_VECTOR cop_sorts = new INT_VECTOR(arity_sorts.size());
      for (int arity_sort = 0; 
               arity_sort < arity_sorts.size(); arity_sort++) 
        cop_sorts.push_back(arity_sorts.item(arity_sort) + lower);
      f_coprod_arity.push_back(cop_sorts);
    }
    f_colimit_valid = false;
    return sort_node;
  }

  @SuppressWarnings("unused")
public int insert_morphism(INT_VECTOR sort_morphism, 
                             INT_VECTOR op_morphism,
                             int v, int w)
  {
    int sort_edge = f_sort_diagram.insert_morphism(sort_morphism,v,w);
    int op_edge = f_op_diagram.insert_morphism(op_morphism,v,w);
    f_colimit_valid = false;
    return sort_edge;
  }

  //---------------- colimit computation ---------------------
 
  public COLIM_VECTOR get_colimit_sorts() 
  {
    return f_sort_diagram.get_colimit_set();
  }

  public COLIM_VECTOR get_colimit_ops() 
  {
    return f_op_diagram.get_colimit_set();
  }

  public COLIM_VECTOR get_colimit_arity() 
  {
    if (!f_colimit_valid) compute_colimit();
    return f_colimit_arity;
  }

  //---------------- utilities ---------------------

  INT_VECTOR get_arity(int operation) 
  {
     return (INT_VECTOR) f_coprod_arity.item(operation);
  }

  SET_DIAGRAM get_sort_diagram() { return f_sort_diagram; }
 
  SET_DIAGRAM get_op_diagram() { return f_op_diagram; }

  //---------------- test output  ---------------------

  public String toString()     
  {
    StringBuffer Result = new StringBuffer("\nsort diagram:\n");
    Result.append(f_sort_diagram.toString());
    Result.append("\nop_diagram:\n");
    Result.append(f_op_diagram.toString());
    Result.append("\ncoproduct:\n");
    Result.append(out_object(f_sort_diagram.get_coproduct_set(),
                             f_op_diagram.get_coproduct_set(), 
                             f_coprod_arity));
    Result.append("\n");
    return new String(Result);
  }

  @SuppressWarnings("rawtypes")
public String out_object(COLIM_VECTOR sorts, 
			   COLIM_VECTOR ops,
			   COLIM_VECTOR arity) 
  {
    StringBuffer Result = new StringBuffer("\nsorts: ");
    for (Enumeration en = sorts.elements(); 
                     en.hasMoreElements(); ) {
      Object sort = en.nextElement();
      if (sort != null) { 
        Result.append(sort.toString());
        if (en.hasMoreElements()) Result.append(",");
      }
    }   
    Result.append("\noperations: ");
    Enumeration arity_en = arity.elements();
    for (Enumeration en = ops.elements(); 
                     en.hasMoreElements(); ) {
      Object op = en.nextElement();
      INT_VECTOR arity_sorts = (INT_VECTOR) arity_en.nextElement();
      if (op != null) { 
        Result.append(out_operation(op,arity_sorts,sorts));
        if (en.hasMoreElements()) Result.append("; ");
      }
    }   
    return new String(Result);
  }

  public String out_operation(Object op, INT_VECTOR arity_sorts,
                              COLIM_VECTOR sorts)
  {
    StringBuffer Result = new StringBuffer("");
    Result.append(op.toString());
    Result.append(": ");
    if (arity_sorts.size() > 0) {
      int last = arity_sorts.item(0);
      for (int arity_sort = 1; arity_sort < arity_sorts.size(); ) {
        Result.append((sorts.item(last)).toString());
        last = arity_sorts.item(arity_sort);
        arity_sort++;
        if (arity_sort < arity_sorts.size()) Result.append(",");
      }
      Result.append("->");
      Result.append((sorts.item(last)).toString());
    }
    return new String(Result);
  }

  //---------------- private  implementation features  ---------------------

  @SuppressWarnings("unused")
private void compute_colimit()
  {
    INT_VECTOR colimit_sorts = f_sort_diagram.get_colimit_indices();
    INT_VECTOR colimit_ops = f_op_diagram.get_colimit_indices();
    f_colimit_arity = new COLIM_VECTOR(colimit_ops.size());
    for (int op = 0; op < colimit_ops.size(); op++) {
      INT_VECTOR arity_sorts = (INT_VECTOR) 
                                 f_coprod_arity.item(colimit_ops.item(op));
      INT_VECTOR colim_arity = new INT_VECTOR(arity_sorts.size());
      for (int sort = 0; sort < arity_sorts.size(); sort++) 
        colim_arity.push_back(f_sort_diagram.get_colimit_pos(
                              arity_sorts.item(sort)));
      f_colimit_arity.push_back(colim_arity);
    }
    f_colimit_valid = true;
  }

  private SET_DIAGRAM f_sort_diagram;  
  private SET_DIAGRAM f_op_diagram;  
  private COLIM_VECTOR f_coprod_arity; 
  private COLIM_VECTOR f_colimit_arity;  
  private boolean f_colimit_valid; 
}

