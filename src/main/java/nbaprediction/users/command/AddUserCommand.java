package nbaprediction.users.command;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import nbaprediction.actioncommand.ActionCommand;
import nbaprediction.datalayer.data.User;
import nbaprediction.loginlogout.logic.LoginLogic;
import nbaprediction.users.logic.AddUserLogic;

public class AddUserCommand implements ActionCommand {
	private static final String PARAM_NAME_LOGIN = "userLogin";
	private static final String PARAM_NAME_PASSWORD = "userPassword";
	private static final String PARAM_NAME_FIRSTNAME = "userFirtsName";
	private static final String PARAM_NAME_LASTNAME = "userLastName";
	private static final String PARAM_NAME_EMAIL ="userEmail";
	private static final String PARAM_NAME_ROLE = "selectRole";

	@Override
	public String execute(HttpServletRequest request) {
		String page = "/jsp/commands/adminIndex.jsp";
		String login = request.getParameter(PARAM_NAME_LOGIN);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		String role = request.getParameter(PARAM_NAME_ROLE);
		if(AddUserLogic.isLoginExist(login)) {
			request.setAttribute("isLoginExist", "��� ����� ��� ����������");
		}else {
			if(role.equals("bettor")) {
				String firstname = request.getParameter(PARAM_NAME_FIRSTNAME);
				String lastname = request.getParameter(PARAM_NAME_LASTNAME);
				String email = request.getParameter(PARAM_NAME_EMAIL);
				int groupID = 3;
				AddUserLogic.addAdminModerator(login, password, groupID);
				AddUserLogic.addBettor(firstname, lastname, email);
			} else {
				int groupID = 1;
				if(role.equals("moderator")) {
					groupID = 2;
				}
				AddUserLogic.addAdminModerator(login, password, groupID);
			}
		}
		List<User> users = LoginLogic.getAllUsers();
		request.setAttribute("users", users);
		return page;
	}

}
