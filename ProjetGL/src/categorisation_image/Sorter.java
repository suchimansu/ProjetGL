package categorisation_image;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class Sorter {

    private Calendar tempEventCalendar;
    private Parameter param;

    public Sorter(Parameter p) {
        tempEventCalendar = new Calendar();
        param = p;
    }

    public Sorter(Calendar userCal, Parameter p) {
        this(p);
        this.tempEventCalendar = userCal;
    }

    public void doTri(String pathIn) throws Exception {
        List<Event> listeEvent;
        TreeMap<Long, Image> images;
        Scan s = new Scan();
        Event globalEvent = tempEventCalendar.getGlobalEvent();
        // listeEvent = tempEventCalendar.getListEvent();
        images = s.doScan(new File(pathIn));

        userEventSort(globalEvent, images);
    }

    private void userEventSort(Event globalEvent, TreeMap<Long, Image> mapImage) throws Exception {
        Date dateImage;
        if (globalEvent.hasChild()) {
            for (Long key : mapImage.keySet()) {
                dateImage = mapImage.get(key).getTimeDate();
                if (globalEvent.isInclude(dateImage)) {
                    for (Event childs : globalEvent.getChild()) {
                        if (childs.isInclude(dateImage)) {
                            //l'image est dans une categorie de l'utilisateur
                            userEventSortBis(globalEvent, mapImage, key);
                        }
                    }
                } else {
                    //on ne fait rien : l'image n'est pas dans un parametre utilisateur
                }
            }
        } else {
            //on ne fait rien : pas de parametre utilisateur
        }
    }

    private void userEventSortBis(Event event, TreeMap<Long, Image> mapImage, Long key) {
        boolean b = true;
        Date dateImage = mapImage.get(key).getTimeDate();
        if (event.hasChild()) {
            for (Event childs : event.getChild()) {
                if (childs.isInclude(dateImage)) {
                    //l'image est dans une categorie de l'utilisateur
                    userEventSortBis(childs, mapImage, key);
                    b = !b; //on a trouvé une sous categorie pour l'image
                }
            }
        }

        if (b) {//on est dans le dossier ou doit etre la photo
            String pathImage;
            String pathDest;
            pathImage = mapImage.get(key).getPath();
            //deplacerImage(pathImage, pathDest) 
            mapImage.remove(key);//on enleve l'image du TreeMap
        } else {
            //normalement on ne rentre pas ici
        }
    }

    private void unsortedSort(List l) throws Exception {
        int affinage = param.getSortParameter();
    }

}
