// $Id: SET_DIAGRAM.java,v 1.2 1998/04/07 13:34:10 mich Exp $
package agg.xt_basis.colim;
//-------------------------------------------------------------------
//                 Colimit of a Set Diagram                            
//-------------------------------------------------------------------

//  Copyright (c) 1995 Technical University of Berlin, Dept TFS.
//  All rights reserved.

//  Colimes Computation Project  V1.0 98/02/15  
//  Author: Dietmar Wolz, Technical University Berlin FB 13, WE 1331 
//          email: dietmar@cs.tu-berlin.de
//-------------------------------------------------------------------

// import COLIM_DEFS;
// import COLIM_GRAPH;
// import COLIM_PARTITION;
// import COPROD_OBJECT;
// import INT_VECTOR;
// import COLIM_VECTOR;
import java.util.Enumeration;

public class SET_DIAGRAM extends COLIM_GRAPH {

  //---------------- creation --------------------------------

  public SET_DIAGRAM()  
  {
    f_name = new COLIM_VECTOR();
    f_coproduct = new COLIM_VECTOR();
    f_colimit = new INT_VECTOR();
    f_partition = new COLIM_PARTITION();
    f_colimit_valid = false;
    f_coproduct.push_back(null);
    f_colimit.push_back(bottom);
    f_partition.make_block();
  }

  //---------------- diagram construction --------------------

  public int insert_object(COLIM_VECTOR set, String name)
  {
    int set_lower = coproduct_size();
    int set_upper = set_lower;
    for (int i = 0 ; i < set.size (); i++, set_upper++ ) {
      Object element = set.item (i);
      f_colimit.push_back(set_upper);
      f_coproduct.push_back(element);
      f_partition.make_block();
    }
    f_name.push_back(name);
    f_colimit_valid = false;
    return insert_node(new COPROD_OBJECT(set_lower, set_upper - 1)); 
  }

  int insert_morphism(INT_VECTOR morphism, int v, int w)
  {
    int source_lower = set_at_node(v).lower;
    int source_upper = set_at_node(v).upper;
    int target_lower = set_at_node(w).lower;
    for (int source_pos = source_lower; 
             source_pos <= source_upper; source_pos++) {
      int target = morphism.item(source_pos-source_lower);
      if (target == undefined) delete_element(source_pos);
      else {
        int target_pos = target_lower + target;
        int partition = f_partition.union_elements(
                                   source_pos, target_pos);
        if (f_colimit.item(partition) != bottom && 
        		f_coproduct.item(source_pos) != f_coproduct.item(target_pos)) { 
        	f_colimit.put(target_pos,partition);
        }
      }
    }
    f_colimit_valid = false;
    return insert_edge(morphism,v,w);
  }

  //---------------- colimit computation ---------------------

  public INT_VECTOR get_colimit_indices()
  {
    if (!f_colimit_valid) compute_colimit();
    return f_colimit_indices;
  }

  public COLIM_VECTOR get_colimit_set() 
  {
    if (!f_colimit_valid) compute_colimit();
    return f_colimit_set;
  }

  public COLIM_VECTOR get_coproduct_set()
  { 
    return f_coproduct;
  }

  public  void delete_element(int element) 
  {
    f_colimit_valid = false;
    f_colimit.put(bottom,f_partition.union_elements(element,bottom));
  }

  public  void define_representing (int element, int node) 
  {
    f_colimit_valid = false;
    int colimit_index = f_partition.find(set_at_node(node).lower + element);
    f_colimit.put(element,colimit_index);
  }
 
  public Object get_element(int element) 
  { 
    return f_coproduct.item(element); 
  }

  public void put_element(int element, Object value) 
  { 
    f_coproduct.put(value, element); 
  }

  public Object get_colimit_element (int element)
  {
    return get_element(get_colimit_index(element));
  }

  public Object get_colimit_element_at_node (int element, int node) 
  {
    return get_element(get_colimit_index_at_node(element, node));
  }

  public int get_colimit_index (int element) 
  {
    if (f_colimit_valid) return f_colimit.item(element);
    else return f_colimit.item(f_partition.find(element));
  }

  public int get_colimit_pos (int element) 
  {
    if (!f_colimit_valid) compute_colimit();
    return f_colimit_pos.item(f_colimit.item(element));
  }

