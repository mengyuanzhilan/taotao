package cn.zmt.service.impl;

import cn.zmt.EUDataGridResult;
import cn.zmt.TaotaoResult;
import cn.zmt.mapper.TbItemParamMapper;
import cn.zmt.pojo.TbItem;
import cn.zmt.pojo.TbItemExample;
import cn.zmt.pojo.TbItemParam;
import cn.zmt.pojo.TbItemParamExample;
import cn.zmt.service.ItemParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *商品规格参数
 * @author zmt
 * @date 2018/11/7 - 16:54
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    private TbItemParamMapper tbItemParamMapper;

    /**
     * 根据id查询itemparam
     * @param id
     * @return
     */
    @Override
    public TaotaoResult getTtemParamByCid(Long id) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(id);
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
        if(list!=null&&list.size()>0){
            return TaotaoResult.ok(list);
        }
        return TaotaoResult.ok();
    }

    /**
     * 添加参数模板
     * @param itemParam
     * @return
     */
    @Override
    public TaotaoResult insertItemParam(TbItemParam itemParam) {
        //补全pojo
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        //插入到规格参数模板表
        tbItemParamMapper.insert(itemParam);
        return TaotaoResult.ok();
    }

    /**
     * 分页获取列表
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EUDataGridResult getItemParamList(int page, int rows) {
        //查询商品列表
        //分页处理
        PageHelper.startPage(page,rows);
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBsWithCatName(null);
        //创建一个返回值对象
        EUDataGridResult result = new EUDataGridResult();
        //取记录总数
        PageInfo<TbItemParam> pageInfo = new PageInfo<TbItemParam>(list);
        for (TbItemParam itemParam : list) {
            System.out.println(itemParam);
        }
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);
        return result;
    }
    /**
     * 删除参数模板
     * @param ids
     * @return
     */
    public TaotaoResult deleteItemParam(Long[] ids) throws Exception {
        int count = 0;
        for (Long id : ids) {
            count = tbItemParamMapper.deleteByPrimaryKey(id);
            if(count==0){
                throw new Exception();
            }
        }
        return TaotaoResult.ok();
    }
}
