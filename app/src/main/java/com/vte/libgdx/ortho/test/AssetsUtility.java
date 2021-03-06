package com.vte.libgdx.ortho.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.I18NBundle;
import com.vte.libgdx.ortho.test.persistence.PersistenceProvider;

import java.util.Locale;

/**
 * Created by gwalarn on 27/11/16.
 */

public final class AssetsUtility {
    public static final AssetManager _assetManager = new AssetManager();
    private static final String TAG = AssetsUtility.class.getSimpleName();
    private static InternalFileHandleResolver _filePathResolver = new InternalFileHandleResolver();

    private static I18NBundle sStringsBundle;

    private final static String STRINGS_PATH = "strings/strings";
    public final static String UI_SKIN_PATH = "data/skins/ui.json";
    private final static String ITEMS_TEXTURE_ATLAS_PATH = "data/items/items.atlas";

    public static TextureAtlas ITEMS_TEXTUREATLAS = new TextureAtlas(ITEMS_TEXTURE_ATLAS_PATH);

    public static void unloadAsset(String assetFilenamePath) {
        // once the asset manager is done loading
        if (_assetManager.isLoaded(assetFilenamePath)) {
            _assetManager.unload(assetFilenamePath);
        } else {
            Gdx.app.debug(TAG, "Asset is not loaded; Nothing to unload: " + assetFilenamePath);
        }
    }

    public static float loadCompleted() {
        return _assetManager.getProgress();
    }

    public static int numberAssetsQueued() {
        return _assetManager.getQueuedAssets();
    }

    public static boolean updateAssetLoading() {
        return _assetManager.update();
    }

    public static boolean isAssetLoaded(String fileName) {
        return _assetManager.isLoaded(fileName);

    }

    public static void loadMapAsset(String mapFilenamePath) {
        if (mapFilenamePath == null || mapFilenamePath.isEmpty()) {
            return;
        }

        if (_assetManager.isLoaded(mapFilenamePath)) {
            return;
        }

        //load asset
        if (_filePathResolver.resolve(mapFilenamePath).exists()) {
            _assetManager.setLoader(TiledMap.class, new TmxMapLoader(_filePathResolver));
            _assetManager.load(mapFilenamePath, TiledMap.class);
            //Until we add loading screen, just block until we load the map
            _assetManager.finishLoadingAsset(mapFilenamePath);
            Gdx.app.debug(TAG, "Map loaded!: " + mapFilenamePath);
        } else {
            Gdx.app.debug(TAG, "Map doesn't exist!: " + mapFilenamePath);
        }
    }


    protected static void loadStrings()
    {
        Locale locale;
        if(PersistenceProvider.getInstance().getSettings().language==null) {
            locale = Locale.getDefault();
        }
        else
        {
            if(PersistenceProvider.getInstance().getSettings().countryCode==null)
            {
                locale = new Locale(PersistenceProvider.getInstance().getSettings().language);
            }
            else
            {
                locale = new Locale(PersistenceProvider.getInstance().getSettings().language, PersistenceProvider.getInstance().getSettings().countryCode);
            }

        }
        _assetManager.load(STRINGS_PATH, I18NBundle.class, new I18NBundleLoader.I18NBundleParameter(locale));
        //Until we add loading screen, just block until we load the map
        _assetManager.finishLoadingAsset(STRINGS_PATH);
        Gdx.app.debug(TAG, "Strings loaded!: " + STRINGS_PATH);
    }
    public static void reloadStrings()
    {
        if(_assetManager.isLoaded(STRINGS_PATH))
        {
            _assetManager.unload(STRINGS_PATH);
        }

    }
    /**
     * Gets the string with the specified key from this bundle or one of its parent after replacing the given arguments if they
     * occur.
     *
     * @param key  the key for the desired string
     * @param args the arguments to be replaced in the string associated to the given key.
     * @return the string for the given key formatted with the given arguments
     * @throws NullPointerException     if <code>key</code> is <code>null</code>
     * @throws MissingResourceException if no string for the given key can be found
     */
    public static String getString(String aKey, Object... args) {
        if (aKey == null || aKey.isEmpty()) {
            return null;
        }
        if (sStringsBundle != null) {
            return sStringsBundle.format(aKey, args);
        } else if (!_assetManager.isLoaded(STRINGS_PATH)) {
            loadStrings();

            sStringsBundle = _assetManager.get(STRINGS_PATH, I18NBundle.class);
            return sStringsBundle.format(aKey, args);
        }
        return null;

    }


    public static TiledMap getMapAsset(String mapFilenamePath) {
        TiledMap map = null;

        // once the asset manager is done loading
        if (_assetManager.isLoaded(mapFilenamePath)) {
            map = _assetManager.get(mapFilenamePath, TiledMap.class);
        } else {
            Gdx.app.debug(TAG, "Map is not loaded: " + mapFilenamePath);
        }

        return map;
    }

