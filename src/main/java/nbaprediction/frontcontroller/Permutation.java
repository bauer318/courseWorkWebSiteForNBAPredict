package nbaprediction.frontcontroller;

import java.util.Scanner;

public class Permutation {
	public static void main(String[] args) throws InterruptedException {
		int sourceArray[] = null;	
		Scanner sc = new Scanner(System.in);
		System.out.println("������� n");
		int n = sc.nextInt();
		if(n>0) {
			sourceArray = new int[n];
			for (int i = 0; i < n; i++) {
				sourceArray[i] = i + 1;
			}
			printResult(sourceArray, n);
			while (nextSet(sourceArray, n)) {
				printResult(sourceArray, n);
			}
		}else {
			System.out.println("n ������ ���� > 0");
		}
		sc.close();
	}

	//������� ������ ������� �������� i � j ������� sourceArray
	private static void permuteArrayElements(int sourceArray[], int i, int j) {
		int temp = sourceArray[i];
		sourceArray[i] = sourceArray[j];
		sourceArray[j] = temp;
	}

	//�������� ������� , ���������� ������, ���� ���� ��� �������� ����������� �������� ������� sourceArray
	private static boolean nextSet(int sourceArray[], int n) {
		int i = n - 2;
		while (i != -1 && sourceArray[i] >= sourceArray[i + 1]) {
			i--;
		}
		if (i == -1) {
			return false; // ����� �����, ������ ��� ������������
		}
		int j = n - 1;
		while (sourceArray[i] >= sourceArray[j]) {
			j--;
		}
		permuteArrayElements(sourceArray, i, j);
		//��� ������ ����������
		int left = i + 1;
		int right = n - 1;
		while (left < right) {
			permuteArrayElements(sourceArray, left++, right--);
		}
		return true;
	}

	private static void printResult(int sourceArray[], int n) {
		String result = "(";
		for (int i = 0; i < n; i++) {
			result += String.valueOf(sourceArray[i]);
			if (i != n - 1) {
				result += ",";
			}
		}
		result += ")";
		System.out.println(result);
	}


}
