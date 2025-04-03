package Project;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.util.NoSuchElementException;

public class UpdateTerm {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    private void login() throws InterruptedException {
        driver.get("https://cntttest.vanlanguni.edu.vn:18081/Phancong02/");
        System.out.println("Đã mở trang web thành công.");

        try {
            WebElement advancedButton = driver.findElement(By.id("details-button"));
            advancedButton.click();
            WebElement proceedLink = driver.findElement(By.id("proceed-link"));
            proceedLink.click();
        } catch (Exception ignored) {}

        Thread.sleep(2000);
        driver.findElement(By.id("OpenIdConnect")).click();

        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("hoang.2274802010279@vanlanguni.vn");
        driver.findElement(By.xpath("//input[@type='submit'][@value='Next']")).click();

        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("HuyHoang2911@");
        driver.findElement(By.xpath("//input[@type='submit'][@value='Sign in']")).click();

        Thread.sleep(10000);
        try {
            driver.findElement(By.xpath("//input[@type='submit'][@value='Yes']")).click();
        } catch (Exception ignored) {}

        System.out.println("Đăng nhập thành công.");
    }

    private void navigateToTerm() throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(By.xpath("//a[@href='/Phancong02/Term']//span[contains(text(),'Học kỳ và ngành')]")).click();
        System.out.println("Đã vào trang Học kỳ và Ngành.");

        Thread.sleep(2000);
        WebElement searchBox = driver.findElement(By.xpath("//input[@type='search' and @class='form-control' and @placeholder='Nhập tìm kiếm...']"));
        searchBox.click();
        searchBox.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        searchBox.sendKeys("219");

        Thread.sleep(2000);
        WebElement result = driver.findElement(By.xpath("//td[contains(text(),'219')]"));
        assertNotNull(result, "Không tìm thấy học kỳ 219");

