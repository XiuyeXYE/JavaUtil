package com.xiuye.util.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xiuye.util.log.XLog;
import com.xiuye.util.time.XTime;

public class TravelTreeNode {

	public static void main(String[] args) {

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		TreeNode<String> root = new TreeNode<>();//树的根节点
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
		XLog.log(root);
		XLog.log(gson.toJson(root));
		
		XLog.log("遍历(应该是先序):");
		XTime.start();
		travel(root);
		XTime.outByMS();

		//查询节点
		XLog.log();
		XLog.log("仅仅查出含有msg的节点,如果有上层节点就包含上层节点,但不包含其节点的子节点:");
		TreeNode<String> out = new TreeNode<>();
		getSearchNode("9", root, out);
		XTime.outByMS();
		XLog.log(gson.toJson(out));
		
		//查询节点 2
		XLog.log();
		XLog.log("保留下一级所有子节点,但子节点不open:");
		out = new TreeNode<>();
		getSearchNodeIncludeChildren("9", root, out);
		XTime.outByMS();
		XLog.log(gson.toJson(out));
	}

	//遍历
	public static void travel(TreeNode<String> root) {

		XLog.print(root.getValue());
		List<TreeNode<String>> nodes = root.getNodes();
		if (nodes != null)
			for (int i = 0; i < nodes.size(); i++) {
				travel(nodes.get(i));
			}

	}

	// 仅仅查出含有msg的节点,如果有上层节点就包含上层节点,但不包含其节点的子节点
	public static void getSearchNode(String msg, TreeNode<String> root, TreeNode<String> out) {

		if (Objects.isNull(msg)) {
			throw new NullPointerException("Searching msg is not blank!");
		}

		boolean isSetFatherNodeValue = false;
		// 节点包含 msg中的信息,就赋值
		String v = root.getValue();
		if (!v.isEmpty() && v.toLowerCase().indexOf(msg.toLowerCase()) > -1) {
//			LogUtil.log("Campared");
			out.setValue(v);
			out.setStatus(root.getStatus());
			isSetFatherNodeValue = true;
		}

		// 遍历子节点
		List<TreeNode<String>> nodes = root.getNodes();
		if (nodes != null && nodes.size() > 0) {
			List<TreeNode<String>> ns = new ArrayList<>();
			for (int i = 0; i < nodes.size(); i++) {
				TreeNode<String> node = new TreeNode<>();
				getSearchNode(msg, nodes.get(i), node);
				// 只有当node有值时候,加入数组
				if (!Objects.isNull(node.getValue()) && !node.getValue().isEmpty()) {
					ns.add(node);// 子节点有了,父节点即使开始没有(没有赋值成功),那现在也有了
					if (!isSetFatherNodeValue) {
						out.setValue(root.getValue());
						out.setStatus(root.getStatus());
					}

				}

			}
			// 如果ns中有节点再加入到根节点上
			if (ns.size() > 0) {
				out.setNodes(ns);
			}
		}

	}

	// 保留下一级所有子节点,但子节点不open
	public static void getSearchNodeIncludeChildren(String msg, TreeNode<String> root, TreeNode<String> out) {

		if (Objects.isNull(msg)) {
			throw new NullPointerException("Searching msg is not blank!");
		}

		boolean isSetFatherNodeValue = false;
		// 节点包含 msg中的信息,就赋值
		String v = root.getValue();
		if (!v.isEmpty() && v.toLowerCase().indexOf(msg.toLowerCase()) > -1) {
//			LogUtil.log("Campared");
			out.setValue(v);
			out.setStatus("open");
			isSetFatherNodeValue = true;
		}

		// 遍历子节点
		List<TreeNode<String>> nodes = root.getNodes();
		if (nodes != null && nodes.size() > 0) {
			List<TreeNode<String>> ns = new ArrayList<>();

			for (int i = 0; i < nodes.size(); i++) {
				TreeNode<String> node = new TreeNode<>();
				getSearchNodeIncludeChildren(msg, nodes.get(i), node);
				// 只有当node有值时候,加入数组
				if (!Objects.isNull(node.getValue()) && !node.getValue().isEmpty()) {
					ns.add(node);// 子节点有了,父节点即使开始没有(没有赋值成功),那现在也有了
					if (!isSetFatherNodeValue) {
						out.setValue(root.getValue());
						out.setStatus("open");
					}

					// 判断nodes[i]是否有根节点,并且判断node节点是否没有子节点
					// nodes[i]与node的节点是同一等级的节点,既然node没有子节点了,
					// 而nodes[i]有子节点,说明,node此时已经是out的叶节点,所以,node
					// 加上nodes[i]的子节点,使其子节点都不展开,这样就是查询出包含有子节点的节点,
					// 而与其他查询出的root的叶子节点(没有子节点),共同构成了,查询出的结果.
					// 总结:子节点有时,父节点必须有,而子节点的子节点可以有但不展开
					if ((node.getNodes() == null || node.getNodes().isEmpty())/* 说明是叶节点 */
							&& !Objects.isNull(nodes.get(i).getNodes())
							&& !nodes.get(i).getNodes().isEmpty()/* 说明同级的nodes[i]有子节点 */
					) {
						copyNodeAndSetClose(nodes.get(i), node);
					}

				}

			}
			if (ns.size() > 0) {
				out.setNodes(ns);
			}
		}

	}

	// 只需要一级关闭就行了,但是是深度复制啊
	private static void copyNodeAndSetClose(TreeNode<String> source, TreeNode<String> target) {
		List<TreeNode<String>> children = source.getNodes();
		List<TreeNode<String>> targetChildren = new ArrayList<>();
		for (TreeNode<String> child : children) {
			TreeNode<String> node = new TreeNode<>();
			node.setValue(child.getValue());
			node.setStatus("close");
			targetChildren.add(node);
			if (!Objects.isNull(child.getNodes()) && !child.getNodes().isEmpty()) {
				copyNodeAndSetClose(child, node);
			}
		}
		target.setNodes(targetChildren);
	}
}
