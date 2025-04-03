package Project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddUserToTimeTable {
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
            emailField.sendKeys("EMAIL VLU");
            driver.findElement(By.xpath("//input[@type='submit'][@value='Next']")).click();

            // Nhập mật khẩu
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']")));
            passwordField.sendKeys("PASS EMAIL");
            driver.findElement(By.xpath("//input[@type='submit'][@value='Sign in']")).click();

            // Nhấn "Yes" nếu có
            try {
                WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='submit'][@value='Yes']")));
                yesButton.click();
            } catch (Exception ignored) {}
            

            // Điều hướng đến "Thời khoá biểu"
            WebElement ThoiKhoaBieu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='menu-title text-truncate' and contains(text(),'Thời khoá biểu')]")));
            ThoiKhoaBieu.click();
            WebElement PhanCong = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='menu-item text-truncate' and contains(text(),'Phân công')]")));
            PhanCong.click();
            
            Thread.sleep(5000);
            
            // Nhấn vào dropdown để mở danh sách lựa chọn
            WebElement dropdownHocKy = wait.until(ExpectedConditions.elementToBeClickable(By.id("select2-term-container")));
            dropdownHocKy.click();

            // Nhập giá trị vào ô tìm kiếm
            WebElement searchBoxHocKy = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='select2-search__field']")));
            searchBoxHocKy.sendKeys("219");

            // Chọn giá trị 219 từ danh sách kết quả
            WebElement optionHocKy = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@id, 'select2-term-result') and text()='219']")));
            optionHocKy.click();
            
            // Nhấn vào dropdown để mở danh sách lựa chọn
            WebElement dropdownNganh = wait.until(ExpectedConditions.elementToBeClickable(By.id("select2-major-container")));
            dropdownNganh.click();

            // Nhập giá trị vào ô tìm kiếm
            WebElement searchBoxNganh = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='select2-search__field']")));
            searchBoxNganh.sendKeys("Information Technology IT");

            // Chọn giá trị từ danh sách kết quả
            WebElement optionNganh = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@id, 'select2-major-result') and text()='Information Technology IT']")));
            optionNganh.click();

            Thread.sleep(5000);
            	
            // Click 3 lần vào nút popover
            WebElement popoverButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-bs-toggle='popover']")));
            for (int i = 0; i < 3; i++) {
                popoverButton.click();
                try {
                    Thread.sleep(3000); // Đợi 0.5s giữa các lần click để đảm bảo popover phản hồi
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            Thread.sleep(3000);
            
            // Nhấn vào dropdown để mở danh sách lựa chọn
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class='select2-selection__placeholder' and text()='Chưa phân công']")));
            dropdown.click();

            // Nhấn vào ô tìm kiếm
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='select2-search__field']")));
            searchBox.sendKeys("HoangCT");

            // Chọn giá trị "HoangCT" từ danh sách kết quả
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@class, 'select2-results__option') and text()='HoangCT']")));
            option.click();
            
            // Nhấn vào nút "Assign"
            WebElement assignButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-primary waves-effect waves-float waves-light btn-icon btn-assign ms-25']")));
            assignButton.click();


            
        } finally {
            //driver.quit(); // Đảm bảo đóng trình duyệt sau khi hoàn tất
        }
    }
}
