package cn.zmt.portal.pojo;

import cn.zmt.pojo.TbItem;

/**
 * @author zmt
 * @date 2018/11/14 - 3:43
 */
public class ItemInfo extends TbItem {

    public String[] getImages() {
        String image = getImage();
        if(image!=null){
            String[] images = image.split(",");
            return images;
        }
        return null;
    }
}
