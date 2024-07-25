package com.fts.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fts.ThreadLocalData;
import com.fts.components.AppContext;
import com.fts.components.AutoCompleteComponent;
import com.fts.components.ComboComponent;
import com.fts.components.GridComponent;
import com.fts.components.MailTemplateService;
import com.fts.components.ServiceComponent;
import com.fts.dtos.ComboDTO;
import com.fts.dtos.RolesDTO;
import com.fts.dtos.TreeNodeDTO;
import com.fts.hibernate.dto.GridPaginationDTO;
import com.fts.hibernate.managers.DesignationInfoManager;
import com.fts.hibernate.managers.HQLQueryManager;
import com.fts.hibernate.managers.MenuInfoManager;
import com.fts.hibernate.managers.RoleManager;
import com.fts.hibernate.managers.ScreenInfoManager;
import com.fts.hibernate.managers.SectionManager;
import com.fts.hibernate.managers.UserManager;
import com.fts.hibernate.managers.UserSectionManager;
import com.fts.hibernate.models.DesignationInfo;
import com.fts.hibernate.models.MenuInfo;
import com.fts.hibernate.models.Role;
import com.fts.hibernate.models.ScreenInfo;
import com.fts.hibernate.models.Section;
import com.fts.hibernate.models.UserInfo;
import com.fts.hibernate.models.UserSection;
import com.fts.security.Digester;
import com.fts.utils.DateUtils;

@Service
public class UserInfoService implements GridComponent, ServiceComponent, ComboComponent,AutoCompleteComponent
{
    private static final Log LOG = LogFactory.getLog(UserInfoService.class);

    @Autowired
    private UserManager userManager;

    @Autowired
    private Digester digester;

    @Autowired
    private MenuInfoManager menuInfoManager;
    
    @Autowired
    private ScreenInfoManager screenInfoManager;

    @Autowired
    private HQLQueryManager hqlQueryManager;

    @Autowired
    private CommonService  commonService;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private Properties appConfig;
    
    @Autowired
    private DesignationInfoManager designationInfoManager;
    
    @Autowired
    private SectionManager sectionManager;
    
    @Autowired
    private UserSectionManager userSectionManager;
    
    @Override
    public GridPaginationDTO getGridDataInJSON(Vector<String> sortInfo, String filterString, String start, String limit, String... extraParams) throws Exception
    {
        GridPaginationDTO gridPaginationDTO = new GridPaginationDTO();
        List<UserInfo> records = userManager.filterDataForGrid(sortInfo, filterString, start, limit, extraParams);
        gridPaginationDTO.setRecords(records);
        gridPaginationDTO.setSuccess(true);
        gridPaginationDTO.setTotal(userManager.getRecordsCount(filterString));
        return gridPaginationDTO;
    }
    
    public List<TreeNodeDTO> getScreensByMenuId(Long menuId,Long roleId)
    {
        try
        {
            List<ScreenInfo> screens = screenInfoManager.getScreensByMenuId(menuId, ThreadLocalData.get().getId(),roleId);
            List<TreeNodeDTO> nodes = new ArrayList<TreeNodeDTO>();
            for (ScreenInfo screen : screens)
            {
            	TreeNodeDTO childrenNode = new TreeNodeDTO();
                childrenNode.setId(screen.getId());
                childrenNode.setText(screen.getName());
                childrenNode.setLeaf(true);
                childrenNode.setMethod(screen.getMethod());
                childrenNode.setModule(screen.getModule());
                childrenNode.setIconCls(screen.getIconCls());
                nodes.add(childrenNode);
            }
            return nodes;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            return null;
        }
    }
    
