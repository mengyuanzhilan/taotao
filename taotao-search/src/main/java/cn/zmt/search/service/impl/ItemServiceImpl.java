package cn.zmt.search.service.impl;

import cn.zmt.pojo.TaotaoResult;
import cn.zmt.search.mapper.ItemMapper;
import cn.zmt.search.pojo.Item;
import cn.zmt.search.service.ItemService;
import cn.zmt.utils.ExceptionUtil;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zmt
 * @date 2018/11/13 - 4:11
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private HttpSolrClient httpSolrClient;
    @Override
    public TaotaoResult importAllItems() {
        try {
            //查询商品列表
            List<Item> list = itemMapper.getItemList();

            //把商品信息写入索引库
            for (Item item : list) {
                //创建一个SolrInputDocument对象
                SolrInputDocument document = new SolrInputDocument();
                document.setField("id",item.getId());
                document.setField("item_title",item.getTitle());
                document.setField("item_sell_point",item.getSell_point());
                document.setField("item_price",item.getPrice());
                document.setField("item_image",item.getImage());
                document.setField("item_category_name",item.getCategory_name());
                document.setField("item_desc",item.getItem_des());
                //写入索引库
                httpSolrClient.add(document);
            }
        }catch (Exception e){
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }
}
