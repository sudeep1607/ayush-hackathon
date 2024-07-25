package com.fts.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import com.mot.rfid.api3.RFIDReader;
import com.mot.rfid.api3.RfidEventsListener;
import com.mot.rfid.api3.RfidReadEvents;
import com.mot.rfid.api3.RfidStatusEvents;
import com.mot.rfid.api3.TagData;

@Service
public class EventsHandler implements RfidEventsListener{
	
	private static final Log LOG = LogFactory.getLog(EventsHandler.class);
	TagData[] myTags = null;
	public long totalTags = 0;
	
	public EventsHandler()
	{
	}

	public void eventReadNotify(RfidReadEvents rre) {
		//updateTags(false);
	}
		
	public void eventStatusNotify(RfidStatusEvents rse)
	{
		
		System.out.println("Status Notification " + rse.StatusEventData.getStatusEventType());
		/*postInfoMessage(rse.StatusEventData.getStatusEventType().toString());
		
		STATUS_EVENT_TYPE statusType = rse.StatusEventData.getStatusEventType();
		if (statusType == STATUS_EVENT_TYPE.ACCESS_STOP_EVENT)
		{
			try
			{
			  accessEventLock.lock();
			  accessComplete = true;
			  accessEventCondVar.signalAll();
			}
			finally
			{
				accessEventLock.unlock();
				
			}
			
		}
             else if(statusType == STATUS_EVENT_TYPE.INVENTORY_STOP_EVENT)
             {
                 try
                 {
                     inventoryStopEventLock.lock();
                     inventoryComplete = true;
                     inventoryStopCondVar.signalAll();
                     
                 }
                 finally
                 {
                     inventoryStopEventLock.unlock();
                 }
                         
             }
		else if(statusType == STATUS_EVENT_TYPE.BUFFER_FULL_WARNING_EVENT || statusType == STATUS_EVENT_TYPE.BUFFER_FULL_EVENT)
		{
			postInfoMessage(statusType.toString());
		}*/
	}
	
	void updateTags(Boolean isAccess, RFIDReader reader)
	{
		myTags = reader.Actions.getReadTags(50);
		LOG.info("--------------------------myTags----------------------------"+ myTags);
		if (myTags != null)
		{
				 if(!isAccess)
				 {
					 for (int index = 0; index < myTags.length; index++) 
					 {
						 TagData tag = myTags[index];
						 String key = tag.getTagID();
						// if (!tagStore.containsKey(key))
						// {
						//	tagStore.put(key,totalTags);
							postInfoMessage("ReadTag "+key); 
							//uniqueTags++;
						 // }
						 totalTags++;
					 }
				
				 }
				 else
				 {
					 for (int index = 0; index < myTags.length; index++)
					 {
						 TagData tag = myTags[index];
						 if(tag.getMemoryBankData() != null)
						    postInfoMessage("TagID "+tag.getTagID()+tag.getMemoryBank().toString()+"  "+tag.getMemoryBankData());
						 else
							 postInfoMessage("TagID "+tag.getTagID()+"Access Status:  "+tag.getOpStatus().toString()); 
						   	 
					 }
				 }
		}
	
	}
	static void postInfoMessage(String msg)
    {
		LOG.info(msg);
    }

}
