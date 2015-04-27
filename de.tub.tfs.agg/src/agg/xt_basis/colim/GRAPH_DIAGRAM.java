// $Id: GRAPH_DIAGRAM.java,v 1.2 1998/04/07 13:34:10 mich Exp $
package agg.xt_basis.colim;
//-------------------------------------------------------------------
//                 Colimit of a Graph Diagram                            
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


public class GRAPH_DIAGRAM implements COLIM_DEFS {

  //---------------- creation --------------------------------

  public GRAPH_DIAGRAM() 
  {
    f_node_diagram = new SET_DIAGRAM();
    f_edge_diagram = new SET_DIAGRAM(); 
    f_coprod_source = new INT_VECTOR();
    f_coprod_target = new INT_VECTOR();
    f_coprod_source.push_back(bottom);
    f_coprod_target.push_back(bottom);
    f_colimit_valid = false;
  }

  //---------------- diagram construction --------------------

  @SuppressWarnings("unused")
public int insert_object(COLIM_GRAPH graph, String name)
  {
    int lower = f_node_diagram.coproduct_size();
    int nodes = f_node_diagram.insert_object(graph.f_node,name); 
    int edges = f_edge_diagram.insert_object(graph.f_edge,name); 
    for (int edge = 0; edge < graph.f_edge.size(); edge++) {
       f_coprod_source.push_back(graph.f_source.item(edge) + lower);
       f_coprod_target.push_back(graph.f_target.item(edge) + lower);
    }
    f_colimit_valid = false;
    return nodes;
  }

  @SuppressWarnings("unused")
public int insert_morphism(INT_VECTOR node_morphism, 
                             INT_VECTOR edge_morphism,
                             int v, int w)
  {
    int nodes = f_node_diagram.insert_morphism(node_morphism,v,w);
    int edges = f_edge_diagram.insert_morphism(edge_morphism,v,w);
    f_colimit_valid = false;
    return nodes;
  }

  //---------------- colimit computation ---------------------

  public COLIM_GRAPH get_colimit_graph()
  {
    if (!f_colimit_valid) compute_colimit();
    return f_colim_graph;
  }

  public COLIM_VECTOR get_colimit_nodes() 
  {
    return f_node_diagram.get_colimit_set();
  }

  public COLIM_VECTOR get_colimit_edges() 
  {
    return f_edge_diagram.get_colimit_set();
  }

  //---------------- utilities ---------------------

  public Object get_source(int edge)
  {
     return f_node_diagram.get_element(f_coprod_source.item(edge));
  }

  public Object get_target(int edge)
  {
     return f_node_diagram.get_element(f_coprod_target.item(edge));
  }
 
  public SET_DIAGRAM get_node_diagram() { return f_node_diagram; }  

  public SET_DIAGRAM get_edge_diagram() { return f_edge_diagram; }
 
  //---------------- test output  ---------------------

  public String toString()     
  {
    StringBuffer Result = new StringBuffer("\nsort diagram:\n");
    Result.append(f_node_diagram.toString());
    Result.append("\nedge_diagram:\n");
    Result.append(f_edge_diagram.toString());
    COLIM_GRAPH coprod = new COLIM_GRAPH(f_node_diagram.get_coproduct_set(),
                                         f_edge_diagram.get_coproduct_set(),
                                         f_coprod_source, f_coprod_target);
    Result.append("\ncoproduct:\n");
    Result.append(coprod.toString());
    Result.append("\n");
    return new String(Result);
  }

  //---------------- private implementation features  ---------------------

  @SuppressWarnings("unused")
private void compute_colimit()
  {
    int f;
    f_colim_graph = new COLIM_GRAPH();
    INT_VECTOR colimit_nodes = f_node_diagram.get_colimit_indices();
    for (int node = 0; node < colimit_nodes.size(); node++) { 
      COLIM_VECTOR node_attrs = new COLIM_VECTOR();
      int m = f_colim_graph.insert_node(node_attrs);
    }
    INT_VECTOR colimit_edges = f_edge_diagram.get_colimit_indices();
    for (int edge = 0; edge < colimit_edges.size(); edge++) { 
      COLIM_VECTOR edge_attrs = new COLIM_VECTOR();
      int s = f_node_diagram.get_colimit_pos(
                         f_coprod_source.item(colimit_edges.item(edge)));
      int t = f_node_diagram.get_colimit_pos(
                         f_coprod_target.item(colimit_edges.item(edge)));
      if (s != undefined && t != undefined)  
        f = f_colim_graph.insert_edge(edge_attrs,s,t);
      else f_edge_diagram.delete_element(colimit_edges.item(edge));
    }
    for (int node = 1; node < f_node_diagram.coproduct_size(); node++) { 
       int m = f_node_diagram.get_colimit_pos(node);
       if (m != undefined) {
         COLIM_VECTOR node_attrs = (COLIM_VECTOR) f_colim_graph.node_attr(m);
         node_attrs.push_back(f_node_diagram.get_element(node));
       }
    }
    for (int edge = 1; edge < f_edge_diagram.coproduct_size(); edge++) { 
       f = f_edge_diagram.get_colimit_pos(edge);
       if (f != undefined) {
         COLIM_VECTOR edge_attrs = (COLIM_VECTOR) f_colim_graph.edge_attr(f);
         edge_attrs.push_back(f_edge_diagram.get_element(edge));
       }
    }
    f_colimit_valid = true;
  }

  private SET_DIAGRAM f_node_diagram;  
  private SET_DIAGRAM f_edge_diagram; 
  private INT_VECTOR f_coprod_source;
  private INT_VECTOR f_coprod_target; 
  private COLIM_GRAPH f_colim_graph;
  private boolean f_colimit_valid;
}

