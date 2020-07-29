package com.lin.common.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: Joe
 * @created: 2020/7/25 19:20
 */
@Data
public class LoginDto implements Serializable {


    @NotBlank(message="昵称不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;




}
