import com.labwork.AuthUtils;
import com.labwork.pages.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    private static final By SEARCH_INPUT = By.cssSelector("input[name='text']");
    private static final By FIRST_RECOMMENDED_GOOD = By.xpath("//*[@id='superprice_remix_desktop_RecommendationRoll']//a[@data-auto='snippet-link']");
    private static final By PRODUCT_TITLE = By.xpath("//*[@id=\"/content/page/fancyPage/defaultPage/productTitle\"]/div/div/h1");
    private static final By ADD_TO_CART_BUTTON = By.xpath("//*[@id=\"/content/page/fancyPage/defaultPage/mainDO/actions\"]/div/div/div/div[2]/div/button");
    private static final By BUY_NOW_BUTTON = By.xpath("//*[@id=\"/content/page/fancyPage/defaultPage/mainDO/actions\"]/div/div/div/div[1]/button[1]");
    private static final By CART_BUTTON_HEADER = By.xpath("//*[@data-auto='cart-button']");

    @BeforeEach
    public void setup() {
        setUpDrivers();
        // Chrome page objects
        homePageChrome = new HomePage(getChromeDriver());
        searchPageChrome = new SearchPage(getChromeDriver());
        catalogPageChrome = new CatalogPage(getChromeDriver());
        authPageChrome = new AuthPage(getChromeDriver());
        cartPageChrome = new CartPage(getChromeDriver());
        electronicsPageChrome = new ElectronicsPage(getChromeDriver());
        checkoutPageChrome = new CheckoutPage(getChromeDriver());
        wishlistPageChrome = new WishlistPage(getChromeDriver());
        // Firefox page objects
        homePageFirefox = new HomePage(getFirefoxDriver());
        searchPageFirefox = new SearchPage(getFirefoxDriver());
        catalogPageFirefox = new CatalogPage(getFirefoxDriver());
        authPageFirefox = new AuthPage(getFirefoxDriver());
        cartPageFirefox = new CartPage(getFirefoxDriver());
        electronicsPageFirefox = new ElectronicsPage(getFirefoxDriver());
        checkoutPageFirefox = new CheckoutPage(getFirefoxDriver());
        wishlistPageFirefox = new WishlistPage(getFirefoxDriver());
        // open start pages
        getChromeDriver().get("https://market.yandex.ru/");
        getFirefoxDriver().get("https://market.yandex.ru/");
    }

    @Test
    @Order(1)
    public void searchFoundTest() {
        searchFound(getChromeDriver(), getChromeWait(), homePageChrome, searchPageChrome);
        searchFound(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, searchPageFirefox);
    }

    private void searchFound(WebDriver driver,
                             org.openqa.selenium.support.ui.WebDriverWait wait,
                             HomePage homePage,
                             SearchPage searchPage) {
        // Ждём, пока поле поиска будет кликабельно вместо фиксированного sleep
        wait.until(ExpectedConditions.elementToBeClickable(SEARCH_INPUT));

        homePage.enterSearchInput("Iphone 15 Pro");
        homePage.clickSearchButton();

        String titleText = searchPage.getSearchTitle().getText();
        assertTrue(titleText.startsWith("Iphone 15 Pro"), titleText);
    }

    @Test
    @Order(2)
    public void searchNotFoundTest() {
        searchNotFound(getChromeDriver(), getChromeWait(), homePageChrome, searchPageChrome);
        searchNotFound(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, searchPageFirefox);
    }

    private void searchNotFound(WebDriver driver,
                                org.openqa.selenium.support.ui.WebDriverWait wait,
                                HomePage homePage,
                                SearchPage searchPage) {
        wait.until(ExpectedConditions.elementToBeClickable(SEARCH_INPUT));

        homePage.enterSearchInput("sdfksflwoelrijwer");
        homePage.clickSearchButton();

        String titleText = searchPage.getEmptySearchPage().getText();
        assertTrue(titleText.startsWith("Такого у нас нет"), titleText);
    }

    @Test
    @Order(3)
    public void searchMainPageTest() {
        searchMainPage(getChromeDriver(), getChromeWait(), homePageChrome);
        searchMainPage(getFirefoxDriver(), getFirefoxWait(), homePageFirefox);
    }

    private void searchMainPage(WebDriver driver,
                                org.openqa.selenium.support.ui.WebDriverWait wait,
                                HomePage homePage) {
        // Captcha handling – ждём, пока URL перестанет содержать showcaptcha
        if (driver.getCurrentUrl().contains("showcaptcha")) {
            System.out.println("Обнаружена капча! Пройди её вручную...");
            wait.until(d -> !d.getCurrentUrl().contains("showcaptcha"));
        }

        homePage.clickBelowMarketButton();

        WebElement firstGood = wait.until(ExpectedConditions.elementToBeClickable(FIRST_RECOMMENDED_GOOD));
        String url = firstGood.getAttribute("href");
        driver.get(url);

        wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_TITLE));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("card") || currentUrl.contains("product--"),
                "URL не содержит ни 'card', ни 'product--'. Текущий URL: " + currentUrl);
    }

    @Test
    @Order(4)
    public void searchViaCatalogTest() {
        searchViaCatalog(getChromeDriver(), getChromeWait(), homePageChrome);
        searchViaCatalog(getFirefoxDriver(), getFirefoxWait(), homePageFirefox);
    }

    private void searchViaCatalog(WebDriver driver,
                                  org.openqa.selenium.support.ui.WebDriverWait wait,
                                  HomePage homePage) {
        homePage.clickCatalogButton();
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@href='/catalog--muzhskaia-odezhda/54404675']")));
        element.click();

        WebElement marketTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(@class, 'ds-text') and contains(@class, 'ds-text_headline-4_bold')]")));
        String titleText = marketTitle.getText();
        assertTrue(titleText.startsWith("Мужская одежда"), titleText);
    }

    @Test
    @Order(5)
    public void testPriceSearchFilters() {
        PriceSearchFilters(getChromeDriver(), getChromeWait(), homePageChrome, searchPageChrome);
        PriceSearchFilters(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, searchPageFirefox);
    }

    private void PriceSearchFilters(WebDriver driver,
                                    org.openqa.selenium.support.ui.WebDriverWait wait,
                                    HomePage homePage,
                                    SearchPage searchPage) {
        wait.until(ExpectedConditions.elementToBeClickable(SEARCH_INPUT));
        homePage.enterSearchInput("Iphone 15 Pro");
        homePage.clickSearchButton();

        // Ждём, пока поле ввода цены появится
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[name='min-price']")));
        searchPage.inputPriceRange("77777");

        // Цена применена – ждём появления элементов с ценами
        wait.until(driver1 -> driver1.findElements(By.cssSelector(".ds-text_color_price-term")).size() > 0);

        searchPage.clickViewTypeSelector();

        // Ждём обновления списка после смены вида отображения
        wait.until(driver1 -> driver1.findElements(By.cssSelector(".ds-text_color_price-term")).size() > 0);

        List<Integer> prices = getTop5PricesAsIntegers(driver);
        boolean allBelowLimit = prices.stream().allMatch(price -> price <= 77777);
        assertTrue(allBelowLimit, "Один или несколько товаров превышают лимит в 77777");
    }

    public List<Integer> getTop5PricesAsIntegers(WebDriver driver) {
        List<Integer> prices = new ArrayList<>();
        List<WebElement> priceElements = driver.findElements(By.cssSelector(".ds-text_color_price-term"));

        for (WebElement priceElement : priceElements) {
            String rawText = priceElement.getText().replaceAll("\\D", "");
            if (!rawText.isEmpty()) {
                try {
                    prices.add(Integer.parseInt(rawText));
                } catch (NumberFormatException ignored) {
                }
            }
            if (prices.size() == 5) break;
        }
        return prices;
    }

    @Test
    @Order(6)
    public void testLoginWithEmptyCredentials() {
        LoginWithEmptyCredentials(getChromeDriver(), getChromeWait(), homePageChrome, authPageChrome);
        LoginWithEmptyCredentials(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, authPageFirefox);
    }

    private void LoginWithEmptyCredentials(WebDriver driver,
                                           org.openqa.selenium.support.ui.WebDriverWait wait,
                                           HomePage homePage,
                                           AuthPage authPage) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a")));
        element.click();

        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        authPage.enterLoginField("");
        WebElement marketTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"field:input-login:hint\"]")));
        String titleText = marketTitle.getText();
        assertTrue(titleText.startsWith("Логин"), titleText);
    }

    @Test
    @Order(7)
    public void testLoginWhichNonExists() {
        LoginWhichNonExists(getChromeDriver(), getChromeWait(), homePageChrome, authPageChrome);
        LoginWhichNonExists(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, authPageFirefox);
    }

    private void LoginWhichNonExists(WebDriver driver,
                                     org.openqa.selenium.support.ui.WebDriverWait wait,
                                     HomePage homePage,
                                     AuthPage authPage) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a")));
        element.click();

        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        authPage.enterLoginField("testinobuggulino1337");
        WebElement marketTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"field:input-login:hint\"]")));
        String titleText = marketTitle.getText();
        assertTrue(titleText.startsWith("Нет"), titleText);
    }

    @Test
    @Order(8)
    public void testLoginWithInvalidLoginCredentials() {
        LoginWithInvalidLoginCredentials(getChromeDriver(), getChromeWait(), homePageChrome, authPageChrome);
        LoginWithInvalidLoginCredentials(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, authPageFirefox);
    }

    private void LoginWithInvalidLoginCredentials(WebDriver driver,
                                                  org.openqa.selenium.support.ui.WebDriverWait wait,
                                                  HomePage homePage,
                                                  AuthPage authPage) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a")));
        element.click();

        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        authPage.enterLoginField("~~~~~~~~~~~~~KFSKDF");
        WebElement marketTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"field:input-login:hint\"]")));
        String titleText = marketTitle.getText();
        assertTrue(titleText.startsWith("Такой"), titleText);
    }

    @Test
    @Order(9)
    public void testLoginWithInvalidPasswordCredentials() {
        LoginWithInvalidPasswordCredentials(getChromeDriver(), getChromeWait(), homePageChrome, authPageChrome);
        LoginWithInvalidPasswordCredentials(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, authPageFirefox);
    }

    private void LoginWithInvalidPasswordCredentials(WebDriver driver,
                                                     org.openqa.selenium.support.ui.WebDriverWait wait,
                                                     HomePage homePage,
                                                     AuthPage authPage) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a")));
        element.click();

        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        authPage.enterLoginField("lomaniinoss");
        authPage.enterPasswordField("90000000000");

        WebElement marketTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"field:input-passwd:hint\"]")));
        String titleText = marketTitle.getText();
        assertTrue(titleText.startsWith("Неверный"), titleText);
    }

    @Test
    @Order(10)
    public void testLoginWithNoPassword() {
        LoginWithNoPassword(getChromeDriver(), getChromeWait(), homePageChrome, authPageChrome);
        LoginWithNoPassword(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, authPageFirefox);
    }

    private void LoginWithNoPassword(WebDriver driver,
                                     org.openqa.selenium.support.ui.WebDriverWait wait,
                                     HomePage homePage,
                                     AuthPage authPage) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a")));
        element.click();

        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        authPage.enterLoginField("lomaniinoss");
        authPage.enterPasswordField("");

        WebElement marketTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"field:input-passwd:hint\"]")));
        String titleText = marketTitle.getText();
        assertTrue(titleText.startsWith("Пароль"), titleText);
    }

    @Test
    @Order(11)
    public void productBuyTest() {
        productBuy(getChromeDriver(), getChromeWait(), homePageChrome, authPageChrome, cartPageChrome, checkoutPageChrome);
        productBuy(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, authPageFirefox, cartPageFirefox, checkoutPageFirefox);
    }

    private void productBuy(WebDriver driver,
                            org.openqa.selenium.support.ui.WebDriverWait wait,
                            HomePage homePage,
                            AuthPage authPage,
                            CartPage cartPage,
                            CheckoutPage checkoutPage) {
        // Авторизация
        WebElement profileBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"/content/header/header/personalization/profile\"]/div/a")));
        profileBtn.click();
        authPage.clickMoreWaysButton();
        authPage.clickByLoginButton();

        String login = AuthUtils.getCredentialsFromFile("/home/extrzz/stuff/verySecretPassword")[0];
        String password = AuthUtils.getCredentialsFromFile("/home/extrzz/stuff/verySecretPassword")[1];
        authPage.enterLoginField(login);
        authPage.enterPasswordField(password);

        System.out.println("Пожалуйста, введите код доступа вручную и нажмите Enter.");
        Scanner scanner = new Scanner(System.in);
        String accessCode = scanner.nextLine();
        authPage.enterPhoneCodeField(accessCode);

        // Ждём возврата на главную
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/header/div[1]/div/noindex")));
        driver.get("https://market.yandex.ru/");

        // Работа с товаром
        homePage.clickBelowMarketButton();
        WebElement firstGood = wait.until(ExpectedConditions.elementToBeClickable(FIRST_RECOMMENDED_GOOD));
        driver.get(firstGood.getAttribute("href"));

        wait.until(ExpectedConditions.elementToBeClickable(ADD_TO_CART_BUTTON));
        cartPage.clickInCardButton();

        wait.until(ExpectedConditions.elementToBeClickable(CART_BUTTON_HEADER));
        homePage.clickCartButton();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("cartCheckoutButton")));
        cartPage.clickCheckoutButton();

        String titleText = checkoutPage.getTitle().getText();
        assertTrue(titleText.startsWith("Оформление"), titleText);
    }

    @Test
    @Order(12)
    public void productBuyInstantTest() {
        productBuyInstantTest(getChromeDriver(), getChromeWait(), homePageChrome, cartPageChrome, checkoutPageChrome);
        productBuyInstantTest(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, cartPageFirefox, checkoutPageFirefox);
    }

    private void productBuyInstantTest(WebDriver driver,
                                       org.openqa.selenium.support.ui.WebDriverWait wait,
                                       HomePage homePage,
                                       CartPage cartPage,
                                       CheckoutPage checkoutPage) {
        homePage.clickBelowMarketButton();
        WebElement firstGood = wait.until(ExpectedConditions.elementToBeClickable(FIRST_RECOMMENDED_GOOD));
        driver.get(firstGood.getAttribute("href"));

        wait.until(ExpectedConditions.elementToBeClickable(BUY_NOW_BUTTON));
        cartPage.clickInstantBuyButton();

        String titleText = checkoutPage.getTitle().getText();
        assertTrue(titleText.startsWith("Оформление"), titleText);
    }

    @Test
    @Order(13)
    public void addToWishListTest() {
        addToWishList(getChromeDriver(), getChromeWait(), homePageChrome, cartPageChrome, wishlistPageChrome);
        addToWishList(getFirefoxDriver(), getFirefoxWait(), homePageFirefox, cartPageFirefox, wishlistPageFirefox);
    }

    private void addToWishList(WebDriver driver,
                               org.openqa.selenium.support.ui.WebDriverWait wait,
                               HomePage homePage,
                               CartPage cartPage,
                               WishlistPage wishlistPage) {
        homePage.clickBelowMarketButton();
        WebElement firstGood = wait.until(ExpectedConditions.elementToBeClickable(FIRST_RECOMMENDED_GOOD));
        driver.get(firstGood.getAttribute("href"));

        wait.until(ExpectedConditions.elementToBeClickable(BUY_NOW_BUTTON));
        cartPage.clickWishlistButton();

        // Запоминаем название товара
        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_TITLE));
        String productTitle = titleElement.getText().trim();

        homePage.clickWishlistButton();
        WebElement wishlistDiv = wishlistPage.getWishListDiv();
        wait.until(ExpectedConditions.visibilityOf(wishlistDiv));

        WebElement foundItem = findWishListItemByName(productTitle, wishlistPage);
        assertNotNull(foundItem, "Товар не найден в избранном: " + productTitle);
    }

    public WebElement findWishListItemByName(String targetName, WishlistPage wishlistPage) {
        WebElement wishListDiv = wishlistPage.getWishListDiv();
        List<WebElement> spans = wishListDiv.findElements(By.xpath(".//span"));
        return spans.stream()
                .filter(span -> span.getText().trim().equalsIgnoreCase(targetName))
                .findFirst()
                .orElse(null);
    }
}
