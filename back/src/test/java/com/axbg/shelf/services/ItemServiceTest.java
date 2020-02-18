package com.axbg.shelf.services;


import static com.axbg.shelf.config.Constants.UNSORTED_COLLECTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.axbg.shelf.dao.ItemDao;
import com.axbg.shelf.entity.Collection;
import com.axbg.shelf.entity.Item;
import com.axbg.shelf.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ItemServiceTest {

    @MockBean
    private CollectionService collectionService;

    @MockBean
    private ItemDao itemDao;

    @Autowired
    private ItemService itemService;

    private Item expectedItem;
    private Collection expectedCollection;

    private final String URL = "https://spring.io/";

    @BeforeEach
    public void init() {
        expectedCollection = buildCollection();
        expectedItem = buildItem();
        when(collectionService.findByName(anyString())).thenReturn(expectedCollection);
        when(itemDao.save(ArgumentMatchers.any())).thenReturn(expectedItem);
    }

    @Test
    public void createItemTest() throws CustomException {
        Item actualItem = itemService.scrapAndCreateItem(URL);

        assertThat(actualItem.getUrl()).isEqualTo(expectedItem.getUrl());
        assertThat(actualItem.getTitle()).isEqualTo(expectedItem.getTitle());
        assertThat(actualItem.getPhoto()).isEqualTo(expectedItem.getPhoto());
        assertThat(actualItem.getCollection()).isEqualTo(expectedCollection);
    }

    private Collection buildCollection() {
        return Collection.builder()
                .name(UNSORTED_COLLECTION)
                .build();
    }

    private Item buildItem() {
        return Item.builder()
                .url(URL)
                .title("Spring")
                .photo("https://spring.io/img/favicon-ca31b78daf0dd9a106bbf3c6d87d4ec7.png")
                .collection(expectedCollection)
                .build();
    }

}
 