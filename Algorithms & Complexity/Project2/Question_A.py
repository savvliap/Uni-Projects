import networkx as nx
import itertools
import matplotlib.pyplot as plt
import timeit


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


# 1)
def k_centers_bf(G, k=3):
    # find the combinations
    comb = itertools.combinations(G.nodes, k)
    score = float('Inf')
    best_comb = []
    for centers in comb:
        shortest_paths = nx.multi_source_dijkstra_path_length(G, centers, weight='weight')
        # the score that we want to minimize is the maximum distance from centers
        temp_score = max(shortest_paths.values())
        if temp_score < score:
            score = temp_score
            best_comb = centers
    return best_comb, score


# 2)
graph_paths = ['graph_0005_01234.gexf', 'graph_0010_01234.gexf',
               'graph_0015_01234.gexf', 'graph_0020_01234.gexf',
               'graph_0030_01234.gexf', 'graph_0050_01234.gexf',
               'graph_0146_01234.gexf', 'graph_0500_01234.gexf']

timings = []

# graph 5
path = "../graphs01234/" + graph_paths[0]
G = nx.read_gexf(path, node_type=int)

start = timeit.default_timer()
centers5, score5 = k_centers_bf(G, k=3)
timings.append(timeit.default_timer()-start)
print('k-centers computation time for 5 node graph and k=3 is: {}'.format(timings[0]))

# ---another way of counting time. Included because it returns different results but we care more about the ratio---
# setup_code = '''
# import networkx as nx
# from __main__ import k_centers_bf
# path = "../graphs01234/" + "graph_0005_01234.gexf"
# G = nx.read_gexf(path, node_type=int)
# '''
#
# test_code = '''
# centers, score = k_centers_bf(G, k=3)'''
#
# print(timeit.timeit(stmt=test_code, setup=setup_code, number=100))


#  graph 10
path = "../graphs01234/" + graph_paths[1]
G = nx.read_gexf(path, node_type=int)

start = timeit.default_timer()
centers10, score10 = k_centers_bf(G, k=3)
timings.append(timeit.default_timer()-start)
print('k-centers computation time for 10 node graph and k=3 is: {}'.format(timings[1]))


# graph 20
path = "../graphs01234/" + graph_paths[3]
G = nx.read_gexf(path, node_type=int)

start = timeit.default_timer()
centers20, score20 = k_centers_bf(G, k=3)
timings.append(timeit.default_timer()-start)
print('k-centers computation time for 20 node graph and k=3 is: {}'.format(timings[2]))


# graph 50
path = "../graphs01234/" + graph_paths[5]
G = nx.read_gexf(path, node_type=int)

start = timeit.default_timer()
centers50, score50 = k_centers_bf(G, k=3)
timings.append(timeit.default_timer()-start)
print('k-centers computation time for 50 node graph and k=3 is: {}'.format(timings[3]))


# use this
# fig, axes = plt.subplots(figsize = (7,3))
# or this, it's virtually the same
fig = plt.figure(figsize=(7, 3))
# axes = plt.gca()

plt.scatter([5, 10, 20, 50], timings)  # for the dots
plt.plot([5, 10, 20, 50], timings)     # for the line
plt.title('K-centers - computation time', fontsize='xx-large')
plt.xticks([5, 10, 20, 50])
plt.yticks([1, timings[-1]])
plt.tick_params(direction='inout', length=6, width=2, colors='k', labelsize=15)
plt.xlabel('Number of nodes', fontsize='xx-large')
plt.ylabel('Time in seconds', fontsize='xx-large')
plt.tight_layout()
plt.show()


# 3)
# graph 30
path = "../graphs01234/" + graph_paths[4]
G = nx.read_gexf(path, node_type=int)

timings30 = []
results = []
k_list = [2, 3, 4, 5]

for k in k_list:
    start = timeit.default_timer()
    tempres = k_centers_bf(G, k=k)  # tempres so that the append process doesnt affect the timing
    timings30.append(timeit.default_timer()-start)
    results.append(tempres)

fig = plt.figure(figsize=(7, 3))
# axes = plt.gca()

plt.scatter(k_list, timings30)  # for the dots
plt.plot(k_list, timings30)     # for the line
plt.title('computation time - K-centers on 30 node graph', fontsize='xx-large')
plt.xticks(k_list)
plt.yticks(timings30)
plt.tick_params(direction='inout', length=6, width=2, colors='k', labelsize=15)
plt.xlabel('Number of K-centers', fontsize='xx-large')
plt.ylabel('Time in seconds', fontsize='xx-large')
plt.tight_layout()
plt.show()
