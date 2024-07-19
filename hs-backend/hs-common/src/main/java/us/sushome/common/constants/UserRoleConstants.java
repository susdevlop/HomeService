package us.sushome.common.constants;

/**
 * RoleConstants
 *
 * @author star
 */
public final class UserRoleConstants {

    private UserRoleConstants() {
        throw new IllegalStateException("Cannot create instance of static constant class");
    }


    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    public static final String ROLE_USER = "ROLE_USER";

    public static final String GUEST_USER = "GUEST_USER"; //暂时此处用于某些情况下没有权限的人的权限配置，其余的未来应当在数据库中取

}
