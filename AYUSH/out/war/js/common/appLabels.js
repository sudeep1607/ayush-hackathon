var appLbls = new Ext.util.HashMap();

/**   Global Labels   */

appLbls.add('AMS.GLOBAL.CONFIRM','Confirm');
appLbls.add('AMS.GLOBAL.SUCCESS','Success');
appLbls.add('AMS.GLOBAL.FAILURE','Failure');
appLbls.add('AMS.GLOBAL.MESSAGE','Message');
appLbls.add('AMS.GLOBAL.REGRET'	,'Regret');
appLbls.add('AMS.GLOBAL.INFO'	, 'Info');
appLbls.add('AMS.GLOBAL.SELECT'	,'Please select a record.');
appLbls.add('AMS.GLOBAL.DOB'	,'Date Of Birth Cannot be a future Date');
appLbls.add('AMS.GLOBAL.DOJ'	,'Date Of Joining Cannot be a future Date');
appLbls.add('AMS.GLOBAL.RELOGIN','Re-Login');
appLbls.add('AMS.GLOBAL.PWDCHNG','Password Changed successfully. Please Re-Login with new password.');
appLbls.add('AMS.GLOBAL.CONFIRM','Confirm');
appLbls.add('AMS.GLOBAL.FAILURE.MSG','Sorry! Unable to Process your request');
appLbls.add('AMS.GLOBAL.ATTACHMENTS.TITLE','Attachments');
appLbls.add('AMS.GLOBAL.WARNING', 'Warning');
appLbls.add('AMS.GLOBAL.ERROR'	, 'Error');
appLbls.add('AMS.GLOBAL.EXPORT'	, 'No records available');
appLbls.add('AMS.GLOBAL.RESET.PASSWORD', 'Do You Want To Reset Password ?');
appLbls.add('AMS.RESET.PASSWORD.SECCESS', 'Password Changed Successfully!');
appLbls.add('AMS.RESET.PASSWORD.FAILURE', 'Unable To Reset Password');
appLbls.add('AMS.GLOBAL.FILE_UPLOAD.FAILURE', 'File extension must be either of these xls,xlsx,xlsm');
/*** Common Form Dirty Close ***/

appLbls.add('FORM.DIRTY.CLOSE', 'Unsaved Changes Detected. You will loss the changes if you close the window.<br>Do you want to continue ?');
appLbls.add('FORM.UNDIRTY.SAVE', 'Nothing has been changed to Save.');

/***  Module Specific Labels ***/
  


/***  project info Module  Starts ***/

appLbls.add('AMS.PROJECT.GRID.TITLE'		, 'Project Details');

appLbls.add('AMS.PROJECT.SAVE.SUCCESS'		, 'Project Info saved successfully.');
appLbls.add('AMS.PROJECT.SAVE.FAILURE'		, 'Unable to save Project Info.');
appLbls.add('AMS.PROJECT.WINDOW.TITLE'		, 'Project Information');
appLbls.add('AMS.PROJECT.DELETE.SUCCESS'	, 'Project Info deleted successfully.');
appLbls.add('AMS.PROJECT.DELETE.FAILURE'	, 'Unable to delete Project Info.');

/***  project Module  Ends ***/



/***  Designation info Module  Starts ***/

appLbls.add('AMS.DESIGNATION.GRID.TITLE'		, 'Designation Details');
appLbls.add('AMS.DESIGNATION.SAVE.SUCCESS'		, 'Designation Info saved successfully.');
appLbls.add('AMS.DESIGNATION.SAVE.FAILURE'		, 'Unable to save Designation Info.');
appLbls.add('AMS.DESIGNATION.WINDOW.TITLE'		, 'Designation Information');
appLbls.add('AMS.DESIGNATION.DELETE.SUCCESS'	, 'Designation Info deleted successfully.');
appLbls.add('AMS.DESIGNATION.DELETE.FAILURE'	, 'Unable to delete Designation Info.');

