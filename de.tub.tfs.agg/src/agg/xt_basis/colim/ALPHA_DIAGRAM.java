// $Id: ALPHA_DIAGRAM.java,v 1.2 1998/04/07 13:34:10 mich Exp $
package agg.xt_basis.colim;
//-------------------------------------------------------------------
//                 Colimit of an Alpha Algebra Diagram                  
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


public class ALPHA_DIAGRAM implements COLIM_DEFS {

  //---------------- creation  ------------------------------

  public ALPHA_DIAGRAM()  
  {
    f_diagram = new SET_DIAGRAM();
    f_coprod_refs = new COLIM_VECTOR();
    f_coprod_attrs = new COLIM_VECTOR();
    f_coprod_refs.push_back(null);
    f_coprod_attrs.push_back(null);
    f_colimit_valid = false; 
    f_attr_sets_valid = false; 
    f_total_valid = false; 
  }

  //---------------- diagram construction --------------------

  public int insert_object(COLIM_VECTOR items, COLIM_VECTOR refs,
                           COLIM_VECTOR attrs, String name)
  {
    int lower = f_diagram.coproduct_size();
    int node = f_diagram.insert_object(items,name);
    for (int i = 0; i < refs.size(); i++) {
      INT_VECTOR ref = (INT_VECTOR) refs.item (i);
      INT_VECTOR coproduct_ref = new INT_VECTOR(ref.size());
      for (int item_ref = 0; item_ref < ref.size(); item_ref++) 
        coproduct_ref.push_back(ref.item(item_ref) + lower);
      f_coprod_refs.push_back(coproduct_ref);
    }
    for (int i = 0; i < attrs.size(); i++) 
      f_coprod_attrs.push_back(attrs.item (i));
    f_colimit_valid = false;
    return node;
  }

  public int insert_morphism(INT_VECTOR morphism, int v, int w)
  {
    int edge = f_diagram.insert_morphism(morphism,v,w);
    f_colimit_valid = false;
    return edge;
  }

  //---------------- colimit computation ---------------------

  public COLIM_VECTOR get_colimit_items()
  {
    return f_diagram.get_colimit_set();
  }

  public COLIM_VECTOR get_colimit_refs()
  {
    if (!f_colimit_valid) compute_colimit();
    return f_colimit_refs;
  }

  public COLIM_VECTOR get_colimit_attrs()
  {
    if (!f_colimit_valid) compute_colimit();
    return f_colimit_attrs;
  }

  public COLIM_VECTOR get_colimit_attr_sets()
  {
    if (!f_colimit_valid) {
      compute_colimit();
      compute_colimit_attr_sets();
    }
    else if (!f_attr_sets_valid)
      compute_colimit_attr_sets();
    return f_colimit_attr_sets;
  }

  public COLIM_VECTOR get_colimit_items_total()
  {
    if (!(f_total_valid && f_colimit_valid)) totalize();
    return f_diagram.get_colimit_set();
  }

  public COLIM_VECTOR get_colimit_refs_total()
  {
    if (!(f_total_valid && f_colimit_valid)) {
      totalize();
      compute_colimit();
    }
    return f_colimit_refs;
  }

  public COLIM_VECTOR get_colimit_attrs_total()
  {
    if (!(f_total_valid && f_colimit_valid)) {
      totalize();
      compute_colimit();
    }
    return f_colimit_attrs;
  }

  public COLIM_VECTOR get_colimit_attr_sets_total()
  {
    if (!(f_total_valid && f_colimit_valid)) {
      totalize();
      compute_colimit();
      compute_colimit_attr_sets();
    }
    else if (!f_attr_sets_valid)
      compute_colimit_attr_sets();
    return f_colimit_attr_sets;
  }

  //---------------- utilities ---------------------

  public INT_VECTOR get_references(int item) 
  {
     return (INT_VECTOR) f_coprod_refs.item(item);
  }

  public COLIM_VECTOR get_attributes(int item) 
  {
     return (COLIM_VECTOR) f_coprod_attrs.item(item);
  }
 
  public SET_DIAGRAM get_item_diagram() { return f_diagram; }
  
  //------------------ double pushout support ----------------------------