    @Override
	public List<?> getData4AutoComplete(String... extraParams) {
		List<UserInfo> users = new ArrayList<>();
		try 
		{
			String[] temp;
			Section sec=null;
			DesignationInfo desig=null;
			if(extraParams[0]!=null)
			{
				if(extraParams[0].contains(","))
				{
					temp = extraParams[0].split(",");
					sec = sectionManager.getSectionBySecName(temp[0]);
					desig = designationInfoManager.getDesignationByName(temp[1]);
					users =  userManager.getActiveUsersBySectionNdesig(sec!=null?sec.getId():0,desig!=null?desig.getId():0);
				}
				
			}
			else
				users =  userManager.getActiveUsers();
		} 
		catch (Exception e) {
			LOG.info(e.getMessage(),e);
		}
		
		List<ComboDTO> userDto = new ArrayList<ComboDTO>();
		ComboDTO dto=null;
		for(UserInfo usr: users)
		{
			dto = new ComboDTO();
			dto.setId(usr.getId());
			dto.setUserName(usr.getFirstName());
			dto.setSectionId(usr.getSection().getId());
			dto.setDesignationId(usr.getDesignationInfo().getId());
			dto.setSectionName(usr.getSection().getSectionName());
			dto.setDesignationName(usr.getDesignationInfo().getDesignationName());
			userDto.add(dto);
		}
		return userDto;
	}

    @Override
    @Transactional
    public String insert(String jsonData, String... extraParams) throws Exception
    {
        try
        {
            UserInfo userInfo = new ObjectMapper().readValue(jsonData, UserInfo.class);
            String password = null;
             if(userInfo != null && userInfo.getId() != null &&  userInfo.getId() > 0)
            {
            	List<Role> roles = getRoleInfos(userInfo);
            	UserInfo uUserInfo = userManager.get(userInfo.getId());
            	uUserInfo.setModifiedBy(ThreadLocalData.get());
            	uUserInfo.setModifyon(DateUtils.getCurrentSystemTimestamp());
            	uUserInfo.setRoles(getRoleInfos(userInfo));
            	uUserInfo.setActive(userInfo.getActive());
            	uUserInfo.setPhoneNo(userInfo.getPhoneNo());
            	uUserInfo.setEmployeeId(userInfo.getEmployeeId());
            	uUserInfo.setDesignationInfo(getDesignation(userInfo.getDesignationId()));
            	uUserInfo.setSection(userInfo.getSection());
            	if(userInfo.getDefaultRoleId() ==null)
        		uUserInfo.setDefaultRoleId(roles.size()> 0 ? roles.get(0).getId() : null);
            	else
        		uUserInfo.setDefaultRoleId( userInfo.getDefaultRoleId());
            
            	if(userInfo.getDateOfBirth()!=null)
        		uUserInfo.setDateOfBirth(userInfo.getDateOfBirth());
            	uUserInfo.setEmailId(userInfo.getEmailId());
            	uUserInfo.setFirstName(userInfo.getFirstName());
            	uUserInfo.setLastName(userInfo.getLastName());
            	uUserInfo.setMobileNo(userInfo.getMobileNo());
            	uUserInfo.setGender(userInfo.getGender());
            	userInfo.setRoles(roles);
            	uUserInfo.setSection(userInfo.getSection());
            	//uUserInfo.setIsCreateFile(1);
        	    userManager.saveOrUpdate(uUserInfo);
            }else if(userInfo != null)
            {
            	List<Role> roles = getRoleInfos(userInfo);
                password = "ofb";
                String digestPassword = digester.digest(password);
				userInfo.setPassword(digestPassword);
				userInfo.setCreatedon(DateUtils.getCurrentSystemTimestamp());
				userInfo.setCreatedBy(ThreadLocalData.get());
             	if(userInfo.getDefaultRoleId() ==null)
        		userInfo.setDefaultRoleId(roles.size()> 0 ? roles.get(0).getId() : null);
        		userInfo.setDateOfBirth(userInfo.getDateOfBirth());
        		userInfo.setEmployeeId(userInfo.getEmployeeId());
            	userInfo.setRoles(roles);
            	userInfo.setDesignationInfo(getDesignation(userInfo.getDesignationId()));
            	userInfo.setIsCreateFile(0);
                userManager.saveOrUpdate(userInfo);
                
                UserInfo uinf = userManager.getUserByEmplyeeId(userInfo.getEmployeeId());
                UserSection us = new UserSection();
                	us.setUser(uinf);
                	us.setSection(userInfo.getSection());
                userSectionManager.saveOrUpdate(us);
                
            }else
            {
            	 return "Failure";
            }
            return "Success";
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            return "Failure";
        }
    }
    public UserInfo getUserInfoObj(UserInfo userInfo)
    {
    	return userInfo;
    }
    
