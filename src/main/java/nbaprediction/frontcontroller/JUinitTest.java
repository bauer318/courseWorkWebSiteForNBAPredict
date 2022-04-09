package nbaprediction.frontcontroller;

import nbaprediction.moderator.logic.AddPredictionLogic;

public class JUinitTest {
	public static void main(String[] args){
		
		boolean isConfirms = AddPredictionLogic.confirmsSuperior(22.3, 29,28,11);
		if(isConfirms) {
			System.out.println("Ok");
		}else {
			System.out.println("No");
		}
		
	}
}
