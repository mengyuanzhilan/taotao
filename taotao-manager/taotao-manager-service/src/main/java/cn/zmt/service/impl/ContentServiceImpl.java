package cn.zmt.service.impl;

import cn.zmt.mapper.TbContentMapper;
import cn.zmt.pojo.*;
import cn.zmt.service.ContentService;
import cn.zmt.utils.HttpClientUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zmt
 * @date 2018/11/12 - 3:29
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    TbContentMapper contentMapper;
    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_CONTENT_SYNC_URL}")
    private String REST_CONTENT_SYNC_URL;
    /**
     * 根据categoryId分页查询
     * @param page
     * @param rows
     * @param categoryId
     * @return
     */
    @Override
    public EUDataGridResult getContentList(int page, int rows, long categoryId) {
        //查询条件
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        //分页处理
        PageHelper.startPage(page,rows);
        //查询
        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
        //创建一个返回值对象
        EUDataGridResult result = new EUDataGridResult();
        //取记录总数
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());//获取记录总数
        result.setRows(list);
        return result;
    }

    /**
     * 添加
     * @param content
     * @return
     */
    @Override
    public TaotaoResult insertContent(TbContent content) {
        //补全pojo
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);
        //添加缓冲同步逻辑
        try {
            HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId());
        }catch (Exception e){
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }

    /**
     * 修改
     * @param content
     * @return
     */
    @Override
    public TaotaoResult updateContent(TbContent content) {
        //补全pojo
        content.setUpdated(new Date());
        contentMapper.updateByPrimaryKeyWithBLOBs(content);
        //添加缓冲同步逻辑
        try {
            HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId());
        }catch (Exception e){
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Override
    public TaotaoResult deleteContent(long[] ids) {
        for (long id : ids) {
            contentMapper.deleteByPrimaryKey(id);
        }
        //添加缓冲同步逻辑
        try {
            HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+ids[ids.length-1]);
        }catch (Exception e){
            e.printStackTrace();
        }
        return TaotaoResult.ok();
    }
}
