package at.tuwien.telemedizin.dermadoc.app;

import java.util.Random;

/**
 * Created by FAUser on 18.11.2015.
 *
 * // TODO REMOVE THIS CLASS! DATA FILLER ONLY
 */
public class DataGenerator {

    /**
     * returns a Disease-Name from a list randomly
     * @return
     */
    public static String getDisease() {
        return DiseaseGenerator.getDisease();
    }


    public static String getSymptoms() {
        return SymptomGenerator.getSymptom();
    }

    private static class SymptomGenerator {
        private static String[] extent = new String[]{
                "spots", "area", "surface", "flat"  };

        private static String[] color = new String[]{
                "red", "white", "black", "brown", "white-brown", "red-black", "purple" };


        private static String[] adjectives = new String[] {
                "itching", "itches", "burn", "burning", "biting", "hot", "stinging", "drawing", "no pain", "dry"
        };

        public static String getSymptom() {
            Random randomGenerator = new Random();
            int randomIntExtent = randomGenerator.nextInt(1) + 1;
            int randomIntColor = randomGenerator.nextInt(3);
            int randomIntAdj = randomGenerator.nextInt(4);

            String extent = "";
            for(int i = 0; i < randomIntExtent; i++) {
                extent += getExtent() + " ";
            }

            String color = "";
            for(int i = 0; i < randomIntColor; i++) {
                color += getColor() + ", ";
            }

            String adj = "";
            for(int i = 0; i < randomIntAdj; i++) {
                adj += getAdjectives() + ", ";
            }

            return extent + color + adj;
        }

        private static String getExtent() {
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(extent.length);
            return extent[randomInt];
        }

        private static String getColor() {
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(color.length);
            return color[randomInt];
        }

        private static String getAdjectives() {
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(adjectives.length);
            return adjectives[randomInt];
        }
    }

    private static class DiseaseGenerator {
        private static String[] names = new String[]{
                "Acne", "Actinic Purpura", "Bee Sting", "Basal Cell Carcinoma", "Bruising Hands",
                "Alopecia Mucinosa", "Dermal Fillers", "Dermatomyositis", "Dry Skin", "Dyshidrotic Dermatitis",
                "Leiomyoma", "Liposuction", "Kawasaki\'s Disease" };


        public static String getDisease() {
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(names.length);
            return names[randomInt];
        }
    }

}
