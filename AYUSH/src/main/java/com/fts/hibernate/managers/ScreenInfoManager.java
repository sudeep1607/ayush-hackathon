package com.fts.hibernate.managers;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.fts.hibernate.common.GenericManager;
import com.fts.hibernate.models.ScreenInfo;

@Repository
public class ScreenInfoManager extends GenericManager<ScreenInfo, Long>
{
    @SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog(ScreenInfoManager.class);

    public ScreenInfoManager()
    {
        super(ScreenInfo.class);
    }

    @SuppressWarnings("unchecked")
    public List<ScreenInfo> getScreensByMenuId(Long menuId, Long userId,Long roleId)
    {
        String sql = "select si.* from SCREEN_INFO si  "
	                + " left outer join MENU_INFO mi on si.menu_id = mi.id"
	                + " left outer join role_screen rs on rs.screen_id = si.id"
	                + " left outer join user_role ur on ur.role_id = rs.role_id"
	                + " where si.status = 1 and mi.status = 1 and ur.user_id = " + userId + " and si.menu_id = " + menuId
	                + " and  mi.id !=5 and rs.role_id = "+ roleId
	                + " order by si.priority asc";
        return findByNativeSql(sql);
    }

}
