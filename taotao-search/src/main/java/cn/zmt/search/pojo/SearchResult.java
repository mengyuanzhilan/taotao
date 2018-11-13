package cn.zmt.search.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author zmt
 * @date 2018/11/13 - 20:13
 */
@Getter
@Setter
public class SearchResult {
    //商品列表
    private List<Item> itemList;
    //总记录数
    private long recordCount;
    //总页数
    private long pageCont;
    //当前页
    private long curPage;
}
