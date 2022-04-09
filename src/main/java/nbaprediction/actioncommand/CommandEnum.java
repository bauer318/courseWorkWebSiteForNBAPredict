package nbaprediction.actioncommand;

import nbaprediction.adminmoderator.command.DeleteAdminModeratorCommand;
import nbaprediction.adminmoderator.command.EditAdminModeratorCommand;
import nbaprediction.adminmoderator.command.SaveAdminModeratorDataCommand;
import nbaprediction.loginlogout.command.ConnectedCommand;
import nbaprediction.loginlogout.command.LoginCommand;
import nbaprediction.loginlogout.command.LogoutCommand;
import nbaprediction.moderator.command.AddMatchCommand;
import nbaprediction.moderator.command.AddPredictionCommand;
import nbaprediction.moderator.command.MenuAddMatchCommand;
import nbaprediction.moderator.command.MenuPredictionCommand;
import nbaprediction.moderator.command.MenuRantingCommand;
import nbaprediction.moderator.command.MenuUpdatePointsCommand;
import nbaprediction.moderator.command.RantingCommand;
import nbaprediction.moderator.command.UpdatePointsCommand;
import nbaprediction.users.command.AddUserCommand;
import nbaprediction.users.command.EditUserCommand;
import nbaprediction.users.command.MenuAddUserCommand;

public enum CommandEnum {

	LOGIN {
		{
			this.command = new LoginCommand();
		}
	},
	LOGOUT {
		{
			this.command = new LogoutCommand();
		}
	},
	CONNECTED {
		{
			this.command = new ConnectedCommand();
		}
	},
	ADDUSER {
		{
			this.command = new AddUserCommand();
		}
	},
	MENUADDUSER {
		{
			this.command = new MenuAddUserCommand();
		}
	},
	EDITUSER {
		{
			this.command = new EditUserCommand();
		}
	},
	EDITADMINMODERATOR {
		{
			this.command = new EditAdminModeratorCommand();
		}
	},
	DELETEADMINMODERATOR {
		{
			this.command = new DeleteAdminModeratorCommand();
		}
	},
	SAVEADMINMODERATORDATA {
		{
			this.command = new SaveAdminModeratorDataCommand();
		}
	},
	ADDMATCH {
		{
			this.command = new AddMatchCommand();
		}
	},
	MENUADDMATCH {
		{
			this.command = new MenuAddMatchCommand();
		}
	},
	MENUUPDATEPOINTS {
		{
			this.command = new MenuUpdatePointsCommand();
		}
	},
	UPDATEPOINTS {
		{
			this.command = new UpdatePointsCommand();
		}
	},
	MENUPREDICTION {
		{
			this.command = new MenuPredictionCommand();
		}
	},
	ADDPREDICTION {
		{
			this.command = new AddPredictionCommand();
		}
	},
	MENURANTING {
		{
			this.command = new MenuRantingCommand();
		}
	},
	RANTING {
		{
			this.command = new RantingCommand();
		}
	};

	ActionCommand command;

	public ActionCommand getCurrentCommand() {
		return command;
	}

}
