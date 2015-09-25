package com.gohuinuo.common.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeNode {

	private Long id;
	private Long parentId; //父级id
	private String name; //名称
	private String url; //链接
	private String icon; //图标
	private int order; //排序
	private int level=-1; //深度
	private boolean open = false; //是否打开
	private boolean hasChild = false; //有子节点吗
	private List<TreeNode> items; //子节点集合
	
	
	public TreeNode(){}
	public TreeNode(Long id, Long parentId, String name, String url, String icon) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.url = url;
		this.icon = icon;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	
	public boolean getHasChild() {
		return hasChild;
	}
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
	public List<TreeNode> getItems() {
		return items;
	}
	
	//添加子节点
	private void addChild(final TreeNode node){
		if(items == null){
			items = new ArrayList<TreeNode>();
		}
	    items.add(node);
	}
	
	//递归找level
	private static int resolveLevel(final TreeNode node, final Map<Long, TreeNode> nodes){
	    int level = node.level;
	    if(level == -2){
	        throw new RuntimeException("Node循环了, id=" + node.id);
	    }
	    if(level == -1){
	        node.level  = -2;
	        level = node.level = resolveLevel(nodes.get(node.getParentId()),nodes) +1;
	    }else{
	    	node.hasChild = true;
	    }
	    return level;
	}

	/**
	 * 构造无限级树结构
	* @param list 初始的list
	* @return
	* @throws Exception parentNode is null
	 */
	public static List<TreeNode> baseTreeNode(List<TreeNode> list){
		final Map<Long, TreeNode> nodes = new HashMap<Long, TreeNode>();

		//所有节点记录下来
		for(TreeNode node : list){
		    nodes.put(node.getId(), node);
		}

		final TreeNode root = new TreeNode();
		root.level = 0;
		nodes.put(0L, root);

		for(TreeNode node : list){
		    final TreeNode parent = nodes.get(node.parentId);
			if(parent == null){
				throw new RuntimeException("子节点有父级id，却没有找到此父级的对象");
			}
		    //添加子节点
		    parent.addChild(node);
		}
		
		int max = 0;
		for(TreeNode node : list){
			max = Math.max(resolveLevel(node, nodes), max);
		}

		return root.items;
	}
	

}
