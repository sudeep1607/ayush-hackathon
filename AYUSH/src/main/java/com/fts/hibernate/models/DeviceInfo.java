package com.fts.hibernate.models;

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


@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "DEVICEINFO")
public class DeviceInfo extends CreateAndModify 
{
    
   
	@Id 
    @GeneratedValue
    protected Long id;
	
	 public DeviceInfo()
	    {
	        
	    }
	    public DeviceInfo(String id)
	    {
	        this.id = Long.parseLong(id);
	    }
	    
	    public DeviceInfo(Long id)
	    {
	        this.id = id;
	    }
	
	@Column(name = "device_name")
	private String deviceName;
	
	@Column(name = "serialno")
	private String serialno;
	
	@JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @Cascade({ CascadeType.REPLICATE })
	private UserInfo custodianName;
	
	@Transient
	private  Long custodianId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public String getSerialno() {
		return serialno;
	}
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	public UserInfo getCustodianName() {
		return custodianName;
	}
	public void setCustodianName(UserInfo custodianName) {
		this.custodianName = custodianName;
	}
	public Long getCustodianId() {
		return custodianId;
	}
	public void setCustodianId(Long custodianId) {
		this.custodianId = custodianId;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	

	
	
}
