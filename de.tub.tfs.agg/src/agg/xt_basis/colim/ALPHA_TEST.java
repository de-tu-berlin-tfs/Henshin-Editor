// $Id: ALPHA_TEST.java,v 1.2 1998/04/07 13:34:10 mich Exp $
package agg.xt_basis.colim;
//-------------------------------------------------------------------
//                 Alpha Algebra Diagram Test Driver                           
//-------------------------------------------------------------------

//  Copyright (c) 1995 Technical University of Berlin, Dept TFS.
//  All rights reserved.

//  Colimes Computation Project V0.7 96/08/01 
//  Author: Dietmar Wolz, Technical University Berlin FB 13, WE 1331 
//          email: dietmar@cs.tu-berlin.de
//-------------------------------------------------------------------

//import java.lang.System;
// import COLIM_DEFS;
// import COLIM_VECTOR;
// import INT_VECTOR;
// import ALPHA_DIAGRAM;
// import GRAPH_DIAGRAM;
// import GRAPH_TEST;

public class ALPHA_TEST implements COLIM_DEFS {

  public ALPHA_TEST() 
  {
    alpha_diagram = new ALPHA_DIAGRAM();
    gen_alpha_test();
    System.out.print("\nalpha diagram:\n" + alpha_diagram.toString());
    COLIM_VECTOR items = alpha_diagram.get_colimit_items_total();  
    COLIM_VECTOR refs = alpha_diagram.get_colimit_refs_total();
    COLIM_VECTOR attrs = alpha_diagram.get_colimit_attrs_total();
    COLIM_VECTOR attr_sets = alpha_diagram.get_colimit_attr_sets_total();
    System.out.print("\ncolimit alpha algebra:\n" + 
                     alpha_diagram.out_object(items,refs,attrs) +
		     "\ncolimit alpha algebra with attribute sets:\n" +
		     alpha_diagram.out_object_attr_sets(items,refs,attr_sets));
    System.out.flush();
  }

  @SuppressWarnings("unused")
private void gen_alpha_test()
  {
    GRAPH_TEST graph_test = new GRAPH_TEST();
    GRAPH_DIAGRAM graph_diagram = graph_test.graph_diagram;
    SET_DIAGRAM node_diagram = graph_diagram.get_node_diagram();
    SET_DIAGRAM edge_diagram = graph_diagram.get_edge_diagram();
    for (int obj = 0; obj < node_diagram.object_count(); obj++) {
      COPROD_OBJECT nodes = node_diagram.set_at_node(obj);
      COPROD_OBJECT edges = edge_diagram.set_at_node(obj);
      COLIM_VECTOR items = new COLIM_VECTOR();
      COLIM_VECTOR refs = new COLIM_VECTOR();
      COLIM_VECTOR attrs = new COLIM_VECTOR();
      for (int node = nodes.lower; node <= nodes.upper; node++) {
        INT_VECTOR ref = new INT_VECTOR();
        COLIM_VECTOR attr = new COLIM_VECTOR();
        attr.push_back(node_diagram.get_element(node));
        items.push_back(node_diagram.get_element(node));
        refs.push_back(ref);
        attrs.push_back(attr);
      }
      for (int edge = edges.lower; edge <= edges.upper; edge++) {
        INT_VECTOR ref = new INT_VECTOR();
        ref.push_back(items.indexOf(graph_diagram.get_source(edge)));
        ref.push_back(items.indexOf(graph_diagram.get_target(edge)));
        COLIM_VECTOR attr = new COLIM_VECTOR();
        attr.push_back(edge_diagram.get_element(edge));
        items.push_back(edge_diagram.get_element(edge));
        refs.push_back(ref);
        attrs.push_back(attr);
      }
      int n = alpha_diagram.insert_object(items,refs,attrs,
                            graph_diagram.get_node_diagram().get_name(obj));
    }      
    for (int mor = 0; mor < node_diagram.morphism_count(); mor++) {
      INT_VECTOR morphism = new INT_VECTOR 
                              (node_diagram.morphism_at_edge(mor));
      int target_upper = 
             node_diagram.object_size(node_diagram.target_at_edge(mor));
      INT_VECTOR e_mor = edge_diagram.morphism_at_edge(mor);
      for (int i = 0; i < e_mor.size(); i++)
        morphism.push_back(e_mor.item(i) + target_upper);
      int e = alpha_diagram.insert_morphism(morphism,
					    node_diagram.source_at_edge(mor),
					    node_diagram.target_at_edge(mor));
    }
  }

  private ALPHA_DIAGRAM alpha_diagram;
}

