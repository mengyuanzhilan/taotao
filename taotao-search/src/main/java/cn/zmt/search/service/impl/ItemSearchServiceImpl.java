package cn.zmt.search.service.impl;

import cn.zmt.search.dao.ItemSearchDao;
import cn.zmt.search.pojo.SearchResult;
import cn.zmt.search.service.ItemSearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 搜索Service
 * @author zmt
 * @date 2018/11/13 - 20:54
 */
@Service
public class ItemSearchServiceImpl implements ItemSearchService {
    @Autowired
    private ItemSearchDao itemSearchDao;

    @Override
    public SearchResult search(String queryString, int page, int rows) throws Exception {
        //创建查询对象
        SolrQuery query = new SolrQuery();
        //设置查询条件
        query.setQuery(queryString);
        //设置分页
        query.setStart((page - 1) * rows);
        query.setRows(rows);
        //设置默认搜索域
        query.set("df","item_keywords");
        //设置高亮显示
        query.setHighlight(true);
        //设置高亮显示的域
        query.addHighlightField("item_title");
        //高亮显示前缀
        query.setHighlightSimplePre("<em style=\"color:red\">");
        //后缀
        query.setHighlightSimplePost("</em>");
        //执行查询
        SearchResult searchResult = itemSearchDao.search(query);
        //计算结果总页数
        long recordCount = searchResult.getRecordCount();
        long pageCount = recordCount / rows;
        if (recordCount % rows > 0) {
            pageCount++;
        }
        searchResult.setPageCont(pageCount);
        searchResult.setCurPage(page);

        return searchResult;
    }
}
