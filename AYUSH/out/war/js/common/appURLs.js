Ext.define('AMS.Urls',
{
	statics :
	{
		command						:	"./command"
		,gridData					:	"./gridData"
		,pageRenderer				:	"./pageRenderer"
		,autoCompleteData			:	"./authoCompleteData"
		,comboData					:	"./comboData"
		,customComboData			:	"./customComboData"
		,storesListForAssetRequest	:	"./storesListForAssetRequest"
		,changePassword				:	"./changePassword"
		,deleteUser					:	"./deleteUser"
		,deleteWebsite				:	"./deleteWebsite"
		,getScreensByMenuId			:	"./getScreensByMenuId"
		,submitReqForApproval		:	"./submitReqForApproval"
		,issueRequestedItems		:	"./issueRequestedItems"
		,userMappedRoles			:	"./userMappedRoles/"
		,userMappedprojects			:	"./userMappedprojects/"
		,validateEmployeeId			:	"./validateEmployeeId"
		,validateRoleName			:	"./validateRoleName"
		,resetUserPassword			:   "./tr/resetUserPassword"
		,validateProjectName    	:   "./master/validateProjectName"
		,submitRequest    			:   "./submitRequest"
		,getBarCodesByItemCode		:	"./getBarCodesByItemCode"
		,reqRecommendApproval		: 	"./master/reqRecommendApproval"
		,RemoveRequestItem				:	"./master/RemoveSelectedItem"
		,approveRequest				:	"./master/approveRequest"
		,approvePurchaseRequest		:	"./master/approvePurchaseRequest"
		,validateDesignation    	:   "./master/validateDesignation"
		,roleScreens				:	"./master/screens/"
		,getItemsBySearchText		:	"./master/getItemsBySearchText"
		,getItemsByBarcode			:	"./master/getItemsByBarcode"
		,getItemByBarCodeStoreItemCode	:	"./master/getItemByBarCodeStoreItemCode"
		,getItemsByBarcodeRepairOrCondem	:	"./master/getItemsByBarcodeRepairOrCondem"
		,getItemsIssuedForReturnByBarcode	:	"./master/getItemsIssuedForReturnByBarcode"
		,validateRoleName	 				:	"./master/validateRoleName/"
		,cancelAssetRequest					:	"./master/cancelAssetRequest/"
		,userProjects						:   "./userProjects/"
		,userRoles					:	"./userRoles/"
		,storeComboUserwise		:	"./master/storeComboUserwise"
		,barCodeMethod          :   "./barCodeGen/barCodeMethod"
		,barCodeMethodLedgerView          :   "./barCodeGen/barCodeMethodLedgerView"
		,rfidCodeMethod          :   "./barCodeGen/rfidCodeMethod"
		,officerComboData		: 	"master/getOfficerComboData"
		,getItemsByBarcodeWithOfficerId : "./master/getItemsByBarcodeWithOfficerId"
		,getItemComboStoreTransferUser	: "./master/getItemComboStoreTransferUser"
		,getItemsByItemNameWithOfficerId: "./master/getItemsByItemNameWithOfficerId"
		,getItemsForSingleUserTransfer  : "./master/getItemsForSingleUserTransfer"
		,validateStoreCode		:	"./master/validateStoreCode"
	    ,printRecord            :	"./printForm/getAssetIssuedItemContent"
	    ,getAssetIssuedItemContent :"./master/getAssetIssuedItemContent"
	    ,printRecord1  			:	"./printForm/getAssetRepairContent"
	    ,entryItemsUpload		:	"./uploadExcel/itemsEntry"
	    ,getGridItemsForExcelUpload : "./uploadExcel/getGridItemsForExcelUpload"
	    ,validateBuilding    	:   "./master/validateBuilding"
	    ,validateBuildingSection:   "./master/validateBuildingSection"	
	    ,validateSection        :	"./master/validateSection"
	    ,itemNameForEntryCombo	:	"./master/itemNameForEntryCombo"
	    ,validateItemNameInMaster : "./master/validateItemNameInMaster"
    	,validateItemNameByStoreInMaster : "./master/validateItemNameByStoreInMaster"
    	,validateFWById				: "./master/validateFWById"
	    ,validateStoreName		:	"./master/validateStoreName"
	    ,supplierComboStoreWise	:	"./master/supplierComboStoreWise"
    	,supplyOrderComboSupplierWise 	: "./master/supplyOrderComboSupplierWise"
    	,validateIp	                    : "./master/validateIp"
	    ,validateReaderIp               :  "./master/validateReaderIp"
    	,validateWorkFlowNames		:	"./workflow/validateWorkFlowNames"
    	,getAuthorityStatusMap		:	"./workflow/getAuthorityStatusMap/"
    	,getworkflowAuthorityMap	:	"./workflow/getworkflowAuthorityMap/"
    	,getWorkflowMappedAuthorities	:	"./workflow/getWorkflowMappedAuthorities/"
    	,getLeavesInfo	:	"./master/getLeavesInfo/"
    	,deleteFacultyLeaveRequest: "./master/deleteFacultyLeaveRequest/"
    	,deleteHoliday :"./master/deleteHoliday/"
       ,validateSubStoreName		:	"./master/validateSubStoreName"
       ,assignUserReportingManager		:	"./master/assignUserReportingManager"
       ,unAssignUserReportingManager  : "./master/unAssignUserReportingManager"
       ,uploadItemRequest         : "./master/uploadItemRequest"
       ,removeApprovalAuthentication :"./master/removeApprovalAuthentication"
       ,getItemsForSubStore          :"./master/getItemsForSubStore"
      ,updateScanCopyRequestRecordStatusToFullyIssue  :"./master/updateScanCopyRequestRecordStatusToFullyIssue"
      ,getSubstoresAdminWise    :"./master/getSubstoresAdminWise"
      ,updateReqQtyApprovalLevel :"./master/updateReqQuantityAtApprovalLevel"
      ,validateFileType    	:   "./master/validateFileType"
      ,assignFileAuthentication		:	"./master/assignFileAuthorization"
      ,releaseFileAuthentication    : "./master/releaseFileAuthoization"
      ,saveOpenFileWF : "./master/saveOpenFileWF"
      ,submitFile    : "./master/submitFile"
      ,approveFile    : "./master/approveFile"  
      ,userReportingmanagers : "./master/userReportingmanagers"
      ,getFileDetailsByBarcode : "./master/getFileDetailsByBarcode"
      ,uploadFileAttachmentCopy : "./master/uploadFileAttachmentCopy"
      ,getFileDetailsByBarcodeToView:"./master/getFileDetailsByBarcodeToView"
      ,cancelFileEntry					:	"./master/cancelFileEntry/"
      ,validateFileCode			:	"./master/validateFileCode"
      ,userSections				:	"./master/userSections/"
      ,getNotifiAlerts			:"./master/getNotifiAlerts"
    	
	}
});


Ext.define('AMS.Constants',
{
	statics :
	{
		defaultProfileImg : "images/default-profile.png"
		,citizenProfileImg: "./image/citizenImage?id="//id
		,menus : 
		[
			{
				id		: 6
				,name	: 'SOS'
			}
		]
		,screens : 
		[
			{
				id		: 21
				,name	: 'Open SOS'
			}
		]
	}

});