import networkx as nx
import matplotlib.pyplot as plt

G = nx.gnp_random_graph(25, 0.07, 123)

C = nx.connected_components(G)
print(len(list(C)))

nx.draw_networkx(G, with_labels=True)
plt.show()
