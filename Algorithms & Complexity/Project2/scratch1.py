import networkx as nx
import random
import itertools
import matplotlib.pyplot as plt


# Απεικόνιση γράφου με χρωματισμό των centers
def draw_k_centers_graph(G, centers=None):
    pos = nx.kamada_kawai_layout(G)

    if centers is None:
        node_color = list(itertools.repeat('yellow', len(G.nodes)))
    else:
        node_color = []
        for node in G:
            if node in centers:
                node_color.append('red')
            else:
                node_color.append('yellow')
    nx.draw(G, pos, node_color=node_color, with_labels=True)
    labels = nx.get_edge_attributes(G, 'weight')
    nx.draw_networkx_edge_labels(G, pos, edge_labels=labels)
    plt.show()


# Δημιουργία πλήρους γράφου
# G = nx.complete_graph(5)

G = nx.read_gexf('../graphs01234/graph_0005_01234.gexf', node_type=int)

draw_k_centers_graph(G,[1,2])


print(G.nodes)


def k_centers_bf(G, k):
    # find the combinations
    ''' what is this '''
    comb = itertools.combinations(G.nodes, k)

    score = float('Inf')
    best_comb = []

    for centers in comb:

        nodes = list(G.nodes)
        for cent in centers:
            nodes.remove(cent)

        # we have all the nodes except the ones that are the centers
        temp_score = 0
        for node in nodes:
            min_dist_from_center = float('Inf')
            for cent in centers:
                dist = nx.shortest_path_length(G, node, cent, weight='weight')
                if dist < min_dist_from_center:
                    min_dist_from_center = dist
            # at this point I have found the min distance from any center for a given node
            # i want to find the node that has the highest distance
            if min_dist_from_center > temp_score:
                temp_score = min_dist_from_center
        # after looping through all the nodes temp_score will be the furthest
        # distance of a node from a given combination of centers

        if temp_score < score:
            score = temp_score
            best_comb = centers

    return best_comb, score

# check multisource dijkstra

all_lengths = nx.multi_source_dijkstra_path_length(G,sources=[1,2],weight='weight')

print(all_lengths)
print([nx.shortest_path_length(G,1,weight='weight')])
print([nx.shortest_path_length(G,2,weight='weight')])
print(max(all_lengths.values()))

def k_centers_bf_2(G, k):
    # find the combinations
    comb = itertools.combinations(G.nodes, k)

    score = float('Inf')
    best_comb = []

    for centers in comb:

        shortest_paths = nx.multi_source_dijkstra_path_length(G,centers,weight='weight')
        temp_score = max(shortest_paths.values())

        if temp_score < score:
            score = temp_score
            best_comb = centers

    return best_comb, score

import timeit

start = timeit.default_timer()
res1 = k_centers_bf(G,2)
print('time1: {}'.format(timeit.default_timer()-start))

start = timeit.default_timer()
res2 = k_centers_bf_2(G,2)
print('time2: {}'.format(timeit.default_timer()-start))

print(res1)
print(res2)

# draw_k_centers_graph(G,res1[0])
# draw_k_centers_graph(G,res2[0])

setup_code = ''' 
from __main__ import k_centers_bf_2 
import networkx as nx
G = nx.read_gexf('../graphs01234/graph_0005_01234.gexf', node_type=int)
'''

setup_code = '''
from __main__ import k_centers_bf_2
from __main__ import k_centers_bf
import networkx as nx
G = nx.read_gexf('../graphs01234/graph_0005_01234.gexf', node_type=int)'''


print(timeit.timeit(stmt="res = k_centers_bf_2(G,2)", setup=setup_code,number=100))
print(timeit.timeit(stmt="res = k_centers_bf(G,2)", setup=setup_code,number=100))


print(k_centers_bf.__doc__)