/***  Designation Module  Ends ***/


/***  UserInfo Module  Starts ***/

appLbls.add('AMS.UI.GRID.TITLE'			, 'Employee Details');
appLbls.add('AMS.UI.SAVE.SUCCESS'		, 'Employee Info saved successfully.');
appLbls.add('AMS.UI.SAVE.FAILURE'		, 'Unable to save  Employee Info.');
appLbls.add('AMS.UI.WINDOW.TITLE'		, 'Employee Information');
appLbls.add('AMS.UI.DELETE.SUCCESS'		, 'Employee Info deleted successfully.');
appLbls.add('AMS.UI.DELETE.FAILURE'		, 'Unable to delete  Employee Info.');
appLbls.add('AMS.UI.ROLE.SELECT'		, 'Please Select at Least One Role to Create/Update an Employee.');


/***  UserInfo Module  end ***/

/***  Role info Module  end ***/

appLbls.add('AMS.RI.GRID.TITLE'			, 'Role Details');
appLbls.add('AMS.RI.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.RI.WINDOW.TITLE'		, 'Role Information');
appLbls.add('AMS.RI.SAVE.SUCCESS'		, 'Role Info saved successfully.');
appLbls.add('AMS.RI.SAVE.FAILURE'		, 'Unable to save  role Info.');
appLbls.add('AMS.RI.SELECTROLE'			, 'Please Select at Least One Screen to Create/Update an Role.');

/***  Role info Module  end ***/

/***  Section info Module  end ***/

appLbls.add('AMS.SI.GRID.TITLE'			, 'Section Details');
appLbls.add('AMS.SI.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.SI.WINDOW.TITLE'		, 'Section');
appLbls.add('AMS.SI.SAVE.SUCCESS'		, 'Section Info saved successfully.');
appLbls.add('AMS.SI.SAVE.FAILURE'		, 'Unable to save Section Info.');
appLbls.add('AMS.SI.SELECTSECTION'		, 'Please Select at Least One Screen to Create/Update a Section.');
appLbls.add('AMS.SI.SECTIONEXISTS'		, 'The section is already registered under this building');

/***  Section info Module  end ***/

/***  Building Module  end ***/

appLbls.add('AMS.BI.GRID.TITLE'			, 'Building Details');
appLbls.add('AMS.BI.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.BI.WINDOW.TITLE'		, 'Building');
appLbls.add('AMS.BI.SAVE.SUCCESS'		, 'Building Info saved successfully.');
appLbls.add('AMS.BI.SAVE.FAILURE'		, 'Unable to save Building Info.');
appLbls.add('AMS.BI.SELECTSECTION'		, 'Please Select at Least One Screen to Create/Update a Building.');

/***  Building info Module  end ***/

/***  Store info Module  end ***/

appLbls.add('AMS.StoreInfo.GRID.TITLE'			, 'Store Details');
appLbls.add('AMS.StoreInfo.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.StoreInfo.WINDOW.TITLE'		, 'Store');
appLbls.add('AMS.StoreInfo.SAVE.SUCCESS'		, 'Store Info saved successfully.');
appLbls.add('AMS.StoreInfo.SAVE.FAILURE'		, 'Unable to save Store Info.');
appLbls.add('AMS.StoreInfo.SELECTSECTION'		, 'Please Select at Least One Screen to Create/Update a Store.');

/***  Store info Module  end ***/

/***  UOM info Module  end ***/

appLbls.add('AMS.UOM.GRID.TITLE'		, 'UOM Details');
appLbls.add('AMS.UOM.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.UOM.WINDOW.TITLE'		, 'Unit of Measurement (UOM)');
appLbls.add('AMS.UOM.SAVE.SUCCESS'		, 'UOM Info saved successfully.');
appLbls.add('AMS.UOM.SAVE.FAILURE'		, 'Unable to save UOM Info.');
appLbls.add('AMS.UOM.SELECTSECTION'		, 'Please Select at Least One Screen to Create/Update a UOM.');

