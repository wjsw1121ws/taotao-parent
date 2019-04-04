package com.wcc.taotao.mapper;

import com.wcc.taotao.pojo.TbItem;

import java.util.List;

/**
 * @Description:
 * @ClassName: TestMapper
 * @Auther: changchun_wu
 * @Date: 2018/12/22 17:53
 * @Version: 1.0
 **/
public interface TestMapper {
    String selectNow();

    List<TbItem> testPageHelp();
}
