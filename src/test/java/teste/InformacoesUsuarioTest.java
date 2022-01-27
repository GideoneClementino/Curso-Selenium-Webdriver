package teste;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class InformacoesUsuarioTest {
    private WebDriver navegador;

    @Before
    public void setUp() {
        //Abrindo navegador
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\driver\\chromedriver.exe");
        navegador = new ChromeDriver();

        //Aguarda os elementos na pagina
        navegador.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Navegando para a pagina do Taskit
        navegador.get("http://www.juliodelima.com.br/taskit");



    }

    @Test
    public void testAdicionarUmaInformacaoAdicionalDoUsuario(){
        //Clicar no link que possui o texto "Sign in"
        navegador.findElement(By.linkText("Sign in")).click();

        // Identificando o formulario de login
        WebElement formularioSignInBox = navegador.findElement(By.id("signinbox"));

        formularioSignInBox.findElement(By.name("login")).sendKeys("gideone001");
        formularioSignInBox.findElement(By.name("password")).sendKeys("12345");

        navegador.findElement(By.linkText("SIGN IN")).click();

    }

    @After
    public void tearDown() {
        //navegador.quit();
    }
}
