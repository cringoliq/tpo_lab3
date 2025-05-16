import com.labwork.AuthUtils;
import com.labwork.pages.*;
import junit.framework.TestCase;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OneInstantTest extends BaseTest {

    private static HomePage homePage;
    private static SearchPage searchPage;
    private static CatalogPage catalogPage;
    private static ElectronicsPage electronicsPage;
    private static AuthPage authPage;
    private static CartPage cartPage;
    private static CheckoutPage checkoutPage;
    private static WishlistPage wishlistPage;

    @BeforeEach
    public void setUp() {


        homePage = new HomePage(chromeDriver);
        searchPage = new SearchPage(chromeDriver);
        catalogPage = new CatalogPage(chromeDriver);
        authPage = new AuthPage(chromeDriver);
        cartPage = new CartPage(chromeDriver);
        electronicsPage = new ElectronicsPage(chromeDriver);
        checkoutPage = new CheckoutPage(chromeDriver);
        wishlistPage = new WishlistPage(chromeDriver);
        chromeDriver.get("https://market.yandex.ru/");

    }


    @Test
    @Order(1)
    public void searchFoundTest() {
        homePage.enterSearchInput("Iphone 15 Pro");
        homePage.clickSearchButton();

        String titleText = searchPage.getSearchTitle().getText();

        assertTrue(titleText.startsWith("Iphone 15 Pro"), titleText);
    }
    @Test
    @Order(2)
    public void searchNotFoundTest() {
        homePage.enterSearchInput("sdfksflwoelrijwer");
        homePage.clickSearchButton();

        String titleText = searchPage.getEmptySearchPage().getText();

        assertTrue(titleText.startsWith("Такого у нас нет"), titleText);
    }

    @Test
    @Order(3)
    public void searchMainPageTest() {
        homePage.clickBelowMarketButton();

        WebElement firstGood = chromeDriver.findElement(By.xpath("//*[@id='superprice_remix_desktop_RecommendationRoll']//a[@data-auto='snippet-link']"));
        String url = firstGood.getAttribute("href");

        // Вместо клика открываем ссылку в текущем окне
        chromeDriver.get(url);

        chromeWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"/content/page/fancyPage/defaultPage/productTitle\"]/div/div/h1")));
        String currentUrl = chromeDriver.getCurrentUrl();
        System.out.println("Текущий URL: " + currentUrl);

        assertTrue(currentUrl.contains("product--"), "URL не содержит 'product--'. Текущий URL: " + currentUrl);
    }





    @Test
    @Order(4)
    public void searchViaCatalogTest() throws InterruptedException {

//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Войдите, и станет дешевле']\n")));
//        actions.moveByOffset(100, 100).click().perform();

        homePage.clickCatalogButton();

//*[@id="/marketfrontDynamicPopupLoader43/content"]/div
        WebElement element = chromeWait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[@href='/catalog--muzhskaia-odezhda/54404675']")
        ));

        element.click();





        WebElement marketTitle = chromeWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(@class, 'ds-text') and contains(@class, 'ds-text_headline-4_bold')]")));


        String titleText = marketTitle.getText();

        assertTrue(titleText.startsWith("Мужская одежда"), titleText);
    }

    @Test
    @Order(5)
    public void testPriceSearchFilters() throws InterruptedException {
        homePage.enterSearchInput("Iphone 15 Pro");
        homePage.clickSearchButton();
        searchPage.inputPriceRange("77777");

        Thread.sleep(2000); // Лучше заменить на wait
        chromeWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".ds-text_color_price-term")
        ));
        searchPage.clickViewTypeSelector();
        Thread.sleep(2000);
        List<Integer> prices = getTop5PricesAsIntegers();

        for (int i = 0; i < prices.size(); i++) {
            int price = prices.get(i);
            System.out.println("Товар #" + (i + 1) + " — Цена: " + price);
        }


        boolean allBelowLimit = prices.stream().allMatch(price -> price <= 77777);
        assertTrue(allBelowLimit, "Один или несколько товаров превышают лимит в 70 000");

    }

    public List<Integer> getTop5PricesAsIntegers() {
        List<Integer> prices = new ArrayList<>();
        List<WebElement> priceElements = chromeDriver.findElements(By.cssSelector(".ds-text_color_price-term"));

        for (WebElement priceElement : priceElements) {
            String rawText = priceElement.getText().replaceAll("\\D", ""); // Удаляем всё кроме цифр
            if (!rawText.isEmpty()) {
                try {
                    prices.add(Integer.parseInt(rawText));
                } catch (NumberFormatException ignored) {
                    // Пропускаем, если парсинг не удался
                }
            }
            if (prices.size() == 5) break;
        }

        return prices;
    }





    @Test
    @Order(5)
    public void testLoginWithEmptyCredentials() throws InterruptedException {
        WebElement element = chromeWait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a\n")
        ));

        element.click();
