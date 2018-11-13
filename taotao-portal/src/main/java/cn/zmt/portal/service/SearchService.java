package cn.zmt.portal.service;

import cn.zmt.portal.pojo.SearchResult;

/**
 * @author zmt
 * @date 2018/11/13 - 23:50
 */
public interface SearchService {

    SearchResult search(String queryString,int page);
}
