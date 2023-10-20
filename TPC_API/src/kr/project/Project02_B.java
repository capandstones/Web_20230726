package kr.project;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import kr.soldesk.DownloadBroker;

public class Project02_B {

	public static void main(String[] args) {

		String url = "https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BosyMatter?qt_ty=QT1";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("[입력->년(yyyy)-월(mm)-일(dd)] : ");
			String bible = br.readLine();
			url = url + "&Base_de=" + bible + "&bibleType=1";// Base_de:날짜 bibleType : = 기본1
			System.out.println("===============================================");
			Document doc = Jsoup.connect(url).post();
			Element bible_text = doc.select(".bible_text").first();// 주제목
			System.out.println(bible_text.text());

			Element bible_info_box = doc.select(".bibleinfo_box").first();
			System.out.println(bible_info_box.text());

			Elements liList = doc.select(".body_list > li");
			for (Element li : liList) {
				System.out.print(li.select(".num").first().text() + ":");
				System.out.println(li.select(".info").first().text());
			}
			/*
			 * // 리소스(이미지, 소리, 영상)//*[@id="video"]/source Element tag =
			 * doc.select("source").first(); String dPath = tag.attr("src").trim();
			 * System.out.println(dPath); //
			 * http://meditation.su.or.kr/meditation_mp3/2019/20191010.mp3 String fileName =
			 * dPath.substring(dPath.lastIndexOf("/") + 1); System.out.println(fileName);
			 */

			Element tag = doc.select(".img > img").first();
			String dPath = "https://sum.su.or.kr:8888" + tag.attr("src").trim();
			System.out.println(dPath);
			String fileName = dPath.substring(dPath.lastIndexOf("/") + 1);

			Runnable r = new DownloadBroker(dPath, fileName);
			Thread dLoad = new Thread(r);// 스레드 구현
			dLoad.start();
			for (int i = 0; i < 10; i++) {
				try {
					Thread.sleep(1000); // 1초
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.print("" + (i + 1));
			}
			System.out.println();
			System.out.println("===============================");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
