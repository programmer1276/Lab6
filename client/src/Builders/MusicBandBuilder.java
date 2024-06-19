package Builders;

import Classes.MusicBand;
import UDPClient.Client;

import java.time.LocalDate;

/**
 * Class to create an object <MusicBand>newBand</MusicBand>
 */
public class MusicBandBuilder implements Builder<MusicBand> {
    static int id = 0;
    //CollectionManager manager = CollectionManager.getInstance();
    @Override
    public MusicBand build() {
        checkForEmptySlot();
        MusicBand newBand = new MusicBand(0, null, null, null, 0, null, null, null, null);
        final LocalDate creationDate = LocalDate.now();
        newBand.setCreationDate(creationDate);
        newBand.setId(id);
        return newBand;
    }

    /**
     * Method to update id and for monitoring for empty id slot to add this id to new collection
     */
    private void checkForEmptySlot() {
        if (!(Client.getIDs().isEmpty())) {
            for (int i = 0; i <= Client.getIDs().size(); i++) {
                if (!(Client.getIDs().contains(i))) {
                    id = i;
                    break;
                }
                else {
                    id = Client.getIDs().size() + 1;
                }
            }
        } else {
            id = 0;
        }
    }
}
