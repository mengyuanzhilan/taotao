package cn.zmt.service;

import cn.zmt.EUDataGridResult;
import cn.zmt.TaotaoResult;
import cn.zmt.pojo.TbItem;
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
     * 分页获取列表
     * @param page
     * @param rows
     * @return
     */
    EUDataGridResult getItemParamList(int page, int rows);
}
