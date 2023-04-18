package cz.czechitas.lekce10.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Uloziste {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Cat cat;
    private Mouse mouse;


    public void nacistPlochuZeSouboru() throws IOException {
        //nacistPlochuZeSouboru(Paths.get("level-01.json"));
        //pouze pro ukázku
        nacistPlochuZeSouboru(Path.of("level-01.json"));
    }

    public void nacistPlochuZeSouboru(Path path) {
        UlozenaPlocha ulozenaPlocha = null;
        try {
            ulozenaPlocha = objectMapper.readValue(path.toFile(), UlozenaPlocha.class);
        } catch (FileNotFoundException | EOFException e) {
            System.out.println("...");
            throw new RuntimeException(e);
        } catch (IOException e){
            throw new RuntimeException(e);
        }finally {
            //používá se pro uklizení souborů když není metoda, která by se po zápisu i sama uzavřela
            // totéž udělá i závorka za try
        }

        //takhle nejsou vyjímky dobře zachycené
        //pozor na prázdný catchblock (tzn. chybí throw), pokud bych ho opravdu potřebovala, tak aspoň napsat komentář, že ta situace nemůže nastat HAHA
        cat = new Cat(ulozenaPlocha.getCat());
        mouse = new Mouse(ulozenaPlocha.getMouse());
        for (Point treePoint : ulozenaPlocha.getTrees()) {
            new Tree(treePoint);
        }
        new Cheese(ulozenaPlocha.getCheese());
        new Meat(ulozenaPlocha.getMeat());
    }

    public void nacistStavZeSouboru() throws IOException {
        nacistStavZeSouboru(Paths.get("stav.json"));
    }

    public void ulozitStavDoSouboru(Path path) throws IOException {
        UlozenyStav ulozenyStav = new UlozenyStav(); // Vytvořit objekt UlozenyStav
        ulozenyStav.setCat(cat.getLocation());
        ulozenyStav.setMouse(mouse.getLocation());// Uložit do něj souřadnice kočky a myši – souřadnice získáte voláním getLocation()
        objectMapper.writeValue(path.toFile(), ulozenyStav);   // Uložit objekt UlozenyStav do souboru pomocí objectMapper.writeValue(file, object)
    }

    public void ulozitStavDoSouboru() throws IOException {
        ulozitStavDoSouboru(Paths.get("stav.json"));
    }

    public void nacistStavZeSouboru(Path path) throws IOException {
        UlozenyStav ulozenyStav = objectMapper.readValue(path.toFile(), UlozenyStav.class);// Načíst objekt UlozenyStav pomocí objectMapper.readValue(file, UlozenyStav.class)
        mouse.setLocation(ulozenyStav.getMouse());// Získat z UlozenyStav souřadnice kočky a myši
        cat.setLocation(ulozenyStav.getCat());// Zapsat tyto souřadnice do objektů kočky a myši pomocí setLocation()
    }

    /*   public void ulozitPlochuZeSouboru(Path path) throws IOException {
           UlozenaPlocha ulozenaPlocha = null;
          objectMapper.writeValue(path.toFile(),ulozenaPlocha);
       }
 // zbytečné jen pro ukázku
     */

    public Cat getCat() {
        return cat;
    }

    public Mouse getMouse() {
        return mouse;
    }


}
