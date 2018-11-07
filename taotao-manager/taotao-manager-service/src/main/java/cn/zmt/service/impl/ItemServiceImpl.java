package cn.zmt.service.impl;

import cn.zmt.EUDataGridResult;
import cn.zmt.IDUtils;
import cn.zmt.TaotaoResult;
import cn.zmt.mapper.TbItemDescMapper;
import cn.zmt.mapper.TbItemMapper;
import cn.zmt.mapper.TbItemParamItemMapper;
import cn.zmt.pojo.TbItem;
import cn.zmt.pojo.TbItemDesc;
import cn.zmt.pojo.TbItemExample;
import cn.zmt.pojo.TbItemParamItem;
import cn.zmt.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商品列表
 * @author zmt
 * @date 2018/10/27 - 23:06
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDesc;

    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;
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
    public TaotaoResult createItem(TbItem item,String desc,String itemParam) throws Exception {
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
        //添加商品描述信息
        TaotaoResult result = insertItemDesc(itemId,desc);
        if(result.getStatus()!=200){
            throw  new Exception();
        }
        result = insertItemParamItem(itemId,itemParam);
        if(result.getStatus()!=200){
            throw  new Exception();
        }
        return TaotaoResult.ok();
    }

    /**
     * 添加商品描述
     * @param desc
     * @return
     */
    private TaotaoResult insertItemDesc(Long itemId,String desc){
        TbItemDesc itemDesc = new TbItemDesc();
        //商品id
        itemDesc.setItemId(itemId);
        //商品描述
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        return TaotaoResult.ok();
    }

    /**
     *添加商品规格参数
     * @param intemId
     * @param itemParam
     * @return
     */
    private TaotaoResult insertItemParamItem(Long intemId,String itemParam){
        //补完pojo
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(intemId);
        itemParamItem.setParamData(itemParam);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        //向表中插入数据
        tbItemParamItemMapper.insert(itemParamItem);
        return TaotaoResult.ok();
    }
}
