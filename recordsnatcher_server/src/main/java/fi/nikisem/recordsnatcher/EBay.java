package fi.nikisem.recordsnatcher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

import static java.time.LocalDateTime.now;


public class EBay {


    public ArrayList<Item> fetchData(String keyword, String offset) {

        String baseURL = "/sch/11233/i.html?_from=R40&_sop=10&_ipg=200&_nkw=" + keyword + "&rt=nc&LH_PrefLoc=1&_trksid=p2045573.m1684";
        String[] com = {"com", "https://www.ebay.com/sch/Music/11233/i.html?_from=R40&_sop=10&_nkw=" + keyword + "&_ipg=200&rt=nc", "New Listing", offset};
        String[] uk = {"uk", "https://www.ebay.co.uk/sch/11233/i.html?_from=R40&_nkw=" + keyword + "&_sop=10&_ipg=200", "New listing", offset};
        String[] austria = {"austria", "https://www.ebay.at" + baseURL, "Neues Angebot", offset};
        String[] belgium = {"belgium", "https://www.befr.ebay.be" + baseURL, "Nouvelle annonce", offset};
        String[] france = {"france", "https://www.ebay.fr" + baseURL, "Nouvelle annonce", offset};
        String[] germany = {"germany", "https://www.ebay.de" + baseURL, "Neues Angebot", offset};
        String[] ireland = {"ireland", "https://www.ebay.ie" + baseURL, "New listing", offset};
        String[] italy = {"italy", "https://www.ebay.it" + baseURL, "Nuova inserzione", offset};
        String[] netherland = {"netherland", "https://www.ebay.nl" + baseURL, "Nieuwe aanbieding", offset};
        String[] poland = {"poland", "https://www.ebay.pl" + baseURL, "Nowa oferta", offset};
        String[] spain = {"spain", "https://www.ebay.es" + baseURL, "Nuevo anuncio", offset};
        String[] switzerland = {"switzerland", "https://www.ebay.ch" + baseURL, "Neues angebot", offset};

        String[] australia = {"australia", "https://www.ebay.com.au" + baseURL, "New listing", offset};
        String[] hongKong = {"hong kong", "https://www.ebay.com.hk/sch/Music/11233/i.html?_from=R40&_sop=10&_ipg=200&_nkw=" + keyword + "&rt=nc&LH_PrefLoc=4&_trksid=p2045573.m1684", "新刊登物品", offset};
        String[] india = {"india", "https://www.ebay.in/sch/i.html?_odkw=" + keyword + "&LH_PrefLoc=7&_sop=10&_osacat=0&_from=R40&_trksid=p2045573.m570.l1313.TR0.TRC0.H0.TRS0&_nkw=" + keyword + "&_sacat=11232", "New listing", offset};
        String[] malaysia = {"malaysia", "https://www.ebay.com.my" + baseURL, "New listing", offset};
        String[] philippines = {"philippines", "https://www.ebay.ph/sch/i.html?LH_PrefLoc=1&_sop=10&_from=R40&_trksid=m570.l1313&_nkw=" + keyword + "&_sacat=11233", "New listing", offset};
        String[] singapore = {"singapore", "https://www.ebay.com.sg" + baseURL, "New listing", offset};

        String[] canada = {"canada", "https://www.ebay.ca" + baseURL, "New listing", offset};

        ArrayList<Item> eBayList = new ArrayList<>();
        ArrayList<ArrayList<Item>> tempList = new ArrayList<>();

        eBayList.addAll(get(com));
        tempList.add(get(uk));
        tempList.add(get(austria));
        tempList.add(get(belgium));
        tempList.add(get(france));
        tempList.add(get(germany));
        tempList.add(get(ireland));
        tempList.add(get(italy));
        tempList.add(get(netherland));
        tempList.add(get(poland));
        tempList.add(get(spain));
        tempList.add(get(switzerland));
        tempList.add(get(australia));
        tempList.add(get(hongKong));
        tempList.add(get(india));
        tempList.add(get(malaysia));
        tempList.add(get(philippines));
        tempList.add(get(singapore));
        tempList.add(get(canada));

        int dublicates = 0;


        for (int i = 0; i < tempList.size(); i++) {

            for (int j = 0; j < tempList.get(i).size(); j++) {

                for (Iterator<Item> it = eBayList.iterator(); it.hasNext(); ) {
                    if (tempList.get(i).get(j).getItemID().equals(it.next().getItemID())) {

                        it.remove();
                        dublicates++;

                    }

                }

                eBayList.add(tempList.get(i).get(j));

            }

        }

        System.out.println("Dublicates removed: " + dublicates);

        return eBayList;

    }


