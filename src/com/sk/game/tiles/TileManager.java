package com.sk.game.tiles;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import com.sk.game.graphics.Sprite;
import com.sk.game.utils.Camera;
import com.sk.game.utils.Vector2f;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.print.Doc;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.Objects;

public class TileManager {

    public Camera cam;
    public static ArrayList<TileMap> tm;

    public TileManager() {
        tm = new ArrayList<TileMap>();
    }

    public TileManager(Camera cam, String... path) {
        tm = new ArrayList<TileMap>();
        for (String tileSet: path) {
            addTileMap(tileSet, 64, 64, cam);
        }
    }

    public TileManager(String path, int blockWidth, int blockHeight, Camera cam) {
        tm = new ArrayList<TileMap>();
        addTileMap(path, blockWidth, blockHeight, cam);
    }

    public void addTileMap(String path, int blockWidth, int blockHeight, Camera cam){
        this.cam = cam;
        String imagePath;

        int width = 0;
        int height = 0;
        int tileWidth;
        int tileHeight;
        int tileCount;
        int tileColumns;
        int layers = 0;
        String imagePathTSX;
        Sprite sprite;

        String[] data = new String[10];

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(new File(Objects.requireNonNull(getClass().getClassLoader().getResource(path)).toURI()));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("tileset");
            Node node = list.item(0);
            Element eElement = (Element) node;

            imagePath = eElement.getAttribute("name");

            list = doc.getElementsByTagName("map");
            node = list.item(0);
            eElement = (Element) node;

            tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(eElement.getAttribute("tileheight"));

            sprite = new Sprite( "tile/" +  imagePath + ".png", tileWidth, tileHeight);

            tileColumns = sprite.getSpriteSheetWidth() / tileWidth;
            tileCount = tileColumns * (sprite.getSpriteSheetHeight() / tileHeight);

            list = doc.getElementsByTagName("layer");
            layers = list.getLength();

            for (int i = 0; i < layers; i++) {
                node = list.item(i);
                eElement = (Element) node;
                if (i <= 0) {
                    width = Integer.parseInt(eElement.getAttribute("width"));
                    height = Integer.parseInt(eElement.getAttribute("height"));
                }

                data[i] = eElement.getElementsByTagName("data").item(0).getTextContent();

                if(i>=1) {
                    tm.add(new TileMapNorm(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
                } else {
                    tm.add(new TileMapObj(data[i], sprite, width, height, blockWidth, blockHeight, tileColumns));
                }

                cam.setLimit(width * blockWidth, height * blockHeight);

            }
        } catch (Exception e) {
            System.out.println("ERROR - TILEMANAGER: Could not load tilemap");
        }
    }

    public void removeTileMap(int index){
        tm.remove(index);
    }

    public void render(Graphics2D g) {
        if(cam == null)
            return;

        for (TileMap tileMap : tm) {
            tileMap.render(g, cam.getBounds());
        }
    }
}
