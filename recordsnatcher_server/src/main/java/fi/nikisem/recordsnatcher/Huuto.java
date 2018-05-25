package fi.nikisem.recordsnatcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Huuto {


    public ArrayList<Item> fetchData(String keyword, String offset) {

        Document doc = null;
        ArrayList<Item> huutoList = new ArrayList<>();

        try {

            doc = Jsoup.connect("https://www.huuto.net/haku/words/" + keyword + "/sort/newest").get();

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println("Data fetch from huuto.net failed.");

        }

        Elements items = doc.select("div.grid-element-container");
        Elements links = items.select("a");
        Elements images = items.select("div.grid-element-picture");
        Elements timeStamps = items.select("span.tooltips");
        Elements titles = items.select("div.grid-element-title");
        Elements prices = items.select("div.grid-element-price-container");

        for (int i = 0; i < items.size(); i++) {

            String link = "https://www.huuto.net/" + links.get(i).attr("href");

            String image = "";

            if (!images.get(i).children().hasClass("item-list-no-image")) {

                image = images.get(i).children().attr("src");
            } else {
                image = "NO IMAGE AVAILABLE";
            }

            String title = titles.get(i).attr("title");

            String source = "huuto";

            String itemID = "huuto";

            String timeStampString = timeStamps.get(i).children().text();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy H:m");
            LocalDateTime timeStamp = LocalDateTime.parse(timeStampString, formatter);

            String offsetString = offset;

            if(!offsetString.contains("-") && !offsetString.contains("+") && !offsetString.contains("Z")) {
                offsetString = "+" + offsetString;
            }

            ZoneOffset zoneOffset = ZoneOffset.of(offsetString);
            ZoneId newZone = ZoneId.ofOffset("UTC", zoneOffset);

            LocalDateTime oldDateTime = timeStamp;
            ZoneId oldZone = ZoneId.of("Europe/Helsinki");
            timeStamp = oldDateTime.atZone(oldZone)
                    .withZoneSameInstant(newZone)
                    .toLocalDateTime();

            String price = prices.get(i).children().text();

            huutoList.add(new Item(title, image, link, itemID, price, source, timeStamp));

        }

        return huutoList;

    }

}