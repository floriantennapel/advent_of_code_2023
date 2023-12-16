import numpy as np

# reading input to a list of 2d arrays mirrors
# -----------------------------------
file = open('inputData.txt', mode='r')
mirrors = []

mirror = []
for line in file.readlines():
    if line == '\n':
        mirrors.append(mirror)
        mirror = []
        continue
        
    mirror.append([c for c in line.strip()])
mirrors.append(mirror)

file.close()
mirrors = [np.array(m) for m in mirrors]
# -----------------------------------

# get reflection line from mirror
# returns 0 if not found
# -----------------------------------
def is_reflection_line(row_i, mirror):
    fst = mirror[row_i-1::-1]
    snd = mirror[row_i:]

    n = min(len(fst), len(snd))

    return (fst[:n] == snd[:n]).all()

def get_reflection(mirror, orig=None):
    for i in range(len(mirror)):
        if is_reflection_line(i, mirror):
            if orig == None:
                return 100*i
            elif orig != None and 100*i != orig:
                return 100*i
                
    transposed = np.transpose(mirror)

    for i in range(len(transposed)):
        if is_reflection_line(i, transposed):
            if orig == None:
                return i
            elif orig != None and orig != i:
                return i

    # no reflection found
    return 0
# -----------------------------------

# part 1
print(sum(map(get_reflection, mirrors)))
# -----------------------------------


def smudge(i, j, mirror):
    if mirror[i][j] == '#':
        mirror[i][j] = '.'
    else:
        mirror[i][j] = '#'

def get_smudged_reflection(mirror):
    orig = get_reflection(mirror)

    for i in range(len(mirror)):
        for j in range(len(mirror[0])):
            smudge(i, j, mirror)
            new = get_reflection(mirror, orig)
            smudge(i, j, mirror)
            
            if new != orig and new != 0:
                return new
            
    return 0

    
# part 2
print(sum(map(get_smudged_reflection, mirrors)))


                       
            
    


