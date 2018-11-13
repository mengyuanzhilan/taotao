package cn.zmt.search.service;

import cn.zmt.search.pojo.SearchResult;
import org.springframework.stereotype.Service;

/**
 * @author zmt
 * @date 2018/11/13 - 20:53
 */
public interface ItemSearchService {
    SearchResult search(String queryString, int page, int rows) throws Exception;
}
