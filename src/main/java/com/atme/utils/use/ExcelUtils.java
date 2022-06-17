package com.atme.utils.use;

import com.atme.utils.model.EmailList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author amao
 * @create 2022-06-16-22:14
 */
public class ExcelUtils {

    public static List<EmailList> getSendEmailList() throws IOException {
        //获取工作簿
//        String filePath = new File(ResourceUtils.getURL("classpath:").getPath()).getParentFile().getParentFile().getParent() + "volunteer.xlsx";
//        //服务器上filePath为：file:/usr/local/zyd  截取后面的 jar包所在路径
//        //本地则不用截取
//        String path = filePath.substring(5, filePath.length());
//        XSSFWorkbook book = new XSSFWorkbook(path);
        XSSFWorkbook book = new XSSFWorkbook("/Users/user567/Documents/volunteeropt.xlsx");
        //获取工作表
        XSSFSheet sheet = book.getSheetAt(0);
        List<EmailList> emailList = new ArrayList<>();
        int lastRowNum = sheet.getLastRowNum();
        System.out.println("最后一行：" + lastRowNum);
        for (int i = 1; i <= lastRowNum; i++) {
            //获取单元格
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                List<String> list = new ArrayList<>();
                for (Cell cell : row) {
                    if (cell != null && !"".equals(cell)) {
                        //单元格都转换成String类型
                        cell.setCellType(CellType.STRING);
                        String cellValue = cell.getStringCellValue();
                        list.add(cellValue);
                    }
                }
                if (list.size() > 0) {
                    EmailList mlist = new EmailList(Long.valueOf(list.get(0)), list.get(2));
                    emailList.add(mlist);
                }
            }
        }
        book.close();
        return emailList;
    }

    public static List<String> getSchoolList(String url) throws IOException {
        //获取工作簿
        XSSFWorkbook book = new XSSFWorkbook(url);
        //获取工作表
        XSSFSheet sheet = book.getSheetAt(0);
        List<String> schoolList = new ArrayList<>();
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 1; i <= lastRowNum; i++) {
            //获取单元格
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                List<String> list = new ArrayList<>();
                for (Cell cell : row) {
                    if (cell != null && !"".equals(cell)) {
                        //此处是把单元格都转换成String类型
                        cell.setCellType(CellType.STRING);
                        String cellValue = cell.getStringCellValue();
                        list.add(cellValue);
                    }
                }
                if (list.size() > 0) {
                    schoolList.add(list.get(0));
                }
            }
        }
        book.close();
        return schoolList;
    }

}
