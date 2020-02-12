package com.axbg.shelf.services;

import static com.axbg.shelf.config.Constants.DEFAULT_FAVICON;
import static com.axbg.shelf.config.Constants.UNSORTED_COLLECTION;

import com.axbg.shelf.dao.ItemDao;
import com.axbg.shelf.entity.Item;
import com.axbg.shelf.exception.CustomException;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemDao itemDao;
    private final UserService userService;
    private final CollectionService collectionService;

    @Override
    public Item createItem(final String url) throws CustomException {
        try {
            if (!verifyUrl(url)) {
                throw new CustomException("Invalid URL", HttpStatus.BAD_REQUEST);
            }

            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            Element favicon = doc.head().select("link[rel~=icon]").first();
            String photo = favicon != null ? getFaviconUrl(url, favicon.attr("href")) : DEFAULT_FAVICON;

            Item item = Item.builder()
                    .url(url)
                    .title(title)
                    .photo(photo)
                    .collection(collectionService.findByName(UNSORTED_COLLECTION))
                    .build();

            return itemDao.save(item);
        } catch (CustomException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CustomException("Error on page scrapping", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Item> findAllByUserAndPage(int page, int size) {
        return itemDao.findAllByUser(userService.findUser(), PageRequest.of(page, size, Sort.by("id").descending()));
    }

    @Override
    public Optional<Item> findByIdAndUser(final long id) {
        Optional<Item> item = itemDao.findById(id);
        return item.isPresent() && item.get().getCollection().getUser().equals(userService.findUser()) ?
                item : Optional.empty();
    }

    @Override
    public List<Item> searchByName(String title) {
        return itemDao.findByUserAndTitleContaining(userService.findUser(), title);
    }

    @Override
    public boolean isPresentByUrl(final String url) {
        return itemDao.findByUserAndUrl(userService.findUser(), url).isPresent();
    }

    @Override
    public void deleteItem(final long id) {
        itemDao.deleteItemById(id);
    }

    private boolean verifyUrl(String url) {
        return !url.isBlank() && (url.contains("http://") || url.contains("https://"));
    }

    private String getFaviconUrl(String fullUrl, String faviconRoute) {
        return faviconRoute.contains("http") ? faviconRoute : fullUrl.substring(0, getUrlRoot(fullUrl)) + faviconRoute;
    }

    private int getUrlRoot(String fullUrl) {
        int i = 0;
        for (int ct = 0; i < fullUrl.length(); i++) {
            if (fullUrl.charAt(i) == '/') {
                ct++;
                if (ct == 3) {
                    return i;
                }
            }
        }
        return i;
    }
}