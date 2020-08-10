package com.xiuye.util.graph;

import java.util.Stack;

public class DijkstraSP {

	private DirectedEdge[] edgeTo;
	private double []distTo;
	private IndexMinPQ<Double> pq;
	public DijkstraSP(EdgeWeightedDigraph g,int s) {
		edgeTo = new DirectedEdge[g.V()];
		distTo = new double[g.V()];
		pq = new IndexMinPQ<>(g.V());
		
		//initialize distTo
		for(int i=0;i<g.V();i++) {
			distTo[i] = Double.POSITIVE_INFINITY;
		}
		
		distTo[0] = 0.0;
		pq.insert(0, 0.0);
		
		while(!pq.isEmpty()) {
			relax(g,pq.delMin());
		}
		
		
	}
	private void relax(EdgeWeightedDigraph g, int v) {
		for(DirectedEdge e:g.adj(v)) {
			int w=e.to();
			if(distTo[w]>distTo[v]+e.getWeight()) {
				distTo[w] = distTo[v] + e.getWeight();
				edgeTo[w] = e;
				if(pq.contains(w)) {
					pq.change(w, distTo[w]);
				}
				else {
					pq.insert(w, distTo[w]);
				}
			}
		}
	}
	public Iterable<DirectedEdge> pathTo(int t) {
		if(!hasPathTo(t))return null;
		Stack<DirectedEdge> path = new Stack<DirectedEdge>();
		for(DirectedEdge e=edgeTo[t];e!=null;e=edgeTo[e.from()]) {
			path.push(e);
		}
		return path;
	}
	
	public boolean hasPathTo(int v) {
		return distTo[v] < Double.POSITIVE_INFINITY;
	}
	
	public double distTo(int t) {
		return distTo[t];
	}
	
}
