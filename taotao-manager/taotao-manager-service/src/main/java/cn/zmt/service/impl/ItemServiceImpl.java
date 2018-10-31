package cn.zmt.service.impl;

import cn.zmt.mapper.TbItemMapper;
import cn.zmt.pojo.TbItem;
import cn.zmt.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zmt
 * @date 2018/10/27 - 23:06
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    TbItemMapper tbItemMapper;
    @Override
    public TbItem getItemById(long itemId) {
        return tbItemMapper.selectByPrimaryKey(itemId);
    }
}
