
INSERT INTO user_info (id, emailId, mobileNo, password, active, createdon, modifyon, remarks,date_of_birth, phone_no, first_name, gender, last_name, employee_id, createdBy_id, modifiedBy_id,default_roleid)
VALUES (1, NULL, 9999999999, 'PSy/1yg0RRbi7ztZ/i4wsmTwCJAPcKqN+IEDEkCtVzX7zkAffxCU/SFVBG+VSRHbsSmywToFSqS0W/Cuxo9N8w==',1, '28-09-15', NULL, NULL, NULL, '9999999999', 'Admin', 'Male', 'admin', 'admin', NULL, NULL,'1');

INSERT INTO user_info (Employee_id,username, Emailid, phoneno, password, createdon, updateon,dob, Gender)
VALUES (56789,test@gmail.com, test@gmail.com, 9999999999, 'admin','28-09-15', NULL, 16-06-98', 'Male');

create table  user_info (
Employee_id varchar not null primary key,
username varchar not null, 
Emailid varchar not null,
 phoneno varchar not null, 
password varchar not null,
 createdon varchar not null,
 updateon varchar null,
dob varchar not null,
 Gender varchar not null);
--VALUES (56789,test@gmail.com, test@gmail.com, 9999999999, 'admin','28-09-15', NULL, 16-06-98', 'Male');




INSERT INTO role (ID, DESCRIPTION, name, CREATEDON, ACTIVE, CREATEDBY_ID ) VALUES ('1' , 'Super Admin' , 'super admin' ,'01-10-18' ,1,1);
INSERT INTO role (ID, DESCRIPTION, name, CREATEDON, ACTIVE, CREATEDBY_ID ) VALUES ('2' , 'Store Admin' , 'store admin' ,'01-10-18' ,1,1);
INSERT INTO role (ID, DESCRIPTION, name, CREATEDON, ACTIVE, CREATEDBY_ID ) VALUES ('3' , 'User' , 'user' ,'01-10-18' ,1,1);

INSERT INTO user_role (USER_ID, ROLE_ID) VALUES ('1', '1');

INSERT INTO menu_info(id, menuText, priority, status)  VALUES ('1', 'User Mangement','1','1');
INSERT INTO menu_info(id, menuText, priority, status)  VALUES ('2', 'File Management', '2', '1');

INSERT INTO screen_info (id, method, module, name, priority, status, MENU_ID) VALUES (1, 'getUserGrid', 'USER', 'Employee Info', 1, 1, 1);
INSERT INTO role_screen (ROLE_ID, SCREEN_ID) VALUES (1, 1);
INSERT INTO screen_info (id, method, module, name, priority, status, MENU_ID) VALUES (2, 'getRoleGrid', 'ROLE', 'Role', 2, 1, 1);
INSERT INTO role_screen (ROLE_ID, SCREEN_ID) VALUES (1, 2);
INSERT INTO screen_info (id, method, module, name, priority, status, MENU_ID) VALUES (3, 'getDesignationGrid', 'DESIGNATION', 'Designation', 3, 1, 1);
INSERT INTO role_screen (ROLE_ID, SCREEN_ID) VALUES (1, 3);
INSERT INTO screen_info (id, method, module, name, priority, status, MENU_ID) VALUES (4, 'getSectionGrid', 'SECTION', 'Section', 4, 1, 1);
INSERT INTO role_screen (ROLE_ID, SCREEN_ID) VALUES (1, 4);
INSERT INTO screen_info (id, method, module, name, priority, status, MENU_ID) VALUES (5, 'getOrganizationGrid', 'ORGANIZATION', 'Organization', 5, 1, 1);
INSERT INTO role_screen (ROLE_ID, SCREEN_ID) VALUES (1, 5);
INSERT INTO screen_info (id, method, module, name, priority, status, MENU_ID) VALUES (6, 'getFileTypeGrid', 'FILETYPES', 'File Action  Type', 6, 1, 1);
INSERT INTO role_screen (ROLE_ID, SCREEN_ID) VALUES (1, 6);
INSERT INTO screen_info (id, method, module, name, priority, status, MENU_ID) VALUES (7, 'getAssigFileCreatingAuthrGrid', 'FILECREATINGAUTH', 'File Creation Authentication ', 7, 1, 1);
INSERT INTO role_screen (ROLE_ID, SCREEN_ID) VALUES (1, 7);

    

INSERT INTO `fts`.`screen_info` (`id`, `method`, `module`, `name`, `priority`, `status`, `menu_id`) VALUES ('8', 'getFileCreationGrid', 'FILECREATION', 'File Creation', '1', '1', '2');

INSERT INTO `fts`.`screen_info` (`id`, `method`, `module`, `name`, `priority`, `status`, `menu_id`) VALUES ('11', 'getWorkFlowGrid', 'WORKFLOW', 'WORKFLOW', '11', '1', '1');

INSERT INTO `fts`.`screen_info` (`id`, `method`, `module`, `name`, `priority`, `status`, `menu_id`) VALUES ('12', 'getFileApprovalGrid', 'FILEAPPROVAL', 'File Process', '3', '1', '2');


INSERT INTO `fts`.`screen_info` (`id`, `iconCls`, `method`, `module`, `name`, `priority`, `status`, `menu_id`) VALUES ('21', 'x-ia-fileprocess-icon', 'getFileScanGrid', 'SCANFILE', 'Scan File', '4', '1', '2');



INSERT INTO screen_info (id, method, module, name, priority, status, MENU_ID) VALUES (24, 'getSectoinDashboardGrid', 'SECTIONFILEDASHBOARD', 'DashBoard', 1, 1, 4);
INSERT INTO role_screen (ROLE_ID, SCREEN_ID) VALUES (1, 24);


