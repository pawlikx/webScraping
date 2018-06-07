package sample;

import com.google.common.base.Stopwatch;
import com.google.gson.*;
import javafx.scene.paint.Stop;
import org.apache.xpath.operations.Bool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;



import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class ImageScraper {

    private WebDriver driver;
    private final int DefaultNumberOfImg = 12;
    private Set<String> imgSet = new HashSet<String>();
    private Set<String> fullImgUrlSet = new HashSet<String>();



    public ImageScraper(){}
    public ImageScraper(WebDriver driver){
        this.driver = driver;
    }




    public int imageCounter(){
        Document InstaProfileHeader = Jsoup.parse(driver.getPageSource());
        Elements HeaderElements = InstaProfileHeader.select("header section.zwlfE  ul.k9GMp ");
        String NumberOfImagesInString = HeaderElements.select("li.Y8-fY span.-nal3 span.g47SY").first().text();
        int NumberOfImages = Integer.parseInt(NumberOfImagesInString.replaceAll("[ |\\t]",""));
        return NumberOfImages;
    }


    public void renderAllPhotos() throws InterruptedException, IOException {
        PageScroller scroller = new PageScroller(driver);
        int defaultStartScrollingPixels = 1290;
        int pixelsToScroll = 980;
        Integer loadedImgRows = 12;
        Integer previousNumberOfLoadedRows = 0;
        Integer imgRowsToLoad = HelperClass.numberCeiling(imageCounter()/3);


        scroller.scrollSomePixelsDown(0,pixelsToScroll);
        while(fullImgUrlSet.size() < imageCounter()){

            Thread.sleep(560);
            scroller.scrollSomePixelsDown(0,defaultStartScrollingPixels);
            defaultStartScrollingPixels+=1290;
            Thread.sleep(460);

            createImageSet(Jsoup.parse(driver.getPageSource()));
        }
        driver.quit();
        createFullResImgSet();
    }

    public void imgScrap(String pathToDownload) {
        try {
            int counter = 1;
            driver.quit();

            for (String img : mySet){
                System.out.println("Pobieram zdjęcie nr " + counter);
                imageDownloader(img,Integer.toString(counter),pathToDownload);
                counter++;
            }



        }
        catch(Exception exception){
            System.out.println("Error occurred");
        }
    }

    public void createImageSet(Document doc){

        String staticInstaLink = "https://www.instagram.com";

        Elements body = doc.select("main.SCxLW div.v9tJq");
        for (Element el : body.select("article div div div.Nnq7C")) {
            for (Element image : el.select("div.v1Nh3 a")) {
                String fullImgSiteURL = staticInstaLink + image.attr("href");
                fullImgUrlSet.add(fullImgSiteURL);
            }
        }
    }

    Set<String> mySet = Collections.newSetFromMap(new ConcurrentHashMap<String,Boolean>());

    public void createFullResImgSet() throws IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Krolik\\IdeaProjects\\insta-scraper-gradle\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("headless");
        options.addArguments("window-size=1200x600");
        //WebDriver driver = new ChromeDriver(options);
        ThreadLocal<WebDriver> driverT;
        driverT = new ThreadLocal<WebDriver>(){
            @Override
            protected WebDriver initialValue(){
                return new ChromeDriver(options);
            }
        };

        final AtomicInteger counter = new AtomicInteger(1);

        long startTimer = System.currentTimeMillis();

        fullImgUrlSet.parallelStream().forEach((url) -> {

            driverT.get().get(url);

            Document doc = Jsoup.parse(driverT.get().getPageSource());

            Elements body = doc.select("main.SCxLW article.QBXjJ");
            Elements fullImg = body.select("img.FFVAD");
            if (!fullImg.isEmpty()) {
                String fullResImgUrl = fullImg.attr("src");
                mySet.add(fullResImgUrl);
            }
            System.out.printf("Loading photos... %d/%d loaded\n",counter.get(),fullImgUrlSet.size());
            counter.getAndAdd(1);
        });

        driverT.get().quit();

        long stopTimer = System.currentTimeMillis();
        long elapsedTime = stopTimer - startTimer;
        String time = String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(elapsedTime),
                TimeUnit.MILLISECONDS.toSeconds(elapsedTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsedTime))
        );
        System.out.println(time);


    }

    public void imageDownloader(String linkToDownload,String name){
        imageDownloader(linkToDownload,name,null);
    }

    public void imageDownloader(String linkToDownload,String name,String pathToDownload){
        if(!pathToDownload.isEmpty() && pathToDownload != null) {

            String path = pathToDownload + "\\";
            path = path.replaceAll("\\\\","/");


            try{
                URL url = new URL(linkToDownload);
                InputStream is = url.openStream();
                OutputStream os = new FileOutputStream(path+name+".jpg");

                byte[] b = new byte[2048];
                int length;
                while((length = is.read(b)) != -1){
                    os.write(b,0,length);
                }

                is.close();
                os.close();

            }
            catch(Exception ex){
                System.out.println(ex);
            }

        }else{
            System.out.println("Download error");
        }

    }

    public String formatJSON(String uglyJsonString){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJsonString);
        String prettyJsonString = gson.toJson(je);

        return prettyJsonString;
    }









}
