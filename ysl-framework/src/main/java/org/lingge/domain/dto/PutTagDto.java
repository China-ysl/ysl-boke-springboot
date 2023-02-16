package org.lingge.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor//空参构造
@AllArgsConstructor//有参构造
@ToString
public class PutTagDto {
    private Long id;
    private String name;

    private String remark;
}
