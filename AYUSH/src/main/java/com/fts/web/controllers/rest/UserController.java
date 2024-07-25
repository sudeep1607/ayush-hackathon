package com.fts.web.controllers.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fts.dtos.RolesDTO;
import com.fts.dtos.TreeNodeDTO;
import com.fts.services.UserInfoService;
 

@RestController
public class UserController
{
    @SuppressWarnings("unused")
    private static final Log LOG = LogFactory.getLog(UserController.class);

    @Autowired
    private UserInfoService userInfoService;
    
    
    @RequestMapping("/getScreensByMenuId")
    public List<TreeNodeDTO> getScreensByMenuId(@RequestParam("menuId") Long menuId,@RequestParam("roleId") Long roleId)
    {
        return userInfoService.getScreensByMenuId(menuId,roleId);
    }

    @RequestMapping("/userMappedRoles/{useId}")
    public List<RolesDTO> getUserMappedRoles(@PathVariable("useId") Long userId)
    {
        return userInfoService.getUserMappedRoles(userId);
    }
    
    @RequestMapping(value = "/validateEmployeeId", method = RequestMethod.POST)
    public String validatePersNOs(@RequestParam("value") String value, @RequestParam("id") Long id)
    {
        return userInfoService.validateEmployeeId(value, id);
    }
    
    
    @RequestMapping("/userRoles/{authorizeeId}") 
    public List<RolesDTO> getUserRoles(@PathVariable("authorizeeId") Long authorizeeId)
    {
        return userInfoService.getUserRoles(authorizeeId);
    }
    
    @RequestMapping(value = "/tr/resetUserPassword", method = RequestMethod.POST)
    public String resetUserPassword(@RequestParam("id") Long id) throws Exception
    {
        return userInfoService.resetUserPassword(id);
    }
    
    @RequestMapping("/changePassword")
    public String changePassword(HttpServletRequest request, @RequestParam String existingPassword, @RequestParam String newPassword)
    {
        return userInfoService.changePassword(existingPassword, newPassword);
    }
    
    
}
