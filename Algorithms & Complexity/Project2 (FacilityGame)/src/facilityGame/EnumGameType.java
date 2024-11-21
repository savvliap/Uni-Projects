package facilityGame;

public enum EnumGameType {
	NORMAL, // normal facility game 
	COPY,	// facility game where player B has to imitate player A
	COMPLEMENT, // facility game where player B has to choose nodes of complementary value to player's B moves
	NOTRIPLES, // facility game where player B has to prevent player A from allocating triples of nodes,
	NOQUADRUPLES, // facility game where player B has to prevent player A from allocating quadruples of nodes
	SWITCHING // facility game where player B has to select a move that is not within 3 blocks distance from his last move
}