/***  UOM info Module  end ***/

/***  Item Category Module  end ***/

appLbls.add('AMS.ICI.GRID.TITLE'		, 'Item Category Details');
appLbls.add('AMS.ICI.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.ICI.WINDOW.TITLE'		, 'Item Category');
appLbls.add('AMS.ICI.SAVE.SUCCESS'		, 'Item Category Info saved successfully.');
appLbls.add('AMS.ICI.SAVE.FAILURE'		, 'Unable to save Item Category Info.');
appLbls.add('AMS.ICI.SELECTSECTION'		, 'Please Select at Least One Screen to Create/Update a Item Category.');

/***  Item Category Module  end ***/

/***  Item Category Module  end ***/

appLbls.add('AMS.ITEMS.GRID.TITLE'			, 'Item Details');
appLbls.add('AMS.ITEMSMASTER.WINDOW.TITLE'	, 'Item Creation Form');
/***  Item Category Module  end ***/

/***  Items  Module  end ***/

appLbls.add('AMS.ITEMS.GRID.TITLE'			, 'Item Details');
appLbls.add('AMS.ITEMS.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.ITEMS.WINDOW.TITLE'		, 'Item ');
appLbls.add('AMS.ITEMS.SAVE.SUCCESS'		, 'Item Info saved successfully.');
appLbls.add('AMS.ITEMS.SAVE.FAILURE'		, 'Unable to save Item Info.');
appLbls.add('AMS.ITEMS.SELECTSECTION'		, 'Please Select at Least One Screen to Create/Update a Item .');

/***  Items Module  end ***/

/***  SupplierName info Module  end ***/

appLbls.add('AMS.SUPNAME.GRID.TITLE'		, 'Supplier Details');
appLbls.add('AMS.SUPNAME.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.SUPNAME.WINDOW.TITLE'		, 'Supplier Form');
appLbls.add('AMS.SUPNAME.SAVE.SUCCESS'		, 'Supplier Info saved successfully.');
appLbls.add('AMS.SUPNAME.SAVE.FAILURE'		, 'Unable to save Supplier Info.');

/***  SupplierName info Module  end ***/
/***  SupplierName info Module  end ***/

appLbls.add('AMS.SUPORDER.GRID.TITLE'		, 'Supply Order Details');
appLbls.add('AMS.SUPORDER.SCREEN.GRID.TITLE', 'Screen Information');
appLbls.add('AMS.SUPORDER.WINDOW.TITLE'		, 'Supply Order Form');
appLbls.add('AMS.SUPORDER.SAVE.SUCCESS'		, 'Supply Order Info saved successfully.');
appLbls.add('AMS.SUPORDER.SAVE.FAILURE'		, 'Unable to save Supply Order Info.');


/***  SupplierName info Module  end ***/

/***  IpRegistration info Module  end ***/

appLbls.add('AMS.IPREGISTRATION.GRID.TITLE'			, 'Reader IP Details');
appLbls.add('AMS.IPREGISTRATION.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.IPREGISTRATION.WINDOW.TITLE'		, 'Reader IP Mapping');
appLbls.add('AMS.IPREGISTRATION.SAVE.SUCCESS'		, 'IP Info saved successfully.');
appLbls.add('AMS.IPREGISTRATION.SAVE.FAILURE'		, 'Unable to save Ip Registration Info.');
appLbls.add('AMS.IPREGISTRATION.PCREADERVALIDATE'	, 'PC IP and Reader IP Cannot be  Equal');
appLbls.add('AMS.IPREGISTRATION.SELECTSECTION'		, 'Please Select at Least One Screen to Create/Update a IP.');

/***  IpRegistration info Module  end ***/



/***  Asset Entry Module  end ***/

