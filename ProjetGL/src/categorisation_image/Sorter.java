package categorisation_image;

import java.io.*;
import java.nio.file.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public class Sorter {

    private Calendar tempEventCalendar;
    private Parametre param;

    public Sorter(Parametre p) {
        tempEventCalendar = new Calendar();
        param = p;
    }

    public Sorter(Calendar userCal, Parametre p) {
        this(p);
        this.tempEventCalendar = userCal;
    }

    public void doTri(String path) throws Exception {
        List<Event> listeEvent;
        TreeMap<Integer, Image> images;
        Scan s = new Scan();
        listeEvent = tempEventCalendar.getListEvent();
        images = s.doScan(path);

        userEventSort(listeEvent, path, images);
    }

    private void userEventSort(List<Event> l, String path, TreeMap<Integer, Image> tm) throws Exception {
        String destination;
        Path chemin;
        // Parcours des clefs
        for (Event element : l) {
            Date debut = element.getIntervale().get(0);
            Date fin = element.getIntervale().get(1);
            for (Integer key : tm.keySet()) {
                if (debut.getTime() <= key && key <= fin.getTime()) {
                    destination = param.getDestDir() + "/" + element.getNom(); //chemin du dossier de destination
                    //chemin = Paths.get(destination);//on cree le Path a partir de la destination
                    //Path cheminDest = Files.createDirectory(chemin);//on créé la destination
                    Path monFichier = Paths.get(tm.get(key).getPath());
                    Path monFichierCopie = Paths.get(destination);
                    Files.copy(monFichier, monFichierCopie, REPLACE_EXISTING);
                }
            }
        }

    }

    private void unsortedSort(List l) throws Exception {
        int affinage = param.getSortParameter();
    }

}
