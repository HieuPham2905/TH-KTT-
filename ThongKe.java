package Project;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ThongKe {
    public static void main(String[] args) {
        // Thiết lập đường dẫn ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        
        // Khởi tạo WebDriver
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, 10); 

        try {
            driver.manage().window().maximize();
            driver.get("https://cntttest.vanlanguni.edu.vn:18081/Phancong02/");
            
            // Xử lý cảnh báo bảo mật nếu có
            try {
                WebElement advancedButton = driver.findElement(By.id("details-button"));
                advancedButton.click();
                WebElement proceedLink = driver.findElement(By.id("proceed-link"));
                proceedLink.click();
            } catch (Exception ignored) {}

            // Đăng nhập OpenID Connect
            WebElement openIdLoginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("OpenIdConnect")));
            openIdLoginButton.click();

            // Nhập email
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")));
            emailField.sendKeys("hoang.2274802010279@vanlanguni.vn");
            driver.findElement(By.xpath("//input[@type='submit'][@value='Next']")).click();

            // Nhập mật khẩu
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']")));
            passwordField.sendKeys("HuyHoang2911@");
            driver.findElement(By.xpath("//input[@type='submit'][@value='Sign in']")).click();

            // Nhấn "Yes" nếu có
            try {
                WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='submit'][@value='Yes']")));
                yesButton.click();
            } catch (Exception ignored) {}

            // Điều hướng đến "Số giờ giảng viên"
            WebElement thongKeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='menu-title text-truncate' and contains(text(),'Thời khoá biểu')]")));
            thongKeButton.click();
            WebElement soGioGiangVien = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'Số giờ giảng viên')]")));
            soGioGiangVien.click();

            // Chọn giá trị dropdown
            selectDropdownValue(driver, wait, "select2-unit-container", "Học kỳ");
            selectDropdownValue(driver, wait, "select2-term-container", "219");
            selectDropdownValue(driver, wait, "select2-major-container", "Information Technology IT");
            selectDropdownValue(driver, wait, "select2-lecturerType-container", "Tất cả");

            // Chuyển sang tab "Table"
            WebElement tableTab = wait.until(ExpectedConditions.elementToBeClickable(By.id("table-tab")));
            tableTab.click();

            // Nhập từ khóa tìm kiếm
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='search' and contains(@placeholder, 'Nhập tìm kiếm')]")));
            searchBox.sendKeys("2274802010279");

            // Nhấn vào nút dấu cộng "+"
            WebElement buttonPlus = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'viewInfo') and contains(@class, 'btn-success')]")));
            buttonPlus.click();

            // Lấy danh sách học phần
            List<WebElement> rows = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//table[@class='table table-sm']/tbody/tr")));

            System.out.println("===== Danh sách học phần =====");
            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (cells.size() >= 6) {
                    String maHP = cells.get(0).getText();
                    String tenHP = cells.get(1).getText();
                    String nganh = cells.get(2).getText();
                    String soTC = cells.get(3).getText();
                    String soLop = cells.get(4).getText();
                    String soGioGiang = cells.get(5).getText();
                    
                    System.out.printf("%-15s | %-40s | %-30s | %-5s | %-10s | %-10s %n",
                        maHP, tenHP, nganh, soTC, soLop, soGioGiang);
                }
            }

            // Giữ trình duyệt mở để kiểm tra kết quả
            System.out.println("Kiểm thử hoàn tất. Nhấn Enter để đóng trình duyệt.");
            System.in.read();

        } catch (Exception e) {
            System.out.println("Lỗi trong quá trình kiểm thử: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }

    // Hàm chọn giá trị trong dropdown có thể nhập trước (Select2)
    private static void selectDropdownValue(WebDriver driver, WebDriverWait wait, String containerId, String value) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id(containerId)));
            dropdown.click();
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='select2-search__field']")));
            searchBox.sendKeys(value);
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@class, 'select2-results__option') and text()='" + value + "']")));
            option.click();
        } catch (Exception e) {
            System.out.println("✘ Không tìm thấy hoặc không thể chọn giá trị: " + value);
        }
    }
}
