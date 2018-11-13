package cn.zmt.search.dao;

import cn.zmt.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * @author zmt
 * @date 2018/11/13 - 20:22
 */
public interface ItemSearchDao {
    /**
     * 查询商品列表
     * @param query
     * @return
     * @throws Exception
     */
    SearchResult search(SolrQuery query) throws Exception;
}
