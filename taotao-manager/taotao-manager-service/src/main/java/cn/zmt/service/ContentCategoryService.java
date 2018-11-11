package cn.zmt.service;

import cn.zmt.pojo.EUTreeNode;
import cn.zmt.pojo.TaotaoResult;

import java.util.List;

/**
 * @author 赵铭涛
 * @creation time 2018/11/10 - 13:34
 */
public interface ContentCategoryService {
    /**
     * 根据id查询
     * @param parentId
     * @return
     */
    List<EUTreeNode> getCategoryList(long parentId);

    /**
     * 添加
     * @param parentId
     * @param name
     * @return
     */
    TaotaoResult insertContentCategory(long parentId,String name);

    /**
     * 删除
     * @param parentId
     * @param id
     * @return
     */
    TaotaoResult deleteContentCategory(long parentId,long id);

    /**
     * 修改节点名称
     * @return
     */
    TaotaoResult updateContentCategoryName(long id,String name);
}
