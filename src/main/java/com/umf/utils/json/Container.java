package com.umf.utils.json;

import java.io.Serializable;
import java.util.List;

public class Container implements Serializable {

    private String key;
    private String name;
    private List<ListChild> list;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListChild> getList() {
        return list;
    }

    public void setList(List<ListChild> list) {
        this.list = list;
    }
}
