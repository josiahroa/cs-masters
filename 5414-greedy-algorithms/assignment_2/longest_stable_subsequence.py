# Program the recurrence for longest stable subsequence
# 0 <= i <= len(a)
# Note that if j == -1, then take aj = None
# else aj = a[j]
def lssLength(a, i, j):
    if i == len(a):
        return 0
    aj = a[j] if 0 <= j < len(a) else None 

    if aj != None and (abs(a[i] - a[j]) > 1):
        return lssLength(a, i+1, j)
        
    return max(lssLength(a, i+1, i) + 1, lssLength(a, i+1, j))
    
def memoizeLSS(a):
    T = {} # Initialize the memo table to empty dictionary
    # Now populate the entries for the base case 
    n = len(a)
    for j in range(-1, n):
        T[(n, j)] = 0 # i = n and j 
    # Now fill out the table : figure out the two nested for loops
    # It is important to also figure out the order in which you iterate the indices i and j
    # Use the recurrence structure itself as a guide: see for instance that T[(i,j)] will depend on T[(i+1, j)]
    # your code here
    
    for i in range(n-1, -1, -1):
        for j in range(-1, i):
            aj = a[j] if 0 <= j < len(a) else None 
            
            if aj != None and abs(a[i] - aj) > 1:
                T[(i,j)] = T[(i + 1, j)]
            else:
                T[(i, j)] = max(T[(i + 1), i] + 1, T[(i + 1, j)])
    
    return T
    
def computeLSS(a):
    T = memoizeLSS(a)
    S = []
    i, j = 0, -1
    
    while i < len(a):
        aj = a[j] if 0 <= j < len(a) else None
        
        canSkip = aj is None or abs(a[i] - aj) <=1
        
        if (T[(i + 1, i)] + 1 > T[(i+1), j]) and canSkip:
            S.append(a[i])
            j = i
        
        i += 1
    
    return S    