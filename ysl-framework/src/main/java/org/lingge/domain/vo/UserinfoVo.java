package org.lingge.domain.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
//链式访问，该注解设置chain=true，生成setter方法返回this（也就是返回的是对象），代替了默认的返回void。
@Accessors(chain = true)
public class UserinfoVo {
    /**
     * 主键
     */
    private Long id;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;
}
