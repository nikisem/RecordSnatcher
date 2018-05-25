package fi.nikisem.recordsnatcher;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


@RestController
@RequestMapping("/api")
public class RestControl {


    @CrossOrigin
    @GetMapping("/get")
    public List<Item> getItems(@RequestParam("keyword") String keyword, @RequestParam("offset") String offset, @RequestParam("filters") String[] filters) {

        keyword = keyword.toLowerCase();
        keyword = keyword.replace(" ", "+");

        System.out.println("Data fetching started. Searching for: " + keyword);

        ArrayList<Item> list = new ArrayList();
        ArrayList<Item> eBay = new EBay().fetchData(keyword, offset);
        ArrayList<Item> huuto = new Huuto().fetchData(keyword, offset);

        list.addAll(eBay);
        list.addAll(huuto);

        Collections.sort(list, new ItemComparator());

        for (int i = 0; i < filters.length; i++) {

            for (Iterator<Item> it = list.iterator(); it.hasNext(); ) {

                if (it.next().getTitle().toLowerCase().contains(filters[i].toLowerCase())) {

                    it.remove();

                }

            }

        }

        System.out.println("Unique itrms fetched: " + list.size());

        return list;
    }

}