appLbls.add('AMS.AE.GRID.TITLE'			, 'Asset Registration Details');
appLbls.add('AMS.AE.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.AE.WINDOW.TITLE'		, 'Asset Registration');
appLbls.add('AMS.AE.SAVE.SUCCESS'		, 'Asset Info saved successfully.');
appLbls.add('AMS.AE.SAVE.FAILURE'		, 'Unable to save Asset registration Info.');
appLbls.add('AMS.AE.BARCODE.FAILURE'	, 'Please select barcode category..');
appLbls.add('AMS.AE.SELECTSECTION'		, 'Please Enter atleast One item to continue.');

/***  Asset Entry Module  end ***/

/***  Asset Issue Module  end ***/

appLbls.add('AMS.AI.GRID.TITLE'			, 'Asset Issue Details');
appLbls.add('AMS.AI.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.AI.WINDOW.TITLE'		, 'Asset Issue');
appLbls.add('AMS.AI.UPLOAD.WINDOW.TITLE', 'Upload Form');
appLbls.add('AMS.AI.SAVE.SUCCESS'		, 'Asset Issue saved successfully.');
appLbls.add('AMS.AI.SAVE.FAILURE'		, 'Unable to save Asset Issue Info.');
appLbls.add('AMS.AI.SELECTITEMS'		, 'Please select atleast one item to issue.');
appLbls.add('AMS.AI.SELECTONEITEM'		, 'Please select the record and then click..');
appLbls.add('AMS.AI.SAVE.CONFIRM'		, 'Please confirm the details, Once saved cannot be modified..');
appLbls.add('AMS.AI.ITEM.USED'			, 'Item already in use..');
appLbls.add('AMS.AI.TRANSFERITEMS'		, 'Issue receipt will not be generated for Transfer/Visitor transactions..');

/***  Asset Issue Module  end ***/

/***  Asset Request Module  end ***/
appLbls.add('AMS.ARI.WINDOW.TITLE'		, 'Asset Issue Online');
appLbls.add('AMS.ARI.SELECT.ROW',		'Please select one record to proceed');
appLbls.add('AMS.ARI.DELETE.SUCCESS', 'Requested Item Deleted successfully');
appLbls.add('AMS.ARI.DELETE.CONFIRM',	'Are you sure to remove this record?');
appLbls.add('AMS.ARI.ISSUE.CONFIRM',	'Are you sure to issue the items?');
appLbls.add('AMS.ARI.REQUEST.CONFIRM',	'Are you sure to Save?');
appLbls.add('AMS.ARI.CANCEL.CONFIRM',	'Are you sure to Cancel?');
appLbls.add('AMS.ARI.SUBMIT.CONFIRM',	'Are you sure to Submit?');
appLbls.add('AMS.ARI.ISSUE.SUCCESS', 'Items Issued Successfully');
appLbls.add('AMS.ARI.ISSUE.FAILURE', 'Items Issue Failed. Pleas check the details..');
appLbls.add('AMS.ARE.GRID.TITLE'			, 'Asset Request Details');
appLbls.add('AMS.ARE.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.ARE.WINDOW.TITLE'		, 'Asset Request');
appLbls.add('AMS.ARE.UPLOAD.WINDOW.TITLE', 'Upload Form');
appLbls.add('AMS.ARE.SAVE.SUCCESS'		, 'Asset Request saved successfully.');
appLbls.add('AMS.ARE.SAVE.FAILURE'		, 'Unable to save Asset Request Info.');
appLbls.add('AMS.ARE.SELECTITEMS'		, 'Please select atleast one item to request.');
appLbls.add('AMS.ARE.SELECTONEITEM'		, 'Please select the record and then click..');
appLbls.add('AMS.ARE.SAVE.CONFIRM'		, 'Please confirm the details, Once saved cannot be modified..');
appLbls.add('AMS.ARE.ITEM.USED'			, 'Item already is use..');
appLbls.add('AMS.ARE.REQ.SUBMIT.SUCCESS'			, 'Request Submitted Successfully.');
appLbls.add('AMS.ARE.REQ.SUBMIT.FAIL'			, 'Submitting Request Failed.');
appLbls.add('AMS.REQUEST.SELECT'			, 'Please select one record');

