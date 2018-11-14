package cn.zmt.portal.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 赵铭涛
 * @creation time 2018/11/14 - 16:43
 */
@Setter
@Getter
public class CatrItem {
    private long id;
    private String title;
    private long price;
    private String image;
    private Integer num;
}
