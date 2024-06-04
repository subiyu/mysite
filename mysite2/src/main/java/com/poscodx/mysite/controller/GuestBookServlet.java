package com.poscodx.mysite.controller;

import java.util.Map;
import com.poscodx.mysite.controller.action.guestbook.DeleteAction;
import com.poscodx.mysite.controller.action.guestbook.DeleteFormAction;
import com.poscodx.mysite.controller.action.guestbook.InsertAction;
import com.poscodx.mysite.controller.action.guestbook.ListAction;

public class GuestBookServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String, Action> mapAction = Map.of(
			"deleteform", new DeleteFormAction(),
			"insert", new InsertAction(),
			"delete", new DeleteAction(),
			"list", new ListAction()
			);

	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new ListAction());
	}
}
