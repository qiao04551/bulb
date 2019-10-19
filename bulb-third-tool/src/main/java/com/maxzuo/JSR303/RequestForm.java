package com.maxzuo.JSR303;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by zfh on 2019/10/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestForm extends BaseForm {

    @NotNull(message = "userId不能为空！")
    private Integer userId;

    @NotEmpty(message = "username不能为空！")
    private String username;
}
