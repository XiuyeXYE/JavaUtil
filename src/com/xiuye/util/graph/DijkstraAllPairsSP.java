package com.xiuye.util.graph;



public class DijkstraAllPairsSP {

	private DijkstraSP[] all;
	public DijkstraAllPairsSP(EdgeWeightedDigraph g) {
		all = new DijkstraSP[g.V()];
		for(int v=0;v<g.V();v++) {
			all[v] = new DijkstraSP(g, v);
		}
	}
	
	Iterable<DirectedEdge> path(int s,int t){
		return all[s].pathTo(t);
	}
	double dist(int s,int t) {
		return all[s].distTo(t);
	}
	
	
}
