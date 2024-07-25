package com.fts.hibernate.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.fts.hibernate.common.GenericManager;
import com.fts.hibernate.models.FileType;

@Repository
public class FileTypeManager extends GenericManager<FileType	, Long> {
	
	private static final Log LOG = LogFactory.getLog(FileTypeManager.class);
	public  FileTypeManager() {
		// TODO Auto-generated constructor stub
	 
		super(FileType.class);
	}
	
	 @SuppressWarnings("unchecked")
	    public List<FileType> filterDataForGrid(Vector<String> sortInfo, String filterString, String start, String limit, String... extraParams)
	    {
	        try
	        {
	            String sortColumn = "fileTypeName ";
	            String sortDirection = "asc";
	            if (sortInfo.size() > 0)
	            {
	                sortColumn = sortInfo.get(0);
	                sortDirection = sortInfo.get(1);
	            }
	            String sql = "from FileType where active=1 " + filterString + " order by " + sortColumn + " " + sortDirection;
	            return findByFilter(sql, start, limit);
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(),e);
	            return null;
	        }
	    }
	    
	    public long getRecordsCount(String filterString, String... extraParams)
	    {
	        String sql = "select count(*) from FileType where active=1 " + filterString;
	        return (Long) getCount(sql);
	    }

        @SuppressWarnings("unchecked")
        public List<FileType> getAllActiveItemCategories()
        {
            try
            {
                return find("from FileType where active = 1 order by fileTypeName asc ");
            }
            catch (Exception e)
            {
                LOG.info(e.getCause(), e);
                return new ArrayList<FileType>();
            }
        }
        
        @SuppressWarnings("unchecked")
        public List<FileType> getAllActiveFileTypes()
        {
            try
            {
                return find("from FileType where active = 1 order by fileTypeName asc ");
            }
            catch (Exception e)
            {
                LOG.info(e.getCause(), e);
                return new ArrayList<FileType>();
            }
        }
        
        
        @SuppressWarnings({ "unchecked", "unused" })
        public List<FileType> getAllActiveFileTypesForOpenTypeFiles()
        {
            try
            {
               // return find("from FileType where active = 1 and id in(3,4,6,8,9) order by fileTypeName asc ");
            	String fileType = "OPENGEN";
            	return find("from FileType where active = 1 and  mapFileTypeActions like '%OPENGEN%' order by fileTypeName asc ");
            	
            }
            catch (Exception e)
            {
                LOG.info(e.getCause(), e);
                return new ArrayList<FileType>();
            }
        }
        
        @SuppressWarnings("unchecked")
        public List<FileType> getAllActiveFileTypesForSecureApproveFiles()
        {
            try
            {
                return find("from FileType where active = 1 and id in(7,13) order by fileTypeName asc ");
            }
            catch (Exception e)
            {
                LOG.info(e.getCause(), e);
                return new ArrayList<FileType>();
            }
        }
        
        @SuppressWarnings("unchecked")
        public List<FileType> getAllActiveFileTypesForOpenApproveFiles()
        {
            try
            {
                return find("from FileType where active = 1 and id in(3,7,8,13) order by fileTypeName asc ");
            }
            catch (Exception e)
            {
                LOG.info(e.getCause(), e);
                return new ArrayList<FileType>();
            }
        }

		@SuppressWarnings("unchecked")
		public List<FileType> getAllActiveFileTypesByUserMapping(String actionTypes) {
			  try
	            {
	                
				  return find(" from FileType where active = 1 and id in(3,7,8,13) order by fileTypeName asc ");
	            }
	            catch (Exception e)
	            {
	                LOG.info(e.getCause(), e);
	                return new ArrayList<FileType>();
	            }
		}

		@SuppressWarnings("unchecked")
		public FileType getFileTypeByDescription(String desc) {
			try
            {
                String sql="  from FileType where active = 1 and description='"+desc+"' order by fileTypeName asc ";
                List<FileType> fileTypeList =  find(sql);
                return fileTypeList!=null ? (fileTypeList.size()>0?(fileTypeList.get(0)):null):null;
            }
            catch (Exception e)
            {
                LOG.info(e.getCause(), e);
                return null;
            }
		}

		@SuppressWarnings("unchecked")
		public List<FileType> getAllActiveFileTypesById(String ids) {
			try
            {
                String sql=" from FileType where active = 1 and id in("+ids+") order by fileTypeName asc ";
                LOG.info("HHHHHHHHHH------------>"+sql);
                return find(sql);
            }
            catch (Exception e)
            {
                LOG.info(e.getCause(), e);
                return new ArrayList<FileType>();
            }
		}

}
