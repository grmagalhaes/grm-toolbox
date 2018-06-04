package sofia.toolbox.msoffice;


import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class Excel {

	public static short ALIGN_CENTER = XSSFCellStyle.ALIGN_CENTER;
	public static short ALIGN_CENTER_SELECTION = XSSFCellStyle.ALIGN_CENTER_SELECTION;
	public static short ALIGN_FILL = XSSFCellStyle.ALIGN_FILL;
	public static short ALIGN_GENERAL = XSSFCellStyle.ALIGN_GENERAL;	
	public static short ALIGN_JUSTIFY = XSSFCellStyle.ALIGN_JUSTIFY;
	public static short ALIGN_LEFT = XSSFCellStyle.ALIGN_LEFT;
	public static short ALIGN_RIGHT = XSSFCellStyle.ALIGN_RIGHT;	

    private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	private int countRow = 1;
	private int countCol = 0;
	
	
	public XSSFWorkbook getCurrentWorkbook() {
		return this.workbook;
	}
	
	public XSSFSheet getCurrentSheet() {
		return this.sheet;
	}
	
	public void setWorkbook(XSSFWorkbook workbook) {
		this.workbook = workbook;
	}
	
	public void setSheet(XSSFSheet sheet) {
		this.sheet = sheet;
	}	
	
	public XSSFWorkbook createWorkbook() {

		this.workbook = new XSSFWorkbook();
		
		return this.workbook;
	}
	
	public XSSFWorkbook createWorkbook(String name, String[] titles, int[] colSize) {

		this.workbook = new XSSFWorkbook();
		
		this.sheet = this.workbook.createSheet(name);
		
		createHeader(getStyle(XSSFCellStyle.ALIGN_CENTER), titles, colSize);
		
		XSSFRow rowTitle = this.sheet.createRow(0);
		
		if (titles != null) {
			for (int i = 0; i < titles.length; i++) {
				XSSFCell cell = rowTitle.createCell(i);
				cell.setCellValue(new XSSFRichTextString(titles[i]));
				cell.setCellStyle(getStyle(XSSFCellStyle.ALIGN_CENTER));
				if (colSize != null && i < colSize.length) {
					sheet.setColumnWidth(i, colSize[i]);
				}
			}
		}		
		
		
		return this.workbook;
	}
	
	
	public XSSFSheet createSheet(String name) {

		this.sheet = this.workbook.createSheet(name);
	
		return this.sheet;
	}	
	
	private void createHeader(XSSFCellStyle style, String[] titles, int[] colSize) {
		
		XSSFRow rowTitle = this.sheet.createRow(0);
						
		if (titles != null) {
			for (int i = 0; i < titles.length; i++) {
				XSSFCell cell = rowTitle.createCell(i);
				cell.setCellValue(new XSSFRichTextString(titles[i]));
				cell.setCellStyle(style);
				if (colSize != null && i < colSize.length) {
					sheet.setColumnWidth(i, colSize[i]);
				}
			}
		}
	}

	public void createRow() {

		this.sheet.createRow(countRow++);
		this.countCol = 0;
	}
	
	public void setCellValue(int col, int row, String text, short align) {

		XSSFRow rownum = this.sheet.createRow(row);
		XSSFCell cell = rownum.createCell(col);
		cell.setCellValue( new XSSFRichTextString(text));
		cell.setCellStyle(this.getStyle(align));
		cell.setCellType( XSSFCell.CELL_TYPE_STRING );
	}	

	public void createColumn(String text, short align) {

		XSSFCell cell = this.sheet.getRow(countRow-1).createCell(countCol++);
		cell.setCellValue( new XSSFRichTextString(text));
		cell.setCellStyle(this.getStyle(align));
		cell.setCellType( XSSFCell.CELL_TYPE_STRING );
	}
	
	public XSSFCellStyle getStyle(short align) {
		
		XSSFCellStyle style = this.workbook.createCellStyle();
		style.setAlignment(align);
		
		/*style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(XSSFColor.BLACK.index);
		style.setBorderTop(XSSFCellStyle.BORDER_THIN); 
        style.setTopBorderColor(XSSFColor.BLACK.index);
		style.setBorderLeft(XSSFCellStyle.BORDER_THIN); 
        style.setLeftBorderColor(XSSFColor.BLACK.index);
		style.setBorderRight(XSSFCellStyle.BORDER_THIN); 
        style.setRightBorderColor(XSSFColor.BLACK.index);*/	
        
        return style;
	}
	
	public String getStringCellValue(int col, int row) {

		XSSFCell cell = this.sheet.getRow(row).getCell(col);
		return cell.getStringCellValue();
	}
	
	public double getNumericCellValue(int col, int row) {

		XSSFCell cell = this.sheet.getRow(row).getCell(col);
		return cell.getNumericCellValue();
	}	
		
    public void save(String filename) throws IOException {
        File file = new File(filename);
        OutputStream outputStream = new FileOutputStream(file);
        try {
            this.workbook.write(outputStream);
            outputStream.flush();
        } catch (IOException e) {
            throw e;
        } finally {
            outputStream.close();
        }
    }

	
}
