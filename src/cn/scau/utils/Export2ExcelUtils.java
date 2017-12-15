package cn.scau.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 导出excel的工具类,借鉴于博客园--钟正123
 * https://www.cnblogs.com/zhongzheng123
 * @author Unicorn
 *
 */
public final class Export2ExcelUtils {
	/**
	 * 
	 * @param heads excel的表格头部
	 * @param names json的键值
	 * @param jsonArray json数据
	 */
	public static void Export2Excel(String[] heads,String[] names,JSONArray jsonArray) {

        HSSFWorkbook workbook = new HSSFWorkbook();// 创建一个Excel文件

        HSSFSheet sheet = workbook.createSheet();// 创建一个Excel的Sheet

        HSSFCellStyle style = workbook.createCellStyle();

        style.setFillBackgroundColor(HSSFColor.BLUE_GREY.index);

        HSSFRow titleRow = sheet.createRow(0);
        
        for(int i=0;i<heads.length;i++){
            titleRow.createCell(i).setCellValue(heads[i]);
        }
        
        titleRow.setRowStyle(style);
    
        if (jsonArray.size() > 0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                HSSFRow row = sheet.createRow(i + 1);
                JSONObject json = jsonArray.getJSONObject(i); // 遍历 jsonarray
                for(int j=0;j<names.length;j++){
                    row.createCell(j).setCellValue(json.get(names[j]).toString());
                }                                        
                
            }
        }
        
        try {
			FileOutputStream fos = new FileOutputStream("F:\\RapidClipseWorkSpace\\SchoolManagementSystem\\WebContent\\files\\" + UUIDUtil.getFullUUID() + ".xls");
			workbook.write(fos);
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
    }
	
}
