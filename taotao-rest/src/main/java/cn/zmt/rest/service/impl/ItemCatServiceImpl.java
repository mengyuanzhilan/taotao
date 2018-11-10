package cn.zmt.rest.service.impl;

import cn.zmt.mapper.TbItemCatMapper;
import cn.zmt.pojo.TbItemCat;
import cn.zmt.pojo.TbItemCatExample;
import cn.zmt.rest.pojo.CatNode;
import cn.zmt.rest.pojo.CatResult;
import cn.zmt.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询节点信息
 * @author 赵铭涛
 * @creation time 2018/11/10 - 8:47
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;

    /**
     * 获取商品分类列表
     * @return
     */
    @Override
    public CatResult getitemCatList() {
        CatResult catResult = new CatResult();
        //查询分类列表
        catResult.setData(getCatList(0));
        return catResult;
    }

    /**
     * 查询分类列表
     * @param parentId
     * @return
     */
    private List<?> getCatList(long parentId){
        //创建查询条件
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        //返回值list
        List resultList = new ArrayList<>();
        //向list中添加节点
        int count = 0;
        for (TbItemCat tbItemCat : list) {
            //判断是否为父节点
            if (tbItemCat.getIsParent()) {
                CatNode catNode = new CatNode();
                if (parentId==0) {
                    catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
                }else {
                    catNode.setName(tbItemCat.getName());
                }
                catNode.setUrl("/products/\"+tbItemCat.getId()+\".html");
                //递归查询子节点
                catNode.setItem(getCatList(tbItemCat.getId()));
                resultList.add(catNode);
                count++;
                //第一层只取14条记录
                if (parentId == 0 && count >=14) {
                    break;
                }
            }else {
                //如果是叶子节点
                resultList.add("/products/"+tbItemCat.getId()+".html|" + tbItemCat.getName());
            }
        }
        return resultList;
    }
}
