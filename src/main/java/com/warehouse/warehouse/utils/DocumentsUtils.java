package com.warehouse.warehouse.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.warehouse.warehouse.repository.entity.Product;
import com.warehouse.warehouse.service.ProductServiceImpl;
import com.warehouse.warehouse.service.WarehouseServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class DocumentsUtils {

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    WarehouseServiceImpl warehouseService;

    public List<Product> getProductsFromExcelFile(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        Workbook workbook = new HSSFWorkbook(inputStream);
        List<Product> productList = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Product product = new Product();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                int colIdx = cell.getColumnIndex();
                switch (colIdx) {
                    case 0:
                        product.setArticle((int) cell.getNumericCellValue());
                        break;
                    case 1:
                        product.setProductName(cell.getStringCellValue());
                        break;
                    case 2:
                        product.setLastPurchasePrice(BigDecimal.valueOf(cell.getNumericCellValue()));
                        break;
                    case 3:
                        product.setLastSellPrice(BigDecimal.valueOf(cell.getNumericCellValue()));
                    case 4:
                        product.setQuantity((int) cell.getNumericCellValue());
                        break;
                    case 5:
                        product.setWarehouse(warehouseService.getWarehouseById((long)cell.getNumericCellValue()).get());
                        break;
                }
            }
            productList.add(product);
        }
        return productList;
    }

    public void getProductsFromCSVFile(MultipartFile file) throws IOException, CsvException {
        InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream());
        CSVReader reader = new CSVReader(inputStreamReader);
        List<String[]> rows = reader.readAll();
        rows.stream().forEach(e -> {
            for (int i = 0; i <= e.length; i++) {
                String[] row = e[i].split(",");
                productService.saveProduct(new Product(
                                Integer.parseInt(row[0]),
                                row[1],
                                new BigDecimal(row[2]),
                                new BigDecimal(row[3]),
                                Integer.parseInt(row[4])
                        )
                );
            }
        });
    }

    public boolean downloadProductsListInExcel() throws IOException {
        final String FILE_NAME = "C:\\Users\\79639\\Desktop\\Products.xls";
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Products");
        var productsList = productService.findAll();
        int i = 0;
        for (Product product : productsList) {
            Row row = sheet.createRow(i);
            Cell article = row.createCell(0);
            article.setCellValue(product.getArticle());
            Cell productName = row.createCell(1);
            productName.setCellValue(product.getProductName());
            Cell purchasePrice = row.createCell(2);
            purchasePrice.setCellValue(product.getLastPurchasePrice().doubleValue());
            Cell sellPrice = row.createCell(3);
            sellPrice.setCellValue(product.getLastSellPrice().doubleValue());
            Cell quantity = row.createCell(4);
            quantity.setCellValue(product.getQuantity());
            ++i;
        }
        workbook.write(new FileOutputStream(FILE_NAME));
        workbook.close();
        return true;
    }
}
