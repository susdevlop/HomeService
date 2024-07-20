package us.sushome.db.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("hs_permission")
@ApiModel(value = "HsPermission对象", description = "权限表")
public class HsPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pm_id", type = IdType.AUTO)
    private Integer pmId;

    @TableField("pm_role_id")
    private Integer pmRoleId;

    @TableField("pm_permission")
    private String pmPermission;
}
