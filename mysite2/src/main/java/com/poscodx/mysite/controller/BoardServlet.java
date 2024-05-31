package com.poscodx.mysite.controller;

import java.util.Map;

import com.poscodx.mysite.controller.action.board.DeleteAction;
import com.poscodx.mysite.controller.action.board.ListAction;
import com.poscodx.mysite.controller.action.board.ModifyAction;
import com.poscodx.mysite.controller.action.board.ModifyFormAction;
import com.poscodx.mysite.controller.action.board.ReplyAction;
import com.poscodx.mysite.controller.action.board.SearchBoardAction;
import com.poscodx.mysite.controller.action.board.ViewAction;
import com.poscodx.mysite.controller.action.board.WriteAction;
import com.poscodx.mysite.controller.action.board.WriteFormAction;

public class BoardServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Action> mapAction = Map.of(
			"board", new ListAction(),
			"write_form", new WriteFormAction(),
			"write", new WriteAction(),
			"reply", new ReplyAction(),
			"search_board", new SearchBoardAction(),
			"view", new ViewAction(),
			"delete", new DeleteAction(),
			"modify_form", new ModifyFormAction(),
			"modify", new ModifyAction()
			);
	
	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new ListAction());
	}

}