    public static void loadSoundAsset(String soundFilenamePath) {
        if (soundFilenamePath == null || soundFilenamePath.isEmpty()) {
            return;
        }

        if (_assetManager.isLoaded(soundFilenamePath)) {
            return;
        }

        //load asset
        if (_filePathResolver.resolve(soundFilenamePath).exists()) {
            _assetManager.setLoader(Sound.class, new SoundLoader(_filePathResolver));
            _assetManager.load(soundFilenamePath, Sound.class);
            //Until we add loading screen, just block until we load the map
            _assetManager.finishLoadingAsset(soundFilenamePath);
            Gdx.app.debug(TAG, "Sound loaded!: " + soundFilenamePath);
        } else {
            Gdx.app.debug(TAG, "Sound doesn't exist!: " + soundFilenamePath);
        }
    }


    public static Sound getSoundAsset(String soundFilenamePath) {
        Sound sound = null;

        // once the asset manager is done loading
        if (_assetManager.isLoaded(soundFilenamePath)) {
            sound = _assetManager.get(soundFilenamePath, Sound.class);
        } else {
            Gdx.app.debug(TAG, "Sound is not loaded: " + soundFilenamePath);
        }

        return sound;
    }

    public static void loadMusicAsset(String musicFilenamePath) {
        if (musicFilenamePath == null || musicFilenamePath.isEmpty()) {
            return;
        }

        if (_assetManager.isLoaded(musicFilenamePath)) {
            return;
        }

        //load asset
        if (_filePathResolver.resolve(musicFilenamePath).exists()) {
            _assetManager.setLoader(Music.class, new MusicLoader(_filePathResolver));
            _assetManager.load(musicFilenamePath, Music.class);
            //Until we add loading screen, just block until we load the map
            _assetManager.finishLoadingAsset(musicFilenamePath);
            Gdx.app.debug(TAG, "Music loaded!: " + musicFilenamePath);
        } else {
            Gdx.app.debug(TAG, "Music doesn't exist!: " + musicFilenamePath);
        }
    }


    public static Music getMusicAsset(String musicFilenamePath) {
        Music music = null;

        // once the asset manager is done loading
        if (_assetManager.isLoaded(musicFilenamePath)) {
            music = _assetManager.get(musicFilenamePath, Music.class);
        } else {
            Gdx.app.debug(TAG, "Music is not loaded: " + musicFilenamePath);
        }

        return music;
    }


    public static void loadTextureAtlasAsset(String atlasFilenamePath) {
        if (atlasFilenamePath == null || atlasFilenamePath.isEmpty()) {
            return;
        }

        if (_assetManager.isLoaded(atlasFilenamePath)) {
            return;
        }

        //load asset
        if (_filePathResolver.resolve(atlasFilenamePath).exists()) {
            _assetManager.setLoader(TextureAtlas.class, new TextureAtlasLoader(_filePathResolver));
            _assetManager.load(atlasFilenamePath, TextureAtlas.class);
            //Until we add loading screen, just block until we load the map
            _assetManager.finishLoadingAsset(atlasFilenamePath);
        } else {
            Gdx.app.debug(TAG, "Atlas doesn't exist!: " + atlasFilenamePath);
        }
    }

    public static TextureAtlas getTextureAtlasAsset(String atlasFilenamePath) {
        TextureAtlas atlas = null;

        // once the asset manager is done loading
        if (_assetManager.isLoaded(atlasFilenamePath)) {
            atlas = _assetManager.get(atlasFilenamePath, TextureAtlas.class);
        } else {
            Gdx.app.debug(TAG, "Texture is not loaded: " + atlasFilenamePath);
        }

        return atlas;
    }

    public static void loadTextureAsset(String textureFilenamePath) {
        if (textureFilenamePath == null || textureFilenamePath.isEmpty()) {
            return;
        }

        if (_assetManager.isLoaded(textureFilenamePath)) {
            return;
        }

        //load asset
        if (_filePathResolver.resolve(textureFilenamePath).exists()) {
            _assetManager.setLoader(Texture.class, new TextureLoader(_filePathResolver));
            _assetManager.load(textureFilenamePath, Texture.class);
            //Until we add loading screen, just block until we load the map
            _assetManager.finishLoadingAsset(textureFilenamePath);
        } else {
            Gdx.app.debug(TAG, "Texture doesn't exist!: " + textureFilenamePath);
        }
    }

    public static Texture getTextureAsset(String textureFilenamePath) {
        Texture texture = null;

        // once the asset manager is done loading
        if (_assetManager.isLoaded(textureFilenamePath)) {
            texture = _assetManager.get(textureFilenamePath, Texture.class);
        } else {
            Gdx.app.debug(TAG, "Texture is not loaded: " + textureFilenamePath);
        }

        return texture;
    }

}

