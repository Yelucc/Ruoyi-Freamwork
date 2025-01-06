package com.ruoyi.common.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysMenu;

/**
 * Treeselect树结构实体类
 * 
 * @author ruoyi
 */
public class TreeSelect implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect()
    {

    }
    /**
     * 递归获取树结构的所有节点ID
     * @param treeNodes 树的根节点列表
     * @return 所有节点ID的列表
     */
    public static List<Long> getAllNodeIds(List<TreeSelect> treeNodes) {
        List<Long> ids = new ArrayList<>();
        for (TreeSelect node : treeNodes) {
            ids.add(node.getId()); // 添加当前节点的ID
            if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                ids.addAll(getAllNodeIds(node.getChildren())); // 递归添加子节点的ID
            }
        }
        return ids;
    }

    /**
     * 递归获取指定节点ID下的所有子节点
     * @param treeNodes 树的根节点列表
     * @param targetId 目标节点ID
     * @return 指定节点ID下的所有子节点列表
     */
    public static List<TreeSelect> getSubNodesById(List<TreeSelect> treeNodes, Long targetId) {
        List<TreeSelect> result = new ArrayList<>();
        for (TreeSelect node : treeNodes) {
            if (node.getId().equals(targetId)) {
                collectAllChildren(node, result); // 找到目标节点后，收集其子节点
                break; // 找到目标节点后可以中止搜索
            } else if (node.getChildren() != null && !node.getChildren().isEmpty()) {
                result.addAll(getSubNodesById(node.getChildren(), targetId)); // 递归继续查找
            }
        }
        return result;
    }

    /**
     * 辅助方法：递归收集指定节点下的所有子节点
     * @param node 当前节点
     * @param result 结果列表
     */
    private static void collectAllChildren(TreeSelect node, List<TreeSelect> result) {
        if (node.getChildren() != null && !node.getChildren().isEmpty()) {
            for (TreeSelect child : node.getChildren()) {
                result.add(child); // 添加子节点
                collectAllChildren(child, result); // 递归收集更深层的子节点
            }
        }
    }
    public TreeSelect(SysDept dept)
    {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(SysMenu menu)
    {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public List<TreeSelect> getChildren()
    {
        return children;
    }

    public void setChildren(List<TreeSelect> children)
    {
        this.children = children;
    }
}