    public ArrayList<Item> get(String[] country) {

        Document doc = null;

        try {

            doc = Jsoup.connect(country[1]).get();

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println("Data fetch from " + country[0] + " failed.");

        }

        ArrayList<Item> singleSourceList = new ArrayList<>();

        Elements titles = null;
        Elements images;
        Elements links;
        Elements timeStamps;
        Elements prices;
        Elements notEnoughResults = null;
        Elements noResults;

        String title = null;
        String image = null;
        String link = null;
        String itemID;
        String source;
        String timeStampString;
        LocalDateTime timeStamp;
        String price;
        int loop;

        if (doc.select("h3").hasClass("s-item__title")) {

            System.out.println(country[0] + " used new code.");

            titles = doc.select("h3.s-item__title");
            images = doc.select("img.s-item__image-img");
            links = doc.select("a.s-item__link");
            timeStamps = doc.select("span.s-item__listingDate");
            prices = doc.select("span.s-item__price");
            notEnoughResults = doc.select("div.srp-message");
            noResults = doc.select("h1.srp-controls__count-heading");
            String itemsFound;
            Boolean zeroResults = false;
            loop = titles.size();

            if (notEnoughResults.size() > 0) {

                if (noResults.size() > 0) {

                    itemsFound = noResults.get(0).text();

                    if (itemsFound.charAt(0) == '0') {

                        zeroResults = true;

                    } else {

                        itemsFound = notEnoughResults.get(0).text().substring(0, itemsFound.indexOf(" "));

                        if (itemsFound.charAt(0) == '0') {

                            zeroResults = true;

                        } else {

                            itemsFound = noResults.get(0).text().substring(0, noResults.get(0).text().indexOf(" "));

                            if (itemsFound.contains(".")) {

                                itemsFound = itemsFound.replace(".", "");

                            }
                            if (itemsFound.contains(",")) {

                                itemsFound = itemsFound.replace(",", "");

                            }

                            if (Integer.parseInt(itemsFound) < 200) {

                                loop = Integer.parseInt(itemsFound);

                            }

                        }

                    }

                }

            }

            for (int i = 0; i < loop; i++) {

                if (zeroResults) {

                    System.out.println("No search results from " + country[0]);
                    break;

                } else {

                    title = titles.get(i).text();

                    if (title.toLowerCase().contains(country[2].toLowerCase())) {

                        title = title.replace(country[2], "");

                    }

                    if (images.get(i).hasAttr("data-src")) {

                        image = images.get(i).attr("data-src");

                    } else {

                        image = images.get(i).attr("src");

                    }

                    link = links.get(i).select("a").attr("href");

                    itemID = link.substring(link.lastIndexOf("/") + 1, link.indexOf("?"));

                    source = country[0];

                    timeStampString = timeStamps.get(i).children().text();

                    timeStamp = parseDate(timeStampString, country);

                    price = prices.get(i).text();

                    singleSourceList.add(new Item(title, image, link, itemID, price, source, timeStamp));

                }
            }

        } else {

            System.out.println(country[0] + " used old code.");

            titles = doc.select("h3.lvtitle");
            images = doc.select("div.lvpic");
            timeStamps = doc.select("li.timeleft");
            prices = doc.select("li.lvprice");
            notEnoughResults = doc.select("li.lvresult");
            noResults = doc.select("p.sm-md");

            if (noResults.size() > 0) {

                System.out.println("No search results from " + country[0]);

            }

            for (int i = 0; i < titles.size(); i++) {

                if (notEnoughResults.get(i).children().hasClass("sifExp") || noResults.size() > 0) {

                    break;

                } else {

                    if (titles.get(i).children().text().contains(country[2])) {

                        title = titles.get(i).children().text().replace(country[2] + " ", "");

                    } else {

                        title = titles.get(i).text();

                    }

                    if (!images.get(i).select("img").hasAttr("imgurl")) {

                        if (!images.get(i).select("img").hasClass("stockImg")) {

                            image = images.get(i).select("img").attr("src");

                        } else {

                            image = "NO IMAGE AVAILABLE";

                        }

                    } else {

                        if (!images.get(i).select("img").hasClass("stockImg")) {

                            image = images.get(i).select("img").attr("imgurl");

                        } else {

                            image = "NO IMAGE AVAILABLE";

                        }

                    }

                    link = images.get(i).select("a").attr("href");

                    itemID = images.get(i).attr("iid");

                    source = country[0];

                    if (!timeStamps.get(i).select("span").hasClass("alert")) {

                        timeStampString = timeStamps.get(i).select("span.red").text();

                    } else {

                        timeStampString = timeStamps.get(i).select("span.alert").text();

                    }

                    timeStamp = parseDate(timeStampString, country);

                    price = prices.get(i).children().text();

                    singleSourceList.add(new Item(title, image, link, itemID, price, source, timeStamp));

                }

            }

        }

        return singleSourceList;

    }


