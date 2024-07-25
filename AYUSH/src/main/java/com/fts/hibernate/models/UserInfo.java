package com.fts.hibernate.models;

import java.sql.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import com.fts.hibernate.models.CreateAndModify;



@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonRootName("records")
@Table(name = "user_info")
public class UserInfo extends CreateAndModify
{
    /**
     * 
     */
    @SuppressWarnings("unused")
	private static final long serialVersionUID = -9076373934232613249L;
    public UserInfo()
    {
        
    }
    public UserInfo(String id)
    {
        this.id = Long.parseLong(id);
    }
    
    public UserInfo(Long id)
    {
        this.id = id;
    }
    
    
    @Id 
	@GeneratedValue(generator = "userSeq")
	@SequenceGenerator(name = "userSeq", sequenceName = "User_SEQ", allocationSize = 1,initialValue =2)
	protected Long id;
	public Long getId()
	{
	    return id;
	}

    @Column(length = 255, nullable = false,name ="first_name")
    private String firstName;
    @Column(name ="last_name")
    private String lastName;
    private String gender;
    @Column(name ="date_of_birth")
    private Date dateOfBirth;
    private String emailId;
    private String mobileNo;
    @Column
    private String password;
    @Column(name ="phone_no")
    private String phoneNo;
    @Column(name ="employee_id",nullable = false)
    private String employeeId;
    
    @OneToOne(fetch = FetchType.EAGER)
    @Cascade({CascadeType.REPLICATE })
	private DesignationInfo  designationInfo;
    
    @OneToOne
    @Cascade({CascadeType.REPLICATE })
    @Fetch(FetchMode.SELECT)
	private Section section;
    
    @Column(name ="default_RoleId")
    private Long defaultRoleId;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ROLE", joinColumns =
    {
        @JoinColumn(name = "USER_ID")
    }, inverseJoinColumns =
    {
        @JoinColumn(name = "ROLE_ID")
    })
    private List<Role> roles;
    
   /* @Column(columnDefinition = "int default 0")
	private int isAuthoriseeUser;*/
    
   /* @Column(name ="authoriser_id")
	private Long  authoriserId;*/
    
    @Column(name ="valid_from")
    private Date validFrom;
    
    @Column(name ="valid_to")
    private Date validTo;
    
    @Transient
    private String roleIds;
    
    @Transient
    private Long designationId;
    
    @Column(name ="is_create_file" ,columnDefinition = "int default 0")
    private int isCreateFile;
    
	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}

/*	public int getIsAuthoriseeUser() {
		return isAuthoriseeUser;
	}

	public void setIsAuthoriseeUser(int isAuthoriseeUser) {
		this.isAuthoriseeUser = isAuthoriseeUser;
	}

	public Long getAuthoriserId() {
		return authoriserId;
	}

	public void setAuthoriserId(Long authoriserId) {
		this.authoriserId = authoriserId;
	}*/

	public Long getDefaultRoleId() {
		return defaultRoleId;
	}

	public void setDefaultRoleId(Long defaultRoleId) {
		this.defaultRoleId = defaultRoleId;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public DesignationInfo getDesignationInfo() {
		return designationInfo;
	}

	public void setDesignationInfo(DesignationInfo designationInfo) {
		this.designationInfo = designationInfo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmailId()
    {
        return emailId;
    }

    public void setEmailId(String emailId)
    {
        this.emailId = emailId;
    }

    public List<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

    public String getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(String roleIds)
    {
        this.roleIds = roleIds;
    }

    public String getMobileNo()
    {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo)
    {
        this.mobileNo = mobileNo;
    }

	public void setId(Long id) {
		this.id = id;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}
	public int getIsCreateFile() {
		return isCreateFile;
	}
	public void setIsCreateFile(int isCreateFile) {
		this.isCreateFile = isCreateFile;
	}
	
}