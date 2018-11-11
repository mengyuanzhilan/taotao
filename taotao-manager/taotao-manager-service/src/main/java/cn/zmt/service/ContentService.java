package cn.zmt.service;

import cn.zmt.pojo.EUDataGridResult;
import cn.zmt.pojo.TaotaoResult;
import cn.zmt.pojo.TbContent;

/**
 * 内容管理
 * @author zmt
 * @date 2018/11/12 - 3:26
 */
public interface ContentService {

    /**
     * 根据categoryId分页查询
     * @param page
     * @param rows
     * @param categoryId
     * @return
     */
    EUDataGridResult getContentList(int page,int rows,long categoryId);

    /**
     * 添加
     * @param content
     * @return
     */
    TaotaoResult insertContent(TbContent content);

    /**
     * 修改
     * @param content
     * @return
     */
    TaotaoResult updateContent(TbContent content);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    TaotaoResult deleteContent(long[] ids);
}