  public int get_colimit_index_at_node (int element, int node) 
  {
    return get_colimit_index(element + set_at_node(node).lower);
  }

  //---------------- utilities -------------------------------------

  public String get_name (int node) { return (String) f_name.item(node); }

  public COPROD_OBJECT set_at_node (int node) 
  { 
    return (COPROD_OBJECT) f_node.item(node); 
  }
  
  public INT_VECTOR morphism_at_edge (int edge) 
  { 
    return (INT_VECTOR) f_edge.item(edge); 
  }
  
  public int source_at_edge (int edge) { return f_source.item(edge); }

  public int target_at_edge (int edge) { return f_target.item(edge); }

  public int object_count() { return  f_node.size(); }

  public int morphism_count() { return  f_edge.size(); }

  public int object_size(int node) 
  {
    COPROD_OBJECT set = set_at_node(node);
    return set.upper - set.lower + 1; 
  }
 
  public int coproduct_size() { return f_coproduct.size(); }


  //---------------- test output  ---------------------

  @SuppressWarnings({ "unused", "rawtypes" })
public String toString()     
  {
    StringBuffer Result = new StringBuffer("\nobjects: ");
    int index = 0;
    for (Enumeration en = f_node.elements(); 
                     en.hasMoreElements(); index++) {
      Object n = en.nextElement();
      Result.append(get_name(index));
      Result.append(": ");
      Result.append(out_object(index));
      if (en.hasMoreElements()) Result.append("; ");
    }
    Result.append("\nmorphisms: ");
    index = 0;
    for (Enumeration en = f_edge.elements(); 
                     en.hasMoreElements(); index++) {
      Object e = en.nextElement(); 
      Result.append(out_morphism(index));
      if (en.hasMoreElements()) Result.append("; ");
    }
    Result.append("\n");
    return new String(Result);
  } 

  String out_object(int node)  
  {
    StringBuffer Result = new StringBuffer("{");
    COPROD_OBJECT set = set_at_node(node);
    for (int i = set.lower;  i <= set.upper; ) {
      Result.append(get_element(i).toString());
      i++;
      if (i <= set.upper) Result.append(",");
    }
    Result.append("}");
    return new String(Result);
  }

  public String out_morphism(int edge) 
  {
    StringBuffer Result = new StringBuffer("");
    int s = f_source.item(edge);
    int t = f_target.item(edge);
    COPROD_OBJECT source_set = set_at_node(s);
    COPROD_OBJECT target_set = set_at_node(t);
    INT_VECTOR mor = morphism_at_edge(edge);
    Result.append(get_name(s));
    Result.append(" --> ");
    Result.append(get_name(t));
    Result.append(": ");
    Result.append("{");
    for (int i = source_set.lower;  i <= source_set.upper; ) {
      Result.append(get_element(i).toString());
      Result.append("->");
      int pos = mor.item(i-source_set.lower);
      if (pos == undefined) Result.append("#");
      else Result.append(get_element(pos + target_set.lower).toString());
      i++;
      if (i <= source_set.upper) Result.append(", ");
    }
    Result.append("}");
    return new String(Result);
  }

  //---------------- private  implementation features  ---------------------

  private void compute_colimit()
  {
    f_colimit_indices = new INT_VECTOR();
    f_colimit_set = new COLIM_VECTOR();
    for (int element = 1; element < coproduct_size(); element++) {
      int colim_index = f_colimit.item(f_partition.find(element));
      f_colimit.put(colim_index,element);
      if (element == colim_index) {
        f_colimit_indices.push_back(colim_index);
        f_colimit_set.push_back(f_coproduct.item(colim_index));
      }
    }
    f_colimit_pos = new INT_VECTOR();
    f_colimit_pos.setSize(coproduct_size());
    f_colimit_pos.put(undefined,bottom);
    for (int element = 0; element < f_colimit_indices.size(); element++) {
      f_colimit_pos.put(element, f_colimit_indices.item(element));
    }
    f_colimit_valid = true;
  }

  private COLIM_VECTOR f_name;
  private COLIM_VECTOR f_coproduct;
  private INT_VECTOR f_colimit;
  private INT_VECTOR f_colimit_pos;
  private COLIM_VECTOR f_colimit_set;
  private INT_VECTOR f_colimit_indices;
  private COLIM_PARTITION f_partition; 
  private boolean f_colimit_valid;
}











