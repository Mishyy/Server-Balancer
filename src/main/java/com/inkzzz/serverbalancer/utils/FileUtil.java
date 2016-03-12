package com.inkzzz.serverbalancer.utils;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Luke Denham on 12/03/2016.
 */
public final class FileUtil {

    private final static String DIRECTORY = "plugins/ServerBalancer/";

    public static Configuration getFile(String name) {
        final File file = get(name);
        if(file == null) {
            return null;
        }
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void save(Configuration configuration, String name) {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, get(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File get(String name) {
        final File file = new File(getDirectory(), name);
        if(!file.exists()) {
            try {
                if(!file.createNewFile()) {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private static String getDirectory() {
        return DIRECTORY;
    }

}
