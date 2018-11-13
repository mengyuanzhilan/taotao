package cn.zmt.portal.service.impl;

import cn.zmt.pojo.TaotaoResult;
import cn.zmt.portal.pojo.SearchResult;
import cn.zmt.portal.service.SearchService;
import cn.zmt.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zmt
 * @date 2018/11/13 - 23:51
 */
@Service
public class SearchServiceImpl  implements SearchService {
    @Value("${SEARCH_BASE_URL}")
    private String  SEARCH_BASE_URL;

    @Override
    public SearchResult search(String queryString, int page) {
        //调用taotoa-search
        //查询参数
        Map<String,String> param = new HashMap<>();
        param.put("q",queryString);
        param.put("page",page+"");
        try {
            //调用服务
            String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
            //把字符串转换成java对象
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, SearchResult.class);
            if (taotaoResult.getStatus()==200) {
                SearchResult searchResult = (SearchResult) taotaoResult.getData();
                return searchResult;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
