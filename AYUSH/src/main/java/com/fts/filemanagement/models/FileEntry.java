package com.fts.filemanagement.models;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import com.fts.hibernate.models.CreateAndModify;
import com.fts.hibernate.models.FileType;
import com.fts.hibernate.models.Section;
import com.fts.hibernate.models.UserInfo;


@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table (name="File_Entry")
public class FileEntry  extends CreateAndModify
{
	
	@Id 
    @GeneratedValue
    protected Long id;
	
	@Column(name="file_no")
	private String fileNo;
	
	@Column(name="file_created_date")
	private Date fileCreatedDate;
	
	@JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @Cascade({CascadeType.REPLICATE })
	private Section section;
	
	public UserInfo getFileInitiator() {
		return fileInitiator;
	}

	public void setFileInitiator(UserInfo fileInitiator) {
		this.fileInitiator = fileInitiator;
	}

	@JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @Cascade({CascadeType.REPLICATE })
	private UserInfo fileInitiator;
	
	@Column(name="file_name")
	private String fileName;
	
	@Column(name="file_subject")
	private String  fileSubject;
	
	@Column(name="file_Details")
	private String  fileDetails;
	
	@Column(name="file_Comments")
	private String  fileComments;
	
	@Column(name="file_priority")
	private String  filePriority;
	
	@Column(name="file_type")
	private String  fileType;
	
	@Column(name="file_barcode")
	private String  fileBarcode;
	
	@Column(name = "ATTACH_FILE_PATH")
	private String attachFilePath;
	
	@Column(name = "FILE_CODE")
	private String fileCode;
	
	@JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @Cascade({CascadeType.REPLICATE })
	private FileType fileStatus;
	
	@Transient
	private String authorityIds;
	
	public String getAuthorityIds() {
		return authorityIds;
	}

	public void setAuthorityIds(String authorityIds) {
		this.authorityIds = authorityIds;
	}

	public FileType getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(FileType fileStatus) {
		this.fileStatus = fileStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public Date getFileCreatedDate() {
		return fileCreatedDate;
	}

	public void setFileCreatedDate(Date fileCreatedDate) {
		this.fileCreatedDate = fileCreatedDate;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSubject() {
		return fileSubject;
	}

	public void setFileSubject(String fileSubject) {
		this.fileSubject = fileSubject;
	}

	public String getFileDetails() {
		return fileDetails;
	}

	public void setFileDetails(String fileDetails) {
		this.fileDetails = fileDetails;
	}

	public String getFileComments() {
		return fileComments;
	}

	public void setFileComments(String fileComments) {
		this.fileComments = fileComments;
	}

	public String getFilePriority() {
		return filePriority;
	}

	public void setFilePriority(String filePriority) {
		this.filePriority = filePriority;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileBarcode() {
		return fileBarcode;
	}

	public void setFileBarcode(String fileBarcode) {
		this.fileBarcode = fileBarcode;
	}

	public String getAttachFilePath() {
		return attachFilePath;
	}

	public void setAttachFilePath(String attachFilePath) {
		this.attachFilePath = attachFilePath;
	}

	public String getFileCode() {
		return fileCode;
	}

	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}
}