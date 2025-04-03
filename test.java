package Project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class test {
    public static void main(String[] args) throws InterruptedException {
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
            

            // Nhấn vào mục "Học kỳ và Ngành"
            WebElement hocKyVaNganh = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/Phancong02/Term']//span[contains(text(),'Học kỳ và ngành')]")));
            hocKyVaNganh.click();

            // Tìm và nhấn vào ô tìm kiếm
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='search' and @class='form-control' and @placeholder='Nhập tìm kiếm...']")));
            searchBox.click();

            // Nhập "219" vào ô tìm kiếm
            searchBox.sendKeys("219");
            
            
            // 1. Ấn vào nút "Chỉnh sửa"
            WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@class, 'editRow') and @title='Chỉnh sửa']")));
            editButton.click();

            // 2. Ấn vào dropdown "Năm bắt đầu"
            WebElement startYearDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("select2-start_year-container")));
            startYearDropdown.click();

            // 3. Tìm kiếm và chọn năm 2025
            WebElement startYearOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@id, 'select2-start_year-result') and text()='2025']")));
            startYearOption.click();

            // 4. Ấn vào dropdown "Năm kết thúc"
            WebElement endYearDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("select2-end_year-container")));
            endYearDropdown.click();

            // 5. Tìm kiếm và chọn năm 2026
            WebElement endYearOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@id, 'select2-end_year-result') and text()='2026']")));
            endYearOption.click();

            // 6. Ấn dấu cộng 5 lần ở phần "Tuần bắt đầu"
            WebElement plusButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'bootstrap-touchspin-up')]")));
            for (int i = 0; i < 5; i++) {
                plusButton.click();
                Thread.sleep(500); // Chờ một chút để giao diện cập nhật
            }

            // 7. Ấn nút "Lưu"
            WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'btn-primary') and text()='Lưu']")));
            saveButton.click();

            Thread.sleep(5000);
     
            
        } finally {
            driver.quit(); // Đảm bảo đóng trình duyệt sau khi hoàn tất
        }
    }
}