    public LocalDateTime parseDate(String timeStampString, String[] country) {

        LocalDateTime timeStamp = null;
        String offsetString = country[3];

        if (!offsetString.contains("-") && !offsetString.contains("+") && !offsetString.contains("Z")) {

            offsetString = "+" + offsetString;

        }

        ZoneOffset offset = ZoneOffset.of(offsetString);
        ZoneId newZone = ZoneId.ofOffset("UTC", offset);

        int year = Calendar.getInstance().get(Calendar.YEAR);
        LocalDateTime now = now(ZoneId.of("UTC"));
        OffsetDateTime timeUtc = now.atOffset(ZoneOffset.UTC);
        OffsetDateTime offsetTime = timeUtc.withOffsetSameInstant(offset);
        now = offsetTime.toLocalDateTime();

        if (country[0].equals("com") || country[0].equals("australia")) {

            DateTimeFormatter formatter = null;

            if (country[0].equals("com")) {

                timeStampString = timeStampString.replace(" ", "-" + year + " ");
                formatter = DateTimeFormatter.ofPattern("MMM-d-yyyy HH:mm", Locale.US);

                timeStamp = parseTimeStamp(timeStampString, formatter, country[0]);

                LocalDateTime oldDateTime = timeStamp;
                ZoneId oldZone = ZoneId.of("US/Pacific");
                timeStamp = oldDateTime.atZone(oldZone)
                        .withZoneSameInstant(newZone)
                        .toLocalDateTime();
            }

            if (country[0].equals("australia")) {

                if (Character.isDigit(timeStampString.charAt(0))) {

                    timeStampString = timeStampString.replace(" ", "-" + year + " ");
                    formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm", Locale.UK);

                } else {

                    timeStampString = timeStampString.replace(" ", "-" + year + " ");
                    formatter = DateTimeFormatter.ofPattern("MMM-d-yyyy HH:mm", Locale.US);

                }

                timeStamp = parseTimeStamp(timeStampString, formatter, country[0]);


                LocalDateTime oldDateTime = timeStamp;
                ZoneId oldZone = ZoneId.of("Australia/Sydney");
                timeStamp = oldDateTime.atZone(oldZone)
                        .withZoneSameInstant(newZone)
                        .toLocalDateTime();
            }

        }

        if (country[0].equals("uk") || country[0].equals("ireland") || country[0].equals("canada") || country[0].equals("india")) {

            timeStampString = timeStampString.replace(" ", "-" + year + " ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm", Locale.UK);

            timeStamp = parseTimeStamp(timeStampString, formatter, country[0]);

            if (country[0].equals("uk")) {

                LocalDateTime oldDateTime = timeStamp;
                ZoneId oldZone = ZoneId.of("Europe/London");
                timeStamp = oldDateTime.atZone(oldZone)
                        .withZoneSameInstant(newZone)
                        .toLocalDateTime();
            }

            if (country[0].equals("ireland")) {

                LocalDateTime oldDateTime = timeStamp;
                ZoneId oldZone = ZoneId.of("Europe/Dublin");
                timeStamp = oldDateTime.atZone(oldZone)
                        .withZoneSameInstant(newZone)
                        .toLocalDateTime();
            }

            if (country[0].equals("canada")) {

                LocalDateTime oldDateTime = timeStamp;
                ZoneId oldZone = ZoneId.of("Canada/Eastern");
                timeStamp = oldDateTime.atZone(oldZone)
                        .withZoneSameInstant(newZone)
                        .toLocalDateTime();
            }

            if (country[0].equals("india")) {

                LocalDateTime oldDateTime = timeStamp;
                ZoneId oldZone = ZoneId.of("Asia/Kolkata");
                timeStamp = oldDateTime.atZone(oldZone)
                        .withZoneSameInstant(newZone)
                        .toLocalDateTime();
            }

        }

        if (country[0].equals("france") || country[0].equals("belgium")) {

            timeStampString = timeStampString.replace(" ", "-" + year + " ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm", Locale.FRANCE);

            timeStamp = parseTimeStamp(timeStampString, formatter, country[0]);

            if (country[0].equals("france")) {

                LocalDateTime oldDateTime = timeStamp;
                ZoneId oldZone = ZoneId.of("Europe/Paris");
                timeStamp = oldDateTime.atZone(oldZone)
                        .withZoneSameInstant(newZone)
                        .toLocalDateTime();
            }

            if (country[0].equals("belgium")) {

                LocalDateTime oldDateTime = timeStamp;
                ZoneId oldZone = ZoneId.of("Europe/Brussels");
                timeStamp = oldDateTime.atZone(oldZone)
                        .withZoneSameInstant(newZone)
                        .toLocalDateTime();
            }

        }

        if (country[0].equals("germany") || country[0].equals("switzerland")) {

            String first = timeStampString.substring(0, 8);
            if (first.contains("Mrz")) {
                first = first.replace("Mrz", "Mär");
            }

            String last = timeStampString.substring(9, timeStampString.length());
            timeStampString = first + " " + year + " " + last;
            timeStampString = timeStampString.replace(". ", ".");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy HH:mm", Locale.GERMANY);

            timeStamp = parseTimeStamp(timeStampString, formatter, country[0]);

            if (country[0].equals("germany")) {

                LocalDateTime oldDateTime = timeStamp;
                ZoneId oldZone = ZoneId.of("Europe/Berlin");
                timeStamp = oldDateTime.atZone(oldZone)
                        .withZoneSameInstant(newZone)
                        .toLocalDateTime();
            }

            if (country[0].equals("switzerland")) {

                LocalDateTime oldDateTime = timeStamp;
                ZoneId oldZone = ZoneId.of("Europe/Zurich");
                timeStamp = oldDateTime.atZone(oldZone)
                        .withZoneSameInstant(newZone)
                        .toLocalDateTime();
            }

        }

        if (country[0].equals("italy")) {

            timeStampString = timeStampString.replace(" ", "-" + year + " ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm", Locale.ITALY);

            timeStamp = parseTimeStamp(timeStampString, formatter, country[0]);

            LocalDateTime oldDateTime = timeStamp;
            ZoneId oldZone = ZoneId.of("Europe/Rome");
            timeStamp = oldDateTime.atZone(oldZone)
                    .withZoneSameInstant(newZone)
                    .toLocalDateTime();
        }

        if (country[0].equals("austria")) {

            String first = timeStampString.substring(0, 8);
            String last = timeStampString.substring(9, timeStampString.length());
            timeStampString = first + " " + year + " " + last;
            timeStampString = timeStampString.replace(". ", ".");
            Locale austria = new Locale("de", "AT");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy HH:mm", austria);

            timeStamp = parseTimeStamp(timeStampString, formatter, country[0]);

            LocalDateTime oldDateTime = timeStamp;
            ZoneId oldZone = ZoneId.of("Europe/Vienna");
            timeStamp = oldDateTime.atZone(oldZone)
                    .withZoneSameInstant(newZone)
                    .toLocalDateTime();
        }

        if (country[0].equals("netherland")) {

            timeStampString = timeStampString.replace(" ", "-" + year + " ");
            Locale netherland = new Locale("nl", "NL");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm", netherland);

            timeStamp = parseTimeStamp(timeStampString, formatter, country[0]);

            LocalDateTime oldDateTime = timeStamp;
            ZoneId oldZone = ZoneId.of("Europe/Amsterdam");
            timeStamp = oldDateTime.atZone(oldZone)
                    .withZoneSameInstant(newZone)
                    .toLocalDateTime();
        }

        if (country[0].equals("poland")) {

            timeStampString = timeStampString.replace(" ", "-" + year + " ");
            Locale poland = new Locale("pl", "PL");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm", poland);

            timeStamp = parseTimeStamp(timeStampString, formatter, country[0]);

            LocalDateTime oldDateTime = timeStamp;
            ZoneId oldZone = ZoneId.of("Europe/Warsaw");
            timeStamp = oldDateTime.atZone(oldZone)
                    .withZoneSameInstant(newZone)
                    .toLocalDateTime();
        }

        if (country[0].equals("spain")) {

            timeStampString = timeStampString.replace(" ", "-" + year + " ");
            Locale spain = new Locale("es", "ES");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm", spain);

            timeStamp = parseTimeStamp(timeStampString, formatter, country[0]);

            LocalDateTime oldDateTime = timeStamp;
            ZoneId oldZone = ZoneId.of("Europe/Madrid");
            timeStamp = oldDateTime.atZone(oldZone)
                    .withZoneSameInstant(newZone)
                    .toLocalDateTime();
        }

        if (country[0].equals("hong kong")) {

            String first = timeStampString.substring(0, 9);
            String last = timeStampString.substring(10, timeStampString.length());
            timeStampString = first + " " + year + " " + last;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM 月 dd 日 yyyy HH:mm", Locale.CHINA);

            timeStamp = parseTimeStamp(timeStampString, formatter, country[0]);

            LocalDateTime oldDateTime = timeStamp;
            ZoneId oldZone = ZoneId.of("Hongkong");
            timeStamp = oldDateTime.atZone(oldZone)
                    .withZoneSameInstant(newZone)
                    .toLocalDateTime();
        }

        if (country[0].equals("malaysia") || country[0].equals("philippines") || country[0].equals("singapore")) {

            String first = timeStampString.substring(0, 6);
            String last = timeStampString.substring(7, timeStampString.length());
            timeStampString = first + " " + year + " " + last;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm", Locale.US);

            timeStamp = parseTimeStamp(timeStampString, formatter, country[0]);

            if (country[0].equals("malaysia")) {

                LocalDateTime oldDateTime = timeStamp;
                ZoneId oldZone = ZoneId.of("Asia/Kuala_Lumpur");
                timeStamp = oldDateTime.atZone(oldZone)
                        .withZoneSameInstant(newZone)
                        .toLocalDateTime();
            }

            if (country[0].equals("philippines")) {

                LocalDateTime oldDateTime = timeStamp;
                ZoneId oldZone = ZoneId.of("Asia/Manila");
                timeStamp = oldDateTime.atZone(oldZone)
                        .withZoneSameInstant(newZone)
                        .toLocalDateTime();
            }

            if (country[0].equals("singapore")) {

                LocalDateTime oldDateTime = timeStamp;
                ZoneId oldZone = ZoneId.of("Asia/Singapore");
                timeStamp = oldDateTime.atZone(oldZone)
                        .withZoneSameInstant(newZone)
                        .toLocalDateTime();
            }

        }

        if (timeStamp.isAfter(now)) {
            timeStamp = timeStamp.minusYears(1);

        }

        return timeStamp;

    }


    private LocalDateTime parseTimeStamp(String timeStampString, DateTimeFormatter formatter, String source) {

        LocalDateTime timeStamp = null;

        try {

            timeStamp = LocalDateTime.parse(timeStampString, formatter);

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Failed to parse LocalDateTime for " + source + ".");

        }

        return timeStamp;
    }

}