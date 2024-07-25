package com.fts;

import java.util.Hashtable;

import javax.servlet.http.HttpSession;

public class Constants
{
    public static Hashtable<String, HttpSession> customerLoggedUsers = new Hashtable<String, HttpSession>();

    public static String USERSESSION = "USERSESSION";

    public static String DEFAULT_LANGUAGE = "ENG";

    public static String GOOGLE_API_KEY_ALERT = "AIzaSyBg0M9gw0lu-5rFf3VgRuPAwQhwVKV6po0";
    public static String GCM_ALERT_MESSAGE_KEY = "message";
    public static final String DEFAULT_PROFILE_IMG = "/images/default-profile.png";
    public static final Long VIJAYAWADA_GEOFENCE_ID = 1L;
    public static final double GEOFENCE_TOLERANCE_DISTANCE = 100.0; // In Metres
    public static final boolean ENFORCE_GEOFENCE_TOLERANCE = false;
    public static final String DEFAULT_IMG_EXT = "jpg";

    public static class errorCodes
    {
        public static final String SUCCESS_MSG = "Success";
        public static final int SUCCESS_CODE = 100;
        public static final String FAIL_MSG = "Fail";
        public static final int FAIL_CODE = 101;
        public static final String INVALID_INPUT_MSG = "Invalid input data";
        public static final int INVALID_INPUT_CODE = 102;
        public static final String NO_RECORD_FOUND_MSG = "No record found";
        public static final int NO_RECORD_FOUND_CODE = 103; 
        public static final int SERVER_ECHLN_ISSUE_CODE = 104;
        public static final int SERVER_CASESTS_ISSUE_CODE = 105; 
        public static final int SERVER_ISSUE_CODE = 106; 
    } 

    public static class Status
    {
        public static final int OPEN = 1;
        public static final int ASSIGNED = 2;
        public static final int CLOSED = 3;
    }

    public static class StatusText
    {
        public static final String OPEN = "Open";
        public static final String ASSIGNED = "Assigned";
        public static final String CLOSED = "Closed";
    }
    
    public static class MartialStatus
    {
        public static final String MARRIED = "Married";
        public static final String SINGLE = "Single";
    }

    public static class Gender
    {
        public static final String MALE = "Male";
        public static final String FEMALE = "Female";
    }
    
    public static class FileStatus
    {
    	public static final long DRAFT				=1;
    	public static final long SUBMITTED			=2;
    	public static final long FORWARD			=3;
    	public static final long TRANSFER			=4;
    	public static final long INTRANSIST			=5;
    	public static final long HOLD				=6;
    	public static final long REVERT				=7;
    	public static final long REJECT				=8;
    	public static final long CLOSE				=9;
    	public static final long PENDING			=10;
    	public static final long WAITINGAPPROVAL	=11;
    	public static final long APPROVED			=12;
    	public static final long APPROVE			=13;
    	public static final long CLOSED				=14;
    	public static final long UNDERPROCESS		=15;
    	public static final long CANCELLED			=16;
    	public static final long REJECTED			=17;
    }
    
    public static class AuthenticationStatus   {
     	public static final String ASSIGNA	= "ASSIGNED";
     	public static final String REMOVEA	= "REMOVED";
    	
    }
}
