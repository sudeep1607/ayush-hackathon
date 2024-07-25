	package com.fts.hibernate.models;
    import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.FetchType;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.OneToOne;
	import javax.persistence.Table;
	
	import org.hibernate.annotations.Cascade;
	import org.hibernate.annotations.CascadeType;

import com.fts.hibernate.models.CreateAndModify;

	@Entity
	@Table(name = "file_creation_authentication")
	public class FileCreationAuthentication extends CreateAndModify 
	{
	    public FileCreationAuthentication()
	    {
	        
	    }
	    public FileCreationAuthentication(String id)
	    {
	        this.id = Long.parseLong(id);
	    }
	    
	    public FileCreationAuthentication(Long id)
	    {
	        this.id = id;
	    }
		
	    @Id 
	    @GeneratedValue(strategy=GenerationType.AUTO)
	    protected Long id;

       @OneToOne(fetch = FetchType.LAZY)
	    @Cascade({ CascadeType.REPLICATE })
		private UserInfo employeeId;
      
	    @Column(name="assign_remarks")
	    private String assignRemarks;
        
        @Column(name="is_filecreation_authentication")
	    private int isFileCreationAuthentication;
        
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
		public UserInfo getEmployeeId() {
			return employeeId;
		}
		public void setEmployeeId(UserInfo employeeId) {
			this.employeeId = employeeId;
		}
		public String getAssignRemarks() {
			return assignRemarks;
		}
		public void setAssignRemarks(String assignRemarks) {
			this.assignRemarks = assignRemarks;
		}
		public int getIsFileCreationAuthentication() {
			return isFileCreationAuthentication;
		}
		public void setIsFileCreationAuthentication(int isFileCreationAuthentication) {
			this.isFileCreationAuthentication = isFileCreationAuthentication;
		}
		
		
}
