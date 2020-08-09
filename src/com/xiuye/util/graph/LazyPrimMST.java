package com.xiuye.util.graph;

import java.util.LinkedList;
import java.util.Queue;


public class LazyPrimMST {

	
	private boolean []marked;
	private Queue<Edge> mst;
	private MinPQ<Edge> pq;
	
	public LazyPrimMST(EdgeWeightedGraph g) {
		pq = new MinPQ<Edge>();
		marked = new boolean[g.V()];
		mst = new LinkedList<>();
		visit(g,0);
		while(!pq.isEmpty()) {
			Edge e = pq.delMin();
			int v = e.either();
			int w = e.other(v);
			if(marked[v]&&marked[w]) {
				continue;
			}
			mst.add(e);
			if(!marked[v])
				visit(g,v);
			if(!marked[w])
				visit(g,v);
		}
		
	}

	private void visit(EdgeWeightedGraph g, int v) {
		marked[v] = true;
		for(Edge e:g.adj(v)) {
			if(!marked[e.other(v)]) {
				pq.insert(e);
			}
		}
	}
	
	public Iterable<Edge> edges(){
		return mst;
	}
}
