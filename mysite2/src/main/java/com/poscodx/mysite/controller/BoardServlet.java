package com.poscodx.mysite.controller;

import java.util.Map;

import com.poscodx.mysite.controller.action.board.DeleteAction;
import com.poscodx.mysite.controller.action.board.ListAction;
import com.poscodx.mysite.controller.action.board.SearchBoardAction;
import com.poscodx.mysite.controller.action.board.ViewAction;
import com.poscodx.mysite.controller.action.board.WriteAction;

public class BoardServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Action> mapAction = Map.of(
			"board", new ListAction(),
			"write", new WriteAction(),
			"search_board", new SearchBoardAction(),
			"view", new ViewAction(),
			"delete", new DeleteAction()
			);
	
	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new ListAction());
	}

}
