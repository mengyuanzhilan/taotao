package cn.zmt.portal.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zmt
 * @date 2018/11/13 - 4:03
 */
@Setter
@Getter
public class Item {

    private String id;
    private String title;
    private String sell_point;
    private long price;
    private String image;
    private String category_name;
    private String item_des;

    public String[] getImages() {
        if(image!=null){
            String[] images = image.split(",");
            return images;
        }
        return null;
    }
}
