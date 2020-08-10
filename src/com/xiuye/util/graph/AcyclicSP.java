package com.xiuye.util.graph;

public class AcyclicSP {

	private DirectedEdge[] edgeTo;
	private double []distTo;
	
	public AcyclicSP(EdgeWeightedDigraph g,int s) {
		edgeTo = new DirectedEdge[g.V()];
		distTo = new double[g.V()];
		for(int v=0;v<g.V();v++) {
			distTo[v] = Double.POSITIVE_INFINITY;
		}
		
		distTo[s] = 0.0;
		
		Topological top = new Topological(g);
		for(int v:top.order()) {
			relax(g,v);
		}
	}

	private void relax(EdgeWeightedDigraph g, int v) {
		for(DirectedEdge e:g.adj(v)) {
			int w=e.to();
			if(distTo[w]>distTo[v]+e.getWeight()) {
				distTo[w] = distTo[v] + e.getWeight();
				edgeTo[w] = e;
				
			}
		}
	}
	
}