        driver.findElement(By.xpath("//a[contains(@class, 'editRow') and @title='Chỉnh sửa']")).click();
        Thread.sleep(3000);
        System.out.println("Đã mở giao diện chỉnh sửa học kỳ.");
        
    }
    
    
    @Test
    public void testMaxClassHigh() throws InterruptedException {
        navigateToTerm();

        WebElement maxClassInput = driver.findElement(By.id("max_class"));
        maxClassInput.click();
        Thread.sleep(1000);
        maxClassInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        Thread.sleep(1000);
        maxClassInput.sendKeys("32");
        Thread.sleep(2000);

        driver.findElement(By.xpath("//button[contains(@class, 'btn-primary') and text()='Lưu']")).click();
        Thread.sleep(5000);
        
    }
    
    @Test
    public void testMaxClassLow() throws InterruptedException {

        navigateToTerm();

        WebElement maxClassInput = driver.findElement(By.id("max_class"));
        maxClassInput.click();
        Thread.sleep(1000);
        maxClassInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        Thread.sleep(1000);
        maxClassInput.sendKeys("0");
        Thread.sleep(2000);

        driver.findElement(By.xpath("//button[contains(@class, 'btn-primary') and text()='Lưu']")).click();
        Thread.sleep(5000);
        
        try {
            // Kiểm tra nếu có thông báo lỗi "Năm kết thúc không thể nhỏ hơn năm bắt đầu!"
            WebElement errorMessage3 = driver.findElement(By.id("max_class-error"));

            if (errorMessage3.isDisplayed()) {
                System.out.println("Lỗi: " + errorMessage3.getText());
            }
        } catch (NoSuchElementException e) {
            System.out.println("Không tìm thấy thông báo lỗi hoặc không có lỗi.");
        }

        navigateToTerm();


    }
    
    @Test
    public void testMaxLessonHigh() throws InterruptedException {
        navigateToTerm();

        WebElement maxLessonInput = driver.findElement(By.id("max_lesson"));
        maxLessonInput.click();
        Thread.sleep(1000);
        maxLessonInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        Thread.sleep(1000);
        maxLessonInput.sendKeys("16");
        Thread.sleep(2000);

        driver.findElement(By.xpath("//button[contains(@class, 'btn-primary') and text()='Lưu']")).click();
        Thread.sleep(5000);
        
        try {
            // Kiểm tra nếu có thông báo lỗi "Năm kết thúc không thể nhỏ hơn năm bắt đầu!"
            WebElement errorMessage3 = driver.findElement(By.id("max_lesson-error"));

            if (errorMessage3.isDisplayed()) {
                System.out.println("Lỗi: " + errorMessage3.getText());
            }
        } catch (NoSuchElementException e) {
            System.out.println("Không tìm thấy thông báo lỗi hoặc không có lỗi.");
        }

        testMaxClassLow();

    }
    
    @Test
    public void testMaxLessonLow() throws InterruptedException {
        navigateToTerm();

        WebElement maxLessonInput = driver.findElement(By.id("max_lesson"));
        maxLessonInput.click();
        Thread.sleep(1000);
        maxLessonInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        Thread.sleep(1000);
        maxLessonInput.sendKeys("2");
        Thread.sleep(2000);

        driver.findElement(By.xpath("//button[contains(@class, 'btn-primary') and text()='Lưu']")).click();
        Thread.sleep(5000);
        
        try {
            // Kiểm tra nếu có thông báo lỗi "Năm kết thúc không thể nhỏ hơn năm bắt đầu!"
            WebElement errorMessage3 = driver.findElement(By.id("max_lesson-error"));

            if (errorMessage3.isDisplayed()) {
                System.out.println("Lỗi: " + errorMessage3.getText());
            }
        } catch (NoSuchElementException e) {
            System.out.println("Không tìm thấy thông báo lỗi hoặc không có lỗi.");
        }

        testMaxLessonHigh();

    }
    
    @Test
    public void testStartWeekHigh() throws InterruptedException {
        navigateToTerm();

        WebElement startWeekInput = driver.findElement(By.id("start_week"));
        startWeekInput.click();
        Thread.sleep(1000);
        startWeekInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        Thread.sleep(1000);
        startWeekInput.sendKeys("53");
        Thread.sleep(2000);

        driver.findElement(By.xpath("//button[contains(@class, 'btn-primary') and text()='Lưu']")).click();
        Thread.sleep(5000);
        
        try {
            // Kiểm tra nếu có thông báo lỗi "Năm kết thúc không thể nhỏ hơn năm bắt đầu!"
            WebElement errorMessage3 = driver.findElement(By.id("start_week-error"));

            if (errorMessage3.isDisplayed()) {
                System.out.println("Lỗi: " + errorMessage3.getText());
            }
        } catch (NoSuchElementException e) {
            System.out.println("Không tìm thấy thông báo lỗi hoặc không có lỗi.");
        }

        testMaxLessonLow();

    }

    @Test
    public void testStartWeekLow() throws InterruptedException {
        navigateToTerm();
        
        WebElement startWeekInput = driver.findElement(By.id("start_week"));
        startWeekInput.click();
        Thread.sleep(1000);
        startWeekInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        Thread.sleep(1000);
        startWeekInput.sendKeys("0");
        Thread.sleep(2000);

        driver.findElement(By.xpath("//button[contains(@class, 'btn-primary') and text()='Lưu']")).click();
        Thread.sleep(5000);
        
        try {
            // Kiểm tra nếu có thông báo lỗi "Năm kết thúc không thể nhỏ hơn năm bắt đầu!"
            WebElement errorMessage2 = driver.findElement(By.id("start_week-error"));

            if (errorMessage2.isDisplayed()) {
                System.out.println("Lỗi: " + errorMessage2.getText());
            }
        } catch (NoSuchElementException e) {
            System.out.println("Không tìm thấy thông báo lỗi hoặc không có lỗi.");
        }

        testStartWeekHigh();

    }
    
    @Test
    public void testYearInvalid() throws InterruptedException {
        login();
        navigateToTerm();

        // Chọn năm bắt đầu
        driver.findElement(By.id("select2-start_year-container")).click();
        driver.findElement(By.xpath("//li[contains(@id, 'select2-start_year-result') and text()='2027']")).click();
        Thread.sleep(2000);

        // Chọn năm kết thúc
        driver.findElement(By.id("select2-end_year-container")).click();
        driver.findElement(By.xpath("//li[contains(@id, 'select2-end_year-result') and text()='2026']")).click();
        Thread.sleep(2000);

        // Nhấn nút Lưu
        driver.findElement(By.xpath("//button[contains(@class, 'btn-primary') and text()='Lưu']")).click();
        Thread.sleep(5000);

        // Kiểm tra lỗi nếu có
        try {
            // Kiểm tra nếu có thông báo lỗi "Năm kết thúc không thể nhỏ hơn năm bắt đầu!"
            WebElement errorMessage1 = driver.findElement(By.id("end_year-error"));

            if (errorMessage1.isDisplayed()) {
                System.out.println("Lỗi: " + errorMessage1.getText());
            }
        } catch (NoSuchElementException e) {
            System.out.println("Không tìm thấy thông báo lỗi hoặc không có lỗi.");
        }

        testStartWeekLow();
    }



    @AfterClass
    public void tearDown() {
        if (driver != null) {
            try {
                driver.close();
                driver.quit();
            } catch (Exception ignored) {}
        }
    }
}
