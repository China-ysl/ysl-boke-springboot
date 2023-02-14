package org.lingge.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminUserInofVo {

    private List<String> Permissions;

    private List<String> roles;

    private UserinfoVo user;


}