    public List<Role> getRoleInfos(UserInfo userInfo)
    {
    	List<Role> roleInfos = new ArrayList<Role>();
        String[] roles = userInfo.getRoleIds().split(",");
        for (int i = 0; i < roles.length; i++)
        {
            roleInfos.add(roleManager.get(Long.parseLong(roles[i])));
        }
        
        return roleInfos;
    }
    
    public UserInfo getUserByUserNameAndPwd(String userName, String password)
    {
        try
        {
        	 UserInfo userInfo = userManager.getUserByUserName(userName);
        	 return (userInfo != null) ? ((userInfo.getPassword().equals(password)) ? userInfo : null) : null;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            return null;
        }
    }
    
    public UserInfo getUserByUserName(String userName)
    {
        try
        {
        	 UserInfo userInfo = userManager.getUserByUserName(userName);
        	 return userInfo;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            return null;
        }
    }
    
    public long getUserByValidity(long id)
    {
        try
        {
	  		return hqlQueryManager.validateAuthorisedUser(id);
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            return 0;
        }
    }
    public String deleteUser(Long id) throws Exception
    {
        try
        {
            userManager.delete(userManager.load(id));
            return "Success";
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            return null;
        }
    }

    public List<MenuInfo> getUserMappedMunuItems()
    {
        try
        {
            return menuInfoManager.getUserMappedMunuItems(ThreadLocalData.get().getId(),ThreadLocalData.get().getDefaultRoleId());
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            return new ArrayList<MenuInfo>();
        }
    }
    
    public List<MenuInfo> getUserMappedMunuItems(Long roleId)
    {
        try
        {
            return menuInfoManager.getUserMappedMunuItems(ThreadLocalData.get().getId(),roleId);
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            return new ArrayList<MenuInfo>();
        }
    }

    public String validateEmployeeId(String value, Long id)
    {
        try
        {
            if (commonService.isFieldValueExits(id, value, "employeeId", "UserInfo"))
            {
                return "{\"success\":true,\"message\":\"Employee ID Already Exists.\"}";
            }
            else
            {
                return "{\"success\":true,\"message\":true}";
            }
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            return "{\"success\":false,\"message\":\"Remote Exception\"}";
        }
    }

    public String changePassword(String existingPassword, String newPassword)
    {
        try
        {
            UserInfo userInfo = ThreadLocalData.get();
            if (userInfo.getPassword().equals(digester.digest(existingPassword)))
            {
                userInfo.setPassword(digester.digest(newPassword));
                userManager.saveOrUpdate(userInfo);
                sendMailForPasswordChange(userInfo, newPassword);
                return "true";
            }
            else
            {
                return "FALSE";
            }
        }
        catch (Exception e)
        {
            LOG.error(e.getCause(), e);
            return null;
        }
    }

    public String resetUserPassword(Long id)
    {
        try
        {
            UserInfo userInfo = userManager.get(id);
            if (userInfo.getId() != null)
            {
                String password = "empover#123";
                String digestPassword = digester.digest(password);
                userInfo.setPassword(digestPassword);
                userManager.saveOrUpdate(userInfo);

                sendMailForPasswordChange(userInfo, password);
            }
            return "Success";
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            return "Failure";
        }
    }