  public boolean is_dangling_ok()
  {
    compute_dependent();
    INT_VECTOR deleted = (INT_VECTOR) f_dependent.item(bottom);
    return deleted.size() == 0;
  }

  public boolean is_identification_ok(INT_VECTOR morphism, int target_node)
  {
    COPROD_OBJECT target = f_diagram.set_at_node(target_node);
    INT_VECTOR source_num = new INT_VECTOR();
    source_num.setSize(target.upper-target.lower+1);
    for (int i = 0; i < morphism.size(); i++) {
       int t = morphism.item(i);
       if (t != undefined) source_num.put(source_num.item(t)+1, t);
    }
    for (int i = 0; i < morphism.size(); i++) {
      if (source_num.item(i) > 1 && 
          f_diagram.get_colimit_index(target.lower + i) == bottom)
        return false;
    }     
    return true;
  }

  public boolean is_gluing_ok(INT_VECTOR morphism, int target_node)
  {
    return is_dangling_ok() && is_identification_ok(morphism, target_node);
  }

  //---------------- test output  ---------------------

  public String toString()     
  {
    StringBuffer Result = new StringBuffer("\nitem diagram:\n");
    Result.append(f_diagram.toString());
    Result.append("coproduct:\n");
    Result.append(out_object(f_diagram.get_coproduct_set(),
                                f_coprod_refs, f_coprod_attrs));
    return new String(Result);
  }

  public String out_object(COLIM_VECTOR items, COLIM_VECTOR refs, 
                           COLIM_VECTOR attrs) 
  {
    StringBuffer Result = new StringBuffer("\n");
    for (int i = 0; i < items.size(); i++) { 
      if (items.item(i) != null) {
        Result.append((items.item(i)).toString());
        Result.append(" -> ");
        INT_VECTOR refs_i = (INT_VECTOR) refs.item(i);
        for (int index = 0; index < refs_i.size(); ) {
          int ref = refs_i.item(index);
          Result.append((items.item(ref)).toString());
          index++;
          if (index < refs_i.size()) Result.append(", ");
	}
        Result.append("; attributes: ");
        COLIM_VECTOR attrs_i = (COLIM_VECTOR) attrs.item(i);
        for (int index = 0; index < attrs_i.size(); ) {
          Result.append((attrs_i.item(index)).toString());
          index++;
          if (index < attrs_i.size()) Result.append(", ");
	}
      }
      Result.append("\n");
    }
    return new String(Result);
  }

  public String out_object_attr_sets(COLIM_VECTOR items, COLIM_VECTOR  refs, 
				     COLIM_VECTOR attr_sets)  
  {
    StringBuffer Result = new StringBuffer("\n");
    for (int i = 0; i < items.size(); i++) { 
      if (items.item(i) != null) {
        Result.append((items.item(i)).toString());
        Result.append(" -> ");
        INT_VECTOR refs_i = (INT_VECTOR) refs.item(i);
        for (int index = 0; index < refs_i.size(); ) {
          int ref = refs_i.item(index);
          Result.append((items.item(ref)).toString());
          index++;
          if (index < refs_i.size()) Result.append(", ");
	}
        Result.append("; attribute sets: ");
        COLIM_VECTOR attr_sets_i = (COLIM_VECTOR) attr_sets.item(i);
        for (int index = 0; index < attr_sets_i.size(); ) {
          Result.append("{");
          COLIM_VECTOR attrs_i = (COLIM_VECTOR) attr_sets_i.item(index);
          for (int elem = 0; elem < attrs_i.size(); ) {
            Result.append((attrs_i.item(elem)).toString());
            elem++;
            if (elem < attrs_i.size()) Result.append(", ");
	  }
          Result.append("}");
          index++;
          if (index < attr_sets_i.size()) Result.append(", ");
	}
      }
      Result.append("\n");
    }
    return new String(Result);
  }

//---------------- private implementation features  ---------------