appLbls.add('AMS.ARI.GRID.TITLE'			, 'Asset Issue Online Form');
/***  Asset Request Module  end ***/

appLbls.add('AMS.PR.GRID.TITLE'			, 'Purchase Request Details');
appLbls.add('AMS.PR.WINDOW.TITLE'		, 'Purchase Order Form');
appLbls.add('AMS.PR.SAVE.SUCCESS'		, 'Purchase Request saved successfully.');
appLbls.add('AMS.PR.SAVE.FAILURE'		, 'Purchase Request Save Failed...');

/***  WORK FLOW START ***/

appLbls.add('AMS.WF.GRID.TITLE'					, 'Workflow Config');
appLbls.add('AMS.WF.SAVE.SUCCESS'				, 'Workflow saved successfully.');
appLbls.add('AMS.WF.SAVE.FAILURE'				, 'Unable to save  Workflow.');
appLbls.add('AMS.WF.WINDOW.TITLE'				, 'Workflow Information');
appLbls.add('AMS.WF.DELETE.SUCCESS'				, 'Workflow deleted successfully.');
appLbls.add('AMS.WF.DELETE.FAILURE'				, 'Unable to delete  Workflow.');
appLbls.add('AMS.WF.AUTHORITY.SELECT'			, 'Please Select at Least One Authority to Create/Update an Workflow.');
appLbls.add('AMS.WF.AUTHORITY.SELECTPRIORITY'	, 'Please Select the priority.');
appLbls.add('AMS.WI.AUTHORITYGRID.TITLE'		, 'Approval Authorities');
appLbls.add('AMS.WI.STATUSGRID.TITLE'			, 'Status');
appLbls.add('AMS.WF.AUTHORITY.DUPLICATEPRIORITY'			, 'Duplicate priority');

appLbls.add('AMS.REQUEST_APPROVE.GRID.TITTLE', 'Request Approve Details');
appLbls.add('AMS.REQUEST.WINDOW.TITLE',	'Request Details');
appLbls.add('AMS.REQUEST.SELECT'       		, 'Please select the record to proceed.');
appLbls.add('AMS.REQUEST.STATUS.APPROVEDCODE', 'APPROVED');
appLbls.add('AMS.REQUEST.APPROVE.SUCCESS', 'Approved successfully.');


/***  WORK FLOW END ***/

/***  Asset Return Module  end ***/

appLbls.add('AMS.AR.GRID.TITLE'			, 'Asset Return Details');
appLbls.add('AMS.AR.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.AR.WINDOW.TITLE'		, 'Asset Return');
appLbls.add('AMS.AR.SAVE.SUCCESS'		, 'Asset Return saved successfully.');
appLbls.add('AMS.AR.SAVE.FAILURE'		, 'Unable to save Asset Return Info.');
appLbls.add('AMS.AR.SELECTITEMS'		, 'Please Select atleast One item to Return.');
appLbls.add('AMS.AR.SAVE.CONFIRM'		, 'Please confirm the details, Once saved cannot be modified..');

/***  Asset Return Module  end ***/

/***  Asset Transfer Module  end ***/

appLbls.add('AMS.AT.GRID.TITLE'			, 'Asset Transfer Details');
appLbls.add('AMS.AT.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.AT.WINDOW.TITLE'		, 'Asset Transfer');
appLbls.add('AMS.AT.SAVE.SUCCESS'		, 'Asset Transfer saved successfully.');
appLbls.add('AMS.AT.SAVE.FAILURE'		, 'Unable to save Asset Transfer Info.');
appLbls.add('AMS.AT.SELECTSECTION'		, 'Please Select at Least One Screen to Create/Update a Asset Transfer.');
appLbls.add('AMS.AT.SAVE.CONFIRM'		, 'Please confirm the details, Once saved cannot be modified..');

