package com.axbg.shelf.services.impl;

import com.axbg.shelf.dao.ItemDao;
import com.axbg.shelf.entity.Item;
import com.axbg.shelf.exception.CustomException;
import com.axbg.shelf.services.CollectionService;
import com.axbg.shelf.services.ItemService;
import com.axbg.shelf.services.UserService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.axbg.shelf.config.Constants.DEFAULT_FAVICON;
import static com.axbg.shelf.config.Constants.UNSORTED_COLLECTION;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemDao itemDao;
    private final UserService userService;
    private final CollectionService collectionService;

    @Override
    public Item createItem(String url, String title, String photo) {
        Item item = Item.builder()
                .url(url)
                .title(title)
                .photo(photo)
                .collection(collectionService.findByName(UNSORTED_COLLECTION))
                .build();

        return itemDao.save(item);
    }

    @Override
    public boolean updateItemTitle(long id, String title) {
        Item item = itemDao.findByIdAndUser(id, userService.findUser()).orElse(null);

        if (item != null) {
            item.setTitle(title);
            itemDao.save(item);
            return true;
        }

        return false;
    }

    @Override
    public Item scrapAndCreateItem(final String url) throws CustomException {
        try {
            if (!verifyUrl(url)) {
                throw new CustomException("Invalid URL", HttpStatus.BAD_REQUEST);
            }

            Document doc = Jsoup.connect(url).get();
            String title = doc.title();
            Element favicon = doc.head().select("link[rel~=icon]").first();
            String photo = favicon != null ? getFaviconUrl(url, favicon.attr("href")) : DEFAULT_FAVICON;

            return createItem(url, title, photo);
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

    @Override
    public void deleteItemByUrl(final String url) {
        itemDao.deleteItemByUrl(url);
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
