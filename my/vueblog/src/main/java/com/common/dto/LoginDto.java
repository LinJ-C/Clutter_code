package com.common.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * <p>
 *
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2020-07-01
 */
@Data
public class LoginDto implements Serializable {


    @NotBlank(message="昵称不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;




}
