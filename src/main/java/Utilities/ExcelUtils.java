package Utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	
	private Workbook workbook;
    private Sheet sheet;
    private String filePath;

    // ─────────────────────────────────────────────
    // CONSTRUCTORS
    // ─────────────────────────────────────────────

    /**
     * Open an existing Excel file (.xlsx or .xls).
     */
    public ExcelUtils(String filePath) throws IOException {
        this.filePath = filePath;
        try (FileInputStream fis = new FileInputStream(filePath)) {
            workbook = filePath.endsWith(".xlsx")
                    ? new XSSFWorkbook(fis)
                    : new HSSFWorkbook(fis);
        }
    }

    /**
     * Create a new Excel file in memory (call flush() to save).
     *
     * @param filePath path where file will be saved
     * @param useXlsx  true → .xlsx | false → .xls
     */
    public ExcelUtils(String filePath, boolean useXlsx) {
        this.filePath = filePath;
        workbook = useXlsx ? new XSSFWorkbook() : new HSSFWorkbook();
    }

    // ─────────────────────────────────────────────
    // SHEET MANAGEMENT
    // ─────────────────────────────────────────────

    /**
     * Select a sheet by name to work on.
     */
    public ExcelUtils useSheet(String sheetName) {
        sheet = workbook.getSheet(sheetName);
        if (sheet == null)
            throw new IllegalArgumentException("Sheet not found: " + sheetName);
        return this;
    }

    /**
     * Select a sheet by zero-based index.
     */
    public ExcelUtils useSheet(int index) {
        sheet = workbook.getSheetAt(index);
        return this;
    }

    /**
     * Create a new sheet and switch context to it.
     */
    public ExcelUtils createSheet(String sheetName) {
        sheet = workbook.createSheet(sheetName);
        return this;
    }

    /**
     * Get all sheet names in the workbook.
     */
    public List<String> getSheetNames() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++)
            names.add(workbook.getSheetName(i));
        return names;
    }

    // ─────────────────────────────────────────────
    // READ OPERATIONS
    // ─────────────────────────────────────────────

    /**
     * Read a single cell value as String.
     *
     * @param rowIndex 0-based row index
     * @param colIndex 0-based column index
     */
    public String getCellValue(int rowIndex, int colIndex) {
        ensureSheet();
        Row row = sheet.getRow(rowIndex);
        if (row == null) return "";
        Cell cell = row.getCell(colIndex);
        return cellToString(cell);
    }

    /**
     * Read all values in a row as a List of Strings.
     */
    public List<String> getRowData(int rowIndex) {
        ensureSheet();
        List<String> rowData = new ArrayList<>();
        Row row = sheet.getRow(rowIndex);
        if (row == null) return rowData;
        for (Cell cell : row)
            rowData.add(cellToString(cell));
        return rowData;
    }

    /**
     * Read the entire sheet as List of rows (each row = List of Strings).
     */
    public List<List<String>> getAllData() {
        ensureSheet();
        List<List<String>> data = new ArrayList<>();
        for (Row row : sheet) {
            List<String> rowData = new ArrayList<>();
            for (Cell cell : row)
                rowData.add(cellToString(cell));
            data.add(rowData);
        }
        return data;
    }

    /**
     * Read sheet as List of Maps — first row is treated as headers.
     * Best suited for data-driven testing with named columns.
     *
     * Example map: { "username" -> "admin", "password" -> "admin123" }
     */
    public List<Map<String, String>> getDataAsMapList() {
        ensureSheet();
        List<Map<String, String>> result = new ArrayList<>();
        List<String> headers = getRowData(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            Map<String, String> rowMap = new LinkedHashMap<>();
            for (int j = 0; j < headers.size(); j++) {
                Cell cell = row.getCell(j);
                rowMap.put(headers.get(j), cellToString(cell));
            }
            result.add(rowMap);
        }
        return result;
    }

    /**
     * Find the first row index where a column matches the given value.
     * Returns -1 if not found.
     */
    public int findRowByValue(int colIndex, String searchValue) {
        ensureSheet();
        for (Row row : sheet) {
            Cell cell = row.getCell(colIndex);
            if (cell != null && cellToString(cell).equalsIgnoreCase(searchValue))
                return row.getRowNum();
        }
        return -1;
    }

    /**
     * Total number of rows with data (includes header row).
     */
    public int getRowCount() {
        ensureSheet();
        return sheet.getLastRowNum() + 1;
    }

    /**
     * Total number of columns in a given row.
     */
    public int getColCount(int rowIndex) {
        ensureSheet();
        Row row = sheet.getRow(rowIndex);
        return row == null ? 0 : row.getLastCellNum();
    }

    // ─────────────────────────────────────────────
    // WRITE OPERATIONS
    // ─────────────────────────────────────────────

    /**
     * Write a String value to a specific cell.
     * Creates row/cell if they don't exist.
     */
    public ExcelUtils setCellValue(int rowIndex, int colIndex, String value) {
        ensureSheet();
        getOrCreateCell(getOrCreateRow(rowIndex), colIndex).setCellValue(value);
        return this;
    }

    /**
     * Write a numeric value to a specific cell.
     */
    public ExcelUtils setCellValue(int rowIndex, int colIndex, double value) {
        ensureSheet();
        getOrCreateCell(getOrCreateRow(rowIndex), colIndex).setCellValue(value);
        return this;
    }

    /**
     * Write a boolean value to a specific cell.
     */
    public ExcelUtils setCellValue(int rowIndex, int colIndex, boolean value) {
        ensureSheet();
        getOrCreateCell(getOrCreateRow(rowIndex), colIndex).setCellValue(value);
        return this;
    }

    /**
     * Append a new row at the end of the sheet.
     */
    public ExcelUtils appendRow(List<String> values) {
        ensureSheet();
        int newRowIndex = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(newRowIndex);
        for (int i = 0; i < values.size(); i++)
            row.createCell(i).setCellValue(values.get(i));
        return this;
    }

    /**
     * Write header row (row 0) without any styling.
     */
    public ExcelUtils writeHeaders(List<String> headers) {
        return writeHeaders(headers, null);
    }

    /**
     * Write header row (row 0) with optional CellStyle (e.g. bold).
     */
    public ExcelUtils writeHeaders(List<String> headers, CellStyle style) {
        ensureSheet();
        Row row = getOrCreateRow(0);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = getOrCreateCell(row, i);
            cell.setCellValue(headers.get(i));
            if (style != null) cell.setCellStyle(style);
        }
        return this;
    }

    // ─────────────────────────────────────────────
    // UPDATE OPERATIONS
    // ─────────────────────────────────────────────

    /**
     * Search for a row where searchColIndex == searchValue,
     * then update targetColIndex to newValue.
     *
     * @return true if a matching row was found and updated
     */
    public boolean updateCell(int searchColIndex, String searchValue,
                              int targetColIndex, String newValue) {
        int rowIndex = findRowByValue(searchColIndex, searchValue);
        if (rowIndex == -1) return false;
        setCellValue(rowIndex, targetColIndex, newValue);
        return true;
    }

    /**
     * Overwrite an entire row at rowIndex with new values.
     */
    public ExcelUtils updateRow(int rowIndex, List<String> values) {
        ensureSheet();
        Row row = getOrCreateRow(rowIndex);
        for (int i = 0; i < values.size(); i++)
            getOrCreateCell(row, i).setCellValue(values.get(i));
        return this;
    }

    // ─────────────────────────────────────────────
    // DELETE OPERATIONS
    // ─────────────────────────────────────────────

    /**
     * Clear the content of a single cell (keeps the cell object).
     */
    public ExcelUtils clearCell(int rowIndex, int colIndex) {
        ensureSheet();
        Row row = sheet.getRow(rowIndex);
        if (row != null) {
            Cell cell = row.getCell(colIndex);
            if (cell != null) cell.setBlank();
        }
        return this;
    }

    /**
     * Delete an entire row and shift all rows below it upward.
     */
    public ExcelUtils deleteRow(int rowIndex) {
        ensureSheet();
        int lastRow = sheet.getLastRowNum();
        if (rowIndex >= 0 && rowIndex <= lastRow) {
            sheet.removeRow(sheet.getRow(rowIndex));
            if (rowIndex < lastRow)
                sheet.shiftRows(rowIndex + 1, lastRow, -1);
        }
        return this;
    }

    // ─────────────────────────────────────────────
    // STYLING
    // ─────────────────────────────────────────────

    /**
     * Create a bold CellStyle — pass to writeHeaders() or apply manually.
     */
    public CellStyle createBoldStyle() {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    /**
     * Apply a background color fill to a specific cell.
     *
     * @param color use IndexedColors.YELLOW.getIndex() etc.
     */
    public ExcelUtils setCellColor(int rowIndex, int colIndex, short color) {
        ensureSheet();
        Cell cell = getOrCreateCell(getOrCreateRow(rowIndex), colIndex);
        CellStyle style = workbook.createCellStyle();
        style.cloneStyleFrom(cell.getCellStyle());
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cell.setCellStyle(style);
        return this;
    }

    /**
     * Auto-fit column widths for the given number of columns.
     */
    public ExcelUtils autoSizeColumns(int colCount) {
        ensureSheet();
        for (int i = 0; i < colCount; i++)
            sheet.autoSizeColumn(i);
        return this;
    }

    // ─────────────────────────────────────────────
    // SAVE / CLOSE
    // ─────────────────────────────────────────────

    /**
     * Save the workbook to its original file path.
     * Must be called after all write/update operations.
     */
    public void flush() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }
    }

    /**
     * Save the workbook to a new file path (Save As).
     */
    public void flushAs(String newPath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(newPath)) {
            workbook.write(fos);
        }
        this.filePath = newPath;
    }

    /**
     * Close the workbook and release all resources.
     * Always call in a finally block or use try-with-resources.
     */
    public void close() throws IOException {
        if (workbook != null) workbook.close();
    }

    // ─────────────────────────────────────────────
    // PRIVATE HELPERS
    // ─────────────────────────────────────────────

    private void ensureSheet() {
        if (sheet == null)
            throw new IllegalStateException(
                "No sheet selected. Call useSheet() or createSheet() first.");
    }

    private Row getOrCreateRow(int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        return row != null ? row : sheet.createRow(rowIndex);
    }

    private Cell getOrCreateCell(Row row, int colIndex) {
        Cell cell = row.getCell(colIndex);
        return cell != null ? cell : row.createCell(colIndex);
    }

    private String cellToString(Cell cell) {
        if (cell == null) return "";
        return new DataFormatter().formatCellValue(cell).trim();
    }
}
	
	

