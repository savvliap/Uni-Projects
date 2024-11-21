- ���������� ������� jar �� ��� ���������� ������:
���� ���� ���� ��� project -> Export... -> Java - JAR File ->
���������� ��� ���� ����� ��� ������ �� Project ��� ������� �� ������� export (FacilityGame)
��� ���������� ��� ���� ����� ��� ������ ��� ����� "JAR file:" �� ����� ��� ������� (��� �� ���� ���
��� �����) ��� ����� �� "������������" �� Project. 
�� ����� ������� �������� �� ������� ��. FacilityGameOmadaXX.jar
-> Next -> Next -> Finish

�������� ��������� ��� �� ������ jar:
��� �� ������ �������, ���� ����������� ���� ��������� �� ������ FacilityGameOmadaXX.
��� ��� Server:
java -cp FacilityGameOmadaXX.jar facilityGame.FServer

��� ��� Client:
java -cp FacilityGameOmadaXX.jar facilityGame.FClient

�� ����������� (Server):
java -cp FacilityGameOmadaXX.jar facilityGame.FServer <player type> <verbose> <port>
����������:
java -cp FacilityGame.jar facilityGame.FServer DUMMY_PLAYER_1 false 4465

�� ����������� (Client):
java -cp FacilityGameOmadaXX.jar facilityGame.FClient <player type> <verbose> <server-host> <server-port> <n> <seed> <PLAYER_A or PLAYER_B>
����������:
java -cp FacilityGame.jar facilityGame.FClient DUMMY_PLAYER_1 false localhost 4465 100 123