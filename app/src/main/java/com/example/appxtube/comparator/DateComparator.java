package com.example.appxtube.comparator;

import com.example.appxtube.entity.Item;

import java.util.Comparator;

public class DateComparator implements Comparator<Item> {
    @Override
    public int compare(Item first, Item seconnd) {
        return first.getSnippet().getPublishedAt().compareTo(seconnd.getSnippet().publishedAt);
    }
}
