package nbaprediction.moderator.command;

import jakarta.servlet.http.HttpServletRequest;
import nbaprediction.actioncommand.ActionCommand;

public class MenuUpdatePointsCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		String page = "/jsp/commands/updatePoints.jsp";
		return page;
	}

}