/***  Asset Transfer Module  end ***/

/***  Asset Repair Module  end ***/

appLbls.add('AMS.REPAIR.GRID.TITLE'			, 'Asset Repair Details');
appLbls.add('AMS.REPAIR.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.REPAIR.WINDOW.TITLE'		, 'Asset Repair');
appLbls.add('AMS.REPAIR.SAVE.SUCCESS'		, 'Asset Repair saved successfully.');
appLbls.add('AMS.REPAIR.SAVE.FAILURE'		, 'Unable to save Asset Repair Info.');
appLbls.add('AMS.REPAIR.SELECTSECTION'		, 'Please Select at Least One Screen to Create/Update a Asset Repair.');
appLbls.add('AMS.REPAIR.SAVE.CONFIRM'		, 'Please confirm the details, Once saved cannot be modified..');
appLbls.add('AMS.REPAIR.SELECTITEMS'		, 'Please select the record and then click..');
appLbls.add('AMS.REPAIR.SELECTEDITEMS2'		, 'Please Select atleast One item to Repair.');

/***  Asset Repair Module  end ***/

/***  CONDEMNATION Module  end ***/

appLbls.add('AMS.CONDEMNATION.GRID.TITLE'			, 'Condemnation Details');
appLbls.add('AMS.CONDEMNATION.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.CONDEMNATION.WINDOW.TITLE'			, 'Condemnation');
appLbls.add('AMS.CONDEMNATION.SAVE.SUCCESS'			, 'Condemnation saved successfully.');
appLbls.add('AMS.CONDEMNATION.SAVE.FAILURE'			, 'Unable to save Condemnation Info.');
appLbls.add('AMS.CONDEMNATION.SELECTSECTION'		, 'Please Select at Least One Screen to Create/Update a Condemnation.');
appLbls.add('AMS.CONDEMNATION.SAVE.CONFIRM'		    , 'Please confirm the details, Once saved cannot be modified..');

/***  CONDEMNATION Module  end ***/

/***  Main Inventory Module  end ***/

appLbls.add('AMS.MI.GRID.TITLE'			, 'Main Inventory Details');
appLbls.add('AMS.MI.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.MI.WINDOW.TITLE'		, 'Main Inventory');
appLbls.add('AMS.MI.SAVE.SUCCESS'		, 'Main Inventory saved successfully.');
appLbls.add('AMS.MI.SAVE.FAILURE'		, 'Unable to save  Main Inventory Info.');
appLbls.add('AMS.MI.SELECTSECTION'		, 'Please Select at Least One Screen to Create/Update a  Main Inventory.');

/***  Main Inventory Module  end ***/

/***  User Inventory Module  end ***/

appLbls.add('AMS.UINV.GRID.TITLE'			, 'User Inventory Details');
appLbls.add('AMS.UINV.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.UINV.WINDOW.TITLE'			, 'User Inventory');
appLbls.add('AMS.UINV.SAVE.SUCCESS'			, 'User Inventory saved successfully.');
appLbls.add('AMS.UINV.SAVE.FAILURE'			, 'Unable to save  User Inventory Info.');
appLbls.add('AMS.UINV.SELECTSECTION'		, 'Please Select at Least One Screen to Create/Update a  User Inventory.');

/***  User Inventory Module  end ***/

/***  Role info Module  end ***/

/***  Leave info Module  start ***/
appLbls.add('AMS.LEAVEINFO.GRID.TITLE'		, 'Leave Information  Details');
appLbls.add('AMS.LEAVEINFO.SAVE.SUCCESS'		, 'Leave Data saved successfully.');
appLbls.add('AMS.LEAVEINFO.SAVE.FAILURE'		, 'Unable to save Leave Data.');
appLbls.add('AMS.LEAVEINFO.WINDOW.TITLE'		, 'Leave Request Form');
appLbls.add('AMS.LEAVEINFO.DELETE.SUCCESS'	, 'Leave Info deleted successfully.');
appLbls.add('AMS.LEAVEINFO.DELETE.FAILURE'	, 'Unable to delete Leaves Info.');
appLbls.add('AMS.LEAVEINFO.DELETE.CONFIRM',	'Are you sure to Delete this record?');
appLbls.add('AMS.LEAVEINFO.DELETE.FAILURE'		, 'Unable to Delete Asset Leave Request.');

