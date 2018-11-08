package cn.zmt.service.impl;

import cn.zmt.EUDataGridResult;
import cn.zmt.IDUtils;
import cn.zmt.TaotaoResult;
import cn.zmt.mapper.TbItemDescMapper;
import cn.zmt.mapper.TbItemMapper;
import cn.zmt.mapper.TbItemParamItemMapper;
import cn.zmt.pojo.*;
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
    private TbItemDescMapper tbItemDescMapper;

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
        tbItemDescMapper.insert(itemDesc);
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
    /**
     * 修改商品信息
     * @param item 商品信息
     * @param desc 商品描述
     * @param itemParam 商品参数
     * @return
     * @throws Exception
     */
    public TaotaoResult updateItem(TbItem item,String desc,String itemParam) throws Exception {
        //修改时间
        Long itemId = item.getId();
        item.setUpdated(new Date());
        tbItemMapper.updateByPrimaryKeySelective(item);
        TaotaoResult result = updateItemDesc(itemId,desc);
        if(result.getStatus()!=200){
            throw  new Exception();
        }
        result = updateItemParamItem(itemId,itemParam);
        if(result.getStatus()!=200){
            throw  new Exception();
        }
        return TaotaoResult.ok();
    }

    /**
     * 修改商品描述
     * @param itemId
     * @param desc
     * @return
     */
    private TaotaoResult updateItemDesc(Long itemId,String desc) throws Exception {
        //先确认是否有这条数据
        TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        if(itemDesc==null){
            insertItemDesc(itemId,desc);
        }else{
            itemDesc = new TbItemDesc();
            //补全pojo
            itemDesc.setItemId(itemId);
            itemDesc.setUpdated(new Date());
            itemDesc.setItemDesc(desc);
            tbItemDescMapper.updateByPrimaryKeySelective(itemDesc);
        }
        return TaotaoResult.ok();
    }

    /**
     * 修改参数
     * @param itemId
     * @param itemParam
     * @return
     */
    private TaotaoResult updateItemParamItem(Long itemId,String itemParam){
        //补全pojo
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setUpdated(new Date());
        itemParamItem.setParamData(itemParam);
        //添加条件
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        //先确认是否有这条数据
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if(list==null||list.size()==0){//如果没有就新添加一条
            insertItemParamItem(itemId,itemParam);
        }else{//如果有就修改
            //提交修改
            tbItemParamItemMapper.updateByExampleSelective(itemParamItem,example);
        }
        return TaotaoResult.ok();
    }

    /**
     * 删除商品
     * @param itemId
     * @return
     * @throws Exception
     */
    @Override
    public TaotaoResult deleteItem(Long[] itemId) throws Exception {
        for (Long id : itemId) {
            TaotaoResult result = deleteItemDesc(id);
            if(result.getStatus()!=200){
                throw  new Exception();
            }
            result = deleteItemParamItem(id);
            if(result.getStatus()!=200){
                throw  new Exception();
            }
            tbItemMapper.deleteByPrimaryKey(id);
        }
        return TaotaoResult.ok();
    }

    /**
     * 删除关联商品的描述
     * @param itemId
     * @return
     */
    private TaotaoResult deleteItemDesc(Long itemId){
        tbItemDescMapper.deleteByPrimaryKey(itemId);
        return TaotaoResult.ok();
    }

    /**
     * 删除关联商品的参数
     * @param itemId
     * @return
     */
    private TaotaoResult deleteItemParamItem(Long itemId){
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        tbItemParamItemMapper.deleteByExample(example);
        return TaotaoResult.ok();
    }

    /**
     * 下架商品
     * @param ids
     * @return
     */
    @Override
    public TaotaoResult upOrDownItem(Long[] ids,Integer or) throws Exception {
        TbItem item = new TbItem();
        item.setStatus(Byte.parseByte(or+""));
        int count = 0;
        for (Long id : ids) {
            item.setId(id);
            count = tbItemMapper.updateByPrimaryKeySelective(item);
            if(count==0){
                throw new Exception();
            }
        }
        return TaotaoResult.ok();
    }

}
