package com.davai.template.handler

import com.davai.template.event.*

interface EventHandler {
	void handleEvent(Event event) 
}