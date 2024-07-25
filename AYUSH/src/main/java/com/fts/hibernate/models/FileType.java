	package com.fts.hibernate.models;

	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.Id;
	import javax.persistence.Table;

import com.fts.hibernate.models.CreateAndModify;

	@Entity
	@Table(name = "file_action_Type")
	public class FileType  extends CreateAndModify
	{

	    public FileType()
	    {
	        
	    }
	    public FileType(String id)
	    {
	        this.id = Long.parseLong(id);
	    }
	    
	    public FileType(Long id)
	    {
	        this.id = id;
	    }
		
		@Id 
	    @GeneratedValue
	    protected Long id;
		
		@Column(name = "file_type_name")
		private String fileTypeName;
		
		@Column(name = "description")
		private String description;
	
		@Column(name = "map_filetype_actions")
		private String mapFileTypeActions;
		
		public String getMapFileTypeActions() {
			return mapFileTypeActions;
		}
		public void setMapFileTypeActions(String mapFileTypeActions) {
			this.mapFileTypeActions = mapFileTypeActions;
		}
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		public String getFileTypeName() {
			return fileTypeName;
		}
		public void setFileTypeName(String fileTypeName) {
			this.fileTypeName = fileTypeName;
		}
			
	}		

