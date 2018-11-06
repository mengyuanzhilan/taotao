package cn.zmt.service.impl;

import cn.zmt.EUDataGridResult;
import cn.zmt.IDUtils;
import cn.zmt.TaotaoResult;
import cn.zmt.mapper.TbItemMapper;
import cn.zmt.pojo.TbItem;
import cn.zmt.pojo.TbItemExample;
import cn.zmt.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zmt
 * @date 2018/10/27 - 23:06
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    TbItemMapper tbItemMapper;
    /**
     * 商品列表查询
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EUDataGridResult getItemList(int page, int rows) {
        //查询商品列表
        TbItemExample example = new TbItemExample();
        //分页处理
        PageHelper.startPage(page,rows);
        List<TbItem> list = tbItemMapper.selectByExample(example);
        //创建一个返回值对象
        EUDataGridResult result = new EUDataGridResult();
        //取记录总数
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);
        return result;
    }

    @Override
    public TbItem getItemById(long itemId) {
        return tbItemMapper.selectByPrimaryKey(itemId);
    }

    /**
     * 创建商品
     * @param item
     * @return
     */
    @Override
    public TaotaoResult createItem(TbItem item) {
        //item补全
        //生成商品ID
        Long itemId = IDUtils.genItemId();
        //商品的状态,1 正常, 2 下架,3 删除
        item.setStatus((byte)1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        item.setId(itemId);
        //插入到数据库
        tbItemMapper.insert(item);
        return TaotaoResult.ok();
    }
}
