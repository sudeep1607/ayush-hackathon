package com.fts.hibernate.managers;

import org.springframework.stereotype.Repository;

import com.fts.hibernate.common.GenericManager;
import com.fts.hibernate.models.EventLogger;

@Repository
public class EventLoggerManager extends GenericManager<EventLogger, Long> {

	public EventLoggerManager(  ) {
		super(EventLogger.class);
		
	}
}
