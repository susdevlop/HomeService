package us.sushome.db.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author sushome
 * @since 2024-07-20
 */
@ToString
@Getter
@Setter
@TableName("hs_roles")
@ApiModel(value = "HsRoles对象", description = "权限表")
public class HsRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("权限 id")
    @TableField("roles_id")
    private Integer rolesId;

    @ApiModelProperty("权限名")
    @TableField("roles_name")
    private String rolesName;

    @ApiModelProperty("0关闭 1开启")
    @TableField("roles_enable")
    private Byte rolesEnable;
}
