package Project;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ViewTerm {

    public static void main(String[] args) {

        // CẤU HÌNH SELENIUM & CHROME DRIVER
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell Pre\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-infobars");
        options.addArguments("--remote-debugging-port=9222");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--allow-insecure-localhost");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // 1️⃣ ĐĂNG NHẬP
            driver.get("https://cntttest.vanlanguni.edu.vn:18081/Phancong02/");
            driver.manage().window().maximize();
            System.out.println("Đã mở trang đăng nhập");

            WebElement dangNhapBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Đăng nhập']")));
            dangNhapBtn.click();
            System.out.println("Người dùng đã nhấn 'Đăng nhập'");

            // 2️⃣ CHUYỂN HƯỚNG ĐẾN MICROSOFT LOGIN
            wait.until(ExpectedConditions.urlContains("login.microsoftonline.com"));
            System.out.println("Chuyển hướng đến Microsoft Login...");

            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")));
            emailInput.sendKeys("hieu.2274802010258@vanlanguni.vn");
            emailInput.sendKeys(Keys.RETURN);
            System.out.println("Đã nhập email");

            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']")));
            passwordInput.sendKeys("VLU29052004");
            passwordInput.sendKeys(Keys.RETURN);
            System.out.println("Đã nhập mật khẩu");

            // 3️⃣ CHỜ ĐĂNG NHẬP THÀNH CÔNG
            wait.until(ExpectedConditions.urlContains("Phancong02"));
            System.out.println("Đăng nhập thành công vào hệ thống Van Lang!");

            // 4️⃣ NHẤP VÀO "HỌC KỲ"
            WebElement hocKyTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/Phancong02/Term']")));
            hocKyTab.click();
            System.out.println("Đã vào trang 'Học kỳ'");

            // 5️⃣ TÌM KIẾM "951" TRONG HỌC KỲ
            WebElement searchTermInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-controls='tblTerm']")));
            searchTermInput.sendKeys("951");
            System.out.println("Đã nhập từ khóa tìm kiếm '951'");

            Thread.sleep(2000); // Chờ kết quả cập nhật

            List<WebElement> termResults = driver.findElements(By.cssSelector("#tblTerm tbody tr"));
            System.out.println("Số kết quả tìm thấy cho '951': " + termResults.size());

            // 6️⃣ CHUYỂN SANG "NGÀNH"
            WebElement nganhTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/Phancong02/Major']")));
            nganhTab.click();
            System.out.println("Đã vào trang 'Ngành'");

            // 7️⃣ TÌM KIẾM "CNTT" TRONG NGÀNH
            WebElement searchMajorInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-controls='tblMajor']")));
            searchMajorInput.sendKeys("CNTT");
            System.out.println("Đã nhập từ khóa tìm kiếm 'CNTT'");

            Thread.sleep(2000); // Chờ kết quả cập nhật

            List<WebElement> majorResults = driver.findElements(By.cssSelector("#tblMajor tbody tr"));
            System.out.println("Số kết quả tìm thấy cho 'CNTT': " + majorResults.size());

        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        } finally {
            // 8️⃣ ĐÓNG TRÌNH DUYỆT
            driver.quit();
            System.out.println("Đã đóng trình duyệt");
        }
    }
}
