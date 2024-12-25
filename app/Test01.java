package test01.app;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import test01.lib.ContactTest1;

public class Test01 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		HashMap<Integer, ContactTest1> contact = new HashMap<Integer, ContactTest1>();

		String name;
		int phone;
		String address;
		String relationship;

		int number = 0;
		while (number == 0) {
			System.out.println(" ");
			String print = """
					=========================
					   다음 메뉴 중 하나를 선택하세요.
					=========================
					 1. 회원 추가
					 2. 회원 목록 보기
					 3. 회원 정보 수정하기
					 4. 회원 삭제
					 5. 종료 """;
			System.out.println(print);
			try {
				number = scanner.nextInt();
				if (number <= 0 || number > 5) {
					System.out.println("1에서 5사이의 값을 입력하세요.");
				}
			} catch (InputMismatchException i) {
				System.out.println("숫자를 입력하세요.");
			}

			while (number == 1) {
				try {
					System.out.println("등록할 회원의 정보를 입력하세요.");
					System.out.println("이름: ");
					name = scanner.next();
					System.out.println("전화번호(ex: 01012345678) : ");
					phone = scanner.nextInt();
//					if(phone)  폰번호 같은거 있을때 예외처리 해야함 
					System.out.println("주소: ");
					address = scanner.next();
					System.out.println("구분(ex. 가족, 친구, 기타) : ");
					relationship = scanner.next();
					System.out.println();
					if (relationship.equals("가족") || relationship.equals("친구") || relationship.equals("기타")) {
						contact.put(phone, new ContactTest1(name, phone, address, relationship));
						number = 0;
					} else {
						System.out.println("구분자를 잘못 입력하셨습니다. 다시 입력하세요.");
					}
				} catch (InputMismatchException i) {
					System.out.println("전화번호에 숫자만 입력하세요. 다시 입력하세요.");
					continue;
				}

			}
			Set<Integer> phonenum = contact.keySet();
			Iterator<Integer> phonekey = phonenum.iterator();
			while (number == 2) {
				System.out.println("총" + contact.size() + "명의 회원이 저장되어 있습니다.");
				while (phonekey.hasNext()) {
					int phonenumber = phonekey.next();
					System.out.println(contact.get(phonenumber).toString());
				}
				number = 0;

			}

			while (number == 3) {
				// 수정할 회원 찾아서 이름 누르면 이름 같은 사람 프린트 하게 함/ 인덱스 주고 선택해서 그거 수정하게 해야함
				System.out.println("수정할 회원의 이름을 입력하세요");
				System.out.println("이름");
				String modifyname = scanner.next();
				// HashMap에 저장된것들 반복해서 전부 꺼내서 비교
				Set<Integer> phonenum2 = contact.keySet();
				Iterator<Integer> phonekey2 = phonenum2.iterator();
				int samename = 0;
				while (phonekey2.hasNext()) {
					int phonenumber = phonekey2.next();
					String findname = contact.get(phonenumber).getName();
					// 입력한 이름과 해쉬맵의 이름들 꺼내서 비교하기 , 같은 정보들 빼내기
					if (modifyname.equals(findname)) {
						System.out.println(contact.get(phonenumber).toString());
						samename += 1; //인덱스 만들어야함  //ArrayList 만들어서 for문 돌려서 새로 값 넣어주기 (그런데 수정해서 다시 HashMap 안에 넣어줘야함)
					}
				}
				System.out.println("총 " + samename + "개의 목록이 검색되었습니다.");
				System.out.println("아래 목록 중 수정할 회원의 번호를 입력하세요.");
				number = 0;
			}

		}
	}
}