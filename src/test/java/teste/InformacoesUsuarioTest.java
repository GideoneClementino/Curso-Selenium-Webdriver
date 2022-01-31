package teste;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Gerador;
import suporte.Screenshot;
import java.time.Duration;
import static org.junit.Assert.assertEquals;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "InformacoesUsuarioTestData.csv")
public class InformacoesUsuarioTest {
    private WebDriver navegador;
    private WebDriverWait aguardar;

    @Rule
    public TestName test = new TestName();

    @Before
    public void setUp() {
        //Abrindo navegador
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\driver\\chromedriver.exe");
        navegador = new ChromeDriver();

        //Aguarda os elementos na pagina
        navegador.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Navegando para a pagina do Taskit
        navegador.get("http://www.juliodelima.com.br/taskit");

        //Clicar no link que possui o texto "Sign in"
        navegador.findElement(By.linkText("Sign in")).click();

        // Identificando o formulario de login
        WebElement formularioSignInBox = navegador.findElement(By.id("signinbox"));

        // Enviando informações do usuario
        formularioSignInBox.findElement(By.name("login")).sendKeys("gideone001");
        formularioSignInBox.findElement(By.name("password")).sendKeys("12345");

        navegador.findElement(By.linkText("SIGN IN")).click();
        navegador.findElement(By.xpath("//ul[@class='right hide-on-med-and-down'] //a[@class=\"me\"]")).click();
        navegador.findElement(By.xpath("//a[contains(text(), 'More data about you')]")).click();
    }

    //@Test
    public void testAdicionarUmaInformacaoAdicionalDoUsuario(@Param(name="tipo")String tipo,
                                                             @Param(name = "contato")String contato,
                                                             @Param(name = "mensagem")String mensagemEsperada){
        navegador.findElement(By.xpath("//button[@data-target='addmoredata']")).click();

        WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata"));

        //Trabalhando com select
        WebElement campoType = popupAddMoreData.findElement(By.name("type"));
        new Select(campoType).selectByVisibleText(tipo);

        popupAddMoreData.findElement(By.name("contact")).sendKeys(contato);
        popupAddMoreData.findElement(By.linkText("SAVE")).click();

        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals(mensagemEsperada, mensagem);

        aguardar = new WebDriverWait(navegador, Duration.ofSeconds(10));
        aguardar.until(ExpectedConditions.invisibilityOf(popupAddMoreData));

        String screenshotArquivo = System.getProperty("user.dir") + "\\screenshots\\" +
                Gerador.dataHoraParaArquivo() +
                test.getMethodName() + ".png";
        Screenshot.tirar(navegador, screenshotArquivo);

        aguardar = new WebDriverWait(navegador, Duration.ofSeconds(10));
        aguardar.until(ExpectedConditions.stalenessOf(mensagemPop));
    }

    @Test
    public void removerUmContatoDeUmUsuario() {
        //preceding-sibling:: irá pegar o elemento anterior, folowing-sibling o proximo
        navegador.findElement(By.xpath("//span[text() = \"+55119898981112\"]/following-sibling::a")).click();

        //confirmando uma janela java script
        navegador.switchTo().alert().accept();

        //Validar que a mensagem foi rest in peace
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals("Rest in peace, dear phone!", mensagem);

        String screenshotArquivo = "C:\\Users\\Gideone\\Documents\\Test_report\\" + Gerador.dataHoraParaArquivo() + test.getMethodName() + ".png";
        Screenshot.tirar(navegador, screenshotArquivo);

        //Aguardando que o DOM suma da tela
        WebDriverWait aguardar = new WebDriverWait(navegador, Duration.ofSeconds(10));
        aguardar.until(ExpectedConditions.stalenessOf(mensagemPop));

        navegador.findElement(By.linkText("Logout")).click();
    }

    @After
    public void tearDown() {
        navegador.quit();
    }
}
