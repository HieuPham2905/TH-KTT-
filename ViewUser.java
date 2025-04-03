package Project;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ViewUser {
    public static void main(String[] args) {
        // 1. CẤU HÌNH SELENIUM VÀ CHROME DRIVER
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
            // 2. ĐĂNG NHẬP VÀO HỆ THỐNG
            driver.get("https://cntttest.vanlanguni.edu.vn:18081/Phancong02/");
            driver.manage().window().maximize();
            System.out.println("Đã mở trang đăng nhập");

            WebElement dangNhapBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Đăng nhập']")));
            dangNhapBtn.click();
            System.out.println("Người dùng đã nhấn 'Đăng nhập'");

            // 3. CHUYỂN HƯỚNG ĐẾN MICROSOFT LOGIN
            wait.until(ExpectedConditions.urlContains("login.microsoftonline.com"));
            System.out.println("Chuyển hướng đến Microsoft Login...");

            // Nhập email
            WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")));
            emailInput.sendKeys("hieu.2274802010258@vanlanguni.vn");
            emailInput.sendKeys(Keys.RETURN);
            System.out.println("Đã nhập email");

            // Nhập mật khẩu
            WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']")));
            passwordInput.sendKeys("VLU29052004");
            passwordInput.sendKeys(Keys.RETURN);
            System.out.println("Đã nhập mật khẩu");

            // 4. CHỜ ĐĂNG NHẬP HOÀN TẤT
            wait.until(ExpectedConditions.urlContains("Phancong02"));
            System.out.println("Đăng nhập thành công vào hệ thống Van Lang!");

            // 5. ĐI ĐẾN TRANG "NGƯỜI DÙNG"
            driver.get("https://cntttest.vanlanguni.edu.vn:18081/Phancong02/User");
            wait.until(ExpectedConditions.urlContains("Phancong02/User"));
            System.out.println("Đã vào trang 'Người dùng'");

            // 6. KIỂM THỬ CÁC KẾT HỢP LOẠI GIẢNG VIÊN VÀ ROLE
            String[] loaiGiangVien = {"Cơ hữu", "Thỉnh giảng"};
            String[] roles = {"BCN khoa", "Bộ môn", "Chưa phân quyền", "Giảng viên"};

            WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));

            for (String giangVien : loaiGiangVien) {
                for (String role : roles) {
                    System.out.println("Kiểm thử với Loại giảng viên: " + giangVien + " - Role: " + role);

                    // Chọn Loại giảng viên từ dropdown
                    Select loaiGVSelect = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("UserType"))));
                    loaiGVSelect.selectByVisibleText(giangVien);

                    // Chọn Role từ dropdown
                    Select roleSelect = new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("UserRole"))));
                    roleSelect.selectByVisibleText(role);

                    // Chờ cập nhật dữ liệu (giả sử bảng sẽ tự động cập nhật)
                    Thread.sleep(3000); // Tạm dừng 2 giây để dữ liệu thay đổi

                    // Kiểm tra số lượng dòng trong bảng sau khi lọc
                    List<WebElement> rows = table.findElements(By.tagName("tr"));
                    if (rows.size() > 1) { 
                        System.out.println("✅ Kết quả tìm thấy với " + giangVien + " - " + role + " (" + (rows.size() - 1) + " dòng)");
                    } else {
                        System.out.println("⚠️ Không có dữ liệu với " + giangVien + " - " + role);
                    }
                }
            }

            // GIỮ TRÌNH DUYỆT MỞ 5 PHÚT ĐỂ KIỂM TRA THỦ CÔNG
            System.out.println("Trình duyệt sẽ giữ mở trong 5 phút để kiểm tra thủ công...");
            Thread.sleep(300000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng trình duyệt
            driver.quit();
            System.out.println("Đã kết thúc kiểm thử.");
        }
    }
}
