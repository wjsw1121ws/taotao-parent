package com.wcc.taotao.pojo;

import java.io.Serializable;

/**
 * @Description:
 * @ClassName: EasyUiTreeNodeResult
 * @Auther: changchun_wu
 * @Date: 2018/12/26 0:26
 * @Version: 1.0
 **/
public class EasyUiTreeNodeResult implements Serializable {
    private Long id;
    private String text;
    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
