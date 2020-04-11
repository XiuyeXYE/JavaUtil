package com.xiuye.util.tree;

import java.util.List;

public class TreeNode<T> {

	private T value;
	private T status;

	public T getStatus() {
		return status;
	}

	public void setStatus(T status) {
		this.status = status;
	}

	private List<TreeNode<T>> nodes;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public List<TreeNode<T>> getNodes() {
		return nodes;
	}

	public void setNodes(List<TreeNode<T>> nodes) {
		this.nodes = nodes;
	}

	@Override
	public String toString() {
		return "TreeNode [value=" + value + ", status=" + status + ", nodes=" + nodes + "]";
	}

}
