package com.xiuye.util.tree;

import java.util.List;

public class TreeNode {

	private String value;
	private List<TreeNode> nodes;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<TreeNode> getNodes() {
		return nodes;
	}
	public void setNodes(List<TreeNode> nodes) {
		this.nodes = nodes;
	}
	@Override
	public String toString() {
		return "TreeNode [value=" + value + ", nodes=" + nodes + "]";
	}
	
	
}
