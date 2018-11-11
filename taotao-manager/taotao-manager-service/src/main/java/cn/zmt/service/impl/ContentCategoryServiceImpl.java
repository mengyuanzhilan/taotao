package cn.zmt.service.impl;

import cn.zmt.mapper.TbContentCategoryMapper;
import cn.zmt.pojo.EUTreeNode;
import cn.zmt.pojo.TaotaoResult;
import cn.zmt.pojo.TbContentCategory;
import cn.zmt.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 赵铭涛
 * @creation time 2018/11/10 - 13:35
 */
@Service
public class ContentCategoryServiceImpl implements cn.zmt.service.ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    /**
     * 根据id查询节点列表
     * @param parentId
     * @return
     */
    @Override
    public List<EUTreeNode> getCategoryList(long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EUTreeNode> nodeList = new ArrayList<>();
        for (TbContentCategory contentCategory : list) {
            //创建一个节点
            EUTreeNode node = new EUTreeNode();
            node.setId(contentCategory.getId());
            node.setText(contentCategory.getName());
            node.setState(contentCategory.getIsParent()?"closed":"open");
            nodeList.add(node);
        }
        return nodeList;
    }

    /**
     * 添加
     * @param parentId 父节点id
     * @param name 节点名称
     * @return
     */
    @Override
    public TaotaoResult insertContentCategory(long parentId, String name) {
        //补完pojo
        TbContentCategory category = new TbContentCategory();
        category.setName(name);
        //该类目是否为父类目，1为true，0为false'
        category.setIsParent(false);
        //'状态。可选值:1(正常),2(删除)'
        category.setStatus(1);
        //父类目ID=0时，代表的是一级的类目
        category.setParentId(parentId);
        //排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
        category.setSortOrder(1);
        category.setCreated(new Date());
        category.setUpdated(new Date());
        //添加记录
        contentCategoryMapper.insertSelective(category);
        //查看父节点的IsParent列是否为true，如果不是true则改成true
        TbContentCategory category1 = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!category1.getIsParent()) {
            category1.setIsParent(false);
            //更新父节点
            contentCategoryMapper.updateByPrimaryKey(category1);
        }
        //返回结果
        return TaotaoResult.ok(category);
    }

    /**
     * 删除
     * @param parentId
     * @param id
     * @return
     */
    @Override
    public TaotaoResult deleteContentCategory(long parentId, long id) {
        //删除当前节点
        contentCategoryMapper.deleteByPrimaryKey(id);
        //判断父节点下是否存在子节点，如果没有子节点则把父节点变为子节点 isparent改为false
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        //判断父节点下是否存在子节点
        if (list.size()==0||list==null) {
            //不存在子节点，把父节点变为子节点 isparent改为false
            TbContentCategory category = new TbContentCategory();
            category.setIsParent(false);
            contentCategoryMapper.updateByExampleSelective(category,example);
        }
        return TaotaoResult.ok();
    }


    /**
     * 修改节点名称
     * @return
     */
    @Override
    public TaotaoResult updateContentCategoryName(long id, String name) {
        //补全pojo
        TbContentCategory category = new TbContentCategory();
        category.setId(id);
        category.setName(name);
        category.setUpdated(new Date());
        contentCategoryMapper.updateByPrimaryKey(category);
        return TaotaoResult.ok();
    }
}
