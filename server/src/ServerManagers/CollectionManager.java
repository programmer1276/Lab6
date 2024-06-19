package ServerManagers;


import Classes.MusicBand;
import Exceptions.EmptyCollectionException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class to work with collection
 */
public class CollectionManager {
    private static CollectionManager singletonPattern;
    private static ArrayList<MusicBand> musicBands;
    /**
     * For fixation creation date of collection
     */
    private final static LocalDateTime InitializationDate = LocalDateTime.now();
    /**
     * All current ids which are used in collection
     */
    private static ArrayList<Integer> previousIDs = new ArrayList<>();

    /**
     * Method to create only one class object everywhere
     * @return
     */
    public static CollectionManager getInstance() {
        if (singletonPattern == null) {
            singletonPattern = new CollectionManager();
        }
        return singletonPattern;
    }
    public static ArrayList<MusicBand> getCollection() {
        return musicBands;
    }
    public static void setCollection(ArrayList<MusicBand> list) {
        musicBands = list;
    }
    public static LocalDateTime getCollectionInitialisationDate() {
        return InitializationDate;
    }
    public static MusicBand getBandById(int id) {
        CollectionManager manager = CollectionManager.getInstance();
        for (MusicBand band : manager.getCollection()) {
            if (band.getID() == id) {
                return band;
            }
        }
        return null;
    }
    public static ArrayList<Integer> getPreviousIDs() {
        return previousIDs;
    }

    public static void addElementToCollection(MusicBand value){
        CollectionManager manager = CollectionManager.getInstance();
        if (musicBands == null){
            ArrayList<MusicBand> musicBands = new ArrayList<>();
            musicBands.add(value);
            manager.setCollection(musicBands);
        }
        else{
            musicBands.add(value);
        }
        previousIDs.add(value.getID());
    }

    public static MusicBand getCollectionById(int id) throws NoSuchElementException {
        MusicBand mb = null;
        Iterator<MusicBand> iter = musicBands.iterator();
        while (iter.hasNext()){
            MusicBand temp = iter.next();
            if (temp.getID() == id){
                mb = temp;
                break;
            }
        }
        if (mb == null){
            throw new NoSuchElementException("Нет такого элемента. Попробуйте снова");
        }
        return mb;
    }

    //команда add
    public static boolean add(MusicBand musicBand) {
        if (musicBand != null){
            addElementToCollection(musicBand);
            return true;
        }
        return false;
    }

    public static boolean clear() {
        if (getCollection() == null) throw new EmptyCollectionException("Коллекция null!");
        else if (getCollection().isEmpty()) throw new EmptyCollectionException("Коллекция пустая!");
        getCollection().clear();
        getPreviousIDs().clear();
        return true;
    }

    private static ArrayList<MusicBand> elementTofind = new ArrayList<>();
    public static ArrayList<MusicBand> getElementToFind() {
        return elementTofind;
    }
    public static boolean filterStartsWithName(String arg) {
        ArrayList<MusicBand> arr = getCollection();
        String nameTofind = arg;
        Iterator iter = arr.iterator();
        while (iter.hasNext()) {
            MusicBand element = (MusicBand) iter.next();
            if (element.getName().startsWith(nameTofind)) {
                elementTofind.add(element);
            }
        }
        if (elementTofind != null && !elementTofind.isEmpty()) return true;
        return false;
    }
    public static boolean removeById(Integer id) throws EmptyCollectionException {
        //CollectionManager manager = getInstance();
        if (getCollection() == null) throw new EmptyCollectionException("Ваша коллекция пуста. Воспользуйтесь командой \"add\" для добавления элемента в коллекцию");
        MusicBand musicBand = getCollectionById(id);
        if(musicBand != null){
            getPreviousIDs().remove(id);
            getCollection().remove(musicBand);
            return true;
        }
        return false;
    }

    public static boolean removeFirst() {
        CollectionManager manager = getInstance();
        if (manager.getCollection() == null) throw new EmptyCollectionException("Ваша коллекция пуста. Воспользуйтесь командой \\\"add\\\" для добавления элемента в коллекцию");
        if (!manager.getCollection().isEmpty()) {
            MusicBand musicBand = manager.getCollection().remove(0);
            getPreviousIDs().remove(musicBand.getID());
            return true;
        }
        return false;
    }
    public static boolean removeLast() {
        CollectionManager manager = getInstance();
        if (manager.getCollection() == null) throw new EmptyCollectionException("Ваша коллекция пуста. Воспользуйтесь командой \\\"add\\\" для добавления элемента в коллекцию");
        int lastElementIndex = manager.getCollection().size() - 1;
        if (lastElementIndex >= 0) {
            MusicBand musicBand = manager.getCollection().get(lastElementIndex);
            getPreviousIDs().remove(musicBand.getID());
            manager.getCollection().remove(lastElementIndex);
            return true;
        }
        return false;
    }
    public static boolean reorder() {
        if (getCollection() != null) {
            Collections.reverse(CollectionManager.getCollection());
            return true;
        }
        return false;
    }

    public static boolean show() {
        if (getCollection() == null) throw new EmptyCollectionException("Коллекция не должна быть null. Воспользуйтесь командой \"add\"");
        if (getCollection().isEmpty()) {
            return false;
        }
        getCollection().sort(MusicBand::compareTo);
        return true;
    }

    public static void update(MusicBand musicBand) {
        addElementToCollection(musicBand);
    }
}
