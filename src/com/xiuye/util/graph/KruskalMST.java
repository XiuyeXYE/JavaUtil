package com.xiuye.util.graph;

import java.util.LinkedList;
import java.util.Queue;

public class KruskalMST {

	private Queue<Edge> mst;
	
	public KruskalMST(EdgeWeightedGraph g) {
		
		mst = new LinkedList<Edge>();
		
		MinPQ<Edge> pq = new MinPQ<>();
		for(Edge e:g.edges())
			pq.insert(e);

		UF uf = new UF(g.V());
		
		while(!pq.isEmpty()&&mst.size()<g.V()-1) {
			Edge e= pq.delMin();
			int v = e.either();
			int w = e.other(v);
			
			if(uf.connected(v,w)) {
				continue;
			}
			
			uf.union(v,w);
			
			mst.add(e);
			
		}
		
	}
	
	public Iterable<Edge> edges(){
		return mst;
	}
	
}
