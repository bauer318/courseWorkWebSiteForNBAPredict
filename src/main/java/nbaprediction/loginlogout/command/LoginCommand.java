package nbaprediction.loginlogout.command;



import jakarta.servlet.http.HttpServletRequest;
import nbaprediction.actioncommand.ActionCommand;
import nbaprediction.data.UserTypeEnum;
import nbaprediction.loginlogout.logic.LoginLogic;

public class LoginCommand implements ActionCommand{
	private static final String PARAM_NAME_LOGIN = "login";
	private static final String PARAM_NAME_PASSWORD = "password";
	private static final int AUTHORIZATION_STATUS = 1;

	@Override
	public String execute(HttpServletRequest request) {
		String page=null;
		String login = request.getParameter(PARAM_NAME_LOGIN);
		String password = request.getParameter(PARAM_NAME_PASSWORD);
		if(LoginLogic.checkLogin(login, password)) {
			LoginLogic.setAuthorizationStatus(login, AUTHORIZATION_STATUS);
			UserTypeEnum role = LoginLogic.getUserRoleByLogin(login);
			LoginLogic.setUserSession(request, login, role);
			role.setUserMenu(request);
			page = role.getUserIndexPage();
		}else {
			request.setAttribute("wrongloginpass", "Неправильный логин и/или пароль.");
			page="/jsp/commands/login.jsp";
		}
		return page;
	}

}
