from heapq import heappop, heappush
from time import time

#####################################
# reading input and setting constants
#####################################
file = open('input.txt')
city = [[int(c) for c in line.strip()] for line in file.readlines()]
rows = len(city)
cols = len(city[0])

min_moves = 4
max_moves = 10

class Crucible:
    def __init__(self, prev, to_pos):
        if (prev == None):
            self.cost = 0
            self.move_count = 0
            self.pos = to_pos
            self.dir = 'n'
            return
        
        self.pos = to_pos

        self.dir = None
        match (to_pos[0] - prev.pos[0],  to_pos[1] - prev.pos[1]):
            case (1, _):
                self.dir = 's'
            case (-1, _):
                self.dir = 'n'
            case (_, 1):
                self.dir = 'e'
            case (_, -1):
                self.dir = 'w'
        
        self.move_count = prev.move_count + 1 if self.dir == prev.dir else 1        
        self.cost = prev.cost + city[to_pos[0]][to_pos[1]]

    def __lt__(self, other):
        return self.cost < other.cost
    
###################################
# helper functions
###################################
def move(pos, dir):
    delta = ()
    match dir:
        case 'n':
            delta = (-1, 0)
        case 's':
            delta = (1, 0)
        case 'w':
            delta = (0, -1)
        case 'e':
            delta = (0, 1)
    
    return (pos[0] + delta[0], pos[1] + delta[1])

def opposite(dir):
    match dir:
        case 'n':
            return 's'
        case 's':
            return 'n'
        case 'e':
            return 'w'
        case 'w':
            return 'e'
    

def valid_pos(pos):
    r = pos[0]
    c = pos[1]
    return r >= 0 and c >= 0 and r < rows and c < rows


visited = dict()

def get_next(crucible):
    ret = set()
    if (crucible.move_count < max_moves):
        next_pos = move(crucible.pos, crucible.dir)
        if valid_pos(next_pos):
            next = Crucible(crucible, next_pos)
            visit_val = visited.get((next.pos, next.dir), -1)
            if  visit_val == -1 or next.move_count > visit_val:
                ret.add(next)

    if (crucible.move_count >= min_moves):
        for dir in [c for c in "nsew" if c != opposite(crucible.dir) and c != crucible.dir]:
            next_pos = move(crucible.pos, dir)
            if valid_pos(next_pos):
                next = Crucible(crucible, next_pos)
                visit_val = visited.get((next.pos, next.dir), -1)
                if  visit_val == -1 or next.move_count < visit_val:
                    ret.add(next)
    
    return ret


start_time = time()
# only used to check progress
queue_dist = 0
###################################
# Graph traversal
###################################
start = Crucible(None, (0, 0))
queue = []
heappush(queue, Crucible(start, (0, 1)))
heappush(queue, Crucible(start, (1, 0)))
results = set()

while True:
    current = heappop(queue)

    if current.cost > queue_dist:
        print(current.cost)
        queue_dist = current.cost

    visited[(current.pos, current.dir)] = current.move_count

    if current.pos == (rows-1, cols-1):
        print(current.cost)
        break

    for crucible in get_next(current):
        heappush(queue, crucible)

print("took %.2f seconds" %(time() - start_time))
