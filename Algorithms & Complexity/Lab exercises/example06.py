import networkx as nx
import matplotlib.pyplot as plt

G = nx.gnp_random_graph(25, 0.12, 123)

print('Is connected: ', nx.is_connected(G))

nx.draw_networkx(G, with_labels=True)
plt.show()
