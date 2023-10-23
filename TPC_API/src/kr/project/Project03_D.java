package kr.project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import kr.soldesk.ExcelVO;

public class Project03_D {

	public static void main(String[] args) {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {

			System.out.print("책제목:");
			String title = br.readLine();
			System.out.print("책저자:");
			String author = br.readLine();
			System.out.print("출판사:");
			String company = br.readLine();

			ExcelVO vo = new ExcelVO(title, author, company);
			getIsbnImage(vo);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void getIsbnImage(ExcelVO vo) {

		try {
			// 요청 url과 요청변수
			String openApi = "https://openapi.naver.com/v1/search/book_adv.xml?d_titl="
					+ URLEncoder.encode(vo.getTitle(), "UTF-8") + "&d_auth="
					+ URLEncoder.encode(vo.getAuthor(), "UTF-8") + "&d_publ="
					+ URLEncoder.encode(vo.getCompany(), "UTF-8");
			URL url = new URL(openApi);//url형식으로 변환

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", "M1QZZ7e6rzruKFr8n4mD");
			con.setRequestProperty("X-Naver-Client-Secret", "IwWpn8pHhR");
			int responseCode = con.getResponseCode();

			BufferedReader br1 = null;
			if (responseCode == 200) {
				br1 = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			} else {
				br1 = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}

			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = br1.readLine()) != null) {

				response.append(inputLine);

			}
			br1.close();
			// System.out.println(response.toString());

			Document doc = Jsoup.parse(response.toString());
			System.out.println("doc : " + doc.toString());
			Element total = doc.select("total").first();
			System.out.println(total.text());
			// -----------------------------------------------------------------------
			if (!(total.text().equals("0"))) {
				Element isbn = doc.select("isbn").first();
				String isbnStr = isbn.text();
				System.out.println(isbnStr); // 9791158464035
				String isbn_find = isbnStr.split(" ")[0]; // [0] 또는 [1]공백으로 자른뒤 첫번째(9791158464035) isbn만 가져오기
				vo.setIsbn(isbn_find); // (International Standard Book Number, ISBN)
				// -------------------------------------
				String imgDoc = doc.toString();
				String imgTag = imgDoc.substring(imgDoc.indexOf("<img>") + 5); // <img>테그 5글자 뒤부터 가져옴
				String imgURL = imgTag.substring(0, imgTag.indexOf("<author>")); // 처음부터 ?전까지 잘라서
																					
				System.out.println("imgURL : " + imgURL);// 가져옴(https://bookthumb-phinf.pstatic.net/cover/160/313/16031391.jpg)
				String fileName = imgURL.substring(imgURL.lastIndexOf("/") + 13);// 마지막 /부터 끝까지
				System.out.println("fileName : " + fileName);
				vo.setImgurl(fileName);
				System.out.println(vo);
				
			}else {
				System.out.println("검색데이터가 없습니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
