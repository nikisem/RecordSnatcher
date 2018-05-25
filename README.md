## Record Snatcher (beta)

Record Snatcher is a power search tool for music collectors. It gets you the latest listings from 19 different eBay sites and huuto.net.

## Motivation

Online auctions and marketplaces are the cornerstone of modern day record collecting.
Many of these services run on a "first come, first served" -principle.
One of the biggest platforms for buying records is eBay which has its own sites for several countries all around the world.
Currently it is impossible to get all globally available search results from a single eBay site. If you search for the same keyword on eBays of two different countries the majority of the results will be identical but some of them will be unique to the specific site.
Because of all these factors serious record collectors have to manually check many different websites several times a day in order to be the first to spot and buy those rare gems.

This is where Record Snatcher comes in and does all that manual work for its user. 
It fetches data from the following sites:
- eBay.com
- eBay UK
- eBay Austria
- eBay Belgium 
- eBay France
- eBay Germany
- eBay Ireland
- eBay Italy
- eBay Netherland
- eBay Poland
- eBay Spain
- eBay Switzerland
- eBay Australia
- eBay Hong Kong
- eBay India
- eBay Malaysia
- eBay Philippines
- eBay Singapore
- eBay Canada
- huuto.net (Finland)

After that all the duplicate items are removed and remaining results are displayed to the user from newest to oldest. And to better keep track of things all the listing times are converted to user's local time zone.

You can try out the [Record Snatcher beta app here](http://recordsnatcher.s3-website.eu-central-1.amazonaws.com).

## Additional Features
In addition to the above user can add filter keywords to the search. Items containing those keywords in the title will be filtered out from the search results.

There is also a mailer feature which is currently publicly unavailable but you can take a look at the code in the server's Mailer class. 
Mailer feature automatically fetches data every 10 minutes based on a main keyword predetermined by the user. If any of the fetched items contain some of the additional user predetermined keywords in their titles the mailer will send user an email notification containing the details of those items. 

## Built With
- [Java 8](http://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html)
- [Spring Boot](https://projects.spring.io/spring-boot/)
- [JSoup](https://jsoup.org)
- [Maven](https://maven.apache.org)
- [React](https://reactjs.org)
- [Bootstrap](https://getbootstrap.com)