  private void compute_colimit()
  {
    INT_VECTOR colimit_items = f_diagram.get_colimit_indices();
    f_colimit_refs = new COLIM_VECTOR(colimit_items.size());
    f_colimit_attrs = new COLIM_VECTOR(colimit_items.size());
    for (int item = 0; item < colimit_items.size(); item++) {
      INT_VECTOR ref = (INT_VECTOR) 
                          f_coprod_refs.item(colimit_items.item(item));
      INT_VECTOR colimit_ref = new INT_VECTOR();
      for (int item_ref = 0; item_ref < ref.size(); item_ref++) { 
        int colimit_item_ref = f_diagram.get_colimit_pos(ref.item(item_ref));
        if (colimit_item_ref != undefined) 
          colimit_ref.push_back(colimit_item_ref);
      }
      f_colimit_refs.push_back(colimit_ref);
      f_colimit_attrs.push_back(
                          f_coprod_attrs.item(colimit_items.item(item)));
    }
    f_colimit_valid = true;
  }

  private void compute_colimit_attr_sets() 
  {
    INT_VECTOR colimit_items = f_diagram.get_colimit_indices();
    f_colimit_attr_sets = new COLIM_VECTOR();
    f_colimit_attr_sets.setSize(colimit_items.size());
    for (int item = 1; item < f_diagram.coproduct_size(); item++) { 
       int colimit_item = f_diagram.get_colimit_pos(item);
       if (colimit_item != undefined) {
         COLIM_VECTOR attrs = (COLIM_VECTOR) f_coprod_attrs.item(item);
         COLIM_VECTOR attr_sets = (COLIM_VECTOR)
                                    f_colimit_attr_sets.item(colimit_item);
         if (attr_sets == null) {
           attr_sets = new COLIM_VECTOR(attrs.size());
           for (int i = 0; i < attrs.size(); i++) 
             attr_sets.push_back(new COLIM_VECTOR());
         }
         for (int attr = 0; attr < attrs.size(); attr++) {
           COLIM_VECTOR attr_set = (COLIM_VECTOR) attr_sets.item(attr);
           attr_set.push_back(attrs.item(attr));
         }
         f_colimit_attr_sets.put(attr_sets,colimit_item);
       }
    }
    f_attr_sets_valid = true;
  }
  
  private void totalize() 
  {
    compute_dependent();
    INT_VECTOR deleted = (INT_VECTOR) f_dependent.item(bottom);
    delete_dependent(deleted);
    f_total_valid = true;
  }

  private void compute_dependent()
  { 
    if (f_dependent == null || !f_colimit_valid) {
      f_dependent = new COLIM_VECTOR();
      f_dependent.setSize(f_coprod_refs.size());
      f_dependent.put(new INT_VECTOR(),bottom);     
      INT_VECTOR colimit_items = f_diagram.get_colimit_indices(); 
      for (int i = 0; i < colimit_items.size(); i++)
        f_dependent.put(new INT_VECTOR(), colimit_items.item(i)); 
      for (int i = 0; i < colimit_items.size(); i++) {
        int item = colimit_items.item(i); 
        INT_VECTOR item_refs = (INT_VECTOR) f_coprod_refs.item(item);
        for (int ref = 0; ref < item_refs.size(); ref++) {
          int colimit_item_ref = 
                     f_diagram.get_colimit_index(item_refs.item(ref));
          INT_VECTOR dependent = (INT_VECTOR)
                                    f_dependent.item(colimit_item_ref);
          dependent.push_back(item);
        }
      }
    }
  }

  private void delete_dependent(INT_VECTOR deleted) 
  {
    for (int i = 0; i < deleted.size(); i++) {
      int item = deleted.item(i); 
      if (f_diagram.get_colimit_index(item) != bottom) {
        f_diagram.delete_element(item); 
        INT_VECTOR del = (INT_VECTOR) f_dependent.item(item);
        delete_dependent(del);
      }
    }
  }

  private SET_DIAGRAM f_diagram;  
  private COLIM_VECTOR f_coprod_refs; 
  private COLIM_VECTOR f_coprod_attrs; 
  private COLIM_VECTOR f_colimit_refs;  
  private COLIM_VECTOR f_colimit_attrs; 
  private COLIM_VECTOR f_colimit_attr_sets; 
  private COLIM_VECTOR f_dependent;  
  private boolean f_colimit_valid;
  private boolean f_total_valid;
  private boolean f_attr_sets_valid;
}
