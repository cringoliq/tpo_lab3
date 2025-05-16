import com.labwork.AuthUtils;
import com.labwork.pages.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OneInstanceTest extends BaseTest {
    private HomePage homePageChrome;
    private SearchPage searchPageChrome;
    private CatalogPage catalogPageChrome;
    private ElectronicsPage electronicsPageChrome;
    private AuthPage authPageChrome;
    private CartPage cartPageChrome;
    private CheckoutPage checkoutPageChrome;
    private WishlistPage wishlistPageChrome;


    private HomePage homePageFirefox;
    private SearchPage searchPageFirefox;
    private CatalogPage catalogPageFirefox;
    private ElectronicsPage electronicsPageFirefox;
    private AuthPage authPageFirefox;
    private CartPage cartPageFirefox;
    private CheckoutPage checkoutPageFirefox;
    private WishlistPage wishlistPageFirefox;

    @BeforeEach
    public void setup() {


        setUpDrivers();
        // Инициализация страниц для Chrome
        homePageChrome = new HomePage(getChromeDriver());
        searchPageChrome = new SearchPage(getChromeDriver());
        catalogPageChrome = new CatalogPage(getChromeDriver());
        authPageChrome = new AuthPage(getChromeDriver());
        cartPageChrome = new CartPage(getChromeDriver());
        electronicsPageChrome = new ElectronicsPage(getChromeDriver());
        checkoutPageChrome = new CheckoutPage(getChromeDriver());
        wishlistPageChrome = new WishlistPage(getChromeDriver());
        // Инициализация страниц для Firefox
        homePageFirefox = new HomePage(getFirefoxDriver());
        searchPageFirefox = new SearchPage(getFirefoxDriver());
        catalogPageFirefox = new CatalogPage(getFirefoxDriver());
        authPageFirefox = new AuthPage(getFirefoxDriver());
        cartPageFirefox = new CartPage(getFirefoxDriver());
        electronicsPageFirefox = new ElectronicsPage(getFirefoxDriver());
        checkoutPageFirefox = new CheckoutPage(getFirefoxDriver());
        wishlistPageFirefox = new WishlistPage(getFirefoxDriver());
        // Открываем страницу в обоих браузерах
        getChromeDriver().get("https://market.yandex.ru/");
        getFirefoxDriver().get("https://market.yandex.ru/");

    }

    @Test
    @Order(1)
    public void searchFoundTest() throws InterruptedException {
        searchFound(getChromeDriver(), getChromeWait(), homePageChrome, searchPageChrome, catalogPageChrome, authPageChrome, cartPageChrome, electronicsPageChrome, checkoutPageChrome, wishlistPageChrome);
        searchFound(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, searchPageFirefox, catalogPageFirefox, authPageFirefox, cartPageFirefox, electronicsPageFirefox, checkoutPageFirefox, wishlistPageFirefox);

    }
    private void searchFound(WebDriver driver,
                                  org.openqa.selenium.support.ui.WebDriverWait wait,
                                  HomePage homePage,
                                  SearchPage searchPage,
                                  CatalogPage catalogPage,
                                  AuthPage authPage,
                                  CartPage cartPage,
                                  ElectronicsPage electronicsPage,
                                  CheckoutPage checkoutPage,
                                  WishlistPage wishlistPage) throws InterruptedException {
        Thread.sleep(5000);
        homePage.enterSearchInput("Iphone 15 Pro");
        homePage.clickSearchButton();

        String titleText = searchPage.getSearchTitle().getText();

        assertTrue(titleText.startsWith("Iphone 15 Pro"), titleText);

    }


    @Test
    @Order(2)
    public void searchNotFoundTest() throws InterruptedException {
        searchNotFound(getChromeDriver(), getChromeWait(), homePageChrome, searchPageChrome, catalogPageChrome, authPageChrome, cartPageChrome, electronicsPageChrome, checkoutPageChrome, wishlistPageChrome);
        searchNotFound(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, searchPageFirefox, catalogPageFirefox, authPageFirefox, cartPageFirefox, electronicsPageFirefox, checkoutPageFirefox, wishlistPageFirefox);

    }
    private void searchNotFound(WebDriver driver,
                             org.openqa.selenium.support.ui.WebDriverWait wait,
                             HomePage homePage,
                             SearchPage searchPage,
                             CatalogPage catalogPage,
                             AuthPage authPage,
                             CartPage cartPage,
                             ElectronicsPage electronicsPage,
                             CheckoutPage checkoutPage,
                             WishlistPage wishlistPage) throws InterruptedException {

        homePage.enterSearchInput("sdfksflwoelrijwer");
        homePage.clickSearchButton();

        String titleText = searchPage.getEmptySearchPage().getText();

        assertTrue(titleText.startsWith("Такого у нас нет"), titleText);
    }

    private void handleCaptchaIfPresent(WebDriver driver) {

        if (isCaptchaPage(driver)) {
            System.out.println("Обнаружена капча. Пожалуйста, решите её вручную и нажмите Enter...");
            new Scanner(System.in).nextLine(); // Ждем ручного подтверждения

        }
    }

    private boolean isCaptchaPage(WebDriver driver) {
        String url = driver.getCurrentUrl().toLowerCase();
        String source = driver.getPageSource().toLowerCase();
        return url.contains("showcaptcha");
    }
    @Test
    @Order(3)
    public void searchMainPageTest() throws InterruptedException {
        searchMainPage(getChromeDriver(), getChromeWait(), homePageChrome, searchPageChrome, catalogPageChrome, authPageChrome, cartPageChrome, electronicsPageChrome, checkoutPageChrome, wishlistPageChrome);
        searchMainPage(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, searchPageFirefox, catalogPageFirefox, authPageFirefox, cartPageFirefox, electronicsPageFirefox, checkoutPageFirefox, wishlistPageFirefox);


    }
    private void searchMainPage(WebDriver driver,
                                org.openqa.selenium.support.ui.WebDriverWait wait,
                                HomePage homePage,
                                SearchPage searchPage,
                                CatalogPage catalogPage,
                                AuthPage authPage,
                                CartPage cartPage,
                                ElectronicsPage electronicsPage,
                                CheckoutPage checkoutPage,
                                WishlistPage wishlistPage) throws InterruptedException {

        if (driver.getCurrentUrl().contains("showcaptcha")) {
            System.out.println("Обнаружена капча! Пройди её вручную...");
            Thread.sleep(20000); // Дай себе 30 секунд, чтобы вручную пройти
        }

        homePage.clickBelowMarketButton();

        WebElement firstGood = driver.findElement(By.xpath("//*[@id='superprice_remix_desktop_RecommendationRoll']//a[@data-auto='snippet-link']"));
        String url = firstGood.getAttribute("href");

        // Вместо клика открываем ссылку в текущем окне
        driver.get(url);
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"/content/page/fancyPage/defaultPage/productTitle\"]/div/div/h1")));
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Текущий URL: " + currentUrl);

        assertTrue(
                currentUrl.contains("card") || currentUrl.contains("product--"),
                "URL не содержит ни 'card', ни 'product--'. Текущий URL: " + currentUrl
        );
    }
    @Test
    @Order(4)
    public void searchViaCatalogTest() throws InterruptedException {
        searchViaCatalog(getChromeDriver(), getChromeWait(), homePageChrome, searchPageChrome, catalogPageChrome, authPageChrome, cartPageChrome, electronicsPageChrome, checkoutPageChrome, wishlistPageChrome);
        searchViaCatalog(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, searchPageFirefox, catalogPageFirefox, authPageFirefox, cartPageFirefox, electronicsPageFirefox, checkoutPageFirefox, wishlistPageFirefox);


    }
    private void searchViaCatalog(WebDriver driver,
                                org.openqa.selenium.support.ui.WebDriverWait wait,
                                HomePage homePage,
                                SearchPage searchPage,
                                CatalogPage catalogPage,
                                AuthPage authPage,
                                CartPage cartPage,
                                ElectronicsPage electronicsPage,
                                CheckoutPage checkoutPage,
                                WishlistPage wishlistPage) throws InterruptedException {



        homePage.clickCatalogButton();
        Thread.sleep(2000);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[@href='/catalog--muzhskaia-odezhda/54404675']")
        ));
        element.click();

        WebElement marketTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[contains(@class, 'ds-text') and contains(@class, 'ds-text_headline-4_bold')]")));
        String titleText = marketTitle.getText();

        assertTrue(titleText.startsWith("Мужская одежда"), titleText);
    }
    @Test
    @Order(5)
    public void testPriceSearchFilters() throws InterruptedException {
        PriceSearchFilters(getChromeDriver(), getChromeWait(), homePageChrome, searchPageChrome, catalogPageChrome, authPageChrome, cartPageChrome, electronicsPageChrome, checkoutPageChrome, wishlistPageChrome);
        PriceSearchFilters(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, searchPageFirefox, catalogPageFirefox, authPageFirefox, cartPageFirefox, electronicsPageFirefox, checkoutPageFirefox, wishlistPageFirefox);


    }
    private void PriceSearchFilters(WebDriver driver,
                                  org.openqa.selenium.support.ui.WebDriverWait wait,
                                  HomePage homePage,
                                  SearchPage searchPage,
                                  CatalogPage catalogPage,
                                  AuthPage authPage,
                                  CartPage cartPage,
                                  ElectronicsPage electronicsPage,
                                  CheckoutPage checkoutPage,
                                  WishlistPage wishlistPage) throws InterruptedException {


        homePage.enterSearchInput("Iphone 15 Pro");
        homePage.clickSearchButton();
        searchPage.inputPriceRange("77777");

        Thread.sleep(2000); // Лучше заменить на wait
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".ds-text_color_price-term")
        ));
        searchPage.clickViewTypeSelector();
        Thread.sleep(2000);
        List<Integer> prices = getTop5PricesAsIntegers(driver);

        for (int i = 0; i < prices.size(); i++) {
            int price = prices.get(i);
            System.out.println("Товар #" + (i + 1) + " — Цена: " + price);
        }


        boolean allBelowLimit = prices.stream().allMatch(price -> price <= 77777);
        assertTrue(allBelowLimit, "Один или несколько товаров превышают лимит в 77777");
    }

    public List<Integer> getTop5PricesAsIntegers(WebDriver driver) {
        List<Integer> prices = new ArrayList<>();
        List<WebElement> priceElements = driver.findElements(By.cssSelector(".ds-text_color_price-term"));

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
    @Order(6)
    public void testLoginWithEmptyCredentials() throws InterruptedException {
        LoginWithEmptyCredentials(getChromeDriver(), getChromeWait(), homePageChrome, searchPageChrome, catalogPageChrome, authPageChrome, cartPageChrome, electronicsPageChrome, checkoutPageChrome, wishlistPageChrome);
        LoginWithEmptyCredentials(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, searchPageFirefox, catalogPageFirefox, authPageFirefox, cartPageFirefox, electronicsPageFirefox, checkoutPageFirefox, wishlistPageFirefox);


    }
    private void LoginWithEmptyCredentials(WebDriver driver,
                                    org.openqa.selenium.support.ui.WebDriverWait wait,
                                    HomePage homePage,
                                    SearchPage searchPage,
                                    CatalogPage catalogPage,
                                    AuthPage authPage,
                                    CartPage cartPage,
                                    ElectronicsPage electronicsPage,
                                    CheckoutPage checkoutPage,
                                    WishlistPage wishlistPage) throws InterruptedException {


        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a\n")
        ));

        element.click();

        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        authPage.enterLoginField("");

        Thread.sleep(1000);
        WebElement marketTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"field:input-login:hint\"]")));
        String titleText = marketTitle.getText();
        System.out.println(titleText);

        assertTrue(titleText.startsWith("Логин"), titleText);
    }

    @Test
    @Order(7)
    public void testLoginWhichNonExists() throws InterruptedException {
        LoginWhichNonExists(getChromeDriver(), getChromeWait(), homePageChrome, searchPageChrome, catalogPageChrome, authPageChrome, cartPageChrome, electronicsPageChrome, checkoutPageChrome, wishlistPageChrome);

        LoginWhichNonExists(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, searchPageFirefox, catalogPageFirefox, authPageFirefox, cartPageFirefox, electronicsPageFirefox, checkoutPageFirefox, wishlistPageFirefox);


    }
    private void LoginWhichNonExists(WebDriver driver,
                                           org.openqa.selenium.support.ui.WebDriverWait wait,
                                           HomePage homePage,
                                           SearchPage searchPage,
                                           CatalogPage catalogPage,
                                           AuthPage authPage,
                                           CartPage cartPage,
                                           ElectronicsPage electronicsPage,
                                           CheckoutPage checkoutPage,
                                           WishlistPage wishlistPage) throws InterruptedException {


        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a\n")
        ));

        element.click();

        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        authPage.enterLoginField("testinobuggulino1337");

        Thread.sleep(1000);
        WebElement marketTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"field:input-login:hint\"]")));
        String titleText = marketTitle.getText();
        System.out.println(titleText);

        assertTrue(titleText.startsWith("Нет"), titleText);
    }

    @Test
    @Order(8)
    public void testLoginWithInvalidLoginCredentials() throws InterruptedException {
        LoginWithInvalidLoginCredentials(getChromeDriver(), getChromeWait(), homePageChrome, searchPageChrome, catalogPageChrome, authPageChrome, cartPageChrome, electronicsPageChrome, checkoutPageChrome, wishlistPageChrome);

        LoginWithInvalidLoginCredentials(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, searchPageFirefox, catalogPageFirefox, authPageFirefox, cartPageFirefox, electronicsPageFirefox, checkoutPageFirefox, wishlistPageFirefox);


    }
    private void LoginWithInvalidLoginCredentials(WebDriver driver,
                                     org.openqa.selenium.support.ui.WebDriverWait wait,
                                     HomePage homePage,
                                     SearchPage searchPage,
                                     CatalogPage catalogPage,
                                     AuthPage authPage,
                                     CartPage cartPage,
                                     ElectronicsPage electronicsPage,
                                     CheckoutPage checkoutPage,
                                     WishlistPage wishlistPage) throws InterruptedException {


        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a\n")
        ));

        element.click();
        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        String incorrectLogin = "~~~~~~~~~~~~~KFSKDF";
        authPage.enterLoginField(incorrectLogin);


        Thread.sleep(1000);

        WebElement marketTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"field:input-login:hint\"]")));
        String titleText = marketTitle.getText();
        System.out.println(titleText);
        assertTrue(titleText.startsWith("Такой"), titleText);
    }

    @Test
    @Order(9)
    public void testLoginWithInvalidPasswordCredentials() throws InterruptedException {
        LoginWithInvalidPasswordCredentials(getChromeDriver(), getChromeWait(), homePageChrome, searchPageChrome, catalogPageChrome, authPageChrome, cartPageChrome, electronicsPageChrome, checkoutPageChrome, wishlistPageChrome);

        LoginWithInvalidPasswordCredentials(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, searchPageFirefox, catalogPageFirefox, authPageFirefox, cartPageFirefox, electronicsPageFirefox, checkoutPageFirefox, wishlistPageFirefox);


    }
    private void LoginWithInvalidPasswordCredentials(WebDriver driver,
                                                  org.openqa.selenium.support.ui.WebDriverWait wait,
                                                  HomePage homePage,
                                                  SearchPage searchPage,
                                                  CatalogPage catalogPage,
                                                  AuthPage authPage,
                                                  CartPage cartPage,
                                                  ElectronicsPage electronicsPage,
                                                  CheckoutPage checkoutPage,
                                                  WishlistPage wishlistPage) throws InterruptedException {


        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a\n")
        ));

        element.click();
        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        String incorrectLogin = "lomaniinoss";
        authPage.enterLoginField(incorrectLogin);

        authPage.enterPasswordField("90000000000");

        Thread.sleep(1000);

        WebElement marketTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"field:input-passwd:hint\"]\n")));
        String titleText = marketTitle.getText();
        System.out.println(titleText);
        assertTrue(titleText.startsWith("Неверный"), titleText);
    }

    @Test
    @Order(10)
    public void testLoginWithNoPassword() throws InterruptedException {
        LoginWithNoPassword(getChromeDriver(), getChromeWait(), homePageChrome, searchPageChrome, catalogPageChrome, authPageChrome, cartPageChrome, electronicsPageChrome, checkoutPageChrome, wishlistPageChrome);

        LoginWithNoPassword(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, searchPageFirefox, catalogPageFirefox, authPageFirefox, cartPageFirefox, electronicsPageFirefox, checkoutPageFirefox, wishlistPageFirefox);


    }
    private void LoginWithNoPassword(WebDriver driver,
                                                     org.openqa.selenium.support.ui.WebDriverWait wait,
                                                     HomePage homePage,
                                                     SearchPage searchPage,
                                                     CatalogPage catalogPage,
                                                     AuthPage authPage,
                                                     CartPage cartPage,
                                                     ElectronicsPage electronicsPage,
                                                     CheckoutPage checkoutPage,
                                                     WishlistPage wishlistPage) throws InterruptedException {


        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a\n")
        ));

        element.click();
        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        String incorrectLogin = "lomaniinoss";
        authPage.enterLoginField(incorrectLogin);

        authPage.enterPasswordField("");

        Thread.sleep(1000);

        WebElement marketTitle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"field:input-passwd:hint\"]\n")));
        String titleText = marketTitle.getText();
        System.out.println(titleText);
        assertTrue(titleText.startsWith("Пароль"), titleText);
    }

    @Test
    @Order(11)
    public void productBuyTest() throws InterruptedException {
        productBuy(getChromeDriver(), getChromeWait(), homePageChrome, searchPageChrome, catalogPageChrome, authPageChrome, cartPageChrome, electronicsPageChrome, checkoutPageChrome, wishlistPageChrome);

        productBuy(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, searchPageFirefox, catalogPageFirefox, authPageFirefox, cartPageFirefox, electronicsPageFirefox, checkoutPageFirefox, wishlistPageFirefox);


    }
    private void productBuy(WebDriver driver,
                                     org.openqa.selenium.support.ui.WebDriverWait wait,
                                     HomePage homePage,
                                     SearchPage searchPage,
                                     CatalogPage catalogPage,
                                     AuthPage authPage,
                                     CartPage cartPage,
                                     ElectronicsPage electronicsPage,
                                     CheckoutPage checkoutPage,
                                     WishlistPage wishlistPage) throws InterruptedException {


        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(
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
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/header/div[1]/div/noindex")));
        driver.get("https://market.yandex.ru/");

        Thread.sleep(2000);

        homePage.clickBelowMarketButton();

        WebElement firstGood = driver.findElement(By.xpath("//*[@id='superprice_remix_desktop_RecommendationRoll']//a[@data-auto='snippet-link']"));
        String url = firstGood.getAttribute("href");

        // Вместо клика открываем ссылку в текущем окне
        driver.get(url);
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"/content/page/fancyPage/defaultPage/mainDO/actions\"]/div/div/div/div[2]/div/button")));

        cartPage.clickInCardButton();



        Thread.sleep(5000);
        homePage.clickCartButton();

        Thread.sleep(2000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"cartCheckoutButton\"]")));
        cartPage.clickCheckoutButton();

        String titleText = checkoutPage.getTitle().getText();

        assertTrue(titleText.startsWith("Оформление"), titleText);


    }


    @Test
    @Order(12)
    public void productBuyInstantTest() throws InterruptedException {
        productBuyInstantTest(getChromeDriver(), getChromeWait(), homePageChrome, searchPageChrome, catalogPageChrome, authPageChrome, cartPageChrome, electronicsPageChrome, checkoutPageChrome, wishlistPageChrome);

        productBuyInstantTest(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, searchPageFirefox, catalogPageFirefox, authPageFirefox, cartPageFirefox, electronicsPageFirefox, checkoutPageFirefox, wishlistPageFirefox);


    }
    private void productBuyInstantTest(WebDriver driver,
                            org.openqa.selenium.support.ui.WebDriverWait wait,
                            HomePage homePage,
                            SearchPage searchPage,
                            CatalogPage catalogPage,
                            AuthPage authPage,
                            CartPage cartPage,
                            ElectronicsPage electronicsPage,
                            CheckoutPage checkoutPage,
                            WishlistPage wishlistPage) throws InterruptedException {


        homePage.clickBelowMarketButton();





        Thread.sleep(2000);

        WebElement firstGood = driver.findElement(By.xpath("//*[@id='superprice_remix_desktop_RecommendationRoll']//a[@data-auto='snippet-link']"));
        String url = firstGood.getAttribute("href");

        driver.get(url);


        Thread.sleep(2000);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"/content/page/fancyPage/defaultPage/mainDO/actions\"]/div/div/div/div[1]/button[1]")));

        cartPage.clickInstantBuyButton();

        String titleText = checkoutPage.getTitle().getText();

        assertTrue(titleText.startsWith("Оформление"), titleText);


    }


    @Test
    @Order(13)
    public void addToWishListTest() throws InterruptedException {
        addToWishList(getChromeDriver(), getChromeWait(), homePageChrome, searchPageChrome, catalogPageChrome, authPageChrome, cartPageChrome, electronicsPageChrome, checkoutPageChrome, wishlistPageChrome);

        addToWishList(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, searchPageFirefox, catalogPageFirefox, authPageFirefox, cartPageFirefox, electronicsPageFirefox, checkoutPageFirefox, wishlistPageFirefox);


    }
    private void addToWishList(WebDriver driver,
                                       org.openqa.selenium.support.ui.WebDriverWait wait,
                                       HomePage homePage,
                                       SearchPage searchPage,
                                       CatalogPage catalogPage,
                                       AuthPage authPage,
                                       CartPage cartPage,
                                       ElectronicsPage electronicsPage,
                                       CheckoutPage checkoutPage,
                                       WishlistPage wishlistPage) throws InterruptedException {


        homePage.clickBelowMarketButton();

        Thread.sleep(2000);

        WebElement firstGood = driver.findElement(By.xpath("//*[@id='superprice_remix_desktop_RecommendationRoll']//a[@data-auto='snippet-link']"));
        String url = firstGood.getAttribute("href");


        driver.get(url);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"/content/page/fancyPage/defaultPage/mainDO/actions\"]/div/div/div/div[1]/button[1]")));

        cartPage.clickWishlistButton();

        //далее нужно запомнить название товара который потом будет искать в вишлисте
        // xpath элемента названия
        // Получаем название товара перед добавлением в вишлист
        WebElement titleElement = driver.findElement(
                By.xpath("//*[@id=\"/content/page/fancyPage/defaultPage/productTitle\"]/div/div/h1")
        );
        String productTitle = titleElement.getText().trim();
        homePage.clickWishlistButton();

        // Ждём, пока вишлист загрузится
        WebElement wishlistDiv = wishlistPage.getWishListDiv();
        wait.until(ExpectedConditions.visibilityOf(wishlistDiv));

        WebElement foundItem = findWishListItemByName(productTitle, wishlistPage);
        assertNotNull(foundItem, "Товар не найден в избранном: " + productTitle);




    }

    public WebElement findWishListItemByName(String targetName, WishlistPage wishlistPage) {
        WebElement wishListDiv = wishlistPage.getWishListDiv();
        List<WebElement> spans = wishListDiv.findElements(By.xpath(".//span"));

        for (WebElement span : spans) {
            System.out.println(span.getText());
            String text = span.getText().trim();
            if (text.equalsIgnoreCase(targetName)) {
                return span;
            }
        }

        return null;
    }


}
