package sofia.toolbox.msoffice;


import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class Excel97 {

	public static short ALIGN_CENTER = HSSFCellStyle.ALIGN_CENTER;
	public static short ALIGN_CENTER_SELECTION = HSSFCellStyle.ALIGN_CENTER_SELECTION;
	public static short ALIGN_FILL = HSSFCellStyle.ALIGN_FILL;
	public static short ALIGN_GENERAL = HSSFCellStyle.ALIGN_GENERAL;	
	public static short ALIGN_JUSTIFY = HSSFCellStyle.ALIGN_JUSTIFY;
	public static short ALIGN_LEFT = HSSFCellStyle.ALIGN_LEFT;
	public static short ALIGN_RIGHT = HSSFCellStyle.ALIGN_RIGHT;	

    private HSSFWorkbook workbook;
	private HSSFSheet sheet;

	private int countRow = 1;
	private int countCol = 0;
	
	
	public HSSFWorkbook getCurrentWorkbook() {
		return this.workbook;
	}
	
	public HSSFSheet getCurrentSheet() {
		return this.sheet;
	}
	
	public void setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}
	
	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}	
	
	public HSSFWorkbook createWorkbook() {

		this.workbook = new HSSFWorkbook();
		
		return this.workbook;
	}
	
	public HSSFWorkbook createWorkbook(String name, String[] titles, int[] colSize) {

		this.workbook = new HSSFWorkbook();
		
		this.sheet = this.workbook.createSheet(name);
		
		createHeader(getStyle(HSSFCellStyle.ALIGN_CENTER), titles, colSize);
		
		HSSFRow rowTitle = this.sheet.createRow(0);
		
		if (titles != null) {
			for (int i = 0; i < titles.length; i++) {
				HSSFCell cell = rowTitle.createCell(i);
				cell.setCellValue(new HSSFRichTextString(titles[i]));
				cell.setCellStyle(getStyle(HSSFCellStyle.ALIGN_CENTER));
				if (colSize != null && i < colSize.length) {
					sheet.setColumnWidth(i, colSize[i]);
				}
			}
		}		
		
		
		return this.workbook;
	}
	
	
	public HSSFSheet createSheet(String name) {

		this.sheet = this.workbook.createSheet(name);
	
		return this.sheet;
	}	
	
	private void createHeader(HSSFCellStyle style, String[] titles, int[] colSize) {
		
		HSSFRow rowTitle = this.sheet.createRow(0);
						
		if (titles != null) {
			for (int i = 0; i < titles.length; i++) {
				HSSFCell cell = rowTitle.createCell(i);
				cell.setCellValue(new HSSFRichTextString(titles[i]));
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

		HSSFRow rownum = this.sheet.createRow(row);
		HSSFCell cell = rownum.createCell(col);
		cell.setCellValue( new HSSFRichTextString(text));
		cell.setCellStyle(this.getStyle(align));
		cell.setCellType( HSSFCell.CELL_TYPE_STRING );
	}	

	public void createColumn(String text, short align) {

		HSSFCell cell = this.sheet.getRow(countRow-1).createCell(countCol++);
		cell.setCellValue( new HSSFRichTextString(text));
		cell.setCellStyle(this.getStyle(align));
		cell.setCellType( HSSFCell.CELL_TYPE_STRING );
	}
	
	public HSSFCellStyle getStyle(short align) {
		
		HSSFCellStyle style = this.workbook.createCellStyle();
		style.setAlignment(align);
		
		/*style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN); 
        style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN); 
        style.setLeftBorderColor(HSSFColor.BLACK.index);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN); 
        style.setRightBorderColor(HSSFColor.BLACK.index);*/	
        
        return style;
	}
	
	public String getStringCellValue(int col, int row) {

		HSSFCell cell = this.sheet.getRow(row).getCell(col);
		return cell.getStringCellValue();
	}
	
	public double getNumericCellValue(int col, int row) {

		HSSFCell cell = this.sheet.getRow(row).getCell(col);
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
