package com.wcc.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wcc.taotao.mapper.TestMapper;
import com.wcc.taotao.pojo.TbItem;
import com.wcc.taotao.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @ClassName: TestMapperServiceImpl
 * @Auther: changchun_wu
 * @Date: 2018/12/20 1:31
 * @Version: 1.0
 **/

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestMapper testMapper;
    @Override
    public String selectNow() {
        return testMapper.selectNow();
    }

    @Override
    public List<TbItem> testPageHelp() {

        PageHelper.startPage(1,5);
        List<TbItem> list = testMapper.testPageHelp();
        PageInfo<TbItem> info = new PageInfo<>(list);
        return list;
    }
}
