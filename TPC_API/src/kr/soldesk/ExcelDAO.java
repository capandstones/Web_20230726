package kr.soldesk;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.*;

public class ExcelDAO {

	private List<ExcelVO> list;
	private HSSFWorkbook wb;
	
	public ExcelDAO() {
		list = new ArrayList<ExcelVO>();//책정보
		wb = new HSSFWorkbook();//워크북 생성
	}

}
