package com.caiata.steps;

import com.caiata.utils.ManagementDriver;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BershkaSteps {

    static private AndroidDriver<?> driver = ManagementDriver.getAndroidDriver();

    public void inizializza(Properties prop){
        try {
            Thread.sleep(20000);
            driver.findElement(By.id(prop.getProperty("id.accept.cookie"))).click();
            Thread.sleep(18000);
            driver.findElement(By.id(prop.getProperty("id.btn.go"))).click();
            Thread.sleep(18000);
            driver.findElement(By.id(prop.getProperty("id.btn.continue"))).click();
            Thread.sleep(18000);
            driver.findElement(By.id(prop.getProperty("id.btn.accept"))).click();
            Thread.sleep(18000);
            driver.findElement(By.id(prop.getProperty("id.btn.localizationPermission"))).click();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addCart(Properties prop){
        try {
            Thread.sleep(9500);
            driver.findElement(By.id("com.inditex.ecommerce.bershka:id/image")).click();
            Thread.sleep(18000);
            driver.findElement(By.id("com.inditex.ecommerce.bershka:id/product_list.row_image")).click();
            Thread.sleep(18000);
            driver.findElement(By.id("com.inditex.ecommerce.bershka:id/product_detail.add_container")).click();
            Thread.sleep(8000);
            driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.RelativeLayout[1]/android.widget.RelativeLayout[1]/android.widget.RelativeLayout/android.widget.RelativeLayout/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[4]/android.widget.FrameLayout/android.widget.LinearLayout[2]/android.widget.TextView")).click();
            Thread.sleep(10000);
            driver.findElement(By.id("com.inditex.ecommerce.bershka:id/size.add")).click();
            Thread.sleep(10000);
            driver.findElement(By.id("com.inditex.ecommerce.bershka:id/toolbar.icon")).click();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void prezzo(Properties prop){
        try {
            Thread.sleep(10000);
            System.out.println("Titolo : " + driver.findElement(By.id(prop.getProperty("id.title"))).getText());
            Thread.sleep(5000);
            System.out.println("Prezzo articolo nel carrello : " + driver.findElement(By.id(prop.getProperty("id.price"))).getText());
            Thread.sleep(5000);
            System.out.println("Prezzo totale del carrello : " + driver.findElement(By.id(prop.getProperty("id.total.price"))).getText());
            Thread.sleep(5000);
            assertEquals(driver.findElement(By.id(prop.getProperty("id.total.price"))).getText(), driver.findElement(By.id(prop.getProperty("id.price"))).getText());
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void svuotaCarrello(Properties prop){
        try {
            Thread.sleep(10000);
            driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout[1]/androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.RelativeLayout[2]/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.LinearLayout[1]/android.widget.ImageView")).click();
            Thread.sleep(10000);
            driver.findElement(By.id("com.inditex.ecommerce.bershka:id/toolbar.close")).click();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public boolean indietro(Properties prop) {
        try {
            Thread.sleep(5000);
            driver.findElement(By.xpath(prop.getProperty("xpath.indietro"))).click();
            Thread.sleep(5000);
            TouchAction action = new TouchAction((PerformsTouchActions) driver);
            action.press(PointOption.point(496, 1772));
            action.moveTo(PointOption.point(536, 87));
            action.release();
            action.perform();
            Thread.sleep(5000);
            driver.findElement(By.xpath(prop.getProperty("xpath.item2"))).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

}
