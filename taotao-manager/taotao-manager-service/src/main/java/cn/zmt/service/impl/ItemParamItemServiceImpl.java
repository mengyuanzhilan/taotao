package cn.zmt.service.impl;

import cn.zmt.JsonUtils;
import cn.zmt.TaotaoResult;
import cn.zmt.mapper.TbItemParamItemMapper;
import cn.zmt.pojo.TbItemParamItem;
import cn.zmt.pojo.TbItemParamItemExample;
import cn.zmt.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 商品详细规格参数
 * @author zmt
 * @date 2018/11/7 - 21:19
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {
    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    /**
     * 根据商品id查询规格参数
     * @param itemId
     * @return
     */
    @Override
    public String getItemParamByItemId(Long itemId) {
        //根据商品id查询规格参数
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        //执行查询
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list==null||list.size()==0){
            return "";
        }
        //取规格参数信息
        TbItemParamItem itemParamItem = list.get(0);
        String paramData = itemParamItem.getParamData();
        //生成html
        // 把规格参数json数据转换成java对象
        List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
        StringBuffer sb = new StringBuffer();
        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
        sb.append("    <tbody>\n");
        for(Map m1:jsonList) {
            sb.append("        <tr>\n");
            sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
            sb.append("        </tr>\n");
            List<Map> list2 = (List<Map>) m1.get("params");
            for(Map m2:list2) {
                sb.append("        <tr>\n");
                sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
                sb.append("            <td>"+m2.get("v")+"</td>\n");
                sb.append("        </tr>\n");
            }
        }
        sb.append("    </tbody>\n");
        sb.append("</table>");
        return sb.toString();
    }

    /**
     * 通过id获取详情规格参数
     * @param itemId
     * @return
     */
    @Override
    public TaotaoResult getItemParamByItemIdPojo(Long itemId) {
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        return TaotaoResult.ok(list);
    }
}
