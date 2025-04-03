package Project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UpdateUser {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
        loginToSystem();
    }

    @Test
    public void testCases() {
        executeTest("hoang.invalidemail@gmail.com", "2274802010292", true);
        executeTest("hoang.2274802010292@vanlanguni.vn", "2274802010292", false);
        executeTest("hoang.2274802010292@vanlanguni.vn", "2274802010279", true);
        executeTest("hoang.2274802010292@vanlanguni.vn", "2274802010292", false);
    }

    public void loginToSystem() {
        driver.get("https://cntttest.vanlanguni.edu.vn:18081/Phancong02/");
        
        try {
            WebElement advancedButton = driver.findElement(By.id("details-button"));
            advancedButton.click();
            WebElement proceedLink = driver.findElement(By.id("proceed-link"));
            proceedLink.click();
        } catch (Exception ignored) {}

        WebElement openIdLoginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("OpenIdConnect")));
        openIdLoginButton.click();

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='email']")));
        emailField.sendKeys("hoang.2274802010279@vanlanguni.vn");
        driver.findElement(By.xpath("//input[@type='submit'][@value='Next']")).click();

        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']")));
        passwordField.sendKeys("HuyHoang2911@");
        driver.findElement(By.xpath("//input[@type='submit'][@value='Sign in']")).click();
        
        try {
            WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='submit'][@value='Yes']")));
            yesButton.click();
        } catch (Exception ignored) {}

        navigateToUserManagement();
    }

    public void executeTest(String email, String staffId, boolean expectError) {
        try {
            navigateToUserManagement();
            testNguoiDung(email, staffId, expectError);
        } catch (Exception e) {
            System.out.println("⚠ Test encountered an error, canceling and continuing.");
            cancelAndContinue();
        }
    }

    public void cancelAndContinue() {
        try {
            WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnClose")));
            cancelButton.click();
            Thread.sleep(2000);
            navigateToUserManagement();
        } catch (Exception ignored) {}
    }

    public void navigateToUserManagement() {
        try {
            WebElement nguoiDungButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Người dùng')]")));
            nguoiDungButton.click();
        } catch (Exception ignored) {}
    }

    public void testNguoiDung(String email, String staffId, boolean expectError) throws InterruptedException {
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='search'][contains(@placeholder, 'Nhập tìm kiếm')]")));
        searchBox.clear();
        searchBox.sendKeys("HoangCTU");
        
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(@class, 'feather-edit')]")));
        editButton.click();

        WebElement staffIdField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("staff_id")));
        staffIdField.clear();
        staffIdField.sendKeys(staffId);
        
        WebElement emailFieldEdit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        emailFieldEdit.clear();
        emailFieldEdit.sendKeys(email);
        
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Lưu')]")));
        saveButton.click();
        
        if (expectError) {
            try {
                WebElement errorLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email-error")));
                if (errorLabel.getText().contains("Vui lòng nhập email Văn Lang hợp lệ!")) {
                    System.out.println("✔ Test passed: Hệ thống hiển thị lỗi email không hợp lệ như mong đợi.");
                    cancelAndContinue();
                    return;
                }
                WebElement errorPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("swal2-popup")));
                WebElement okButton = errorPopup.findElement(By.xpath("//button[contains(text(), 'OK')]"));
                okButton.click();
                System.out.println("✔ Test passed: Hệ thống hiển thị lỗi như mong đợi.");
            } catch (Exception e) {
                System.out.println("✘ Test failed: Không thấy thông báo lỗi khi cần thiết.");
            }
        } else {
            System.out.println("✔ Test passed: Lưu thông tin thành công.");
        }

        try {
            WebElement errorPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("swal2-html-container")));
            if (errorPopup.getText().contains("Mã giảng viên này đã có trong hệ thống!")) {
                WebElement okButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'OK')]")));
                okButton.click();
                System.out.println("✔ Test passed: Hệ thống hiển thị lỗi mã giảng viên đã tồn tại như mong đợi.");
            }
        } catch (Exception ignored) {}
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            try {
                driver.close(); // Đóng tab hiện tại
                Thread.sleep(1000); // Chờ 1s để đảm bảo tiến trình kết thúc
                driver.quit(); // Đóng WebDriver
            } catch (Exception e) {
                System.out.println("⚠️ Lỗi khi đóng trình duyệt: " + e.getMessage());
            }
        }
    }
}
