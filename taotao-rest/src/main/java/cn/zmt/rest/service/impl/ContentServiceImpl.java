package cn.zmt.rest.service.impl;

import cn.zmt.mapper.TbContentMapper;
import cn.zmt.pojo.TbContent;
import cn.zmt.pojo.TbContentExample;
import cn.zmt.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zmt
 * @date 2018/11/12 - 4:04
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbContentMapper contentMapper;

    /**
     * 根据内容分类id查询内容列表
     * @param contentCid
     * @return
     */
    @Override
    public List<TbContent> getContentList(long contentCid) {
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(contentCid);
        //执行查询
        return contentMapper.selectByExampleWithBLOBs(example);
    }
}
