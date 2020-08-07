package com.xiuye.test;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.xiuye.test.TestDirectefWeightGraph.DirectefWeightGraph;

public class TestDirectefWeightGraph {
	
	public class DirectefWeightGraph {
		List<DirectedWeightEdge> [] adj;
	}

	private double []distTo;//s->v 的距离 ,distTo[s]=0!
	private DirectedWeightEdge []edgeTo;//s->v 的边,最短路径
	
	class DirectedWeightEdge{
		int v;
		int w;
		double weight;
		int from() {
			return v;
		}
		int to() {
			return w;
		}
	}
	//边的松弛函数
	//distTo[0(s)] = 0;distTo[(!s)1..] = infinite
	private void relax(DirectedWeightEdge e) {
		int v = e.from();
		int w = e.to();
		//s->v v->w 的中的 权重比较
		//这是一个递推公式 : key,crucial!!!
		if(distTo[w] > distTo[v]+e.weight) {
			distTo[w] = distTo[v] +e.weight;
			edgeTo[w] = e;
		}
	}
	
	//顶点的松弛:
	//遍历顶点 + 边的松弛
	private void relax(DirectefWeightGraph g,int v/*start vertex*/) {
		for(DirectedWeightEdge e:g.adj[v]) {
			//v->w : weight ,smallest
			int w = e.to();
			if(distTo[w] > distTo[v]+e.weight) {
				distTo[w] = distTo[v] + e.weight;
				edgeTo[w] = e;
			}
		}
	}
	
	public double distTo(int v) {
		return distTo[v];
	}
	
	//s -> v
	public boolean hasPathTo(int v) {
		return distTo[v] < Double.POSITIVE_INFINITY;
	}
	//s->v
	//all routes
	public Stack<DirectedWeightEdge> pathTo(int v) {
		if(!hasPathTo(v))return null;
		Stack<DirectedWeightEdge> path = new Stack<>();
		for(DirectedWeightEdge e = edgeTo[v];e!= null;e=edgeTo[e.from()]) {
			path.push(e);
		}
		return path;
	}
	
	public static void main(String[] args) {
		
		
	}

}
