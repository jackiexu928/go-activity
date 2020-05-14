package com.jackie.goactivity.util;

/**
 * Created with IntelliJ IDEA
 * Description:
 *
 * @author xujj
 * @date 2020-04-29
 */
public class TrackHolder {
    final static ThreadLocal<Tracker> cache = new ThreadLocal<Tracker>();

    public static Tracker getTracker() {
        return cache.get() == null ? new Tracker() : cache.get();
    }

    public static void set(Tracker tracker) {
        cache.set(tracker);
    }

    public static void remove() {
        cache.remove();
    }
}