//        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Войти']]")));
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].click();", loginButton);

        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        authPage.enterLoginField("");

        Thread.sleep(1000);
        WebElement marketTitle = chromeWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"field:input-login:hint\"]")));
        String titleText = marketTitle.getText();
        System.out.println(titleText);

        assertTrue(titleText.startsWith("Логин"), titleText);
    }


    @Test
    @Order(6)
    public void testLoginWhichNonExists() throws InterruptedException {
        WebElement element = chromeWait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a\n")
        ));

        element.click();
//        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Войти']]")));
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].click();", loginButton);

        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        authPage.enterLoginField("testinobuggulino1337");

        Thread.sleep(1000);
        WebElement marketTitle = chromeWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"field:input-login:hint\"]")));
        String titleText = marketTitle.getText();
        System.out.println(titleText);

        assertTrue(titleText.startsWith("Нет"), titleText);
    }
    @Test
    @Order(7)
    public void testLoginWithInvalidLoginCredentials() throws InterruptedException {
//        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Войти']]")));
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].click();", loginButton);

        WebElement element = chromeWait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a\n")
        ));

        element.click();
        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        String incorrectLogin = "~~~~~~~~~~~~~KFSKDF";
        authPage.enterLoginField(incorrectLogin);


        Thread.sleep(1000);

        WebElement marketTitle = chromeWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"field:input-login:hint\"]")));
        String titleText = marketTitle.getText();
        System.out.println(titleText);
        assertTrue(titleText.startsWith("Такой"), titleText);
    }

    @Test
    @Order(8)
    public void testLoginWithInvalidPasswordCredentials() throws InterruptedException {
//        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Войти']]")));
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].click();", loginButton);

        WebElement element = chromeWait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a\n")
        ));

        element.click();
        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        String incorrectLogin = "lomaniinoss";
        authPage.enterLoginField(incorrectLogin);

        authPage.enterPasswordField("90000000000");

        Thread.sleep(1000);

        WebElement marketTitle = chromeWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"field:input-passwd:hint\"]\n")));
        String titleText = marketTitle.getText();
        System.out.println(titleText);
        assertTrue(titleText.startsWith("Неверный"), titleText);
    }
    @Test
    @Order(9)
    public void testLoginWithNoPassword() throws InterruptedException {
//        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[span[text()='Войти']]")));
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("arguments[0].click();", loginButton);

        WebElement element = chromeWait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a\n")
        ));

        element.click();
        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        String incorrectLogin = "lomaniinoss";
        authPage.enterLoginField(incorrectLogin);

        authPage.enterPasswordField("");

        Thread.sleep(1000);

        WebElement marketTitle = chromeWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"field:input-passwd:hint\"]\n")));
        String titleText = marketTitle.getText();
        System.out.println(titleText);
        assertTrue(titleText.startsWith("Пароль"), titleText);
    }




    @Test
    @Order(10)
    public void productBuyTest() throws InterruptedException {


        WebElement element = chromeWait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a\n")
        ));

        element.click();
        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();
        String login = AuthUtils.getCredentialsFromFile("/home/extrzz/stuff/verySecretPassword")[0];

        authPage.enterLoginField(login);
        String password = AuthUtils.getCredentialsFromFile("/home/extrzz/stuff/verySecretPassword")[1];
        authPage.enterPasswordField(password);

        // Ждем, пока пользователь введет код доступа вручную
        System.out.println("Пожалуйста, введите код доступа вручную и нажмите Enter.");

        // Ожидаем, что пользователь введет код и нажмет Enter


        Scanner scanner = new Scanner(System.in);
        String accessCode = scanner.nextLine();  // Чтение кода из консоли

        authPage.enterPhoneCodeField(accessCode);

        homePage.clickBelowMarketButton();

        Thread.sleep(2000);

        WebElement firstGood = chromeDriver.findElement(By.xpath("//*[@id='superprice_remix_desktop_RecommendationRoll']//a[@data-auto='snippet-link']"));
        String url = firstGood.getAttribute("href");

        // Вместо клика открываем ссылку в текущем окне
        chromeDriver.get(url);

        chromeWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"/content/page/fancyPage/defaultPage/mainDO/actions\"]/div/div/div/div[2]/div/button")));

        cartPage.clickInCardButton();

        String originalHandleCart = chromeDriver.getWindowHandle();
        Set<String> oldHandlesCart = chromeDriver.getWindowHandles();

        Thread.sleep(2000);
        homePage.clickCartButton();


        chromeWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"cartCheckoutButton\"]")));
        cartPage.clickCheckoutButton();

        String titleText = checkoutPage.getTitle().getText();

        assertTrue(titleText.startsWith("Оформление"), titleText);



    }
    @Test
    @Order(11)
    public void productBuyInstantTest() throws InterruptedException {
//

        homePage.clickBelowMarketButton();





        Thread.sleep(2000);

        WebElement firstGood = chromeDriver.findElement(By.xpath("//*[@id='superprice_remix_desktop_RecommendationRoll']//a[@data-auto='snippet-link']"));
        String url = firstGood.getAttribute("href");

        // Вместо клика открываем ссылку в текущем окне
        chromeDriver.get(url);


        chromeWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"/content/page/fancyPage/defaultPage/mainDO/actions\"]/div/div/div/div[1]/button[1]")));

        cartPage.clickInstantBuyButton();

        String titleText = checkoutPage.getTitle().getText();

        assertTrue(titleText.startsWith("Оформление"), titleText);
    }


    @Test
    @Order(12)
    public void addToWishList() throws InterruptedException {


//        WebElement element = chromeWait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a\n")
//        ));
//
//        element.click();
//        authPage.clickMoreWaysButton();
//        authPage.clickByLoginButton();
//        String login = AuthUtils.getCredentialsFromFile("/home/extrzz/stuff/verySecretPassword")[0];
//
//        authPage.enterLoginField(login);
//        String password = AuthUtils.getCredentialsFromFile("/home/extrzz/stuff/verySecretPassword")[1];
//        authPage.enterPasswordField(password);
//
//        // Ждем, пока пользователь введет код доступа вручную
//        System.out.println("Пожалуйста, введите код доступа вручную и нажмите Enter.");
//
//        // Ожидаем, что пользователь введет код и нажмет Enter
//
//
//        Scanner scanner = new Scanner(System.in);
//        String accessCode = scanner.nextLine();  // Чтение кода из консоли
//
//        authPage.enterPhoneCodeField(accessCode);



        homePage.clickBelowMarketButton();

        Thread.sleep(2000);

        WebElement firstGood = chromeDriver.findElement(By.xpath("//*[@id='superprice_remix_desktop_RecommendationRoll']//a[@data-auto='snippet-link']"));
        String url = firstGood.getAttribute("href");


        // Вместо клика открываем ссылку в текущем окне
        chromeDriver.get(url);

        chromeWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"/content/page/fancyPage/defaultPage/mainDO/actions\"]/div/div/div/div[1]/button[1]")));

        cartPage.clickWishlistButton();

        //далее нужно запомнить название товара который потом будет искать в вишлисте
        // xpath элемента названия
        // Получаем название товара перед добавлением в вишлист
        WebElement titleElement = chromeDriver.findElement(
                By.xpath("//*[@id=\"/content/page/fancyPage/defaultPage/productTitle\"]/div/div/h1")
        );
        String productTitle = titleElement.getText().trim();
        homePage.clickWishlistButton();

        // Ждём, пока вишлист загрузится
        WebElement wishlistDiv = wishlistPage.getWishListDiv();
        chromeWait.until(ExpectedConditions.visibilityOf(wishlistDiv));

        WebElement foundItem = findWishListItemByName(productTitle);
        assertNotNull(foundItem, "Товар не найден в избранном: " + productTitle);





    }
    public WebElement findWishListItemByName(String targetName) {
        // Ищем все <span> в wishListDiv (можно уточнить до .//span[contains(...)] при необходимости)
        WebElement wishListDiv = wishlistPage.getWishListDiv();
        List<WebElement> spans = wishListDiv.findElements(By.xpath(".//span"));

        for (WebElement span : spans) {
            System.out.println(span.getText());
            String text = span.getText().trim();
            if (text.equalsIgnoreCase(targetName)) {
                return span;
            }
        }

        return null; // если не найден
    }

}

