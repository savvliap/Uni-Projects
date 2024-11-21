- Δημιουργία αρχείου jar με τον εκτελέσιμο κώδικα:
Δεξί κλικ πάνω στο project -> Export... -> Java - JAR File ->
Τσεκάρουμε στο πάνω μέρος της φόρμας το Project που θέλουμε να κάνουμε export (FacilityGame)
και επιλέγουμε στο κάτω μέρος της φόρμας στο πεδίο "JAR file:" το όνομα του αρχείου (και τη θέση του
στο δίσκο) στο οποίο θα "πακεταριστεί" το Project. 
Ως όνομα αρχείου μπορούμε να δώσουμε πχ. FacilityGameOmadaXX.jar
-> Next -> Next -> Finish

Εκτέλεση εφαρμογής από το αρχείο jar:
Από τη γραμμή εντολών, στον υποκατάλογο όπου βρίσκεται το αρχείο FacilityGameOmadaXX.
Για τον Server:
java -cp FacilityGameOmadaXX.jar facilityGame.FServer

Για τον Client:
java -cp FacilityGameOmadaXX.jar facilityGame.FClient

Με παραμέτρους (Server):
java -cp FacilityGameOmadaXX.jar facilityGame.FServer <player type> <verbose> <port>
Παράδειγμα:
java -cp FacilityGame.jar facilityGame.FServer DUMMY_PLAYER_1 false 4465

Με παραμέτρους (Client):
java -cp FacilityGameOmadaXX.jar facilityGame.FClient <player type> <verbose> <server-host> <server-port> <n> <seed> <PLAYER_A or PLAYER_B>
Παράδειγμα:
java -cp FacilityGame.jar facilityGame.FClient DUMMY_PLAYER_1 false localhost 4465 100 123