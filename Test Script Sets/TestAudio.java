import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import static org.junit.Assert.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class TestAudio {

    public static final String ENGLISH_LANGUAGE = "English";
    public static final String HINDI_LANGUAGE = "Hindi";
    public static final String MARATHI_LANGUAGE = "Marathi";
    public static final String KANNADA_LANGUAGE = "Kannada";
    public static final String GUJARATI_LANGUAGE = "Gujarati";

    public static final String ENGLISH = "English (India)";
    public static final String HINDI = "हिन्दी (भारत)";
    public static final String MARATHI = "मराठी (भारत)";
    public static final String KANNADA = "ಕನ್ನಡ (ಭಾರತ)";
    public static final String GUJARATI = "ગુજરાતી (ભારત)";

    public static void playMusicWav(String filepath) {
        InputStream music;
        try{
            music = new FileInputStream(new File(filepath));
            AudioStream audio = new AudioStream(music);
            AudioPlayer.player.start(audio);
            Thread.sleep(15000);
            AudioPlayer.player.stop(audio);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void getLanguageFolders(String folderpath) throws Exception {
        List<String> pathnames;

        // Creates a new File instance by converting the given pathname string
        // into an abstract pathname
        File f = new File(folderpath);

        // Populates the array with names of files and directories
        pathnames = Arrays.asList(f.list());

        // For each pathname in the pathnames array
        for (String folder : pathnames) {
        // Print the names of files and directories
            switch (folder) {
                case ENGLISH_LANGUAGE:
                    getFilesInAFolder(folderpath + folder, ENGLISH);
                    break;
                case HINDI_LANGUAGE:
                    getFilesInAFolder(folderpath + folder, HINDI);
                    break;
                case KANNADA_LANGUAGE:
                    getFilesInAFolder(folderpath + folder, KANNADA);
                    break;
                case MARATHI_LANGUAGE:
                    getFilesInAFolder(folderpath + folder, MARATHI);
                    break;
                case GUJARATI_LANGUAGE:
                    getFilesInAFolder(folderpath + folder, GUJARATI);
                    break;
                default:
                    getFilesInAFolder(folderpath + folder, ENGLISH);
            }
        }
    }

    public static void getFilesInAFolder(String subfolderpath, String language) throws Exception {
        List<String> pathnames;

        // Creates a new File instance by converting the given pathname string
        // into an abstract pathname
        File f = new File(subfolderpath);

        // Populates the array with names of files and directories
        pathnames = Arrays.asList(f.list());
        for (String file : pathnames) {
            // Print the names of files and directories
            new TestAudio().performAutomation(subfolderpath + "\\" + file, language, file);
        }
    }

    public void performAutomation(String pathname, String language, String file) throws Exception {
        try {
            DesiredCapabilities dc = new DesiredCapabilities();

            dc.setCapability(MobileCapabilityType.DEVICE_NAME, "OnePlus 5T");
            dc.setCapability("udid", "2f2e11ea");
            dc.setCapability("platformName", "Android");
            dc.setCapability("appPackage", "com.speechtexter.speechtexter");
            dc.setCapability("appActivity", ".MainActivity");
            dc.setCapability("automationName", "UiAutomator1");

            AndroidDriver<AndroidElement> ad = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), dc);

            MobileElement el1 = (MobileElement) ad.findElementById("com.speechtexter.speechtexter:id/action_lang_change");
            el1.click();
            MobileElement selectedLanguage = (MobileElement) ad.findElement(
                    MobileBy.AndroidUIAutomator(
                            "new UiScrollable(new UiSelector()).scrollIntoView("
                                    + "new UiSelector().text(\"" + language + "\"));"));
            selectedLanguage.click();
            MobileElement el2 = (MobileElement) ad.findElementById("android:id/button1");
            el2.click();
            MobileElement el3 = (MobileElement) ad.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.view.ViewGroup/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.LinearLayout[3]/android.view.View");
            el3.click();
            MobileElement el4 = (MobileElement) ad.findElementById("com.android.packageinstaller:id/permission_allow_button");
            el4.click();
            MobileElement el5 = (MobileElement) ad.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.view.ViewGroup/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.LinearLayout[3]/android.view.View");
            el5.click();

            playMusicWav(pathname);

            if( language == KANNADA && file == "Long sentence") {
                assertEquals(ad.findElementById("com.speechtexter.speechtexter:id/txt").getText(), "ನನ್ನ ಹೆಸರು ವರ್ಷ ನನಗೆ ನಾಯಿಗಳ ಕಂಡ್ರೆ ತುಂಬಾ ಇಷ್ಟ ನನ್ನ ಹೆಸರು ವರ್ಷ ನನಗೆ ನಾಯಿಗಳ ಕಂಡ್ರೆ ತುಂಬಾ ಇಷ್ಟ");
            } else if( language == ENGLISH && file == "Short sentence") {
                assertEquals(ad.findElementById("com.speechtexter.speechtexter:id/txt").getText(), "Go to the marketplace and buy a dollhouse go to the marketplace and buy a dollhouse");
            } else if( language == HINDI && file == "Short sentence") {
                assertEquals(ad.findElementById("com.speechtexter.speechtexter:id/txt").getText(), "क्या कर रहे हो आप क्या कर रहे हो आप");
            } else if( language == MARATHI && file == "Marathi") {
                assertEquals(ad.findElementById("com.speechtexter.speechtexter:id/txt").getText(), "हॅलो माझं नाव कल्याणी आहे हॅलो माझं नाव कल्याणी आहे");
            } else if( language == GUJARATI && file == "Short sentence") {
                assertEquals(ad.findElementById("com.speechtexter.speechtexter:id/txt").getText(), "કેમ છો કેમ છો");
            }

            MobileElement el6 = (MobileElement) ad.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.view.ViewGroup/android.widget.RelativeLayout/android.view.ViewGroup/android.widget.LinearLayout[3]/android.view.View");
            el6.click();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

}
