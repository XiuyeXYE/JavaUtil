package com.xiuye.util.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xiuye.util.log.LogUtil;
import com.xiuye.util.time.TimeUtil;

public class TravelTreeNode {

	public static void main(String[] args) {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		TreeNode<String> root = new TreeNode<>();
		root.setValue("A");
		root.setStatus("open");
		List<TreeNode<String>> nodes = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			TreeNode<String> node = new TreeNode<>();
			node.setValue("B" + i);
			node.setStatus("close");
			List<TreeNode<String>> l = new ArrayList<>();
			for (int j = 0; j < 3; j++) {
				TreeNode<String> nodej = new TreeNode<>();
				nodej.setValue("C" + j);
				nodej.setStatus("close");
				l.add(nodej);
			}
			node.setNodes(l);
			nodes.add(node);
		}
		root.setNodes(nodes);
		LogUtil.log(root);
		LogUtil.log(gson.toJson(root));
		TimeUtil.start();
		travel(root);
		TimeUtil.outCostOnConsoleMs();
		
		TreeNode<String> out = new TreeNode<>();
		getSearchNode("B",root,out);
		TimeUtil.outCostOnConsoleMs();
		LogUtil.log(gson.toJson(out));
		out = new TreeNode<>();
		getSearchNodeIncludeChildren("1",root,out);
		TimeUtil.outCostOnConsoleMs();
		LogUtil.log(gson.toJson(out));
	}

	public static void travel(TreeNode<String> root) {

		LogUtil.print(root.getValue());
		List<TreeNode<String>> nodes = root.getNodes();
		if (nodes != null)
			for (int i = 0; i < nodes.size(); i++) {
				travel(nodes.get(i));
			}

	}

	//仅仅查出含有msg的节点,如果有上层节点就包含上层节点,但不包含其节点的子节点
	public static void getSearchNode(String msg,TreeNode<String> root,TreeNode<String> out) {
		
		if(Objects.isNull(msg)) {
			throw new NullPointerException("Searching msg is not blank!");
		}
		
		boolean isSetFatherNodeValue = false;
		//节点包含 msg中的信息,就赋值
		String v = root.getValue();
		if(!v.isEmpty() && v.toLowerCase().indexOf(msg.toLowerCase()) > -1) {
			LogUtil.log("Campared");
			out.setValue(v);
			out.setStatus(root.getStatus());
			isSetFatherNodeValue = true;
		}
		
		//遍历子节点
		List<TreeNode<String>> nodes = root.getNodes();
		if (nodes != null && nodes.size() > 0) {
			List<TreeNode<String>> ns = new ArrayList<>();
			for (int i = 0; i < nodes.size(); i++) {
				TreeNode<String> node = new TreeNode<>();
				getSearchNode(msg,nodes.get(i),node);
				//只有当node有值时候,加入数组
				if(!Objects.isNull(node.getValue())&&!node.getValue().isEmpty()) {
					ns.add(node);//子节点有了,父节点即使开始没有(没有赋值成功),那现在也有了
					if(!isSetFatherNodeValue) {
						out.setValue(root.getValue());
						out.setStatus(root.getStatus());
					}
				}
				
			}
			//如果ns中有节点再加入到根节点上
			if(ns.size()>0) {
				out.setNodes(ns);
			}
		}
		
	}
	
	//保留下一级所有节点,但不open
	public static void getSearchNodeIncludeChildren(String msg,TreeNode<String> root,TreeNode<String> out) {
		
		if(Objects.isNull(msg)) {
			throw new NullPointerException("Searching msg is not blank!");
		}
		
		boolean isSetFatherNodeValue = false;
		//节点包含 msg中的信息,就赋值
		String v = root.getValue();
		if(!v.isEmpty() && v.toLowerCase().indexOf(msg.toLowerCase()) > -1) {
			LogUtil.log("Campared");
			out.setValue(v);
			out.setStatus("open");
			isSetFatherNodeValue = true;
		}
		
		//遍历子节点
		List<TreeNode<String>> nodes = root.getNodes();
		if (nodes != null && nodes.size() > 0) {
			List<TreeNode<String>> ns = new ArrayList<>();
			
			for (int i = 0; i < nodes.size(); i++) {
				TreeNode<String> node = new TreeNode<>();
				getSearchNodeIncludeChildren(msg,nodes.get(i),node);
				//只有当node有值时候,加入数组
				if(!Objects.isNull(node.getValue())&&!node.getValue().isEmpty()) {
					ns.add(node);//子节点有了,父节点即使开始没有(没有赋值成功),那现在也有了
					if(!isSetFatherNodeValue) {
						out.setValue(root.getValue());
						out.setStatus("open");
					}
				}
				
			}
			if(ns.size()>0) {
				out.setNodes(ns);
			}
		}
		
	}

}
