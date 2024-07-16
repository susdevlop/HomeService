package us.sushome.db.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * user table 
 * </p>
 *
 * @author sushome
 * @since 2024-07-16
 */
@Getter
@Setter
@TableName("hs_user")
@ApiModel(value = "HsUser对象", description = "user table ")
public class HsUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableField("user_id")
    private String userId;

    @TableField("user_name")
    private String userName;

    @TableField("user_passwd")
    private String userPasswd;

    @ApiModelProperty("1 male 2 female")
    @TableField("user_sex")
    private Byte userSex;

    @ApiModelProperty("email")
    @TableField("user_email")
    private String userEmail;

    @TableField("user_cellphone")
    private String userCellphone;

    @TableField("user_openid")
    private String userOpenid;
}