/***  Leave info Module  end ***/

/***  Holiday info Module  start ***/
appLbls.add('AMS.HOLIDAYINFO.GRID.TITLE'		, 'Holiday Information Details');
appLbls.add('AMS.HOLIDAYINFO.SAVE.SUCCESS'		, 'Holiday Data saved successfully.');
appLbls.add('AMS.HOLIDAYINFO.SAVE.FAILURE'		, 'Unable to save Holiday Data.');
appLbls.add('AMS.HOLIDAYINFO.WINDOW.TITLE'		, 'Holidays Form');
appLbls.add('AMS.HOLIDAYINFO.DELETE.SUCCESS'	, 'Holidays Information deleted successfully.');
appLbls.add('AMS.HOLIDAYINFO.DELETE.FAILURE'	, 'Unable to delete Holidays Info.');

/***   Holiday   info Module  end ***/


/*** Sub Store info Module  start ***/

appLbls.add('AMS.SubStoresInfo.GRID.TITLE'			, 'Sub Store Details');
appLbls.add('AMS.SubStoresInfo.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.SubStoresInfo.WINDOW.TITLE'		, 'SubStore');
appLbls.add('AMS.SubStoresInfo.SAVE.SUCCESS'		, 'Sub Store Info saved successfully.');
appLbls.add('AMS.SubStoresInfo.SAVE.FAILURE'		, 'Unable to save Sub Store Info.');
appLbls.add('AMS.SubStoresInfo.SELECTSECTION'		, 'Please Select at Least One Screen to Create  a SubStore.');

/***  Sub Store info Module  end ***/


/*** Main Store to Sub Store Item Issue info Module  start ***/

