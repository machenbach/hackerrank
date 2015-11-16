'''
Created on Oct 15, 2015

@author: machenbach
'''


# number of queens
n = 31


def initialSpace():
    return {(x, y) for x in range(0, n) for y in range(0,n)}

# generate the set of squares covered by the queen at x, y
def covers((x, y)):
    # rank and file
    r = {(x, i) for i in range(0, n)}  | {(i, y) for i in range(0, n)}
    #diagonals
    p = min(x,y)
    d = {(x+i, y+i) for i in range(-p, n-p)} |  {(x+i, y-i) for i in range(-p, n-p)}
    # any more conditions after this
    return r | d

def placeQueen(queens, level, space):
    for q in space:
        queens.append(q)
        if level == n-1:
            return True
        if placeQueen(queens, level+1, space - covers(q)):
            return True
        queens.pop()
    return False

def isClear(queens, row, col):
    for q in queens:
        if q[0] == row or q[1] == col or abs(q[0] - row) == abs(q[1] - col):
            return False
    return True

def placeQueen2(queens, col):
    if col == n:
        return True
    
    for row in range(0,n):
        if isClear(queens, row, col):
            queens.append((row, col))
            if placeQueen2(queens, col + 1):
                return True
            queens.pop()
    return False

def printQueens(queens):
    board=[[". " for i in range(0, n)] for j in range(0, n)]
    for q in queens:
        board[q[0]][q[1]] = "Q "
    
    for l in board:
        print "".join(l)
        
def printSpace(space):
    board=[["# " for i in range(0, n)] for j in range(0, n)]
    for s in space:
        board[s[0]][s[1]] = ". "
    
    for l in board:
        print "".join(l)
        
queens = []
if placeQueen2(queens, 0):
    print "found answer"
else:
    print "did not"
    
print(sorted(queens))
printQueens(queens)
    
        
        