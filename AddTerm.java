package Project;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AddTerm {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell Pre\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-infobars");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // 1️⃣ MỞ TRANG WEB & ĐĂNG NHẬP
            driver.get("https://cntttest.vanlanguni.edu.vn:18081/Phancong02/");
            driver.manage().window().maximize();
            System.out.println("Đã mở trang đăng nhập");

            WebElement dangNhapBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Đăng nhập']")));
            dangNhapBtn.click();
            System.out.println("Người dùng đã nhấn 'Đăng nhập'");

            // Chờ chuyển hướng Microsoft Login
            wait.until(ExpectedConditions.urlContains("login.microsoftonline.com"));

            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")));
            emailInput.sendKeys("hieu.2274802010258@vanlanguni.vn");
            emailInput.sendKeys(Keys.RETURN);
            System.out.println("Đã nhập email");

            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']")));
            passwordInput.sendKeys("VLU29052004");
            passwordInput.sendKeys(Keys.RETURN);
            System.out.println("Đã nhập mật khẩu");

            // 2️⃣ VÀO TRANG "HỌC KỲ VÀ NGÀNH"
            wait.until(ExpectedConditions.urlContains("Phancong02"));
            driver.get("https://cntttest.vanlanguni.edu.vn:18081/Phancong02/Term");
            wait.until(ExpectedConditions.urlContains("Phancong02/Term"));
            System.out.println("Đã vào trang 'Học kỳ và Ngành'");

            // 3️⃣ NHẤN "THÊM HỌC KỲ MỚI"
            WebElement themHocKyBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'Thêm học kỳ mới')]")));
            themHocKyBtn.click();
            System.out.println("Đã nhấn nút 'Thêm học kỳ mới'");

            // 4️⃣ NHẬP HỌC KỲ
            WebElement hocKyInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id")));
            hocKyInput.sendKeys("968");
            System.out.println("Đã nhập học kỳ");

            // 5️⃣ CHỌN NGÀY BẮT ĐẦU (Cập nhật để chắc chắn chọn được)
            WebElement ngayBatDauInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Chọn ngày bắt đầu']")));

            // Cuộn đến ô chọn ngày nếu bị khuất
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ngayBatDauInput);
            Thread.sleep(1000); // Chờ để cuộn hoàn tất

            // Click hai lần để chắc chắn bật lịch
            ngayBatDauInput.click();
            Thread.sleep(1000);
            ngayBatDauInput.click();
            System.out.println("Đã nhấn vào ô chọn ngày, chờ lịch hiển thị...");

            // Chờ lịch hiển thị
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'flatpickr-calendar')]")));
            System.out.println("Lịch đã hiển thị");

            // Chọn ngày 29
            WebElement ngayChon = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[contains(@class, 'flatpickr-day') and text()='29' and not(contains(@class, 'disabled'))]")));
            ngayChon.click();
            System.out.println("Đã chọn ngày 29");

            // 6️⃣ CHỌN LỚP TỐI ĐA = 3
            WebElement classSelect = wait.until(ExpectedConditions.elementToBeClickable(By.id("class-limit")));
            classSelect.sendKeys("3");
            System.out.println("Đã chọn lớp tối đa là 3");

            // 7️⃣ KIỂM TRA & ĐẢM BẢO SỐ TIẾT >= 2
            WebElement tietInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tiet")));
            int tietValue = Integer.parseInt(tietInput.getAttribute("value"));
            while (tietValue < 2) {
                WebElement plusButton = driver.findElement(By.xpath("//button[contains(@class, 'increase')]"));
                plusButton.click();
                tietValue = Integer.parseInt(tietInput.getAttribute("value"));
            }
            System.out.println("Đã đảm bảo số tiết >= 2");

            // 8️⃣ LƯU DỮ LIỆU
            WebElement luuBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Lưu']")));
            luuBtn.click();
            System.out.println("Đã nhấn nút 'Lưu'");

            // 9️⃣ KIỂM TRA THÊM HỌC KỲ THÀNH CÔNG
            try {
                wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//table"), "968"));
                System.out.println("Thêm học kỳ thành công!");
            } catch (TimeoutException e) {
                System.out.println("Lỗi: Thêm học kỳ không thành công!");
            }

            // Giữ trình duyệt mở 5 phút để kiểm tra
            Thread.sleep(300000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
