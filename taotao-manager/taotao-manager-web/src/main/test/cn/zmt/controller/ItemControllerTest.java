package cn.zmt.controller;

import cn.zmt.mapper.TbItemCatMapper;
import cn.zmt.mapper.TbItemMapper;
import cn.zmt.pojo.TbItem;
import cn.zmt.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author zmt
 * @date 2018/10/31 - 16:10
 * 推荐spring的项目可以使用spring的单元测试，可以自动注入我们需要的组件
 * 1，导入SpringTest模块
 * 2，@ContextConfiguration指定Spring配置文件的位置
 * 3,直接@Autowired
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-*.xml"})
public class ItemControllerTest {
    @Autowired
    TbItemMapper mapper;
    @Test
    public void testPageHelper(){
        TbItemExample example = new TbItemExample();
        PageHelper.startPage(1,10);
        List<TbItem> list = mapper.selectByExample(example);
        for (TbItem i : list) {
            System.out.println(i);
        }
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        System.out.println("共有商品："+pageInfo.getTotal());
    }
}
