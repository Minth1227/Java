package test01.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import test01.lib.ContactTest1;

public class Test1_1 {

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


			Set<Integer> phonenum0 = contact.keySet();
			Iterator<Integer> phonekey0 = phonenum0.iterator();
			
			while (number == 1) {
				try {
					System.out.println("등록할 회원의 정보를 입력하세요.");
					System.out.println("이름: ");
					name = scanner.next();
					System.out.println("전화번호(ex: 01012345678) : ");
					phone = scanner.nextInt();
					while (phonekey0.hasNext()) {
//						int phonenumber2 = phonekey0.next();

//					}if (contact.size()==0) {
//							continue;			
//					}
					 if((contact.get(phone).getPhone())==phone) {
						System.out.println("이미 입력한 번호입니다.");
					}
					 //nullPoint 에러 잡기 
					
//							if(phone)  폰번호 같은거 있을때 예외처리 해야함 
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
					}
				} catch (InputMismatchException i) {
					System.out.println("잘못입력하셨습니다. 다시 입력하세요.");
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

			// 수정하기(삭제하고 다시 입력하기)
			Set<Integer> phonenum2 = contact.keySet();
			Iterator<Integer> phonekey2 = phonenum2.iterator();
			ArrayList<ContactTest1> modifyList = new ArrayList<ContactTest1>();

			while (number == 3) {
				// 수정할 회원 찾아서 이름 누르면 이름 같은 사람 프린트 하게 함/ 인덱스 주고 선택해서 그거 수정하게 해야함
				System.out.println("수정할 회원의 이름을 입력하세요");
				System.out.println("이름");
				String modifyname = scanner.next();
				// HashMap에 저장된것들 반복해서 전부 꺼내서 비교

				int samename = 0;

				while (phonekey2.hasNext()) {
					int phonenumber2 = phonekey2.next();

					String findname = contact.get(phonenumber2).getName();

					if (findname.equals(modifyname)) {
						modifyList.add(contact.get(phonenumber2));
						samename += 1;
						// ArrayList에 담아서 꺼낼까?
					}

				}
				if(samename == 0) {
					System.out.println("해당하는 회원 정보가 없습니다.");
					number =0;
					break;
				}
				System.out.println("총" + samename + "개의 목록이 검색되었습니다.");
//				수정 할 목록 삭제하기 
				while (true) {
					if (samename != 0) {
						System.out.println("아래 목록 중 수정할 회원의 번호를 입력하세요.");
					}

					for (int i = 0; i < modifyList.size(); i++) {
						System.out.println((i + 1) + "." + modifyList.get(i).toString());
					}

					int deletnumber = scanner.nextInt() - 1;
					// 목록 삭제하기 (배열에서는 삭제 할 필요 없고, 배열의 핸드폰번호 get으로 빼서, 일치하는 그 키값을 다 삭제)
					if (deletnumber >= 0 || deletnumber < modifyList.size()) {
						int deletkey = modifyList.get(deletnumber).getPhone();
						contact.remove(deletkey);
						break;
					} else {
						System.out.println("수정 할 회원번호를 잘못입력하셨습니다. ");
						continue;
					}
				}
//					삭제 후 저장할 목록 생성하기.

				while (true) {
					try {
						System.out.println("수정할 정보를 입력하세요.");
						System.out.println("이름: ");
						name = scanner.next();
						System.out.println("전화번호(ex: 01012345678) : ");
						phone = scanner.nextInt();
//								if(phone)  폰번호 같은거 있을때 예외처리 해야함 
						System.out.println("주소: ");
						address = scanner.next();
						System.out.println("구분(ex. 가족, 친구, 기타) : ");
						relationship = scanner.next();
						System.out.println();
						if (relationship.equals("가족") || relationship.equals("친구") || relationship.equals("기타")) {
							contact.put(phone, new ContactTest1(name, phone, address, relationship));
							number = 0;
							break;
						} else {
							System.out.println("구분자를 잘못 입력하셨습니다. 다시 입력하세요.");
						}
					} catch (InputMismatchException i) {
						System.out.println("전화번호에 숫자만 입력하세요. 다시 입력하세요.");
						continue;
					}
				}

			}
			// 여기까지

//					
//					String findname = contact.get(phonenumber).getName();
//					// 입력한 이름과 해쉬맵의 이름들 꺼내서 비교하기 , 같은 정보들 빼내기
//					if (modifyname.equals(findname)) {
//						System.out.println(contact.get(phonenumber).toString());
//						samename += 1; // 인덱스 만들어야함 //ArrayList 만들어서 for문 돌려서 새로 값 넣어주기 (그런데 수정해서 다시 HashMap 안에 넣어줘야함)
//					}

			// 삭제하
			Set<Integer> phonenum3 = contact.keySet();
			Iterator<Integer> phonekey3 = phonenum3.iterator();
			int searchname = 0;
			// 삭제 할 리스트 따로 만들기
			ArrayList<ContactTest1> deleteList = new ArrayList<ContactTest1>();

			while (number == 4) {
				System.out.println("삭제할 회원의 이름을 입력하세요.");
				System.out.println("이름: ");
				String deletname = scanner.next();
//				ContactTest1 findname = new ContactTest1();
				while (phonekey3.hasNext()) {
					int phonenumber3 = phonekey3.next();
					// @개 목록이 검색되었습니다.
					String findname = contact.get(phonenumber3).getName();

					if (findname.equals(deletname)) {
						deleteList.add(contact.get(phonenumber3));
						searchname += 1;
						// ArrayList에 담아서 꺼낼까?
					}

				}
				System.out.println("총" + searchname + "개의 목록이 검색되었습니다.");

				while (true) {
					if (searchname != 0) {
						System.out.println("아래 목록 중 삭제할 회원의 번호를 입력하세요.");
					}

					for (int i = 0; i < deleteList.size(); i++) {
						System.out.println((i + 1) + "." + deleteList.get(i).toString());
					}

					int deletenumber = scanner.nextInt() - 1;
					// 목록 삭제하기 (배열에서는 삭제 할 필요 없고, 배열의 핸드폰번호 get으로 빼서, 일치하는 그 키값을 다 삭제)
					if (deletenumber >= 0 || deletenumber < deleteList.size()) {
						int deletekey = deleteList.get(deletenumber).getPhone();
						contact.remove(deletekey);
						break;
					} else {
						System.out.println("삭제 할 회원번호를 잘못입력하셨습니다. ");
						continue;
					}
				}

				System.out.println("삭제가 완료되었습니다.");
				number = 0;

			}
		}
		scanner.close();
	}
}
