// $Id: GRAPH_TEST.java,v 1.1 1997/08/25 10:39:02 mich Exp $
package agg.xt_basis.colim;
//-------------------------------------------------------------------
//                 Graph Diagram Test Driver                           
//-------------------------------------------------------------------

//  Copyright (c) 1995 Technical University of Berlin, Dept TFS.
//  All rights reserved.

//  Colimes Computation Project V0.7 96/08/01 
//  Author: Dietmar Wolz, Technical University Berlin FB 13, WE 1331 
//          email: dietmar@cs.tu-berlin.de
//-------------------------------------------------------------------


// import COLIM_DEFS;
// import COLIM_VECTOR;
// import INT_VECTOR;
// import GRAPH_DIAGRAM;

public class GRAPH_TEST implements COLIM_DEFS {

  public GRAPH_TEST() 
  {
    graph_diagram = new GRAPH_DIAGRAM();
    gen_graph_test();
    System.out.print("\ngraph diagram:\n" + graph_diagram.toString());
    COLIM_GRAPH colim_graph = graph_diagram.get_colimit_graph();
    System.out.print("\ncolimit graph:\n" + colim_graph.toString());
    System.out.flush();
  }

  @SuppressWarnings("unused")
private void gen_graph_test()
  {
    COLIM_GRAPH L = new COLIM_GRAPH();
    COLIM_GRAPH G = new COLIM_GRAPH();
    COLIM_GRAPH R = new COLIM_GRAPH();
    
    String _L1 = "L1";
    String _L2 = "L2";
    String _L3 = "L3";
    String _L4 = "L4";
    String _R1 = "R1";
    String _R2 = "R2";
    String _R3 = "R3";
    String _G1 = "G1";
    String _G2 = "G2";
    String _G3 = "G3";
    String _G4 = "G4";
    String _G5 = "G5";
    String _R33 = "R33";
    String _R31 = "R31";
    String _R23 = "R23";
    String _G34 = "G34";
    String _G54 = "G54";
    String _r = "r";
    String _m = "m";
    String _Ln = "L";
    String _Rn = "R";
    String _Gn = "G";

    int L1_ = L.insert_node(_L1);
    int L2_ = L.insert_node(_L2);
    int L3_ = L.insert_node(_L3);
    int L4_ = L.insert_node(_L4);
    int R1_ = R.insert_node(_R1);
    int R2_ = R.insert_node(_R2);
    int R3_ = R.insert_node(_R3);
    int G1_ = G.insert_node(_G1);
    int G2_ = G.insert_node(_G2);
    int G3_ = G.insert_node(_G3);
    int G4_ = G.insert_node(_G4);
    int G5_ = G.insert_node(_G5);

    int R31_ = R.insert_edge(_R33,R3_,R3_);
    int R32_ = R.insert_edge(_R31,R3_,R1_);
    int R23_ = R.insert_edge(_R23,R2_,R3_);
    int G34_ = G.insert_edge(_G34,G3_,G4_);
    int G54_ = G.insert_edge(_G54,G5_,G4_);

    INT_VECTOR  r = new INT_VECTOR(); r.setSize(4);
    INT_VECTOR  m = new INT_VECTOR(); m.setSize(4);
    INT_VECTOR  empty = new INT_VECTOR();

    r.put(R1_,L1_);
    r.put(R2_,L2_);
    r.put(R3_,L3_);
    r.put(undefined,L4_);
    m.put(G1_,L1_);
    m.put(G2_,L2_);
    m.put(G3_,L3_);
    m.put(G3_,L4_);

    int L_ = graph_diagram.insert_object(L,_Ln);
    int R_ = graph_diagram.insert_object(R,_Rn);
    int G_ = graph_diagram.insert_object(G,_Gn);
    graph_diagram.insert_morphism(r,empty,L_,R_);
    graph_diagram.insert_morphism(m,empty,L_,G_);
  }
      
  public GRAPH_DIAGRAM graph_diagram;
}
