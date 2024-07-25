package com.fts.hibernate.managers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fts.dtos.FileTypeActionDTO;
import com.fts.dtos.OrganizationDTO;
import com.fts.dtos.RolesDTO;
import com.fts.dtos.ScreensDTO;
import com.fts.dtos.SectionDTO;
import com.fts.dtos.UserDTO;
import com.fts.hibernate.common.HibernateDao;
import com.fts.ThreadLocalData;
import com.fts.dtos.AlertMonthDTO;
import com.fts.dtos.EnployeeInfoDTO;
import com.fts.dtos.FileEntryDTO;
import com.fts.workflow.dtos.AuthorityDTO;
import com.fts.workflow.dtos.WorkflowAuthorityMapDTO;

@Repository
public class HQLQueryManager
{
    private static final Log LOG = LogFactory.getLog(HQLQueryManager.class);
    @Autowired
    @Qualifier("hibernateDao")
    protected HibernateDao hibernateDao;
    
    /**
     * This method is used to get the screens to map a particular roles
     */

    @SuppressWarnings("unchecked")
    public List<SectionDTO> getUserSectionMappings(Long userId)
    {
        StringBuffer sb = new StringBuffer("Select distinct se.id , se.section_Name as sectionName");
        sb.append(" ,(case when EXISTS (select * from user_section us where us.user_id = " + userId + " and us.section_id = se.id) then 1 else 0 end) As checked ");
        sb.append(" from SECTION se left join user_info ui on ui.section_id=se.id where se.active=1 ");
        LOG.info("VVVVVVVVVV---------->"+sb.toString());
        SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.aliasToBean(SectionDTO.class));
        sqlQuery.addScalar("id", LongType.INSTANCE);
        sqlQuery.addScalar("sectionName", StringType.INSTANCE);
        sqlQuery.addScalar("checked", BooleanType.INSTANCE);
        //sqlQuery.addScalar("viewOnly", BooleanType.INSTANCE);
        return sqlQuery.list();
    }
    
    @SuppressWarnings("unchecked")
    public List<ScreensDTO> getRoleMappingScreens(Long roleId)
    {
        StringBuffer sb = new StringBuffer("Select si.id , si.name , si.description , si.id as menuId , mi.menuText as menuName ");
        sb.append(" ,(case when EXISTS (select * from role_screen rs where rs.role_id = " + roleId + " and rs.screen_id = si.id) then 1 else 0 end) As checked ");
        /*sb.append(" ,(case when EXISTS (select * from role_screen rs where rs.role_id = " + roleId + " and rs.screen_id = si.id) then 1 else 0 end) As viewOnly ");*/
        sb.append(" from screen_info si inner join menu_info mi on mi.id = si.menu_Id where si.status = 1");
        
        SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sb.toString()).setResultTransformer(Transformers.aliasToBean(ScreensDTO.class));
        sqlQuery.addScalar("id", LongType.INSTANCE);
        sqlQuery.addScalar("name", StringType.INSTANCE);
        sqlQuery.addScalar("description", StringType.INSTANCE);
        sqlQuery.addScalar("menuId", LongType.INSTANCE);
        sqlQuery.addScalar("menuName", StringType.INSTANCE);
        sqlQuery.addScalar("checked", BooleanType.INSTANCE);
        //sqlQuery.addScalar("viewOnly", BooleanType.INSTANCE);
        return sqlQuery.list();
    }
    
    /**
     * This method is used to get the roles to map to a particular user
     */
    @SuppressWarnings("unchecked")
    public List<RolesDTO> getUserMappedRoles(Long userId)
    {
        String sql = "Select ri.id , ri.name , ri.description , (case when EXISTS (select * from user_role ur where ur.user_id = " + userId + " and ur.role_id = ri.id  ) then 1 else (case when ri.id =3 then 1 else 0 end) end) As checked , (case when  EXISTS(select * from user_info uf where uf.id = " + userId + "  and uf.DEFAULT_ROLEID = ri.id) then 1 else 0 end) as defaultRole from role ri";
        SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(RolesDTO.class));
        sqlQuery.addScalar("id", LongType.INSTANCE);
        sqlQuery.addScalar("name", StringType.INSTANCE);
        sqlQuery.addScalar("description", StringType.INSTANCE);
        sqlQuery.addScalar("checked", BooleanType.INSTANCE);
        sqlQuery.addScalar("defaultRole", BooleanType.INSTANCE);
        
        return sqlQuery.list();
    }
    
    
      
    /**
     * This method is used to get the roles to map to a particular user
     */
    @SuppressWarnings("unchecked")
    public List<RolesDTO> getUserRoles(Long authorizeeId , Long userId)
    {
    	String sql =  "Select  ri.id,ri.name,ri.description,  (case when EXISTS (select * from user_role ur where ur.user_id = " + authorizeeId + " and ur.role_id = ri.id  ) then 1 else 0 end) As checked , (case when  EXISTS(select * from user_info uf where uf.id =  " + authorizeeId + "  and uf.DEFAULT_ROLEID = ri.id) then 1 else 0 end) as defaultRole from USER_ROLE ur  join ROLE ri on  ri.id =  ur.ROLE_ID and ur.USER_ID=" + userId ; 
        SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(RolesDTO.class));
        LOG.info(sql);
        sqlQuery.addScalar("id", LongType.INSTANCE);
        sqlQuery.addScalar("name", StringType.INSTANCE);
        sqlQuery.addScalar("description", StringType.INSTANCE);
        sqlQuery.addScalar("checked", BooleanType.INSTANCE);
        sqlQuery.addScalar("defaultRole", BooleanType.INSTANCE);
        return sqlQuery.list();
    }
     
    public long validateAuthorisedUser(Long authorizeeId)
    {
    	try {
			String sql = "select id from user_info   where  id = "+ authorizeeId +"  and active = 1 and VALID_FROM <= sysdate and  VALID_TO >= sysdate" ;
			SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(UserDTO.class));
			sqlQuery.addScalar("id", LongType.INSTANCE);
			return   (sqlQuery.list().size() > 0 ? 1: 0);
		} catch (HibernateException e) {
			return 0;
		}
    }
    
    @Transactional
    public long validateUserExistsCount(String authorizeeId)
    {
    	try {
			String sql = "select id from user_info   where active = 1 and employee_Id= '"+authorizeeId+"'" ;
			SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(UserDTO.class));
			sqlQuery.addScalar("id", LongType.INSTANCE);
			return   (sqlQuery.list().size() > 0 ? 1: 0);
		} catch (HibernateException e) {
			LOG.info(e.getMessage());
			return 0;
		}
    }
    
    @SuppressWarnings("unchecked")
    public List<SectionDTO> getSectionCombo(long buildingid)
    {
        try
        {
            String sql = "select s.id,s.section_name as sectionName,s.sectionIncharge_id as sectionInchargeId,s.building_id as buildingId,"
            		+ "d.designationName as designationName from section s "
            		+ "join user_info u on u.id = s.sectionIncharge_id "
            		+ "join DesignationInfo d on d.id = u.designationInfo_id ";
            if(buildingid > 0){
            	sql += " where s.building_id = "+buildingid+"  and s.active= 1 ";
            }
            else{
            	sql += " where s.active= 1 ";
            }
            SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(SectionDTO.class));
            sqlQuery.addScalar("id", LongType.INSTANCE);
            sqlQuery.addScalar("sectionName", StringType.INSTANCE);
            sqlQuery.addScalar("sectionInchargeId", LongType.INSTANCE);
            sqlQuery.addScalar("buildingId", LongType.INSTANCE);
            sqlQuery.addScalar("designationName", StringType.INSTANCE);
            return sqlQuery.list();
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return null;
        }
    }
    @SuppressWarnings("unchecked")
    public List<SectionDTO> getSectionBuilding()
    {
        try
        {
            String sql = "select s.id,CONCAT(s.section_name,'--',b.building_name) as sectionName " +
            		"from section s join BUILDING b on b.id = s.building_id  where s.active=1";
            SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(SectionDTO.class));
            sqlQuery.addScalar("id", LongType.INSTANCE);
            sqlQuery.addScalar("sectionName", StringType.INSTANCE);
          
            return sqlQuery.list();
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<SectionDTO> getSection()
    {
        try
        {
            String sql = "select s.id,s.section_name as sectionName " +
            		"from section s where s.active=1";
            SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(SectionDTO.class));
            sqlQuery.addScalar("id", LongType.INSTANCE);
            sqlQuery.addScalar("sectionName", StringType.INSTANCE);
          
            return sqlQuery.list();
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<SectionDTO> getSectionByUserId()
    {
        try
        {
        	String sql = "";
        	if(ThreadLocalData.get().getDefaultRoleId()==3)
        		sql = " select us.section_id as id,s.section_name as sectionName from User_section us join section s on s.id=us.section_id where s.active=1 and us.user_id="+ThreadLocalData.get().getId();
        	else
        		sql = " select s.id as id,s.section_name as sectionName from Section s where s.active=1 order by section_name asc";
        	LOG.info(" HHHHHHHH--------->"+sql);
            SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(SectionDTO.class));
            sqlQuery.addScalar("id", LongType.INSTANCE);
            sqlQuery.addScalar("sectionName", StringType.INSTANCE);
          
            return sqlQuery.list();
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
	public List<AlertMonthDTO> getAlertForMonthRecord(Vector<String> sortInfo, String filterString, String start,String limit, String[] extraParams) {
		try
        {
            String sql = "";
            if((extraParams != null) && (extraParams.length>0))
            {
                if("3".equals(extraParams[0]))
                {
                	sql = "select fe.id as id,fe.file_name as fileName "
                            + " FROM  file_entry fe "
                            + " join wf_approval_auth_map wfa on wfa.fileEntry_id=fe.id"
                            + "  where fe.active=1 "
                            + " and wfa.approvalAuthority_id="+ThreadLocalData.get().getId()+" "
                            + " and wfa.fileStatus_id=10 ";
                }
                else
                {
                	 sql = "select fe.id as id,fe.file_name as fileName "
                             + " FROM  file_entry fe "
                             + " join wf_approval_auth_map wfa on wfa.fileEntry_id=fe.id"
                             + "  where fe.active=1 "
                             + " and wfa.fileStatus_id=10 ";
                }
                SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(AlertMonthDTO.class));
                sqlQuery.addScalar("id", LongType.INSTANCE);
                sqlQuery.addScalar("fileName", StringType.INSTANCE);
                return  sqlQuery.list();
            }
            return null;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return null;
        }
	}
    
    
    public Long getAlertForMonthRecordsCount(String filterString, String[] extraParams)
    {
        try
        {
            String sql = "";
            if((extraParams != null) && (extraParams.length>0))
            {
                if("3".equals(extraParams[0]))
                {
                	sql = "select fe.id as id,fe.file_name as fileName "
                            + " FROM  file_entry fe "
                            + " join wf_approval_auth_map wfa on wfa.fileEntry_id=fe.id"
                            + "  where fe.active=1 "
                            + " and wfa.approvalAuthority_id="+ThreadLocalData.get().getId()+" "
                            + " and wfa.fileStatus_id=10 ";
                }
                else
                {
                	 sql = "select fe.id as id,fe.file_name as fileName "
                             + " FROM  file_entry fe "
                             + " join wf_approval_auth_map wfa on wfa.fileEntry_id=fe.id"
                             + "  where fe.active=1 "
                             + " and wfa.fileStatus_id=10 ";
                }
                SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(AlertMonthDTO.class));
                sqlQuery.addScalar("id", LongType.INSTANCE);
                sqlQuery.addScalar("fileName", StringType.INSTANCE);
                return (long) sqlQuery.list().size();
            }
            return 0L;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return 0L;
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<OrganizationDTO> getOrganizationCombo()
    {
        try
        {
            String sql = "select org.id,org.organization_name as organizationName,b.organizationIncharge_id as organizationInchargeId,"
            		+ "d.designationName as designationName from organization org "
            		+ "join user_info u on u.id = b.organizationIncharge_id "
            		+ "join DesignationInfo d on d.id = u.designationInfo_id  where b.active = 1";
            SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(OrganizationDTO.class));
            sqlQuery.addScalar("id", LongType.INSTANCE);
            sqlQuery.addScalar("organizationName", StringType.INSTANCE);
            sqlQuery.addScalar("organizationInchargeId", LongType.INSTANCE);
            sqlQuery.addScalar("designationName", StringType.INSTANCE);
            return sqlQuery.list();
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return null;
        }
    }

    public Long getNewlyAssignedFilesCount(String filterString, String[] extraParams)
    {
        try
        {
            String sql = "";
            if((extraParams != null) && (extraParams.length>0))
            {
                if("3".equals(extraParams[0]))
                {
                	sql = "select fe.id as id,fe.file_name as fileName "
                            + " FROM  file_entry fe "
                            + " join wf_approval_auth_map wfa on wfa.fileEntry_id=fe.id"
                            + "  where fe.active=1 "
                            + " and wfa.approvalAuthority_id="+ThreadLocalData.get().getId()+" "
                            + " and wfa.fileStatus_id=10 and wfa.fileInTime is null and wfa.fileOutTime is null ";
                }
                else
                {
                	 sql = "select fe.id as id,fe.file_name as fileName "
                             + " FROM  file_entry fe "
                             + " join wf_approval_auth_map wfa on wfa.fileEntry_id=fe.id"
                             + "  where fe.active=1 "
                             + " and wfa.fileStatus_id=10 and wfa.fileInTime is null and wfa.fileOutTime is null ";
                }
                LOG.info(" QQQQQQQQQQQQ----------->"+sql);
                SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(AlertMonthDTO.class));
                sqlQuery.addScalar("id", LongType.INSTANCE);
                sqlQuery.addScalar("fileName", StringType.INSTANCE);
                return (long) sqlQuery.list().size();
            }
            return 0L;
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
            return 0L;
        }
    }
    
    
    @SuppressWarnings("unchecked")
    public List<AlertMonthDTO> getOneMonthAlertForStoreAdmin(Session session)
    {
        try{
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, +90);
                String date = sdf.format(cal.getTime());
                
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date dat = formatter.parse(date);
                Calendar c = formatter.getCalendar();
                c.setTime(dat);
                long l = c.getTimeInMillis();
                java.sql.Date futureDate = new java.sql.Date(l);
                // "+futureDate+"
                
                String sql = "select ie.id as id,ie.item_name as itemName,ie.item_code as itemCode,ae.supply_order_no as supplyOrderNumber,ie.expiry_Date as expiryDate,s.store_name as storesName,ie.barcode_RFID as barcodeRFID,ie.cnc as cNC ,(ie.total_quantity-ie.issued_quantity+ie.return_quantity-ie.condem_quantity) as quantity "
                        + " ,u.emailId as email, u.first_name as firstName, u.last_name as lastName,s.id as storesId, ie.cost as purchaseCost "
                        + "FROM items_entry ie "
                        + "join asset_entry ae on ie.assetEntry_id = ae.id "
                        + "join Stores s on s.id = ae.store_id "
                        + "join user_info u on u.id = s.store_admin "
                        +"  where 1=1 and (ie.expiry_Date = '"+futureDate+"') and  (ie.total_quantity-ie.issued_quantity+ie.return_quantity-ie.condem_quantity)>0 ";
                
                SQLQuery sqlQuery = (SQLQuery) session.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(AlertMonthDTO.class));
                sqlQuery.addScalar("id", LongType.INSTANCE);
                sqlQuery.addScalar("itemName", StringType.INSTANCE);
                sqlQuery.addScalar("itemCode", StringType.INSTANCE);
                sqlQuery.addScalar("supplyOrderNumber", StringType.INSTANCE);
                sqlQuery.addScalar("expiryDate", DateType.INSTANCE);
                sqlQuery.addScalar("storesName", StringType.INSTANCE);
                sqlQuery.addScalar("barCodeRFID", StringType.INSTANCE);
                sqlQuery.addScalar("cNC", StringType.INSTANCE);
                sqlQuery.addScalar("quantity", DoubleType.INSTANCE);
                sqlQuery.addScalar("email", StringType.INSTANCE);
                sqlQuery.addScalar("firstName", StringType.INSTANCE);
                sqlQuery.addScalar("lastName", StringType.INSTANCE);
                sqlQuery.addScalar("storesId", LongType.INSTANCE);
                sqlQuery.addScalar("purchaseCost", DoubleType.INSTANCE);
                return sqlQuery.list();
            
        }catch(Exception e){
            LOG.info(e.getCause(),e);
            return new ArrayList<AlertMonthDTO>();
        }
    }

    @SuppressWarnings("unchecked")
    public List<AlertMonthDTO> getOneMonthAlertForEmployees(Session session)
    {
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, +90);
            String date = sdf.format(cal.getTime());
            
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date dat = formatter.parse(date);
            Calendar c = formatter.getCalendar();
            c.setTime(dat);
            long l = c.getTimeInMillis();
            java.sql.Date futureDate = new java.sql.Date(l);
            // "+futureDate+"
            
            String sql = "select ii.id as id,ii.item_name as itemName,ii.item_code as itemCode,ii.supply_order_no as supplyOrderNumber,ie.expiry_Date as expiryDate,s.store_name as storesName,ie.barcode_RFID as barcodeRFID,ie.cnc as cNC,(ii.qty_issued-ii.qty_transfered-ii.qty_returned) as quantity "
                            + " ,u.emailId as email, u.first_name as firstName, u.last_name as lastName,s.id as storesId, ie.cost as purchaseCost ,ai.issue_to as issueTo,ai.officer_id  as officerId "        
                            + "FROM issued_items ii "
                            + "join asset_issue ai on ii.assetIssue_id = ai.id "
                            + "join items_entry ie on ie.item_code = ii.item_code "
                            + "join asset_entry ae on ie.assetEntry_id = ae.id "
                            + "join Stores s on s.id = ae.store_id " 
                            + "join user_info u on u.id = ai.officer_id "
                            +"  where 1=1 and (ie.expiry_Date = '"+futureDate+"') and  (ie.total_quantity-ie.issued_quantity+ie.return_quantity-ie.condem_quantity)>0 ";
            
            SQLQuery sqlQuery = (SQLQuery) session.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(AlertMonthDTO.class));
            sqlQuery.addScalar("id", LongType.INSTANCE);
            sqlQuery.addScalar("itemName", StringType.INSTANCE);
            sqlQuery.addScalar("itemCode", StringType.INSTANCE);
            sqlQuery.addScalar("supplyOrderNumber", StringType.INSTANCE);
            sqlQuery.addScalar("expiryDate", DateType.INSTANCE);
            sqlQuery.addScalar("storesName", StringType.INSTANCE);
            sqlQuery.addScalar("barCodeRFID", StringType.INSTANCE);
            sqlQuery.addScalar("cNC", StringType.INSTANCE);
            sqlQuery.addScalar("quantity", DoubleType.INSTANCE);
            sqlQuery.addScalar("email", StringType.INSTANCE);
            sqlQuery.addScalar("firstName", StringType.INSTANCE);
            sqlQuery.addScalar("lastName", StringType.INSTANCE);
            sqlQuery.addScalar("storesId", LongType.INSTANCE);
            sqlQuery.addScalar("purchaseCost", DoubleType.INSTANCE);
            sqlQuery.addScalar("issueTo", StringType.INSTANCE);
            sqlQuery.addScalar("officerId", LongType.INSTANCE);
            return sqlQuery.list();
        
        }catch(Exception e){
            LOG.info(e.getCause(),e);
            return new ArrayList<AlertMonthDTO>();
        }
    }
    
    public void submitRequest(long reqId, long sts)
    {
        try
        {
        	String sql = " update AssetRequest set status='1' where id="+reqId+" ";
            hibernateDao.executeSqlQuery(sql);
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(),e);
        }
    }

	@SuppressWarnings("unchecked")
    public List<AuthorityDTO> getworkflowAuthorityMap(Long fileEntryId)
    {
		//String sql = "Select aa.id,aa.authorityName as name, aa.description as description,(case when EXISTS (select * from wf_approval_auth_map wfa where wfa.approvalAuthority_id = aa.id and  wfa.active =1  and wfa.workflowConfig_id = " + workflowConfigId + "  )  then 1 else 0 end) As checked , (select wfa1.priority from wf_approval_auth_map wfa1 where wfa1.approvalAuthority_id = aa.id and wfa1.active =1 and wfa1.workflowConfig_id = " + workflowConfigId + "  )  As priority from approval_auth aa";
		
		String sql = "Select ui.id,ui.first_Name as name, ui.last_name as description,d.designationName as designationName,s.section_name as sectionName "
				+ ",(case when EXISTS (select * from wf_approval_auth_map wfa where wfa.approvalAuthority_id = ui.id and  wfa.active =1  and wfa.fileEntry_id = " + fileEntryId + "  )  then 1 else 0 end) As checked , (select wfa1.priority from wf_approval_auth_map wfa1 where wfa1.approvalAuthority_id = ui.id and wfa1.active =1 and wfa1.fileEntry_id = " + fileEntryId + " )  As priority from user_info ui  "
						+ "  left join designationInfo d on d.id=ui.designationInfo_id "
						+ "  left join section s on s.id=ui.section_id order by checked desc";
		SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(AuthorityDTO.class));
		
		LOG.info("THE WORKFLOW CONFUGARATION QUERY ----------==="+sqlQuery);
        sqlQuery.addScalar("id", LongType.INSTANCE);
        sqlQuery.addScalar("name", StringType.INSTANCE);
        sqlQuery.addScalar("description", StringType.INSTANCE);
        sqlQuery.addScalar("priority", LongType.INSTANCE);
        sqlQuery.addScalar("checked", BooleanType.INSTANCE);
        sqlQuery.addScalar("sectionName", StringType.INSTANCE);
        sqlQuery.addScalar("designationName", StringType.INSTANCE);
        return sqlQuery.list();
    }

	  @SuppressWarnings("unchecked")
	    public List<WorkflowAuthorityMapDTO> getWorkflowMappedAuthorities(Long fileEntryId)
	    {
	    	String sql = "select  wfa.id as id,fe.id as fileEntryId ,wc.NAME as workFlowName,wfa.activestatusid as activestatusId ,wfa.approvalstatusid  as approvalstatusId ,wfa.approvalauthority_id as approvalauthorityId ,"
		    			+ " ui.first_name as authorityName,ui.last_name as description,wfa.PRIORITY as priority "
		    			+" from WF_APPROVAL_AUTH_MAP wfa join user_info ui  on  wfa.APPROVALAUTHORITY_ID = ui.ID "
		    			+" join  FileEntry fe on wc.id = wfa.WORKFLOWCONFIG_ID  where  wfa.active =1 and wfa.fileEntry_id ="+ fileEntryId ;
	        SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(WorkflowAuthorityMapDTO.class));
	        sqlQuery.addScalar("id", LongType.INSTANCE);
	        sqlQuery.addScalar("fileEntryId", LongType.INSTANCE);
	        sqlQuery.addScalar("workFlowName", StringType.INSTANCE);
	        
	        sqlQuery.addScalar("activestatusId", LongType.INSTANCE);
	        sqlQuery.addScalar("approvalstatusid", LongType.INSTANCE);
	        sqlQuery.addScalar("approvalauthorityId", LongType.INSTANCE);
	        
	        sqlQuery.addScalar("authorityName", StringType.INSTANCE);
	        sqlQuery.addScalar("description", StringType.INSTANCE);
	        sqlQuery.addScalar("priority", LongType.INSTANCE);
	        
	        return sqlQuery.list();
	    }

	 //to assign the report managers for different users
	@SuppressWarnings("unchecked")
	public List<EnployeeInfoDTO> getAllUserInfoDataForAssigningReportingManagers(String search, String value, String type, String limit) {

		String isearch="";
		String[] sectionAndRankSearch = null;
		if(search=="ALL" || search.equals("ALL"))
				{
			       isearch ="NOSEARCH";
				}
				else if(search.contains(",") && search!=null )
				{
					 sectionAndRankSearch = search.split(",");
					 isearch = sectionAndRankSearch[1];
					 if(isearch=="Section" || isearch.equals("Section" ))
					 {
					 isearch ="NOSEARCH";					 
					 }
					 else
					 {
					 isearch="SEARCHYES";
					 }
				}
		try {
			String sql = " ";
			if ((value == "ALL" || value.equals("ALL")) && value != null) {

				sql = "select distinct ui.id as id,ui.section_id as sectionId ,si.section_name as sectionName,ui.first_name as firstName,"
						+ " ui.last_name as lastName, CONCAT(ui.first_name,' ',ui.last_name) as employeeName, ui.employee_id as employeeId ,di.designationName as designationName"
						+ " from user_info ui  left join user_reporting_Manager  reja on ui.id=reja.employeeId_id"
						+ " left join designationinfo di on di.id=ui.designationInfo_id"
						+ " left join SECTION si on si.id=ui.section_id"
						+ " where (ui.id NOT IN ( select rejas.employeeId_id from  user_reporting_Manager  rejas where rejas.is_reporting_manager=1 and rejas.active=1))";
				if (search != null && search!= "ALL" && isearch!="NOSEARCH") {
					sql += "and  (ui.first_name like '%" + search + "%' or  ui.last_name like '%" + search
							+ "%' or ui.employee_id  like '%" + search + "%') order by ui.createdon desc";
				} else {
					sql += " order by ui.createdon desc ";
				}

			} else if (value != null && (type.equals("Section") || type == "Section")) {
				sql = "select distinct ui.id as id,ui.section_id as sectionId ,si.section_name as sectionName,ui.first_name as firstName,"
						+ " ui.last_name as lastName,CONCAT(ui.first_name,' ',ui.last_name) as employeeName, ui.employee_id as employeeId ,di.designationName as designationName"
						+ " from user_info ui  left join user_reporting_Manager  reja on ui.id=reja.employeeId_id"
						+ " left join designationinfo di on di.id=ui.designationInfo_id"
						+ " left join SECTION si on si.id=ui.section_id"
						+ " where  ui.section_id ='" + value+"' "
						+ " and  (ui.id NOT IN ( select rejas.employeeId_id from  user_reporting_Manager  rejas where rejas.is_reporting_manager=1 and rejas.active=1))";
				if (search != null && search != "" && isearch!="NOSEARCH" ) {
					sql += "and  (ui.first_name like '%" + search + "%' or  ui.last_name like '%" + search
							+ "%' or ui.employee_id  like '%" + search + "%') order by ui.createdon desc";
				} else {
					sql += " order by ui.createdon desc ";
				}
			}

			LOG.info("THE SQL FOR ALL DEFAULT ASSIGNED users and employee=to assig reporting assignin=====" + sql);
			SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql)
					.setResultTransformer(Transformers.aliasToBean(EnployeeInfoDTO.class));
			sqlQuery.addScalar("id", LongType.INSTANCE);
			sqlQuery.addScalar("firstName", StringType.INSTANCE);
			sqlQuery.addScalar("lastName", StringType.INSTANCE);
			sqlQuery.addScalar("employeeId", StringType.INSTANCE);
			sqlQuery.addScalar("designationName", StringType.INSTANCE);
			sqlQuery.addScalar("sectionId", LongType.INSTANCE);
			sqlQuery.addScalar("sectionName", StringType.INSTANCE);
			sqlQuery.addScalar("employeeName", StringType.INSTANCE);
			return sqlQuery.list();
		} catch (Exception e) {
			LOG.info(e.getCause(), e);
			return new ArrayList<EnployeeInfoDTO>();
		}

	
	}
	
	// to un assign the reportmanager user---@vivek
		@SuppressWarnings("unchecked")
		public List<EnployeeInfoDTO> getAllJobAssignedReportMangersUsers(String ifReportManagerSelected) {

			try {
				String sql =null; 
				if (ifReportManagerSelected != null && (ifReportManagerSelected.equals("ALLASS1") || ifReportManagerSelected == "ALLASS1")) {
					 sql = "select distinct ui.employee_id as employeeId , reja.id as id ,reja.reportingManager_id as reportingEmployeeId,ui.first_name as firstName,"
							+" ui.last_name as lastName, CONCAT(ui.first_name,' ',ui.last_name) as employeeName ,"
							+ " di.designationName as designationName ,reja.reporting_manger_remarks as assigningRemarks,si.section_name as sectionName"
							+ " from user_reporting_Manager reja left join"
							+ " user_info ui on ui.id=reja.employeeId_id"
							+ " left join SECTION si on si.id=ui.section_id"
							+ " left join designationinfo di on di.id=ui.designationInfo_id"
							+ " where reja.is_reporting_manager = 1 and reja.active=1 order by reja.createdon desc";
				} else {
					 sql = "select  distinct ui.employee_id as employeeId, reja.id as id ,reja.reportingManager_id as reportingEmployeeId ,ui.first_name as firstName,ui.last_name as lastName, di.designationName as designationName "
							+" , CONCAT(ui.first_name,' ',ui.last_name) as employeeName,"
							+ " di.designationName as designationName ,reja.reporting_manger_remarks as assigningRemarks ,si.section_name as sectionName"
							+ " from user_reporting_Manager reja left join"
							+ " user_info ui on ui.id=reja.employeeId_id"
							+ " left join SECTION si on si.id=ui.section_id"
							+ " left join designationinfo di on di.id=ui.designationInfo_id"
							+ " where reja.is_reporting_manager = 1 and reja.active=1 and reja.reportingManager_id='" + ifReportManagerSelected
							+ "' order by reja.createdon desc";
					LOG.info("THE SQL FOR reportingmanagers ASSIGNED REPORTS======" + sql);
				}
					SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql)
							.setResultTransformer(Transformers.aliasToBean(EnployeeInfoDTO.class));
					sqlQuery.addScalar("sectionName", StringType.INSTANCE);
					sqlQuery.addScalar("assigningRemarks", StringType.INSTANCE);
					sqlQuery.addScalar("firstName", StringType.INSTANCE);
					sqlQuery.addScalar("lastName", StringType.INSTANCE);
					sqlQuery.addScalar("designationName", StringType.INSTANCE);
					sqlQuery.addScalar("employeeId", StringType.INSTANCE);
					sqlQuery.addScalar("employeeName", StringType.INSTANCE);
					sqlQuery.addScalar("id", LongType.INSTANCE);
					sqlQuery.addScalar("reportingEmployeeId", LongType.INSTANCE);
					return sqlQuery.list();
			} catch (Exception e) {
				LOG.info(e.getCause(), e);
				return new ArrayList<EnployeeInfoDTO>();
			}

		}
		

		public String unAssignTheReportingManger(String eid ,long reportId) {
			try {

				String sql = " update  UserReportingManager reja set  reja.isReportingManager ='0' , reja.active= '0'  where reja.employeeId.id =(select ui.id  from UserInfo ui where ui.employeeId= '"
						+ eid + "')  and  reja.id='"+ reportId +"' ";
				LOG.info("THE SQL QUERY ISS========for unassignreport= " + sql);
				hibernateDao.executeSqlQuery(sql);
			} catch (Exception e) {
				LOG.info(e.getCause(), e);
				return "failure";
			}
			return "unassigned";
		}
		
	    public String updateScanedCopyRequestToFullyIssueInASsetRequest(long reqId)
	    {
	        try
	        {
	        	String sql = " update AssetRequest set status='10' where id="+reqId+" ";
	            hibernateDao.executeSqlQuery(sql);
	          return "success";
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return "failure";
	        }
	    }
	    
	    public String updateScanedCopyRequestStatusToFullyIssueInAssetIssue(long reqId)
	    {
	        try
	        {
	        	String sql = " update AssetIssue set status='10' where assetRequest.id="+reqId+" ";
	            hibernateDao.executeSqlQuery(sql);
	          return "success";
	        
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return "failure";
	        }
	    }

	public String releaseFileAuthentiction(String eid ,long reportId) {
		try {

			String sql = " update  FileCreationAuthentication reja set  reja.isFileCreationAuthentication ='0' , reja.active= '0'  where reja.employeeId.id =(select ui.id  from UserInfo ui where ui.employeeId= '"
					+ eid + "')  and  reja.id='"+ reportId +"' ";
			LOG.info("THE SQL QUERY ISS========for unassignreport= " + sql);
			hibernateDao.executeSqlQuery(sql);
		} catch (Exception e) {
			LOG.info(e.getCause(), e);
			return "failure";
		}
		return "unassigned";
	}
	 //to assign the report managers for different users
		@SuppressWarnings("unchecked")
		public List<EnployeeInfoDTO> getAllUserInfoDataForAssigningFileAuthentication(String search, String value, String type, String limit) {

			String isearch="";
			String[] sectionAndRankSearch = null;
			if(search=="ALL" || search.equals("ALL"))
					{
				       isearch ="NOSEARCH";
					}
					else if(search.contains(",") && search!=null )
					{
						 sectionAndRankSearch = search.split(",");
						 isearch = sectionAndRankSearch[1];
						 if(isearch=="Section" || isearch.equals("Section" ) || isearch=="Designation" || isearch.equals("Designation" ))
						 {
						 isearch ="NOSEARCH";					 
						 }
						 else
						 {
						 isearch="SEARCHYES";
						 }
					}
			try {
				String sql = " ";
				if ((value == "ALL" || value.equals("ALL")) && value != null) {
					sql = "select distinct ui.id as id,ui.section_id as sectionId ,si.section_name as sectionName,ui.first_name as firstName,"
							+ " ui.last_name as lastName, CONCAT(ui.first_name,' ',ui.last_name) as employeeName, ui.employee_id as employeeId ,di.designationName as designationName"
							+ " from user_info ui  left join file_creation_authentication  reja on ui.id=reja.employeeId_id"
							+ " left join designationinfo di on di.id=ui.designationInfo_id"
							+ " left join SECTION si on si.id=ui.section_id"
							+ " where (ui.id NOT IN ( select rejas.employeeId_id from  file_creation_authentication  rejas where rejas.is_filecreation_authentication=1 and rejas.active=1))";
					if (search != null && search!= "ALL" && isearch!="NOSEARCH") {
						sql += "and  (ui.first_name like '%" + search + "%' or  ui.last_name like '%" + search
								+ "%' or ui.employee_id  like '%" + search + "%') order by ui.createdon desc";
					} else {
						sql += " order by ui.createdon desc ";
					}

				} else if (value != null && (type.equals("Section") || type == "Section")) {
					sql = "select distinct ui.id as id,ui.section_id as sectionId ,si.section_name as sectionName,ui.first_name as firstName,"
							+ " ui.last_name as lastName,CONCAT(ui.first_name,' ',ui.last_name) as employeeName, ui.employee_id as employeeId ,di.designationName as designationName"
							+ " from user_info ui  left join file_creation_authentication  reja on ui.id=reja.employeeId_id"
							+ " left join designationinfo di on di.id=ui.designationInfo_id"
							+ " left join SECTION si on si.id=ui.section_id"
							+ " where  ui.section_id ='" + value+"' "
							+ " and  (ui.id NOT IN ( select rejas.employeeId_id from  file_creation_authentication  rejas where rejas.is_filecreation_authentication=1 and rejas.active=1))";
					if (search != null && search != "" && isearch!="NOSEARCH" ) {
						sql += "and  (ui.first_name like '%" + search + "%' or  ui.last_name like '%" + search
								+ "%' or ui.employee_id  like '%" + search + "%') order by ui.createdon desc";
					} else {
						sql += " order by ui.createdon desc ";
					}
				}
				else if (value != null && (type.equals("Designation") || type == "Designation")) {
					sql = "select distinct ui.id as id,ui.section_id as sectionId ,si.section_name as sectionName,ui.first_name as firstName,"
							+ " ui.last_name as lastName,CONCAT(ui.first_name,' ',ui.last_name) as employeeName, ui.employee_id as employeeId ,di.designationName as designationName"
							+ " from user_info ui  left join file_creation_authentication  reja on ui.id=reja.employeeId_id"
							+ " left join designationinfo di on di.id=ui.designationInfo_id"
							+ " left join SECTION si on si.id=ui.section_id"
							+ " where  ui.designationInfo_id ='" + value+"' "
							+ " and  (ui.id NOT IN ( select rejas.employeeId_id from  file_creation_authentication  rejas where rejas.is_filecreation_authentication=1 and rejas.active=1))";
					if (search != null && search != "" && isearch!="NOSEARCH" ) {
						sql += "and  (ui.first_name like '%" + search + "%' or  ui.last_name like '%" + search
								+ "%' or ui.employee_id  like '%" + search + "%') order by ui.createdon desc";
					} else {
						sql += " order by ui.createdon desc ";
					}
				}

				LOG.info("THE SQL FOR ALL DEFAULT ASSIGNED users and employee=to assig reporting assignin=====" + sql);
				SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql)
						.setResultTransformer(Transformers.aliasToBean(EnployeeInfoDTO.class));
				sqlQuery.addScalar("id", LongType.INSTANCE);
				sqlQuery.addScalar("firstName", StringType.INSTANCE);
				sqlQuery.addScalar("lastName", StringType.INSTANCE);
				sqlQuery.addScalar("employeeId", StringType.INSTANCE);
				sqlQuery.addScalar("designationName", StringType.INSTANCE);
				sqlQuery.addScalar("sectionId", LongType.INSTANCE);
				sqlQuery.addScalar("sectionName", StringType.INSTANCE);
				sqlQuery.addScalar("employeeName", StringType.INSTANCE);
				return sqlQuery.list();
			} catch (Exception e) {
				LOG.info(e.getCause(), e);
				return new ArrayList<EnployeeInfoDTO>();
			}

		
		}
		// to un assign the reportmanager user---@vivek
				@SuppressWarnings("unchecked")
				public List<EnployeeInfoDTO> getAllAssignedAuthenticationsUsers() {

					try {
						String sql =null; 
							 sql = "select distinct ui.employee_id as employeeId , reja.id as id ,ui.first_name as firstName,"
									+" ui.last_name as lastName, CONCAT(ui.first_name,' ',ui.last_name) as employeeName ,"
									+ " di.designationName as designationName ,reja.assign_remarks as assigningRemarks,si.section_name as sectionName"
									+ " from file_creation_authentication reja left join"
									+ " user_info ui on ui.id=reja.employeeId_id"
									+ " left join SECTION si on si.id=ui.section_id"
									+ " left join designationinfo di on di.id=ui.designationInfo_id"
									+ " where reja.is_filecreation_authentication = 1 and reja.active=1 order by reja.createdon desc";
						/*else {
							 sql = "select  distinct ui.employee_id as employeeId, reja.id as id ,reja.reportingManager_id as reportingEmployeeId ,ui.first_name as firstName,ui.last_name as lastName, di.designationName as designationName "
									+" , CONCAT(ui.first_name,' ',ui.last_name) as employeeName,"
									+ " di.designationName as designationName ,reja.reporting_manger_remarks as assigningRemarks ,si.section_name as sectionName"
									+ " from user_reporting_Manager reja left join"
									+ " user_info ui on ui.id=reja.employeeId_id"
									+ " left join SECTION si on si.id=ui.section_id"
									+ " left join designationinfo di on di.id=ui.designationInfo_id"
									+ " where reja.is_reporting_manager = 1 and reja.active=1 and reja.reportingManager_id='" + ifReportManagerSelected
									+ "' order by reja.createdon desc";
							LOG.info("THE SQL FOR reportingmanagers ASSIGNED REPORTS======" + sql);
						}*/
							 LOG.info("THE SQL FOR  file  creation authentication===" + sql);
							SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql)
									.setResultTransformer(Transformers.aliasToBean(EnployeeInfoDTO.class));
							sqlQuery.addScalar("sectionName", StringType.INSTANCE);
							sqlQuery.addScalar("assigningRemarks", StringType.INSTANCE);
							sqlQuery.addScalar("firstName", StringType.INSTANCE);
							sqlQuery.addScalar("lastName", StringType.INSTANCE);
							sqlQuery.addScalar("designationName", StringType.INSTANCE);
							sqlQuery.addScalar("employeeId", StringType.INSTANCE);
							sqlQuery.addScalar("employeeName", StringType.INSTANCE);
							sqlQuery.addScalar("id", LongType.INSTANCE);
							return sqlQuery.list();
					} catch (Exception e) {
						LOG.info(e.getCause(), e);
						return new ArrayList<EnployeeInfoDTO>();
					}

				}

				@SuppressWarnings("unchecked")
				public List<FileEntryDTO> getAllFilesAssignedToUser(Vector<String> sortInfo, String filterString,String start, String limit, String[] extraParams) 
				{
					try {
							String sql = " select fe.id as id,fe.file_barcode as fileBarcode,fe.section_id as section,fe.fileStatus_id as fileStatus from File_Entry fe,wf_approval_auth_map wf where fe.id=wf.fileEntry_id and fe.active=1 and wf.fileintime is not null ";
							LOG.info("1111111111----------->"+sql);
							SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql)
									.setResultTransformer(Transformers.aliasToBean(FileEntryDTO.class));
							sqlQuery.addScalar("id", LongType.INSTANCE);
							sqlQuery.addScalar("fileBarcode", StringType.INSTANCE);
							sqlQuery.addScalar("section", LongType.INSTANCE);
							sqlQuery.addScalar("fileStatus", LongType.INSTANCE);
							return sqlQuery.list();
					} catch (HibernateException e) {
						e.printStackTrace();
						return null;
					}
					
				}

			public FileEntryDTO getFileDetailsByBarcode(String searchText) {
				try {
					/*String sql = " select fe.id as id from File_Entry fe,wf_approval_auth_map wf where fe.id=wf.fileEntry_id and fe.active=1 and wf.approvalAuthority_id="+ThreadLocalData.get().getId()+" and fe.file_barcode='"+searchText+"' ";
					LOG.info("1111111111----------->"+sql);
					SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql)
							.setResultTransformer(Transformers.aliasToBean(FileEntryDTO.class));
					sqlQuery.addScalar("id", LongType.INSTANCE);
					
					return sqlQuery.list();*/
					return null;
			} catch (HibernateException e) {
				e.printStackTrace();
				return null;
			}
			}
				
		@SuppressWarnings("unchecked")
		public long getMaxFileNo()
		{
					
		try {	
			String sql=" SELECT IFNULL(MAX(CAST(SUBSTRING(t.file_no,LOCATE('/', t.file_no) + 1)AS UNSIGNED)),0) AS maxFileNo FROM File_Entry AS t";	
			SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(FileEntryDTO.class));
			sqlQuery.addScalar("maxFileNo", LongType.INSTANCE);
			
			 List< FileEntryDTO> dto = sqlQuery.list();
		       long maxId =  dto.get(0).getMaxFileNo() != 0 ? dto.get(0).getMaxFileNo() : 0;
		       if(maxId > 0) return maxId; else return 0;
		} catch (HibernateException e) {
			e.printStackTrace();
			return 0;
		}
		}

		@SuppressWarnings("unchecked")
		public List<FileTypeActionDTO> getActiveFileActionTypes() {
	        try
	        {
	        	String sql = " select id as id ,file_type_name as fileTypeName,file_type_value as fileTypeValue from file_creation_types  where active= 1 ";
	            SQLQuery sqlQuery = (SQLQuery) hibernateDao.getSession().createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(FileTypeActionDTO.class));
	            sqlQuery.addScalar("id", LongType.INSTANCE);
	            sqlQuery.addScalar("fileTypeName", StringType.INSTANCE);
	            sqlQuery.addScalar("fileTypeValue", StringType.INSTANCE);
	            return sqlQuery.list();
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return new ArrayList<FileTypeActionDTO>();
	        }
	    
		}
}
