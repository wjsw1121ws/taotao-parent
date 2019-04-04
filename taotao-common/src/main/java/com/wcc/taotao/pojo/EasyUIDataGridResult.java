package com.wcc.taotao.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: EasyUI返回数据
 * @ClassName: EasyUIDataGridResult
 * @Auther: changchun_wu
 * @Date: 2018/12/23 23:59
 * @Version: 1.0
 **/
public class EasyUIDataGridResult<T> implements Serializable {
    private Long total;
    private List<T> rows;

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getTotal() {
        return total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
