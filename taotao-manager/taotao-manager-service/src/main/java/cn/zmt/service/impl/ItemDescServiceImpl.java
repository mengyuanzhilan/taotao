package cn.zmt.service.impl;

import cn.zmt.TaotaoResult;
import cn.zmt.mapper.TbItemDescMapper;
import cn.zmt.pojo.TbItemDesc;
import cn.zmt.pojo.TbItemDescExample;
import cn.zmt.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品描述
 * @author 赵铭涛
 * @creation time 2018/11/8 - 8:59
 */
@Service
public class ItemDescServiceImpl implements ItemDescService {
    @Autowired
    TbItemDescMapper tbItemDescMapper;
    /**
     * 通过商品id获得商品描述
     * @param itemId
     * @return
     */
    @Override
    public TaotaoResult getItemDescByItemId(Long itemId) {
        TbItemDescExample example = new TbItemDescExample();
        TbItemDescExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemDesc> list = tbItemDescMapper.selectByExampleWithBLOBs(example);
        return TaotaoResult.ok(list);
    }
}
