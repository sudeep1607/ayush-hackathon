package com.fts.utils;

/**
 * 
 */

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

/**
 * @author keshab.g
 */
public class ExcelUtils
{

    private static final Log s_log = LogFactory.getLog(ExcelUtils.class);

    /**
     * @param sheetName
     * @return
     */

    public static CellStyle getCellStyle(HSSFWorkbook workbook, boolean isBold, int boldWeight)
    {
        Font headerFont = workbook.createFont();
        headerFont.setFontName(HSSFFont.FONT_ARIAL);

        headerFont.setFontHeightInPoints((short) boldWeight);
        if (isBold)
        {
            headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        }
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(headerFont);
        return cellStyle;
    }

    /**
     * renders headers data and remaining rows data from records in excel sheet rows
     * 
     * @param workbook
     * @param sheet
     * @param headers
     * @param records
     * @return
     */
    @SuppressWarnings({
            "deprecation", "unused"
    })
    public static HSSFSheet populateDataInSheet(HSSFWorkbook workbook, HSSFSheet sheet, List<String> headers, List<List<String>> records)
    {
        try
        {
            int sheetRowNumber = 0;
            HSSFRow rowHeader = sheet.createRow(sheetRowNumber);
            sheet.setDefaultColumnWidth((short) 15);

            CellStyle headerCellStyle = getCellStyle(workbook, true, 10);
            CellStyle bodyCellStyle = getCellStyle(workbook, false, 10);

            if (s_log.isDebugEnabled())
            {
                s_log.debug("headerCellStyle : " + headerCellStyle);
                s_log.debug("bodyCellStyle : " + bodyCellStyle);
            }

            /**
             * populating headers in excel sheet first row
             */
            for (int i = 0; i < headers.size(); i++)
            {
                Cell cell = rowHeader.createCell(i);
                cell.setCellStyle(headerCellStyle);
                cell.setCellValue(headers.get(i));
            }

            /**
             * populating body rows in excel sheet
             */
            for (int i = 0; i < records.size(); i++)
            {
                HSSFRow row = sheet.createRow(++sheetRowNumber);
                List<String> record = records.get(i);
                for (int j = 0; j < record.size(); j++)
                {
                    Cell cell = row.createCell(j);
                    cell.setCellStyle(bodyCellStyle);
                    cell.setCellValue(record.get(j));
                    try{
                        String[] val = record.get(j).split(":");
                    }catch (Exception e) {
                        
                    }
                }
            }
            return sheet;
        }
        catch (Exception e)
        {
            s_log.error(e.getMessage(), e);
        }
        return null;
    }

}
