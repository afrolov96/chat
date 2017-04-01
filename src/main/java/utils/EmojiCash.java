package utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class EmojiCash {
    private static Map<String, String> emojiCash = new HashMap<>();

    private EmojiCash(String path) {
    }

    public static Map<String, String> getInstance(String path) {
        if (emojiCash.size() == 0) {
            synchronized (EmojiCash.class) {
                if (emojiCash.size() == 0) {
                    init(path);
                }
            }
        }
        return emojiCash;
    }

    private static void init(String path) {
        File picsList = new File(path);
        for (File file : picsList.listFiles()) {
            if (file.isFile()) {
                emojiCash.put(file.getName().substring(0, file.getName().indexOf(".png")),
                        "pics/emoji/png_64/" + file.getName());
            }
        }
    }
}
