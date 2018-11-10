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

        return null;
    }
}
