package cn.zmt.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author 赵铭涛
 * @creation time 2018/11/2 - 12:03
 * 分页转换
 */
@Getter
@Setter
@ToString
public class EUDataGridResult {

    private long total;
    private List<?> rows;

}
