package cn.zmt.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;



@Getter
@Setter
@ToString
public class TbItem {
    private Long id;

    private String title;

    private String sellPoint;

    private Long price;

    private Integer num;

    private String barcode;

    private String image;

    private Long cid;

    private Byte status;

    private Date created;

    private Date updated;
}