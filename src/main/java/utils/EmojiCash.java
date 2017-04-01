package utils;

import models.Emoji;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class EmojiCash {
    private static Collection<Emoji> emojiCash = new ArrayList<>();

    private EmojiCash(String path) {
    }

    public static Collection<Emoji> getInstance(String path) {
        if (emojiCash.size() == 0) {
            synchronized (EmojiCash.class) {
                if (emojiCash.size() == 0) {
                    if (path != null) {
                        init(path);
                    }
                }
            }
        }
        return emojiCash;
    }

    private static void init(String path) {
        File picsList = new File(path);
        for (File file : picsList.listFiles()) {
            if (file.isFile()) {
                emojiCash.add(new Emoji(file.getName().substring(0, file.getName().indexOf(".png")),
                        AppProperties.getProperties().get("picsPath") + File.separator + file.getName()));
            }
        }
    }
}