    public void sendMailForPasswordChange(UserInfo userInfo, String password)
    {
		String mailSubject = appConfig.getProperty("PASS_CHANGE_EMAIL_SUB");
        String content = appConfig.getProperty("PASS_CHANGE_EMAIL_CONT");
        StringBuilder mailContent = new StringBuilder();
        mailContent.append("Hi " + userInfo.getFirstName() + " " + userInfo.getLastName() + ", <br><br>");
        mailContent.append(content + " " + password);
        mailContent.append("<br><br>");
        String fromAddress = appConfig.getProperty("PASS_CHANGE_FROM_ADDRESS");

        MailTemplateService mailTemplateService = AppContext.getApplicationContext().getBean(MailTemplateService.class);
        mailTemplateService.setValues(fromAddress, new String[]
        {
            userInfo.getEmailId()
        }, new String[] {}, mailSubject, mailContent.toString(), null, null);
        mailTemplateService.start();
    }
    
    public List<RolesDTO> getUserMappedRoles(Long userId)
    {
        try
        {
            List<RolesDTO> rolesDTOs = hqlQueryManager.getUserMappedRoles(userId);
            return rolesDTOs;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            return null;
        }
    }

    public List<RolesDTO> getUserRoles(long authorizeeId)
    {
        try
        {
            List<RolesDTO> rolesDTOs = hqlQueryManager.getUserRoles(authorizeeId,ThreadLocalData.get().getId());
            return rolesDTOs;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            return null;
        }
    }
    
    public Role getUserDefaultRole( )
    {
    	try {
			return roleManager.get(ThreadLocalData.get().getDefaultRoleId());
		} catch (Exception e) {
			 LOG.info(e.getCause(), e);
            return null;
		}
    }
    
    public Role getUserDefaultRole(long roleId)
    {
    	try {
			return roleManager.get(roleId);
		} catch (Exception e) {
			 LOG.info(e.getCause(), e);
            return null;
		}
    }
    public DesignationInfo getDesignation(long designationId)
    {
    	return designationInfoManager.get(designationId);
    }

    @Override
    public List<?> getData4Combo(String... extraParams)
    {
        List<ComboDTO> comboDTOs = new ArrayList<ComboDTO>();
        List<UserInfo> userInfos =null;
        if(extraParams[0]!=null)
        {
        	if(extraParams[0].contains(",")){
        		String[] temp = extraParams[0].split(",");
        		userInfos =  userManager.getActiveUsersBySectionNdesig(Long.parseLong(temp[0]),Long.parseLong(temp[1]));
        	}
        	else
        		userInfos =  userManager.getActiveUsersBySection(Long.parseLong(extraParams[0]));
        }
        else
        	 userInfos =  userManager.getActiveUsers();
        
        if(userInfos!=null){
        for (UserInfo obj : userInfos)
        {
            ComboDTO comboDTO = new ComboDTO();
            comboDTO.setId(obj.getId());
            String lastName = obj.getLastName() != null ? obj.getLastName() : "";
            comboDTO.setName(obj.getFirstName()+" "+lastName);
            comboDTO.setRefId(obj.getDesignationInfo() != null ? obj.getDesignationInfo().getDesignationName() : null);
            comboDTO.setSectionId(obj.getSection().getId());
            comboDTOs.add(comboDTO);
        }
        }
        return comboDTOs;
    }
    
	public List<ComboDTO> getActiveUsers()
	{
		 List<ComboDTO> comboDTOs = new ArrayList<ComboDTO>();
		 List<UserInfo> userInfos =userManager.getActiveUsers();
        for (UserInfo obj : userInfos)
        {
            ComboDTO comboDTO = new ComboDTO();
            comboDTO.setId(obj.getId());
            String lastName = obj.getLastName() != null ? obj.getLastName() : "";
            comboDTO.setName(obj.getFirstName()+" "+lastName);
            comboDTO.setRefId(obj.getDesignationInfo() != null ? obj.getDesignationInfo().getDesignationName() : null);
            comboDTO.setSectionId(obj.getSection().getId());
            comboDTOs.add(comboDTO);
        }
        return comboDTOs;
	}
    
}