appLbls.add('AMS.SUBSTOREITEMISSUE.GRID.TITLE'			, 'Main Store To Sub Store Item Issue Details');
appLbls.add('AMS.SUBSTOREITEMISSUE.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('AMS.SUBSTOREITEMISSUE.WINDOW.TITLE'		, 'Main Store To Sub Store Item Issue ');
appLbls.add('AMS.SUBSTOREITEMISSUE.SAVE.SUCCESS'		, 'Main Store To Sub Store Item Issued  successfully.');
appLbls.add('AMS.SUBSTOREITEMISSUE.SAVE.FAILURE'		, 'Unable to save Item Info.');
appLbls.add('AMS.SUBSTOREITEMISSUE.SELECTSECTION'		, 'Please Select Record');

/*** Main Store to Sub Store Item Issue info Module   start ***/
appLbls.add('AMS.AOID'		, UserData.firstname+" "+UserData.lastName);
appLbls.add('AMS.ASSIGN'		, 'ASSIGN AUTHENTICATION');
appLbls.add('AMS.AUTHENTICATION.GRID.TITLE'		, 'AUTHENTICATION DETAILS');
/*** Main Store to Sub Store Item Issue info Module   end ***/

/*** Labels for the DashBoardModule   start ***/
appLbls.add('PendingRequestCount', 'Total Pending Request');
appLbls.add('AssetFullyIssued', 'Total Assets Fully Issued');
appLbls.add('AssetPartialIssued', 'Total Assets Partial Issued');

/*** added for FTS file  type   start ***/
appLbls.add('FTS.FILETYPE.GRID.TITLE', 'File Action Type Details');
appLbls.add('FTS.FILETYPE.SCREEN.GRID.TITLE'	, 'File Action Type Information');
appLbls.add('FTS.FILETYPE.WINDOW.TITLE'		, 'File Action Type Information');
appLbls.add('FTS.FILETYPE.SAVE.SUCCESS'		, 'File Action Type Info saved successfully.');
appLbls.add('FTS.FILETYPE.SAVE.FAILURE'		, 'Unable to save File Action Type  Info.');
appLbls.add('FTS.FILETYPE.SELECT'		, 'Please Select record');

appLbls.add('FTS.FE.GRID.TITLE'		, 'File Details');
appLbls.add('FTS.FE.WINDOW.TITLE'		, 'File Entry');
appLbls.add('FTS.FE.SAVE.SUCCESS'		, 'File Info saved successfully.');
appLbls.add('FTS.FE.SAVE.FAILURE'		, 'Unable to save File Info.');

appLbls.add('FTS.FILE.SUBMIT.SUCCESS'  	, 'File Process Flow Saved successfully');
appLbls.add('FTS.FILE.SUBMIT'  			, 'Are you sure, Modifications not allowed once File is Submitted');
/*** added for FTS file  type   end ***/

/**
 * To return the label
 * @param labelKey
 * @returns
 */		

/***  Organization Module  end ***/

appLbls.add('FTS.ORG.GRID.TITLE'			, 'Organization Details');
appLbls.add('FTS.ORG.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('FTS.ORG.WINDOW.TITLE'		, 'Organization Details');
appLbls.add('FTS.ORG.SAVE.SUCCESS'		, 'Organization Info saved successfully.');
appLbls.add('FTS.ORG.SAVE.FAILURE'		, 'Unable to save Organization Info.');
appLbls.add('FTS.ORG.SELECTSECTION'		, 'Please Select at Least One Screen to Create/Update a Organization.');
appLbls.add('FTS.WFA.WINDOW.TITLE'		, 'Workflow');
appLbls.add('FTS.GLOBAL.ATTACHMENTS.TITLE','Attachments');
appLbls.add('FTS.WFA.SAVE.SUCCESS'		,	'Authorities saved succesfully');
appLbls.add('FTS.WFA.SAVE.FAILURE'		,	'Unable To Save Authorities Details');
appLbls.add('FTS.WFA.EMPTY.SAVE.FAILURE',	'Authorities empty.Unable to save');
appLbls.add('FTS.WFA.SELECT.ROW','Please select one record to proceed');
appLbls.add('FTS.FE.CANCEL.CONFIRM',	'Are you sure to Cancel?');
appLbls.add('FTS.FE.SUBMIT.CONFIRM',	'Once the File is Submitted. You Can not modify the process flow. Are you sure to Submit?');
appLbls.add('FTS.FILESTATUSREPORT.GRID.TITTLE', 'File Status Details');

/***  device info Module  end ***/

appLbls.add('FTS.DEV.GRID.TITLE'			, 'Device Info Details');
appLbls.add('FTS.DEV.SCREEN.GRID.TITLE'	, 'Screen Information');
appLbls.add('FTS.DEV.WINDOW.TITLE'		, 'Device Info Details');
appLbls.add('FTS.DEV.SAVE.SUCCESS'		, 'Device Info saved successfully.');
appLbls.add('FTS.DEV.SAVE.FAILURE'		, 'Unable to save Device Info.');
appLbls.add('FTS.DEV.SELECTSECTION'		, 'Please Select at Least One Screen to Create/Update a Device.');
appLbls.add('FTS.WF.AUTHORITY.DUPLICATEPRIORITY'			, 'Duplicate priority');
appLbls.add('AMS.USM.SELECTROLE'			, 'Please Select at Least One Section to Create/Update User Section Map.');
appLbls.add('AMS.USM.SCREEN.GRID.TITLE'	, 'Section Information');
appLbls.add('AMS.USM.WINDOW.TITLE'		, 'User Information');
appLbls.add('AMS.USM.GRID.TITLE'			, 'User Details');
/***  Organization info Module  end ***/

function getLabel(labelKey)
{
	return appLbls.get(labelKey);
}