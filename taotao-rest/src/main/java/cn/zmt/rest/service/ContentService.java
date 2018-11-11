package cn.zmt.rest.service;

import cn.zmt.pojo.TbContent;

import java.util.List;

/**
 * @author zmt
 * @date 2018/11/12 - 4:03
 */
public interface ContentService {

    /**
     * 通过id获取
     * @param contentCid
     * @return
     */
    List<TbContent> getContentList(long contentCid);
}
