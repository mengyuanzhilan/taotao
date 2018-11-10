package cn.zmt.service;

import cn.zmt.pojo.EUDataGridResult;
import cn.zmt.pojo.TaotaoResult;
import cn.zmt.pojo.TbItemParam;

/**
 * @author zmt
 * @date 2018/11/7 - 16:53
 */
public interface ItemParamService {
    /**
     *通过cid获取
     * @param id
     * @return
     */
    TaotaoResult getTtemParamByCid(Long id);

    /**
     * 添加参数信息
     * @param itemParam
     * @return
     */
    TaotaoResult insertItemParam(TbItemParam itemParam);

    /**
     * 分页获取参数模板列表
     * @param page
     * @param rows
     * @return
     */
    EUDataGridResult getItemParamList(int page, int rows);

    /**
     * 删除参数模板
     * @param ids
     * @return
     */
    TaotaoResult deleteItemParam(Long[] ids) throws Exception;
}
