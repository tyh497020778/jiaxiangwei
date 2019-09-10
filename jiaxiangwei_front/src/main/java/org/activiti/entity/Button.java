package org.activiti.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

/**
 * Created by administration on 2019/8/10.
 */
@Getter
@Setter
public class Button {
    private String key;
    private String name;

    public Button(String key, String name) {
        this.key=key;
        this.name=name;
    }

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
}
