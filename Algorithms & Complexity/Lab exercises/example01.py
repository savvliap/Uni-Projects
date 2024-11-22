# create a graph
# store the graph in two formats:
# as an edge list
# in gexf format
# load the graph from the corresponding files

import networkx as nx

G = nx.karate_club_graph()

# Write G to a file as an edgelist
nx.write_edgelist(G, "karate_club.edgelist")

# Write G to a file in gexf format
nx.write_gexf(G, "karate_club.gexf")

# Read G1 from an edgelist file
G1 = nx.read_edgelist("karate_club.edgelist")

# Read G2 from a gexf file
G2 = nx.read_gexf("karate_club.gexf")
