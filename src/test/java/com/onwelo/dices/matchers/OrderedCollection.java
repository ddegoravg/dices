package com.onwelo.dices.matchers;

import java.util.Collection;
import java.util.Iterator;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class OrderedCollection extends TypeSafeMatcher<Collection<Comparable>> {
    public static OrderedCollection orderedCollection() {
        return new OrderedCollection();
    }

    @Override
    protected boolean matchesSafely(Collection<Comparable> collection) {
        if (collection.size() < 2) {
            return true;
        }
        Iterator<Comparable> iter = collection.iterator();
        Comparable previous;
        Comparable next = iter.next();
        while (iter.hasNext()) {
            previous = next;
            next = iter.next();
            if (previous.compareTo(next) > 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("ordered collection");
    }

}
