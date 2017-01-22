package com.example.x6.sidebardemo;

import java.util.Comparator;

/**
 * Created by x6 on 2017/1/18.
 */
public class PinyinComparator implements Comparator<SortMode> {
    public int compare(SortMode lhs, SortMode rhs) {
        return lhs.getSortLetter().compareTo(rhs.getSortLetter());
    }
}
