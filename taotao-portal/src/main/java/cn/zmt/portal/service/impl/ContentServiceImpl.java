package cn.zmt.portal.service.impl;

import cn.zmt.pojo.TaotaoResult;
import cn.zmt.pojo.TbContent;
import cn.zmt.portal.service.ContentService;
import cn.zmt.utils.HttpClientUtil;
import cn.zmt.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调用服务层查询内容列表
 * @author zmt
 * @date 2018/11/12 - 4:43
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_INDEX_AD_URL}")
    private String REST_INDEX_AD_URL;
    @Override
    public String getContentList() {
        //调用服务层的服务
        String result = HttpClientUtil.doGet(REST_BASE_URL+REST_INDEX_AD_URL);
        //把字符串转换成TaotaoResult
        try {
            TaotaoResult taotaoResult = TaotaoResult.formatToList(result, TbContent.class);
            //取内容列表
            List<TbContent> list = (List<TbContent>) taotaoResult.getData();
            List<Map> resultList = new ArrayList<>();
            //创建一个jsp页面要求的pojolist列表
            for (TbContent tbContent : list) {
                Map map = new HashMap<>();
                map.put("src", tbContent.getPic());
                map.put("height", 240);//高度
                map.put("width", 670);//宽度
                map.put("srcB", tbContent.getPic2());
                map.put("widthB", 550);
                map.put("heightB", 240);
                map.put("href", tbContent.getUrl());//地址
                map.put("alt", tbContent.getSubTitle());//鼠标悬浮
                resultList.add(map);
            }
            return JsonUtils.objectToJson(resultList);//转换成json数据
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
