package cn.zmt.service;

import cn.zmt.pojo.TbItemCat;

import java.util.List;

/**
 * @author 赵铭涛
 * @creation time 2018/11/3 - 9:34
 */
public interface ItemCatService {
    /**
     * 树节点查询方法
     * @param parentId
     * @return
     * @throws Exception
     */
    public List<TbItemCat> getItemCatList(Long parentId) throws Exception;
}
