package com.wcc.taotao.service;

import com.wcc.taotao.pojo.TbItem;

import java.util.List;

/**
 * @Description:
 * @ClassName: TestMapper
 * @Auther: changchun_wu
 * @Date: 2018/12/20 0:54
 * @Version: 1.0
 **/
public interface TestService{
    String selectNow();

    List<TbItem> testPageHelp();
}
