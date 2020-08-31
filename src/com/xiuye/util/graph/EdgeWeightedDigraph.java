package com.xiuye.util.graph;

public class EdgeWeightedDigraph {

	private final int V;
	private  int E;
	private Bag<DirectedEdge> []adj;
	
	public EdgeWeightedDigraph(int V) {
		this.V = V;
		this.E = 0;
		adj = new Bag[V];
		
		for(int v=0;v < V;v++) {
			adj[v] = new Bag<>();
		}
				
	}
	
	 public int V() {
		 return V;
	 }
	 
	 public int E() {
		 return E;
	 }
	 
	 
	 public void addEdge(DirectedEdge e) {
		 adj[e.from()].add(e);
		 E++;
	 }
	 
	 public Iterable<DirectedEdge> edges(){
		 Bag<DirectedEdge> b = new Bag<>();
		 for(int v=0;v<V;v++) {
			 for(DirectedEdge e: adj[v]) {
					b.add(e);
			 }
		 }
		 return b;
	 }

	public Bag<DirectedEdge> adj(int v) {
		return adj[v];
	}
	 
}

