package cn.zmt.service.impl;

import cn.zmt.mapper.TbItemCatMapper;
import cn.zmt.pojo.TbItemCat;
import cn.zmt.pojo.TbItemCatExample;
import cn.zmt.pojo.TbItemExample;
import cn.zmt.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 赵铭涛
 * @creation time 2018/11/3 - 9:35
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    TbItemCatMapper itemCatMapper;
    @Override
    public List<TbItemCat> getItemCatList(Long parentId) throws Exception {
        TbItemCatExample example = new TbItemCatExample();
        //设置查询条件
        TbItemCatExample.Criteria criteria = example.createCriteria();
        //根据parentId查询子节点
        criteria.andParentIdEqualTo(parentId);
        //返回子节点列表
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        return list;
    }
}
