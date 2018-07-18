package com.xiuye.util.tree;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.xiuye.util.log.LogUtil;

public class TravelTreeNode {

	public static void main(String[] args) {

		Gson gson = new Gson();
		
		TreeNode root = new TreeNode();
		root.setValue("a");
		List<TreeNode> nodes = new ArrayList<>(); 
		for(int i=0;i<10;i++) {
			TreeNode node = new TreeNode();
			node.setValue("B"+i);
			nodes.add(node);
		}
		root.setNodes(nodes);
		LogUtil.log(root);
		LogUtil.log(gson.toJson(root));
		
		
		
	}

}
