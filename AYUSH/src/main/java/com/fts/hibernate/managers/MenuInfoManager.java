package com.fts.hibernate.managers;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.fts.hibernate.common.GenericManager;
import com.fts.hibernate.models.MenuInfo;

@Repository
public class MenuInfoManager extends GenericManager<MenuInfo, Long>
{
    private static final Log LOG = LogFactory.getLog(MenuInfoManager.class);

    public MenuInfoManager()
    {
        super(MenuInfo.class);
    }

   /* @SuppressWarnings("unchecked")
    public List<MenuInfo> getUserMappedMunuItems(long userId)
    {
        try
        {
            String sql = "select mi.* from SCREEN_INFO si  "
                    + " left outer join MENU_INFO mi on si.menu_id = mi.id"
                    + " left outer join role_screen rs on rs.screen_id = si.id"
                    + " left outer join user_role ur on ur.role_id = rs.role_id"
                    + " where si.status = 1 and mi.status = 1 and ur.user_id = " + userId 
                    + " group by mi.id order by mi.priority asc";  
        	
        	
        	String sql = "select mi.* from SCREEN_INFO si  "
                    + " left outer join MENU_INFO mi on si.menu_id = mi.id"
                    + " left outer join role_screen rs on rs.screen_id = si.id"
                    + " left outer join user_role ur on ur.role_id = rs.role_id"
                    + " where si.status = 1 and mi.status = 1 and ur.user_id = " + userId 
                    + " group by mi.id,mi.MENUTEXT,mi.PRIORITY,mi.STATUS,mi.VIEWMODE,mi.ICONCLS  order by mi.priority asc";

            return findByNativeSql(sql);
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return null;
        }
    }*/
    
    @SuppressWarnings("unchecked")
    public List<MenuInfo> getUserMappedMunuItems(long userId,long roleId)
    {
        try
        {
        	String sql = "select mi.* from SCREEN_INFO si  "
                    + " left outer join MENU_INFO mi on si.menu_id = mi.id"
                    + " left outer join role_screen rs on rs.screen_id = si.id"
                    + " left outer join user_role ur on ur.role_id = rs.role_id"
                    + " where si.status = 1 and mi.status = 1 and ur.user_id = " + userId 
                    + " and mi.id != 5  and rs.role_id =" + roleId
                    + " group by mi.id,mi.MENUTEXT,mi.PRIORITY,mi.STATUS,mi.VIEWMODE,mi.ICONCLS  order by mi.priority asc";

            return findByNativeSql(sql);
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return null;
        }
    }

}
