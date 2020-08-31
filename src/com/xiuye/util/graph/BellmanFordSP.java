package com.xiuye.util.graph;

import javax.swing.plaf.IconUIResource;
import java.util.LinkedList;
import java.util.Queue;

public class BellmanFordSP {
    private double distTo[];
    private DirectedEdge[] edgeTo;
    private boolean []onQ;
    private Queue<Integer> queue;

    private int cost;
    private Iterable<DirectedEdge> cycle;

    public BellmanFordSP(EdgeWeightedDigraph g,int s){
        distTo = new double[g.V()];
        edgeTo = new DirectedEdge[g.V()];
        onQ = new boolean[g.V()];
        queue = new LinkedList<>();
        for(int v=0;v<g.V();v++){
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;
        queue.add(s);
        onQ[s] = true;
        while(!queue.isEmpty()&&!hashNegativeCycle()){
            int v = queue.poll();
            onQ[v] = false;
            relax(g,v);
        }
        

    }

    private void relax(EdgeWeightedDigraph g, int v) {
        for(DirectedEdge e:g.adj(v)){
            int w = e.to();
            if(distTo[w] > distTo[v]+e.getWeight()){
                distTo[w] = distTo[v] + e.getWeight();
                edgeTo[w] = e;
                if(!onQ[w]){
                    queue.add(w);
                    onQ[w] = true;
                }
            }
            if(cost++%g.V() == 0){
                findNegativeCycle();
            }
        }
    }

    private void findNegativeCycle() {
    }

    private boolean hashNegativeCycle() {
        return false;
    }

}
