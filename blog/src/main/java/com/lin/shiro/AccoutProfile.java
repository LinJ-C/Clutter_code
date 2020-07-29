package com.lin.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: Joe
 * @created: 2020/7/1 14:55
 */

@Data
public class AccoutProfile implements Serializable {

    private Long id;

    private String username;

    private String avatar;

    private String email;
}
